package com.mryunqi.qimenbot.Model;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

public class SelectUser {
    public static String Get_UserAll(JdbcTemplate jct) {
        String sql = "SELECT * FROM user";
        for (Map.Entry<String, Object> entry : jct.queryForMap(sql).entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        return "";
    }
}
