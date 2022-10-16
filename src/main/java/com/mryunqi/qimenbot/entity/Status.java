package com.mryunqi.qimenbot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * (Status)表实体类
 *
 * @author mryunqi
 * @since 2022-10-09 09:46:22
 */
@SuppressWarnings("serial")
@Data
@TableName(value = "status")
public class Status extends Model<Status> {
    @TableId("ztname")
    private String ztname;
    
    private Integer bubat;
    
    private Integer pr;
    
    private Integer pow;
    
    private Integer ct;
    
    private Integer ctp;
    
    private Integer speed;
    
    private Integer de;
    
    private Integer diehp;
    
    private Integer cd;
    
    private String info;


   /* public String getZtname() {
        return ztname;
    }

    public void setZtname(String ztname) {
        this.ztname = ztname;
    }

    public Integer getBubat() {
        return bubat;
    }

    public void setBubat(Integer bubat) {
        this.bubat = bubat;
    }

    public Long getPr() {
        return pr;
    }

    public void setPr(Long pr) {
        this.pr = pr;
    }

    public Long getPow() {
        return pow;
    }

    public void setPow(Long pow) {
        this.pow = pow;
    }

    public Long getCt() {
        return ct;
    }

    public void setCt(Long ct) {
        this.ct = ct;
    }

    public Long getCtp() {
        return ctp;
    }

    public void setCtp(Long ctp) {
        this.ctp = ctp;
    }

    public Long getSpeed() {
        return speed;
    }

    public void setSpeed(Long speed) {
        this.speed = speed;
    }

    public Long getDe() {
        return de;
    }

    public void setDe(Long de) {
        this.de = de;
    }

    public Long getDiehp() {
        return diehp;
    }

    public void setDiehp(Long diehp) {
        this.diehp = diehp;
    }

    public Integer getCd() {
        return cd;
    }

    public void setCd(Integer cd) {
        this.cd = cd;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }*/

}

