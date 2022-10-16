package com.mryunqi.qimenbot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mryunqi.qimenbot.entity.Fightdata;
import org.apache.ibatis.annotations.Mapper;

/**
 * (Fightdata)表数据库访问层
 *
 * @author mryunqi
 * @since 2022-10-09 09:49:15
 */
@Mapper
public interface FightdataDao extends BaseMapper<Fightdata> {

}

