package com.bbd.service.impl;

import com.bbd.service.Quest;

import java.io.PrintStream;

/**
 * @author Liuweibo
 * @version Id: SlayDragonQuest.java, v0.1 2017/10/16 Liuweibo Exp $$
 */
public class SlayDragonQuest implements Quest {

    private PrintStream ps;

    public SlayDragonQuest(PrintStream ps) {
        this.ps = ps;
    }

    @Override
    public void embark() {
        ps.println("Embark on quest to slay the dragon!");
    }
}
    
    