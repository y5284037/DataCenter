package com.bmofang.service.data.unpack;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bmofang.bean.OriginalData;
import com.bmofang.mapper.OriginalDataMapper;
import com.bmofang.service.data.ack.ServerRecvDCUPortDataAck;
import com.bmofang.service.data.constant.DCUDataPkgType;
import com.bmofang.service.data.constant.ProtocolVersion;
import com.bmofang.service.data.filter.PortDataFilter;
import com.bmofang.service.data.model.DCUCollectData;
import com.bmofang.service.data.model.DCUDataPkgInfo;
import com.bmofang.service.data.model.DCUInfo;
import com.bmofang.service.data.model.DCUPortData;
import com.bmofang.service.data.rabbitMQ.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  DataCenterTest.java
 *文件描述：  解包类.
 *修改日期：  2018-06-06 15:43.
 *文件作者：  Arike.Y
 *
 **********************************************/

@Component
public class UnPackProcess {
    
    private final OriginalDataMapper originalDataMapper;
    private final DCUPortDataPkgParser_P1 dcuPortDataPkgParser_p1;
    private final DCUPortDataPkgParser_P2 dcuPortDataPkgParser_p2;
    private final DCUDataPkgParser dcuDataPkgParser;
    private final TimeSyncHandler timeSyncHandler;
    private final Producer producer;
    private final PortDataFilter portDataFilter;
    private HashMap<Long, ServerRecvDCUPortDataAck> dataPkgAcks;
    private Base64.Encoder encoder;
    
    @Autowired
    
    public UnPackProcess(OriginalDataMapper originalDataMapper, DCUPortDataPkgParser_P1 dcuPortDataPkgParser_p1, DCUPortDataPkgParser_P2 dcuPortDataPkgParser_p2, DCUDataPkgParser dcuDataPkgParser, TimeSyncHandler timeSyncHandler, Producer producer,PortDataFilter portDataFilter) {
        this.originalDataMapper = originalDataMapper;
        this.dcuPortDataPkgParser_p1 = dcuPortDataPkgParser_p1;
        this.dcuPortDataPkgParser_p2 = dcuPortDataPkgParser_p2;
        this.dcuDataPkgParser = dcuDataPkgParser;
        this.timeSyncHandler = timeSyncHandler;
        this.dataPkgAcks = new HashMap<>();
        this.encoder = Base64.getEncoder();
        this.producer = producer;
        this.portDataFilter = portDataFilter;
    }
    
    /**
     * 解包函数
     *
     * @param routingKey 路由键
     * @param dtuID      数据传输单元ID
     * @param dtuData    DCU采集数据包
     */
    public void unpackData(String routingKey, String dtuID, byte[] dtuData) {
        DCUDataPkgInfo DCUDataPkgInfo = new DCUDataPkgInfo();
        DCUDataPkgInfo.setRecvTime(System.currentTimeMillis());
        DCUDataPkgInfo.setDcuInfo(new DCUInfo());
        int unpackedBytes = dcuDataPkgParser.unpackDCUDataPkgInfo(DCUDataPkgInfo, dtuData);
        unpackedBytes = dcuDataPkgParser.unpackDCUInfo(DCUDataPkgInfo.getDcuInfo(), dtuData, unpackedBytes);
        
        DCUCollectData collectData = new DCUCollectData();
        
        switch (DCUDataPkgInfo.getProtocolVerNum()) {
            case ProtocolVersion.PROTOCOL_1:
                HandleDCUDataPkg_P1(routingKey, dtuID, DCUDataPkgInfo, collectData, dtuData, unpackedBytes);
                break;
            case ProtocolVersion.PROTOCOL_2:
                HandleDCUDataPkg_P2(routingKey, dtuID, DCUDataPkgInfo, collectData, dtuData, unpackedBytes);
                break;
            default:
        }
//        System.out.println(DCUDataPkgInfo);
//        System.out.println(collectData);
    }
    
    /**
     * 冰魔方数据处理函数
     *
     * @param routingKey     路由键
     * @param dtuID          数据传输单元ID
     * @param DCUDataPkgInfo DCU数据包信息
     * @param collectData    DCU端口采集数据
     * @param dtuData        DCU采集数据包
     * @param offset         偏移量
     */
    private void HandleDCUDataPkg_P1(String routingKey, String dtuID, DCUDataPkgInfo DCUDataPkgInfo, DCUCollectData collectData, byte[] dtuData, int offset) {
        
        HashMap<String, List<DCUPortData>> data = new HashMap<>();
        collectData.setData(data);
        
        switch (DCUDataPkgInfo.getDataPkgType()) {
            //端口数据包
            case DCUDataPkgType.DCU_PORT_ACQ_DATA:
                if (dcuPortDataPkgParser_p1.Unpack(collectData, dtuID, DCUDataPkgInfo, dtuData, offset)) {
                    HandleDCUPortData(routingKey, dtuID, collectData, DCUDataPkgInfo);
                }
                break;
            //时间同步请求包
            case DCUDataPkgType.TIME_SYNC_REQUEST:
                //todo 此处使用时间时间同步请求数据包的设备信息进行上报。
                timeSyncHandler.handleTimeSyncRequest(routingKey, dtuID, DCUDataPkgInfo.getDcuInfo(), dtuData, offset);
                break;
            default:
        }
    }
    
