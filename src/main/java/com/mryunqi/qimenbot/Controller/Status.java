package com.mryunqi.qimenbot.Controller;

import com.alibaba.fastjson2.JSONObject;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;

public class Status {
    /* 获取状态是否允许战斗 */
    public boolean Is_StatusFight(@NotNull JdbcTemplate jct, String StatusName) {
        String sql = String.format("SELECT bubat From status WHERE ztname ='%s'", StatusName);
        try {
            int bubat = jct.queryForObject(sql,Integer.class);
            return bubat == 1;
        } catch (Exception e) {
            return false;
        }
    }
    /* 获取状态攻击增幅 */
    public int Get_StatusAttack(@NotNull JdbcTemplate jct, String StatusName) {
        String sql = String.format("SELECT pr From status WHERE ztname ='%s'", StatusName);
        try {
            return jct.queryForObject(sql,Integer.class);
        } catch (Exception e) {
            return 100;
        }
    }

    /* 获取状态力量增幅 */
    public int Get_StatusPower(@NotNull JdbcTemplate jct, String StatusName) {
        String sql = String.format("SELECT pow From status WHERE ztname ='%s'", StatusName);
        try {
            return jct.queryForObject(sql,Integer.class);
        } catch (Exception e) {
            return 100;
        }
    }

    /* 获取状态暴击率增幅 */
    public int Get_StatusCrit(@NotNull JdbcTemplate jct, String StatusName) {
        String sql = String.format("SELECT ct From status WHERE ztname ='%s'", StatusName);
        try {
            return jct.queryForObject(sql,Integer.class);
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
    }

    /* 获取状态暴击伤害增幅 */
    public int Get_StatusCritDamage(@NotNull JdbcTemplate jct, String StatusName) {
        String sql = String.format("SELECT ctp From status WHERE ztname ='%s'", StatusName);
        try {
            return jct.queryForObject(sql,Integer.class);
        } catch (Exception e) {
            return 0;
        }
    }

    /* 获取状态速度增幅 */
    public int Get_StatusSpeed(@NotNull JdbcTemplate jct, String StatusName) {
        String sql = String.format("SELECT speed From status WHERE ztname ='%s'", StatusName);
        try {
            return jct.queryForObject(sql,Integer.class);
        } catch (Exception e) {
            return 100;
        }
    }

    /* 获取状态防御增幅 */
    public int Get_StatusDefence(@NotNull JdbcTemplate jct, String StatusName) {
        String sql = String.format("SELECT de From status WHERE ztname ='%s'", StatusName);
        try {
            return jct.queryForObject(sql,Integer.class);
        } catch (Exception e) {
            return 100;
        }
    }

    /* 获取状态持续时间 */
    public int Get_StatusCD(@NotNull JdbcTemplate jct, String StatusName) {
        String sql = String.format("SELECT cd From status WHERE ztname ='%s'", StatusName);
        try {
            return jct.queryForObject(sql,Integer.class);
        } catch (Exception e) {
            return 0;
        }
    }

    /* PVE战斗附加状态 */
    public void Set_StatusPVE(@NotNull JdbcTemplate jct, String StatusName,int BattleID) {
        int UpPr = Get_StatusAttack(jct, StatusName);
        int UpPow = Get_StatusPower(jct, StatusName);
        int UpCt = Get_StatusCrit(jct, StatusName);
        int UpCtP = Get_StatusCritDamage(jct, StatusName);
        int UpSpeed = Get_StatusSpeed(jct, StatusName);
        int UpDe = Get_StatusDefence(jct, StatusName);
        if (Is_StatusFight(jct,StatusName)){
            Fight.PVE_Set_Forbid(jct,1,BattleID);
        }
        Fight fight = new Fight();
        fight.PVE_Set_Attribute(jct,BattleID, UpPow * 0.01,"力量");
        fight.PVE_Set_Attribute(jct,BattleID, UpCt,"暴击率");
        fight.PVE_Set_Attribute(jct,BattleID, UpCtP,"暴伤");
        fight.PVE_Set_Attribute(jct,BattleID, UpSpeed * 0.01,"速度");
        fight.PVE_Set_Attribute(jct,BattleID, UpDe * 0.01,"防御");
        String FightStatus = fight.Get_PVE_MonsterStatus(jct,BattleID);
        JSONObject json;
        if (FightStatus == null){
            json = new JSONObject();
        }else {
            json = JSONObject.parseObject(FightStatus);
        }
        json.put(StatusName,Function.Get_NowTime());
        String sql = String.format("UPDATE fightdata SET status = '%s' WHERE id = %d",json.toJSONString(),BattleID);
        jct.execute(sql);
    }
}
