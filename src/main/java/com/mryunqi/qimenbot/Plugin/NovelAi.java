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
public class NovelAi extends BotPlugin {
    @Override
    public int onAnyMessage(@NotNull Bot bot, @NotNull AnyMessageEvent event){
        String msg = event.getMessage();
        String CmdMatcher = "^AI绘图(.*)$";
        if (msg.matches(CmdMatcher)){
            bot.sendMsg(event,"已加入绘制队列，请耐心等待...\nPrompt:",false);
            String PlanText = msg.replaceAll("^AI绘图","").trim();
            bot.sendMsg(event, PlanText,false);
            //bot.sendMsg(event, String.valueOf(PostAPI(PlanText)),false);"\n[CQ:image,file=" + url + ",cache=0]"
            JSONObject json = PostAPI(PlanText);
            JSONArray jsonArray = json.getJSONArray("data").getJSONArray(0);
            String rootPath = System.getProperty("user.dir").concat("/Upload/");
            String path = generateImage(jsonArray.getString(0),String.valueOf(event.getUserId()),rootPath);
            bot.sendMsg(event, "[CQ:image,file=file:///" + rootPath +path + ",cache=0]",false);
        }
        return MESSAGE_IGNORE;
    }

    public JSONObject PostAPI(String Prompt) {
        //定义发送数据
        //["Charles Mahoney","","None","None",20,"Euler a",false,false,1,1,7,-1,-1,0,0,0,false,512,512,false,false,0.7,
        // "None",false,false,null,"","Seed","","Steps","",true,false,null,"",""]
        List data = new ArrayList();
        JSONObject param = new JSONObject();
        param.put("session_hash", "ee5xafgjlfy");
        param.put("fn_index", 11);
        data.add(Prompt);
        data.add("lowres, bad anatomy, bad hands, text, error, missing fingers, extra digit, fewer digits, cropped, worst quality, low quality, normal quality, jpeg artifacts, signature, watermark, username, blurry, bad feet");
        data.add("None");
        data.add("None");
        data.add(20);
        data.add("Euler a");
        data.add(false);
        data.add(false);
        data.add(1);
        data.add(1);
        data.add(7);
        data.add(-1);
        data.add(-1);
        data.add(0);
        data.add(0);
        data.add(0);
        data.add(false);
        data.add(512);
        data.add(1024);
        data.add(false);
        data.add(false);
        data.add(0.7);
        data.add("None");
        data.add(false);
        data.add(false);
        data.add(null);
        data.add("");
        data.add("Seed");
        data.add("");
        data.add("Steps");
        data.add("");
        data.add(true);
        data.add(false);
        data.add(null);
        data.add("");
        data.add("");
        param.put("data", data);
        //定义接收数据
        JSONObject result = new JSONObject();
        String url = "http://127.0.0.1:7860/api/predict";
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
