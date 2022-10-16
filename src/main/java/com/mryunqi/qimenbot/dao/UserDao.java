package com.mryunqi.qimenbot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mryunqi.qimenbot.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * (User)表数据库访问层
 *
 * @author mryunqi
 * @since 2022-10-09 09:42:58
 */
@Mapper
public interface UserDao extends BaseMapper<User> {

}

