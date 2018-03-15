package com.zt.base;

import com.zt.pushservice.app.PushServiceApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author zhangtong
 * Created by on 2017/11/29
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PushServiceApplication.class)
@WebAppConfiguration
public class BaseTest {

}
