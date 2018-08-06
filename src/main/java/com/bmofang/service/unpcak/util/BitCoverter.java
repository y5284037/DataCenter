package com.bmofang.service.unpcak.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**********************************************
 *
 //Copyright© 2014 冷云能源科技有限公司.版权所有
 *
 *文件名  ：  DataCenterTest.java
 *文件描述：  字节数组以小端模式(高位高地址)转换为各字节长度的int整型.
 *修改日期：  2018-06-06 17:56.
 *文件作者：  Arike.Y 
 *
 **********************************************/

public class BitCoverter {
    
    //todo 增加有符号int转换函数
    /**
     * 将byte数组从指定索引开始的2个字节以小端模式转换为一个Uint16
     *
     * @param byteArr    字节数组
     * @param startIndex 开始索引(下标)
     * @return 能正确表示无符号16位整型的int数
     */
    public static int toUint16(byte[] byteArr, int startIndex) {
         /*int Uint16Num =0;
        for (int i = 0; i <= 1; i++) {
            Uint16Num += (byteArr[startIndex] & 0xFF) << (i << 3);//i<<3等同于i*8,但是位元算效率最高.
            startIndex++;
        }
        return Uint16Num;*/
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArr, startIndex, 2);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return byteBuffer.getShort() & 0xFFFF;
    }
    
    /**
     * 将byte数组从指定索引开始的4个字节以小端模式转换为一个Uint32
     *
     * @param byteArr    字节数组
     * @param startIndex 开始索引(下标)
     * @return 能正确表示无符号32位整型的long数
     */
    public static long toUint32(byte[] byteArr, int startIndex) {
        /*long Uint32Num = 0;
        for (int i = 0; i <= 3; i++) {
            Uint32Num += (byteArr[startIndex] & 0xFFL) << (i << 3);
            startIndex++;
        }
        return Uint32Num;
        */
        
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArr, startIndex, 4);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return byteBuffer.getInt() & 0xFFFFFFFFFFL;
    }
    
    /**
     * 将byte数组从指定索引开始的8个字节以小端模式转换为一个Uint64
     *
     * @param byteArr    字节数组
     * @param startIndex 开始索引(下标)
     * @return 64位long数.(并不能表示Uint64的最大范围, 因为多了一个符号位, 但是基本不会越界, 所以暂时用有符号64位整型处理)
     */
    public static long toUint64(byte[] byteArr, int startIndex) {
        /*long Uint64Num = 0;
        for (int i = 0; i <= 7; i++) {
            Uint64Num += (byteArr[startIndex] & 0xFFL) << (i << 3);
            startIndex++;
        }
        
        return Uint64Num;*/
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArr, startIndex, 8);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        
        return byteBuffer.getLong();
        
    }
    
    /**
     * 将long数按照小端模式转换为长度为8的字节数组
     *
     * @param num 需要转换的数据
     * @return 小端byte数组
     */
    public static byte[] getBytes(long num) {
        /*byte[] retVal = new byte[8];
        
        for (int i = 0; i < 8; i++) {
            retVal[i] = (byte) num;
            num >>= 8;
        }
        return retVal;*/
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putLong(num);
        
        return byteBuffer.array();
        
    }
    
    /**
     * 将int数按照小端模式转换为长度为4的字节数组
     *
     * @param num 需要转换的数据
     * @return 小端byte数组
     */
    public static byte[] getBytes(int num) {
        /*byte[] retVal = new byte[4];
        
        for (int i = 0; i < 4; i++) {
            retVal[i] = (byte) num;
            num >>= 8;
        }
        return retVal;*/
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putInt(num);
        
        return byteBuffer.array();
    }
    
    /**
     * 将short数按照小端模式转换为长度为2的字节数组
     *
     * @param num 需要转换的数据
     * @return 小端byte数组
     */
    public static byte[] getBytes(short num) {
        /*byte[] retVal = new byte[2];
        
        for (int i = 0; i < 2; i++) {
            retVal[i] = (byte) num;
            num >>= 8;
        }
        return retVal;*/
        ByteBuffer byteBuffer = ByteBuffer.allocate(2);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putShort(num);
        
        return byteBuffer.array();
    }
}


