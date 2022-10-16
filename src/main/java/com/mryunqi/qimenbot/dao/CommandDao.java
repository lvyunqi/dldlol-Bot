package com.mryunqi.qimenbot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mryunqi.qimenbot.entity.Command;
import org.apache.ibatis.annotations.Mapper;

/**
 * (Command)表数据库访问层
 *
 * @author mryunqi
 * @since 2022-10-09 09:49:29
 */
@Mapper
public interface CommandDao extends BaseMapper<Command> {

}

