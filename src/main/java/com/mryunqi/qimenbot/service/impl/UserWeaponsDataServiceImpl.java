package com.mryunqi.qimenbot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mryunqi.qimenbot.dao.UserWeaponsDataDao;
import com.mryunqi.qimenbot.entity.UserWeaponsData;
import com.mryunqi.qimenbot.service.UserWeaponsDataService;
import org.springframework.stereotype.Service;

/**
 * (UserWeaponsData)表服务实现类
 *
 * @author makejava
 * @since 2022-10-09 09:45:21
 */
@Service("userWeaponsDataService")
public class UserWeaponsDataServiceImpl extends ServiceImpl<UserWeaponsDataDao, UserWeaponsData> implements UserWeaponsDataService {

}

