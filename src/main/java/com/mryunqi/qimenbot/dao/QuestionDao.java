package com.mryunqi.qimenbot.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mryunqi.qimenbot.entity.Question;
import org.apache.ibatis.annotations.Mapper;

/**
 * (Question)表数据库访问层
 *
 * @author mryunqi
 * @since 2022-10-09 09:46:55
 */
@Mapper
public interface QuestionDao extends BaseMapper<Question> {

}

