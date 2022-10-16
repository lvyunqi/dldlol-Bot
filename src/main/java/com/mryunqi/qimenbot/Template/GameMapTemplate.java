package com.mryunqi.qimenbot.Template;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import static com.mryunqi.qimenbot.Util.FileUtils.is_file;

public class GameMapTemplate {
    public String GetGameMapTemplate(String UserData,String NowMap,String NPC,String Hunshou,String Gamemap,String UserNowAttribute,String MapTransport){
        JSONObject map = JSON.parseObject(UserData);
        String rootPath = System.getProperty("user.dir");
        return "To" + map.getJSONObject("userInfo").getString("name") + "〔" + map.getJSONObject("userData").getString(UserNowAttribute) + "〕\n" +
                "【" + NowMap + "】\n" +
                (!is_file("/斗罗大陆图片/地图/" + NowMap + ".jpg") ? "" : "[CQ:image,file=file:///" + rootPath + "/斗罗大陆图片/地图/" + NowMap + ".jpg,cache=0]\n") +
                (NPC.equals("") ? "" : "NPC：" + NPC + "\n") +
                (Hunshou.equals("") ? "" : "魂兽：" + Hunshou + "\n") +
                (MapTransport.equals("") ? "" : "|>>>传送装置<<<|\n\n") +
                "特殊场景：敬请期待\n\n" +
                "[地图方向]\n" +
                Gamemap + "\n" +
                "<可用命令>\n" +
                "进入 场景名称\n" +
                "对话 NPC名称\n" +
                "查看传送 地图名称\n" +
                "传送 地图名称\n" +
                "向 上/下/左/右\n";
    }
}
