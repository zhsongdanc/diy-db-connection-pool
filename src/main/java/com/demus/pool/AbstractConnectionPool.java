package com.demus.pool;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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

    protected Map<ConnectionWrapper, Object> connections;


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
}
