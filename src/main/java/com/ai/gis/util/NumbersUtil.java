package com.ai.gis.util;

import java.util.Random;

public class NumbersUtil {
    public static String generateTableId(){
        Random random = new Random();
        int length = 10; // 随机字符串的长度
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ut_");
        for (int i = 0; i < length; i++) {
            char randomChar = (char) ('a' + random.nextInt(26)); // 生成小写字母
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }
}
