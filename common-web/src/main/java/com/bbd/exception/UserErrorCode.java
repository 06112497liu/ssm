/**
 * BBD Service Inc
 * All Rights Reserved @2016
 */
package com.bbd.exception;

/**
 * 用户异常。已2000-2099。
 *
 * @author tjwang
 * @version $Id: UserErrorCode.java, v 0.1 2017/9/25 0025 15:20 tjwang Exp $
 */
public enum UserErrorCode implements ErrorCode {

    USERNAME_NOT_EXIST(2000, "用户名不存在"), USERNAME_PASSWORD_ERROR(2001, "用户名/密码错误"), ;

    private int    status;
    private String message;

    UserErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public int getStatus() {
        return this.status;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
