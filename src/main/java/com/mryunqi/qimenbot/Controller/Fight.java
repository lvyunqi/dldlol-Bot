package com.mryunqi.qimenbot.Controller;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mryunqi.qimenbot.dao.FightdataDao;
import com.mryunqi.qimenbot.entity.Fightdata;
import com.mryunqi.qimenbot.service.FightdataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

public class Fight {

    /* 获取PVE战斗ID对应的魂兽Name */
    public String Get_PVE_MonsterName(JdbcTemplate jct, int id) {
        String sql = String.format("SELECT hsname FROM fightdata WHERE id=%d", id);
        return jct.queryForObject(sql, String.class);
    }

    /* 创建战斗数据 */
    public void Create_FightData(JdbcTemplate jct, String hsname,String hsData, int id,String Time) {
        String sql = String.format("INSERT INTO fightdata (id, hsname,data,startdate,lastdate) VALUES (%d, '%s', '%s', '%s', '%s')", id, hsname,hsData, Time, Time);
        jct.execute(sql);
    }

    /* 获取战斗时魂兽属性 */
    public String Get_PVE_MonsterAttribute(JdbcTemplate jct, int id) {
        String sql = String.format("SELECT data FROM fightdata WHERE id=%d", id);
        return jct.queryForObject(sql, String.class);
    }

    /* 玩家暴击伤害计算 */
    public int Get_UserCritDamage(int UserAtk,int UserCritDamage,int UserPower,int UserSp,int HunShouDef) {
        int Atk = (int) ((UserAtk * ((1 + UserPower + UserSp) * 0.004) * (UserCritDamage * 0.01)) - (HunShouDef * 1.5));
        if (Atk <= 0) {
            Atk = 1;
        }
        return Atk;
    }

    /* 玩家伤害计算 */
    public int Get_UserDamage(int UserAtk,int UserPower,int UserSp,int HunShouDef) {
        int Atk = (int) ((UserAtk * ((1 + UserPower + UserSp) * 0.004)) - (HunShouDef * 1.5));
        if (Atk <= 0) {
            Atk = 1;
        }
        return Atk;
    }
    /* 计算闪避率 */
    public int Get_Dodge(int Dodge,int Speed) {
        return (int) (Dodge + (Speed * 0.08));
    }

    /* 获取上一次战斗的时间 */
    public String Get_LastFightTime(JdbcTemplate jct, int id) {
        String sql = String.format("SELECT lastdate FROM fightdata WHERE id=%d", id);
        try {
            return jct.queryForObject(sql, String.class);
        } catch (Exception e) {
            return "";
        }
    }

    /* 魂兽暴击伤害计算 */
    public int Get_HunShouCritDamage(int HunShouCritDamage,int HunShouPower,int UserDef) {
        int Atk = (int) (HunShouPower * 2.5 * (HunShouCritDamage * 0.01) - (UserDef * 1.5));
        if (Atk <= 0) {
            Atk = 1;
        }
        return Atk;
    }

    /* 魂兽普通攻击伤害计算 */
    public int Get_HunShouAttack(int HunShouPower,int UserDef){
        int Atk = (int) (HunShouPower * 2.5 - (UserDef * 1.5));
        if (Atk <= 0) {
            Atk = 1;
        }
        return Atk;
    }

    /* 更新魂兽战斗时属性 */
    public static void Update_HunShouAttribute(JdbcTemplate jct, int id, String data) {
        String sql = String.format("UPDATE fightdata SET data='%s' WHERE id=%d", data, id);
        jct.execute(sql);
    }

    /* 判断魂兽魂技是否存在冷却 */
    public boolean Is_HunShouSkill_CoolDown(JdbcTemplate jct, int id, String skill, int cd) {
        String sql = String.format("SELECT hsskilldata FROM fightdata WHERE id=%d", id);
        try {
            String data = jct.queryForObject(sql, String.class);
            if (data == null) {
                return false;
            }
            JSONObject json = JSONObject.parseObject(data);
            if (!json.containsKey(skill)) {
                return false;
            }
            String time = json.getString(skill);
            String NowTime = Function.Get_NowTime();
            int TimeCha = Function.Get_Second_Diff(NowTime, time);
            return TimeCha < cd;
        } catch (Exception e) {
            return false;
        }
    }

