package com.mryunqi.qimenbot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mryunqi.qimenbot.entity.Map;
import org.apache.ibatis.annotations.Mapper;

/**
 * (Map)表数据库访问层
 *
 * @author mryunqi
 * @since 2022-10-09 09:47:37
 */
@Mapper
public interface MapDao extends BaseMapper<Map> {

}

