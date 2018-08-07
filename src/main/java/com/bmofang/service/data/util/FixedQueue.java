package com.bmofang.service.data.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  DataCenterTest.java
 *文件描述：  固定长度的FIFO QUEUE
 *修改日期：  2018-06-20 14:16.
 *文件作者：  Arike.Y 
 *
 **********************************************/

public class FixedQueue<E> extends LinkedList<E> {
    //队列长度
    private int limit;
    
    public FixedQueue(int limit) {
        super();
        this.limit = limit;
    }
    
    @Override
    public boolean offer(E e) {
        if (size() >= limit) {
            poll();
        }
        return super.offer(e);
    }
}
