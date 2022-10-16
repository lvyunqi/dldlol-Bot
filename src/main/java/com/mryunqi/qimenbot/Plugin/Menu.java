package com.mryunqi.qimenbot.Plugin;

import com.mikuac.shiro.common.utils.MsgUtils;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.WholeMessageEvent;
import com.mryunqi.qimenbot.Util.CmdUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.*;
import java.net.URLEncoder;


@Component
public class Menu extends BotPlugin {
    @Override
    public int onWholeMessage(@NotNull Bot bot, @NotNull WholeMessageEvent event) {
        String msg = event.getMessage();
        if ("斗罗系统".equals(msg)){
            //构建消息
            String sendMsg = MsgUtils.builder()
                    .text("╭═══★斗罗大陆★═══╮\n" +
                            "角色菜单【☆】快捷菜单\n" +
                            "魂骨菜单【☆】神位菜单\n" +
                            "职业菜单【☆】斗铠菜单\n" +
                            "副本菜单【☆】排行菜单\n" +
                            "交易菜单【☆】队伍菜单\n" +
                            "地图菜单【☆】任务菜单\n" +
                            "战斗菜单【☆】剧情菜单\n" +
                            "关系菜单【☆】万兽菜单\n" +
                            "╰═══★══════★═══╯")
                    .build();
            bot.sendMsg(event,sendMsg,false);
            return MESSAGE_IGNORE;
        }
        if ("角色菜单".equals(msg)){
            String sendMsg = MsgUtils.builder()
                    .text("╭═══★角色菜单★═══╮\n" +
                            "状态\n" +
                            "╰═══★══════★═══╯")
                    .build();
            bot.sendMsg(event,sendMsg,false);
            return MESSAGE_IGNORE;
        }
        if ("快捷菜单".equals(msg)){
            String sendMsg = MsgUtils.builder()
                    .text("╭═══★快捷菜单★═══╮\n" +
                            "快捷键列表\n" +
                            "查看快捷键\n" +
                            "设置快捷键\n" +
                            "删除快捷键\n" +
                            "╰═══★══════★═══╯")
                    .build();
            bot.sendMsg(event,sendMsg,false);
            return MESSAGE_IGNORE;
        }
        if ("地图菜单".equals(msg)){
            String sendMsg = MsgUtils.builder()
                    .text("╭═══★地图菜单★═══╮\n" +
                            "位置\n" +
                            "╰═══★══════★═══╯")
                    .build();
            bot.sendMsg(event,sendMsg,false);
            return MESSAGE_IGNORE;
        }
        if ("测试程序".equals(msg)){
            String sendMsg = null;
            try {
                sendMsg = CmdUtil.Run("go-cqhttp.exe","1");
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            bot.sendMsg(event,sendMsg,false);
            return MESSAGE_IGNORE;
        }
        return MESSAGE_IGNORE;
    }

}
