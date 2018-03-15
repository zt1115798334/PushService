package com.zt.pushservice.task;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author zhangtong
 * Created by on 2018/3/15
 */
@Component
@Order(value = 1)
public class PushTask2 implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    System.out.println("第二个执行啦 -----------------");
                    try {
                        Thread.sleep(2000L);
                    } catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();

    }
}
