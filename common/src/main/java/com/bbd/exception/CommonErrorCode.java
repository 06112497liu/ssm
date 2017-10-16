package com.bbd.exception;

/**
 * 核心模块错误(0-999)
 *
 * @author wangtianjing 
 */
public enum CommonErrorCode implements ErrorCode {

    SUCCESS(200, "成功"),

    PARAM_ERROR(201, "参数异常"),

    BIZ_ERROR(202, "业务异常"),

    SYS_ERROR(101, "系统异常"),

    INNER_ERROR(102, "内部异常"),

    PARAM_NULL(1014, "参数不能为空！"), ;

    private int    status;
    private String message;

    CommonErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
