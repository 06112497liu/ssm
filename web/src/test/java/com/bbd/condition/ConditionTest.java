package com.bbd.condition;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Liuweibo
 * @version Id: ConditionTest.java, v0.1 2017/10/17 Liuweibo Exp $$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MagicConfig.class)
public class ConditionTest {

    @Autowired
    private Magic magic;

    @Test
    public void test01() {
        magic.play();
    }
}
