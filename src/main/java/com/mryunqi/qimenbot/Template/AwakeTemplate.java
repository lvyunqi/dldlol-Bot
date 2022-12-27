package com.mryunqi.qimenbot.Template;


import com.alibaba.fastjson.JSONObject;

import java.util.Random;

public class AwakeTemplate {

    public String get_awakeSingleTemplate(String name,String sex,String wuhunName,String wuhunData,int lv,int con){
        JSONObject data = JSONObject.parseObject(wuhunData);
        Random r = new Random();
        int pow = (r.nextInt(5)+1) * lv + Integer.parseInt(data.getString("力量"));
        int pr = (r.nextInt(5)+1) * lv + Integer.parseInt(data.getString("攻击"));
        int de = (r.nextInt(10)+1) + Integer.parseInt(data.getString("防御"));
        int speed = Integer.parseInt(data.getString("速度"));
        int sp = (r.nextInt(30)+30) + Integer.parseInt(data.getString("精神力"));
        int hp = (r.nextInt(240)+240);
        int mp = (r.nextInt(20)+10) + Integer.parseInt(data.getString("魂力值"));
        int dodge = Integer.parseInt(data.getString("闪避"));
        int crit = Integer.parseInt(data.getString("暴击率"));
        int crit_damage = Integer.parseInt(data.getString("暴击伤害"));
        int cdAdd = Integer.parseInt(data.getString("技能加速"));
        String WuhunType = data.getString("武魂类型");
        String WuhunDes = data.getString("武魂描述");
        JSONObject userData = new JSONObject();
        userData.put("等级",lv);
        userData.put("经验",1);
        userData.put("体力",con);
        userData.put("力量",pow);
        userData.put("生命",hp);
        userData.put("当前生命",hp);
        userData.put("魂力值",mp);
        userData.put("当前魂力值",mp);
        userData.put("攻击",pr);
        userData.put("防御",de);
        userData.put("速度",speed);
        userData.put("精神力",sp);
        userData.put("闪避",dodge);
        userData.put("暴击率",crit);
        userData.put("暴击伤害",crit_damage);
        userData.put("生命储备",0);
        userData.put("魂力储备",0);
        userData.put("生命储备上限",500);
        userData.put("魂力储备上限",500);
        userData.put("技能加速",cdAdd);
        userData.put("第一武魂",wuhunName);
        userData.put("第一武魂类型",WuhunType);
        userData.put("第一武魂描述",WuhunDes);
        userData.put("第二武魂","");
        userData.put("第二武魂类型","");
        userData.put("第二武魂描述","");
        userData.put("气血之力","");
        userData.put("气血之力描述","");
        JSONObject userInfo = new JSONObject();
        userInfo.put("name",name);
        userInfo.put("sex",sex);
        userInfo.put("第二武魂增幅","");
        userInfo.put("气血之力增幅","");
        userInfo.put("武魂数量","1");
        userInfo.put("当前属性","第一武魂");
        JSONObject UserData = new JSONObject();
        UserData.put("userData",userData);
        UserData.put("userInfo",userInfo);
        return UserData.toJSONString();
    }

