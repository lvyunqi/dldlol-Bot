package com.mryunqi.qimenbot.Controller;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class GameHunshou {

    public static int Get_HunShouLevel(long Age){
        if (Age <= 99){
            return 1;
        } else if (Age <= 999) {
            return 2;
        } else if (Age <= 9999) {
            return 3;
        } else if (Age <= 99999) {
            return 4;
        } else if (Age <= 249999) {
            return 5;
        } else if (Age <= 9999999) {
            return 6;
        } else {
            return 7;
        }
    }
    public static String Get_HunShouAgeLevelName(long Age){
        int Level = Get_HunShouLevel(Age);
        switch (Level){
            case 1:
                return "白";
            case 2:
                return "黄";
            case 3:
                return "紫";
            case 4:
                return "黑";
            case 5:
                return "红";
            case 6:
                return "橙";
            case 7:
                return "金";
            default:
                return "彩";
        }
    }
    public String Get_Hunshou_Name_Template(JdbcTemplate jct, String NowMap) {
        String sql = "SELECT hsname From hunshou WHERE map = ?";
        List<Map<String,Object>> list = jct.queryForList(sql, NowMap);
        //如何为空，则返回空字符串
        if(list.isEmpty()){
            return "";
        }
        StringBuilder message = new StringBuilder();
        for (Map<String,Object> map : list){
            message.append(map.get("hsname")).append("|");
        }
        return message.substring(0,message.length()-1);
    }

    /* 判断魂兽是否在当前地图 */
    public boolean Is_Hunshou_In_Map(JdbcTemplate jct, String NowMap, String HunshouName) {
        String MapHunshou = Get_Hunshou_Name_Template(jct, NowMap);
        return MapHunshou.contains(HunshouName);
    }

    /* 获取魂兽的属性数据 */
    public String Get_HunshouData(JdbcTemplate jct, String HunshouName) {
        String sql = "SELECT data From hunshou WHERE hsname = ?";
        return jct.queryForObject(sql, String.class, HunshouName);
    }

    /* 判断魂兽是否有技能 */
    public boolean Is_Hunshou_Has_Skill(JdbcTemplate jct, String HunshouName) {
        String sql = "SELECT skill From hunshou WHERE hsname = ?";
        String skill = jct.queryForObject(sql, String.class, HunshouName);
        return skill != null;
    }

    /* 随机获取魂兽一个技能名 */
    public String Get_Hunshou_Random_Skill(JdbcTemplate jct, String HunshouName) {
        String sql = "SELECT skill From hunshou WHERE hsname = ?";
        String skill = jct.queryForObject(sql, String.class, HunshouName);
        if(skill == null){
            return "";
        }
        JSONObject obj = JSONObject.parseObject(skill);
        // 随机获取一个key返回
        String[] keys = obj.keySet().toArray(new String[0]);
        int index = (int) (Math.random() * keys.length);
        return keys[index];
    }
    /* 获取魂兽的技能数据 */
    public String Get_Hunshou_Skill_Data(JdbcTemplate jct, String HunshouName) {
        String sql = "SELECT skill From hunshou WHERE hsname = ?";
        String skill = jct.queryForObject(sql, String.class, HunshouName);
        if(skill == null){
            return "";
        }
        return skill;
    }

    /* 获取魂兽EXP */
    public int Get_Hunshou_EXP(JdbcTemplate jct, String HunshouName) {
        String sql = "SELECT exp From hunshou WHERE hsname = ?";
        return jct.queryForObject(sql, Integer.class, HunshouName);
    }

    /* 获取魂兽掉落物品 */
    public String Get_Hunshou_Drop_Item(JdbcTemplate jct, String HunshouName) {
        String sql = "SELECT drop_items From hunshou WHERE hsname = ?";
        return jct.queryForObject(sql, String.class, HunshouName);
    }

    /* 获取魂兽年龄 */
    public int Get_Hunshou_Age(JdbcTemplate jct, String HunshouName) {
        String sql = "SELECT age From hunshou WHERE hsname = ?";
        return jct.queryForObject(sql, Integer.class, HunshouName);
    }

    public String Get_RandomSkill(String SkillList){
        JSONObject obj = JSONObject.parseObject(SkillList);
        JSONObject skill = new JSONObject();
        // 遍历
        for (String key : obj.keySet()) {
            skill.put(key, obj.getJSONObject(key).getIntValue("rp"));
        }
        return Function.Get_Random_Range_More(String.valueOf(skill));
    }
}
