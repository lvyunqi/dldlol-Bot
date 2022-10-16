package com.mryunqi.qimenbot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mryunqi.qimenbot.entity.Npc;
import org.apache.ibatis.annotations.Mapper;

/**
 * (Npc)表数据库访问层
 *
 * @author mryunqi
 * @since 2022-10-09 09:47:21
 */
@Mapper
public interface NpcDao extends BaseMapper<Npc> {

}

