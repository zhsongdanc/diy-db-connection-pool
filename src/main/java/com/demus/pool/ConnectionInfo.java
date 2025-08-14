package com.demus.pool;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Connection;

/**
 * @author demussong
 * @describe
 * @date 2025/6/10 00:37
 */
@Data
@AllArgsConstructor
public class ConnectionInfo {

    private Connection connection;

    // available;using
    private String status;
}
