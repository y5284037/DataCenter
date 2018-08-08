package com.bmofang.service.data.util;

import com.alibaba.fastjson.JSONObject;

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

public class DCUHwModel {
    
    private static JSONObject hwModel;
    
    /**
     * 进行json对象的初始化
     */
    static {
        hwModel = new JSONObject();
        LoadHardwareModelConfig();//在静态代码块中执行是为了作为驱动加载一次
    }
    
    /**
     * 读取硬件配置文件
     */
    @SuppressWarnings("unchecked")
    private static void LoadHardwareModelConfig() {
        hwModel = JSONCoverter.JsonFileToMap("conf/HardwareModelConfig.json");
    }
    
    /**
     * 获取到硬件版本号
     *
     * @param modelNum 对应版本号值的key
     * @return
     */
    public static String getName(int modelNum) {
        
        return hwModel.getString(Integer.toString(modelNum));
    }
}
