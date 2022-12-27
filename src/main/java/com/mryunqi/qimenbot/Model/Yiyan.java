package com.mryunqi.qimenbot.Model;

import com.alibaba.fastjson2.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Yiyan {
    private static RequestConfig requestConfig;
    /**
     *  get 请求，将参数包含在 url 路径中
     *  url : 路径
     *  return: json 对象
     */
    public static JSONObject YiyanGet(String url){
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
}
