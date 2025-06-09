package com.demus.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Set;

/**
 * @author demussong
 * @describe
 * @date 2025/6/10 00:10
 */
public class DemusConnectionPool extends AbstractConnectionPool{



    public DemusConnectionPool(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    public Connection getConnection() throws SQLException {




        DriverManager.getConnection(url, username, password);
    }

    @Override
    public void releaseConnection(Connection conn) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void setMaxActive(int maxActive) {

    }

    @Override
    public void setCoreSize(int coreSize) {

    }
}
