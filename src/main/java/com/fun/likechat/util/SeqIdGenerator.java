package com.fun.likechat.util;

import java.util.Random;

public class SeqIdGenerator {
    public static String generate(){
        StringBuffer seqId = new StringBuffer();
        seqId.append(System.currentTimeMillis());
        int num = new Random().nextInt(9999);
        seqId.append(num);
        return seqId.toString();
    }
    
    /*
     * 返回长度为【strLength】的随机数，在前面补0
     */
    public static String getFixLenthString(int strLength) {
        Random rm = new Random();
        // 获得随机数
//        double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);
        double pross = (1 + rm.nextDouble());
        // 将获得的获得随机数转化为字符串
        String fixLenthString = String.valueOf(pross);
        // 返回固定的长度的随机数
        return fixLenthString.substring(2, strLength + 2);
    }
    
    public static void main(String[] args) {
    	for(int i = 0; i <100; i++) {
    		System.out.println(getFixLenthString(8));
//    		 System.out.println((1 +  new Random().nextDouble()));
        }

    }
}
