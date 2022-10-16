package com.mryunqi.qimenbot.Controller;

import org.springframework.jdbc.core.JdbcTemplate;

public class Command {

    public String Get_CommandAuthGroupList(JdbcTemplate jct) {
        String sql = "SELECT group_list FROM command WHERE id=1";
        return jct.queryForObject(sql, String.class);
    }

    public static String Get_CommandExpUp(JdbcTemplate jct) {
        String sql = "SELECT exp_up FROM command WHERE id=1";
        return jct.queryForObject(sql, String.class);
    }

    public String Get_CommandDefaultMap(JdbcTemplate jct) {
        String sql = "SELECT start_map FROM command WHERE id=1";
        return jct.queryForObject(sql, String.class);
    }

    public String Get_CommandDefaultCon(JdbcTemplate jct) {
        String sql = "SELECT con FROM command WHERE id=1";
        return jct.queryForObject(sql, String.class);
    }

    public String Get_CommandCurrencyList(JdbcTemplate jct) {
        String sql = "SELECT currency FROM command WHERE id=1";
        return jct.queryForObject(sql, String.class);
    }

    public String Get_CommandDefaultMoney(JdbcTemplate jct) {
        String sql = "SELECT init_money FROM command WHERE id=1";
        return jct.queryForObject(sql, String.class);
    }

    public String Get_CommandDefaultBackpack(JdbcTemplate jct) {
        String sql = "SELECT init_backpack FROM command WHERE id=1";
        return jct.queryForObject(sql, String.class);
    }

    public String Get_CommandSgin(JdbcTemplate jct) {
        String sql = "SELECT sgin_in FROM command WHERE id=1";
        return jct.queryForObject(sql, String.class);
    }

    public int Get_TwinsProbability(JdbcTemplate jct){
        String sql = "SELECT twinsp FROM command WHERE id=1";
        if (jct.queryForObject(sql, String.class) == null) return 0;
        return jct.queryForObject(sql, Integer.class);
    }

    /* 获取管理员 */
    public String Get_CommandAdmin(JdbcTemplate jct) {
        String sql = "SELECT admin FROM command WHERE id=1";
        return jct.queryForObject(sql, String.class);
    }

    public void Set_CommandAuthGroup(JdbcTemplate jct, String toJSONString) {
        String sql = "UPDATE command SET group_list=? WHERE id=1";
        jct.update(sql, toJSONString);
    }

    /* 判断是否全体禁止战斗 */
    public boolean Is_CommandForbidden(JdbcTemplate jct) {
        String sql = "SELECT nofight FROM command WHERE id=1";
        return jct.queryForObject(sql, String.class) != "0";
    }

    /* 获取复活扣除货币 */
    public static String Get_CommandReviveCurrency(JdbcTemplate jct) {
        String sql = "SELECT revive_currency FROM command WHERE id=1";
        return jct.queryForObject(sql, String.class);
    }

    /* 获取答题成功人数 */
    public static int Get_CommandQuestionPlayer(JdbcTemplate jct) {
        String sql = "SELECT question_player FROM command WHERE id=1";
        return jct.queryForObject(sql, Integer.class);
    }
}
