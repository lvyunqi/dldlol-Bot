package com.mryunqi.qimenbot.Plugin;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.AnyMessageEvent;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mryunqi.qimenbot.Util.ImageUtil.generateImage;

@Component
public class ChatGPT extends BotPlugin {
    @Override
    public int onAnyMessage(@NotNull Bot bot, @NotNull AnyMessageEvent event){
        String msg = event.getMessage();
        System.out.println(msg);
        // [CQ:at,qq=2733944636]
        String cmdMatcher = "^input:(.*)$";
        System.out.println(msg.matches(cmdMatcher));
        if (msg.matches(cmdMatcher)){
            String planText = msg.replaceAll("^input:","").trim();
            JSONObject json = postAPI(planText);
            System.out.println(json);
            String message = json.getString("data");
            bot.sendMsg(event, message,false);
        }
        return MESSAGE_IGNORE;
    }

    public JSONObject postAPI(String msg) {
        //定义发送数据
        JSONObject param = new JSONObject();
        param.put("msg", msg);
        //定义接收数据
        JSONObject result = new JSONObject();
        String url = "http://127.0.0.1:7777/chat";
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient client = HttpClients.createDefault();
        //请求参数转JOSN字符串
        StringEntity entity = new StringEntity(param.toString(), "UTF-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        try {
            HttpResponse response = client.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                result = JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            result.put("error", "连接错误！");
        }
        return result;
    }
}
