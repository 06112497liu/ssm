package com.bbd.condition;


import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author Liuweibo
 * @version Id: MagicExistsCondition.java, v0.1 2017/10/17 Liuweibo Exp $$
 */
public class MagicExistsCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment env = context.getEnvironment();
        return !env.containsProperty("magic");
    }
}
    
    