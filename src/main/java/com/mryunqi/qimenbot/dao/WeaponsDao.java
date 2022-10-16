package com.mryunqi.qimenbot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mryunqi.qimenbot.entity.Weapons;
import org.apache.ibatis.annotations.Mapper;

/**
 * (Weapons)表数据库访问层
 *
 * @author mryunqi
 * @since 2022-10-09 09:44:55
 */
@Mapper
public interface WeaponsDao extends BaseMapper<Weapons> {

}

