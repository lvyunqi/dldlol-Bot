package com.mryunqi.qimenbot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * (Command)表实体类
 *
 * @author mryunqi
 * @since 2022-10-09 09:49:29
 */
@SuppressWarnings("serial")
@Data
@TableName(value = "command")
public class Command extends Model<Command> {
    
    private Integer id;
    //管理员
    private String admin;
    //群授权
    private String groupList;
    //经验增幅
    private Integer expUp;
    //初始地图
    private String startMap;
    //体力上限
    private Integer con;
    //币种
    private String currency;
    //初始货币数量
    private String initMoney;
    //初始背包（初始赠送，默认空）
    private String initBackpack;
    //签到奖励
    private String sginIn;
    //双生武魂概率
    private Integer twinsp;
    //全体禁止战斗，0允许1禁止
    private Integer nofight;
    //复活扣除的币种
    private String reviveCurrency;
    //答题成功人数上限
    private Integer questionPlayer;


/*    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getGroupList() {
        return groupList;
    }

    public void setGroupList(String groupList) {
        this.groupList = groupList;
    }

    public Integer getExpUp() {
        return expUp;
    }

    public void setExpUp(Integer expUp) {
        this.expUp = expUp;
    }

    public String getStartMap() {
        return startMap;
    }

    public void setStartMap(String startMap) {
        this.startMap = startMap;
    }

    public Integer getCon() {
        return con;
    }

    public void setCon(Integer con) {
        this.con = con;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getInitMoney() {
        return initMoney;
    }

    public void setInitMoney(String initMoney) {
        this.initMoney = initMoney;
    }

    public String getInitBackpack() {
        return initBackpack;
    }

    public void setInitBackpack(String initBackpack) {
        this.initBackpack = initBackpack;
    }

    public String getSginIn() {
        return sginIn;
    }

    public void setSginIn(String sginIn) {
        this.sginIn = sginIn;
    }

    public Integer getTwinsp() {
        return twinsp;
    }

    public void setTwinsp(Integer twinsp) {
        this.twinsp = twinsp;
    }

    public Integer getNofight() {
        return nofight;
    }

    public void setNofight(Integer nofight) {
        this.nofight = nofight;
    }

    public String getReviveCurrency() {
        return reviveCurrency;
    }

    public void setReviveCurrency(String reviveCurrency) {
        this.reviveCurrency = reviveCurrency;
    }

    public Integer getQuestionPlayer() {
        return questionPlayer;
    }

    public void setQuestionPlayer(Integer questionPlayer) {
        this.questionPlayer = questionPlayer;
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

