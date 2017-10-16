package com.bbd.service.impl;

import com.bbd.service.Knight;
import com.bbd.service.Quest;
import org.springframework.beans.factory.BeanNameAware;

/**
 * @author Liuweibo
 * @version Id: BraveKnight.java, v0.1 2017/10/16 Liuweibo Exp $$
 */
public class BraveKnight implements Knight, BeanNameAware {

    private Quest quest;

    public BraveKnight(Quest quest) {
        this.quest = quest;
    }

    @Override
    public void embarkOnQuest() {
        quest.embark();
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("setBeanName方法被调用：" + name);
    }
}
    
    