/**
 * 解码工具
 */
package com.ai.gis.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class DecodeUtil {
    /**
     * 将16进制字符串转换为byte数组
     * @param hexString
     * @return
     */
    public static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i+1), 16));
        }
        return data;
    }

    /*
    获取shapefile字符编码
    如果存在.cpg文件，则从中读取，否则默认为UTF-8
 */
    public static String getCharSet(String path){
        String charset = "UTF-8";
        int p = path.lastIndexOf(".");
        String cpg = path.substring(0,p) + ".cpg";
//        File file = FileUtil.getFile(cpg);
//        if(file != null) {
//            RandomAccessFile raf = null;
//            try {
//                raf = new RandomAccessFile(cpg, "r");
//                charset = raf.readLine();
//                raf.close();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        return charset;
    }
}
