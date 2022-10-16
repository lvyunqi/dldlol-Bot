package com.mryunqi.qimenbot.Template;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mryunqi.qimenbot.Controller.PublicAuth;

public class HunHuanListTpl {
    public static String No_HunHuan(String UserData,String Attribute,String QQ){
        PublicAuth publicAuth = new PublicAuth();
        JSONObject map = JSON.parseObject(UserData);
        return publicAuth.Get_UserHead(UserData,Attribute) +
                "以下是 "+map.getJSONObject("userInfo").getString("name")+"["+QQ+"]的["+map.getJSONObject("userData").getString(Attribute)+"]魂环列表：\n" +
                "无附加魂环\n"+
                "<可用命令>\n" +
                "附加魂环\n";
    }
    public static String HunHuanListTemp(String UserData,String Attribute,String QQ,String data){
        PublicAuth publicAuth = new PublicAuth();
        JSONObject map = JSON.parseObject(UserData);
        return publicAuth.Get_UserHead(UserData,Attribute) +
                "以下是 "+map.getJSONObject("userInfo").getString("name")+"["+QQ+"]的["+map.getJSONObject("userData").getString(Attribute)+"]魂环列表：\n" +
                data;
    }

    public static String Other_No_HunHuan(String UserData,String OtherUserData,String Attribute,String OtherAttribute,String OtherQQ){
        PublicAuth publicAuth = new PublicAuth();
        JSONObject map = JSON.parseObject(OtherUserData);
        return publicAuth.Get_UserHead(UserData,Attribute) +
                "以下是 "+map.getJSONObject("userInfo").getString("name")+"["+OtherQQ+"]的["+map.getJSONObject("userData").getString(OtherAttribute)+"]魂环列表：\n" +
                "无附加魂环";
    }
    public static String Other_HunHuanListTemp(String UserData,String OtherUserData,String Attribute,String OtherAttribute,String OtherQQ,String data){
        PublicAuth publicAuth = new PublicAuth();
        JSONObject map = JSON.parseObject(OtherUserData);
        return publicAuth.Get_UserHead(UserData,Attribute) +
                "以下是 "+map.getJSONObject("userInfo").getString("name")+"["+OtherQQ+"]的["+map.getJSONObject("userData").getString(OtherAttribute)+"]魂环列表：\n" +
                data;
    }
}
