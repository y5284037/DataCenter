package com.bmofang.service.unpcak.unpack;

import com.bmofang.service.unpcak.constant.SizeOf;
import com.bmofang.service.unpcak.constant.CommonConvention;
import com.bmofang.service.unpcak.model.DCUDataPkgInfo;
import com.bmofang.service.unpcak.model.DCUInfo;
import com.bmofang.service.unpcak.util.BitCoverter;
import com.bmofang.service.unpcak.util.DCUHwModel;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  DataCenterTest.java
 *文件描述：  DCU数据包头解析类
 *修改日期：  2018-06-11 11:02.
 *文件作者：  Arike.Y 
 *
 **********************************************/

public class DCUDataPkgParser {
    
    /**
     * 解包硬件的包头,然后根据通信协议解包
     *
     * @param DCUDataPkgInfo DCU数据包信息包实体类
     * @param dtuData        Dcu数据包
     * @return 已解字节数
     */
    public int unpackDCUDataPkgInfo(DCUDataPkgInfo DCUDataPkgInfo, byte[] dtuData) {
        int unpackedBytes = 0;
        //获取协议版本号
        DCUDataPkgInfo.setProtocolVerNum(dtuData[0]);
        unpackedBytes += SizeOf.INT_8;
        //获取数据类型
        DCUDataPkgInfo.setDataPkgType(dtuData[1]);
        unpackedBytes += SizeOf.INT_8;
        return unpackedBytes;
    }
    
    /**
     * 硬件信息，一般情况下结构化的格式不变
     *
     * @param dcuInfo DCU信息实体类
     * @param dtuData DCU数据包
     * @param offset  偏移量
     * @return 已解字节数
     */
    public int unpackDCUInfo(DCUInfo dcuInfo, byte[] dtuData, int offset) {
        int unpackedBytes = 0;
        //获取DCUID
        dcuInfo.setDcuID(BitCoverter.toUint32(dtuData, offset + unpackedBytes));
        unpackedBytes += SizeOf.INT_32;
        //获取DCU主从序号
        dcuInfo.setSlaveIndex(dtuData[offset + unpackedBytes]);
        unpackedBytes += SizeOf.INT_8;
        //获取DCU主机ID
        dcuInfo.setMasterDCUID(BitCoverter.toUint32(dtuData, offset + unpackedBytes));
        unpackedBytes += SizeOf.INT_32;
        //获取硬件版本号
        int hwModelNum = BitCoverter.toUint16(dtuData, offset + unpackedBytes);
        dcuInfo.setHardwareModel(DCUHwModel.getName(hwModelNum));
        unpackedBytes += SizeOf.INT_16;
        //获取固件版本号
        long fw_ver = BitCoverter.toUint32(dtuData, offset + unpackedBytes);
        unpackedBytes += SizeOf.INT_32;
        long fwMajorVersion = (fw_ver & CommonConvention.FW_MAJOR_VER_BITS_AND) >> CommonConvention.FW_MAJOR_VER_RIGHT_SHIFT_BITS;
        long fwMinorVersion = (fw_ver & CommonConvention.FW_MINOR_VER_BITS_AND) >> CommonConvention.FW_MINOR_VER_RIGHT_SHIFT_BITS;
        long fwPatchVersion = (fw_ver & CommonConvention.FW_PATCH_VER_BITS_AND);
        dcuInfo.setFirmwareVersion(String.valueOf(fwMajorVersion) + "." + String.valueOf(fwMinorVersion) + "." + String.valueOf(fwPatchVersion));
        return unpackedBytes + offset;
    }
}
