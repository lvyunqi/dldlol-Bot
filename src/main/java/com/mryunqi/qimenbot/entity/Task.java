package com.mryunqi.qimenbot.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * (Task)表实体类
 *
 * @author mryunqi
 * @since 2022-12-26 15:45:02
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Task extends Model<Task> {
    
    private Integer id;
    
    private String name;
    //任务限制
    private String tasklimit;
    //完成某任务
    private String cat;
    //达到某级
    private Integer racl;
    //收集某物品
    private String ci;
    //击杀魂兽
    private String beat;
    //NPC对话
    private String npcd;
    //奖励
    private String reward;
    //任务说明
    private String details;


    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.id;
    }
    }

