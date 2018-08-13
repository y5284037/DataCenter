package com.bmofang.service.data.rabbitMQ;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

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
@Slf4j
@Component
public class Producer implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback {
    
    
    private final RabbitTemplate rabbitTemplate;
    
    /**
     * 初始化RabbitTemplate操作类,将确认回调和返回回调配置.
     */
    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }
    
    @Autowired
    public Producer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    
    /**
     * ConfirmCallback接口用于实现消息发送到RabbitMQ交换器后接收ack回调。
     * @param correlationData 发送数据包中添加的数据包身份ID
     * @param ack    发送状态(是否成功)
     * @param cause  发送失败造成的原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if(ack){
//            System.out.println("消息发送成功:"+correlationData);
        }else {
          log.error("消息发送失败:"+cause);
        }
    
    }
    
    /**
     * ReturnCallback接口用于实现消息发送到RabbitMQ交换器，但无相应队列与交换器绑定时的回调。
     * @param message       发送的数据
     * @param replyCode     响应码
     * @param replyText     响应消息
     * @param exchange      交换机
     * @param routingKey    路由键
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.error(message.getMessageProperties().getCorrelationId()+"该RoutingKey没有对应绑定exchange的Queue.发送失败");
    }
    
    
    public void send(String routingKey, Object message) {
        CorrelationData correlationID = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_DC_TO_GC,routingKey,message,correlationID);
    }
    
}