    /**
     * 冰精数据处理函数
     *
     * @param routingKey     路由键
     * @param dtuID          数据传输单元ID
     * @param DCUDataPkgInfo DCU数据包信息
     * @param collectData    DCU端口采集数据
     * @param dtuData        DCU采集数据包
     * @param offset         偏移量
     */
    private void HandleDCUDataPkg_P2(String routingKey, String dtuID, DCUDataPkgInfo DCUDataPkgInfo, DCUCollectData collectData, byte[] dtuData, int offset) {
        
        HashMap<String, List<DCUPortData>> data = new HashMap<>();
        collectData.setData(data);
        
        switch (DCUDataPkgInfo.getDataPkgType()) {
            //端口数据包
            case DCUDataPkgType.DCU_PORT_ACQ_DATA:
                if (dcuPortDataPkgParser_p2.Unpack(collectData, dtuID, DCUDataPkgInfo, dtuData, offset)) {
                    HandleDCUPortData(routingKey, dtuID, collectData, DCUDataPkgInfo);
                }
                break;
            //时间同步请求包
            case DCUDataPkgType.TIME_SYNC_REQUEST:
                timeSyncHandler.handleTimeSyncRequest(routingKey, dtuID, DCUDataPkgInfo.getDcuInfo(), dtuData, offset);
                break;
            default:
        }
    }
    
    /**
     * 数据解析后处理函数
     *
     * @param routingKey     路由键
     * @param dtuID          数据传输单元ID
     * @param collectData    DCU端口采集数据
     * @param DCUDataPkgInfo DCU数据包信息
     */
    private void HandleDCUPortData(String routingKey, String dtuID, DCUCollectData collectData, DCUDataPkgInfo DCUDataPkgInfo) {
        // 处理一个收到的合法的、非重复的数据包
        OriginalData originalData = new OriginalData();
        long dcuID = DCUDataPkgInfo.getDcuInfo().getDcuID();
        int pkgID = collectData.getPkgID();
        long collectTimestamp = collectData.getCollectTimestamp();
        HashMap<String, List<DCUPortData>> data = collectData.getData();
        String portData = JSON.toJSONString(data);
        String filterString = JSON.toJSONString(portDataFilter.filter(data));
        System.out.println(filterString);
//        System.out.println(portData);
        originalData.setDCUID(dcuID);
        originalData.setPkgID(pkgID);
        originalData.setCollectTime(collectTimestamp);
        originalData.setData(portData);
        //todo  添加了数据持久化的代码 √
        originalDataMapper.add(originalData);
        System.out.println(originalData);
//        System.out.println(originalDataMapper);
//        System.out.println(JSON.toJSONString(dataPkgAcks));
        // 5.向监测器返回数据包接收回执
        AckRecvDCUPortDataPkg(routingKey, dtuID, dcuID, pkgID, collectTimestamp);
    }
    
    /**
     * 发送数据包回执
     *
     * @param dtuID            数据传输单元ID
     * @param dcuID            数据采集单元ID
     * @param pkgID            DCU数据包ID
     * @param collectTimestamp DCU数据采集时间
     */
    private void AckRecvDCUPortDataPkg(String rouyingKey, String dtuID, long dcuID, int pkgID, long collectTimestamp) {
        ServerRecvDCUPortDataAck serverRecvDCUPortDataAck;
        
        if (dataPkgAcks.containsKey(dcuID)) {
            serverRecvDCUPortDataAck = dataPkgAcks.get(dcuID);
        } else {
            serverRecvDCUPortDataAck = new ServerRecvDCUPortDataAck();
            dataPkgAcks.put(dcuID, serverRecvDCUPortDataAck);
        }
        serverRecvDCUPortDataAck.ackQueue.offer(serverRecvDCUPortDataAck.new RecvDCUPortDataAck((int) dcuID, (short) pkgID));
        JSONObject dtuOutData = new JSONObject();
        dtuOutData.put("DTUID", dtuID);
        dtuOutData.put("Data", encoder.encodeToString(serverRecvDCUPortDataAck.Serialize()));
        producer.send(rouyingKey, dtuOutData.toString().getBytes());
    }
}
