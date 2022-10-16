package com.mryunqi.qimenbot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mryunqi.qimenbot.dao.MapDao;
import com.mryunqi.qimenbot.entity.Map;
import com.mryunqi.qimenbot.service.MapService;
import org.springframework.stereotype.Service;

/**
 * (Map)表服务实现类
 *
 * @author makejava
 * @since 2022-10-09 09:47:37
 */
@Service("mapService")
public class MapServiceImpl extends ServiceImpl<MapDao, Map> implements MapService {

}

