package com.mryunqi.qimenbot.Plugin;

import com.mikuac.shiro.annotation.Shiro;
import com.mikuac.shiro.common.utils.MsgUtils;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;
import com.mikuac.shiro.dto.event.message.GuildMessageEvent;
import com.mikuac.shiro.dto.event.message.PrivateMessageEvent;
import com.mryunqi.qimenbot.Controller.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;


@Shiro
@Component
public class DemoPlugin extends BotPlugin {
    @Override
    public int onPrivateMessage(@NotNull Bot bot, @NotNull PrivateMessageEvent event) {
        String msg = event.getMessage();
        if ("hi".equals(msg)) {
            // 构建消息
            String message = "[CQ:image,url=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAyAAAAByCAIAAAAkvCJeAAAKS0lEQVR4nO3dWWyM3R/A8d9Ma9pi0FiiRdMXNfZdEWsEQUXU1gpBrFVLSDXqgkqInQtbaguGUhFbaEgRSyyNi8ZeW4YiTVP0LaWddKYz/4sn/8m802qbcUSnvp+rOufMmee5+2bMnEfndDoFAAAA6uj/9AUAAADUNgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYgQWAACAYr4UWPn5+efPn//x44drpLi42Gw2v3nzxus9U1NTk5KS0tPTVVwgAACAiG8F1r59+6Kjo3v16uUauXHjxsyZM00m05cvX7zbMzU1dfPmzffv3/fitU6n0732AAAAND4TWHa7ff/+/SKyYMEC1+CVK1dEJDIysnHjxt5t+/btWxGJiIgoP9WvXz9dpfR6fXh4+KVLl7x7awAAUFv5TGAdPXr048ePRqNxzpw5rsHLly+LSFRUlHd7OhyOd+/eiUi7du282+Hz589Xr1717rUAAKC20jmdzj99DVWz2+0mk8lisaxYsWLr1q3aYGZmZv/+/UXk9evXbdu29WJbi8XSpk0bESksLGzYsKHH7NevX202W4UvzM3NHThwYFFRUdu2bR89elS3bl0v3h0AANRW/n/6Aqplz549FoulQYMGSUlJrsEjR46ISGRkZHXqymq1BgYGegxmZ2eLSFhYWPm6EpEKB0XE4XBMnTq1qKgoKCjo9OnT1BUAAPDgA/9F+OnTp7Vr14pIYmKi67tWJSUlp06dEpEHDx5U/k0pjdFoXLJkifba0tJSq9VqtVqfPHki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,cache=0]";
            String sendMsg = MsgUtils.builder()
                    .face(66)
                    .text("Hello, this is demo.")
                    .build();
            // 发送私聊消息
            bot.sendPrivateMsg(event.getUserId(), message, false);
        }
        // 返回 MESSAGE_IGNORE 插件向下执行，返回 MESSAGE_BLOCK 则不执行下一个插件
        return MESSAGE_IGNORE;
    }

    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull GroupMessageEvent event) {
        String msg = event.getMessage();
        String UserId = String.valueOf(event.getUserId());
        User user = new User(UserId);
        long message = user.Get_UserAttackUp(11,1131);
        if ("测试".equals(msg)) {
            // 构建消息
            MsgUtils sendMsg = MsgUtils.builder()
                    .at(event.getUserId())
                    .face(66)
                    .text(String.valueOf(message));
            // 发送群消息
            bot.sendGroupMsg(event.getGroupId(), sendMsg.build(), false);
        }
        // 返回 MESSAGE_IGNORE 插件向下执行，返回 MESSAGE_BLOCK 则不执行下一个插件
        return MESSAGE_IGNORE;
    }

    @Override
    public int onGuildMessage(@NotNull Bot bot, @NotNull GuildMessageEvent event) {
        // do something···
        String msg = event.getMessage();
        String guildId = event.getGuildId();
        String channelId = event.getChannelId();
        System.out.println("[event]:"+event);
        System.out.println("[guildId]:"+guildId);
        System.out.println("[channelId]:"+channelId);
        if ("test".equals(msg)){
            bot.sendGuildMsg(guildId,channelId,"频道测试成功！");
        }
        return MESSAGE_IGNORE;
    }

}
