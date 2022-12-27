package com.mryunqi.qimenbot.Model;

import com.alibaba.fastjson2.JSONObject;
import com.mryunqi.qimenbot.Util.code.ResponseResult;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Login {
    public static String Get_CommandAdmin(JdbcTemplate jct) {
        String sql = "SELECT admin FROM command WHERE id=1";
        return jct.queryForObject(sql, String.class);
    }

    public static ResponseResult<String> LoginMain(JdbcTemplate jct, String data, HttpServletRequest request, HttpServletResponse response) {
        String Admin = Get_CommandAdmin(jct);
        JSONObject json = JSONObject.parseObject(data);
        JSONObject AdminJson = JSONObject.parseObject(Admin);
        String username = json.getString("username");
        String password = json.getString("password");
        for (String key : AdminJson.keySet()) {
            if (key.equals(username)) {
                if (AdminJson.getString(key).equals(password)) {
                    HttpSession req =request.getSession();
                    String session_id = req.getId();
                    req.setAttribute("loginName", username);
                    Cookie cookie = new Cookie("JSESSIONID",session_id);
                    cookie.setMaxAge(60 * 60 * 24 * 30);
                    cookie.setPath("/");
                    response.addCookie(cookie);
//                    req.setAttribute("loginName", username);
                    return ResponseResult.ok("登录成功!");
                }
                return ResponseResult.fail("密码错误!");
            }
            return ResponseResult.fail("用户名无效!");
        }
        return ResponseResult.fail(ResponseResult.RespCode.LOGIN_FAIL);
    }
    //    获取管理员头像
    public static ResponseResult<String> LoginPng(JdbcTemplate jct,HttpServletRequest request) {
        String qq = (String) request.getSession().getAttribute("loginName");
        return ResponseResult.ok(qq);
    }
}
