package com.mryunqi.qimenbot.Plugin;

import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.AnyMessageEvent;
import com.mryunqi.qimenbot.Controller.Command;
import com.mryunqi.qimenbot.Controller.Function;
import com.mryunqi.qimenbot.Controller.PublicAuth;
import com.mryunqi.qimenbot.Controller.User;
import com.mryunqi.qimenbot.Template.SetUserSkillTemplate;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class SetUserSkill extends BotPlugin {
    private final JdbcTemplate jct;

    public SetUserSkill(JdbcTemplate jct) {
        this.jct = jct;
    }

    @Override
    public int onAnyMessage(@NotNull Bot bot, @NotNull AnyMessageEvent event){
        String msg = event.getMessage();
        String userId = String.valueOf(event.getUserId());
        String groupId = String.valueOf(event.getGroupId());
        Command command = new Command();
        User user = new User(userId);
        Function func = new Function();
        String AuthGroupList = command.Get_CommandAuthGroupList(jct);
        String CmdMatcher = "^设置魂技(.*)$";
        if (msg.matches(CmdMatcher)){
            if (PublicAuth.AuthHeader(bot, event, jct, user, AuthGroupList, groupId) == MESSAGE_IGNORE) {
                return MESSAGE_IGNORE;
            }
            String UserData = user.Get_UserData(jct);
            String Attribute = user.Get_UserNowAttribute(UserData);
            String PlanText = msg.replaceAll("^设置魂技","").trim();
            // 获取字符-坐标
            int index = PlanText.indexOf("-");
            // 判断是否有-
            if (!PlanText.contains("-")){
                bot.sendMsg(event, "请输入正确的魂技格式：设置魂技[魂技ID]-[魂技名称]", false);
                return MESSAGE_IGNORE;
            }
            String SkillId = PlanText.substring(0,index).trim();
            String SkillName = PlanText.substring(index+1).trim();
            if (index == 0){
                bot.sendMsg(event, "请输入正确的魂技格式：设置魂技[魂技ID]-[魂技名称]", false);
                return MESSAGE_IGNORE;
            }
            if (PlanText.charAt(PlanText.length() - 1) == '-'){
                bot.sendMsg(event, "请输入正确的魂技格式：设置魂技[魂技ID]-[魂技名称]", false);
                return MESSAGE_IGNORE;
            }
            // 判断SkillId是否为数字
            if(!func.Is_Number(SkillId)){
                bot.sendMsg(event, SetUserSkillTemplate.Get_SetUserSkill_No_Num(UserData,Attribute), false);
                return MESSAGE_IGNORE;
            }
            if (!user.Is_UserSkillExist(jct,SkillName,Attribute)){
                bot.sendMsg(event, SetUserSkillTemplate.Get_SetUserSkill_No_Skill(SkillName,UserData,Attribute), false);
                return MESSAGE_IGNORE;
            }
            user.Add_UserSkill(jct,SkillName,SkillId,Attribute);
            bot.sendMsg(event, SetUserSkillTemplate.Get_SetUserSkill_Success(UserData,Attribute,SkillId,SkillName), false);
            return MESSAGE_IGNORE;
        }
        return MESSAGE_IGNORE;
    }
}
