package com.mryunqi.qimenbot.Template;

import com.alibaba.fastjson2.JSONObject;
import com.mryunqi.qimenbot.Controller.GameHunshou;
import com.mryunqi.qimenbot.Controller.PublicAuth;

import static com.mryunqi.qimenbot.Util.FileUtils.is_file;

public class UserSkillTemplate {
    public static String Get_UserSkillTempSkill_null(String UserData,String Attribute){
        PublicAuth publicAuth = new PublicAuth();
        return publicAuth.Get_UserHead(UserData,Attribute) +
                "当前地图您还没有新的击杀魂兽所得魂环！\n" +
                "<可用命令>\n" +
                "状态\n";
    }

    public static String Get_UserSkillTempSkill(String UserData,String Attribute,String UserTempSkill){
        PublicAuth publicAuth = new PublicAuth();
        String rootPath = System.getProperty("user.dir");
        JSONObject map = JSONObject.parseObject(UserTempSkill);
        return publicAuth.Get_UserHead(UserData,Attribute) +
                "魂环信息如下：\n" +
                (!is_file("/斗罗大陆图片/魂环/" + GameHunshou.Get_HunShouAgeLevelName(Integer.parseInt(map.getString("Age"))) + ".jpg") ? "" : "[CQ:image,file=file:///" + rootPath + "/斗罗大陆图片/魂环/" + GameHunshou.Get_HunShouAgeLevelName(Integer.parseInt(map.getString("Age"))) + ".jpg,cache=0]\n") +
                "所属魂兽：" + map.getString("HunShouName") + "\n" +
                "魂环年份：" + map.getString("Age") + "\n" +
                "<可用命令>\n" +
                "附加魂环\n";
    }

    public static String Get_UserAddSkill_NullSkill(String UserData,String Attribute,int HP){
        PublicAuth publicAuth = new PublicAuth();
        return publicAuth.Get_UserHead(UserData,Attribute) +
                "[魂环附加失败！]\n" +
                "该魂环为残缺魂环，未附带任何魂技！\n" +
                "生命减少：-" + HP + "\n" +
                "<可用命令>\n" +
                "状态\n";
    }

    /* 附加魂环成功 */
    public static String Get_UserAddSkill_Success(String UserData,String Attribute,String SkillData){
        JSONObject obj = JSONObject.parseObject(SkillData);
        PublicAuth publicAuth = new PublicAuth();
        String rootPath = System.getProperty("user.dir");
        return publicAuth.Get_UserHead(UserData,Attribute) +
                "恭喜您附加魂环成功！魂环状态如下:\n" +
                (!is_file("/斗罗大陆图片/魂环/" + GameHunshou.Get_HunShouAgeLevelName(Integer.parseInt(obj.getString("SkillAge"))) + ".jpg") ? "" : "[CQ:image,file=file:///" + rootPath + "/斗罗大陆图片/魂环/" + GameHunshou.Get_HunShouAgeLevelName(Integer.parseInt(obj.getString("SkillAge"))) + ".jpg,cache=0]\n") +
                "·所属魂兽：" + obj.getString("SkillHunShouName") + "\n" +
                "·魂环年份：" + obj.getString("SkillAge") + "\n" +
                "·魂环技能：" + obj.getString("SkillName") + "\n" +
                "<可用命令>\n" +
                "查看魂环\n";
    }

    /* 玩家无需附加魂环 */
    public static String Get_UserAddSkill_NoSkill(String UserData,String Attribute){
        PublicAuth publicAuth = new PublicAuth();
        return publicAuth.Get_UserHead(UserData,Attribute) +
                "您没有多余的魂环空位哦！\n" +
                "<可用命令>\n" +
                "状态\n";
    }
}
