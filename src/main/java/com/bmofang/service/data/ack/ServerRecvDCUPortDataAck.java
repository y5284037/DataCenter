package com.bmofang.service.data.ack;

import com.bmofang.service.data.constant.SizeOf;
import com.bmofang.service.data.constant.DataCenterPkgType;
import com.bmofang.service.data.util.BitCoverter;
import com.bmofang.service.data.util.FixedQueue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  DataCenterTest.java
 *文件描述：  数据包回执操作类
 *修改日期：  2018-06-20 11:13.
 *文件作者：  Arike.Y 
 *
 **********************************************/
@Component
public class ServerRecvDCUPortDataAck {
    private final int kLatestAckCount = 20;//注意dcu数据pkg ID包含19个以前从DTU发送的pkg ID。
    
    public FixedQueue<RecvDCUPortDataAck> ackQueue;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class RecvDCUPortDataAck {
        private int DCUID;
        private Short DCUDataPkgID;
    }
    
    /**
     * 在构造内对ackQueue进行了初始化,添加了20个无效值(DCUID=0,DCUDataPkgID=0).
     */
    public ServerRecvDCUPortDataAck() {
        ackQueue = new FixedQueue<>(kLatestAckCount);
        int DCUID = 0;
        short DCUDataPkgID = 0;
        for (int i = 0; i < 20; i++) {
            ackQueue.offer(new RecvDCUPortDataAck(DCUID, DCUDataPkgID));
        }
    }
    
    /**
     * 数据包回执序列化
     *
     * @return 序列化后的数据包
     */
    public byte[] Serialize() {
        int offset = 0;
        byte[] binaryData = new byte[SizeOf.INT_8 + kLatestAckCount * (SizeOf.INT_32 + SizeOf.INT_16)];
        byte[] elem;
    
        byte kDataPkgType = DataCenterPkgType.RECV_DCU_PORT_DATA_ACK;
        elem = new byte[]{kDataPkgType};
        System.arraycopy(elem, 0, binaryData, offset, elem.length);
        offset += elem.length;
        
        for (RecvDCUPortDataAck recvDCUPortDataAck : ackQueue) {
            elem = BitCoverter.getBytes(recvDCUPortDataAck.getDCUID());
            System.arraycopy(elem, 0, binaryData, offset, elem.length);
            offset += elem.length;
            
            elem = BitCoverter.getBytes(recvDCUPortDataAck.getDCUDataPkgID());
            System.arraycopy(elem, 0, binaryData, offset, elem.length);
            offset += elem.length;
        }
        return binaryData;
    }
}
