package com.mryunqi.qimenbot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mryunqi.qimenbot.dao.WuhunDao;
import com.mryunqi.qimenbot.entity.Wuhun;
import com.mryunqi.qimenbot.service.WuhunService;
import org.springframework.stereotype.Service;

/**
 * (Wuhun)表服务实现类
 *
 * @author makejava
 * @since 2022-10-09 09:44:21
 */
@Service("wuhunService")
public class WuhunServiceImpl extends ServiceImpl<WuhunDao, Wuhun> implements WuhunService {

}

