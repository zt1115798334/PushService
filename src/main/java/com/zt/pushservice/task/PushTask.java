package com.zt.pushservice.task;

import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author zhangtong
 * Created by on 2018/3/15
 */
@Component
@Order(value = 2)
public class PushTask {

    @Scheduled(fixedDelay = 3600000)
    public void execute() {
        System.out.println("开始执行定时器");
    }
}
