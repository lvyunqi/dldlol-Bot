package com.mryunqi.qimenbot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * (Wuhun)表实体类
 *
 * @author mryunqi
 * @since 2022-10-09 09:44:21
 */
@SuppressWarnings("serial")
@Data
@TableName(value = "wuhun")
public class Wuhun extends Model<Wuhun> {
    @TableId("name")
    private String name;
    
    private String category;
    
    private Integer pow;
    
    private Integer dodge;
    
    private Integer ct;
    
    private Integer ctp;
    
    private Integer cdadd;
    
    private Integer pr;
    
    private Integer de;
    
    private Integer mp;
    
    private Integer speed;
    
    private Integer sp;
    
    private String des;
    
    private String data;


    /*public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getPow() {
        return pow;
    }

    public void setPow(Integer pow) {
        this.pow = pow;
    }

    public Integer getDodge() {
        return dodge;
    }

    public void setDodge(Integer dodge) {
        this.dodge = dodge;
    }

    public Integer getCt() {
        return ct;
    }

    public void setCt(Integer ct) {
        this.ct = ct;
    }

    public Integer getCtp() {
        return ctp;
    }

    public void setCtp(Integer ctp) {
        this.ctp = ctp;
    }

    public Integer getCdadd() {
        return cdadd;
    }

    public void setCdadd(Integer cdadd) {
        this.cdadd = cdadd;
    }

    public Integer getPr() {
        return pr;
    }

    public void setPr(Integer pr) {
        this.pr = pr;
    }

    public Integer getDe() {
        return de;
    }

    public void setDe(Integer de) {
        this.de = de;
    }

    public Integer getMp() {
        return mp;
    }

    public void setMp(Integer mp) {
        this.mp = mp;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getSp() {
        return sp;
    }

    public void setSp(Integer sp) {
        this.sp = sp;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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

