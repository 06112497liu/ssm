package com.bbd.exception;

import org.springframework.stereotype.Component;

/**
 * @author Liuweibo
 * @version Id: Cake.java, v0.1 2017/10/17 Liuweibo Exp $$
 */
@Component
public class Cake implements Dessert {

    @Override
    public void play() {
        System.out.println(getClass().getName() + "好甜");
    }
}
    
    