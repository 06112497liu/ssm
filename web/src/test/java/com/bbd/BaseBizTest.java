package com.bbd;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * spring biz测试环境
 *
 * <P>继承该类，直接写junit4测试，默认事物不回滚，不会产生垃圾数据。
 * 如果不需要数据回滚，请加上注解{@link org.springframework.test.annotation.Rollback} @Rollback(false)</P>
 * @author Liuweibo
 * @version Id: BaseBizTest.java, v0.1 2017/10/16 Liuweibo Exp $$
 */

@ContextConfiguration({"classpath:knights.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseBizTest extends Assert {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy.MM.dd HH:mm:ss").setPrettyPrinting().create();

    private long     startTime;

    @Before
    public void before() {
        logger.debug("");
        logger.debug("----------test before {}-------------------", getClass().getSimpleName());
        startTime = System.currentTimeMillis();
    }

    @After
    public void after() {
        logger.debug("----------test after {} total time {}ms------", getClass().getSimpleName(), System.currentTimeMillis() - startTime);
        logger.debug("");
    }

    /**
     * 获取json字符串用于打印
     */
    public String toPrintJosnString(Object object) {
        return gson.toJson(object);
    }
}
    
    