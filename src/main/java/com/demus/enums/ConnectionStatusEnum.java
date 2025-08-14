package com.demus.enums;

import lombok.Getter;

/**
 * @author demussong
 * @describe
 * @date 2025/8/14 23:05
 */
@Getter
public enum ConnectionStatusEnum {

    USING("USING"),

    AVAILABLE("AVAILABLE");

    private String code;

    ConnectionStatusEnum(String status) {
        this.code = status;
    }

}
