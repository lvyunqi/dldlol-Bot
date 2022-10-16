package com.mryunqi.qimenbot.Controller;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "bot")
@Data
public class Constant {

    /**
     *管理员
     */
    private String superusers;
    /**
     *apikey
     */
    private String apikey;
    /**
     *拓展插件
     */
    private List<String> plugin;

}