    public String get_awakeTwinsTemplate(String name,String sex,String wuhunFirstName,String wuhunSecondName,String wuhunFirstData,String wuhunSecondData,int lv,int con,int twinsp){
        JSONObject dataFirst = JSONObject.parseObject(wuhunFirstData);
        JSONObject dataSecond = JSONObject.parseObject(wuhunSecondData);
        Random r = new Random();
        int pow = (int) ((r.nextInt(5)+1) * lv + Integer.parseInt(dataFirst.getString("力量")) * (twinsp * 0.01+1));
        int pr = (int) ((r.nextInt(5)+1) * lv + Integer.parseInt(dataFirst.getString("攻击")) * (twinsp * 0.01+1));
        int de = (int) ((r.nextInt(10)+1) + Integer.parseInt(dataFirst.getString("防御")) * (twinsp * 0.01+1));
        int speed = Integer.parseInt(dataFirst.getString("速度"));
        int sp = (int) ((r.nextInt(30)+30) + Integer.parseInt(dataFirst.getString("精神力")) * (twinsp * 0.01+1));
        int hp = (r.nextInt(240)+240);
        int mp = (int) ((r.nextInt(20)+10) + Integer.parseInt(dataFirst.getString("魂力值")) * (twinsp * 0.01+1));
        int dodge = Integer.parseInt(dataFirst.getString("闪避"));
        int crit = Integer.parseInt(dataFirst.getString("暴击率"));
        int crit_damage = Integer.parseInt(dataFirst.getString("暴击伤害"));
        int cdAdd = Integer.parseInt(dataFirst.getString("技能加速"));
        String WuhunFirstType = dataFirst.getString("武魂类型");
        String WuhunFirstDes = dataFirst.getString("武魂描述");
        String WuhunSecondType = dataSecond.getString("武魂类型");
        String WuhunSecondDes = dataSecond.getString("武魂描述");
        JSONObject userData = new JSONObject();
        userData.put("等级",lv);
        userData.put("经验",1);
        userData.put("体力",con);
        userData.put("力量",pow);
        userData.put("生命",hp);
        userData.put("当前生命",hp);
        userData.put("魂力值",mp);
        userData.put("当前魂力值",mp);
        userData.put("攻击",pr);
        userData.put("防御",de);
        userData.put("速度",speed);
        userData.put("精神力",sp);
        userData.put("闪避",dodge);
        userData.put("暴击率",crit);
        userData.put("暴击伤害",crit_damage);
        userData.put("生命储备",0);
        userData.put("魂力储备",0);
        userData.put("生命储备上限",500);
        userData.put("魂力储备上限",500);
        userData.put("技能加速",cdAdd);
        userData.put("第一武魂",wuhunFirstName);
        userData.put("第一武魂类型",WuhunFirstType);
        userData.put("第一武魂描述",WuhunFirstDes);
        userData.put("第二武魂",wuhunSecondName);
        userData.put("第二武魂类型",WuhunSecondType);
        userData.put("第二武魂描述",WuhunSecondDes);
        userData.put("气血之力","");
        userData.put("气血之力描述","");
        JSONObject userInfo = new JSONObject();
        userInfo.put("name",name);
        userInfo.put("sex",sex);
        userInfo.put("第二武魂增幅",twinsp);
        userInfo.put("气血之力增幅","");
        userInfo.put("武魂数量","2");
        userInfo.put("当前属性","第一武魂");
        JSONObject UserData = new JSONObject();
        UserData.put("userData",userData);
        UserData.put("userInfo",userInfo);
        return UserData.toJSONString();
    }
    public String Get_AwakeSingleTemp(String UserData){
        JSONObject map = JSONObject.parseObject(UserData);
        return "To" + map.getJSONObject("userInfo").getString("name") + "〔" + map.getJSONObject("userData").getString("第一武魂") + "〕:\n" +
                "恭喜您觉醒成功，您的武魂信息如下:\n" +
                "·武魂名称：[" + map.getJSONObject("userData").getString("第一武魂") + "]\n" +
                "·武魂类型：[" + map.getJSONObject("userData").getString("第一武魂类型") + "]\n" +
                "·武魂描述：[" + map.getJSONObject("userData").getString("第一武魂描述") + "]\n" +
                "·先天魂力：[" + map.getJSONObject("userData").getString("等级") + "]\n" +
                "<可用命令>\n" +
                "状态\n";
    }

    public String Get_AwakeTwinsTemp(String UserData){
        JSONObject map = JSONObject.parseObject(UserData);
        return "To" + map.getJSONObject("userInfo").getString("name") + "〔" + map.getJSONObject("userData").getString("第一武魂") + "〕:\n" +
                "万中无一！恭喜您觉醒了【双生武魂】，您的武魂信息如下:\n" +
                "·第一武魂名称：[" + map.getJSONObject("userData").getString("第一武魂") + "]\n" +
                "·第一武魂类型：[" + map.getJSONObject("userData").getString("第一武魂类型") + "]\n" +
                "·第一武魂描述：[" + map.getJSONObject("userData").getString("第一武魂描述") + "]\n" +
                "·第二武魂名称：[" + map.getJSONObject("userData").getString("第二武魂") + "]\n" +
                "·第二武魂类型：[" + map.getJSONObject("userData").getString("第二武魂类型") + "]\n" +
                "·第二武魂描述：[" + map.getJSONObject("userData").getString("第二武魂描述") + "]\n" +
                "·先天魂力：[" + map.getJSONObject("userData").getString("等级") + "]\n" +
                "<可用命令>\n" +
                "状态\n";
    }
}
