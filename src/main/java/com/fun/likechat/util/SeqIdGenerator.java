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
}
