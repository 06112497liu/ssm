package com.bbd.test;

import com.bbd.service.Knight;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Liuweibo
 * @version Id: TestDemo.java, v0.1 2017/10/16 Liuweibo Exp $$
 */
public class TestDemo {

    @Test
    public void test01() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        Knight knight = context.getBean("knight", Knight.class);
        knight.embarkOnQuest();
        context.close();
    }

}
    
    