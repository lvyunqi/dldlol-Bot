package com.mryunqi.qimenbot.Model;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mryunqi.qimenbot.Util.code.ResponseResult;
import com.mryunqi.qimenbot.dao.CommandDao;
import com.mryunqi.qimenbot.entity.Command;
import com.mryunqi.qimenbot.service.CommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static com.mryunqi.qimenbot.Model.Login.Get_CommandAdmin;

@Slf4j
@Component
public class EditPwd {

    public ResponseResult<String> Edit_pwd(JdbcTemplate jct, String data, HttpServletRequest request,CommandDao commandDao) {
        String Admin = Get_CommandAdmin(jct);
        String username = (String) request.getSession().getAttribute("loginName");
        JSONObject json = JSONObject.parseObject(data);
        JSONObject AdminJson = JSONObject.parseObject(Admin);
        String password = json.getString("password");
        String nwe_pwd = json.getString("nwe_password");
        System.out.println(nwe_pwd);
        for (String key : AdminJson.keySet()) {
            if (key.equals(username)) {
                if (AdminJson.getString(key).equals(password)) {
                    String pwdjson = String.format("{\"%s\":\"%s\"}",key,nwe_pwd);
                    UpdateWrapper<Command> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("id",1).set("admin",pwdjson);
                    commandDao.update(null,updateWrapper);
                    log.info("密码修改成功");
                    return ResponseResult.ok("修改成功!");
                }
                return ResponseResult.fail("密码错误!");
            }
            return ResponseResult.fail("用户名无效!");
        }
        return ResponseResult.fail(ResponseResult.RespCode.LOGIN_FAIL);
    }



}

