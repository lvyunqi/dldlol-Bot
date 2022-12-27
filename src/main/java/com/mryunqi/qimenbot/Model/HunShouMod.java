package com.mryunqi.qimenbot.Model;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mryunqi.qimenbot.Util.code.ResponseResult;
import com.mryunqi.qimenbot.dao.HunshouDao;
import com.mryunqi.qimenbot.dao.UserDao;
import com.mryunqi.qimenbot.entity.Hunshou;
import com.mryunqi.qimenbot.entity.User;
import com.mryunqi.qimenbot.service.HunshouService;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.mryunqi.qimenbot.Controller.API_Auth.ApiKeyAuth;

public class HunShouMod {
    public static ResponseResult<List<String>> SelectAll(HunshouDao hunshouDao, String apikey, String LocalApiKey){
        if (ApiKeyAuth(apikey, LocalApiKey)){
            QueryWrapper<Hunshou> wrapper = new QueryWrapper<>();
            wrapper.select("hsname");
            List<Hunshou> hunshouList = hunshouDao.selectList(wrapper);
            List<String> resultList = new ArrayList<>();
            for (Hunshou hunshou: hunshouList){
                resultList.add(hunshou.getHsname());
            }
            return ResponseResult.ok(resultList);
        }else{
            return ResponseResult.fail(ResponseResult.RespCode.UNAUTHORIZED);
        }
    }

    public static ResponseResult<List<Page<Hunshou>>> SelectHunShouPagingMd(HunshouDao hunshouDao, int num, int PageSize){
        Page<Hunshou> page = new Page<>(num,PageSize);
        List<Page<Hunshou>> result = new ArrayList<>();
        result.add(hunshouDao.selectPage(page,null));
        return ResponseResult.ok(result);
    }
    public static ResponseResult<List<Hunshou>> HsnameToHsdata(HunshouDao hunshouDao, String name){
        QueryWrapper<Hunshou> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("hsname",name);
        List<Hunshou> result =hunshouDao.selectList(queryWrapper);
        return ResponseResult.ok(result);
    }

    public static ResponseResult<String> addHunshou(HunshouDao hunshouDao,JSONObject data){
        String hsname = data.getString("魂兽名");
        long age = data.getLong("年份");
        long exp = data.getLong("经验");
        String map = data.getString("出没地图");
        JSONObject hsData = data.getJSONObject("hsData");
        JSONArray skillData = data.getJSONArray("skillData");
        JSONObject jsonSkillData = new JSONObject();
        for (Object skillDatum : skillData) {
            JSONObject skill = (JSONObject) skillDatum;
            jsonSkillData.put(skill.getString("skill"), skill.remove("skill"));
        }
        Hunshou hunshou = new Hunshou();
        hunshou.setHsname(hsname);
        hunshou.setAge(age);
        hunshou.setExp(exp);
        hunshou.setMap(map);
        hunshou.setData(hsData.toJSONString());
        hunshou.setSkill(jsonSkillData.toJSONString());
        hunshouDao.insert(hunshou);
        return ResponseResult.ok("添加成功");
    }
}
