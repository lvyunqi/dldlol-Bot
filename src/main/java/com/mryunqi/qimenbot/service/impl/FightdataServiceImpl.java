package com.mryunqi.qimenbot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mryunqi.qimenbot.dao.FightdataDao;
import com.mryunqi.qimenbot.entity.Fightdata;
import com.mryunqi.qimenbot.service.FightdataService;
import org.springframework.stereotype.Service;

/**
 * (Fightdata)表服务实现类
 *
 * @author makejava
 * @since 2022-10-09 09:49:15
 */
@Service("fightdataService")
public class FightdataServiceImpl extends ServiceImpl<FightdataDao, Fightdata> implements FightdataService {

}

