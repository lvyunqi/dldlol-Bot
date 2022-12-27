package com.mryunqi.qimenbot.Template;

import com.alibaba.fastjson.JSONObject;
import com.mryunqi.qimenbot.Controller.PublicAuth;

import static com.mryunqi.qimenbot.Util.FileUtils.is_file;

public class BattleTemplate {
    /* 玩家攻击魂兽闪避 */
    public static String Get_User_PVE_HunShou_Dodge_Attack(String UserData, String Attribute, long HunShouHP, int TimeCha, String FightData){
        PublicAuth publicAuth = new PublicAuth();
        JSONObject map = JSONObject.parseObject(UserData);
        return publicAuth.Get_UserHead(UserData,Attribute) +
                map.getJSONObject("userInfo").getString("name")+"拿起[拳头]攻击向对方，却被对方闪开了！\n" +
                "对方生命剩余:" + HunShouHP + "\n\n" +
                "【战斗状况】\n" +
                "在之前的"+TimeCha+"秒中\n"+
                FightData;
    }
    /* 玩家暴击魂兽，魂兽未死亡 */
    public static String Get_User_PVE_HunShou_Crit_NotDie(String UserData, String Attribute, long HunShouHP, int TimeCha, long Hurt, String FightData){
        PublicAuth publicAuth = new PublicAuth();
        JSONObject map = JSONObject.parseObject(UserData);
        return publicAuth.Get_UserHead(UserData,Attribute) +
                map.getJSONObject("userInfo").getString("name")+"拿起[拳头]攻击向对方，造成" +
                Hurt + "点[暴击]伤害\n" +
                "对方生命剩余:" + HunShouHP + "\n\n" +
                "【战斗状况】\n" +
                "在之前的"+TimeCha+"秒中\n"+
                FightData;
    }
    /* 玩家暴击魂兽，魂兽死亡，玩家需要魂环 */
    public static String Get_User_PVE_HunShou_Crit_Die_NeedHuan(String UserData, String Attribute, int TimeCha, long Hurt, long HunShouEXP, String FightData, String DropMessage, String HunShouDropAge, String HunShouAgeLevelName){
        PublicAuth publicAuth = new PublicAuth();
        String rootPath = System.getProperty("user.dir");
        JSONObject map = JSONObject.parseObject(UserData);
        return publicAuth.Get_UserHead(UserData,Attribute) +
                map.getJSONObject("userInfo").getString("name")+"拿起[拳头]攻击向对方，造成" +
                Hurt + "点[暴击]伤害\n" +
                "对方生命剩余:0\n\n" +
                "【战斗状况】\n" +
                "在之前的"+TimeCha+"秒中\n"+
                FightData +
                "对方已倒下，魂环徐徐升起：" + HunShouDropAge +
                (!is_file("/斗罗大陆图片/魂环/" + HunShouAgeLevelName + ".jpg") ? "" : "[CQ:image,file=file:///" + rootPath + "/斗罗大陆图片/魂环/" + HunShouAgeLevelName + ".jpg,cache=0]\n") +
                "["+map.getJSONObject("userInfo").getString("name")+"]获得经验"+HunShouEXP+"[100%][需要魂环]\n" +
                "【物品掉落】\n" +
                DropMessage+
                "<可用命令>\n" +
                "魂环信息";
    }
    /* 玩家暴击魂兽，魂兽死亡，玩家升级 */
    public static String Get_User_PVE_HunShou_Crit_Die_LevelUp(String UserData, String Attribute, int TimeCha, long Hurt, long HunShouEXP, String FightData, String DropMessage, String HunShouDropAge, String HunShouAgeLevelName){
        PublicAuth publicAuth = new PublicAuth();
        String rootPath = System.getProperty("user.dir");
        JSONObject map = JSONObject.parseObject(UserData);
        return publicAuth.Get_UserHead(UserData,Attribute) +
                map.getJSONObject("userInfo").getString("name")+"拿起[拳头]攻击向对方，造成" +
                Hurt + "点[暴击]伤害\n" +
                "对方生命剩余:0\n\n" +
                "【战斗状况】\n" +
                "在之前的"+TimeCha+"秒中\n"+
                FightData +
                "对方已倒下，魂环徐徐升起：" + HunShouDropAge +
                (!is_file("/斗罗大陆图片/魂环/" + HunShouAgeLevelName + ".jpg") ? "" : "[CQ:image,file=file:///" + rootPath + "/斗罗大陆图片/魂环/" + HunShouAgeLevelName + ".jpg,cache=0]\n") +
                "["+map.getJSONObject("userInfo").getString("name")+"]获得经验"+HunShouEXP+"[100%][Lv up!]\n" +
                "【物品掉落】\n" +
                DropMessage+
                "<可用命令>\n" +
                "魂环信息";
    }
    /* 玩家暴击魂兽，魂兽死亡 */
    public static String Get_User_PVE_HunShou_Crit_Die(String UserData, String Attribute, int TimeCha, long Hurt, long HunShouEXP, String FightData, String DropMessage, String HunShouDropAge, String HunShouAgeLevelName){
        PublicAuth publicAuth = new PublicAuth();
        String rootPath = System.getProperty("user.dir");
        JSONObject map = JSONObject.parseObject(UserData);
        return publicAuth.Get_UserHead(UserData,Attribute) +
                map.getJSONObject("userInfo").getString("name")+"拿起[拳头]攻击向对方，造成" +
                Hurt + "点[暴击]伤害\n" +
                "对方生命剩余:0\n\n" +
                "【战斗状况】\n" +
                "在之前的"+TimeCha+"秒中\n"+
                FightData +
                "对方已倒下，魂环徐徐升起：" + HunShouDropAge +
                (!is_file("/斗罗大陆图片/魂环/" + HunShouAgeLevelName + ".jpg") ? "" : "[CQ:image,file=file:///" + rootPath + "/斗罗大陆图片/魂环/" + HunShouAgeLevelName + ".jpg,cache=0]\n") +
                "["+map.getJSONObject("userInfo").getString("name")+"]获得经验"+HunShouEXP+"[100%]\n" +
                "【物品掉落】\n" +
                DropMessage+
                "<可用命令>\n" +
                "魂环信息";
    }
    /* 玩家攻击魂兽，魂兽死亡，玩家需要魂环 */
    public static String Get_User_PVE_HunShou_Die_NeedHuan(String UserData, String Attribute, int TimeCha, long Hurt, long HunShouEXP, String FightData, String DropMessage, String HunShouDropAge, String HunShouAgeLevelName){
        PublicAuth publicAuth = new PublicAuth();
        String rootPath = System.getProperty("user.dir");
        JSONObject map = JSONObject.parseObject(UserData);
        return publicAuth.Get_UserHead(UserData,Attribute) +
                map.getJSONObject("userInfo").getString("name")+"拿起[拳头]攻击向对方，造成" +
                Hurt + "点伤害\n" +
                "对方生命剩余:0\n\n" +
                "【战斗状况】\n" +
                "在之前的"+TimeCha+"秒中\n"+
                FightData +
                "对方已倒下，魂环徐徐升起：" + HunShouDropAge +
                (!is_file("/斗罗大陆图片/魂环/" + HunShouAgeLevelName + ".jpg") ? "" : "[CQ:image,file=file:///" + rootPath + "/斗罗大陆图片/魂环/" + HunShouAgeLevelName + ".jpg,cache=0]\n") +
                "["+map.getJSONObject("userInfo").getString("name")+"]获得经验"+HunShouEXP+"[100%][需要魂环]\n" +
                "【物品掉落】\n" +
                DropMessage+
                "<可用命令>\n" +
                "魂环信息";
    }
    /* 玩家攻击魂兽，魂兽死亡，玩家升级 */
    public static String Get_User_PVE_HunShou_Die_LevelUp(String UserData, String Attribute, int TimeCha, long Hurt, long HunShouEXP, String FightData, String DropMessage, String HunShouDropAge, String HunShouAgeLevelName){
        PublicAuth publicAuth = new PublicAuth();
        String rootPath = System.getProperty("user.dir");
        JSONObject map = JSONObject.parseObject(UserData);
        return publicAuth.Get_UserHead(UserData,Attribute) +
                map.getJSONObject("userInfo").getString("name")+"拿起[拳头]攻击向对方，造成" +
                Hurt + "点伤害\n" +
                "对方生命剩余:0\n\n" +
                "【战斗状况】\n" +
                "在之前的"+TimeCha+"秒中\n"+
                FightData +
                "对方已倒下，魂环徐徐升起：" + HunShouDropAge +
                (!is_file("/斗罗大陆图片/魂环/" + HunShouAgeLevelName + ".jpg") ? "" : "[CQ:image,file=file:///" + rootPath + "/斗罗大陆图片/魂环/" + HunShouAgeLevelName + ".jpg,cache=0]\n") +
                "["+map.getJSONObject("userInfo").getString("name")+"]获得经验"+HunShouEXP+"[100%][Lv up!]\n" +
                "【物品掉落】\n" +
                DropMessage+
                "<可用命令>\n" +
                "魂环信息";
    }
    /* 玩家攻击魂兽，魂兽死亡 */
    public static String Get_User_PVE_HunShou_Die(String UserData, String Attribute, int TimeCha, long Hurt, long HunShouEXP, String FightData, String DropMessage, String HunShouDropAge, String HunShouAgeLevelName){
        PublicAuth publicAuth = new PublicAuth();
        String rootPath = System.getProperty("user.dir");
        JSONObject map = JSONObject.parseObject(UserData);
        return publicAuth.Get_UserHead(UserData,Attribute) +
                map.getJSONObject("userInfo").getString("name")+"拿起[拳头]攻击向对方，造成" +
                Hurt + "点伤害\n" +
                "对方生命剩余:0\n\n" +
                "【战斗状况】\n" +
                "在之前的"+TimeCha+"秒中\n"+
                FightData +
                "对方已倒下，魂环徐徐升起：" + HunShouDropAge +
                (!is_file("/斗罗大陆图片/魂环/" + HunShouAgeLevelName + ".jpg") ? "" : "[CQ:image,file=file:///" + rootPath + "/斗罗大陆图片/魂环/" + HunShouAgeLevelName + ".jpg,cache=0]\n") +
                "["+map.getJSONObject("userInfo").getString("name")+"]获得经验"+HunShouEXP+"[100%]\n" +
                "【物品掉落】\n" +
                DropMessage+
                "<可用命令>\n" +
                "魂环信息";
    }
    /* 玩家攻击魂兽，魂兽未死亡 */
    public static String Get_User_PVE_HunShou_NotDie(String UserData, String Attribute, long HunShouHP, int TimeCha, long Hurt, String FightData){
        PublicAuth publicAuth = new PublicAuth();
        JSONObject map = JSONObject.parseObject(UserData);
        return publicAuth.Get_UserHead(UserData,Attribute) +
                map.getJSONObject("userInfo").getString("name")+"拿起[拳头]攻击向对方，造成" +
                Hurt + "点伤害\n" +
                "对方生命剩余:" + HunShouHP + "\n\n" +
                "【战斗状况】\n" +
                "在之前的"+TimeCha+"秒中\n"+
                FightData;
    }

