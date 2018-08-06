package com.bmofang.service.unpcak.ack;

import com.bmofang.service.unpcak.constant.SizeOf;
import com.bmofang.service.unpcak.constant.DataCenterPkgType;
import com.bmofang.service.unpcak.util.BitCoverter;
import lombok.Data;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  DataCenterTest.java
 *文件描述：  时间同步应答序列化工具类.
 *修改日期：  2018-06-13 15:35.
 *文件作者：  Arike.Y 
 *
 **********************************************/
@Data
public class ServerTimeSyncReply {
    
    private long dcuTimeSyncReqID;       // 从DCU发送的时间同步请求ID。
    private long serverTime;            // 服务器的系统时间，以时间戳的形式。
    
    /**
     * 序列化时间同步应答.
     *
     * @return
     */
    public byte[] serialize() {
        int offset = 0;
        byte[] binaryData = new byte[SizeOf.INT_8 + SizeOf.INT_64 + SizeOf.INT_64];
        byte[] elem;
        elem = new byte[]{DataCenterPkgType.TIME_SYNC_REPLY};
        System.arraycopy(elem, 0, binaryData, offset, elem.length);
        offset += elem.length;
        elem = BitCoverter.getBytes(dcuTimeSyncReqID);
        System.arraycopy(elem, 0, binaryData, offset, elem.length);
        offset += elem.length;
        elem = BitCoverter.getBytes(serverTime);
        System.arraycopy(elem, 0, binaryData, offset, elem.length);
        return binaryData;
    }
}
