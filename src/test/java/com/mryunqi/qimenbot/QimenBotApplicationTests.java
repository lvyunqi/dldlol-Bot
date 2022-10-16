package com.mryunqi.qimenbot;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

@SpringBootTest
class SpringBootHasTableCreatedApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void created() {
        String sql = "SHOW TABLES"; // https://dev.mysql.com/doc/refman/5.7/en/show-tables.html

        List<String> tableNameList = jdbcTemplate.query(sql, null, null, new ResultSetExtractor<List<String>>() {
            @Override
            public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<String> tableNameList = new ArrayList<>();

                while (rs.next()) {
//                    String tableName = rs.getString(1);
                    String tableName = rs.getString("Tables_in_hello-account");

                    tableNameList.add(tableName);
                }

                return tableNameList;
            }
        });

        System.out.println(tableNameList);
    }
}