package com.mryunqi.qimenbot.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class PyExpandHelper {
    private static Logger log = LoggerFactory.getLogger(PyExpandHelper.class);
    private final Environment environment;

    public PyExpandHelper(Environment environment){
        this.environment = environment;
    }

    private static void GetYmlExpand(){

    }
}
