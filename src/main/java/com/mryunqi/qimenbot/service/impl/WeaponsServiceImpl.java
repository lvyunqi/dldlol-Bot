package com.mryunqi.qimenbot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mryunqi.qimenbot.dao.WeaponsDao;
import com.mryunqi.qimenbot.entity.Weapons;
import com.mryunqi.qimenbot.service.WeaponsService;
import org.springframework.stereotype.Service;

/**
 * (Weapons)表服务实现类
 *
 * @author makejava
 * @since 2022-10-09 09:44:55
 */
@Service("weaponsService")
public class WeaponsServiceImpl extends ServiceImpl<WeaponsDao, Weapons> implements WeaponsService {

}

