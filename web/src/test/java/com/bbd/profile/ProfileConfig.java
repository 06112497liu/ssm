package com.bbd.profile;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Liuweibo
 * @version Id: ProfileConfig.java, v0.1 2017/10/17 Liuweibo Exp $$
 */
@Configuration
public class ProfileConfig {

    @Bean("dev")
    @Profile("dev")
    public String devProfile() {
        return "dev-profile";
    }
    @Bean("prod")
    @Profile("prod")
    public String prodProfile() {
        return "prod-profile";
    }

    @Bean("tests")
    @Profile("test")
    public String testProfile() {
        return "test-profile";
    }

}
    
    