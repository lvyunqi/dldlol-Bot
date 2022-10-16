package com.mryunqi.qimenbot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * (Hunshou)表实体类
 *
 * @author mryunqi
 * @since 2022-10-09 09:47:50
 */
@SuppressWarnings("serial")
@Data
@TableName(value = "hunshou")
public class Hunshou extends Model<Hunshou> {
    @TableId("hsname")
    private String hsname;
    
    private String data;
    
    private Long age;
    
    private String map;
    
    private Long exp;
    
    private String skill;
    //掉落物品
    private String dropItems;


/*    public String getHsname() {
        return hsname;
    }

    public void setHsname(String hsname) {
        this.hsname = hsname;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public Long getExp() {
        return exp;
    }

    public void setExp(Long exp) {
        this.exp = exp;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getDropItems() {
        return dropItems;
    }

    public void setDropItems(String dropItems) {
        this.dropItems = dropItems;
    }*/

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.hsname;
    }
    }

