package com.bbd.exception;

import com.bbd.exception.ErrorCode;

/**
 * @author tjwang
 * @version $Id: BizErrorCode.java, v 0.1 2017/8/3 0003 11:04 tjwang Exp $
 */
public enum BizErrorCode implements ErrorCode {

    RESULT_NOT_EMPTY(103, "返回列表不能为空"),

    // 1000 - 1999 参数异常异常
    USRENAME_NOT_BLANK(1000, "用户名不能为空!"),

    AME_NOT_BLANK(1001, "姓名不能为空!"),

    MOBILE_ERROR(1002, "手机号码有误!"),

    PASSWORD_ERROR(1003, "密码必须由6-20位字母和数字组成!"),

    USERNAME_ERROR(1017, "用户名必须由6-20位字母和数字组成!"),

    ID_NOT_NULL(1004, "id不能为空!"),

    USER_NAME_EXIST(1005, "用户名已经存在!"),

    PASSWORD_NOT_BLANK(1006, "密码不能为空!"),

    ACCOUNT_NOT_BLANK(1007, "账号不能为空!"),

    ACCOUNT_OR_PASSWORD_ERROR(1008, "账号或密码错误!"),

    NAME_NOT_BLANK(1009, "真实姓名不能为空!"),

    PROVINCE_NOT_BLANK(1010, "省份不能为空!"),

    MODULES_NOT_EMPTY(1011, "模块列表不能为空!"),

    ROLE_NAME_NOT_BLANK(1012, "角色名称不能为空!"),

    REMIND_NOT_NULL(1013, "未选择手动提示!"),

    PARAM_NULL(1014, "参数为空！"),

    PRAM_DATE_FORMAT_ERROR(1015, "时间格式错误!"),

    USER_MOBILE_EXIST(1016, "手机号已经注册!"),

    // 2000 -- 业务异常
    PERMISSION_NOT_ENOUGH(2000, "你的权限不足!"),

    NOT_LOGGED(2001, "你还没登录!"),

    TEL_REMINDED(2002, "电话已提示，请选择其他模式提醒!"),

    EMAIL_REMINDED(2003, "邮件已提示，请选择其他模式提醒!"),

    SMS_REMINDED(2004, "短信已提示，请选择其他模式提醒!"),

    DISABLED_USER(2005, "账户已经冻结！"),

    // 企业提示相关
    COMPANY_TASK_TYPE_REQUIRED(2010, "缺失企业类型"), COMPANY_TASK_TIME_REQUIRED(2011, "提示时间必填"), COMPANY_TASK_DATE_FISRT(2012, "提示时间不能小于当前系统时间"), COMPANY_TASK_DATE_LAST(2013, "提示时间不能小于上一次提示时间"), COMPANY_TASK_DATE_ALREAY_EXIST(
                                                                                                                                                                                                                             2015,
                                                                                                                                                                                                                             "已经存在相同时间的数据"),

    DATA_PLATFORM_ERROR(301, "访问数据平台接口异常"), ;

    private int    status;
    private String message;

    BizErrorCode(int status, String message) {
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
