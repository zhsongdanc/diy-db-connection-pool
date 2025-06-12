package com.demus.pool;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author demussong
 * @describe
 * @date 2025/6/10 00:12
 */
@Slf4j
public abstract class AbstractConnectionPool implements ConnectionPool{

    private static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

    private static final int DEFAULT_MAX_ACTIVE_SIZE = 30;

    protected String url;
    protected String username;
    protected String password;

    protected LinkedBlockingDeque<Connection> idlePool;
    protected Set<Connection> activePool ;

    protected int maxActiveSize;


    public AbstractConnectionPool(String url, String username, String password) {
        try {
            Class.forName(DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            log.error("数据库驱动加载失败", e);
        }
        this.url = url;
        this.username = username;
        this.password = password;
        idlePool = new LinkedBlockingDeque<>();
        activePool = new HashSet<>();

        this.maxActiveSize = DEFAULT_MAX_ACTIVE_SIZE;
    }


    protected Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            log.error("创建数据库连接失败", e);
        }
        return connection;
    }


    public void setMaxSize(int maxActiveSize) {
        this.maxActiveSize = maxActiveSize;
    }
}
