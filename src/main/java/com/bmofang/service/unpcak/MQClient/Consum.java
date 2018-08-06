package com.bmofang.service.unpcak.MQClient;

import com.rabbitmq.client.Channel;
import lombok.Data;

import java.io.IOException;

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

@Data
public class Consum {
    
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
            EventConsumer eventConsumer = new EventConsumer(eventChannel);
            eventChannel.basicConsume(eventQueue, false, eventConsumer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 开启数据消费
     */
    private void startDataConsum() {
        try {
            String dataQueue = MQ_Config.dtuDataToMQTask.getString("queue");
            int dataChannelNum = Integer.valueOf(MQ_Config.dtuDataToMQTask.getString("channel"));
            Channel dataChannel = RabbitFactory.getConnection().createChannel(dataChannelNum);
            DataConsumer dataConsumer = new DataConsumer(dataChannel);
            dataChannel.basicConsume(dataQueue, false, dataConsumer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
