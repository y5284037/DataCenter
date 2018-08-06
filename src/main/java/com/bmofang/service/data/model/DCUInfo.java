package com.bmofang.service.data.model;

import lombok.Data;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  DataCenterTest.java
 *文件描述：  采集端口类型; 为了兼容; 不能改动.
 *修改日期：  2018-06-06 15:43.
 *文件作者：  Arike.Y
 *
 **********************************************/
@Data
public class DCUInfo {
    private long dcuID;                     // 每个DCU的ID，无论它是主还是从。
    private byte slaveIndex;               // DCU的主从索引。
    private long masterDCUID;             // 主机DCU的ID
    private String hardwareModel;        // DCU硬件型号编号
    private String firmwareVersion;     // DCU固件版本号
}
