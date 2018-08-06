package com.bmofang.service.data.model;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  DataCenterTest.java
 *文件描述：  DCU采集数据包
 *修改日期：  2018-06-06 15:43.
 *文件作者：  Arike.Y
 *
 **********************************************/
@Data
public class DCUCollectData {
        private int pkgID;			     // 从DCU发送到数据中心的每个数据包的ID
        private long collectTimestamp;	// Unix时间戳在8字节的整数值中表示数据收集时间。
        private HashMap<Byte/*portType*/, List<DCUPortData>> data;
}
