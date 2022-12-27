package com.mryunqi.qimenbot.Plugin;

import com.alibaba.fastjson2.JSONObject;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.AnyMessageEvent;
import com.mryunqi.qimenbot.Controller.Command;
import com.mryunqi.qimenbot.Controller.PublicAuth;
import com.mryunqi.qimenbot.Controller.User;
import com.mryunqi.qimenbot.dao.UserDao;
import com.mryunqi.qimenbot.service.CommandService;
import com.mryunqi.qimenbot.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import static com.mryunqi.qimenbot.Controller.BP.sginInGoods;
import static com.mryunqi.qimenbot.Template.SginInTpl.sginInFail;
import static com.mryunqi.qimenbot.Template.SginInTpl.sginInSuccess;

/**
 * @author mryunqi
 * @date 2022/12/18
 */
@Component
public class SginIn extends BotPlugin {
    private final JdbcTemplate jct;
    private final UserService userService;
    private final CommandService commandService;
    private final UserDao userDao;

    public SginIn(JdbcTemplate jct, UserService userService, CommandService commandService, UserDao userDao) {
        this.jct = jct;
        this.userService = userService;
        this.commandService = commandService;
        this.userDao = userDao;
    }

    @Override
    public int onAnyMessage(@NotNull Bot bot, @NotNull AnyMessageEvent event){
        int day;
        int allDay;
        int nowDay;
        int isNowDay;
        JSONObject jsonSginInDay = new JSONObject();
        String msg = event.getMessage();
        String cmdMatcher = "^签到$";
        String userId = String.valueOf(event.getUserId());
        String groupId = String.valueOf(event.getGroupId());
        User user = new User(userId);
        Command command = new Command();
        String authGroupList = command.Get_CommandAuthGroupList(jct);
        String aliasCmd = user.Get_UserAliasCmd(jct, msg);
        if (msg.matches(cmdMatcher) | aliasCmd.matches(cmdMatcher)) {
            if (PublicAuth.AuthHeader(bot, event, jct, user, authGroupList, groupId) == MESSAGE_IGNORE) {
                return MESSAGE_IGNORE;
            }
            com.mryunqi.qimenbot.entity.User player = userService.getById(event.getUserId());
            String sginInDay = player.getSginInDay();
            if (sginInDay == null){
                allDay = 1;
                nowDay = 0;
                isNowDay = 0;
            }else {
                jsonSginInDay = JSONObject.parseObject(sginInDay);
                allDay = jsonSginInDay.getIntValue("allDay")+1;
                nowDay = jsonSginInDay.getIntValue("nowDay");
                isNowDay = jsonSginInDay.getIntValue("isNowDay");
            }
            String userData = user.Get_UserData(jct);
            String attribute = user.Get_UserNowAttribute(userData);
            if (isNowDay == 1){
                String message = sginInFail(userData,attribute);
                bot.sendMsg(event,message,false);
                return MESSAGE_IGNORE;
            }
            com.mryunqi.qimenbot.entity.Command com = commandService.getById(1);
            String commandSginIn = com.getSginIn();
            JSONObject jsonCommandSginIn = JSONObject.parseObject(commandSginIn);
            if (nowDay>=jsonCommandSginIn.size()){
                day = 1;
            }else {
                day = nowDay+1;
            }
            String data = sginInGoods(jct,user,jsonCommandSginIn,day);
            String message = sginInSuccess(userData,attribute,data,allDay,day);
            bot.sendMsg(event,message,false);
            jsonSginInDay.put("isNowDay",1);
            jsonSginInDay.put("nowDay",day);
            jsonSginInDay.put("allDay",allDay);
            com.mryunqi.qimenbot.entity.User player2 = userService.getById(event.getUserId());
            player2.setSginInDay(jsonSginInDay.toJSONString());
            userDao.updateById(player2);
        }
        return MESSAGE_IGNORE;
    }
}
