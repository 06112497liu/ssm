package com.bbd.aspect;

import com.bbd.annotation.Audience;
import org.springframework.stereotype.Component;

import java.net.SocketTimeoutException;

@Component
public class Movie implements Performance {
    @Override
    @Audience
    public void perform() {
        System.out.println("拯救大兵瑞恩正在播放。。。。。。");
    }
}