    /* 玩家攻击魂兽未击中 */
    public static String Get_User_PVE_HunShou_Skill_No_Attack(String UserData, String Attribute, long HunShouHP, int TimeCha, String FightData, String SkillName){
        PublicAuth publicAuth = new PublicAuth();
        JSONObject map = JSONObject.parseObject(UserData);
        return publicAuth.Get_UserHead(UserData,Attribute) +
                map.getJSONObject("userInfo").getString("name")+"发动了["+SkillName+"]"+",却并没有生效！\n" +
                "对方生命剩余:" + HunShouHP + "\n\n" +
                "【战斗状况】\n" +
                "在之前的"+TimeCha+"秒中\n"+
                FightData;
    }

    /* 玩家释放魂技，魂技冷却中 */
    public static String Get_User_PVE_HunShou_Skill_Cooling(String UserData, String Attribute, long HunShouHP, int TimeCha, long CDCha, String FightData, String SkillName){
        PublicAuth publicAuth = new PublicAuth();
        return publicAuth.Get_UserHead(UserData,Attribute) +
                "["+SkillName+"]"+"冷却时间剩余" +CDCha+"秒！\n"+
                "对方生命剩余:" + HunShouHP + "\n\n" +
                "【战斗状况】\n" +
                "在之前的"+TimeCha+"秒中\n"+
                FightData;
    }

