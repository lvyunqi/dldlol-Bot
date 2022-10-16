package com.mryunqi.qimenbot.Controller;

import org.springframework.jdbc.core.JdbcTemplate;

public class Weapons {
    /* 查询魂导器类型 */
    public String Get_WeaponsType(JdbcTemplate jct,String WeaponsName){
        String sql = "SELECT type FROM `weapons` WHERE `name` = '"+WeaponsName+"'";
        return jct.queryForObject(sql,String.class);
    }



}
