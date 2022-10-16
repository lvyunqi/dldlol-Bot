package com.mryunqi.qimenbot.Plugin;

import com.alibaba.fastjson2.JSONObject;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.WholeMessageEvent;
import com.mryunqi.qimenbot.Controller.Command;
import com.mryunqi.qimenbot.Controller.Function;
import com.mryunqi.qimenbot.Controller.User;
import com.mryunqi.qimenbot.Controller.Wuhun;
import com.mryunqi.qimenbot.Template.AwakeTemplate;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Awake extends BotPlugin {
    private final JdbcTemplate jct;

    public Awake(JdbcTemplate jct) {
        this.jct = jct;
    }

    @Override
    public int onWholeMessage(@NotNull Bot bot, @NotNull WholeMessageEvent event){
        String msg = event.getMessage();
        String userId = String.valueOf(event.getUserId());
        String groupId = String.valueOf(event.getGroupId());
        String CmdMatcher = "^武魂觉醒$";
        User user = new User(userId);
        Command command = new Command();
        Function func = new Function();
        Wuhun wuhun = new Wuhun();
        Random r = new Random();
        String AuthGroupList = command.Get_CommandAuthGroupList(jct);
        int TwinsProbability = command.Get_TwinsProbability(jct);
        if (msg.matches(CmdMatcher)){
            if (!AuthGroupList.contains(groupId)){
                bot.sendMsg(event, "该群没有授权斗罗大陆！", false);
                return MESSAGE_IGNORE;
            }
            if (user.Is_UserExist(jct)){
                bot.sendMsg(event, "您还没有穿越到斗罗大陆！\n<可用命令>\n开始穿越", false);
                return MESSAGE_IGNORE;
            }
            if (!user.Is_UserAwake(jct)){
                bot.sendMsg(event, "您已觉醒过武魂，请不要重复觉醒！\n<可用命令>\n状态", false);
                return MESSAGE_IGNORE;
            }
            if (!func.Get_RandomRun(TwinsProbability)){
                String name = user.Get_UserName(jct);
                String sex = user.Get_UserSex(jct);
                String WuhunName = wuhun.Get_SkillRandomName(jct);
                int lv = r.nextInt(10)+1;
                int con = Integer.parseInt(command.Get_CommandDefaultCon(jct));
                String initMoney = command.Get_CommandDefaultMoney(jct);
                String initBackpack = command.Get_CommandDefaultBackpack(jct);
                AwakeTemplate awakeTemplate = new AwakeTemplate();
                String WuhunData = wuhun.Get_SkillData(jct,WuhunName);
                String UserData = awakeTemplate.get_awakeSingleTemplate(name,sex,WuhunName,WuhunData,lv,con);
                JSONObject HunHuanList = new JSONObject();
                JSONObject FirstWuHun = new JSONObject();
                HunHuanList.put("第一武魂",FirstWuHun);
                HunHuanList.put("气血之力",FirstWuHun);
                user.Set_UserData(jct,UserData,"state_info");
                user.Set_UserData(jct,initMoney,"money");
                user.Set_UserData(jct,initBackpack,"backpack");
                user.Set_UserData(jct, String.valueOf(HunHuanList),"skill_data");
                String message = awakeTemplate.Get_AwakeSingleTemp(UserData);
                bot.sendMsg(event, message, false);
                return MESSAGE_IGNORE;
            }
            if (func.Get_RandomRun(TwinsProbability)){
                String name = user.Get_UserName(jct);
                String sex = user.Get_UserSex(jct);
                String WuhunName = wuhun.Get_SkillRandomName(jct);
                String WuhunNameSecond = wuhun.Get_SkillRandomName(jct);
                while (WuhunName.equals(WuhunNameSecond)){
                    WuhunNameSecond = wuhun.Get_SkillRandomName(jct);
                }
                int lv = r.nextInt(10)+1;
                int con = Integer.parseInt(command.Get_CommandDefaultCon(jct));
                String initMoney = command.Get_CommandDefaultMoney(jct);
                String initBackpack = command.Get_CommandDefaultBackpack(jct);
                AwakeTemplate awakeTemplate = new AwakeTemplate();
                String WuhunFirstData = wuhun.Get_SkillData(jct,WuhunName);
                String WuhunSecondData = wuhun.Get_SkillData(jct,WuhunNameSecond);
                int twinsp = command.Get_TwinsProbability(jct);
                String UserData = awakeTemplate.get_awakeTwinsTemplate(name,sex,WuhunName,WuhunNameSecond,WuhunFirstData,WuhunSecondData,lv,con, twinsp);
                JSONObject HunHuanList = new JSONObject();
                JSONObject FirstWuHun = new JSONObject();
                HunHuanList.put("第一武魂",FirstWuHun);
                HunHuanList.put("第二武魂",FirstWuHun);
                user.Set_UserData(jct,UserData,"state_info");
                user.Set_UserData(jct,initMoney,"money");
                user.Set_UserData(jct,initBackpack,"backpack");
                user.Set_UserData(jct, String.valueOf(HunHuanList),"skill_data");
                String message = awakeTemplate.Get_AwakeTwinsTemp(UserData);
                bot.sendMsg(event, message, false);
                return MESSAGE_IGNORE;
            }
        }
        return MESSAGE_IGNORE;
    }
}
