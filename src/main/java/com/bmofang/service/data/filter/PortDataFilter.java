package com.bmofang.service.data.filter;

import com.bmofang.service.data.constant.CommonConvention;
import com.bmofang.service.data.model.DCUPortData;
import com.bmofang.service.data.model.DigitSignalData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  DataCenter.java
 *文件描述：  端口数据过滤
 *修改日期：  2018-08-14 10:38.
 *文件作者：  Arike.Y 
 *
 **********************************************/

@Component
public class PortDataFilter {
    
    private final TreeMap<String, Object> filteredData;
    
    @Autowired
    public PortDataFilter() {
        filteredData = new TreeMap<>();
        
    }
    
    @SuppressWarnings("unchecked")
    public TreeMap<String, Object> filter(HashMap<String, List<DCUPortData>> portData) {
        //清空过滤数据集合
        filteredData.clear();
        //获取到所有端口类型的set集合
        Set<String> keySet = portData.keySet();
        //循环遍历端口类型结合
        for (String portType : keySet) {
            //根据端口类型拿到该端口类型的所有端口数据
            List<DCUPortData> dataList = portData.get(portType);
            if (portType.equals("F") || portType.equals("A")) {//A,F类端口过滤
                for (DCUPortData dcuPortData : dataList) {
                    String portName = portType + dcuPortData.getPortNum();
                    float portValue = (float) dcuPortData.getPortValue();
                    if (portValue >= CommonConvention.PORT_DATA_MIN_F && portValue <= CommonConvention.PORT_DATA_MAX_F) {
                        filteredData.put(portName, portValue);
                    }
                }
            } else if (portType.equals("B") || portType.equals("DV") || portType.equals("C")) {//B类
                for (DCUPortData dcuPortData : dataList) {
                    //获取端口名称,格式:B1,B2
                    String portName = portType + dcuPortData.getPortNum();
                    //获取到每个端口的跳变数据集合
                    List<DigitSignalData> list = (List<DigitSignalData>) dcuPortData.getPortValue();
                    //switchValue用于过滤到同一时间戳的多个跳变量,只保留最后一个.
                    HashMap<String, Integer> switchValue = new HashMap<>();
                    for (DigitSignalData digitSignalData : list) {
                        switchValue.put(digitSignalData.getTime(), digitSignalData.getValue());
                    }
                    //声明过滤后跳变数据集合
                    List<Object> filterList = new ArrayList<>();
                    //将单条跳变几率转换为包含2个元素的数组.并将每一条记录作为filterList元素
                    for (String time : switchValue.keySet()) {
                        int collectValue = switchValue.get(time);
                        if (collectValue == 0 || collectValue == 1) {
                            Object[] fillArr = new Object[2];
                            fillArr[0] = time;
                            fillArr[1] = collectValue;
                            filterList.add(fillArr);
                        }
                    }
                    filteredData.put(portName, filterList);
                }
            } else if (portType.equals("KWH")) {
                for (DCUPortData dcuPortData : dataList) {
                    String portName = portType + dcuPortData.getPortNum();
                    float portValue = (float) dcuPortData.getPortValue();
                    filteredData.put(portName, portValue);
                }
            } else if (portType.equals("D")) {//D类
                for (DCUPortData dcuPortData : dataList) {
                    double portValue = (double) dcuPortData.getPortValue();
                    if (portValue >= CommonConvention.PORT_DATA_MIN_D && portValue <= CommonConvention.PORT_DATA_MAX_D) {
                        String portName = portType + dcuPortData.getPortNum();
                        filteredData.put(portName, portValue);
                    }
                }
            } else if (portType.equals("R")) {//R类, 冰精自采
                for (DCUPortData dcuPortData : dataList) {
                    float portValue = (float) dcuPortData.getPortValue();
                    if (portValue >= CommonConvention.PORT_DATA_MIN_R && portValue <= CommonConvention.PORT_DATA_MAX_R) {
                        String portName = portType + dcuPortData.getPortNum();
                        filteredData.put(portName, portValue);
                    }
                }
            } else if (portType.equals("BT")) {//板载温度
                for (DCUPortData dcuPortData : dataList) {
                    float portValue = (float) dcuPortData.getPortValue();
                    if (portValue >= CommonConvention.PORT_DATA_MIN_BT && portValue <= CommonConvention.PORT_DATA_MAX_BT) {
                        String portName = portType + dcuPortData.getPortNum();
                        filteredData.put(portName, portValue);
                    }
                }
            } else if (portType.equals("RF")) {//RF类
                for (DCUPortData dcuPortData : dataList) {
                    double portValue = (double) dcuPortData.getPortValue();
                    if (portValue >= CommonConvention.PORT_DATA_MIN_RF && portValue <= CommonConvention.PORT_DATA_MAX_RF) {
                        String portName = portType + dcuPortData.getPortNum();
                        filteredData.put(portName, portValue);
                    }
                }
            }
        }
        return filteredData;
    }
}