    /* 更新魂兽魂技冷却时间 */
    public void Update_HunShouSkill_CoolDown(JdbcTemplate jct, int id, String skill, String time) {
        JSONObject json = new JSONObject();
        json.put(skill, time);
        String data = json.toJSONString();
        String sql = String.format("UPDATE fightdata SET hsskilldata='%s' WHERE id=%d", data, id);
        jct.execute(sql);
    }

    /* 获取玩家战斗技能CD */
    public int Get_UserSkill_CoolDown(JdbcTemplate jct, int id, String skill) {
        String sql = String.format("SELECT userskilldata FROM fightdata WHERE id=%d", id);
        try {
            String data = jct.queryForObject(sql, String.class);
            if (data == null) {
                Update_UserSkill_CoolDown(jct, id, skill);
                return 9999999;
            }
            JSONObject json = JSONObject.parseObject(data);
            if (!json.containsKey(skill)) {
                Update_UserSkill_CoolDown(jct, id, skill);
                return 9999999;
            }
            String time = json.getString(skill);
            String NowTime = Function.Get_NowTime();
            return Function.Get_Second_Diff(NowTime, time);
        } catch (Exception e) {
            return 0;
        }
    }
    public void Set_UserSkill_CoolDown(FightdataService fightdataService,FightdataDao fightdataDao,int id,String skill){
        QueryWrapper<Fightdata> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        Fightdata fightdata = fightdataService.getOne(wrapper);
        String data = fightdata.getUserskilldata();
        if (!(StringUtils.isBlank(data))){
            JSONObject json = JSONObject.parseObject(data);
            if (json.containsKey(skill)) {
                json.remove(skill);
                fightdata.setUserskilldata(json.toJSONString());
                fightdataDao.updateById(fightdata);
            }
        }
    }

    /* 更新玩家战斗技能CD */
    public void Update_UserSkill_CoolDown(JdbcTemplate jct, int id, String skill) {
        JSONObject json = new JSONObject();
        json.put(skill, Function.Get_NowTime());
        String data = json.toJSONString();
        String sql = String.format("UPDATE fightdata SET userskilldata='%s' WHERE id=%d", data, id);
        jct.execute(sql);
    }

    /* 更新玩家攻击魂兽的时间 */
    public static void Update_FightLastdate(JdbcTemplate jct, int id) {
        String Time = Function.stampToDate(Function.Get_NowTime());
        String sql = String.format("UPDATE fightdata SET lastdate='%s' WHERE id=%d", Time, id);
        jct.execute(sql);
    }

    /* PVE结束战斗 */
    public void PVE_EndFight(JdbcTemplate jct, int id) {
        String Del = String.format("DELETE FROM fightdata WHERE id=%d", id);
        jct.execute(Del);
    }

    /* 设置PVE当前魂兽禁止战斗 */
    public static void PVE_Set_Forbid(JdbcTemplate jct, int id, int forbid) {
        String sql = String.format("UPDATE fightdata SET bubat=%d WHERE id=%d", forbid, id);
        jct.execute(sql);
    }

    /* 更新PVE当前魂兽指定属性增幅（状态） */
    public void PVE_Set_Attribute(JdbcTemplate jct, int id, double UpValue, String Attribute) {
        String FightData = Get_PVE_MonsterAttribute(jct, id);
        JSONObject json = JSONObject.parseObject(FightData);
        if (Attribute.equals("暴击率")) {
            json.put("暴击率", (int)(UpValue + (json.getInteger("暴击率"))));
        } else if (Attribute.equals("暴伤")) {
            json.put("暴伤", (int)(UpValue + (json.getInteger("暴伤"))));
        }else {
            json.put(Attribute, (int)(UpValue * (json.getInteger(Attribute))));
        }
        String data = json.toJSONString();
        String sql = String.format("UPDATE fightdata SET data='%s' WHERE id=%d", data, id);
        jct.execute(sql);
    }

    /* 获取PVE当前魂兽当前状态 */
    public String Get_PVE_MonsterStatus(JdbcTemplate jct, int id) {
        String sql = String.format("SELECT status FROM fightdata WHERE id=%d", id);
        try {
            return jct.queryForObject(sql, String.class);
        } catch (Exception e) {
            return null;
        }
    }
}
