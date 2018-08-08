package com.bmofang;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  DataCenter.java
 *文件描述：  测试类
 *修改日期：  2018-08-08 11:30.
 *文件作者：  Arike.Y 
 *
 **********************************************/

public class test {
    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        System.out.println(test.class.getResource("/conf/MQTaskConfig.json").getPath());
    }
}
