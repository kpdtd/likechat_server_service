package com.fun.likechat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogFactory {

    private static LogFactory logFactory = new LogFactory();

    private LogFactory() {
    }

    public static LogFactory getInstance() {
        return logFactory;
    }

    /**
     * 获取默认的Logger，默认为控制台
     * 
     * @return Logger
     */
    public Logger getLogger() {
        return LoggerFactory.getLogger("stdout");
    }

    /**
     * 测试方法
     * 
     * @param args
     */
    public static void main(String[] args) {
        String result="";
        for (int i = 0; i < 4; i++) {
            // 调用Math.random()方法
            int num = (int) (Math.random() *10) + 0;
            result=result+num;
        }
        System.out.println(result);
    }

}
