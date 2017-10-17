package com.bbd.condition;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author Liuweibo
 * @version Id: MagicConfig.java, v0.1 2017/10/17 Liuweibo Exp $$
 */
@Configuration
public class MagicConfig {

    @Bean
    @Conditional(MagicExistsCondition.class)
    public Magic magic() {
        return new Magic();
    }
}
    
    