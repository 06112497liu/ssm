package com.bbd.profile;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Liuweibo
 * @version Id: ProfileTest.java, v0.1 2017/10/17 Liuweibo Exp $$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProfileConfig.class)
@ActiveProfiles("test")
public class ProfileTest {

    @Autowired
    private String dev;

    @Test
    public void test01() {
        System.out.println(dev);
    }
}
    
    