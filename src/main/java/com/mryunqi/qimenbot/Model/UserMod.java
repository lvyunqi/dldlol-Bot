package com.mryunqi.qimenbot.Model;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mryunqi.qimenbot.Util.ObjectUtil;
import com.mryunqi.qimenbot.Util.code.ResponseResult;
import com.mryunqi.qimenbot.dao.UserDao;
import com.mryunqi.qimenbot.entity.Hunshou;
import com.mryunqi.qimenbot.entity.User;
import com.mryunqi.qimenbot.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserMod {
    public static ResponseResult<List<Page<User>>> SelectUserModPagingMd(UserDao userDao, int num, int PageSize){
        Page<User> page = new Page<>(num,PageSize);
        List<Page<User>> result = new ArrayList<>();
        result.add(userDao.selectPage(page,null));
        return ResponseResult.ok(result);
    }
    public static ResponseResult<List<User>> UserqqToUserdata(UserDao userDao, Long qq){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("qq",qq);
        List<User> result =userDao.selectList(queryWrapper);
        return ResponseResult.ok(result);
    }

    public static ResponseResult<String> addPlayer(UserService userService,UserDao userDao, JSONObject data){
        long qq = data.getLong("qq");
        User player = userService.getById(qq);
        if(!ObjectUtil.isNull(player)){
            return ResponseResult.fail("该用户已注册！");
        }
        User newPlayer = new User();
        newPlayer.setQq(qq);
        newPlayer.setName(data.getString("name"));
        newPlayer.setSex(data.getString("sex"));
        userDao.insert(newPlayer);
        return ResponseResult.ok("添加成功！");
    }

}
