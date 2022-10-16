package com.mryunqi.qimenbot.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * (Fightdata)表实体类
 *
 * @author mryunqi
 * @since 2022-10-09 09:49:15
 */
@SuppressWarnings("serial")
@Data
@TableName(value = "fightdata")
public class Fightdata extends Model<Fightdata> {
    
    private Long id;
    
    private String hsname;
    
    private String data;
    
    private Date startdate;
    
    private Date lastdate;
    
    private Date enddate;
    
    private Integer bubat;
    
    private String diehp;
    
    private String status;
    //魂兽释放魂技数据
    private String hsskilldata;
    //玩家技能释放数据
    private String userskilldata;


/*    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHsname() {
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

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getLastdate() {
        return lastdate;
    }

    public void setLastdate(Date lastdate) {
        this.lastdate = lastdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public Integer getBubat() {
        return bubat;
    }

    public void setBubat(Integer bubat) {
        this.bubat = bubat;
    }

    public String getDiehp() {
        return diehp;
    }

    public void setDiehp(String diehp) {
        this.diehp = diehp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHsskilldata() {
        return hsskilldata;
    }

    public void setHsskilldata(String hsskilldata) {
        this.hsskilldata = hsskilldata;
    }

    public String getUserskilldata() {
        return userskilldata;
    }

    public void setUserskilldata(String userskilldata) {
        this.userskilldata = userskilldata;
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

