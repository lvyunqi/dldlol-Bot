package com.mryunqi.qimenbot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mryunqi.qimenbot.dao.NpcDao;
import com.mryunqi.qimenbot.entity.Npc;
import com.mryunqi.qimenbot.service.NpcService;
import org.springframework.stereotype.Service;

/**
 * (Npc)表服务实现类
 *
 * @author makejava
 * @since 2022-10-09 09:47:21
 */
@Service("npcService")
public class NpcServiceImpl extends ServiceImpl<NpcDao, Npc> implements NpcService {

}

