package com.demus.service;

import com.demus.pool.ConnectionPool;
import com.demus.pool.DemusConnectionPool;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author demussong
 * @describe
 * @date 2025/8/14 23:07
 */
@Slf4j
public class MainTest {


    @Test
    public void test() throws SQLException {
        final String url = "jdbc:mysql://localhost:3306/file_storage?useSSL=false&serverTimezone=Asia/Shanghai";

        ConnectionPool pool = new DemusConnectionPool(url, "root", "");

        Connection connection = null;
        try {
            connection = pool.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.info("size:{}", pool.getActiveCount());

        boolean autoCommit = connection.getAutoCommit();
        log.info("自动提交状态：{}", autoCommit);
        pool.releaseConnection(connection);
        log.info("size:{}", pool.getActiveCount());


    }
}
