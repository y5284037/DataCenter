package com.bmofang.service.data;

import com.bmofang.service.data.MQClient.Consum;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  DataCenterTest.java
 *文件描述：  项目入口,只负责开启功能
 *修改日期：  2018-06-06 15:43.
 *文件作者：  Arike.Y
 *
 **********************************************/

public class Start {
    public static void main(String[] args) {
        Consum consum = new Consum();
        consum.startConsum();
    }
}
