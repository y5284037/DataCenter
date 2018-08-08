package com;

import com.bmofang.service.data.MQClient.Consum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  DataCenter.java
 *文件描述：  自定义执行类
 *修改日期：  2018-08-07 16:36.
 *文件作者：  Arike.Y 
 *
 **********************************************/
@Component
public class MyApplicationRunner implements ApplicationRunner {
    private final Consum consum;
    
    @Autowired
    public MyApplicationRunner(Consum consum) {
        this.consum = consum;
    }
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        consum.startConsum();
    }
}
