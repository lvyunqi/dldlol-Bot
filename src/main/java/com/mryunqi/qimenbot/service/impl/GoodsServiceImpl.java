package com.mryunqi.qimenbot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mryunqi.qimenbot.dao.GoodsDao;
import com.mryunqi.qimenbot.entity.Goods;
import com.mryunqi.qimenbot.service.GoodsService;
import org.springframework.stereotype.Service;

/**
 * 物品(Goods)表服务实现类
 *
 * @author mryunqi
 * @since 2022-11-27 14:08:11
 */
@Service("goodsService")
public class GoodsServiceImpl extends ServiceImpl<GoodsDao, Goods> implements GoodsService {

}

