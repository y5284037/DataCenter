package com.bmofang.service.data.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  DataCenterTest.java
 *文件描述：  JSON转化工具
 *修改日期：  2018-06-07 16:51.
 *文件作者：  Arike.Y 
 *
 **********************************************/

public class JSONCoverter {
    private static Logger logger = LoggerFactory.getLogger(JSONCoverter.class);
    
    
    /**
     * 根据在resources目录下的Json配置文件生成相应的HashMap对象.
     *
     * @param fileName 配置文件名
     * @return
     */
    public static JSONObject JsonFileToMap(String fileName) {
        
        JSONObject jsonObject = new JSONObject();
        
        File file = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\" + fileName);
        try {
            Reader fileReader = new FileReader(file);
            JSONReader JsonFileReader = new JSONReader(fileReader);
            JsonFileReader.startObject();
            while (JsonFileReader.hasNext()) {//判断是否还有下一组json(key,value).
                String key = JsonFileReader.readString();
                String value = JsonFileReader.readObject().toString();//每读一次object会把行往下移动.下一次读String,就是新的一行的key.
                jsonObject.put(key, value);
            }
            fileReader.close();//关流释放资源
            return jsonObject;
        } catch (FileNotFoundException e) {
            logger.error("无法找到" + fileName + ",请确认该文件在resources下!");
            e.printStackTrace();
            System.exit(-100);//退出状态码,后续补上对应文档.
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-101);
        }
        return null;
    }
}
