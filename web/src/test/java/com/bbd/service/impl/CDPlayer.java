package com.bbd.service.impl;

import com.bbd.service.CompactDisc;
import com.bbd.service.MediaPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Liuweibo
 * @version Id: CDPlayer.java, v0.1 2017/10/16 Liuweibo Exp $$
 */
@Component
public class CDPlayer implements MediaPlayer {

    @Autowired
    private CompactDisc cd;

    @Override
    public void player() {
        cd.play();
    }
}
    
    