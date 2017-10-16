package com.bbd.test;

import com.bbd.config.CDPlayerConfig;
import com.bbd.service.CompactDisc;
import com.bbd.service.impl.CDPlayer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Liuweibo
 * @version Id: CDPlayerTest.java, v0.1 2017/10/16 Liuweibo Exp $$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CDPlayerConfig.class)
public class CDPlayerTest {

    @Autowired
    private CDPlayer cdPlayer;

    private CompactDisc com;

    @Test
    public void cdShouldNotBeNull() {
        Assert.assertNotNull(com);
        com.play();
    }

    @Test
    public void play() {
        cdPlayer.player();
    }

}
    
    