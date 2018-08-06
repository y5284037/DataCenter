package com.bmofang.service.unpcak.MQClient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bmofang.service.unpcak.unpack.UnPackProcess;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.Base64;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  DataCenterTest.java
 *文件描述：  DataConsumer 数据消费者
 *修改日期：  2018-06-06 15:43.
 *文件作者：  Arike.Y
 *
 **********************************************/

public class DataConsumer extends DefaultConsumer {
    
    private UnPackProcess unPackProcess = new UnPackProcess();
    private Channel channel;
    
    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public DataConsumer(Channel channel) {
        super(channel);
        this.channel = channel;
    }
    
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
        channel.basicAck(envelope.getDeliveryTag(), false);
    }
}
