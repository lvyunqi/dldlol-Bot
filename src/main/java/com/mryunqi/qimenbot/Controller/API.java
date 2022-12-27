package com.mryunqi.qimenbot.Controller;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mryunqi.qimenbot.Model.EditPwd;
import com.mryunqi.qimenbot.Model.Login;
import com.mryunqi.qimenbot.Util.code.ResponseResult;
import com.mryunqi.qimenbot.dao.*;
import com.mryunqi.qimenbot.entity.Hunshou;
import com.mryunqi.qimenbot.entity.User;
import com.mryunqi.qimenbot.entity.Wuhun;
import com.mryunqi.qimenbot.service.HunhuanService;
import com.mryunqi.qimenbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static com.mryunqi.qimenbot.Controller.API_User.*;
import static com.mryunqi.qimenbot.Model.HunShouMod.*;
import static com.mryunqi.qimenbot.Model.UserMod.*;
import static com.mryunqi.qimenbot.Model.WuHunmod.GetWuhun;

@RestController
@RequestMapping(value = "/api")
public class API {
    @Autowired
    private Environment environment;
    private final JdbcTemplate jct;
    private final CommandDao commandDao;
    private final HunhuanService hunhuanService;
    private final HunshouDao hunshouDao;
    private final UserDao userDao;
    private final UserService userService;
    private final WuhunDao wuhunDao;

    public API(JdbcTemplate jct, CommandDao commandDao, HunhuanService hunhuanService, HunshouDao hunshouDao, UserDao userDao, UserService userService, WuhunDao wuhunDao) {
        this.jct = jct;
        this.commandDao = commandDao;
        this.hunhuanService = hunhuanService;
        this.hunshouDao = hunshouDao;
        this.userDao = userDao;
        this.userService = userService;
        this.wuhunDao = wuhunDao;
    }

    private String LocalApiKey(){
        return environment.getProperty("bot.apikey");
    }

    @RequestMapping(value = "/WebLogin",method = RequestMethod.POST)
    ResponseResult<String> getUserLoginPost(@RequestBody JSONObject data, HttpServletRequest request, HttpServletResponse response) {
        return Login.LoginMain(jct, String.valueOf(data),request,response);
    }

    /*获取管理员头像模块*/
    @RequestMapping(value = "/LoginPng",method = RequestMethod.GET )
    ResponseResult<String> getLoginPng(HttpServletRequest request){
        return Login.LoginPng(jct, request);
    }

    /*修改密码*/
    @RequestMapping(value = "/EditPwd",method = RequestMethod.POST)
    ResponseResult<String> editpwd(@RequestBody JSONObject data, HttpServletRequest request){
       EditPwd editpwd = new EditPwd();
        return editpwd.Edit_pwd(jct,String.valueOf(data),request,commandDao);
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
    /* HunShou模块 */
    /*获取所有魂兽名*/
    @RequestMapping(value = "/hunshou/all-name",method = RequestMethod.GET)
    ResponseResult<List<String>> SelectAllHunShouName(@RequestParam(value = "apikey") String apikey){
        return SelectAll(hunshouDao,apikey,LocalApiKey());
    }
    /*分页查询魂兽*/
    @RequestMapping(value = "/hunshou/paging",method = RequestMethod.GET)
    ResponseResult<List<Page<Hunshou>>> SelectHunShouPaging(@RequestParam(value = "Page") int page, @RequestParam(value = "PageSize") int PageSize){
        return SelectHunShouPagingMd(hunshouDao,page,PageSize);
    }
    /*添加魂兽*/
    @RequestMapping(value = "/hunshou/add",method = RequestMethod.POST)
    ResponseResult<String> add_Hunshou(@RequestBody JSONObject data){
        System.out.println(data);
        return addHunshou(hunshouDao,data);
    }
    /*分页查询用户*/
    @RequestMapping(value = "/user/paging",method = RequestMethod.GET)
    ResponseResult<List<Page<User>>> SelectUserPaging(@RequestParam(value = "Page") int page, @RequestParam(value = "PageSize") int PageSize){
        return SelectUserModPagingMd(userDao,page,PageSize);
    }
    /*添加玩家*/
    @RequestMapping(value = "/user/add",method = RequestMethod.POST)
    ResponseResult<String> add_Player(@RequestBody JSONObject data){
        return addPlayer(userService,userDao,data);
    }
    /*qq查询用户*/
    @RequestMapping(value = "/Search/user",method = RequestMethod.GET)
    ResponseResult<List<User>> SelectUser(@RequestParam(value = "qq") Long qq){
        return UserqqToUserdata(userDao,qq);
    }
    /*hsname查询hs*/
    @RequestMapping(value = "/Search/hunshou",method = RequestMethod.GET)
    ResponseResult<List<Hunshou>> Selecthsname(@RequestParam(value = "name") String name){
        return HsnameToHsdata(hunshouDao,name);
    }
    @RequestMapping(value = "/wuhun/list",method = RequestMethod.GET)
    ResponseResult<List<Wuhun>> getwuhun(){
        return GetWuhun(wuhunDao);
    }



}
