package com.bmofang.service.data.rabbitMQ;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  DataCenter.java
 *文件描述：  RabbitMQ配置
 *修改日期：  2018-08-09 15:12.
 *文件作者：  Arike.Y 
 *
 **********************************************/
@Configuration
public class RabbitMqConfig {
    //交换机配置
    public static final String DATA_EXCHANGE_IN = "dtu.data.in";
    
    public static final String DATA_EXCHANGE_OUT = "dtu.data.out";
    
    public static final String EVENT_EXCHANGE_IN = "dtu.event.in";
    
    //routingKey
    
    
    
    @Bean
    public Queue DCUEventQueue(){
        return QueueBuilder.durable("dcu_event").build();
    }
    
    @Bean Queue DataOutQueue(){
        return QueueBuilder.durable("dtu.data.out.cdzs").build();
    }
   
    @Bean
    public Exchange dcuEventExchange(){
        return ExchangeBuilder.directExchange("dcu_event").durable(true).build();
    }
    
    @Bean
    public Exchange DataOutExchange(){
        return ExchangeBuilder.directExchange("dtu.data.out").durable(true).build();
    }
    
    @Bean
    Binding bindingDataOutExchange(Queue DataOutQueue, Exchange DataOutExchange){
        return BindingBuilder.bind(DataOutQueue).to(DataOutExchange).with("dtu.data.out.cdzs").noargs();
    }
    
    @Bean
    Binding bindingDcuEventExchange(Queue DCUEventQueue, Exchange dcuEventExchange){
        return BindingBuilder.bind(DCUEventQueue).to(dcuEventExchange).with("dcu_event").noargs();
    }

    
}
