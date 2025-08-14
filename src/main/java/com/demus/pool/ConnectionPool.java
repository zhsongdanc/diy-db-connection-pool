package com.demus.pool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author demussong
 * @describe
 * @date 2025/6/10 00:05
 */
public interface ConnectionPool {

    public Connection getConnection() throws SQLException;

    public void releaseConnection(Connection conn);

    public void destroy();

    public int getActiveCount();

//    public void setMinIdle(int minIdle);

//    public void setMaxWait(long maxWait);
//
//    public void setTestOnBorrow(boolean testOnBorrow);
//
//    public void setTestOnReturn(boolean testOnReturn);
//
//    public void setTestWhileIdle(boolean testWhileIdle);
//
//    public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis);
//
//    public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis);
//
//    public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun);
//
//    public void setRemoveAbandoned(boolean removeAbandoned);
//
//    public void setRemoveAbandonedTimeout(int removeAbandonedTimeout);
//
//    public void setLogAbandoned(boolean logAbandoned);
//
//    public void setInitConnection(boolean initConnection);
//
//    public void setValidationQuery(String validationQuery);
//
//    public void setJdbcUrl(String jdbcUrl);
}
