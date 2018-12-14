package com.test.util;

import java.util.ArrayList;

/**
 * Created by CuncheW1 on 2017/8/10.
 */

public class StrUtil {
    // ArrayList<String> 转 String[]
    public static String[] trans(ArrayList<String> als){
        String[] sa=new String[als.size()];
        als.toArray(sa);
        return sa;
    }

    // String[] 转 ArrayList<String>
    public static ArrayList<String> trans(String[] sa){
        ArrayList<String> als=new ArrayList<String>(0);
        for(int i=0;i<sa.length;i++){
            als.add(sa[i]);
        }
        return als;
    }

    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        // 由高位到低位
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }
}
