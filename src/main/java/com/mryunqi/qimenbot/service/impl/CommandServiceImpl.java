package com.mryunqi.qimenbot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mryunqi.qimenbot.dao.CommandDao;
import com.mryunqi.qimenbot.entity.Command;
import com.mryunqi.qimenbot.service.CommandService;
import org.springframework.stereotype.Service;

/**
 * (Command)表服务实现类
 *
 * @author makejava
 * @since 2022-10-09 09:49:29
 */
@Service("commandService")
public class CommandServiceImpl extends ServiceImpl<CommandDao, Command> implements CommandService {

}

