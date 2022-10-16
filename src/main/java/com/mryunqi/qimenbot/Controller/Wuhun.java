package com.mryunqi.qimenbot.Controller;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

public class Wuhun {
    /* 获取所有武魂名 */
    public String Get_SkillRandomName(JdbcTemplate jct){
        String sql = "SELECT name From wuhun";
        List<Map<String,Object>> list = jct.queryForList(sql);
        List<String> data = new ArrayList<>();
        for (Map<String,Object> map : list){
            data.add((String) map.get("name"));
        }
        Random r = new Random();
        int num = r.nextInt(data.size());
        return data.get(num);
    }

    /* 获取武魂属性数据 */
    public String Get_SkillData(JdbcTemplate jct,String name){
        String sql = "SELECT data FROM wuhun WHERE name = ?";
        return jct.queryForObject(sql, String.class,name);
    }
}
