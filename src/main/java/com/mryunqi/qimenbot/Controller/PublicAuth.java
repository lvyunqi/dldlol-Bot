package com.mryunqi.qimenbot.Controller;

import com.alibaba.fastjson.JSONObject;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.AnyMessageEvent;

import com.mikuac.shiro.dto.event.message.GuildMessageEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PublicAuth extends BotPlugin {

    public static int AuthHeader(Bot bot, AnyMessageEvent event, JdbcTemplate jct, User user, String AuthGroupList, String groupId) {
        JSONObject AuthGroup = JSONObject.parseObject(AuthGroupList);
        if (!AuthGroup.containsKey(groupId)){
            bot.sendMsg(event, "该群没有授权斗罗大陆！", false);
            return MESSAGE_IGNORE;
        }
        if (user.Is_UserExist(jct)){
            bot.sendMsg(event, "您还没有穿越到斗罗大陆！\n<可用命令>\n开始穿越", false);
            return MESSAGE_IGNORE;
        }
        if (user.Is_UserAwake(jct)){
            bot.sendMsg(event, "您还没有武魂觉醒！\n<可用命令>\n武魂觉醒", false);
            return MESSAGE_IGNORE;
        }
        return 6666;
    }

    public static int authGuildHeader(Bot bot, GuildMessageEvent event, JdbcTemplate jct, User user, String AuthGroupList, String groupId, String guildId, String channelId) {
        JSONObject AuthGroup = JSONObject.parseObject(AuthGroupList);
        if (!AuthGroup.containsKey(groupId)){
            bot.sendGuildMsg(guildId,channelId,"该频道没有授权斗罗大陆！");
            return MESSAGE_IGNORE;
        }
        if (user.Is_UserExist(jct)){
            bot.sendGuildMsg(guildId,channelId,"您还没有穿越到斗罗大陆！\n<可用命令>\n开始穿越");
            return MESSAGE_IGNORE;
        }
        if (user.Is_UserAwake(jct)){
            bot.sendGuildMsg(guildId,channelId,"您还没有武魂觉醒！\n<可用命令>\n武魂觉醒");
            return MESSAGE_IGNORE;
        }
        return 6666;
    }

    public String Get_UserHead(String UserData,String Attribute){
        JSONObject map = JSONObject.parseObject(UserData);
        return "To" + map.getJSONObject("userInfo").getString("name") + "〔" + map.getJSONObject("userData").getString(Attribute) + "〕\n";
    }
}
