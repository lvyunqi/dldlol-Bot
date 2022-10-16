package com.mryunqi.qimenbot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mryunqi.qimenbot.entity.Status;
import org.apache.ibatis.annotations.Mapper;

/**
 * (Status)表数据库访问层
 *
 * @author mryunqi
 * @since 2022-10-09 09:46:22
 */
@Mapper
public interface StatusDao extends BaseMapper<Status> {

}

