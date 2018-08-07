package com.bmofang.service.data.MQClient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bmofang.service.data.unpack.UnPackProcess;
import com.rabbitmq.client.*;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  DataCenterTest.java
 *文件描述：  Rabbit消费者(接收端).
 *修改日期：  2018-06-06 15:43.
 *文件作者：  Arike.Y
 *
 **********************************************/

@Component
public class Consum {
    
    @Autowired
    UnPackProcess unPackProcess;
    /**
     * 开启消费函数
     */
    public void startConsum() {
        startDataConsum();
        startEventConsum();
    }
    
    /**
     * 开启事件消费
     */
    private void startEventConsum() {
        try {
            String eventQueue = MQ_Config.dtuEventToMQTask.getString("queue");
            int eventChannelNum = Integer.valueOf(MQ_Config.dtuEventToMQTask.getString("channel"));
            Channel eventChannel = RabbitFactory.getConnection().createChannel(eventChannelNum);
            Consumer eventConsumer = new DefaultConsumer(eventChannel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    super.handleDelivery(consumerTag, envelope, properties, body);//调用父类的方法,完成Rabbit基本获取数据功能.
                    String dataIn = new String(body, "utf-8");
                    JSONObject dataJson = JSON.parseObject(dataIn);
                    System.out.println(dataIn);
                    eventChannel.basicAck(envelope.getDeliveryTag(), false);
                }
            };
            eventChannel.basicConsume(eventQueue, false, eventConsumer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 开启数据消费
     */
    private void startDataConsum() {
        System.out.println(unPackProcess.getEncoder());
        System.out.println(unPackProcess.getOriginalDataMapper());
        try {
            String dataQueue = MQ_Config.dtuDataToMQTask.getString("queue");
            int dataChannelNum = Integer.valueOf(MQ_Config.dtuDataToMQTask.getString("channel"));
            Channel dataChannel = RabbitFactory.getConnection().createChannel(dataChannelNum);
            
            Consumer dataConsumer = new DefaultConsumer(dataChannel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    super.handleDelivery(consumerTag, envelope, properties, body);//调用父类的方法,完成Rabbit基本获取数据功能.
                    String dataIn = new String(body, "utf-8");
                    JSONObject DataJson = JSON.parseObject(dataIn);
                    String data = DataJson.getString("Data");
                    Base64.Decoder decoder = Base64.getDecoder();
                    String dtuID = DataJson.getString("DTUID");
                    byte[] dtuData = decoder.decode(data);
                    String routingKey = Produce.outToCdzs;
                    unPackProcess.unpackData(routingKey, dtuID, dtuData);
                    dataChannel.basicAck(envelope.getDeliveryTag(), false);
                }
            };
            dataChannel.basicConsume(dataQueue, false, dataConsumer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
