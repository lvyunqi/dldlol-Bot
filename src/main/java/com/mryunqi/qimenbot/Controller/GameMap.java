package com.mryunqi.qimenbot.Controller;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Objects;


public class GameMap {
    /* 获取当前地图周围的地图名 */
    public String Get_MapAroundName(JdbcTemplate jct, String NowMap) {
        String top = String.format("SELECT t From map WHERE map ='%s'", NowMap);
        String down = String.format("SELECT d From map WHERE map ='%s'", NowMap);
        String left = String.format("SELECT l From map WHERE map ='%s'", NowMap);
        String right = String.format("SELECT r From map WHERE map ='%s'", NowMap);
        StringBuilder message = new StringBuilder();
        String Top = jct.queryForObject(top, String.class);
        String Down = jct.queryForObject(down,String.class);
        String Left = jct.queryForObject(left,String.class);
        String Right = jct.queryForObject(right,String.class);
        if(!(Top == null)){
            message.append("上：").append(Top).append("\n");
        }
        if(!(Down == null)){
            message.append("下：").append(Down).append("\n");
        }
        if(!(Left == null)){
            message.append("左：").append(Left).append("\n");
        }
        if(!(Right == null)){
            message.append("右：").append(Right).append("\n");
        }
        return message.toString();
    }

    /* 获取当前地图是否支持传送 */
    public String Get_MapTransport(JdbcTemplate jct, String NowMap) {
        String sql = String.format("SELECT tp From map WHERE map ='%s'", NowMap);
        String message = jct.queryForObject(sql,String.class);
        if(Objects.equals(message, "1")){
            return "1";
        }
        return "";
    }

    /* 获取方向对应的地图名 */
    public String Get_MapName(JdbcTemplate jct, String NowMap, String Direction) {
        switch(Direction){
            case "上":
                return jct.queryForObject(String.format("SELECT t From map WHERE map ='%s'", NowMap),String.class);
            case "下":
                return jct.queryForObject(String.format("SELECT d From map WHERE map ='%s'", NowMap),String.class);
            case "左":
                return jct.queryForObject(String.format("SELECT l From map WHERE map ='%s'", NowMap),String.class);
            case "右":
                return jct.queryForObject(String.format("SELECT r From map WHERE map ='%s'", NowMap),String.class);
            default:
                return "";
        }
    }

}
