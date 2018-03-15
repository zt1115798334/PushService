package com.zt.pushservice.task;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author zhangtong
 * Created by on 2018/3/15
 */
@Component
@Order(value = 2)
public class PushTask implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    System.out.println("第一个执行");

                    try {
                        Thread.sleep(1000L);
                    } catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();
    }
//    @Scheduled(fixedDelay = 3600000)
//    public void execute() {
//        System.out.println("开始执行定时器");
//    }
}
