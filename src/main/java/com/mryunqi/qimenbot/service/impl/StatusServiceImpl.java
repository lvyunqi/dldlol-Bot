package com.mryunqi.qimenbot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mryunqi.qimenbot.dao.StatusDao;
import com.mryunqi.qimenbot.entity.Status;
import com.mryunqi.qimenbot.service.StatusService;
import org.springframework.stereotype.Service;

/**
 * (Status)表服务实现类
 *
 * @author makejava
 * @since 2022-10-09 09:46:22
 */
@Service("statusService")
public class StatusServiceImpl extends ServiceImpl<StatusDao, Status> implements StatusService {

}

