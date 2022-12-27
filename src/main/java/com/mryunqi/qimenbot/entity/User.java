package com.mryunqi.qimenbot.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * (User)表实体类
 *
 * @author mryunqi
 * @since 2022-10-09 09:43:01
 */
@SuppressWarnings("serial")
@Data
@TableName(value = "user")
public class User extends Model<User> {
    @TableId("qq")
    private Long qq;
    
    private String name;
    
    private String sex;
    
    private String nowmap;
    //禁止战斗
    private Integer nofight;
    
    private String batid;
    //附加状态
    private String status;
    
    private Date ztstartdate;
    
    private String skill;
    
    private String stateInfo;
    
    private String backpack;
    
    private String money;
    //攻击魂导器
    private Integer weaponsAttack;
    //防御魂导器
    private Integer weaponsDefence;
    //签到天数
    private String sginInDay;
    
    private String team;
    //天赋技能
    private String talent;
    
    private String task;
    //快捷指令
    private String alias;
    //临时魂环存放
    private String temporarySkill;
    //原始魂环
    private String skillData;
    private String goodsData;
    private String npc;
    private String taskData;


/*
    public Long getQq() {
        return qq;
    }

    public void setQq(Long qq) {
        this.qq = qq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNowmap() {
        return nowmap;
    }

    public void setNowmap(String nowmap) {
        this.nowmap = nowmap;
    }

    public Integer getNofight() {
        return nofight;
    }

    public void setNofight(Integer nofight) {
        this.nofight = nofight;
    }

    public String getBatid() {
        return batid;
    }

    public void setBatid(String batid) {
        this.batid = batid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getZtstartdate() {
        return ztstartdate;
    }

    public void setZtstartdate(Date ztstartdate) {
        this.ztstartdate = ztstartdate;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public String getBackpack() {
        return backpack;
    }

    public void setBackpack(String backpack) {
        this.backpack = backpack;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public Integer getWeaponsAttack() {
        return weaponsAttack;
    }

    public void setWeaponsAttack(Integer weaponsAttack) {
        this.weaponsAttack = weaponsAttack;
    }

    public Integer getWeaponsDefence() {
        return weaponsDefence;
    }

    public void setWeaponsDefence(Integer weaponsDefence) {
        this.weaponsDefence = weaponsDefence;
    }

    public String getSginInDay() {
        return sginInDay;
    }

    public void setSginInDay(String sginInDay) {
        this.sginInDay = sginInDay;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getTalent() {
        return talent;
    }

    public void setTalent(String talent) {
        this.talent = talent;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getTemporarySkill() {
        return temporarySkill;
    }

    public void setTemporarySkill(String temporarySkill) {
        this.temporarySkill = temporarySkill;
    }

    public String getSkillData() {
        return skillData;
    }

    public void setSkillData(String skillData) {
        this.skillData = skillData;
    }
*/

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.qq;
    }
    }

