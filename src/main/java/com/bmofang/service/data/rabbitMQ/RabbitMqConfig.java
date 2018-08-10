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
    public static final String EXCHANGE_DC_TO_GC = "dtu.data.out";
    
    public static final String EXCHANGE_DC_TO_DMP = "dcu_event";
    //Queue配置
    public static final String Queue_DATA_GC_TO_DC = "dtu.data.in";
    
    public static final String Queue_DATA_DC_TO_GC = "dtu.data.out.cdzs";
    
    public static final String Queue_EVENT_GC_TO_DC = "dtu.event.in";
    
    public static final String Queue_EVENT_DC_TO_DMP = "dcu_event";
    
    //routingKey配置
    public static final String ROUTING_DC_TO_GC = "dtu.data.out.cdzs";
    
    public static final String ROUTING_DC_TO_DMP = "dcu_event";
    
    
    @Bean
    public Queue DCUEventQueue() {
        return QueueBuilder.durable(Queue_EVENT_DC_TO_DMP).build();
    }
    
    @Bean
    Queue DataOutQueue() {
        return QueueBuilder.durable(Queue_DATA_DC_TO_GC).build();
    }
    
    
    @Bean
    public Exchange dcuEventExchange() {
        return ExchangeBuilder.directExchange(EXCHANGE_DC_TO_DMP).durable(true).build();
    }
    
    @Bean
    public Exchange DataOutExchange() {
        return ExchangeBuilder.directExchange(EXCHANGE_DC_TO_GC).durable(true).build();
    }
    
    @Bean
    Binding bindingDataOutExchange(Queue DataOutQueue, Exchange DataOutExchange) {
        return BindingBuilder.bind(DataOutQueue).to(DataOutExchange).with(ROUTING_DC_TO_GC).noargs();
    }
    
    @Bean
    Binding bindingDcuEventExchange(Queue DCUEventQueue, Exchange dcuEventExchange) {
        return BindingBuilder.bind(DCUEventQueue).to(dcuEventExchange).with(ROUTING_DC_TO_DMP).noargs();
    }
    
    
}
