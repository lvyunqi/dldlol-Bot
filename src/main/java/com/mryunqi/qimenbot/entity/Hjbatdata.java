package com.mryunqi.qimenbot.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * (Hjbatdata)表实体类
 *
 * @author mryunqi
 * @since 2022-10-09 09:48:35
 */
@SuppressWarnings("serial")
@Data
@TableName(value = "hjbatdata")
public class Hjbatdata extends Model<Hjbatdata> {
    
    private Integer id;
    
    private Long batid;
    
    private Long qq;
    //0为玩家1为魂兽
    private Integer ap;
    
    private String hjname;
    
    private Date startdate;


/*    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getBatid() {
        return batid;
    }

    public void setBatid(Long batid) {
        this.batid = batid;
    }

    public Long getQq() {
        return qq;
    }

    public void setQq(Long qq) {
        this.qq = qq;
    }

    public Integer getAp() {
        return ap;
    }

    public void setAp(Integer ap) {
        this.ap = ap;
    }

    public String getHjname() {
        return hjname;
    }

    public void setHjname(String hjname) {
        this.hjname = hjname;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }*/

}

