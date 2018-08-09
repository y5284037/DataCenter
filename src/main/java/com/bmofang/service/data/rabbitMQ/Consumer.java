package com.bmofang.service.data.rabbitMQ;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bmofang.service.data.oldMQClient.Produce;
import com.bmofang.service.data.unpack.UnPackProcess;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  DataCenter.java
 *文件描述：  消费者
 *修改日期：  2018-08-09 15:49.
 *文件作者：  Arike.Y 
 *
 **********************************************/

@Component
public class Consumer {
    
    private final UnPackProcess unPackProcess;
    
    @Autowired
    public Consumer(UnPackProcess unPackProcess) {
        this.unPackProcess = unPackProcess;
    }
    
    /**
     * DIRECT模式.
     *
     * @param message the message
     * @param channel the channel
     * @throws IOException the io exception  这里异常需要处理
     */
    @RabbitListener(queues = {"dtu.data.in"})
    public void deliverMessage(Message message, Channel channel) throws IOException {
        String dataIn = new String(message.getBody(), "utf-8");
        System.out.println(dataIn);
        JSONObject DataJson = JSON.parseObject(dataIn);
        String data = DataJson.getString("Data");
        Base64.Decoder decoder = Base64.getDecoder();
        String dtuID = DataJson.getString("DTUID");
        byte[] dtuData = decoder.decode(data);
        String routingKey = Produce.outToCdzs;
        unPackProcess.unpackData(routingKey, dtuID, dtuData);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
    
}
