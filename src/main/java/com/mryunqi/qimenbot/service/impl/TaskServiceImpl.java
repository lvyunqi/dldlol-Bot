package com.mryunqi.qimenbot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mryunqi.qimenbot.dao.TaskDao;
import com.mryunqi.qimenbot.entity.Task;
import com.mryunqi.qimenbot.service.TaskService;
import org.springframework.stereotype.Service;

/**
 * (Task)表服务实现类
 *
 * @author mryunqi
 * @since 2022-12-26 15:45:02
 */
@Service("taskService")
public class TaskServiceImpl extends ServiceImpl<TaskDao, Task> implements TaskService {

}

