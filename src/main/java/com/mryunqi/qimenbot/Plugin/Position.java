package com.mryunqi.qimenbot.Plugin;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.AnyMessageEvent;
import com.mryunqi.qimenbot.Controller.*;
import com.mryunqi.qimenbot.Template.GameMapTemplate;
import com.mryunqi.qimenbot.Util.ObjectUtil;
import com.mryunqi.qimenbot.entity.Map;
import com.mryunqi.qimenbot.service.MapService;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import static com.mryunqi.qimenbot.Template.GameMapTemplate.NowMapNoTP;

@Component
public class Position extends BotPlugin {
    private final JdbcTemplate jct;
    private final MapService mapService;

    public Position(JdbcTemplate jct, MapService mapService) {
        this.jct = jct;
        this.mapService = mapService;
    }

    @Override
    public int onAnyMessage(@NotNull Bot bot, @NotNull AnyMessageEvent event){
        String msg = event.getMessage();
        String userId = String.valueOf(event.getUserId());
        String groupId = String.valueOf(event.getGroupId());
        String CmdMatcher = "^位置$";
        String CmdMoveMatcher = "^向(.*)$";
        String CmdMsg = "位置";
        String CmdTP = "^传送(.*)$";
        User user = new User(userId);
        Command command = new Command();
        Function func = new Function();
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
        if (msg.matches(CmdTP) | AliasCmd.matches(CmdTP)){
            if (PublicAuth.AuthHeader(bot, event, jct, user, AuthGroupList, groupId) == MESSAGE_IGNORE) {
                return MESSAGE_IGNORE;
            }
            if (AliasCmd.matches(CmdMoveMatcher)){
                msg = AliasCmd;
            }
            if (user.Is_UserPVE(jct)){
                bot.sendMsg(event,"您当前正在战斗中，请先结束战斗后再尝试传送！",false);
                return MESSAGE_IGNORE;
            }
            GameMap gamemap = new GameMap();
            String PlanText = msg.replaceAll("^传送","").trim();
            String UserData = user.Get_UserData(jct);
            String Attribute = user.Get_UserNowAttribute(UserData);
            String UserNowPosition = user.Get_UserNowMap(jct);
            String NowMapTransport = gamemap.Get_MapTransport(jct, UserNowPosition);
            if (NowMapTransport.equals("")){
                bot.sendMsg(event,NowMapNoTP(UserData,Attribute),false);
                return MESSAGE_IGNORE;
            }
            Random rand = new Random();
            int s = rand.nextInt(5000);
            QueryWrapper<Map> mapQueryWrapper = new QueryWrapper<>();
            if (PlanText.equals("")){
                bot.sendMsg(event, "正在激活传送阵，您将随机传送到任一有传送阵的地图...", false);
                mapQueryWrapper.eq("tp",1);
                List<Map> maps = mapService.list(mapQueryWrapper);
                try {
                    Thread.sleep(s);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Map map = maps.get(rand.nextInt(maps.size()));
                String MapName = map.getMap();
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
                return MESSAGE_IGNORE;
            }
            mapQueryWrapper.eq("map",PlanText);
            Map map = mapService.getOne(mapQueryWrapper);
            if (ObjectUtil.isNull(map)){
                bot.sendMsg(event, "您要传送的地图不在传送阵传送范围内！", false);
                return MESSAGE_IGNORE;
            }
            int MapTp = map.getTp();
            if (MapTp == 0){
                bot.sendMsg(event, "您要传送的地图不存在传送阵，无法传送！", false);
                return MESSAGE_IGNORE;
            }
            String MapName = map.getMap();
            bot.sendMsg(event, "正在激活传送阵，您即将传送至["+MapName+"]...", false);
            if (Function.Get_RandomRun(10)){
                try {
                    Thread.sleep(s);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                bot.sendMsg(event, "出现空间乱流，传送失败，您即将被抛出传送通道！", false);
                QueryWrapper<Map> mapQueryWrapper1 = new QueryWrapper<>();
                mapQueryWrapper1.select("map");
                List<Map> maps = mapService.list(mapQueryWrapper1);
                Map mapOne = maps.get(rand.nextInt(maps.size()));
                String MapNameOne = mapOne.getMap();
                GameNPC npc = new GameNPC();
                GameHunshou hunshou = new GameHunshou();
                String NPC = npc.get_NPC_Name_Template(jct, MapNameOne);
                String Hunshou = hunshou.Get_Hunshou_Name_Template(jct, MapNameOne);
                String Gamemap = gamemap.Get_MapAroundName(jct, MapNameOne);
                GameMapTemplate gamemaptemplate = new GameMapTemplate();
                String MapTransport = gamemap.Get_MapTransport(jct, MapNameOne);
                String message = gamemaptemplate.GetGameMapTemplate(UserData,MapNameOne,NPC,Hunshou,Gamemap,Attribute,MapTransport);
                bot.sendMsg(event, message, false);
                user.Set_UserNowMap(jct, MapNameOne);
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
            return MESSAGE_IGNORE;
        }
        return MESSAGE_IGNORE;
    }
}
