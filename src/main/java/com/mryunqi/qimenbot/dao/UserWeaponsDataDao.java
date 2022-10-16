package com.mryunqi.qimenbot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mryunqi.qimenbot.entity.UserWeaponsData;
import org.apache.ibatis.annotations.Mapper;

/**
 * (UserWeaponsData)表数据库访问层
 *
 * @author mryunqi
 * @since 2022-10-09 09:45:21
 */
@Mapper
public interface UserWeaponsDataDao extends BaseMapper<UserWeaponsData> {

}

