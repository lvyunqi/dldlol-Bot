package com.mryunqi.qimenbot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * (Weapons)表实体类
 *
 * @author mryunqi
 * @since 2022-10-09 09:44:55
 */
@SuppressWarnings("serial")
@Data
@TableName(value = "weapons")
public class Weapons extends Model<Weapons> {
    @TableId("name")
    private String name;
    //类型
    private Integer type;
    //品阶
    private String grade;
    //突破素材
    private String breach;
    //说明
    private String info;
    //数据信息
    private String dataInfo;
    //等级限制
    private Integer gradeLimit;
    //封印需要物品
    private String sealGoods;


    /*public String getName() {
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getBreach() {
        return breach;
    }

    public void setBreach(String breach) {
        this.breach = breach;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDataInfo() {
        return dataInfo;
    }

    public void setDataInfo(String dataInfo) {
        this.dataInfo = dataInfo;
    }

    public Integer getGradeLimit() {
        return gradeLimit;
    }

    public void setGradeLimit(Integer gradeLimit) {
        this.gradeLimit = gradeLimit;
    }

    public String getSealGoods() {
        return sealGoods;
    }

    public void setSealGoods(String sealGoods) {
        this.sealGoods = sealGoods;
    }*/

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

