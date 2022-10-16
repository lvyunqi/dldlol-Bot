package com.mryunqi.qimenbot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mryunqi.qimenbot.entity.Hunhuan;
import org.apache.ibatis.annotations.Mapper;

/**
 * (Hunhuan)表数据库访问层
 *
 * @author mryunqi
 * @since 2022-10-09 09:48:03
 */
@Mapper
public interface HunhuanDao extends BaseMapper<Hunhuan> {

}

