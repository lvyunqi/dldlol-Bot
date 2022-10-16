package com.mryunqi.qimenbot.Plugin;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.Event;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;
import com.mikuac.shiro.dto.event.message.PrivateMessageEvent;
import com.mikuac.shiro.dto.event.message.WholeMessageEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import static com.mryunqi.qimenbot.Util.IniUtil.getProfileString;

@Component
public class PyExpand extends BotPlugin {

    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull GroupMessageEvent event){
        String msg = event.getMessage();
        String userId = String.valueOf(event.getUserId());
        String groupId = String.valueOf(event.getGroupId());
        String rootPath = System.getProperty("user.dir");
        String PyPath = rootPath+"\\data\\Python\\python.exe";
        String ExpandConfigPath = rootPath+"\\data\\plugins.ini";
        String IniConfigGroupMessageEventData = "";
        String Expand = "";
        try {
            IniConfigGroupMessageEventData = getProfileString(ExpandConfigPath,"Config","GroupMessageEvent");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JSONObject JsonGroupMessageEventData = JSONObject.parseObject(IniConfigGroupMessageEventData);
        if (JsonGroupMessageEventData == null){
            return MESSAGE_IGNORE;
        }
        for (String key:JsonGroupMessageEventData.keySet()){
            int lastIndex = key.lastIndexOf(".");
            String type = key.substring(lastIndex+1);
            if (("py").equals(type)){
                Expand = JsonGroupMessageEventData.getString(key);
                if (Expand == null){
                    return MESSAGE_IGNORE;
                }
                String[] GroupMessageEventDataList = Expand.split(",");
                for(String group: GroupMessageEventDataList){
                    String FilePath = rootPath+"\\Plugins\\"+key+"\\"+group+".py";
                    String[] arg = {PyPath,FilePath, msg,userId,groupId};
                    try {
                        Process pr = Runtime.getRuntime().exec(arg);
                        BufferedReader in = new BufferedReader(new InputStreamReader(
                                pr.getInputStream()));
                        String result = in.readLine();
                        if (("pass").equals(result)){
                            return MESSAGE_IGNORE;
                        }
                        bot.sendGroupMsg(event.getGroupId(), result, false);
                        in.close();
                        pr.waitFor();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return MESSAGE_IGNORE;
    }

    @Override
    public int onPrivateMessage(@NotNull Bot bot, @NotNull PrivateMessageEvent event){
        String msg = event.getMessage();
        String userId = String.valueOf(event.getUserId());
        String rootPath = System.getProperty("user.dir");
        String PyPath = rootPath+"\\data\\Python\\python.exe";
        String ExpandConfigPath = rootPath+"\\data\\plugins.ini";
        String IniConfigPrivateMessageData = "";
        String Expand = "";
        try {
            IniConfigPrivateMessageData = getProfileString(ExpandConfigPath,"Config","PrivateMessageEvent");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JSONObject JsonPrivateMessageEventData = JSONObject.parseObject(IniConfigPrivateMessageData);
        if (JsonPrivateMessageEventData == null){
            return MESSAGE_IGNORE;
        }
        for (String key:JsonPrivateMessageEventData.keySet()){
            int lastIndex = key.lastIndexOf(".");
            String type = key.substring(lastIndex+1);
            if (("py").equals(type)){
                Expand = JsonPrivateMessageEventData.getString(key);
                if (Expand == null){
                    return MESSAGE_IGNORE;
                }
                String[] PrivateMessageEventDataList = Expand.split(",");
                for(String group: PrivateMessageEventDataList){
                    String FilePath = rootPath+"\\Plugins\\"+key+"\\"+group+".py";
                    String[] arg = {PyPath,FilePath, msg,userId};
                    try {
                        Process pr = Runtime.getRuntime().exec(arg);
                        BufferedReader in = new BufferedReader(new InputStreamReader(
                                pr.getInputStream()));
                        String result = in.readLine();
                        if (("pass").equals(result)){
                            return MESSAGE_IGNORE;
                        }
                        bot.sendPrivateMsg(event.getUserId(), result, false);
                        in.close();
                        pr.waitFor();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return MESSAGE_IGNORE;
    }

    @Override
    public int onWholeMessage(@NotNull Bot bot, @NotNull WholeMessageEvent event){
        String msg = event.getMessage();
        String userId = String.valueOf(event.getUserId());
        String groupId = String.valueOf(event.getGroupId());
        String rootPath = System.getProperty("user.dir");
        String PyPath = rootPath+"\\data\\Python\\python.exe";
        String ExpandConfigPath = rootPath+"\\data\\plugins.ini";
        String IniConfigWholeMessageEventData = "";
        String Expand = "";
        try {
            IniConfigWholeMessageEventData = getProfileString(ExpandConfigPath,"Config","WholeMessageEvent");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JSONObject JsonWholeMessageEventData = JSONObject.parseObject(IniConfigWholeMessageEventData);
        if (JsonWholeMessageEventData == null){
            return MESSAGE_IGNORE;
        }
        for (String key:JsonWholeMessageEventData.keySet()){
            int lastIndex = key.lastIndexOf(".");
            String type = key.substring(lastIndex+1);
            if (("py").equals(type)){
                Expand = JsonWholeMessageEventData.getString(key);
                if (Expand == null){
                    return MESSAGE_IGNORE;
                }
                String[] GroupMessageEventDataList = Expand.split(",");
                for(String group: GroupMessageEventDataList){
                    String FilePath = rootPath+"\\Plugins\\"+key+"\\"+group+".py";
                    String[] arg = {PyPath,FilePath, msg,userId,groupId};
                    try {
                        Process pr = Runtime.getRuntime().exec(arg);
                        BufferedReader in = new BufferedReader(new InputStreamReader(
                                pr.getInputStream()));
                        String result = in.readLine();
                        if (("pass").equals(result)){
                            return MESSAGE_IGNORE;
                        }
                        bot.sendMsg(event,result,false);
                        in.close();
                        pr.waitFor();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return MESSAGE_IGNORE;
    }
}
