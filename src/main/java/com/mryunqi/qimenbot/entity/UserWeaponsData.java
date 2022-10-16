package com.mryunqi.qimenbot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * (UserWeaponsData)表实体类
 *
 * @author mryunqi
 * @since 2022-10-09 09:45:21
 */
@SuppressWarnings("serial")
@Data
@TableName(value = "user_weapons_data")
public class UserWeaponsData extends Model<UserWeaponsData> {
    
    private Integer id;
    
    private Integer qq;
    
    private String name;
    
    private String dataInfo;


    /*public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQq() {
        return qq;
    }

    public void setQq(Integer qq) {
        this.qq = qq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataInfo() {
        return dataInfo;
    }

    public void setDataInfo(String dataInfo) {
        this.dataInfo = dataInfo;
    }*/

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

