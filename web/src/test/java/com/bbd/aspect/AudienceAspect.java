package com.bbd.aspect;

import com.bbd.annotation.Audience;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Liuweibo
 * @version Id: Audience.java, v0.1 2017/10/20 Liuweibo Exp $$
 */
@Aspect
@Order
@Component
public class AudienceAspect {

    @Pointcut(value = "@annotation(audience)", argNames = "audience")
    public void pointcut(Audience audience){}

    @Before(value = "pointcut(audience)")
    public void silenceCellPhones(Audience audience) {
        System.out.println("Silencing cell phones");
    }

    @After(value = "pointcut(audience)")
    public void applause(Audience audience) {
        System.out.println("CLAP CLAP CLAP!!!");
    }

    @Around(value = "pointcut(audience)")
    public void around(ProceedingJoinPoint pjp, Audience audience) {
        try {
            System.out.println("around");
            pjp.proceed();
            pjp.proceed();
            System.out.println("around");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}





















    