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
    public synchronized Connection getConnection() throws SQLException {

        Connection connection = this.idlePool.poll();
        if (connection == null && activePool.size() < maxSize) {
            connection = createConnection();
        }
        if (connection == null) {
            throw new SQLException("连接池已满");
        }
        activePool.add(connection);
        return connection;
    }

    @Override
    public void releaseConnection(Connection conn) {

    }

    @Override
    public void destroy() {

    }
}
