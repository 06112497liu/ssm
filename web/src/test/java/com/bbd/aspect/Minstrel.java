package com.bbd.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.BeanNameAware;

import java.io.PrintStream;

/**
 * @author Liuweibo
 * @version Id: Minstrel.java, v0.1 2017/10/16 Liuweibo Exp $$
 */
public class Minstrel{

    private PrintStream ps;

    public Minstrel(PrintStream ps) {
        this.ps = ps;
    }

    public void singBeforeQuest() {
        ps.println("Fa la la, the knight is so brave!");
    }

    public void singAfterQuest() {
        ps.println("Tee hee hee, the brave knight did embark on a quest!");
    }

}
    
    