package com.mryunqi.qimenbot.Plugin;

import com.alibaba.fastjson2.JSONObject;
import com.mikuac.shiro.common.utils.MsgUtils;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.AnyMessageEvent;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class KanTu extends BotPlugin {
    /**
     * 表单格式传输
     */
    public static final String FORM_DATA_TYPE = "application/x-www-form-urlencoded";

    /**
     * json 默认的编码类型
     */
    public static final String JSON_DEFAULT_CHARSET = "application/json";

    /**
     * 默认的编码格式
     */
    public static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 请求配置对象
     */
    private static RequestConfig requestConfig;

    /**
     *  get 请求，将参数包含在 url 路径中
     *  url : 路径
     *  return: json 对象
     */
    public static JSONObject APIGet(String url){
        JSONObject jsonObject = null;
        try(CloseableHttpClient client = HttpClients.createDefault();){
            HttpGet request  = new HttpGet(url);
            request.setConfig(requestConfig);
            try(CloseableHttpResponse response = client.execute(request);){
                int statusCode = response.getStatusLine().getStatusCode();
                if(statusCode == HttpStatus.SC_OK){
                    HttpEntity entity = response.getEntity();
                    String responseContent = EntityUtils.toString(entity);
                    jsonObject = JSONObject.parseObject(responseContent);
                }else{
                    System.out.println("API请求失败，状态码：" + statusCode);
                }
            }
        }catch (IOException e){
            System.out.println("API请求异常：" + e.getMessage());
            e.printStackTrace();
        }
        return  jsonObject;
    }

    @Override
    public int onAnyMessage(@NotNull Bot bot, @NotNull AnyMessageEvent event){
        String msg = event.getMessage();
        if ("动漫壁纸".equals(msg)){
            bot.sendMsg(event,"正在找图。。。。",false);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            JSONObject APIData = APIGet("https://api.apiopen.top/api/getImages?type=comic&page=0&size=1");
            String message = MsgUtils.builder()
                    .at(event.getUserId())
                    .text(APIData.toJSONString())
                    .build();
            bot.sendMsg(event,message,false);
        }
        return MESSAGE_IGNORE;
    }
}
