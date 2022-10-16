package com.mryunqi.qimenbot.Controller;

import com.mysql.cj.xdevapi.Table;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataBaseMain {
    private static JdbcTemplate jdbcTemplate;
    private static Logger log = LoggerFactory.getLogger(DataBaseMain.class);
    private final Environment environment;

    public static String sql;

    public DataBaseMain(JdbcTemplate jdbcTemplate, Environment environment) {
        DataBaseMain.jdbcTemplate = jdbcTemplate;
        this.environment = environment;
    }

    public static Boolean is_CreateTable(String Table){
        String sql = "SHOW TABLES LIKE '"+Table+"'";
        Boolean Is_User = jdbcTemplate.query(sql, null, null, new ResultSetExtractor<Boolean>(){
            @Override
            public Boolean extractData(ResultSet rs) throws SQLException, DataAccessException {
                return rs.next();
            }
        });
        return Is_User;
    }

    public static @NotNull Boolean CreateTable(String Table){
        String sql = "";
        if (("user").equals(Table)){
            sql = "CREATE TABLE `user` (\n" +
                    "  `qq` int(11) NOT NULL,\n" +
                    "  `name` varchar(18) NOT NULL,\n" +
                    "  `sex` varchar(3) NOT NULL,\n" +
                    "  `nowmap` varchar(255) DEFAULT NULL,\n" +
                    "  `nofight` int(255) DEFAULT NULL,\n" +
                    "  `batid` json DEFAULT NULL,\n" +
                    "  `status` json DEFAULT NULL,\n" +
                    "  `ztstartdate` datetime DEFAULT NULL,\n" +
                    "  `skill` json DEFAULT NULL,\n" +
                    "  `state_info` json DEFAULT NULL,\n" +
                    "  `backpack` json DEFAULT NULL,\n" +
                    "  `money` json DEFAULT NULL,\n" +
                    "  `weapons_attack` int(255) DEFAULT NULL,\n" +
                    "  `weapons_defence` int(255) DEFAULT NULL,\n" +
                    "  `sgin_in_day` json DEFAULT NULL,\n" +
                    "  `team` json DEFAULT NULL,\n" +
                    "  `talent` json DEFAULT NULL,\n" +
                    "  `task` json DEFAULT NULL,\n" +
                    "  `alias` json DEFAULT NULL,\n" +
                    "  `temporary_skill` json DEFAULT NULL,\n" +
                    "  `skill_data` json DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`qq`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
        } else if (("command").equals(Table)) {
            sql = "CREATE TABLE `command` (\n" +
                    "  `id` int(255) NOT NULL,\n" +
                    "  `admin` json DEFAULT NULL,\n" +
                    "  `group_list` json DEFAULT NULL,\n" +
                    "  `exp_up` int(255) NOT NULL,\n" +
                    "  `start_map` varchar(255) DEFAULT NULL,\n" +
                    "  `con` int(255) NOT NULL,\n" +
                    "  `currency` json DEFAULT NULL,\n" +
                    "  `init_money` json DEFAULT NULL,\n" +
                    "  `init_backpack` json DEFAULT NULL,\n" +
                    "  `sgin_in` json DEFAULT NULL,\n" +
                    "  `twinsp` int(255) NOT NULL,\n" +
                    "  `nofight` int(255) NOT NULL,\n" +
                    "  `revive_currency` varchar(255) DEFAULT NULL,\n" +
                    "  `question_player` int(255) NOT NULL,\n" +
                    "  PRIMARY KEY (`id`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
        }
        try{
            jdbcTemplate.execute(sql);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static int Select_Field(String Table,String Field){
        sql = "select count(*) from information_schema.columns where table_name = '"+Table+"' and column_name = '"+Field+"'";
        try{
            return jdbcTemplate.queryForObject(sql, Integer.class);
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public static void Carry_Sql(String sql){
        try{
            jdbcTemplate.execute(sql);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void Add_Field(String Table,String Field){
        if(("user").equals(Table)){
            if (("qq").equals(Field)){
                sql = "ALTER TABLE user ADD qq bigint(255) primary key(qq)";
                Carry_Sql(sql);
            }
            if (("name").equals(Field)){
                sql = "ALTER TABLE user ADD name varchar(18)";
                Carry_Sql(sql);
            }
            if (("sex").equals(Field)){
                sql = "ALTER TABLE user ADD sex varchar(3)";
                Carry_Sql(sql);
            }
            if (("nowmap").equals(Field)){
                sql = "ALTER TABLE user ADD nowmap varchar(255)";
                Carry_Sql(sql);
            }
            if (("nofight").equals(Field)){
                sql = "ALTER TABLE user ADD nofight int(255)";
                Carry_Sql(sql);
            }
            if (("batid").equals(Field)){
                sql = "ALTER TABLE user ADD batid json";
                Carry_Sql(sql);
            }
            if (("status").equals(Field)){
                sql = "ALTER TABLE user ADD status json";
                Carry_Sql(sql);
            }
            if (("ztstartdate").equals(Field)){
                sql = "ALTER TABLE user ADD ztstartdate datetime";
                Carry_Sql(sql);
            }
            if (("skill").equals(Field)){
                sql = "ALTER TABLE user ADD skill json";
                Carry_Sql(sql);
            }
            if (("state_info").equals(Field)){
                sql = "ALTER TABLE user ADD state_info json";
                Carry_Sql(sql);
            }
            if (("backpack").equals(Field)){
                sql = "ALTER TABLE user ADD backpack json";
                Carry_Sql(sql);
            }
            if (("money").equals(Field)){
                sql = "ALTER TABLE user ADD money json";
                Carry_Sql(sql);
            }
            if (("weapons_attack").equals(Field)){
                sql = "ALTER TABLE user ADD weapons_attack int(255)";
                Carry_Sql(sql);
            }
            if (("weapons_defence").equals(Field)){
                sql = "ALTER TABLE user ADD weapons_defence int(255)";
                Carry_Sql(sql);
            }
            if (("sgin_in_day").equals(Field)){
                sql = "ALTER TABLE user ADD sgin_in_day json";
                Carry_Sql(sql);
            }
            if (("team").equals(Field)){
                sql = "ALTER TABLE user ADD team json";
                Carry_Sql(sql);
            }
        }
    }

    public static void Field_Matching(String Table){
        List<String> Table_Field_List = new ArrayList<>();
        if (("user").equals(Table)){
            Table_Field_List.add("qq");
            Table_Field_List.add("name");
            Table_Field_List.add("sex");
            Table_Field_List.add("nowmap");
            Table_Field_List.add("nofight");
            Table_Field_List.add("batid");
            Table_Field_List.add("status");
            Table_Field_List.add("ztstartdate");
            Table_Field_List.add("skill");
            Table_Field_List.add("state_info");
            Table_Field_List.add("backpack");
            Table_Field_List.add("money");
            Table_Field_List.add("weapons_attack");
            Table_Field_List.add("weapons_defence");
            Table_Field_List.add("sgin_in_day");
            Table_Field_List.add("team");
            Table_Field_List.add("talent");
            Table_Field_List.add("task");
            Table_Field_List.add("alias");
            Table_Field_List.add("temporary_skill");
            Table_Field_List.add("skill_data");
            for (String Table_Field: Table_Field_List){
                if (Select_Field(Table,Table_Field) == 1){
                } else {
                    log.warn("表数据异常"+Table_Field);
                }
            }
        } else if (("command").equals(Table)) {
            Table_Field_List.add("id");
            Table_Field_List.add("admin");
            Table_Field_List.add("group_list");
            Table_Field_List.add("exp_up");
            Table_Field_List.add("start_map");
            Table_Field_List.add("con");
            Table_Field_List.add("currency");
            Table_Field_List.add("init_money");
            Table_Field_List.add("init_backpack");
            Table_Field_List.add("sgin_in");
            Table_Field_List.add("twinsp");
            Table_Field_List.add("nofight");
            Table_Field_List.add("revive_currency");
            Table_Field_List.add("question_player");
            for (String Table_Field: Table_Field_List){
                if (Select_Field(Table,Table_Field) == 1){
                } else {
                    log.warn("表数据异常"+Table_Field);
                }
            }
        }
    }

    public static void SleepCode(int s){
        try {
            Thread.sleep(s);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    public static void main() {

        log.info("数据库初始化...");
        //SleepCode(3000);
        List<String> TableList = new ArrayList<>();
        TableList.add("user");
        TableList.add("command");
        for(String TableData : TableList){
            if (!is_CreateTable(TableData)){
                log.warn(TableData+"表:"+(is_CreateTable(TableData) ? "success!" : "lose!"));
                //SleepCode(2000);
                log.warn(TableData+"表不存在，即将重建！");
                Boolean CreateUser = CreateTable(TableData);
                if (CreateUser){
                    log.warn(TableData+"表重建成功！");
                } else {
                    log.warn("系统创建失败，请手动创建！");
                }
            }else {
                log.info(TableData+"表:"+(is_CreateTable(TableData) ? "success!" : "lose!"));
                Field_Matching(TableData);
            }

        }
        log.info("启动成功");
    }
}
