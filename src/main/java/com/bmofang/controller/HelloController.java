package com.bmofang.controller;

import com.bmofang.bean.OriginalData;
import com.bmofang.bean.Student;
import com.bmofang.mapper.OriginalDataMapper;
import com.bmofang.mapper.StudentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  springboot.java
 *文件描述：  this the first springBoot demo
 *修改日期：  2018-06-19 11:10.
 *文件作者：  Arike.Y 
 *
 **********************************************/

@Controller
@Slf4j
public class HelloController {
    
    
    /**
     * 测试Controller
     */
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    private OriginalDataMapper originalDataMapper;
    
    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("now", DateFormat.getInstance().format(new Date()));
        /*return name+age;*/
        return "hello";
    }
    
    @GetMapping("/list")
    public String findAll(Model model) {
        List<Student> students = studentMapper.findall();
        model.addAttribute("students", students);
        System.out.println(studentMapper);
        System.out.println(originalDataMapper);
        return "listStudent";
    }
    
    @PostMapping("/student")
    public String insertStudent(Student student) {
        studentMapper.insert(student);
        return "redirect:list";
    }
    
    @GetMapping("/student")
    public String insertPage() {
        return "insert";
    }
    
    @PostMapping("/onestudent")
    public String deleteStudent(Integer id) {
        System.out.println(id);
        studentMapper.delete(id);
        return "redirect:list";
    }
}
