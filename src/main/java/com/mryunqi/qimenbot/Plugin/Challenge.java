package com.mryunqi.qimenbot.Plugin;

import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.AnyMessageEvent;
import com.mryunqi.qimenbot.Controller.*;
import com.mryunqi.qimenbot.Template.ChallengeTemplate;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class Challenge extends BotPlugin {
    private final JdbcTemplate jct;

    public Challenge(JdbcTemplate jct) {
        this.jct = jct;
    }

    @Override
    public int onAnyMessage(@NotNull Bot bot, @NotNull AnyMessageEvent event){
        String msg = event.getMessage();
        String userId = String.valueOf(event.getUserId());
        String groupId = String.valueOf(event.getGroupId());
        Command command = new Command();
        User user = new User(userId);
        String AuthGroupList = command.Get_CommandAuthGroupList(jct);
        String CmdMatcher = "^挑战(.*)$";
        String AliasCmd = user.Get_UserAliasCmd(jct,msg);
        if (msg.matches(CmdMatcher) | AliasCmd.matches(CmdMatcher)){
            if (PublicAuth.AuthHeader(bot, event, jct, user, AuthGroupList, groupId) == MESSAGE_IGNORE) {
                return MESSAGE_IGNORE;
            }
            if (AliasCmd.matches(CmdMatcher)){
                msg = AliasCmd;
            }
            String UserData = user.Get_UserData(jct);
            String Attribute = user.Get_UserNowAttribute(UserData);
            PublicAuth publicAuth = new PublicAuth();
            if (!command.Is_CommandForbidden(jct)){
                bot.sendMsg(event, publicAuth.Get_UserHead(UserData,Attribute)+"当前处于全服禁止战斗状态！", false);
                return MESSAGE_IGNORE;
            }
            if(user.Is_UserForbidden(jct)){
                bot.sendMsg(event, publicAuth.Get_UserHead(UserData,Attribute)+"你已被禁止任何战斗！", false);
                return MESSAGE_IGNORE;
            }
            if (user.Is_UserPVE(jct)){
                String BatId = user.Get_UserPVEId(jct);
                Fight fight = new Fight();
                String monsterName = fight.Get_PVE_MonsterName(jct, Integer.parseInt(BatId));
                bot.sendMsg(event, publicAuth.Get_UserHead(UserData,Attribute)+"您当前正在与【"+monsterName+"】战斗，请先结束战斗再挑战！\n<可用命令>\n逃跑", false);
                return MESSAGE_IGNORE;
            }
            if (user.Is_UserDead(jct)){
                bot.sendMsg(event, publicAuth.Get_UserHead(UserData,Attribute)+"您已经死亡！！\n<可用命令>\n复活", false);
                return MESSAGE_IGNORE;
            }
            String PlanText = msg.replaceAll("^挑战","").trim();
            if (PlanText.equals("")){
                bot.sendMsg(event, publicAuth.Get_UserHead(UserData,Attribute)+"你是在挑战空气吗？请输入挑战的目标！", false);
            }
            GameHunshou hunshou = new GameHunshou();
            if (!hunshou.Is_Hunshou_In_Map(jct, user.Get_UserNowMap(jct), PlanText)){
                bot.sendMsg(event, publicAuth.Get_UserHead(UserData,Attribute)+"【"+PlanText+"】不在当前地图，请查询当前地图魂兽！\n<可用命令>\n位置", false);
                return MESSAGE_IGNORE;
            }
            String HunShouData = hunshou.Get_HunshouData(jct, PlanText);
            String NowTime = Function.stampToDate(Function.Get_NowTime());
            String RandomNum = Function.Get_Random_Number();
            user.Set_UserBatData(jct,RandomNum,"pve");
            user.Reduce_UserCon(jct,1);
            Fight fight = new Fight();
            fight.Create_FightData(jct,PlanText,HunShouData, Integer.parseInt(RandomNum),NowTime);
            ChallengeTemplate challengeTemplate = new ChallengeTemplate();
            String message = challengeTemplate.get_ChallengeTemplate(UserData,Attribute,PlanText);
            bot.sendMsg(event, message, false);
            return MESSAGE_IGNORE;
        }
        return MESSAGE_IGNORE;
    }
}
