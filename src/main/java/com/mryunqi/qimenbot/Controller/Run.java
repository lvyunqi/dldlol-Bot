package com.mryunqi.qimenbot.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static com.mryunqi.qimenbot.Util.FileUtils.is_Directory;

@Configuration
@Component
public class Run {
    private static final Logger log = LoggerFactory.getLogger(Run.class);
    @PostConstruct
    public void run() {
        List<String> Directory = new ArrayList<>();
        Directory.add("/斗罗大陆图片/地图");
        Directory.add("/斗罗大陆图片/魂兽");
        Directory.add("/斗罗大陆图片/魂环");
        Directory.add("/斗罗大陆图片/魂兽");
        Directory.add("/斗罗大陆图片/武魂");
        Directory.add("/斗罗大陆图片/其他");
        log.info("开始初始化!");
        for (String data : Directory){
            log.info(is_Directory(data));
        }
    }
}
