package com.mryunqi.qimenbot.Plugin;

import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.AnyMessageEvent;
import com.mikuac.shiro.dto.event.message.GuildMessageEvent;
import com.mryunqi.qimenbot.Controller.*;
import com.mryunqi.qimenbot.Template.BackPackTemplate;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BackPack extends BotPlugin {
    private final JdbcTemplate jct;

    public BackPack(JdbcTemplate jct) {
        this.jct = jct;
    }

    @Override
    public int onAnyMessage(@NotNull Bot bot, @NotNull AnyMessageEvent event){
        String msg = event.getMessage();
        String userId = String.valueOf(event.getUserId());
        String groupId = String.valueOf(event.getGroupId());
        Command command = new Command();
        Function func = new Function();
        User user = new User(userId);
        String AuthGroupList = command.Get_CommandAuthGroupList(jct);
        String CmdMatcher = "^背包(.*)$";
        String AliasCmd = user.Get_UserAliasCmd(jct,msg);
        if (msg.matches(CmdMatcher) | AliasCmd.matches(CmdMatcher)){
            if (PublicAuth.AuthHeader(bot, event, jct, user, AuthGroupList, groupId) == MESSAGE_IGNORE) {
                return MESSAGE_IGNORE;
            }
            if (AliasCmd.matches(CmdMatcher)){
                msg = AliasCmd;
            }
            String PlanText = msg.replaceAll("^背包","");
            if (PlanText.equals("")){
                PlanText = "1";
            }
            // 判断PlanText是否为数字
            if (func.Is_Number(PlanText)){
                Money money = new Money();
                String BackPack = user.Get_UserBagData(jct);
                String Money = user.Get_UserWalletData(jct);
                String Currency = command.Get_CommandCurrencyList(jct);
                String MoneyData = money.Get_MoneyString(Money, Currency);
                String UserData = user.Get_UserData(jct);
                String Attribute = user.Get_UserNowAttribute(UserData);
                BP bp = new BP();
                BackPackTemplate backPackTemplate = new BackPackTemplate();
                List<String> BackPackList = bp.Get_BackPackList(jct,BackPack);
                String message = backPackTemplate.Get_backpackTemplate(BackPackList,MoneyData,PlanText,UserData,Attribute);
                bot.sendMsg(event, message, false);
                return MESSAGE_IGNORE;
            }
        }
        return MESSAGE_IGNORE;
    }

    @Override
    public int onGuildMessage(@NotNull Bot bot, @NotNull GuildMessageEvent event) {
        String msg = event.getMessage();
        String userId = String.valueOf(event.getUserId());
        String guildId = event.getGuildId();
        String channelId = event.getChannelId();
        Command command = new Command();
        Function func = new Function();
        User user = new User(userId);
        String AuthGroupList = command.Get_CommandAuthGroupList(jct);
        String CmdMatcher = "^背包(.*)$";
        String AliasCmd = user.Get_UserAliasCmd(jct,msg);
        if (msg.matches(CmdMatcher) | AliasCmd.matches(CmdMatcher)){
            if (PublicAuth.authGuildHeader(bot, event, jct, user, AuthGroupList, guildId,guildId,channelId) == MESSAGE_IGNORE) {
                return MESSAGE_IGNORE;
            }
            if (AliasCmd.matches(CmdMatcher)){
                msg = AliasCmd;
            }
            String PlanText = msg.replaceAll("^背包","");
            if (PlanText.equals("")){
                PlanText = "1";
            }
            // 判断PlanText是否为数字
            if (func.Is_Number(PlanText)){
                Money money = new Money();
                String BackPack = user.Get_UserBagData(jct);
                String Money = user.Get_UserWalletData(jct);
                String Currency = command.Get_CommandCurrencyList(jct);
                String MoneyData = money.Get_MoneyString(Money, Currency);
                String UserData = user.Get_UserData(jct);
                String Attribute = user.Get_UserNowAttribute(UserData);
                BP bp = new BP();
                BackPackTemplate backPackTemplate = new BackPackTemplate();
                List<String> BackPackList = bp.Get_BackPackList(jct,BackPack);
                String message = backPackTemplate.Get_backpackTemplate(BackPackList,MoneyData,PlanText,UserData,Attribute);
                bot.sendGuildMsg(guildId,channelId,message);
                return MESSAGE_IGNORE;
            }
        }
        return MESSAGE_IGNORE;
    }
}
