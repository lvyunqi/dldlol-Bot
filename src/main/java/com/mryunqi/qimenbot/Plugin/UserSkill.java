package com.mryunqi.qimenbot.Plugin;

import com.alibaba.fastjson2.JSONObject;
import com.mikuac.shiro.annotation.Shiro;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.WholeMessageEvent;
import com.mryunqi.qimenbot.Controller.*;
import com.mryunqi.qimenbot.Template.UserSkillTemplate;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import static com.mryunqi.qimenbot.Controller.Function.ReverseAttribute;


@Shiro
@Component
public class UserSkill extends BotPlugin {
    private final JdbcTemplate jct;

    public UserSkill(JdbcTemplate jct) {
        this.jct = jct;
    }

    @Override
    public int onWholeMessage(@NotNull Bot bot, @NotNull WholeMessageEvent event){
        String msg = event.getMessage();
        String userId = String.valueOf(event.getUserId());
        String groupId = String.valueOf(event.getGroupId());
        Command command = new Command();
        User user = new User(userId);
        GameHunshou hunshou = new GameHunshou();
        String AuthGroupList = command.Get_CommandAuthGroupList(jct);
        String CmdMatcher = "^魂环信息$";
        String CmdMatcher2 = "^附加魂环$";
        if (msg.matches(CmdMatcher)){
            if (PublicAuth.AuthHeader(bot, event, jct, user, AuthGroupList, groupId) == MESSAGE_IGNORE) {
                return MESSAGE_IGNORE;
            }
            String UserData = user.Get_UserData(jct);
            String Attribute = user.Get_UserNowAttribute(UserData);
            String UserTempSkill = user.Get_UserTempHuan(jct);
            if (UserTempSkill == null){
                bot.sendMsg(event, UserSkillTemplate.Get_UserSkillTempSkill_null(UserData,Attribute), false);
                return MESSAGE_IGNORE;
            }
            bot.sendMsg(event, UserSkillTemplate.Get_UserSkillTempSkill(UserData,Attribute,UserTempSkill), false);
            return MESSAGE_IGNORE;
        }
        if (msg.matches(CmdMatcher2)){
            if (PublicAuth.AuthHeader(bot, event, jct, user, AuthGroupList, groupId) == MESSAGE_IGNORE) {
                return MESSAGE_IGNORE;
            }
            String UserData = user.Get_UserData(jct);
            String Attribute = user.Get_UserNowAttribute(UserData);
            if (!user.Is_UserNeedHuan(jct,Attribute)){
                bot.sendMsg(event, UserSkillTemplate.Get_UserAddSkill_NoSkill(UserData,Attribute), false);
                return MESSAGE_IGNORE;
            }
            String UserTempSkill = user.Get_UserTempHuan(jct);
            if (UserTempSkill == null){
                bot.sendMsg(event, UserSkillTemplate.Get_UserSkillTempSkill_null(UserData,Attribute), false);
                return MESSAGE_IGNORE;
            }
            JSONObject map = JSONObject.parseObject(UserTempSkill);
            String Age = map.getString("Age");
            String HunShouName = map.getString("HunShouName");
            if (!hunshou.Is_Hunshou_Has_Skill(jct,HunShouName)){
                int UserHPMax = user.Get_UserMaxHP(jct);
                int SubUserHP = Function.Get_Random_Range(1,UserHPMax);
                user.Sub_UserNowHP(jct,SubUserHP);
                user.Clear_UserTempHuan(jct);
                bot.sendMsg(event, UserSkillTemplate.Get_UserAddSkill_NullSkill(UserData,Attribute,SubUserHP), false);
                return MESSAGE_IGNORE;
            }
            String HunShouSkill = hunshou.Get_Hunshou_Skill_Data(jct,HunShouName);
            String SkillName = hunshou.Get_RandomSkill(HunShouSkill);
            JSONObject SkillData = new JSONObject();
            SkillData.put("SkillName",SkillName);
            SkillData.put("SkillAge",Age);
            SkillData.put("SkillHunShouName",HunShouName);
            /*user.Add_UserSkillData(jct, SkillData.toJSONString(),Attribute);
            user.Clear_UserTempHuan(jct);*/

            JSONObject jsonSkillData = JSONObject.parseObject(user.Get_UserSkillData(jct));
            String ReverseAttribute = ReverseAttribute(Attribute,UserData);
            JSONObject json_Attribute_skill_data = jsonSkillData.getJSONObject(Attribute);
            JSONObject json_ReverseAttribute_skill_data = jsonSkillData.getJSONObject(ReverseAttribute);
            if (json_Attribute_skill_data == null){
                if (json_ReverseAttribute_skill_data == null){
                    user.Add_UserSkillData(jct, SkillData.toJSONString(),Attribute);
                    user.Clear_UserTempHuan(jct);
                    user.Update_UserLevelAddHuan(jct);
                    bot.sendMsg(event, UserSkillTemplate.Get_UserAddSkill_Success(UserData,Attribute, String.valueOf(SkillData)), false);
                    return MESSAGE_IGNORE;
                }
                user.Add_UserSkillData(jct, SkillData.toJSONString(),Attribute);
                user.Clear_UserTempHuan(jct);
                user.Update_UserLevel_NO_AddHuan(jct);
                bot.sendMsg(event, UserSkillTemplate.Get_UserAddSkill_Success(UserData,Attribute, String.valueOf(SkillData)), false);
                return MESSAGE_IGNORE;
            }
            int AttributeNum = json_Attribute_skill_data.size();
            int ReverseAttributeNum = json_ReverseAttribute_skill_data.size();
            if (AttributeNum >= ReverseAttributeNum){
                user.Add_UserSkillData(jct, SkillData.toJSONString(),Attribute);
                user.Clear_UserTempHuan(jct);
                user.Update_UserLevelAddHuan(jct);
                bot.sendMsg(event, UserSkillTemplate.Get_UserAddSkill_Success(UserData,Attribute, String.valueOf(SkillData)), false);
                return MESSAGE_IGNORE;
            }
            user.Add_UserSkillData(jct, SkillData.toJSONString(),Attribute);
            user.Clear_UserTempHuan(jct);
            user.Update_UserLevel_NO_AddHuan(jct);
            bot.sendMsg(event, UserSkillTemplate.Get_UserAddSkill_Success(UserData,Attribute, String.valueOf(SkillData)), false);
        }
        return MESSAGE_IGNORE;
    }

}
