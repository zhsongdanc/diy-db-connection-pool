package com.demus.pool;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Set;

/**
 * @author demussong
 * @describe
 * @date 2025/6/10 00:10
 */
@Slf4j
public class DemusConnectionPool extends AbstractConnectionPool{

    public DemusConnectionPool(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    public synchronized Connection getConnection() throws SQLException {

        Connection connection = this.idlePool.poll();
        if (connection == null && activePool.size() < maxActiveSize) {
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
        boolean remove = activePool.remove(conn);
        if (remove) {
            try {
                if (!conn.isClosed() && !conn.isReadOnly()) {
                    conn.setAutoCommit(true);
                    idlePool.add(conn);
                } else {
                    closeConnection(conn);
                }
            } catch (SQLException e) {
                log.error("释放连接失败", e);
            }
        } else {
            log.error("释放连接失败, time={}", new Date());
        }

    }

    @Override
    public void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            log.error("关闭连接失败", e);
        }
    }

}
