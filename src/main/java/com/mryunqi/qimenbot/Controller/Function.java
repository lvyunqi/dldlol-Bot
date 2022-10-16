package com.mryunqi.qimenbot.Controller;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

public class Function {
    public int Get_SkillNum(String SkillData){
        if (SkillData == null) return 0;
        JSONObject obj = JSONObject.parseObject(SkillData);
        int FirstSize = Get_FirstSkillNum(obj);
        int SecondSize = Get_SecondSkillNum(obj);
        int ThirdSize = Get_ThirdSkillNum(obj);
        return Math.max(Math.max(FirstSize,SecondSize),ThirdSize);
    }

    /* 获取第一武魂数量 */
    public int Get_FirstSkillNum(JSONObject SkillData){
        for (String key : SkillData.keySet()){
            if (key.equals("第一武魂")){
                return SkillData.getJSONObject(key).size();
            }
        }
        return 0;
    }

    /* 获取第二武魂数量 */
    public int Get_SecondSkillNum(JSONObject SkillData){
        for (String key : SkillData.keySet()){
            if (key.equals("第二武魂")){
                return SkillData.getJSONObject(key).size();
            }
        }
        return 0;
    }

    /* 获取血脉之力数量 */
    public int Get_ThirdSkillNum(JSONObject SkillData){
        for (String key : SkillData.keySet()){
            if (key.equals("气血之力")){
                return SkillData.getJSONObject(key).size();
            }
        }
        return 0;
    }

    /* 计算升级到下一等级需要的经验 */
    public static int Get_NextLevelExp(int lv,int up){
        return ((lv * 1500) / 8) * up;
    }

    /* 距离下一等级经验百分比 */
    public double Get_NextLevelExpPercent(int exp, int up_exp){
        if (exp == 0){
            return 0.00;
        } else {
            return ((double) exp / (double) up_exp) * 100;
        }
    }

    /* 计算玩家战力 */
    public int Get_Ce(String UserData){
        JSONObject obj = JSONObject.parseObject(UserData);
        int HP = Integer.parseInt(obj.getJSONObject("userData").getString("生命"));
        int PR = Integer.parseInt(obj.getJSONObject("userData").getString("攻击"));
        int DE = Integer.parseInt(obj.getJSONObject("userData").getString("防御"));
        int SPEED = Integer.parseInt(obj.getJSONObject("userData").getString("速度"));
        int SP = Integer.parseInt(obj.getJSONObject("userData").getString("精神力"));
        return (int) ((0.12 * HP) + (0.75 * PR) + (0.12 * DE) + (0.12 * SPEED) + (0.1 * SP));
    }

    /* 获取消息跟随的内容 */
    public String Extract_plain_text(String msg,String Command){
        return msg.substring(Command.length()).trim();
    }

    /* 单概率计算 */
    public boolean Get_RandomRun(int probability){
        int index = Get_Random_Range(1,101);
        return index >= probability;
    }

    /* 判断字符串是否为数字 */
    public boolean Is_Number(String str){
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /* 生成随机6位数 */
    public static String Get_Random_Number(){
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0;i < 6;i++){
            sb.append(r.nextInt(10));
        }
        return sb.toString();
    }

    /* 获取当前时间 */
    public static String Get_NowTime(){
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * 将时间戳转换为时间,参数和返回值都是字符串
     * @param s
     * @return res
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = Long.parseLong(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /* 将yyyy-MM-dd HH:mm:ss转换为时间戳 */
    public static String DateToStamp(String s){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long ts = date.getTime();
        return String.valueOf(ts);
    }

    /* 计算秒级时间差 */
    public static int Get_Second_Diff(String time1,String time2){
        long t1 = Long.parseLong(time1);
        long t2 = Long.parseLong(time2);
        return (int) ((t1 - t2) / 1000);
    }

    /* 随机区间 */
    public static int Get_Random_Range(int min,int max){
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }

    /* 多概率合并计算 */
    public static String Get_Random_Range_More(String probability){
        JSONObject jsonObject = JSONObject.parseObject(probability);
        // 遍历jsonObject将所有value值加起来
        int sum = 0;
        for (String key : jsonObject.keySet()) {
            sum += jsonObject.getInteger(key);
        }
        // 随机数
        int random = Get_Random_Range(1,sum+1);
        // 根据随机数判断所在value概率区间，然后返回key值
        for (String key : jsonObject.keySet()) {
            if (random <= jsonObject.getInteger(key)) {
                return key;
            } else {
                random -= jsonObject.getInteger(key);
            }
        }
        return "";
    }

    public static String Get_Random_Range_More2(String probability){
        JSONObject jsonObject = JSONObject.parseObject(probability);
        List<String> list = new ArrayList<>();
        for (String key : jsonObject.keySet()) {
            int value = jsonObject.getInteger(key);
            for (int i = 0;i < value;i++) {
                list.add(key);
            }
        }
        int index = Get_Random_Range(0,list.size()-1);
        return list.get(index);
    }

    /* 复活扣除货币 */
    public static int Get_Revive_Cost(int level){
        return (int) (Math.pow(level,2));
    }

    /* 获取玩家第二武魂 */
    public static String ReverseAttribute(String Attribute,String UserData) {
        JSONObject map = JSONObject.parseObject(UserData);
        JSONObject UserInfo = map.getJSONObject("userInfo");
        String WuHunNum = UserInfo.getString("武魂数量");
        if (Attribute.equals("第一武魂")){
            if (WuHunNum.equals("1")){
                return "气血之力";
            }else {
                return "第二武魂";
            }
        } else if (Attribute.equals("第二武魂")) {
            return "第一武魂";
        }else {
            return "气血之力";
        }
    }
}
