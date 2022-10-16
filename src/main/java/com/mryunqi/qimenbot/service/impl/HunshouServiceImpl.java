package com.mryunqi.qimenbot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mryunqi.qimenbot.dao.HunshouDao;
import com.mryunqi.qimenbot.entity.Hunshou;
import com.mryunqi.qimenbot.service.HunshouService;
import org.springframework.stereotype.Service;

/**
 * (Hunshou)表服务实现类
 *
 * @author makejava
 * @since 2022-10-09 09:47:50
 */
@Service("hunshouService")
public class HunshouServiceImpl extends ServiceImpl<HunshouDao, Hunshou> implements HunshouService {

}

