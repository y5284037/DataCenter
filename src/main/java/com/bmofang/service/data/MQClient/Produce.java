package com.bmofang.service.data.MQClient;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  DataCenterTest.java
 *文件描述：  生产类
 *修改日期：  2018-06-14 10:24.
 *文件作者：  Arike.Y 
 *
 **********************************************/
@Component
public class Produce {
    
    private Channel channel;
    private static BasicProperties properties;
    
    public static String outToCdzs;
    public static String outToBT;
    private static String exchange;
    
    /**
     * queue和exchange只需要声明一次即可,所以尽量避免多次声明,只有在第一次获取Produce的时候去声明.
     */
    static {
        //为持久化属性赋值
        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        builder.deliveryMode(2);//2代表持久化,1代表不持久化
        properties = builder.build();
        outToCdzs = (String) MQ_Config.dataOutQueue.get(0);
        outToBT = (String) MQ_Config.dataOutQueue.get(1);
        exchange = MQ_Config.mqToDTUTask.getString("exchange");
           /* //声明exchange和queue
           Connection connection = RabbitFactory.getConnection();
            Channel channel = connection.createChannel();
            String exchange = MQ_Config.mqToDTUTask.getString("exchange");
            String exchangeType = MQ_Config.mqToDTUTask.getString("exchangeType");
            channel.exchangeDeclare(exchange, exchangeType, true, false, null);
            //将配置文件中的所有queue声明.
            for (Object queue : MQ_Config.dataOutQueue) {
                channel.queueDeclare((String) queue, true, false, false, null);
            }
            channel.close();
            connection.close();*/
    }
    
    
    public Produce() {
        Connection connection = RabbitFactory.getConnection();
        try {
            channel = connection.createChannel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean publish(String routingKey, byte[] message) {
        try {
            channel.basicPublish(exchange, routingKey, properties, message);
        } catch (IOException e) {
            System.out.println("发送失败,出现IO异常!");
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
