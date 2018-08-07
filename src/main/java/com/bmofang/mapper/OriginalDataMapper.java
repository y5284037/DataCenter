package com.bmofang.mapper;

import com.bmofang.bean.OriginalData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  DataCenter.java
 *文件描述：  原始数据表操作映射接口
 *修改日期：  2018-08-07 11:17.
 *文件作者：  Arike.Y 
 *
 **********************************************/

@Mapper
@Repository
public interface OriginalDataMapper {
    
    @Insert("INSERT INTO OriginalData(DCUID,pkg_ID,collect_time,data) VALUES(#{DCUID},#{pkgID},#{collectTime},#{data})")
    boolean add(OriginalData originalData);
    
}
