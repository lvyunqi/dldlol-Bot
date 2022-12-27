package com.mryunqi.qimenbot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * 物品(Goods)表实体类
 *
 * @author mryunqi
 * @since 2022-11-27 14:08:11
 */
@SuppressWarnings("serial")
@Data
@TableName(value = "goods")
public class Goods extends Model<Goods> {
    
    private Integer id;
    
    private String name;
    //效果范围
    private Integer effectRange;
    //使用场景
    private Integer useScenarios;
    //物品类型（是否可消耗）
    private Integer itemType;
    //能力提升类型
    private String ability;
    //提升量
    private String rise;
    //生命恢复
    private String hpRecovery;
    //生命储备
    private String hpStorage;
    //魂力恢复
    private String mpRecovery;
    //魂力储存
    private String mpStorage;
    //命中率（百分比）
    private Integer hitRate;
    //是否可交易（默认是）
    private Integer tradability;
    //是否可删除账号（默认否）
    private Integer deleteAccount;
    //每天使用次数（默认无限）
    private Integer timesPerDay;
    //附加状态（一个）
    private String additionalStatus;
    //自身附加状态（一个）
    private String selfStatus;
    //是否为定位物品（默认否）
    private Integer tpLocation;
    //是否为定位传送物品（默认否）
    private Integer tpLocationTp;
    //是否随机传送（默认否）
    private Integer tpRandom;
    //传送地图
    private String tp;
    //内涵物品
    private String goodsList;


/*
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEffectRange() {
        return effectRange;
    }

    public void setEffectRange(Integer effectRange) {
        this.effectRange = effectRange;
    }

    public Integer getUseScenarios() {
        return useScenarios;
    }

    public void setUseScenarios(Integer useScenarios) {
        this.useScenarios = useScenarios;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public String getRise() {
        return rise;
    }

    public void setRise(String rise) {
        this.rise = rise;
    }

    public String getHpRecovery() {
        return hpRecovery;
    }

    public void setHpRecovery(String hpRecovery) {
        this.hpRecovery = hpRecovery;
    }

    public String getHpStorage() {
        return hpStorage;
    }

    public void setHpStorage(String hpStorage) {
        this.hpStorage = hpStorage;
    }

    public String getMpRecovery() {
        return mpRecovery;
    }

    public void setMpRecovery(String mpRecovery) {
        this.mpRecovery = mpRecovery;
    }

    public String getMpStorage() {
        return mpStorage;
    }

    public void setMpStorage(String mpStorage) {
        this.mpStorage = mpStorage;
    }

    public Integer getHitRate() {
        return hitRate;
    }

    public void setHitRate(Integer hitRate) {
        this.hitRate = hitRate;
    }

    public Integer getTradability() {
        return tradability;
    }

    public void setTradability(Integer tradability) {
        this.tradability = tradability;
    }

    public Integer getDeleteAccount() {
        return deleteAccount;
    }

    public void setDeleteAccount(Integer deleteAccount) {
        this.deleteAccount = deleteAccount;
    }

    public Integer getTimesPerDay() {
        return timesPerDay;
    }

    public void setTimesPerDay(Integer timesPerDay) {
        this.timesPerDay = timesPerDay;
    }

    public String getAdditionalStatus() {
        return additionalStatus;
    }

    public void setAdditionalStatus(String additionalStatus) {
        this.additionalStatus = additionalStatus;
    }

    public String getSelfStatus() {
        return selfStatus;
    }

    public void setSelfStatus(String selfStatus) {
        this.selfStatus = selfStatus;
    }

    public Integer getTpLocation() {
        return tpLocation;
    }

    public void setTpLocation(Integer tpLocation) {
        this.tpLocation = tpLocation;
    }

    public Integer getTpLocationTp() {
        return tpLocationTp;
    }

    public void setTpLocationTp(Integer tpLocationTp) {
        this.tpLocationTp = tpLocationTp;
    }

    public Integer getTpRandom() {
        return tpRandom;
    }

    public void setTpRandom(Integer tpRandom) {
        this.tpRandom = tpRandom;
    }

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    public String getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(String goodsList) {
        this.goodsList = goodsList;
    }
*/

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

