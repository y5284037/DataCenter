package com.bmofang.service.data.unpack;

import com.bmofang.service.data.constant.AcqPotyType;
import com.bmofang.service.data.constant.CommonConvention;
import com.bmofang.service.data.constant.SizeOf;
import com.bmofang.service.data.model.DCUCollectData;
import com.bmofang.service.data.model.DCUDataPkgInfo;
import com.bmofang.service.data.model.DCUPortData;
import com.bmofang.service.data.model.DigitSignalData;
import com.bmofang.service.data.util.BitCoverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  DataCenterTest.java
 *文件描述：  冰心端口数据包解析类
 *修改日期：  2018-06-12 15:16.
 *文件作者：  Arike.Y 
 *
 **********************************************/
@Component
public class DCUPortDataPkgParser_P2 {
    
    private final DigitSignalDataParser digitSignalDataParser ;
    
    @Autowired
    public DCUPortDataPkgParser_P2(DigitSignalDataParser digitSignalDataParser) {
        this.digitSignalDataParser = digitSignalDataParser;
    }
    
    /**
     * 冰心解包入口
     *
     * @param collectData    冰心端口数据实体类
     * @param dtuID          数据传输单元ID
     * @param DCUDataPkgInfo 监测器数据包信息
     * @param dtuData        监测器采集数据包
     * @param offset         偏移量
     * @return 是否解包成功
     */
    public boolean Unpack(DCUCollectData collectData, String dtuID, DCUDataPkgInfo DCUDataPkgInfo, byte[] dtuData, int offset) {
        //端口采集数据包解包
        doUnpack(collectData, dtuData, DCUDataPkgInfo,offset);
        return true;
    }
    
    /**
     * 冰心数据包解包函数
     *
     * @param collectData 采集数据包实体类
     * @param dtuData     DTU数据包
     * @param offset      偏移量
     */
    private void doUnpack(DCUCollectData collectData, byte[] dtuData,DCUDataPkgInfo DCUDataPkgInfo, int offset) {
        int unpackedBytes = 0;
        //解包打包ID和采集时间
        collectData.setPkgID(BitCoverter.toUint16(dtuData, offset + unpackedBytes));
        unpackedBytes += SizeOf.INT_16;//移动偏移量
        collectData.setCollectTimestamp(BitCoverter.toUint64(dtuData, offset + unpackedBytes));
        unpackedBytes += SizeOf.INT_64;
    
        //获取重复数据包判断值
        String key = new StringBuilder().append(DCUDataPkgInfo.getDcuInfo().getDcuID()).append(collectData.getPkgID()).append(collectData.getCollectTimestamp()).toString();
        //todo 加入HashSet重复数据包缓存,用于重复数据包判断.
        
        //读取端口种类个数
        int portTypeNum = dtuData[offset + unpackedBytes];
        unpackedBytes += SizeOf.INT_8;
        for (int i = 0; i < portTypeNum; i++) {
            //端口的类型,比如F,B
            byte portType = dtuData[offset + unpackedBytes];
            unpackedBytes += SizeOf.INT_8;
            //获取端口的开始和结束索引号(临时版本就按照全部打包)
            int first_port = BitCoverter.toUint16(dtuData, offset + unpackedBytes);
            unpackedBytes += SizeOf.INT_16;
            int last_port = BitCoverter.toUint16(dtuData, offset + unpackedBytes);
            unpackedBytes += SizeOf.INT_16;
            unpackedBytes += unpackPortData(collectData.getData(), collectData.getCollectTimestamp(), portType, first_port, last_port, dtuData, offset + unpackedBytes);
        }
    }
    
    /**
     * 解包冰心端口数据
     *
     * @param portData    端口数据存放集合
     * @param collectTime 采集时间
     * @param portType    端口类型
     * @param first_port  起始端口号
     * @param last_port   最后端口号
     * @param dtuData     原始数据
     * @param offset      偏移量
     */
    private int unpackPortData(HashMap<Byte, List<DCUPortData>> portData, long collectTime, byte portType, int first_port, int last_port, byte[] dtuData, int offset) {
        //声明变量
        int unpackedBytes = 0;
        List<DCUPortData> dcuPortData = new ArrayList<>();
        int portNum = first_port;
        if (portType == AcqPotyType.ACQ_PORT_TYPE_F) {
            for (; portNum <= last_port; portNum++) {
                short data = BitCoverter.toInt16(dtuData, offset + unpackedBytes);
                unpackedBytes += SizeOf.INT_16;
                dcuPortData.add(new DCUPortData(portNum, (float) data / CommonConvention.FLOAT_AMPLIFY_FACTOR));
            }
            portData.put(portType, dcuPortData);
        } else if (portType == AcqPotyType.ACQ_PORT_TYPE_B) {//开关报警类
            for (; portNum <= last_port; portNum++) {
                List<DigitSignalData> digitSignalDataList = new ArrayList<>();
                unpackedBytes += digitSignalDataParser.UnpackDigitSignalData(digitSignalDataList, collectTime, dtuData, offset + unpackedBytes);
                dcuPortData.add(new DCUPortData(portNum, digitSignalDataList));
            }
            portData.put(portType, dcuPortData);
        } else if (portType == AcqPotyType.ACQ_PORT_TYPE_R) {//电阻信号
            for (; portNum <= last_port; portNum++) {
                short data = BitCoverter.toInt16(dtuData, offset + unpackedBytes);
                unpackedBytes += SizeOf.INT_16;
                dcuPortData.add(new DCUPortData(portNum, (float) data / CommonConvention.FLOAT_AMPLIFY_FACTOR));
            }
            portData.put(portType, dcuPortData);
        } else if (portType == AcqPotyType.ACQ_PORT_TYPE_D) {//电能采集
            for (; portNum <= last_port; portNum++) {
                long data = BitCoverter.toInt64(dtuData, offset + unpackedBytes);
                unpackedBytes += SizeOf.INT_64;
                dcuPortData.add(new DCUPortData(portNum, (double) data / CommonConvention.FLOAT_AMPLIFY_FACTOR));
            }
            portData.put(portType, dcuPortData);
        } else if (portType == AcqPotyType.ACQ_PORT_TYPE_RF) {//RF类数据
            for (; portNum <= last_port; portNum++) {
                int data = BitCoverter.toInt32(dtuData, offset + unpackedBytes);
                unpackedBytes += SizeOf.INT_32;
                dcuPortData.add(new DCUPortData(portNum, (double) data / CommonConvention.FLOAT_AMPLIFY_FACTOR));
            }
            portData.put(portType, dcuPortData);
        } else if (portType == AcqPotyType.ACQ_PORT_TYPE_BT) {
            for (; portNum <= last_port; portNum++) {
                short data = BitCoverter.toInt16(dtuData, offset + unpackedBytes);
                unpackedBytes += SizeOf.INT_16;
                dcuPortData.add(new DCUPortData(portNum, (double) data / CommonConvention.FLOAT_AMPLIFY_FACTOR));
            }
            portData.put(portType, dcuPortData);
        }
        return unpackedBytes;
    }
}
