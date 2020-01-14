package com.geo.core.result;

/**
 * @author
 */

public enum ResultCodeEnum {

    // 系统通用
    SUCCESS(0, "操作成功"),

    FAILED(-1, "操作失败"),

    API_CALL_FAIL(500, "接口调用失败"),

    // 用户
    TOKEN_INVALID(401, "token无效，请重新登录");

    private Integer code;
    private String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public final Integer getCode() {
        return this.code;
    }

    public final String getMessage() {
        return this.message;
    }
}
