package com.bmofang.service.data.model;

import lombok.Data;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  DataCenterTest.java
 *文件描述：  DCU数据包信息,用于描述硬件数据包属性
 *修改日期：  2018-06-06 15:43.
 *文件作者：  Arike.Y
 *
 **********************************************/

@Data
public class DCUDataPkgInfo {
    private int protocolVerNum;   // DCU解析协议版本号.
    private byte dataPkgType;    // 数据包类型.
    private long recvTime;
    private DCUInfo dcuInfo ;
}
