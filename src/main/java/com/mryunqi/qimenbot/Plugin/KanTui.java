package com.mryunqi.qimenbot.Plugin;

import com.alibaba.fastjson2.JSONObject;
import com.mikuac.shiro.common.utils.MsgUtils;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.AnyMessageEvent;
import com.mryunqi.qimenbot.Controller.RestAPI;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import static com.mryunqi.qimenbot.Controller.RestAPI.GetPicTui;

@Component
public class KanTui extends BotPlugin {
    @SneakyThrows
    @Override
    public int onAnyMessage(@NotNull Bot bot, @NotNull AnyMessageEvent event){
        String msg = event.getMessage();
        if ("看腿".equals(msg)){
            bot.sendMsg(event,"正在找图。。。。",false);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String Data = GetPicTui("http://qqij.cn/tui/");
            String PicUrl = "http://qqij.cn/tui/"+Data;
            String PicCheck = RestAPI.PicCheck(PicUrl);
            JSONObject resultJson = JSONObject.parseObject(PicCheck);
            if (resultJson.getString("conclusion").equals("合规")){
                System.out.println(PicCheck);
                String message = MsgUtils.builder()
                        .at(event.getUserId())
                        .text("> 该图已经过百度云AI审核"+"\n[CQ:image,file=" + PicUrl + ",cache=0]")
                        .build();
                bot.sendMsg(event,message,false);
            }else {
                return MESSAGE_IGNORE;
            }
        }
        return MESSAGE_IGNORE;
    }
}
