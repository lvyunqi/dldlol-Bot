package com.mryunqi.qimenbot.Plugin;

import com.alibaba.fastjson.JSONObject;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.WholeMessageEvent;
import com.mryunqi.qimenbot.Controller.*;
import com.mryunqi.qimenbot.Template.GameMapTemplate;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Position extends BotPlugin {
    private final JdbcTemplate jct;

    public Position(JdbcTemplate jct) {
        this.jct = jct;
    }

    @Override
    public int onWholeMessage(@NotNull Bot bot, @NotNull WholeMessageEvent event){
        String msg = event.getMessage();
        String userId = String.valueOf(event.getUserId());
        String groupId = String.valueOf(event.getGroupId());
        String CmdMatcher = "^位置$";
        String CmdMoveMatcher = "^向(.*)$";
        String CmdMsg = "位置";
        User user = new User(userId);
        Command command = new Command();
        String AuthGroupList = command.Get_CommandAuthGroupList(jct);
        if (msg.matches(CmdMatcher) | user.Is_UserHaveAlias(jct,CmdMsg,msg)){
            if (PublicAuth.AuthHeader(bot, event, jct, user, AuthGroupList, groupId) == MESSAGE_IGNORE) {
                return MESSAGE_IGNORE;
            }
            String UserNowPosition = user.Get_UserNowMap(jct);
            GameNPC npc = new GameNPC();
            GameHunshou hunshou = new GameHunshou();
            GameMap gamemap = new GameMap();
            GameMapTemplate gamemaptemplate = new GameMapTemplate();
            String UserData = user.Get_UserData(jct);
            String NPC = npc.get_NPC_Name_Template(jct, UserNowPosition);
            String Hunshou = hunshou.Get_Hunshou_Name_Template(jct, UserNowPosition);
            String Gamemap = gamemap.Get_MapAroundName(jct, UserNowPosition);
            String UserNowAttribute = user.Get_UserNowAttribute(UserData);
            String MapTransport = gamemap.Get_MapTransport(jct, UserNowPosition);
            String message = gamemaptemplate.GetGameMapTemplate(UserData,UserNowPosition,NPC,Hunshou,Gamemap,UserNowAttribute,MapTransport);
            bot.sendMsg(event, message, false);
            return  MESSAGE_IGNORE;
        }
        String AliasCmd = user.Get_UserAliasCmd(jct,msg);
        if (msg.matches(CmdMoveMatcher) | AliasCmd.matches(CmdMoveMatcher)){
            if (PublicAuth.AuthHeader(bot, event, jct, user, AuthGroupList, groupId) == MESSAGE_IGNORE) {
                return MESSAGE_IGNORE;
            }
            if (AliasCmd.matches(CmdMoveMatcher)){
                msg = AliasCmd;
            }
            String UserNowPosition = user.Get_UserNowMap(jct);
            String UserData = user.Get_UserData(jct);
            String Attribute = user.Get_UserNowAttribute(UserData);
            JSONObject jsonObject = JSONObject.parseObject(UserData);
            String PlanText = msg.replaceAll("^向","").trim();
            String regex = "^([上下左右])$";
            if (PlanText.equals("") | !PlanText.matches(regex)){
                bot.sendMsg(event, "To" +
                        jsonObject.getJSONObject("userInfo").getString("name") +
                        "〔" + jsonObject.getJSONObject("userData").getString(Attribute) + "〕\n" +
                        "格式不对哟！\n<可用命令>\n向 上/下/左/右", false);
                return MESSAGE_IGNORE;
            }
            GameMap gamemap = new GameMap();
            String MapName = gamemap.Get_MapName(jct, UserNowPosition, PlanText);
            if (Objects.equals(MapName, null)){
                assert jsonObject != null;
                bot.sendMsg(event, "To" +
                        jsonObject.getJSONObject("userInfo").getString("name") +
                        "〔" + jsonObject.getJSONObject("userData").getString(Attribute) + "〕\n" +
                        "没有这条路哦！带上眼睛看清地图再走吧！\n<可用命令>\n向 上/下/左/右", false);
                return MESSAGE_IGNORE;
            }
            GameNPC npc = new GameNPC();
            GameHunshou hunshou = new GameHunshou();
            String NPC = npc.get_NPC_Name_Template(jct, MapName);
            String Hunshou = hunshou.Get_Hunshou_Name_Template(jct, MapName);
            String Gamemap = gamemap.Get_MapAroundName(jct, MapName);
            GameMapTemplate gamemaptemplate = new GameMapTemplate();
            String MapTransport = gamemap.Get_MapTransport(jct, MapName);
            String message = gamemaptemplate.GetGameMapTemplate(UserData,MapName,NPC,Hunshou,Gamemap,Attribute,MapTransport);
            bot.sendMsg(event, message, false);
            user.Set_UserNowMap(jct, MapName);
            return  MESSAGE_IGNORE;
        }
        return MESSAGE_IGNORE;
    }
}
