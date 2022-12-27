package com.mryunqi.qimenbot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mryunqi.qimenbot.dao.WordnoticeDao;
import com.mryunqi.qimenbot.entity.Wordnotice;
import com.mryunqi.qimenbot.service.WordnoticeService;
import org.springframework.stereotype.Service;

/**
 * (Wordnotice)表服务实现类
 *
 * @author mryunqi
 * @since 2022-12-19 21:40:50
 */
@Service("wordnoticeService")
public class WordnoticeServiceImpl extends ServiceImpl<WordnoticeDao, Wordnotice> implements WordnoticeService {

}

