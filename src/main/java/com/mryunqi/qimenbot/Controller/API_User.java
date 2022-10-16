package com.mryunqi.qimenbot.Controller;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class API_User {
    public static String UserList(JdbcTemplate jct, String ApiKey,String LocalApiKey){
        API_Auth apiAuth = new API_Auth();
        JSONObject result = new JSONObject();
        try{
            if (apiAuth.ApiKeyAuth(ApiKey,LocalApiKey)){
                String sql = "SELECT qq FROM user";
                List<String> list = jct.queryForList(sql, String.class);
                result.put("status",200);
                result.put("data",list);
                return result.toString();
            }
            if (!apiAuth.ApiKeyAuth(ApiKey,LocalApiKey)){
                result.put("status",403);
                result.put("error","权限不足");
                return result.toString();
            }
        } catch (Exception e){
            result.put("status",500);
            result.put("error",e);
            return result.toString();
        }
        result.put("status",501);
        result.put("error","未知错误");
        return result.toString();
    }

    public static String GetUserData(JdbcTemplate jct, String ApiKey,String LocalApiKey,String QQ){
        API_Auth apiAuth = new API_Auth();
        JSONObject result = new JSONObject();
        try{
            if (apiAuth.ApiKeyAuth(ApiKey,LocalApiKey)){
                String sql = "SELECT state_info FROM user WHERE qq=?";
                String data = jct.queryForObject(sql,String.class, QQ);
                JSONObject obj = JSONObject.parseObject(data);
                result.put("status",200);
                result.put("data",obj);
                return result.toString();
            }
            if (!apiAuth.ApiKeyAuth(ApiKey,LocalApiKey)){
                result.put("status",403);
                result.put("error","权限不足");
                return result.toString();
            }
        } catch (Exception e){
            result.put("status",500);
            result.put("error",e);
            return result.toString();
        }
        result.put("status",501);
        result.put("error","未知错误");
        return result.toString();
    }

    public static String IsPlayerAwake(JdbcTemplate jct, String ApiKey,String LocalApiKey,String QQ){
        API_Auth apiAuth = new API_Auth();
        JSONObject result = new JSONObject();
        try{
            if (apiAuth.ApiKeyAuth(ApiKey,LocalApiKey)){
                String sql = "SELECT state_info FROM user WHERE qq=" + QQ;
                String data = jct.queryForObject(sql,String.class);
                result.put("status",200);
                if (!(data == null)){
                    result.put("data",false);
                }else {
                    result.put("data",true);
                }
                return result.toString();
            }
            if (!apiAuth.ApiKeyAuth(ApiKey,LocalApiKey)){
                result.put("status",403);
                result.put("error","权限不足");
                return result.toString();
            }
        } catch (Exception e){
            result.put("status",500);
            result.put("error",e);
            return result.toString();
        }
        result.put("status",501);
        result.put("error","未知错误");
        return result.toString();
    }
}
