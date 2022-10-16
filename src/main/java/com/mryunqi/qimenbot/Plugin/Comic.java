package com.mryunqi.qimenbot.Plugin;

import com.alibaba.fastjson2.JSONObject;
import com.mikuac.shiro.common.utils.MsgUtils;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.WholeMessageEvent;
import com.mryunqi.qimenbot.Controller.RestAPI;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class Comic extends BotPlugin {
    @Override
    public int onWholeMessage(@NotNull Bot bot, @NotNull WholeMessageEvent event){
        String msg = event.getMessage();
        if ("动漫壁纸".equals(msg)){
            bot.sendMsg(event,"正在找图。。。。",false);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ResponseEntity<String> result = RestAPI.comic("comic", "0", "1");
            String resultString = String.valueOf(result.getBody());
            JSONObject resultJson = JSONObject.parseObject(resultString);
            String title = resultJson.getObject("result", JSONObject.class).getList("list", JSONObject.class).get(0).getString("title");
            String url = resultJson.getObject("result", JSONObject.class).getList("list", JSONObject.class).get(0).getString("url");
            String message = MsgUtils.builder()
                    .at(event.getUserId())
                    .text("> "+title+"\n[CQ:image,file=" + url + ",cache=0]")
                    .build();
            bot.sendMsg(event,message,false);
        }
        return MESSAGE_IGNORE;
    }
}
