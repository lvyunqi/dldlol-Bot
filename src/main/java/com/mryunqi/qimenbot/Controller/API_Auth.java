package com.mryunqi.qimenbot.Controller;
import com.mryunqi.qimenbot.Util.YmlUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;

@Configuration
@Component
public class API_Auth {
    public boolean ApiKeyAuth(String apiKey,String LocalApiKey){
        return (apiKey).equals(LocalApiKey);
    }
}
