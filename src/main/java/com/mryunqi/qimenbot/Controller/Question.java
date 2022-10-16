package com.mryunqi.qimenbot.Controller;

import org.springframework.jdbc.core.JdbcTemplate;

public class Question {
    private static String sql;

    private static String data;

    /**
     * 判断是否存在问答
     * @param jct
     * @return Boolean
     */
    public static Boolean Is_Question(JdbcTemplate jct){
        sql = "SELECT question FROM function WHERE 1";
        data = jct.queryForObject(sql,String.class);
        if (data != null){
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取数据
     * @param jct
     * @return String
     */
    public static String QuestionData(JdbcTemplate jct){
        sql = "SELECT question FROM function WHERE 1";
        return jct.queryForObject(sql,String.class);
    }

    /**
     * 获取题目
     * @param jct
     * @param id
     * @return String
     */
    public static String Get_Question(JdbcTemplate jct,String id){
        sql = "SELECT question FROM question WHERE id=?".format(id);
        return jct.queryForObject(sql,String.class);
    }

    /**
     * 获取答案
     * @param jct
     * @param id
     * @return String
     */
    public static String Get_Answer(JdbcTemplate jct,String id){
        sql = "SELECT question FROM answer WHERE id=?".format(id);
        return jct.queryForObject(sql,String.class);
    }

}
