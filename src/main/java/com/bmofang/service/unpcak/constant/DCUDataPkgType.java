package com.bmofang.service.unpcak.constant;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  DataCenterTest.java
 *文件描述：  DCU数据包类型
 *修改日期：  2018-06-06 15:43.
 *文件作者：  Arike.Y
 *
 **********************************************/

public class DCUDataPkgType {
    
    public static final byte UNKNOWN_DCU_PKG = 0;
    public static final byte TIME_SYNC_REQUEST = 1;   // 时间同步请求由主控DCU发送到服务器。
    public static final byte DCU_PORT_ACQ_DATA = 2;   // DCU端口采集数据
}
