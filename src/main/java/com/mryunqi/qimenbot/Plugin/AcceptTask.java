package com.mryunqi.qimenbot.Plugin;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.AnyMessageEvent;
import com.mryunqi.qimenbot.Controller.Command;
import com.mryunqi.qimenbot.Controller.Function;
import com.mryunqi.qimenbot.Controller.PublicAuth;
import com.mryunqi.qimenbot.Controller.User;
import com.mryunqi.qimenbot.Util.ObjectUtil;
import com.mryunqi.qimenbot.dao.TaskDao;
import com.mryunqi.qimenbot.dao.UserDao;
import com.mryunqi.qimenbot.dao.WordnoticeDao;
import com.mryunqi.qimenbot.entity.Task;
import com.mryunqi.qimenbot.service.TaskService;
import com.mryunqi.qimenbot.service.UserService;
import com.mryunqi.qimenbot.service.WordnoticeService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import static com.mryunqi.qimenbot.Controller.BP.taskGoodsString;
import static com.mryunqi.qimenbot.Template.TaskTpl.*;

/**
 * @author mryunqi
 * @date 2022/12/26
 */
@Component
@RequiredArgsConstructor
public class AcceptTask extends BotPlugin {
    private final JdbcTemplate jct;
    private final UserService userService;
    private final UserDao userDao;
    private final TaskService taskService;
    private final TaskDao taskDao;
    private final WordnoticeDao wordnoticeDao;
    private final WordnoticeService wordnoticeService;
    @Override
    public int onAnyMessage(@NotNull Bot bot, @NotNull AnyMessageEvent event){
        String msg = event.getMessage();
        String cmdMatcher = "^接受任务(.*)$";
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
            if (aliasCmd.matches(cmdMatcher)){
                msg = aliasCmd;
            }
            String userData = user.Get_UserData(jct);
            String attribute = user.Get_UserNowAttribute(userData);
            String planText = msg.replaceAll("^接受任务","");
            if("".equals(planText)){
                PublicAuth publicAuth = new PublicAuth();
                bot.sendMsg(event, publicAuth.Get_UserHead(userData,attribute)+"不能接受空气任务！", false);
                return MESSAGE_IGNORE;
            }
            QueryWrapper<Task> taskWrapper = new QueryWrapper<>();
            taskWrapper.eq("name",planText);
            Task task = taskDao.selectOne(taskWrapper);
            if (ObjectUtil.isNull(task)){
                String message = getTaskNoTask(wordnoticeDao,wordnoticeService,userData,attribute);
                bot.sendMsg(event,message,false);
                return MESSAGE_IGNORE;
            }
            com.mryunqi.qimenbot.entity.User player = userService.getById(event.getUserId());
            String initPlayerTaskData = player.getTaskData();
            JSONObject jsonPlayerTaskData = JSONObject.parseObject(initPlayerTaskData);
            if (jsonPlayerTaskData == null){
                JSONObject json = new JSONObject();
                JSONArray js = new JSONArray();
                JSONObject jsonB = new JSONObject();
                json.put("已完成",js);
                json.put("已接取",jsonB);
                jsonPlayerTaskData = json;
            }
            JSONArray completed = jsonPlayerTaskData.getJSONArray("已完成");
            JSONObject underway = jsonPlayerTaskData.getJSONObject("已接取");
            JSONObject jsonObject = new JSONObject();
            for (Object id : underway.keySet()){
                jsonObject.put(String.valueOf(id),"已接取");
            }
            for(Object id : completed){
                jsonObject.put(String.valueOf(id), "已完成");
            }
            int taskId = task.getId();
            if (jsonObject.containsKey(String.valueOf(taskId))){
                String message = getTaskConnected(wordnoticeDao,wordnoticeService,userData,attribute);
                bot.sendMsg(event,message,false);
                return MESSAGE_IGNORE;
            }
            String lastTask = task.getCat();
            StringBuilder limitData = new StringBuilder();
            int limitNum = 0;
            if (lastTask != null){
                if(!completed.contains(lastTask)){
                    limitNum++;
                    limitData.append("·").append("完成任务[").append(lastTask).append("]\n");
                }
            }
            JSONObject jsonUserData = JSONObject.parseObject(userData);
            long playerLv = jsonUserData.getJSONObject("userData").getLong("等级");
            String taskLimit = task.getTasklimit();
            JSONObject jsonTaskLimit = JSONObject.parseObject(taskLimit);
            long taskLv = jsonTaskLimit.getLong("等级");
            if (playerLv<taskLv){
                limitNum++;
                limitData.append("·达到等级").append("[").append(taskLv).append("级]\n");
            }
            long ce = func.Get_Ce(userData);
            if (ce < jsonTaskLimit.getLong("战力")){
                limitNum++;
                limitData.append("·达到战力").append("[").append(ce).append("点]\n");
            }
            if(limitNum !=0){
                String message = getTaskUnable(wordnoticeDao,wordnoticeService,userData,attribute,limitData);
                bot.sendMsg(event,message,false);
                return MESSAGE_IGNORE;
            }
            String taskDetails = task.getDetails();
            JSONObject jsonTaskDetails = JSONObject.parseObject(taskDetails);
            String acceptDetail = jsonTaskDetails.getString("承接");
            String reward = task.getReward();
            JSONObject jsonReward = JSONObject.parseObject(reward);
            String taskReward = taskGoodsString(jsonReward);
            String message = getTaskAccept(wordnoticeDao,wordnoticeService,userData,attribute,planText,acceptDetail,taskReward);
            bot.sendMsg(event,message,false);
            JSONObject acceptArray = new JSONObject();
            JSONObject acceptData = new JSONObject();
            JSONObject acceptBeat = new JSONObject();
            JSONObject acceptNpc = new JSONObject();
            String initBeat = task.getBeat();
            if (initBeat != null){
                JSONObject jsonBeat = JSONObject.parseObject(initBeat);
                for(String hunshou: jsonBeat.keySet()){
                    acceptBeat.put(hunshou,0);
                }
            }
            String npcD = task.getNpcd();
            if (npcD != null){
                acceptNpc.put(npcD,0);
            }
            acceptData.put("击杀魂兽",acceptBeat);
            acceptData.put("对话",acceptNpc);
            acceptArray.put(String.valueOf(taskId),acceptData);
            jsonPlayerTaskData.put("已接取",acceptArray);
            player.setTaskData(jsonPlayerTaskData.toJSONString());
            userDao.updateById(player);
        }
        return MESSAGE_IGNORE;
    }
    public static JSONObject ciBuild(JSONObject jsonCi){
        for (String key : jsonCi.keySet()){
            if ("material".equals(key)){
                for (String key2 : jsonCi.getJSONObject(key).keySet()){
                    jsonCi.getJSONObject(key).put(key2, 0);
                }
            }
            if ("consumables".equals(key)){
                for (String key2 : jsonCi.getJSONObject(key).keySet()){
                    jsonCi.getJSONObject(key).put(key2, 0);
                }
            }
        }
        return jsonCi;
    }
}
