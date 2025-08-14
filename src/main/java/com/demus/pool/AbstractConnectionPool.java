package com.demus.pool;

import com.demus.enums.ConnectionStatusEnum;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author demussong
 * @describe
 * @date 2025/6/10 00:12
 */
@Slf4j
public abstract class AbstractConnectionPool implements ConnectionPool{

    private static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

    protected String url;
    protected String username;
    protected String password;

    protected static final int MAX_ACTIVE_SIZE = 10;
    protected AtomicInteger activeSize = new AtomicInteger(0);

    protected Map<Connection, ConnectionInfo> connections;


    public AbstractConnectionPool(String url, String username, String password) {
        try {
            Class.forName(DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            log.error("数据库驱动加载失败", e);
        }
        this.url = url;
        this.username = username;
        this.password = password;
        connections = new ConcurrentHashMap<>();
    }

    protected Connection createConnection() throws SQLException {
        Connection result = null;
        if (activeSize.get() < MAX_ACTIVE_SIZE && activeSize.compareAndSet(activeSize.get(), activeSize.get() + 1)) {
            result = DriverManager.getConnection(url, username, password);
            connections.put(result, new ConnectionInfo(result, ConnectionStatusEnum.USING.getCode()));
            log.info("创建新连接成功");
            return result;
        }
        throw new SQLException("createConnection,连接池已满");
    }
}
