package com.mryunqi.qimenbot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mryunqi.qimenbot.entity.Skill;
import org.apache.ibatis.annotations.Mapper;

/**
 * 技能(Skill)表数据库访问层
 *
 * @author mryunqi
 * @since 2022-10-09 09:46:37
 */
@Mapper
public interface SkillDao extends BaseMapper<Skill> {

}

