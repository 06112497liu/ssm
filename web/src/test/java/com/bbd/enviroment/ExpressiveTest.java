package com.bbd.enviroment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Liuweibo
 * @version Id: ExpressiveTest.java, v0.1 2017/10/17 Liuweibo Exp $$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ExpressiveConfig.class)
public class ExpressiveTest {


    @Value("${es_cluster}")
    private String s;

    @Value("#{systemEnvironment['windir']}")
    private String s2;

    @Value("${jdbc.url}")
    private String s3;

//    @Test
//    public void test01() {
//        System.out.println(env.getProperty("redis.conn.hostName"));
//    }
//
//    @Test
//    public void test02() {
//        RedisVo vo = env.getProperty("redis.conn", RedisVo.class);
//        System.out.println(vo.toString());
//    }

    @Test
    public void test03() {
        //System.out.println(s);

        System.out.println(s2);
    }

    @Test
    public void test04() {
        System.out.println(s3);
    }

}
    
    