package com.demus.pool;

import com.demus.enums.ConnectionStatusEnum;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Objects;

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
    public Connection getConnection() throws SQLException {
        // 使用Stream API查找可用的连接
        Connection result = connections.entrySet().stream()
                .filter(entry -> Objects.equals(entry.getValue().getStatus(), ConnectionStatusEnum.AVAILABLE.getCode()))
                .findFirst()
                .map(entry -> {
                    // 找到可用连接后，将其状态设置为使用中
                    entry.getValue().setStatus(ConnectionStatusEnum.USING.getCode());
                    return entry.getKey();
                })
                .orElse(null);
        if (result != null) {
            // 获取连接信息
            ConnectionInfo connectionInfo = connections.get(result);
            // 返回连接对象
            return connectionInfo.getConnection();
        }

        // 如果池中没有可用连接，则创建新连接
        result = createConnection();
        if (result != null) {
            return result;
        }
        log.error("没有可用连接，创建新连接失败");
        throw new SQLException("没有可用连接，创建新连接失败");
    }

    @Override
    public void releaseConnection(Connection conn) {
        log.info("释放连接");
        Connection tar = connections.entrySet().stream()
                .filter(connectionConnectionInfoEntry -> connectionConnectionInfoEntry.getKey() == conn)
                .findFirst()
                .map(entry -> {
                    entry.getValue().setStatus(ConnectionStatusEnum.AVAILABLE.getCode());
                    return entry.getKey();
                })
                .orElse(null);
        if (Objects.isNull(tar)) {
            throw new RuntimeException("连接不存在");
        } else if (activeSize.compareAndSet(activeSize.get(), activeSize.get() - 1)){
            connections.remove(conn);
            log.info("释放连接成功");
        }
    }

    @Override
    public void destroy() {
        Iterator<Connection> iterator = connections.keySet().iterator();
        while (iterator.hasNext()) {
            Connection connection = iterator.next();
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("关闭数据库连接失败");
                e.printStackTrace();
            }
            // 正确的删除方式
            iterator.remove(); // 安全地删除当前元素
            // 注意：这里不能使用 map.put(null, value) 来“设置key为null”，因为key是不可变的
        }

    }

    @Override
    public int getActiveCount() {
        return super.activeSize.get();
    }
}

