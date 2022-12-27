package com.mryunqi.qimenbot.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mryunqi.qimenbot.dao.WordnoticeDao;
import com.mryunqi.qimenbot.entity.User;
import com.mryunqi.qimenbot.entity.Wordnotice;
import com.mryunqi.qimenbot.service.WordnoticeService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author mryunqi
 * @date 2022/12/19
 */
@RequiredArgsConstructor
public class WordNoticeC {
    private final WordnoticeDao wordnoticeDao;
    private final WordnoticeService wordnoticeService;
    public String getOneWordNotice(){
        // 总记录数
        long count = wordnoticeService.count();
        if (count == 0){
            return "";
        }
        List<String> list;
        LambdaQueryWrapper<Wordnotice> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(Wordnotice::getNotice);
        list = wordnoticeDao.selectObjs(lambdaQueryWrapper).stream()
                .map(o -> (String) o)
                .collect(Collectors.toList());
        Random r = new Random();
        int index = r.nextInt((int) count);
        return "\n【世界公告】："+list.get(index);
    }
}
