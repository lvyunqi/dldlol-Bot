package com.mryunqi.qimenbot.Plugin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.AnyMessageEvent;
import com.mryunqi.qimenbot.Controller.Command;
import com.mryunqi.qimenbot.Controller.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class ChangeSkill extends BotPlugin {
    private final JdbcTemplate jct;

    public ChangeSkill(JdbcTemplate jct) {
        this.jct = jct;
    }

    @Override
    public int onAnyMessage(@NotNull Bot bot, @NotNull AnyMessageEvent event){
        String msg = event.getMessage();
        String userId = String.valueOf(event.getUserId());
        String groupId = String.valueOf(event.getGroupId());
        String cmd = "切换武魂";
        User user = new User(userId);
        Command command = new Command();
        String AuthGroupList = command.Get_CommandAuthGroupList(jct);
        if (cmd.equals(msg) & !AuthGroupList.contains(groupId)) {
            bot.sendMsg(event, "该群没有授权斗罗大陆RPG！", false);
            return MESSAGE_IGNORE;
        }
        if (cmd.equals(msg) & user.Is_UserExist(jct)) {
            bot.sendMsg(event, "您还没有穿越到斗罗大陆！\n<可用命令>\n开始穿越", false);
            return MESSAGE_IGNORE;
        }
        if (!cmd.equals(msg) & user.Is_UserExist(jct)) return MESSAGE_IGNORE;
        if (cmd.equals(msg) & user.Is_UserAwake(jct)){
            bot.sendMsg(event, "您还没有武魂觉醒！\n<可用命令>\n武魂觉醒", false);
            return MESSAGE_IGNORE;
        }
        if (!cmd.equals(msg) & user.Is_UserAwake(jct)) return MESSAGE_IGNORE;
        String Alias = user.Get_UserAlias(jct,cmd);
        if (!cmd.equals(msg) & Alias == null) return MESSAGE_IGNORE;
        JSONObject alias = JSONObject.parseObject(Alias);
        if (alias == null & !AuthGroupList.contains(groupId)) {
            return MESSAGE_IGNORE;
        }
        if (alias != null && alias.containsKey(msg) & !AuthGroupList.contains(groupId)) {
            return MESSAGE_IGNORE;
        }
        String NewAlias = JSON.toJSONString(alias);
        if (cmd.equals(msg) | NewAlias.contains(msg)) {
            String userData = user.Get_UserData(jct);
            String Attribute = user.Get_UserNowAttribute(userData);
            int AttributeNum = user.Get_UserAttributeNum(userData);
            if (Attribute.equals("第一武魂") & AttributeNum == 1) {
                JSONObject map = JSONObject.parseObject(userData);
                JSONObject userInfo = map.getJSONObject("userInfo");
                userInfo.put("当前属性", "气血之力");
                map.put("userInfo", userInfo);
                user.Set_UserData(jct,JSON.toJSONString(map));
                bot.sendMsg(event, "您已将武魂切换为【" +
                        map.getJSONObject("userData").getString("气血之力") +
                        "】！", false);
                return MESSAGE_IGNORE;
            }
            if (Attribute.equals("第一武魂") & AttributeNum == 2) {
                JSONObject map = JSONObject.parseObject(userData);
                JSONObject userInfo = map.getJSONObject("userInfo");
                userInfo.put("当前属性", "第二武魂");
                map.put("userInfo", userInfo);
                user.Set_UserData(jct,JSON.toJSONString(map));
                bot.sendMsg(event, "您已将武魂切换为【" +
                        map.getJSONObject("userData").getString("第二武魂") +
                        "】！", false);
                return MESSAGE_IGNORE;
            }
            if (Attribute.equals("气血之力")) {
                JSONObject map = JSONObject.parseObject(userData);
                JSONObject userInfo = map.getJSONObject("userInfo");
                userInfo.put("当前属性", "第一武魂");
                map.put("userInfo", userInfo);
                user.Set_UserData(jct,JSON.toJSONString(map));
                bot.sendMsg(event, "您已将武魂切换为【" +
                        map.getJSONObject("userData").getString("第一武魂") +
                        "】！", false);
                return MESSAGE_IGNORE;
            }
            if (Attribute.equals("第二武魂")) {
                JSONObject map = JSONObject.parseObject(userData);
                JSONObject userInfo = map.getJSONObject("userInfo");
                userInfo.put("当前属性", "第一武魂");
                map.put("userInfo", userInfo);
                user.Set_UserData(jct,JSON.toJSONString(map));
                bot.sendMsg(event, "您已将武魂切换为【" +
                        map.getJSONObject("userData").getString("第一武魂") +
                        "】！", false);
                return MESSAGE_IGNORE;
            }
            return MESSAGE_IGNORE;
        }
        return MESSAGE_IGNORE;
    }
}
