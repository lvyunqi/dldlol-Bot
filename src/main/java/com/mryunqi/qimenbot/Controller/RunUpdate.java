package com.mryunqi.qimenbot.Controller;

import com.alibaba.fastjson2.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootVersion;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.SpringVersion;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Configuration
@Component
public class RunUpdate {
    @Value(value = "${project.version}")
    private String version;
    private static final Logger log = LoggerFactory.getLogger(RunUpdate.class);

/*    public RunUpdate(Environment environment){
    }*/

    @PostConstruct
    public void checkUpdate(){
        RestTemplate restTemplate = new RestTemplate();
        String updateUrl = "https://gitee.com/lvyunqi/dldlol-Bot/raw/Java-Beta/latest.json";
        ResponseEntity<String> LastData = restTemplate.getForEntity(updateUrl, String.class);
        String resultString = String.valueOf(LastData.getBody());
        JSONObject resultJson = JSONObject.parseObject(resultString);
        String latestVersion = resultJson.getString("version");
        log.info("检查更新中.....");
        if (latestVersion.equals(version)){
            log.info("当前主体框架已更新到最新版！");
        }else {
            log.info("检查到最新版本："+latestVersion);
        }
    }
}
