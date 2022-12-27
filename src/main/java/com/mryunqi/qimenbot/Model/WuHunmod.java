package com.mryunqi.qimenbot.Model;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mryunqi.qimenbot.Util.code.ResponseResult;
import com.mryunqi.qimenbot.dao.WuhunDao;
import com.mryunqi.qimenbot.entity.Wuhun;
import java.util.List;

public class WuHunmod {
    public static ResponseResult<List<Wuhun>> GetWuhun(WuhunDao wuhunDao) {
        List<Wuhun> result = wuhunDao.selectList(null);
        return ResponseResult.ok(result);
    }
}
