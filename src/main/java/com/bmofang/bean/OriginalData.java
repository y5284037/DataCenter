package com.bmofang.bean;

import lombok.Data;
import org.springframework.stereotype.Repository;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  DataCenter.java
 *文件描述：  原始数据表映射类
 *修改日期：  2018-08-07 11:10.
 *文件作者：  Arike.Y 
 *
 **********************************************/

@Repository
@Data
public class OriginalData {
    private Long ID;
    private Long DCUID;
    private Integer pkgID;
    private Long collectTime;
    private String data;
}
