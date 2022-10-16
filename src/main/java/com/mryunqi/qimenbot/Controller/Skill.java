package com.mryunqi.qimenbot.Controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;

public class Skill {
    /* 获取技能魂力消耗 */
    public String Get_SkillCost(@NotNull JdbcTemplate jct, String SkillName) {
        String sql = String.format("SELECT hjmp From skill WHERE hjname ='%s'", SkillName);
        return jct.queryForObject(sql,String.class);
    }

    /* 获取技能威力 */
    public String Get_SkillPower(@NotNull JdbcTemplate jct, String SkillName) {
        String sql = String.format("SELECT hjpr From skill WHERE hjname ='%s'", SkillName);
        return jct.queryForObject(sql,String.class);
    }

    /* 获取技能命中概率 */
    public String Get_SkillHit(@NotNull JdbcTemplate jct, String SkillName) {
        String sql = String.format("SELECT hjhr From skill WHERE hjname ='%s'", SkillName);
        return jct.queryForObject(sql,String.class);
    }

    /* 获取技能CD */
    public String Get_SkillCD(@NotNull JdbcTemplate jct, String SkillName) {
        String sql = String.format("SELECT hjcd From skill WHERE hjname ='%s'", SkillName);
        try {
            return jct.queryForObject(sql,String.class);
        } catch (Exception e) {
            return "0";
        }
    }

    /**
     * 获取技能描述
     * @return String
     */
    public String Get_SkillInfo(@NotNull JdbcTemplate jct, String SkillName){
        String sql = String.format("SELECT info From skill WHERE hjname ='%s'", SkillName);
        try {
            return jct.queryForObject(sql,String.class);
        } catch (Exception e) {
            return "";
        }
    }

    /* 获取技能释放范围 */
    public int Get_SkillAround(@NotNull JdbcTemplate jct,String SkillName){
        String sql = String.format("SELECT around From skill WHERE hjname ='%s'", SkillName);
        try {
            return jct.queryForObject(sql,Integer.class);
        } catch (Exception e) {
            return 0;
        }
    }


    /* 获取技能附加范围 */
    public String Get_SkillRange(@NotNull JdbcTemplate jct, String SkillName) {
        String sql = String.format("SELECT hjfjfw From skill WHERE hjname ='%s'", SkillName);
        return jct.queryForObject(sql,String.class);
    }

    /* 判断魂技是否有附加状态 */
    public boolean Is_SkillStatus(@NotNull JdbcTemplate jct, String SkillName) {
        String sql = String.format("SELECT hjfujia From skill WHERE hjname ='%s'", SkillName);
        // 判断是否为NULL
        String status = jct.queryForObject(sql,String.class);
        return status != null;
    }

    /* 魂技自身附加状态 */
    public String Get_SkillSelfStatus(@NotNull JdbcTemplate jct, String SkillName) {
        String sql = String.format("SELECT hjzsfujia From skill WHERE hjname ='%s'", SkillName);
        try {
            return jct.queryForObject(sql,String.class);
        } catch (Exception e) {
            return null;
        }
    }
    /* 魂技附加状态 */
    public String Get_SkillStatus(@NotNull JdbcTemplate jct, String SkillName) {
        String sql = String.format("SELECT hjfujia From skill WHERE hjname ='%s'", SkillName);
        try {
            return jct.queryForObject(sql,String.class);
        } catch (Exception e) {
            return null;
        }
    }
}
