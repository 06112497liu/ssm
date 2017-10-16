/**  
 * BBD Service Inc
 * All Rights Reserved @2016
 *
 */
package com.bbd.util;

import com.bbd.exception.ApplicationException;
import com.bbd.exception.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 字符串校验工具类
 * 
 * @author zhanghui
 * @version $Id: StringValidator.java, v 0.1 2016年12月7日 下午6:04:40 zhanghui Exp $
 */
public class ValidateUtil {

    /** 手机号码的校验规则 */
    public static final String MOBILE_PATTERN   = "^[1][3-8][0-9]{9}$";

    /** 密码校验规则 */
    //public static final String PASSWORD_PATTERN = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,32}$";
    public static final String PASSWORD_PATTERN = "^((?=.*[0-9].*)(?=.*[A-Za-z].*))[0-9A-Za-z]{6,20}$";

    /** 用户名校验规则  */
    public static final String USERNAME_PATTERN = "^(?=.*[a-zA-Z])(?=.*[0-9])[0-9a-zA-Z]{6,20}$";

    /**
     * 检查对象是否为为空.
     * 
     * @param source 源对象
     */
    public static void checkNull(Object source, ErrorCode status) {
        if (Objects.isNull(source)) {
            throw new ApplicationException(status);
        }
    }

    /**
     * 检查传入的参数列表是否全为空
     * 
     * @param source
     */
    public static void checkAllNull(ErrorCode status, Object... source) {
        for (Object object : source) {
            if (!Objects.isNull(object)) {
                return;
            }
        }
        throw new ApplicationException(status);
    }

    /**
     * 校验字符串是否为空
     * @param source 源字符串
     */
    public static void checkBlank(String source, ErrorCode status) {
        if (StringUtils.isBlank(source)) {
            throw new ApplicationException(status);
        }
    }

    /**
     * 校验手机号码
     * @param source 源字符串
     */
    public static void checkMobile(String source, ErrorCode status) {
        if (!match(source, MOBILE_PATTERN)) {
            throw new ApplicationException(status);
        }
    }

    /**
     * 密码校验.
     * 
     * @param source 密码
     */
    public static void checkPassword(String source, ErrorCode status) {
        if (!match(source, PASSWORD_PATTERN)) {
            throw new ApplicationException(status);
        }
    }

    public static void checkUsername(String source, ErrorCode status) {
        if (!match(source, USERNAME_PATTERN)) {
            throw new ApplicationException(status);
        }
    }

    private static boolean match(String source, String pattern) {
        return Pattern.compile(pattern).matcher(source).matches();
    }

    /**
     * 检验两个Integer是否相等.
     * 
     * @param i2 整数1
     * @param i2 整数2
     */
    public static void checkEquals(Integer i1, Integer i2, ErrorCode status) {
        if (i1 == i2) {
            return;
        }

        if (i1 != null && i1.equals(i2)) {
            return;
        }

        throw new ApplicationException(status);
    }

    /**
     * 检验两个Integer是否相等.
     * 
     */
    public static void checkEquals(Object o1, Object o2, ErrorCode status) {
        if (!Objects.equals(o1, o2)) {
            throw new ApplicationException(status);
        }
    }

    /**
     * 检查字符串是否相同.
     * 
     * @param s1 字符串1
     * @param s2 字符串2
     */
    public static void checkEquals(CharSequence s1, CharSequence s2, ErrorCode status) {
        if (!StringUtils.equals(s1, s2)) {
            throw new ApplicationException(status);
        }
    }

    /**
     * 检查列表是否为空.
     * 
     */
    public static void checkListEmpty(List<?> list, ErrorCode status) {
        if (list == null || list.size() == 0) {
            throw new ApplicationException(status);
        }
    }

    /**
     * 检查所有的字符串不全为空
     */
    public static void checkAllBlank(ErrorCode status, String... args) {
        for (String arg : args) {
            if (StringUtils.isNotBlank(arg)) {
                return;
            }
        }
        throw new ApplicationException(status);
    }

    /**
     * 手机号验证
     * 
     * @param phone
     */
    public static boolean checkMobile(String phone) {
        if (!match(phone, MOBILE_PATTERN)) {
            return false;
        }
        return true;
    }

    /**
     * 判断两个字符串是否不相等 
     * 相等 抛出异常
     */
    public static void checkNotEquals(String s1, String s2, ErrorCode status) {
        if (StringUtils.equals(s1, s2)) {
            throw new ApplicationException(status);
        }
    }

    /**
     * 检查时间字符串的格式
     * @param date 时间字符串
     */
    public static void checkDateStringFormat(String date, String pattern, ErrorCode status) {
        try {
            DateUtils.parseDate(date, pattern);
        } catch (ParseException e) {
            throw new ApplicationException(status);
        }
    }
}
