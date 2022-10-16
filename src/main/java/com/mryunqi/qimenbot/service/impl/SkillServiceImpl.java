package com.mryunqi.qimenbot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mryunqi.qimenbot.dao.SkillDao;
import com.mryunqi.qimenbot.entity.Skill;
import com.mryunqi.qimenbot.service.SkillService;
import org.springframework.stereotype.Service;

/**
 * 技能(Skill)表服务实现类
 *
 * @author makejava
 * @since 2022-10-09 09:46:37
 */
@Service("skillService")
public class SkillServiceImpl extends ServiceImpl<SkillDao, Skill> implements SkillService {

}

