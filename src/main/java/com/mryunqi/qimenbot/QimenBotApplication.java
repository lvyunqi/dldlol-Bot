package com.mryunqi.qimenbot;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@MapperScan("com.mryunqi.qimenbot.dao")
@Slf4j
public class QimenBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(QimenBotApplication.class, args);
        /*SpringApplicationBuilder builder = new SpringApplicationBuilder(QimenBotApplication.class);
        builder.headless(false).run(args);*/
    }


}
