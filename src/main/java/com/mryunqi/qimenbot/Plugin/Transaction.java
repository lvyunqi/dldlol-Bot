package com.mryunqi.qimenbot.Plugin;

import com.alibaba.fastjson2.JSONObject;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.WholeMessageEvent;
import com.mryunqi.qimenbot.Controller.Command;
import com.mryunqi.qimenbot.Controller.PublicAuth;
import com.mryunqi.qimenbot.Controller.User;
import com.mryunqi.qimenbot.Template.TransactionTemplate;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class Transaction extends BotPlugin {
    private final JdbcTemplate jct;

    public Transaction(JdbcTemplate jct) {
        this.jct = jct;
    }
    @Override
    public int onWholeMessage(@NotNull Bot bot, @NotNull WholeMessageEvent event) {
        String OtherQQ;
        String countCurrency;
        String msg = event.getMessage();
        String userId = String.valueOf(event.getUserId());
        String groupId = String.valueOf(event.getGroupId());
        Command command = new Command();
        User user = new User(userId);
        String AuthGroupList = command.Get_CommandAuthGroupList(jct);
        String CmdMatcher = "^转账(.*)$";
        String CmdTransactionMatcher = "^发送物品(.*)$";
        if (msg.matches(CmdMatcher)){
            if (PublicAuth.AuthHeader(bot, event, jct, user, AuthGroupList, groupId) == MESSAGE_IGNORE) {
                return MESSAGE_IGNORE;
            }
            String PlanText = msg.replaceAll("^转账","");
            if (PlanText.equals("") | !PlanText.contains("-")){
                bot.sendMsg(event, "转账失败！格式：转账 [币种][@对方 OR 对方QQ]-[金额]\n", false);
                return MESSAGE_IGNORE;
            }
            if (PlanText.charAt(PlanText.length() - 1) == '-') {
                bot.sendMsg(event, "转账失败！格式：转账 [币种][@对方 OR 对方QQ]-[金额]，你没有填写金额！", false);
                return MESSAGE_IGNORE;
            }
            int index = PlanText.indexOf("-");
            if (index == 0) {
                bot.sendMsg(event, "转账失败！格式：转账 [币种][@对方 OR 对方QQ]-[金额]，你没有填写对方QQ！", false);
                return MESSAGE_IGNORE;
            }
            String ToUser = PlanText.substring(0, index).trim();
            // 判断ToUser中是否存在[CQ:at,qq=]
            if (ToUser.contains("[CQ:at,qq=")) {
                int index2 = ToUser.indexOf("[CQ:at,qq=");
                OtherQQ = ToUser.replaceAll("\\D", "");
                countCurrency = ToUser.substring(0, index2).trim();
            } else {
                String regex = "\\d+";
                countCurrency = ToUser.replaceAll(regex, "");
                OtherQQ = ToUser.replaceAll(countCurrency, "");
            }
            String count = PlanText.substring(index + 1);
            String Currency = command.Get_CommandCurrencyList(jct);
            User OtherUser = new User(OtherQQ);
            if (OtherUser.Is_UserExist(jct)) {
                bot.sendMsg(event, "转账失败！对方还不是斗罗大陆的居民！", false);
                return MESSAGE_IGNORE;
            }
            if (OtherUser.Is_UserAwake(jct)) {
                bot.sendMsg(event, "转账失败！对方还没有武魂觉醒！", false);
                return MESSAGE_IGNORE;
            }
            JSONObject CurrencyList = JSONObject.parseObject(Currency);
            if (!CurrencyList.containsKey(countCurrency)) {
                bot.sendMsg(event, "转账失败！币种不存在！", false);
                return MESSAGE_IGNORE;
            }
            int UserMoney = user.Get_UserMoney(jct,countCurrency);
            if (UserMoney < Integer.parseInt(count)) {
                bot.sendMsg(event, "转账失败！你的[" + countCurrency + "]不足！", false);
                return MESSAGE_IGNORE;
            }
            int NewUserMoney = UserMoney - Integer.parseInt(count);
            int NewOtherUserMoney = OtherUser.Get_UserMoney(jct,countCurrency) + Integer.parseInt(count);
            user.Set_UserWalletData(jct,NewUserMoney,countCurrency);
            OtherUser.Set_UserWalletData(jct,NewOtherUserMoney,countCurrency);
            String UserData = user.Get_UserData(jct);
            String Attribute = user.Get_UserNowAttribute(UserData);
            TransactionTemplate transactionTemplate = new TransactionTemplate();
            String message = transactionTemplate.Get_TransactionMoneyTemplate(UserData,Attribute, Integer.parseInt(count),countCurrency);
            bot.sendMsg(event, message, false);
            return MESSAGE_IGNORE;
        }
        if (msg.matches(CmdTransactionMatcher)){
            if (PublicAuth.AuthHeader(bot, event, jct, user, AuthGroupList, groupId) == MESSAGE_IGNORE) {
                return MESSAGE_IGNORE;
            }
            String PlanText = msg.replaceAll("^发送物品","");
            if (PlanText.equals("") | !PlanText.contains("-")){
                bot.sendMsg(event, "发送物品失败！格式：发送物品 [@对方 OR 对方QQ]-[物品名称]-[数量]", false);
                return MESSAGE_IGNORE;
            }
            if (PlanText.charAt(PlanText.length() - 1) == '-') {
                bot.sendMsg(event, "发送物品失败！格式：发送物品 [@对方 OR 对方QQ]-[物品名称]-[数量]", false);
                return MESSAGE_IGNORE;
            }
            // PlanText = 对方QQ-物品名称-数量
            int indexOne = PlanText.indexOf("-");
            if (indexOne == 0) {
                bot.sendMsg(event, "发送物品失败！格式：发送物品 [@对方 OR 对方QQ]-[物品名称]-[数量]", false);
                return MESSAGE_IGNORE;
            }
            String InitQQ = PlanText.substring(0, indexOne).trim();
            OtherQQ = InitQQ.replaceAll("\\D", "");
            User OtherUser = new User(OtherQQ);
            if (OtherUser.Is_UserExist(jct)) {
                bot.sendMsg(event, "发送物品失败！对方还不是斗罗大陆的居民！", false);
                return MESSAGE_IGNORE;
            }
            if (OtherUser.Is_UserAwake(jct)) {
                bot.sendMsg(event, "发送物品失败！对方还没有武魂觉醒！", false);
                return MESSAGE_IGNORE;
            }
            String InitItem = PlanText.substring(indexOne + 1).trim();
            if (InitItem.equals("") | !InitItem.contains("-")){
                bot.sendMsg(event, "发送物品失败！格式：发送物品 [@对方 OR 对方QQ]-[物品名称]-[数量]", false);
                return MESSAGE_IGNORE;
            }
            int indexTwo = InitItem.indexOf("-");
            if (indexTwo == 0) {
                bot.sendMsg(event, "发送物品失败！格式：发送物品 [@对方 OR 对方QQ]-[物品名称]-[数量]", false);
                return MESSAGE_IGNORE;
            }
            String ItemName = InitItem.substring(0, indexTwo).trim();
            String UserBackPack = user.Get_UserBagData(jct);
            if (!UserBackPack.contains(ItemName)) {
                bot.sendMsg(event, "发送物品失败！你没有这个物品！", false);
                return MESSAGE_IGNORE;
            }
            String ItemType = user.Get_UserItemType(ItemName,UserBackPack);
            String ItemCount = InitItem.substring(indexTwo + 1).trim();
            int UserItemCount = user.Get_UserItemNum(jct,ItemName,ItemType);
            if (UserItemCount < Integer.parseInt(ItemCount)) {
                bot.sendMsg(event, "发送物品失败！你的物品数量不足！", false);
                return MESSAGE_IGNORE;
            }
            user.Reduce_UserItemNum(jct,ItemName,ItemType,Integer.parseInt(ItemCount));
            OtherUser.Add_UserItemNum(jct,ItemName,ItemType,Integer.parseInt(ItemCount));
            TransactionTemplate transactionTemplate = new TransactionTemplate();
            String UserData = user.Get_UserData(jct);
            String Attribute = user.Get_UserNowAttribute(UserData);
            String message = transactionTemplate.Get_TransactionItemTemplate(UserData,Attribute,Integer.parseInt(ItemCount),ItemName);
            bot.sendMsg(event, message, false);
            return MESSAGE_IGNORE;
        }
        return MESSAGE_IGNORE;
    }
}
