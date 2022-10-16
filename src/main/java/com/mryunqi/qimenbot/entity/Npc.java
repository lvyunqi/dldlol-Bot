package com.mryunqi.qimenbot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * (Npc)表实体类
 *
 * @author mryunqi
 * @since 2022-10-09 09:47:21
 */
@SuppressWarnings("serial")
@Data
@TableName(value = "npc")
public class Npc extends Model<Npc> {
    @TableId("name")
    private String name;
    
    private String map;
    //任务
    private String task;
    //任务条件对话
    private String taskTalk;
    //日常对话
    private String dailyTalk;
    
    private String shop;


/*
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getTaskTalk() {
        return taskTalk;
    }

    public void setTaskTalk(String taskTalk) {
        this.taskTalk = taskTalk;
    }

    public String getDailyTalk() {
        return dailyTalk;
    }

    public void setDailyTalk(String dailyTalk) {
        this.dailyTalk = dailyTalk;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }
*/

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.name;
    }
    }

