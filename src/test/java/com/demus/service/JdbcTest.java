package com.demus.service;

import com.demus.pool.DemusConnectionPool;
import com.demus.pool.util.JdbcUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author demussong
 * @describe
 * @date 2025/6/9 23:20
 */
@Slf4j
public class JdbcTest {

    @Test
    public void test() {
        System.out.println("hello world");
    }

    @Test
    public void jdbcTest() {

    }

    @Test
    public void v1Test() {

        DemusConnectionPool demusConnectionPool = new DemusConnectionPool("jdbc:mysql://127.0.0.1:3306/file_storage",
                "root", "");
        demusConnectionPool.setMaxSize(20);

        for (int i = 0; i < 20; i++) {
            new Thread(() ->{
                try {
                    Connection connection = demusConnectionPool.getConnection();
                    Thread.sleep(1000);
                    log.info(Thread.currentThread().getName() + "execute finish!");
                    demusConnectionPool.releaseConnection(connection);
                } catch (Exception e) {
                    log.error("error", e);
                }

            }, "thread" + i).start();
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            log.error("main error", e);
        }
    }
}
