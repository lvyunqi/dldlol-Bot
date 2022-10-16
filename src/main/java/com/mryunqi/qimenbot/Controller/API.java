package com.mryunqi.qimenbot.Controller;

import com.alibaba.fastjson2.JSONObject;
import com.mryunqi.qimenbot.Model.Login;
import com.mryunqi.qimenbot.Model.SelectUser;
import com.mryunqi.qimenbot.Util.BaiduApiAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import static com.mryunqi.qimenbot.Controller.API_User.*;

@RestController
@SpringBootApplication
@RequestMapping(value = "/api")
public class API {
    @Autowired
    private Environment environment;
    private final JdbcTemplate jct;

    public API(JdbcTemplate jct) {
        this.jct = jct;
    }

    private String LocalApiKey(){
        return environment.getProperty("bot.apikey");
    }

    @RequestMapping(value = "/WebLogin",method = RequestMethod.POST)
    String getUserLoginPost(@RequestBody JSONObject data){
        return Login.LoginMain(jct, String.valueOf(data));
    }

    @RequestMapping(value = "/Get_User_All",method = RequestMethod.GET)
    String getUserAll(){
        return SelectUser.Get_UserAll(jct);
    }


    @RequestMapping(value = "/DataBaseSelect",method = RequestMethod.POST)
    String getDatabase_Select(@RequestBody JSONObject data){
        String token = BaiduApiAuth.getAuth();
        BaiduApiAuth.UpdateBaiduToken(jct, token);
        return "token";
    }

    /* User模块 */
    /* 获取全部已注册玩家QQ */
    @RequestMapping(value = "/user/user_list",method = RequestMethod.GET)
    String User_list(@RequestParam(value = "apikey") String apikey){
        return UserList(jct,apikey,LocalApiKey());
    }

    /* 获取指定玩家的数据 */
    @RequestMapping(value = "/user",method = RequestMethod.GET)
    String Select_Player_Data(@RequestParam(value = "apikey") String apikey,@RequestParam(value = "qq") String qq){
        return GetUserData(jct,apikey,LocalApiKey(),qq);
    }

    /* 判断指定玩家是否觉醒武魂*/
    @RequestMapping(value = "/user/awake",method = RequestMethod.GET)
    String Is_Player_Awake(@RequestParam(value = "apikey") String apikey,@RequestParam(value = "qq") String qq){
        return IsPlayerAwake(jct,apikey,LocalApiKey(),qq);
    }

}
