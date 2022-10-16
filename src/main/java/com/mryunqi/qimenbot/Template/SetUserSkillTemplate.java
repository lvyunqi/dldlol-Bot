package com.mryunqi.qimenbot.Template;

import com.mryunqi.qimenbot.Controller.PublicAuth;

public class SetUserSkillTemplate {
    public static String Get_SetUserSkill_No_Skill(String SkillName,String UserData,String Attribute) {
        PublicAuth publicAuth = new PublicAuth();
        return publicAuth.Get_UserHead(UserData,Attribute) +
                "当前武魂附加魂环中没有【" + SkillName + "】该魂技！\n" +
                "<可用命令>\n" +
                "查看魂环";
    }

    public static String Get_SetUserSkill_No_Num(String UserData,String Attribute) {
        PublicAuth publicAuth = new PublicAuth();
        return publicAuth.Get_UserHead(UserData,Attribute) +
                "格式错误！\n" +
                "<可用命令>\n" +
                "查看魂环";
    }
    public static String Get_SetUserSkill_Success(String UserData,String Attribute,String id,String name) {
        PublicAuth publicAuth = new PublicAuth();
        return publicAuth.Get_UserHead(UserData,Attribute) +
                "成功设置["+name+"]到魂技"+id+"，发送 魂技"+id+"！\n" +
                "<可用命令>\n" +
                "查看魂环";
    }
}
