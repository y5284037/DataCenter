package com.bmofang.service.data.unpack;

import com.bmofang.service.data.constant.SizeOf;
import com.bmofang.service.data.constant.CommonConvention;
import com.bmofang.service.data.model.DigitSignalData;
import com.bmofang.service.data.util.BitCoverter;

import java.util.List;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  DataCenterTest.java
 *文件描述：  数字信号解析器
 *修改日期：  2018-06-12 16:01.
 *文件作者：  Arike.Y 
 *
 **********************************************/

public class DigitSignalDataParser {
    
    /**
     * 解包开关，报警以及C类，DV类,B类的数据包信息
     *
     * @param digitSignalDataList 用于存储数字信号数据的集合
     * @param collectTime         采集时间
     * @param dtuData             DTU数据
     * @param offset              偏移量
     * @return 已解字节数
     */
    public int UnpackDigitSignalData(List<DigitSignalData> digitSignalDataList, long collectTime, byte[] dtuData, int offset) {
        int unpackedBytes = 0;
        int num = dtuData[offset + unpackedBytes] & 0xFF;
        unpackedBytes += SizeOf.INT_8;
        for (int i = 0; i < num; i++) {
            int data = BitCoverter.toUint16(dtuData, offset + unpackedBytes);
            unpackedBytes += SizeOf.INT_16;
            /*
             * 协议1和协议2.1所对应的实现方法。
			 * Calculate time & changing status value
			 * DCU使用16位数据中最高的1位来表示只能为0或1的数字信号值。
               UInt16 deltaTime = (UInt16)(data & (UInt16)0x7FFF); // 0x7FFF == 0111111111111111.
               UInt64 time = collectTime - deltaTime;
               UInt8 value = (UInt8)(data >> CommonConvention.DIGIT_SIG_VALUE_RIGHT_SHIFT_BITS);
             */
            
            // 2016.11.10, 需要注意的是目前有两个编号的通信协议：
            // 编号1：对应目前已经出厂的所有冰魔方所使用的协议。
            // 编号2：目前出厂的所有冰心所使用的协议；该协议有两个版本——2.0与2.1
            //      版本2.0：由薛骏所定义并实现的针对最初版本的冰心所使用。
            //      版本2.1：由周川在2.0基础上进行改进的，目前冰心使用中的协议。
            //
            // 关于“数字信号数据”的打包方式，由于协议2.1兼容以前的协议2.0和协议1，
            // 因此这里的实现只保留了协议2.1所对应的实现方法。
            int deltaTime = data & 0x3FF;// 0x03FF = 0011 1111 1111.
            long time = collectTime - deltaTime;
            int value = data >> CommonConvention.DIGIT_SIG_VALUE_RIGHT_SHIFT_BITS;
            if (value == 0x20) {//因为需要考虑兼容老版本协议,而老版本带回来的值只会是0 00000或1 00000(0x20).
                value = 1;
            }
            digitSignalDataList.add(new DigitSignalData(String.valueOf(time), value));
        }
        return unpackedBytes;
    }
}
