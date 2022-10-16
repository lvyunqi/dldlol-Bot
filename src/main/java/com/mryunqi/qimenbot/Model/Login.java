package com.mryunqi.qimenbot.Model;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;

public class Login {
    public static String Get_CommandAdmin(JdbcTemplate jct) {
        String sql = "SELECT admin FROM command WHERE id=1";
        return jct.queryForObject(sql, String.class);
    }

    public static String LoginMain(JdbcTemplate jct, String data) {
        String Admin = Get_CommandAdmin(jct);
        JSONObject json = JSONObject.parseObject(data);
        JSONObject AdminJson = JSONObject.parseObject(Admin);
        JSONObject ReturnJson = new JSONObject();
        String username = json.getString("username");
        String password = json.getString("password");
        for (String key : AdminJson.keySet()) {
            if (key.equals(username)) {
                if (AdminJson.getString(key).equals(password)) {
                    ReturnJson.put("code", 200);
                    ReturnJson.put("status", "success");
                    ReturnJson.put("msg", "登录成功");
                    return ReturnJson.toJSONString();
                }
                ReturnJson.put("code", 903);
                ReturnJson.put("status", "error");
                ReturnJson.put("msg", "密码错误!");
                return ReturnJson.toJSONString();
            }
            ReturnJson.put("code", 902);
            ReturnJson.put("status", "error");
            ReturnJson.put("msg", "用户名无效!");
            return ReturnJson.toJSONString();
        }
        ReturnJson.put("code", 901);
        ReturnJson.put("status", "error");
        ReturnJson.put("msg", "用户名或密码错误!");
        return ReturnJson.toJSONString();
    }
}
