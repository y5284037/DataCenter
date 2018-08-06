package com.bmofang.service.unpcak.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  DataCenterTest.java
 *文件描述：  数字信号数据
 *修改日期：  2018-06-11 15:15.
 *文件作者：  Arike.Y 
 *
 **********************************************/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DigitSignalData {
    private String time;
    private int value;
}
