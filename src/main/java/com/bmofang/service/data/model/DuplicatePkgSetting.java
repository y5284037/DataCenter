package com.bmofang.service.data.model;

import lombok.Data;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  DataCenterTest.java
 *文件描述：  重复数据包设置, 默认从配置文件读取
 *修改日期：  2018-06-06 15:43.
 *文件作者：  Arike.Y
 *
 **********************************************/

@Data
public class DuplicatePkgSetting {
    private int validPkgDays;
    private boolean enableDuplicateMech;
    private int duplicatePkgDays;
}
