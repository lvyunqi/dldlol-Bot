package com.mryunqi.qimenbot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * (Map)表实体类
 *
 * @author mryunqi
 * @since 2022-10-09 09:47:37
 */
@SuppressWarnings("serial")
@Data
@TableName(value = "map")
public class Map extends Model<Map> {
    @TableId("map")
    private String map;
    
    private String t;
    
    private String d;
    
    private String l;
    
    private String r;
    
    private Integer tp;


/*    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public Integer getTp() {
        return tp;
    }

    public void setTp(Integer tp) {
        this.tp = tp;
    }*/

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.map;
    }
    }

