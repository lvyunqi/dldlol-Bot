package com.mryunqi.qimenbot.Controller;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class GameNPC {
    public String get_NPC_Name_Template(JdbcTemplate jct, String NowMap) {
        String sql = "SELECT name From npc WHERE map = ?";
        List<Map<String,Object>> list = jct.queryForList(sql, NowMap);
        StringBuilder message = new StringBuilder();
        for (Map<String,Object> map : list){
            message.append(map.get("name")).append("|");
        }
        if(message.length() == 0){
            return "";
        }
        return message.substring(0,message.length()-1);
    }
}
