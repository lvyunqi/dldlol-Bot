package com.mryunqi.qimenbot.Template;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mryunqi.qimenbot.Controller.Function;
import com.mryunqi.qimenbot.Controller.User;

import static com.mryunqi.qimenbot.Util.FileUtils.is_file;

public class UserStateTemplate {

    public String get_AttackWeaponTmp(String attackWeapon) {
        if (!attackWeapon.equals("")) return "[攻击魂导器：" + attackWeapon + "]\n";
        return "";
    }
    public String get_DefendWeaponTmp(String defendWeapon) {
        if (!defendWeapon.equals("")) return "[防御魂导器：" + defendWeapon + "]\n";
        return "";
    }
    public String get_JobTmp(String Job) {
        if (!Job.equals("")) return "职业："+Job+"\n";
        return "";
    }
    public String get_BattlePlateTmp(String BattlePlate) {
        if (!BattlePlate.equals("")) return "斗铠："+BattlePlate+"\n";
        return "";
    }
    public String get_MechaTmp(String Mecha) {
        if (!Mecha.equals("")) return "机甲："+Mecha+"\n";
        return "";
    }
    public String get_WarshipTmp(String Warship) {
        if (!Warship.equals("")) return "战舰："+Warship+"\n";
        return "";
    }
    public String get_UserTem(String UserData,String LvName,int Ce,int NextLevelExp,double NextLevelExpPercent,int UserSpirit,String UserSpiritName){
        JSONObject map = JSONObject.parseObject(UserData);
        return "·等级：" + map.getJSONObject("userData").getString("等级") + "[" + LvName + "]\n" +
                "·经验：" + map.getJSONObject("userData").getString("经验") + "/" + NextLevelExp + "[" + String.format("%.2f", NextLevelExpPercent) + "%]\n" +
                "·战力：" + Ce + "\n" +
                "·精神力：" + map.getJSONObject("userData").getString("精神力") + "/" + UserSpirit + "[" + UserSpiritName + "]\n" +
                "·生命：" + map.getJSONObject("userData").getString("当前生命") + "/" + map.getJSONObject("userData").getString("生命") + "\n" +
                "·魂力：" + map.getJSONObject("userData").getString("当前魂力值") + "/" + map.getJSONObject("userData").getString("魂力值") + "\n" +
                "·攻击：" + map.getJSONObject("userData").getString("攻击") + "\n" +
                "·力量：" + map.getJSONObject("userData").getString("力量") + "\n" +
                "·防御：" + map.getJSONObject("userData").getString("防御") + "\n" +
                "·暴击：" + map.getJSONObject("userData").getString("暴击率") + "\n" +
                "·暴伤：" + map.getJSONObject("userData").getString("暴击伤害") + "\n" +
                "·速度：" + map.getJSONObject("userData").getString("速度") + "\n" +
                "·闪避：" + map.getJSONObject("userData").getString("闪避") + "\n" +
                "·体力值：" + map.getJSONObject("userData").getString("体力") + "\n" +
                "<可用命令>\n" +
                "背包\n";
    }
    public String UserShelfState(String QQ,String  userData, String attackWeapon, String defendWeapon,String LvName
            ,String Job,String BattlePlate,String Mecha,String Warship,int UpExp) {
        String rootPath = System.getProperty("user.dir");
        Function func = new Function();
        User user = new User(QQ);
        JSONObject map = JSON.parseObject(userData);
        int UserSpirit = user.Get_UserSpirit(Integer.parseInt(map.getJSONObject("userData").getString("精神力")));
        String UserSpiritName = user.Get_UserSpiritName(Integer.parseInt(map.getJSONObject("userData").getString("精神力")));
        int NextLevelExp = func.Get_NextLevelExp(Integer.parseInt(map.getJSONObject("userData").getString("等级")),UpExp);
        double NextLevelExpPercent = func.Get_NextLevelExpPercent(Integer.parseInt(map.getJSONObject("userData").getString("经验")),NextLevelExp);
        int Ce = func.Get_Ce(userData);
        String Attribute = user.Get_UserNowAttribute(userData);
        String head = "";
        switch (Attribute) {
            case "第一武魂":
                return "To" + map.getJSONObject("userInfo").getString("name") + "〔" + map.getJSONObject("userData").getString("第一武魂") + "〕\n" +
                        "【" + map.getJSONObject("userData").getString("第一武魂类型") + "】\n" +
                        (!is_file("/斗罗大陆图片/武魂/".concat(map.getJSONObject("userData").getString("第一武魂").concat(".jpg"))) ? "" : "[CQ:image,file=file:///" + rootPath + "/斗罗大陆图片/武魂/" + map.getJSONObject("userData").getString("第一武魂") + ".jpg,cache=0]\n") +
                        get_AttackWeaponTmp(attackWeapon) +
                        get_DefendWeaponTmp(defendWeapon) +
                        get_JobTmp(Job) +
                        get_BattlePlateTmp(BattlePlate) +
                        get_MechaTmp(Mecha) +
                        get_WarshipTmp(Warship) +
                        get_UserTem(userData,LvName,Ce,NextLevelExp,NextLevelExpPercent,UserSpirit,UserSpiritName);
            case "第二武魂":
                return "To" + map.getJSONObject("userInfo").getString("name") + "〔" + map.getJSONObject("userData").getString("第二武魂") + "〕\n" +
                        "【" + map.getJSONObject("userData").getString("第二武魂类型") + "】\n" +
                        (!is_file("/斗罗大陆图片/武魂/".concat(map.getJSONObject("userData").getString("第二武魂").concat(".jpg"))) ? "" : "[CQ:image,file=file:///" + rootPath + "/斗罗大陆图片/武魂/" + map.getJSONObject("userData").getString("第二武魂") + ".jpg,cache=0]\n") +
                        get_AttackWeaponTmp(attackWeapon) +
                        get_DefendWeaponTmp(defendWeapon) +
                        get_JobTmp(Job) +
                        get_BattlePlateTmp(BattlePlate) +
                        get_MechaTmp(Mecha) +
                        get_WarshipTmp(Warship) +
                        get_UserTem(userData,LvName,Ce,NextLevelExp,NextLevelExpPercent,UserSpirit,UserSpiritName);
            case "气血之力":
                return "To" + map.getJSONObject("userInfo").getString("name") + "〔" + map.getJSONObject("userData").getString("气血之力") + "〕\n" +
                        "【血脉之力】\n" +
                        (!is_file("/斗罗大陆图片/武魂/".concat(map.getJSONObject("userData").getString("气血之力").concat(".jpg"))) ? "" : "[CQ:image,file=file:///" + rootPath + "/斗罗大陆图片/武魂/" + map.getJSONObject("userData").getString("气血之力") + ".jpg,cache=0]\n") +
                        get_AttackWeaponTmp(attackWeapon) +
                        get_DefendWeaponTmp(defendWeapon) +
                        get_JobTmp(Job) +
                        get_BattlePlateTmp(BattlePlate) +
                        get_MechaTmp(Mecha) +
                        get_WarshipTmp(Warship) +
                        get_UserTem(userData,LvName,Ce,NextLevelExp,NextLevelExpPercent,UserSpirit,UserSpiritName);
        }
        return head;
    }
    public String UserOtherState(String QQ,String  userData,String  userOtherData, String attackWeapon, String defendWeapon,String LvName
            ,String Job,String BattlePlate,String Mecha,String Warship,int UpExp) {
        String rootPath = System.getProperty("user.dir");
        User OtherUser = new User(QQ);
        Function func = new Function();
        JSONObject shelfMap = JSON.parseObject(userData);
        JSONObject map = JSON.parseObject(userOtherData);
        int UserSpirit = OtherUser.Get_UserSpirit(Integer.parseInt(map.getJSONObject("userData").getString("精神力")));
        String UserSpiritName = OtherUser.Get_UserSpiritName(Integer.parseInt(map.getJSONObject("userData").getString("精神力")));
        int NextLevelExp = func.Get_NextLevelExp(Integer.parseInt(map.getJSONObject("userData").getString("等级")), UpExp);
        double NextLevelExpPercent = func.Get_NextLevelExpPercent(Integer.parseInt(map.getJSONObject("userData").getString("经验")), NextLevelExp);
        int Ce = func.Get_Ce(userOtherData);
        String Attribute = OtherUser.Get_UserNowAttribute(userOtherData);
        String head = "";
        switch (Attribute) {
            case "第一武魂":
                return "To" + shelfMap.getJSONObject("userInfo").getString("name") + "\n" +
                        "以下是"+map.getJSONObject("userInfo").getString("name")+"["+QQ+"]"+"的信息\n"+"【" + map.getJSONObject("userData").getString("第一武魂") + "-" +
                        map.getJSONObject("userData").getString("第一武魂类型") + "】\n" +
                        (!is_file("/斗罗大陆图片/武魂/".concat(map.getJSONObject("userData").getString("第一武魂").concat(".jpg"))) ? "" : "[CQ:image,file=file:///" + rootPath + "/斗罗大陆图片/武魂/" + map.getJSONObject("userData").getString("第一武魂") + ".jpg,cache=0]\n") +
                        get_AttackWeaponTmp(attackWeapon) +
                        get_DefendWeaponTmp(defendWeapon) +
                        get_JobTmp(Job) +
                        get_BattlePlateTmp(BattlePlate) +
                        get_MechaTmp(Mecha) +
                        get_WarshipTmp(Warship) +
                        get_UserTem(userOtherData, LvName, Ce, NextLevelExp, NextLevelExpPercent, UserSpirit, UserSpiritName);
            case "第二武魂":
                return "To" + shelfMap.getJSONObject("userInfo").getString("name") + "\n" +
                        "以下是"+map.getJSONObject("userInfo").getString("name")+"["+QQ+"]"+"的信息\n"+"【" + map.getJSONObject("userData").getString("第一武魂") + "-" +
                        map.getJSONObject("userData").getString("第二武魂类型") + "】\n" +
                        (!is_file("/斗罗大陆图片/武魂/".concat(map.getJSONObject("userData").getString("第二武魂").concat(".jpg"))) ? "" : "[CQ:image,file=file:///" + rootPath + "/斗罗大陆图片/武魂/" + map.getJSONObject("userData").getString("第二武魂") + ".jpg,cache=0]\n") +
                        get_AttackWeaponTmp(attackWeapon) +
                        get_DefendWeaponTmp(defendWeapon) +
                        get_JobTmp(Job) +
                        get_BattlePlateTmp(BattlePlate) +
                        get_MechaTmp(Mecha) +
                        get_WarshipTmp(Warship) +
                        get_UserTem(userOtherData, LvName, Ce, NextLevelExp, NextLevelExpPercent, UserSpirit, UserSpiritName);
            case "气血之力":
                return "To" + shelfMap.getJSONObject("userInfo").getString("name") + "\n" +
                        "以下是"+map.getJSONObject("userInfo").getString("name")+"["+QQ+"]"+"的信息\n"+"【" + map.getJSONObject("userData").getString("第一武魂") + "-血脉之力】\n" +
                        (!is_file("/斗罗大陆图片/武魂/".concat(map.getJSONObject("userData").getString("气血之力").concat(".jpg"))) ? "" : "[CQ:image,file=file:///" + rootPath + "/斗罗大陆图片/武魂/" + map.getJSONObject("userData").getString("气血之力") + ".jpg,cache=0]\n") +
                        get_AttackWeaponTmp(attackWeapon) +
                        get_DefendWeaponTmp(defendWeapon) +
                        get_JobTmp(Job) +
                        get_BattlePlateTmp(BattlePlate) +
                        get_MechaTmp(Mecha) +
                        get_WarshipTmp(Warship) +
                        get_UserTem(userOtherData, LvName, Ce, NextLevelExp, NextLevelExpPercent, UserSpirit, UserSpiritName);
        }
        return head;
    }

}
