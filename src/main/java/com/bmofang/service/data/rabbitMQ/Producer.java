package com.bmofang.service.data.rabbitMQ;

import com.bmofang.service.data.oldMQClient.Produce;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  DataCenter.java
 *文件描述：  发送者
 *修改日期：  2018-08-09 15:45.
 *文件作者：  Arike.Y 
 *
 **********************************************/

@Component
public class Producer {
    
    private final AmqpTemplate amqpTemplate;
    
    @Autowired
    public Producer(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }
    
    public void publish(String routingKey,byte[] message){
        amqpTemplate.convertAndSend(Produce.exchange, "dtu.data.out.cdzs", message);
        
        
    }
}
