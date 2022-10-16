package com.mryunqi.qimenbot.Controller;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static com.mryunqi.qimenbot.Util.IniUtil.getProfileString;
import static com.mryunqi.qimenbot.Util.IniUtil.setProfileString;

@Data
@Component
//@Configuration
//@EnableConfigurationProperties({BotExpandBean.class})
//@ConfigurationProperties(prefix = "bot") // 配置文件的前缀
public class BotExpandBean {
    /*
     * 需要拦截的路径
     */
    //private List<String> plugin;
    private final Constant constant;

    private static Logger log = LoggerFactory.getLogger(BotExpandBean.class);
    private final Environment environment;
    public BotExpandBean(Environment environment, Constant constant){
        this.environment = environment;
        this.constant = constant;
    }

    @PostConstruct
    public void main() throws IOException {
        String rootPath = System.getProperty("user.dir");
        String PluginsPath = rootPath+"\\Plugins\\";
        String ExpandConfigPath = rootPath+"\\data\\plugins.ini";
        String PyPath = rootPath+"\\data\\Python\\python.exe";
        String ExpandConfig = "";
        for (String data : constant.getPlugin()){
            if (data == null){
                break;
            }
            String ExpandPath = PluginsPath + data + "\\config.py";
            String[] arg = {PyPath,ExpandPath};
            try {
                Process pr = Runtime.getRuntime().exec(arg);
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        pr.getInputStream()));
                ExpandConfig = in.readLine();
                in.close();
                pr.waitFor();
            } catch (Exception e) {
                e.printStackTrace();
            }
            JSONObject JsonMap = JSONObject.parseObject(ExpandConfig);
            String Name = JsonMap.getJSONObject("Basics").getString("Name");
            String Version = JsonMap.getJSONObject("Basics").getString("Version");
            String Author = JsonMap.getJSONObject("Basics").getString("Author");
            log.info("加载拓展["+Name+"]("+Version+") @"+Author);
            String GroupMessageEventData = JsonMap.getJSONObject("ExpandApi").getString("GroupMessageEvent");
            String PrivateMessageEventData = JsonMap.getJSONObject("ExpandApi").getString("PrivateMessageEvent");
            String WholeMessageEventData = JsonMap.getJSONObject("ExpandApi").getString("WholeMessageEvent");
            /*
            String[] GroupMessageEventDataList = GroupMessageEventData.split(",");
            String[] PrivateMessageEventDataList = PrivateMessageEventData.split(",");
            String[] WholeMessageEventDataList = WholeMessageEventData.split(",");
             */
            String IniConfigGroupMessageEventData = getProfileString(ExpandConfigPath,"Config","GroupMessageEvent");
            String IniConfigPrivateMessageEventData = getProfileString(ExpandConfigPath,"Config","PrivateMessageEvent");
            String IniConfigWholeMessageEventData = getProfileString(ExpandConfigPath,"Config","WholeMessageEvent");
            JSONObject JsonGroupMessageEventData = JSONObject.parseObject(IniConfigGroupMessageEventData);
            JsonGroupMessageEventData.put(data,GroupMessageEventData);
            JSONObject JsonPrivateMessageEventData = JSONObject.parseObject(IniConfigPrivateMessageEventData);
            JsonPrivateMessageEventData.put(data,PrivateMessageEventData);
            JSONObject JsonWholeMessageEventData = JSONObject.parseObject(IniConfigWholeMessageEventData);
            JsonWholeMessageEventData.put(data, WholeMessageEventData);
            setProfileString(ExpandConfigPath,"Config","GroupMessageEvent", String.valueOf(JsonGroupMessageEventData));
            setProfileString(ExpandConfigPath,"Config","PrivateMessageEvent",String.valueOf(JsonPrivateMessageEventData));
            setProfileString(ExpandConfigPath,"Config","WholeMessageEvent",String.valueOf(JsonWholeMessageEventData));
            log.info("加载拓展["+Name+"]成功！");
        }
    }
}
