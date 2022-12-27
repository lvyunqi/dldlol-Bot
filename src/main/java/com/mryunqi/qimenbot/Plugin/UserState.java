package com.mryunqi.qimenbot.Plugin;

import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.AnyMessageEvent;
import com.mryunqi.qimenbot.Controller.Command;
import com.mryunqi.qimenbot.Controller.Function;
import com.mryunqi.qimenbot.Controller.PublicAuth;
import com.mryunqi.qimenbot.Controller.User;
import com.mryunqi.qimenbot.Template.UserStateTemplate;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


/**
 * 指令：状态
 * @PluginName: 状态
 * @author mryunqi
 * @since 2022-6-22
 * @version 1.0
 */

@Component
public class UserState extends BotPlugin {
    private final JdbcTemplate jct;

    public UserState(JdbcTemplate jct) {
        this.jct = jct;
    }

    @Override
    public int onAnyMessage(@NotNull Bot bot, @NotNull AnyMessageEvent event){
        String msg = event.getMessage();
        String userId = String.valueOf(event.getUserId());
        String groupId = String.valueOf(event.getGroupId());
        String CmdMatcher = "^状态$";
        String CmdOtherMatcher = "^状态(.*)$";
        String CmdMsg = "状态";
        User user = new User(userId);
        Command command = new Command();
        String AuthGroupList = command.Get_CommandAuthGroupList(jct);
        if (msg.matches(CmdMatcher) | user.Is_UserHaveAlias(jct,CmdMsg,msg)) {
            if (PublicAuth.AuthHeader(bot, event, jct, user, AuthGroupList, groupId) == MESSAGE_IGNORE) {
                return MESSAGE_IGNORE;
            }
            Function func = new Function();
            String userData = user.Get_UserData(jct);
            String userSkill = user.Get_UserSkillData(jct);
            int skillNum = func.Get_SkillNum(userSkill);
            String LvName = user.Get_UserLevelName(skillNum);
            String WeaponAttack = user.Get_UserWeaponName(jct,0);
            String WeaponDefense = user.Get_UserWeaponName(jct,1);
            String UpExp = Command.Get_CommandExpUp(jct);
            UserStateTemplate userStateTemplate = new UserStateTemplate();
            String message = userStateTemplate.UserShelfState(userId,userData,WeaponAttack,WeaponDefense,LvName
                    ,"","","","", Integer.parseInt(UpExp));
            bot.sendMsg(event, message, false);
            return MESSAGE_IGNORE;
        }
        if (msg.matches(CmdOtherMatcher)) {
            if (PublicAuth.AuthHeader(bot, event, jct, user, AuthGroupList, groupId) == MESSAGE_IGNORE) {
                return MESSAGE_IGNORE;
            }
            String PlanText = msg.replaceAll("^状态","");
            // 判断PlanText是否为数字
            if (PlanText.matches("^\\d*$")) {
                if (PlanText.length() > 11){
                    return MESSAGE_IGNORE;
                }
                User OtherUser = new User(PlanText);
                if (OtherUser.Is_UserExist(jct)){
                    bot.sendMsg(event, "对方还没有穿越到斗罗大陆！", false);
                    return MESSAGE_IGNORE;
                }
                if (OtherUser.Is_UserAwake(jct)){
                    bot.sendMsg(event, "对方还没有觉醒武魂！", false);
                    return MESSAGE_IGNORE;
                }
                Function func = new Function();
                String userData = user.Get_UserData(jct);
                String OtherUserData = OtherUser.Get_UserData(jct);
                String userSkill = OtherUser.Get_UserSkillData(jct);
                int skillNum = func.Get_SkillNum(userSkill);
                String LvName = OtherUser.Get_UserLevelName(skillNum);
                String WeaponAttack = OtherUser.Get_UserWeaponName(jct,0);
                String WeaponDefense = OtherUser.Get_UserWeaponName(jct,1);
                String UpExp = Command.Get_CommandExpUp(jct);
                UserStateTemplate userStateTemplate = new UserStateTemplate();
                String message = userStateTemplate.UserOtherState(PlanText,userData,OtherUserData,WeaponAttack,WeaponDefense,LvName
                        ,"","","","", Integer.parseInt(UpExp));
                bot.sendMsg(event, message, false);
                return MESSAGE_IGNORE;
            }
            // 获取[CQ:at,qq=]的qq号码
            String qq = PlanText.replaceAll("\\D", "");
            if (qq.length() > 11) {
                return MESSAGE_IGNORE;
            }
            User OtherUser = new User(qq);
            if (OtherUser.Is_UserExist(jct)){
                bot.sendMsg(event, "对方还没有穿越到斗罗大陆！", false);
                return MESSAGE_IGNORE;
            }
            if (OtherUser.Is_UserAwake(jct)){
                bot.sendMsg(event, "对方还没有觉醒武魂！", false);
                return MESSAGE_IGNORE;
            }
            Function func = new Function();
            String userData = user.Get_UserData(jct);
            String OtherUserData = OtherUser.Get_UserData(jct);
            String userSkill = OtherUser.Get_UserSkillData(jct);
            int skillNum = func.Get_SkillNum(userSkill);
            String LvName = OtherUser.Get_UserLevelName(skillNum);
            String WeaponAttack = OtherUser.Get_UserWeaponName(jct,0);
            String WeaponDefense = OtherUser.Get_UserWeaponName(jct,1);
            String UpExp = Command.Get_CommandExpUp(jct);
            UserStateTemplate userStateTemplate = new UserStateTemplate();
            String message = userStateTemplate.UserOtherState(qq,userData,OtherUserData,WeaponAttack,WeaponDefense,LvName
                    ,"","","","", Integer.parseInt(UpExp));
            bot.sendMsg(event, message, false);
            return MESSAGE_IGNORE;
        }
        return MESSAGE_IGNORE;
    }
}