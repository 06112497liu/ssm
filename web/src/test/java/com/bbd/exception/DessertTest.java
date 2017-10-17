package com.bbd.exception;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sun.security.krb5.internal.crypto.Des;

/**
 * @author Liuweibo
 * @version Id: DessertTest.java, v0.1 2017/10/17 Liuweibo Exp $$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DessertConfig.class)
public class DessertTest {

    @Autowired
    @Qualifier("iceCream")
    private Dessert dessert;

    @Autowired
    @Qualifier("iceCream")
    private Dessert dessert2;

    @Test
    public void test01() {
        System.out.println(dessert == dessert2);
        dessert.play();
    }

}
    
    