package com.mryunqi.qimenbot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * 技能(Skill)表实体类
 *
 * @author mryunqi
 * @since 2022-10-09 09:46:37
 */
@SuppressWarnings("serial")
@Data
@TableName(value = "skill")
public class Skill extends Model<Skill> {
    @TableId("hjname")
    private String hjname;
    
    private String info;
    //0=敌全体 1=己方单体 2=己方全体
    private Integer around;
    
    private Integer hjmp;
    
    private Integer hjpr;
    
    private Integer hjhr;
    
    private String hjfujia;
    //魂技自身附加
    private String hjzsfujia;
    
    private Integer hjcd;
    //0=主动1=被动
    private Integer hjtyp;
    //0=不附加1=附加
    private Integer hjsffujia;
    //0=附加敌方1=附加自己
    private Integer hjfjfw;
    
    private String yearbuff;


    /*public String getHjname() {
        return hjname;
    }

    public void setHjname(String hjname) {
        this.hjname = hjname;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getAround() {
        return around;
    }

    public void setAround(Integer around) {
        this.around = around;
    }

    public Integer getHjmp() {
        return hjmp;
    }

    public void setHjmp(Integer hjmp) {
        this.hjmp = hjmp;
    }

    public Integer getHjpr() {
        return hjpr;
    }

    public void setHjpr(Integer hjpr) {
        this.hjpr = hjpr;
    }

    public Integer getHjhr() {
        return hjhr;
    }

    public void setHjhr(Integer hjhr) {
        this.hjhr = hjhr;
    }

    public String getHjfujia() {
        return hjfujia;
    }

    public void setHjfujia(String hjfujia) {
        this.hjfujia = hjfujia;
    }

    public String getHjzsfujia() {
        return hjzsfujia;
    }

    public void setHjzsfujia(String hjzsfujia) {
        this.hjzsfujia = hjzsfujia;
    }

    public Integer getHjcd() {
        return hjcd;
    }

    public void setHjcd(Integer hjcd) {
        this.hjcd = hjcd;
    }

    public Integer getHjtyp() {
        return hjtyp;
    }

    public void setHjtyp(Integer hjtyp) {
        this.hjtyp = hjtyp;
    }

    public Integer getHjsffujia() {
        return hjsffujia;
    }

    public void setHjsffujia(Integer hjsffujia) {
        this.hjsffujia = hjsffujia;
    }

    public Integer getHjfjfw() {
        return hjfjfw;
    }

    public void setHjfjfw(Integer hjfjfw) {
        this.hjfjfw = hjfjfw;
    }

    public String getYearbuff() {
        return yearbuff;
    }

    public void setYearbuff(String yearbuff) {
        this.yearbuff = yearbuff;
    }*/

}

