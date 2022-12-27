package com.mryunqi.qimenbot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * (Wordnotice)表实体类
 *
 * @author mryunqi
 * @since 2022-12-19 21:40:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "wordNotice")
public class Wordnotice extends Model<Wordnotice> {
    
    private Integer id;
    //公告内容
    private String notice;


    public Integer getId() {
        return id;
    }


    public String getNotice() {
        return notice;
    }


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

