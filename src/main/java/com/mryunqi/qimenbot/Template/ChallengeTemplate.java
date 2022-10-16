package com.mryunqi.qimenbot.Template;

import com.alibaba.fastjson.JSONObject;
import com.mryunqi.qimenbot.Controller.PublicAuth;

import static com.mryunqi.qimenbot.Util.FileUtils.is_file;

public class ChallengeTemplate {
    public String get_ChallengeTemplate(String UserData,String Attribute,String MonsterName) {
        PublicAuth publicAuth = new PublicAuth();
        String rootPath = System.getProperty("user.dir");
        JSONObject map = JSONObject.parseObject(UserData);
        return publicAuth.Get_UserHead(UserData,Attribute) +
                "战斗开始啦，请小心。[体力-1]\n" +
                "敌方魂兽："+MonsterName+"\n" +
                (!is_file("/斗罗大陆图片/魂兽/" + MonsterName + ".jpg") ? "" : "[CQ:image,file=file:///" + rootPath + "/斗罗大陆图片/魂兽/" + MonsterName + ".jpg,cache=0]\n") +
                "1P："+map.getJSONObject("userInfo").getString("name")+"\n\n" +
                "Tip：P 为己方目标\n" +
                "<可用命令>\n" +
                "攻击\n" +
                "魂技1~50-1/2/3/4 P\n" +
                "使物 1~20\n";
    }
}
