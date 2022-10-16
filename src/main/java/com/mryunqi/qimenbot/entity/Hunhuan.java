package com.mryunqi.qimenbot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * (Hunhuan)表实体类
 *
 * @author mryunqi
 * @since 2022-10-09 09:48:03
 */
@SuppressWarnings("serial")
@Data
@TableName(value = "hunhuan")
public class Hunhuan extends Model<Hunhuan> {

    @TableId("qq")
    private Long qq;
    
    private Integer hhnum;
    
    private String hh1;
    
    private String hh2;
    
    private String hh3;
    
    private String hh4;
    
    private String hh5;
    
    private String hh6;
    
    private String hh7;
    
    private String hh8;
    
    private String hh9;
    
    private String hh10;


/*    public Long getQq() {
        return qq;
    }

    public void setQq(Long qq) {
        this.qq = qq;
    }

    public Integer getHhnum() {
        return hhnum;
    }

    public void setHhnum(Integer hhnum) {
        this.hhnum = hhnum;
    }

    public String getHh1() {
        return hh1;
    }

    public void setHh1(String hh1) {
        this.hh1 = hh1;
    }

    public String getHh2() {
        return hh2;
    }

    public void setHh2(String hh2) {
        this.hh2 = hh2;
    }

    public String getHh3() {
        return hh3;
    }

    public void setHh3(String hh3) {
        this.hh3 = hh3;
    }

    public String getHh4() {
        return hh4;
    }

    public void setHh4(String hh4) {
        this.hh4 = hh4;
    }

    public String getHh5() {
        return hh5;
    }

    public void setHh5(String hh5) {
        this.hh5 = hh5;
    }

    public String getHh6() {
        return hh6;
    }

    public void setHh6(String hh6) {
        this.hh6 = hh6;
    }

    public String getHh7() {
        return hh7;
    }

    public void setHh7(String hh7) {
        this.hh7 = hh7;
    }

    public String getHh8() {
        return hh8;
    }

    public void setHh8(String hh8) {
        this.hh8 = hh8;
    }

    public String getHh9() {
        return hh9;
    }

    public void setHh9(String hh9) {
        this.hh9 = hh9;
    }

    public String getHh10() {
        return hh10;
    }

    public void setHh10(String hh10) {
        this.hh10 = hh10;
    }*/

}

