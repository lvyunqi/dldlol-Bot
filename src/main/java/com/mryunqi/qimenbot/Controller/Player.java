package com.mryunqi.qimenbot.Controller;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mryunqi.qimenbot.dao.UserDao;
import com.mryunqi.qimenbot.entity.User;
import com.mryunqi.qimenbot.entity.UserWeaponsData;
import com.mryunqi.qimenbot.service.UserService;
import com.mryunqi.qimenbot.service.UserWeaponsDataService;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class Player {
    public static JSONObject Split_Player_Attributes(UserService userService, UserWeaponsDataService userWeaponsDataService , int qq){
        QueryWrapper<User> playerWrapper = new QueryWrapper<>();
        playerWrapper.eq("qq",qq);
        User player = userService.getOne(playerWrapper);
        String userData = player.getStateInfo();
        JSONObject jsonUserData = JSONObject.parseObject(userData);
        if (StringUtils.isBlank(String.valueOf(player.getWeaponsAttack()))){
            JSONObject jsonPlayerData = jsonUserData.getJSONObject("userData");
            UserWeaponsData playerWeaponsData = userWeaponsDataService.getById(player.getWeaponsAttack());
            String WeaponsData = playerWeaponsData.getDataInfo();
            JSONObject jsonObject = JSONObject.parse(WeaponsData);
            JSONObject dataInfo = jsonObject.getJSONObject("dataInfo");
            jsonPlayerData.put("力量",jsonPlayerData.getLong("力量") - dataInfo.getLong("力量"));
            jsonPlayerData.put("攻击",jsonPlayerData.getLong("攻击") - dataInfo.getLong("攻击"));
            jsonPlayerData.put("生命",jsonPlayerData.getLong("生命") - dataInfo.getLong("血量"));
            jsonPlayerData.put("防御",jsonPlayerData.getLong("防御") - dataInfo.getLong("防御"));
            jsonPlayerData.put("速度",jsonPlayerData.getLong("速度") - dataInfo.getLong("速度"));
            jsonPlayerData.put("闪避",jsonPlayerData.getLong("闪避") - dataInfo.getLong("闪避"));
            jsonPlayerData.put("暴击率",jsonPlayerData.getLong("暴击率") - dataInfo.getLong("暴击率"));
            jsonPlayerData.put("暴击伤害",jsonPlayerData.getLong("暴击伤害") - dataInfo.getLong("暴击伤害"));
            jsonPlayerData.put("魂力值",jsonPlayerData.getLong("魂力值") - dataInfo.getLong("魂力值"));
            jsonUserData.put("userData",jsonPlayerData);
            return jsonUserData;
        }else if(StringUtils.isBlank(String.valueOf(player.getWeaponsDefence()))){
            JSONObject jsonPlayerData = jsonUserData.getJSONObject("userData");
            UserWeaponsData playerWeaponsData = userWeaponsDataService.getById(player.getWeaponsDefence());
            String WeaponsData = playerWeaponsData.getDataInfo();
            JSONObject jsonObject = JSONObject.parse(WeaponsData);
            JSONObject dataInfo = jsonObject.getJSONObject("dataInfo");
            jsonPlayerData.put("力量",jsonPlayerData.getLong("力量") - dataInfo.getLong("力量"));
            jsonPlayerData.put("攻击",jsonPlayerData.getLong("攻击") - dataInfo.getLong("攻击"));
            jsonPlayerData.put("生命",jsonPlayerData.getLong("生命") - dataInfo.getLong("血量"));
            jsonPlayerData.put("防御",jsonPlayerData.getLong("防御") - dataInfo.getLong("防御"));
            jsonPlayerData.put("速度",jsonPlayerData.getLong("速度") - dataInfo.getLong("速度"));
            jsonPlayerData.put("闪避",jsonPlayerData.getLong("闪避") - dataInfo.getLong("闪避"));
            jsonPlayerData.put("暴击率",jsonPlayerData.getLong("暴击率") - dataInfo.getLong("暴击率"));
            jsonPlayerData.put("暴击伤害",jsonPlayerData.getLong("暴击伤害") - dataInfo.getLong("暴击伤害"));
            jsonPlayerData.put("魂力值",jsonPlayerData.getLong("魂力值") - dataInfo.getLong("魂力值"));
            jsonUserData.put("userData",jsonPlayerData);
            return jsonUserData;
        }
        return jsonUserData;
    }

    public static void Add_GoodsTimesPerDay(@NotNull UserService userService, UserDao userDao, String GoodsName, int qq){
        QueryWrapper<User> playerWrapper = new QueryWrapper<>();
        playerWrapper.eq("qq",qq);
        User player = userService.getOne(playerWrapper);
        JSONObject PlayerGoodsDay;
        if (player.getGoodsData() == null){
            PlayerGoodsDay = new JSONObject();
            PlayerGoodsDay.put(GoodsName,1);
        }else {
            PlayerGoodsDay = JSONObject.parseObject(player.getGoodsData());
            if (PlayerGoodsDay.containsKey(GoodsName)){
                PlayerGoodsDay.put(GoodsName,PlayerGoodsDay.getLong(GoodsName)+1);
            }else {
                PlayerGoodsDay.put(GoodsName,1);
            }
        }
        UpdateWrapper<User> playerUpdateWrapper = new UpdateWrapper<>();
        playerUpdateWrapper.eq("qq",qq).set("goods_data",PlayerGoodsDay.toJSONString());
        userDao.update(null,playerUpdateWrapper);
    }

    // 获取所有玩家QQ
    public static List<Long> ALLPlayerQQ(UserDao userDao){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(User::getQq);
        return userDao.selectObjs(lambdaQueryWrapper).stream()
                .map(o -> (Long) o)
                .collect(Collectors.toList());
    }
}
