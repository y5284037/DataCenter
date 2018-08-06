package com.bmofang.service.unpcak.constant;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  DataCenterTest.java
 *文件描述：  数据中心打包类型
 *修改日期：  2018-06-13 15:39.
 *文件作者：  Arike.Y 
 *
 **********************************************/

public class DataCenterPkgType {
    
    public static final byte UNKNOWN_CENTER_PKG = 0;
    public static final byte TIME_SYNC_CMD = 1; // 时间同步命令主动从服务器发送到主控DCU。
    public static final byte TIME_SYNC_REPLY = 2; // 中心对DCU的时间同步请求的回复。
    public static final byte RECV_DCU_PORT_DATA_ACK = 3; // 中心接收DCU端口数据的确认。
}
