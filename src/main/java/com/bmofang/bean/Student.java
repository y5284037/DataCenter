package com.bmofang.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  springboot.java
 *文件描述：  学生类
 *修改日期：  2018-06-26 14:29.
 *文件作者：  Arike.Y 
 *
 **********************************************/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Repository
public class Student {
    private Integer id;
    private Integer studentID;
    private String name;
    private Integer age;
    private String sex;
}
