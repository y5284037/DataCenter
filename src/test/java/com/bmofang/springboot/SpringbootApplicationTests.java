package com.bmofang.springboot;

import com.bmofang.service.data.rabbitMQ.Producer;
import com.bmofang.service.data.util.DCUHwModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootApplicationTests {

    @Autowired
    Producer producer;
    @Test
    public void contextLoads() throws InterruptedException {
        System.out.println(DCUHwModel.getName(20));
        
    }

}
