package com.demus.pool.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * JDBC工具类
 * 演示基本的数据库连接步骤
 */
public class JdbcUtil {
    private static final Logger logger = LoggerFactory.getLogger(JdbcUtil.class);

    // 数据库连接参数
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/file_storage?useSSL=false&serverTimezone=Asia/Shanghai";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    /**
     * 获取数据库连接
     * @return Connection 数据库连接对象
     * @throws SQLException 数据库连接异常
     */
    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            // 1. 加载驱动
            Class.forName(DRIVER);
            logger.info("数据库驱动加载成功");

            // 2. 建立连接
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            logger.info("数据库连接成功");

        } catch (ClassNotFoundException e) {
            logger.error("数据库驱动加载失败", e);
            throw new SQLException("数据库驱动加载失败", e);
        } catch (SQLException e) {
            logger.error("数据库连接失败", e);
            throw e;
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     * @param conn 数据库连接对象
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                logger.info("数据库连接已关闭");
            } catch (SQLException e) {
                logger.error("关闭数据库连接失败", e);
            }
        }
    }

    /**
     * 测试数据库连接
     */
    public static void main(String[] args) {
        Connection conn = null;
        try {
            // 获取连接
            conn = getConnection();
            boolean autoCommit = conn.getAutoCommit();
            logger.info("自动提交状态：{}", autoCommit);

            // 这里可以执行数据库操作
            // ...

        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            // 关闭连接
            closeConnection(conn);
        }
    }
} 