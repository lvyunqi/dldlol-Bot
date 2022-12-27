package com.mryunqi.qimenbot.Plugin;

import com.alibaba.fastjson.JSONObject;
import com.mikuac.shiro.common.utils.MsgUtils;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.AnyMessageEvent;
import com.mryunqi.qimenbot.Controller.Command;
import com.mryunqi.qimenbot.Controller.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class Cmd extends BotPlugin {
    private final JdbcTemplate jct;

    public Cmd(JdbcTemplate jct) {
        this.jct = jct;
    }

    @Override
    public int onAnyMessage(@NotNull Bot bot, @NotNull AnyMessageEvent event){
        String msg = event.getMessage();
        String userId = String.valueOf(event.getUserId());
        String CmdMatcher = "^查看授权$";
        String CmdAddAuthMatcher = "^新增授权(.*)$";
        String CmdDelAuthMatcher = "^删除授权(.*)$";
        Command command = new Command();
        String AuthGroupList = command.Get_CommandAuthGroupList(jct);
        String CmdAdmin = command.Get_CommandAdmin(jct);
        if (msg.matches(CmdMatcher)){
            JSONObject Admin = JSONObject.parseObject(CmdAdmin);
            if (!Admin.containsKey(userId)){
                bot.sendMsg(event, "您不是超级管理员，无权操作！", false);
                return MESSAGE_IGNORE;
            }
            JSONObject AuthGroup = JSONObject.parseObject(AuthGroupList);
            StringBuilder BuildMessage = new StringBuilder();
            for (String key : AuthGroup.keySet()) {
                BuildMessage.append("[").append((key.equals("0") ? "私聊" : key)).append("]").append("\n");
            }
            String message = MsgUtils.builder()
                    .at(event.getUserId())
                    .text("已授权的授权组：\n"+BuildMessage+"<可用命令>\n新增授权\n删除授权")
                    .build();
            bot.sendMsg(event, message, false);
            return MESSAGE_IGNORE;
        }
        if (msg.matches(CmdAddAuthMatcher)){
            JSONObject Admin = JSONObject.parseObject(CmdAdmin);
            if (!Admin.containsKey(userId)){
                bot.sendMsg(event, "您不是超级管理员，无权操作！", false);
                return MESSAGE_IGNORE;
            }
            if (msg.substring(4).equals("")){
                bot.sendMsg(event, "授权组不能为空！", false);
                return MESSAGE_IGNORE;
            }
            String AuthGroup = command.Get_CommandAuthGroupList(jct);
            JSONObject AuthGroupJson = JSONObject.parseObject(AuthGroup);
            String AuthGroupName = msg.replaceAll("^新增授权","");
            if (AuthGroupJson.containsKey(AuthGroupName)){
                bot.sendMsg(event, "授权组已存在！", false);
                return MESSAGE_IGNORE;
            }
            AuthGroupJson.put(AuthGroupName,"null");
            command.Set_CommandAuthGroup(jct,AuthGroupJson.toJSONString());
            bot.sendMsg(event, "授权组添加成功！", false);
            return MESSAGE_IGNORE;
        }
        if (msg.matches(CmdDelAuthMatcher)){
            JSONObject Admin = JSONObject.parseObject(CmdAdmin);
            if (!Admin.containsKey(userId)){
                bot.sendMsg(event, "您不是超级管理员，无权操作！", false);
                return MESSAGE_IGNORE;
            }
            if (msg.substring(4).equals("")){
                bot.sendMsg(event, "授权组不能为空！", false);
                return MESSAGE_IGNORE;
            }
            String AuthGroup = command.Get_CommandAuthGroupList(jct);
            JSONObject AuthGroupJson = JSONObject.parseObject(AuthGroup);
            String AuthGroupName = msg.replaceAll("^删除授权","");
            if (!AuthGroupJson.containsKey(AuthGroupName)){
                bot.sendMsg(event, "授权组不存在！", false);
                return MESSAGE_IGNORE;
            }
            AuthGroupJson.remove(AuthGroupName);
            command.Set_CommandAuthGroup(jct,AuthGroupJson.toJSONString());
            bot.sendMsg(event, "授权组删除成功！", false);
            return MESSAGE_IGNORE;
        }
        return MESSAGE_IGNORE;
    }
}
