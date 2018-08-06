package com.bmofang.service.data.constant;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  DataCenterTest.java
 *文件描述：  端口范围约束
 *修改日期：  2018-06-11 14:48.
 *文件作者：  Arike.Y 
 *
 **********************************************/

public class Convention {
    
    public static final int NUM_PORT_A = 10;     // 电流或电压模拟信号端口的数目。
    public static final int NUM_PORT_R = 10;    // 电阻信号端口数量。
    public static final int NUM_PORT_KWH = 2;  // 电量信号端口数。
    public static final int NUM_PORT_DV = 10; // 数字电压信号端口数。
    public static final int NUM_PORT_C = 20; // 接触器信号端口的数量。
    
    public static final int TOTAL_PORT_NUM = 52; // 端口总数
    
    //不同端口的最后一个端口位置.
    public static final int ACC_NUM_PORT_A = NUM_PORT_A;                           // 10
    public static final int ACC_NUM_PORT_R = ACC_NUM_PORT_A + NUM_PORT_R;         // 20
    public static final int ACC_NUM_PORT_KWH = ACC_NUM_PORT_R + NUM_PORT_KWH;    // 22
    public static final int ACC_NUM_PORT_DV = ACC_NUM_PORT_KWH + NUM_PORT_DV;   // 32
    public static final int ACC_NUM_PORT_C = ACC_NUM_PORT_DV + NUM_PORT_C;     // 52
    
    public static final int TYPE_PORT_A = 0x00;
    public static final int TYPE_PORT_R = 0x20;
    public static final int TYPE_PORT_KWH = 0x40;
    public static final int TYPE_PORT_DV = 0x60;
    public static final int TYPE_PORT_C = 0x80;
}
