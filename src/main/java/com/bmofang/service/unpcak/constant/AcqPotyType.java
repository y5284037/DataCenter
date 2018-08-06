package com.bmofang.service.unpcak.constant;

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

public class AcqPotyType {
    
    public static final byte ACQ_PORT_TYPE_INIT = 0;         //初始值(无效值）
    public static final byte ACQ_PORT_TYPE_A = 1;
    public static final byte ACQ_PORT_TYPE_R = 2;
    public static final byte ACQ_PORT_TYPE_C = 3;
    public static final byte ACQ_PORT_TYPE_DV = 4;
    public static final byte ACQ_PORT_TYPE_KWH = 5;
    public static final byte ACQ_PORT_TYPE_KWH_MB = 6;   // Modbus 485 采集电能
    public static final byte ACQ_PORT_TYPE_F = 7;       // PLC模拟量值，如温度、压力，湿度等等（暂不包含频率、累计时间，因为端口保护等原则不同）
    public static final byte ACQ_PORT_TYPE_B = 8;     // PLC开关或者报警值
    public static final byte ACQ_PORT_TYPE_D = 9;    // 从PLC采集的电能值，在通信中用byte64
    public static final byte ACQ_PORT_TYPE_RF = 10; // 虚拟通信端口，普通浮点数(float型); 最后转换成32位
    public static final byte ACQ_PORT_TYPE_BT = 11;// 板载温度，byte16
  
    
}
