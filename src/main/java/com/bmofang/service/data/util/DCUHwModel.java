package com.bmofang.service.data.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  DataCenterTest.java
 *文件描述：  DCU硬件型号编号类
 *修改日期：  2018-06-07 14:41.
 *文件作者：  Arike.Y 
 *
 **********************************************/
@Component
@Slf4j
public class DCUHwModel {
    
    private static Properties hwModelConf;
    
    /**
     * 进行json对象的初始化
     */
    static {
        hwModelConf = new Properties();
        try {
            hwModelConf.load(DCUHwModel.class.getClassLoader().getResourceAsStream("conf/HardwareModelConfig.properties"));//在静态代码块中执行是为了作为驱动加载一次
        } catch (IOException e) {
            e.printStackTrace();
            log.error("没有读取到硬件配置文件.");
        }
        
    }
    
    /**
     * 获取到硬件版本号
     *
     * @param modelNum 对应版本号值的key
     * @return
     */
    public static String getName(int modelNum) {
        
        return hwModelConf.getProperty(Integer.toString(modelNum));
    }
}
