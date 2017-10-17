package com.bbd.exception;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author Liuweibo
 * @version Id: IceCream.java, v0.1 2017/10/17 Liuweibo Exp $$
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.INTERFACES)
public class IceCream implements Dessert {

    @Override
    public void play() {
        System.out.println(getClass().getName() + "好甜");
    }
}
    
    