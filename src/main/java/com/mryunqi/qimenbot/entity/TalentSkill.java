package com.mryunqi.qimenbot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * (TalentSkill)表实体类
 *
 * @author makejava
 * @since 2022-10-09 09:45:54
 */
@SuppressWarnings("serial")
@Data
@TableName(value = "talent_skill")
public class TalentSkill extends Model<TalentSkill> {
    
    private Integer id;
    
    private String name;
    
    private Integer type;
    
    private String data;


    /*public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }*/

}