    /* 玩家释放魂技，魂力不足 */
    public static String Get_User_PVE_HunShou_Skill_No_MP(String UserData, String Attribute, long HunShouHP, int TimeCha, String FightData){
        PublicAuth publicAuth = new PublicAuth();
        return publicAuth.Get_UserHead(UserData,Attribute) +
                "您的魂力不足,无法释放该魂技！\n"+
                "对方生命剩余:" + HunShouHP + "\n\n" +
                "【战斗状况】\n" +
                "在之前的"+TimeCha+"秒中\n"+
                FightData;
    }

    /* 玩家释放魂技，击杀魂兽，未升级 */
    public static String Get_User_PVE_HunShou_Skill_Die_NoLvUp(String UserData, String Attribute, int TimeCha, long Hurt, String HunShouName, String SkillName, String SkillInfo, String HunShouStatus, String UserStatus, String FightData
            , String HunShouDropAge, String HunShouAgeLevelName, long HunShouEXP, String DropMessage){
        PublicAuth publicAuth = new PublicAuth();
        String rootPath = System.getProperty("user.dir");
        JSONObject map = JSONObject.parseObject(UserData);
        return publicAuth.Get_UserHead(UserData,Attribute) +
                map.getJSONObject("userInfo").getString("name")+"发动了["+SkillName+"]{"+SkillInfo+"}:\n" +
                HunShouName+(HunShouStatus.equals("") ? "" : "+["+HunShouStatus+"]")+"，受到["+Hurt+"]伤害" +(UserStatus.equals("") ? "\n" : ",自身+[" + UserStatus+"]\n") +
                "对方生命剩余:0\n\n" +
                "【战斗状况】\n" +
                "在之前的"+TimeCha+"秒中\n"+
                FightData +
                "对方已倒下，魂环徐徐升起：" + HunShouDropAge +
                (!is_file("/斗罗大陆图片/魂环/" + HunShouAgeLevelName + ".jpg") ? "" : "[CQ:image,file=file:///" + rootPath + "/斗罗大陆图片/魂环/" + HunShouAgeLevelName + ".jpg,cache=0]\n") +
                "["+map.getJSONObject("userInfo").getString("name")+"]获得经验"+HunShouEXP+"[100%]\n" +
                "【物品掉落】\n" +
                DropMessage+
                "<可用命令>\n" +
                "魂环信息";
    }

