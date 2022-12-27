package com.mryunqi.qimenbot.Plugin;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mikuac.shiro.annotation.Shiro;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.AnyMessageEvent;
import com.mryunqi.qimenbot.Controller.Command;
import com.mryunqi.qimenbot.Controller.PublicAuth;
import com.mryunqi.qimenbot.Controller.User;
import com.mryunqi.qimenbot.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import static com.mryunqi.qimenbot.Controller.GameHunshou.Get_HunShouAgeLevelName;
import static com.mryunqi.qimenbot.Template.HunHuanListTpl.*;
import static com.mryunqi.qimenbot.Util.NumberUtil.int2chineseNum;

@Shiro
@Component
public class HunHuanList extends BotPlugin {
    private final JdbcTemplate jct;
    private final UserService userService;

    public HunHuanList(JdbcTemplate jct, UserService userService) {
        this.jct = jct;
        this.userService = userService;
    }

    @Override
    public int onAnyMessage(@NotNull Bot bot, @NotNull AnyMessageEvent event){
        String msg = event.getMessage();
        String CmdMatcher = "^查看魂环(.*)$";
        String userId = String.valueOf(event.getUserId());
        String groupId = String.valueOf(event.getGroupId());
        User user = new User(userId);
        Command command = new Command();
        String AuthGroupList = command.Get_CommandAuthGroupList(jct);
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
            String PlanText = msg.replaceAll("^查看魂环","");
            if (PlanText.equals("")){
                QueryWrapper<com.mryunqi.qimenbot.entity.User> wrapper = new QueryWrapper<>();
                wrapper.eq("qq",userId);
                com.mryunqi.qimenbot.entity.User player = userService.getOne(wrapper);
                String SkillData = player.getSkillData();
                if (StringUtils.isBlank(SkillData)){
                    String message = No_HunHuan(UserData,Attribute,userId);
                    bot.sendMsg(event, message, false);
                    return MESSAGE_IGNORE;
                }
                JSONObject jsonSkillData = JSONObject.parseObject(SkillData);
                JSONObject jsonObject = jsonSkillData.getJSONObject(Attribute);
                StringBuilder data = new StringBuilder();
                for (String key : jsonObject.keySet()){
                    JSONObject jsonObject1 = jsonObject.getJSONObject(key);
                    String SkillName = jsonObject1.getString("SkillName");
                    String SkillAge = jsonObject1.getString("SkillAge");
                    data.append(int2chineseNum(Integer.parseInt(key))).append("环：").append(SkillName)
                            .append("[").append(Get_HunShouAgeLevelName(Integer.parseInt(SkillAge))).append("][")
                            .append(SkillAge).append("年]\n");
                }
                String message = HunHuanListTemp(UserData,Attribute,userId, String.valueOf(data));
                bot.sendMsg(event, message, false);
                return MESSAGE_IGNORE;
            }
            // 判断PlanText是否为数字
            if (PlanText.matches("^\\d*$")){
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
                User player = new User(PlanText);
                String OtherUserData = player.Get_UserData(jct);
                QueryWrapper<com.mryunqi.qimenbot.entity.User> wrapper = new QueryWrapper<>();
                wrapper.eq("qq",PlanText);
                com.mryunqi.qimenbot.entity.User Player = userService.getOne(wrapper);
                String SkillData = Player.getSkillData();
                String OtherAttribute = player.Get_UserNowAttribute(OtherUserData);
                if (StringUtils.isBlank(SkillData)){
                    String message = Other_No_HunHuan(UserData,OtherUserData,Attribute,OtherAttribute,PlanText);
                    bot.sendMsg(event, message, false);
                    return MESSAGE_IGNORE;
                }
                JSONObject jsonSkillData = JSONObject.parseObject(SkillData);
                JSONObject jsonObject = jsonSkillData.getJSONObject(Attribute);
                StringBuilder data = new StringBuilder();
                for (String key : jsonObject.keySet()){
                    JSONObject jsonObject1 = jsonObject.getJSONObject(key);
                    String SkillName = jsonObject1.getString("SkillName");
                    String SkillAge = jsonObject1.getString("SkillAge");
                    data.append(int2chineseNum(Integer.parseInt(key))).append("环：").append(SkillName)
                            .append("[").append(Get_HunShouAgeLevelName(Integer.parseInt(SkillAge))).append("][")
                            .append(SkillAge).append("年]\n");
                }
                String message = Other_HunHuanListTemp(UserData,OtherUserData,Attribute,OtherAttribute,PlanText, String.valueOf(data));
                bot.sendMsg(event, message, false);
                return MESSAGE_IGNORE;
            }
            // 获取[CQ:at,qq=]的qq号码
            String OtherQQ = PlanText.replaceAll("\\D", "");
            if (OtherQQ.length() > 11) {
                return MESSAGE_IGNORE;
            }
            User OtherUser = new User(OtherQQ);
            if (OtherUser.Is_UserExist(jct)){
                bot.sendMsg(event, "对方还没有穿越到斗罗大陆！", false);
                return MESSAGE_IGNORE;
            }
            if (OtherUser.Is_UserAwake(jct)){
                bot.sendMsg(event, "对方还没有觉醒武魂！", false);
                return MESSAGE_IGNORE;
            }
            User player = new User(OtherQQ);
            String OtherUserData = player.Get_UserData(jct);
            QueryWrapper<com.mryunqi.qimenbot.entity.User> wrapper = new QueryWrapper<>();
            wrapper.eq("qq",OtherQQ);
            com.mryunqi.qimenbot.entity.User Player = userService.getOne(wrapper);
            String SkillData = Player.getSkillData();
            String OtherAttribute = player.Get_UserNowAttribute(OtherUserData);
            if (StringUtils.isBlank(SkillData)){
                String message = Other_No_HunHuan(UserData,OtherUserData,Attribute,OtherAttribute,OtherQQ);
                bot.sendMsg(event, message, false);
                return MESSAGE_IGNORE;
            }
            JSONObject jsonSkillData = JSONObject.parseObject(SkillData);
            JSONObject jsonObject = jsonSkillData.getJSONObject(Attribute);
            StringBuilder data = new StringBuilder();
            for (String key : jsonObject.keySet()){
                JSONObject jsonObject1 = jsonObject.getJSONObject(key);
                String SkillName = jsonObject1.getString("SkillName");
                String SkillAge = jsonObject1.getString("SkillAge");
                data.append(int2chineseNum(Integer.parseInt(key))).append("环：").append(SkillName)
                        .append("[").append(Get_HunShouAgeLevelName(Integer.parseInt(SkillAge))).append("][")
                        .append(SkillAge).append("年]\n");
            }
            String message = Other_HunHuanListTemp(UserData,OtherUserData,Attribute,OtherAttribute,OtherQQ, String.valueOf(data));
            bot.sendMsg(event, message, false);
            return MESSAGE_IGNORE;
        }
        return MESSAGE_IGNORE;
    }
}
