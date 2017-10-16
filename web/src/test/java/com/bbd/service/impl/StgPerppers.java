package com.bbd.service.impl;

import com.bbd.service.CompactDisc;
import org.springframework.stereotype.Component;

/**
 * @author Liuweibo
 * @version Id: StgPerppers.java, v0.1 2017/10/16 Liuweibo Exp $$
 */
@Component
public class StgPerppers implements CompactDisc {

    private String title = "Sgt. Perpper's Lonely Hearts Club Band";

    private String artist = "The Beatles";

    @Override
    public void play() {
        System.out.println("Playing " + title + " by " + artist);
    }
}
    
    