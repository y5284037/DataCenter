package com.bmofang.mapper;

import com.bmofang.bean.Student;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  springboot.java
 *文件描述：  学生类映射
 *修改日期：  2018-06-26 14:32.
 *文件作者：  Arike.Y 
 *
 **********************************************/

@Mapper
@Repository
public interface StudentMapper {
    @Select("SELECT * FROM student")
    List<Student> findall();
    
    @Insert("INSERT INTO student(student_id,name,age,sex) VALUES(#{studentID},#{name},#{age},#{sex})")
    void insert(Student student);
    
    @Delete("DELETE FROM student WHERE id = #{id}")
    void delete(Integer id);
}
