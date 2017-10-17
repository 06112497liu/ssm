package com.bbd.enviroment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

/**
 * @author Liuweibo
 * @version Id: ExpressiveConfig.java, v0.1 2017/10/17 Liuweibo Exp $$
 */
@Configuration
@PropertySource(name = "localProperties", value = "classpath:config-dev.properties")
public class ExpressiveConfig {

}
    
    