package com.bmofang.service.data.constant;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  DataCenterTest.java
 *文件描述：  端口无效值
 *修改日期：  2018-06-06 15:43.
 *文件作者：  Arike.Y
 *
 **********************************************/

public class CommonConvention {
    
  
    public static final byte FLOAT_AMPLIFY_FACTOR = 100;
    // 端口类型的无效值.
    // F、R、BT类端口数据会在数据采集时用float数据类型表示，并在结构化时乘以100转换为int16_t型整数(可表示的数值范围是[-32768, 32767]),
    // 因此F、R、BT类端口数据的可用取值范围是[-327.68, 327.67], 我们选择[-327.00， 327.00]作为F、R、BT类端口的有效数据范围，
    // 并将[327.01, 327.67]这段数字范围用于定义无效数据。
    // 目前针对F、R、BT类端口仅定义以下无效值：
    // 1.“初始无效值”——  327.01
    // 2.“读值失败无效值”——  327.02
    // 3.“采集异常值”—— 327.03
    
    //F类端口
    public static final float INVALID_PORT_DATA_INIT_F = 327.67f;  //和以前的无效值相同
    public static final float INVALID_PORT_DATA_READ_F = 327.02f;  //
    public static final float INVALID_PORT_DATA_ACQ_F = 327.03f;
    
    public static final float PORT_DATA_MIN_F = -327.00f;
    public static final float PORT_DATA_MAX_F = 327.00f;
    
    //R类
    public static final float INVALID_PORT_DATA_INIT_R = 327.01f;
    public static final float INVALID_PORT_DATA_READ_R = 327.02f;
    public static final float INVALID_PORT_DATA_ACQ_R = 327.03f;
    
    public static final float PORT_DATA_MIN_R = -327.00f;
    public static final float PORT_DATA_MAX_R = 327.00f;
    
    //BT类
    public static final float INVALID_PORT_DATA_INIT_BT = 327.01f;
    public static final float INVALID_PORT_DATA_READ_BT = 327.02f;
    public static final float INVALID_PORT_DATA_ACQ_BT = 327.03f;
    
    public static final float PORT_DATA_MIN_BT = -327.00f;
    public static final float PORT_DATA_MAX_BT = 327.00f;
    
    
    // 现在由于需要对B类端口数据定义无效值，且考虑到一个采集周期最大不超过900秒，因此使用低10位来表示跳变时间差足以（最大可表示1024秒），
    // 因此现在我们修改B类端口采集数据的结构化方式为：高6位用与表示B类端口采集的数据值（可表示的数值范围是[0, 63]），低10位用于表示跳变的时间差。
    // 由于B类端口的有效数据只能是0或者1，因此我们可以选择[2,63]这个区间内的值来定义无效数据。
    // 但与其他端口不同的是：B类端口采集到的数据是一个采集周期内的多次跳变的值，因此我们会使用一个byte_t类型的数值来表示发生跳变的次数，
    // 而这个次数在初始化时值为0，即初始化状态为“未发生任何变化”。因此无需针对B类端口采集的“单次跳变值”定义“初始无效值”。
    // B类端口的无效值定义如下：
    
    public static final byte INVALID_PORT_DATA_READ_B = 2;//读取失败
    public static final byte INVALID_PORT_DATA_ACQ_B = 3;//采集失败
    
    public static final byte PORT_DATA_MIN_B = 0;
    public static final byte PORT_DATA_MAX_B = 1;
    
    // RF类端口数据会在数据采集时用double数据类型表示，并在结构化时乘以100转换为int32_t型整数(可表示的数值范围是[-2147483648, 2147483647]),
    // 因此RF类端口数据的可用取值范围是[-21474836.48, 21474836.47], 我们选择[-21474836.00， 21474836.00]作为RF类端口的有效数据范围，
    // 并将[21474836.01, 21474836.47]这段数字范围用于定义无效数据。
    // RF类端口的无效值定义如下：
    
    public static final double INVALID_PORT_DATA_INIT_RF = 21474836.01;  //初始无效值
    public static final double INVALID_PORT_DATA_READ_RF = 21474836.02;
    public static final double INVALID_PORT_DATA_ACQ_RF = 21474836.03;
    
    public static final double PORT_DATA_MIN_RF = -21474836.00;
    public static final double PORT_DATA_MAX_RF = 21474836.00;
    
    // D类端口数据会在数据采集时用double数据类型表示，并在结构化时乘以100转换为int64_t型整数(可表示的数值范围是[-2^63, 2^63-1]),
    // 但是double数据类型所能绝对保证的精度范围为15位有效数字，
    // 因此D类端口数据的可选择有效数据范围是[-9999999999999.99, 9999999999999.99]
    // 我们选择[-9999999999999.00， 9999999999999.00]作为D类端口的有效数据范围，
    // 并将[9999999999999.01, 9999999999999.99]这段数字范围用于定义无效数据。
    // 目前D类端口的无效值定义如下：
    
    public static final double INVALID_PORT_DATA_INIT_D = 9999999999999.01;
    public static final double INVALID_PORT_DATA_READ_D = 9999999999999.02;
    public static final double INVALID_PORT_DATA_ACQ_D = 9999999999999.03;
    
    public static final double PORT_DATA_MIN_D = -9999999999999.00;
    public static final double PORT_DATA_MAX_D = 9999999999999.00;
    
    // 旧版本使用第一位表示B类数据，低15位表示跳变时间
    // 新版本使用高6位表示B类数据，低10位表示跳变时间，因此设计的最大时间时1024秒
    public static final byte DIGIT_SIG_VALUE_RIGHT_SHIFT_BITS = 10;
    
    // 使用一个整数数字来还原DCU固件版本，其中最高的10位为主要版本，
    // 中间10位的小版本，最低12位的bug修复补丁版本.
    public static final byte FW_MAJOR_VER_RIGHT_SHIFT_BITS = 22;
    public static final byte FW_MINOR_VER_RIGHT_SHIFT_BITS = 12;
    
    public static final int FW_MAJOR_VER_BITS_AND = 0xFFC00000; // (2^10-1) << 22
    public static final int FW_MINOR_VER_BITS_AND = 0x003FF000; // (2^10-1) << 12
    public static final int FW_PATCH_VER_BITS_AND = 0x00000FFF; // 2^12-1
}
