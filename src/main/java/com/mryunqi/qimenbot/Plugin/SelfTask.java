package com.mryunqi.qimenbot.Plugin;

import com.alibaba.fastjson2.JSONObject;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.AnyMessageEvent;
import com.mryunqi.qimenbot.Controller.Command;
import com.mryunqi.qimenbot.Controller.Function;
import com.mryunqi.qimenbot.Controller.PublicAuth;
import com.mryunqi.qimenbot.Controller.User;
import com.mryunqi.qimenbot.dao.WordnoticeDao;
import com.mryunqi.qimenbot.entity.Task;
import com.mryunqi.qimenbot.service.TaskService;
import com.mryunqi.qimenbot.service.UserService;
import com.mryunqi.qimenbot.service.WordnoticeService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import static com.mryunqi.qimenbot.Template.TaskTpl.getSelfTask;

/**
 * @author mryunqi
 * @date 2022/12/27
 */
@Component
@RequiredArgsConstructor
public class SelfTask extends BotPlugin {
    private final JdbcTemplate jct;
    private final UserService userService;
    private final TaskService taskService;
    private final WordnoticeDao wordnoticeDao;
    private final WordnoticeService wordnoticeService;

    @Override
    public int onAnyMessage(@NotNull Bot bot, @NotNull AnyMessageEvent event){
        String msg = event.getMessage();
        String cmdMatcher = "^我的任务$";
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
            String userData = user.Get_UserData(jct);
            String attribute = user.Get_UserNowAttribute(userData);
            com.mryunqi.qimenbot.entity.User player = userService.getById(userId);
            String initPlayerTaskData = player.getTaskData();
            StringBuilder data = new StringBuilder();
            if (initPlayerTaskData == null){
                data.append("暂未承接任何任务！\n");
                String message = getSelfTask(wordnoticeDao,wordnoticeService,userData,attribute,data);
                bot.sendMsg(event,message,false);
                return MESSAGE_IGNORE;
            }
            JSONObject jsonPlayerTaskData = JSONObject.parseObject(initPlayerTaskData);
            if (jsonPlayerTaskData.size() == 0){
                data.append("暂未承接任何任务！\n");
                String message = getSelfTask(wordnoticeDao,wordnoticeService,userData,attribute,data);
                bot.sendMsg(event,message,false);
                return MESSAGE_IGNORE;
            }
            JSONObject jsonAcceptTask = jsonPlayerTaskData.getJSONObject("已接取");
            if (jsonAcceptTask.size() == 0){
                data.append("暂未承接任何任务！\n");
                String message = getSelfTask(wordnoticeDao,wordnoticeService,userData,attribute,data);
                bot.sendMsg(event,message,false);
                return MESSAGE_IGNORE;
            }
            for (String id : jsonAcceptTask.keySet()){
                int i = 0;
                Task task = taskService.getById(id);
                String taskName = task.getName();
                JSONObject jsonAcceptTaskData = jsonAcceptTask.getJSONObject(id);
                int taskLv = task.getRacl();
                JSONObject jsonUserData = JSONObject.parseObject(userData);
                long playerLv = jsonUserData.getJSONObject("userData").getLong("等级");
                if (taskLv != 0){
                    if (playerLv < taskLv){
                        data.append("·").append(taskName).append("[未完成]\n");
                        continue;
                    }
                }
                String taskBeat = task.getBeat();
                if(taskBeat != null){
                    JSONObject jsonPlayerTaskBeat = jsonAcceptTaskData.getJSONObject("击杀魂兽");
                    JSONObject jsonTaskBeat = JSONObject.parseObject(taskBeat);
                    for (String hunshouName : jsonTaskBeat.keySet()){
                        if (jsonPlayerTaskBeat.getLong(hunshouName) < jsonTaskBeat.getLong(hunshouName)){
                            data.append("·").append(taskName).append("[未完成]\n");
                            i++;
                            break;
                        }
                    }
                }
                if (i > 0){
                    continue;
                }
                String npcTalk = task.getNpcd();
                if (npcTalk != null){
                    JSONObject jsonPlayerTaskNpc = jsonAcceptTaskData.getJSONObject("对话");
                    if (jsonPlayerTaskNpc.getInteger(npcTalk) != 1){
                        data.append("·").append(taskName).append("[未完成]\n");
                        continue;
                    }
                }
                String taskCi = task.getCi();
                if (taskCi != null){
                    JSONObject jsonTaskCi = JSONObject.parseObject(taskCi);
                    String playerBp = player.getBackpack();
                    JSONObject jsonPlayerBp = JSONObject.parseObject(playerBp);
                    for(String type : jsonTaskCi.keySet()){
                        if (jsonPlayerBp.getJSONObject(type).size() == 0){
                            data.append("·").append(taskName).append("[未完成]\n");
                            i++;
                            continue;
                        }
                        for (String goodsName : jsonTaskCi.getJSONObject(type).keySet()){
                            if (!jsonPlayerBp.getJSONObject(type).containsKey(goodsName)){
                                data.append("·").append(taskName).append("[未完成]\n");
                                i++;
                                continue;
                            }
                            if (jsonPlayerBp.getJSONObject(type).getLong(goodsName) < jsonTaskCi.getJSONObject(type).getLong(goodsName)){
                                data.append("·").append(taskName).append("[未完成]\n");
                                i++;
                                break;
                            }
                        }
                    }
                    if (i > 0){
                        continue;
                    }
                }
                data.append("·").append(taskName).append("[已完成]\n");
            }
            String message = getSelfTask(wordnoticeDao,wordnoticeService,userData,attribute,data);
            bot.sendMsg(event,message,false);
        }
        return MESSAGE_IGNORE;
    }
}
