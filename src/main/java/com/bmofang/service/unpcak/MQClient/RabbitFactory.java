package com.bmofang.service.unpcak.MQClient;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  DataCenterTest.java
 *文件描述：  Rabbit工厂类,负责生成Rabbit的各种接口.
 *修改日期：  2018-06-06 15:43.
 *文件作者：  Arike.Y
 *
 **********************************************/


public class RabbitFactory {
    
    private static Logger logger = LoggerFactory.getLogger(RabbitFactory.class);
    
    /**
     * 获取一个新的RabbitMQ Socket连接
     *
     * @return RabbitMQ的连接
     */
    
    public static Connection getConnection() {
        String rabbitHost = MQ_Config.mqToDTUTask.getString("host");
        String rabbitUserName = MQ_Config.mqToDTUTask.getString("userName");
        String rabbitPassword = MQ_Config.mqToDTUTask.getString("userPassword");
        ConnectionFactory connectionFacory = new ConnectionFactory();
        
        connectionFacory.setHost(rabbitHost);
        connectionFacory.setUsername(rabbitUserName);
        connectionFacory.setPassword(rabbitPassword);
        try {
            return connectionFacory.newConnection();
        } catch (IOException e) {
            logger.error("IO连接异常.获取连接失败.");
            e.printStackTrace();
        } catch (TimeoutException e) {
            logger.error("连接超时,获取连接失败.");
            e.printStackTrace();
        }
        return null;
    }
}
