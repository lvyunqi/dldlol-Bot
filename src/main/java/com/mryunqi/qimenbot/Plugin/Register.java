package com.mryunqi.qimenbot.Plugin;

import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.WholeMessageEvent;
import com.mryunqi.qimenbot.Controller.Command;
import com.mryunqi.qimenbot.Controller.User;
import com.mryunqi.qimenbot.Template.RegisterTemplate;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * 指令：开始穿越
 * @PluginName: 斗罗注册
 * @author mryunqi
 * @since 2022-6-23
 * @version 1.0
 */
@Component
public class Register extends BotPlugin {
    private final JdbcTemplate jct;

    public Register(JdbcTemplate jct) {
        this.jct = jct;
    }

    @Override
    public int onWholeMessage(@NotNull Bot bot, @NotNull WholeMessageEvent event){
        String msg = event.getMessage();
        String userId = String.valueOf(event.getUserId());
        String groupId = String.valueOf(event.getGroupId());
        User userDefault = new User(userId);
        Command command = new Command();
        String AuthGroupList = command.Get_CommandAuthGroupList(jct);
        String Matcher = "^开始穿越(.*)$";
        if (msg.matches(Matcher)) {

            if (!AuthGroupList.contains(groupId)){
                bot.sendMsg(event, "该群没有授权斗罗大陆RPG！", false);
                return MESSAGE_IGNORE;
            }
            if (!userDefault.Is_UserExist(jct)) {
                bot.sendMsg(event, "您已经穿越到斗罗大陆了，不可以再穿越了哦！\n<可用命令>\n状态", false);
                return MESSAGE_IGNORE;
            }
            //去除开始穿越
            String StrText = msg.substring(4);
            //去除StrText开头的空格
            String PlanText = StrText.trim();
            if (PlanText.length() == 0 | !PlanText.contains("-")) {
                bot.sendMsg(event, "创建角色失败！注册格式：开始穿越 夜夜-男，你的格式不对,注意名字不可超过6位！", false);
                return MESSAGE_IGNORE;
            }

            //判断-是否在最后一位
            if (PlanText.charAt(PlanText.length() - 1) == '-') {
                bot.sendMsg(event, "创建角色失败！注册格式：开始穿越 夜夜-男，你没有填写性别！", false);
                return MESSAGE_IGNORE;
            }

            int index = PlanText.indexOf("-");

            if (index == 0) {
                bot.sendMsg(event, "创建角色失败！注册格式：开始穿越 夜夜-男，你没有填写名字！", false);
                return MESSAGE_IGNORE;
            }

            String Name = PlanText.substring(0, index);
            String Sex =   PlanText.substring(index + 1);
            String SexMatcher = "^[男|女]$";

            if (Name.length() > 6){
                bot.sendMsg(event, "创建角色失败！名字超过6位！", false);
                return MESSAGE_IGNORE;
            }

            if (!Sex.matches(SexMatcher)){
                bot.sendMsg(event, "本游戏可不支持硅基生物的性别哦！请遵循本身意向性别按格式填写【男】或【女】！", false);
                return MESSAGE_IGNORE;
            }

            String AllName = userDefault.Get_UserAllName(jct);
            if (AllName.contains(Name)){
                bot.sendMsg(event, "创建角色失败！名字已存在！", false);
                return MESSAGE_IGNORE;
            }
            String DefaultMap = command.Get_CommandDefaultMap(jct);
            userDefault.Init_User(jct,Name,Sex,DefaultMap);
            RegisterTemplate registerTemplate = new RegisterTemplate();
            String message = registerTemplate.Register(Name,DefaultMap);
            bot.sendMsg(event, message, false);



            return MESSAGE_IGNORE;
        }
        return MESSAGE_IGNORE;
    }

}