    /* 玩家释放魂技，击杀魂兽，升级，需要魂环*/
    public static String Get_User_PVE_HunShou_Skill_Die_NeedHuan(String UserData, String Attribute, int TimeCha, long Hurt, String HunShouName, String SkillName, String SkillInfo
            , String HunShouStatus, String UserStatus, String FightData, String HunShouDropAge, String HunShouAgeLevelName, long HunShouEXP, String DropMessage){
        PublicAuth publicAuth = new PublicAuth();
        String rootPath = System.getProperty("user.dir");
        JSONObject map = JSONObject.parseObject(UserData);
        return publicAuth.Get_UserHead(UserData,Attribute) +
                map.getJSONObject("userInfo").getString("name")+"发动了["+SkillName+"]{"+SkillInfo+"}:\n" +
                HunShouName+(HunShouStatus.equals("") ? "" : "+["+HunShouStatus+"]")+"，受到["+Hurt+"]伤害" +(UserStatus.equals("") ? "\n" : ",自身+[" + UserStatus+"]\n") +
                "对方生命剩余:0\n\n" +
                "【战斗状况】\n" +
                "在之前的"+TimeCha+"秒中\n"+
                FightData +
                "对方已倒下，魂环徐徐升起：" + HunShouDropAge +
                (!is_file("/斗罗大陆图片/魂环/" + HunShouAgeLevelName + ".jpg") ? "" : "[CQ:image,file=file:///" + rootPath + "/斗罗大陆图片/魂环/" + HunShouAgeLevelName + ".jpg,cache=0]\n") +
                "["+map.getJSONObject("userInfo").getString("name")+"]获得经验"+HunShouEXP+"[100%][需要魂环]\n" +
                "【物品掉落】\n" +
                DropMessage+
                "<可用命令>\n" +
                "魂环信息";
    }

