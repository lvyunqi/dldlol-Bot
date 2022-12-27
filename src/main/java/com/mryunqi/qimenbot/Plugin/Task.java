package com.mryunqi.qimenbot.Plugin;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.AnyMessageEvent;
import com.mryunqi.qimenbot.Controller.Command;
import com.mryunqi.qimenbot.Controller.Function;
import com.mryunqi.qimenbot.Controller.PublicAuth;
import com.mryunqi.qimenbot.Controller.User;
import com.mryunqi.qimenbot.dao.WordnoticeDao;
import com.mryunqi.qimenbot.entity.Npc;
import com.mryunqi.qimenbot.service.NpcService;
import com.mryunqi.qimenbot.service.TaskService;
import com.mryunqi.qimenbot.service.UserService;
import com.mryunqi.qimenbot.service.WordnoticeService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mryunqi.qimenbot.Template.TaskTpl.getTaskTemplate;

/**
 * @author mryunqi
 * @date 2022/12/26
 */
@Component
@RequiredArgsConstructor
public class Task extends BotPlugin {
    private final JdbcTemplate jct;
    private final UserService userService;
    private final NpcService npcService;
    private final TaskService taskService;
    private final WordnoticeDao wordnoticeDao;
    private final WordnoticeService wordnoticeService;
    @Override
    public int onAnyMessage(@NotNull Bot bot, @NotNull AnyMessageEvent event){
        String msg = event.getMessage();
        String cmdMatcher = "^任务$";
        String userId = String.valueOf(event.getUserId());
        String groupId = String.valueOf(event.getGroupId());
        User user = new User(userId);
        Command command = new Command();
        Function func = new Function();
        String authGroupList = command.Get_CommandAuthGroupList(jct);
        String aliasCmd = user.Get_UserAliasCmd(jct, msg);
        if (msg.matches(cmdMatcher) | aliasCmd.matches(cmdMatcher)){
            if (PublicAuth.AuthHeader(bot, event, jct, user, authGroupList, groupId) == MESSAGE_IGNORE) {
                return MESSAGE_IGNORE;
            }
            com.mryunqi.qimenbot.entity.User player = userService.getById(event.getUserId());
            String npcName = player.getNpc();
            if (npcName == null){
                bot.sendMsg(event,"你还未跟任何NPC对话，请先与NPC对话！",false);
                return MESSAGE_IGNORE;
            }
            Npc npc = npcService.getById(npcName);
            String initTask = npc.getTask();
            StringBuilder data = new StringBuilder();
            if (initTask == null){
                data.append("此人并未发布任何任务！\n");
                String message = getTaskTemplate(wordnoticeDao,wordnoticeService,data);
                bot.sendMsg(event,message,false);
                return MESSAGE_IGNORE;
            }
            List<Integer> taskList = Stream.of(initTask.replaceAll("\\[|\\]", "").split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            String initPlayerTaskData = player.getTaskData();
            String userData = user.Get_UserData(jct);
            JSONObject jsonUserData = JSONObject.parseObject(userData);
            if (initPlayerTaskData == null){
                for(int id :taskList){
                    com.mryunqi.qimenbot.entity.Task task = taskService.getById(id);
                    String taskName = task.getName();
                    String lastTask = task.getCat();
                    if (lastTask != null){
                        data.append("·").append(taskName).append("[不可接]\n");
                        continue;
                    }
                    long playerLv = jsonUserData.getJSONObject("userData").getLong("等级");
                    String taskLimit = task.getTasklimit();
                    JSONObject jsonTaskLimit = JSONObject.parseObject(taskLimit);
                    long taskLv = jsonTaskLimit.getLong("等级");
                    if (playerLv<taskLv){
                        data.append("·").append(taskName).append("[不可接]\n");
                        continue;
                    }
                    long ce = func.Get_Ce(userData);
                    if (ce < jsonTaskLimit.getLong("战力")){
                        data.append("·").append(taskName).append("[不可接]\n");
                        continue;
                    }
                    data.append("·").append(taskName).append("[可接]\n");
                }
                String message = getTaskTemplate(wordnoticeDao,wordnoticeService,data);
                bot.sendMsg(event,message,false);
                return MESSAGE_IGNORE;
            }
            JSONObject jsonPlayerTaskData = JSONObject.parseObject(initPlayerTaskData);
            if (jsonPlayerTaskData.size() == 0){
                for(int id :taskList){
                    com.mryunqi.qimenbot.entity.Task task = taskService.getById(id);
                    String taskName = task.getName();
                    String lastTask = task.getCat();
                    if (lastTask != null){
                        data.append("·").append(taskName).append("[不可接]\n");
                        continue;
                    }
                    long playerLv = jsonUserData.getJSONObject("userData").getLong("等级");
                    String taskLimit = task.getTasklimit();
                    JSONObject jsonTaskLimit = JSONObject.parseObject(taskLimit);
                    long taskLv = jsonTaskLimit.getLong("等级");
                    if (playerLv<taskLv){
                        data.append("·").append(taskName).append("[不可接]\n");
                        continue;
                    }
                    long ce = func.Get_Ce(userData);
                    if (ce < jsonTaskLimit.getLong("战力")){
                        data.append("·").append(taskName).append("[不可接]\n");
                        continue;
                    }
                    data.append("·").append(taskName).append("[可接]\n");
                }
                String message = getTaskTemplate(wordnoticeDao,wordnoticeService,data);
                bot.sendMsg(event,message,false);
                return MESSAGE_IGNORE;
            }
            JSONArray completed = jsonPlayerTaskData.getJSONArray("已完成");
            JSONArray underway = jsonPlayerTaskData.getJSONArray("已接取");
            JSONObject jsonObject = new JSONObject();
            for (Object id : underway){
                jsonObject.put((String) id,"已接取");
            }
            for(Object id : completed){
                jsonObject.put((String) id, "已完成");
            }
            for (int id : taskList){
                if (!jsonObject.containsKey((String.valueOf(id)))){
                    com.mryunqi.qimenbot.entity.Task task = taskService.getById(id);
                    String taskName = task.getName();
                    String lastTask = task.getCat();
                    if (lastTask != null){
                        data.append("·").append(taskName).append("[不可接]\n");
                        continue;
                    }
                    long playerLv = jsonUserData.getJSONObject("userData").getLong("等级");
                    String taskLimit = task.getTasklimit();
                    JSONObject jsonTaskLimit = JSONObject.parseObject(taskLimit);
                    long taskLv = jsonTaskLimit.getLong("等级");
                    if (playerLv<taskLv){
                        data.append("·").append(taskName).append("[不可接]\n");
                        continue;
                    }
                    long ce = func.Get_Ce(userData);
                    if (ce < jsonTaskLimit.getLong("战力")){
                        data.append("·").append(taskName).append("[不可接]\n");
                        continue;
                    }
                    data.append("·").append(taskName).append("[可接]\n");
                }
            }
        }
        return MESSAGE_IGNORE;
    }
}
