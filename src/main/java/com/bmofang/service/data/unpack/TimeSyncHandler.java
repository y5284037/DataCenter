package com.bmofang.service.data.unpack;

import com.alibaba.fastjson.JSONObject;
import com.bmofang.service.data.MQClient.Produce;
import com.bmofang.service.data.model.DCUInfo;
import com.bmofang.service.data.util.BitCoverter;
import com.bmofang.service.data.ack.ServerTimeSyncReply;

import java.util.Base64;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  DataCenterTest.java
 *文件描述：  时间同步处理类
 *修改日期：  2018-06-13 10:24.
 *文件作者：  Arike.Y 
 *
 **********************************************/

public class TimeSyncHandler {
    
    private Produce produce = new Produce();
    
    /**
     * 处理时间同步请求
     *
     * @param routingKey 路由绑定键
     * @param dtuID      数据传输单元ID
     * @param dcuInfo    数据采集单元信息
     * @param dtuData    采集数据包
     * @param offset     偏移量
     */
    public void handleTimeSyncRequest(String routingKey, String dtuID, DCUInfo dcuInfo, byte[] dtuData, int offset) {
        long dcuTimeSyncReqID = BitCoverter.toUint64(dtuData, offset);
        long currentTime = System.currentTimeMillis();
        doTimeSyncRequest(routingKey, dtuID, dcuInfo, dcuTimeSyncReqID, currentTime);
    }
    
    /**
     * 发送时间同步应答
     *
     * @param routingKey       路由绑定键
     * @param dtuID            数据传输单元ID
     * @param dcuInfo          数据采集单元信息
     * @param dcuTimeSyncReqID 监测器时间同步请求ID
     * @param currentTime      当前系统时间(ms)
     */
    private void doTimeSyncRequest(String routingKey, String dtuID, DCUInfo dcuInfo, long dcuTimeSyncReqID, long currentTime) {
        ServerTimeSyncReply reply = new ServerTimeSyncReply();
        reply.setDcuTimeSyncReqID(dcuTimeSyncReqID);
        reply.setServerTime(currentTime / 1000);
        produce.publish(routingKey, makeDtuOutData(dtuID, reply.serialize()));
    }
    
    /**
     * 序列化时间同步应答
     *
     * @param dtuID   数据传输单元ID
     * @param dtuData 需要序列化的应答数据
     * @return 序列化之后的数据包
     */
    private byte[] makeDtuOutData(String dtuID, byte[] dtuData) {
        Base64.Encoder encoder = Base64.getEncoder();
        JSONObject dtuOutData = new JSONObject();
        dtuOutData.put("DTUID", dtuID);
        dtuOutData.put("Data", encoder.encodeToString(dtuData));
        return dtuOutData.toString().getBytes();
    }
    
    /**
     * java不支持指针传递,所以只有把这个函数写到执行函数内,否则就需要使用对象对基本数据进行包装,在对象内操作.无意义
     */
   /* private int unpackTimeSyncReqPkg(long dcuTimeSyncReqID, byte[] dtuData, int offset) {
        dcuTimeSyncReqID = BitCoverter.toUint64(dtuData, offset);
        return SizeOf.INT_64;
    }*/
}