    /* 玩家释放魂技，击杀魂兽，升级*/
    public static String Get_User_PVE_HunShou_Skill_LvUP(String UserData, String Attribute, int TimeCha, long Hurt, String HunShouName, String SkillName, String SkillInfo
            , String HunShouStatus, String UserStatus, String FightData, String HunShouDropAge, String HunShouAgeLevelName, long HunShouEXP, String DropMessage){
        PublicAuth publicAuth = new PublicAuth();
        String rootPath = System.getProperty("user.dir");
        JSONObject map = JSONObject.parseObject(UserData);
        return publicAuth.Get_UserHead(UserData,Attribute) +
                map.getJSONObject("userInfo").getString("name")+"发动了["+SkillName+"]{"+SkillInfo+"}:\n" +
                HunShouName+(HunShouStatus.equals("") ? "" : "+["+HunShouStatus+"]")+"，受到["+Hurt+"]伤害" +(UserStatus.equals("") ? "\n" : ",自身+[" + UserStatus+"]\n") +
                "对方生命剩余:0\n\n" +
                "【战斗状况】\n" +
                "在之前的"+TimeCha+"秒中\n"+
                FightData +
                "对方已倒下，魂环徐徐升起：" + HunShouDropAge +
                (!is_file("/斗罗大陆图片/魂环/" + HunShouAgeLevelName + ".jpg") ? "" : "[CQ:image,file=file:///" + rootPath + "/斗罗大陆图片/魂环/" + HunShouAgeLevelName + ".jpg,cache=0]\n") +
                "["+map.getJSONObject("userInfo").getString("name")+"]获得经验"+HunShouEXP+"[100%][Lv up!]\n" +
                "【物品掉落】\n" +
                DropMessage+
                "<可用命令>\n" +
                "魂环信息";
    }

    /* 玩家释放魂技，未击杀魂兽*/
    public static String Get_User_PVE_HunShou_Skill_NoDie(String UserData, String Attribute, long HunShouHP, int TimeCha, long Hurt, String HunShouName, String SkillName, String SkillInfo
            , String HunShouStatus, String UserStatus, String FightData){
        PublicAuth publicAuth = new PublicAuth();
        JSONObject map = JSONObject.parseObject(UserData);
        return publicAuth.Get_UserHead(UserData,Attribute) +
                map.getJSONObject("userInfo").getString("name")+"发动了["+SkillName+"]{"+SkillInfo+"}:\n" +
                HunShouName+(HunShouStatus.equals("") ? "" : "+["+HunShouStatus+"]")+"，受到["+Hurt+"]伤害" +(UserStatus.equals("") ? "\n" : ",自身+[" + UserStatus+"]\n") +
                "对方生命剩余:"+HunShouHP+"\n\n" +
                "【战斗状况】\n" +
                "在之前的"+TimeCha+"秒中\n"+
                FightData;
    }

    /* 逃跑 */
    public static String Escape_PVE(String UserData, String Attribute, long Coin, String Currency){
        PublicAuth publicAuth = new PublicAuth();
        return publicAuth.Get_UserHead(UserData,Attribute) +
                "逃跑成功！您在逃跑时不慎损失["+Currency+"]"+Coin+"\n";
    }
}
