package com.test.util;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by CuncheW1 on 2017/3/29.
 */

public class StringUtil {
    public static final String SEPARATOR_LINE = "|";
    public static final String SEPARATOR_LINE_SPLIT = "\\|";

    public StringUtil() {
    }

    public static boolean isEmpty(String s) {
        return s == null?true:(s.trim().equals("")?true:s.trim().equals("null"));
    }

    public static boolean isNumber(String s) {
        try {
            Float.parseFloat(s);
            return true;
        } catch (Exception var2) {
            return false;
        }
    }

    public static boolean isEqualsByte(byte[] src, int srcPos, byte[] bt2, int length) {
        byte[] temp = new byte[length];
        System.arraycopy(src, srcPos, temp, 0, length);
        return Arrays.equals(temp, bt2);
    }

    public static String str2DateTime(String format, String toformat, String time) {
        String str = "";

        try {
            Date date = (new SimpleDateFormat(format)).parse(time);
            str = (new SimpleDateFormat(toformat)).format(date);
        } catch (ParseException var6) {
            ;
        }

        return str;
    }

    public static byte[] shortToByteArray(short s) {
        byte[] targets = new byte[]{(byte)(s & 255), (byte)((s & '\uff00') >> 8)};
        return targets;
    }

    public static byte[] shortToByteArrayTwo(short s) {
        byte[] targets = new byte[]{(byte)((s & '\uff00') >> 8), (byte)(s & 255)};
        return targets;
    }

    public static byte[] shortArrayToByteArray(short[] s) {
        byte[] targets = new byte[s.length * 2];

        for(int i = 0; i < s.length; ++i) {
            byte[] tmp = shortToByteArray(s[i]);
            targets[2 * i] = tmp[0];
            targets[2 * i + 1] = tmp[1];
        }

        return targets;
    }

    public static short[] byteArraytoShort(byte[] buf) {
        short[] targets = new short[buf.length / 2];
        int len = 0;

        for(int i = 0; i < buf.length; i += 2) {
            short vSample = (short)(buf[i] & 255);
            vSample |= (short)((short)buf[i + 1] << 8 & '\uff00');
            targets[len++] = vSample;
        }

        return targets;
    }

    public static String str2HexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();

        for(int i = 0; i < bs.length; ++i) {
            int bit = (bs[i] & 240) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 15;
            sb.append(chars[bit]);
            sb.append(' ');
        }

        return sb.toString().trim();
    }

    public static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];

        for(int i = 0; i < bytes.length; ++i) {
            int n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte)(n & 255);
        }

        try {
            return new String(bytes, "ISO-8859-1");
        } catch (UnsupportedEncodingException var6) {
            return "";
        }
    }

    public static byte[] str2bytesISO88591(String str) {
        try {
            return str.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static byte[] str2bytesGBK(String str) {
        try {
            return str.getBytes("GBK");
        } catch (UnsupportedEncodingException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static String byteToGBK(byte[] data) {
        String result = "";

        try {
            result = new String(data, "GBK");
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
        }

        return result;
    }

    public static String byte2HexStr(byte[] b) {
        if(b == null) {
            return "";
        } else {
            String stmp = "";
            StringBuilder sb = new StringBuilder("");

            for(int n = 0; n < b.length; ++n) {
                stmp = Integer.toHexString(b[n] & 255);
                sb.append(stmp.length() == 1?"0" + stmp:stmp);
            }

            return sb.toString().toUpperCase().trim();
        }
    }

    public static byte[] hexStr2Bytes(String src) {
        boolean m = false;
        boolean n = false;
        if(src.length() % 2 != 0) {
            src = "0" + src;
        }

        int l = src.length() / 2;
        byte[] ret = new byte[l];

        for(int i = 0; i < l; ++i) {
            int var6 = i * 2 + 1;
            int var7 = var6 + 1;
            ret[i] = Integer.decode("0x" + src.substring(i * 2, var6) + src.substring(var6, var7)).byteValue();
        }

        return ret;
    }

    public static String strToUnicode(String strText) throws Exception {
        StringBuilder str = new StringBuilder();

        for(int i = 0; i < strText.length(); ++i) {
            char c = strText.charAt(i);
            String strHex = Integer.toHexString(c);
            if(c > 128) {
                str.append("\\u" + strHex);
            } else {
                str.append("\\u00" + strHex);
            }
        }

        return str.toString();
    }

    public static String unicodeToString(String hex) {
        int t = hex.length() / 6;
        StringBuilder str = new StringBuilder();

        for(int i = 0; i < t; ++i) {
            String s = hex.substring(i * 6, (i + 1) * 6);
            String s1 = s.substring(2, 4) + "00";
            String s2 = s.substring(4);
            int n = Integer.valueOf(s1, 16).intValue() + Integer.valueOf(s2, 16).intValue();
            char[] chars = Character.toChars(n);
            str.append(new String(chars));
        }

        return str.toString();
    }

    public static int byteToInt(byte[] src) {
        int tmp = 0;

        for(int i = 0; i < src.length; ++i) {
            tmp += src[i] << i * 8 & 255 << i * 8;
        }

        return tmp;
    }

    public static byte[] intToByte(int src) {
        byte[] tmp = new byte[4];

        for(int i = 0; i < tmp.length; ++i) {
            tmp[i] = (byte)(src >> i * 8 & 255);
        }

        return tmp;
    }

    public static byte[] intToBytes2(int value) {
        byte[] src = new byte[]{(byte)(value >> 24 & 255), (byte)(value >> 16 & 255), (byte)(value >> 8 & 255), (byte)(value & 255)};
        return src;
    }

    public static byte[] intToByte1024(int src) {
        byte[] tmp = new byte[1024];

        for(int i = 0; i < tmp.length; ++i) {
            tmp[i] = (byte)(src >> i * 8 & 255);
        }

        return tmp;
    }

    public static String byteToStr(byte[] data) {
        String result = "";

        try {
            result = new String(data, "ISO-8859-1");
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
        }

        return result;
    }

    public static byte[] hexStringToByte(String hex) {
        int len = hex.length() / 2;
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();

        for(int i = 0; i < len; ++i) {
            int pos = i * 2;
            result[i] = (byte)(toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }

        return result;
    }

    private static byte toByte(char c) {
        byte b = (byte)"0123456789ABCDEF".indexOf(c);
        return b;
    }

    public static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);

        for(int i = 0; i < bArray.length; ++i) {
            String sTemp = Integer.toHexString(255 & bArray[i]);
            if(sTemp.length() < 2) {
                sb.append(0);
            }

            sb.append(sTemp.toUpperCase());
        }

        return sb.toString();
    }

    public static byte[] intToByteArray(int i) {
        byte[] targets = new byte[]{(byte)(i & 255), (byte)(i >> 8 & 255), (byte)(i >> 16 & 255), (byte)(i >> 24 & 255)};
        return targets;
    }

    public static int byteArrayToInt(byte[] b) {
        boolean result = false;
        int result1 = b[0] & 255 | b[1] << 8 & '\uffff' | b[2] << 16 & 16777215 | b[3] << 24 & -1;
        return result1;
    }

    public static byte[] formatWithZero(byte[] data) {
        int mark = 0;

        for(int result = 0; result < data.length; ++result) {
            if(data[result] == 0) {
                mark = result;
                break;
            }
        }

        if(mark == 0) {
            mark = data.length;
        }

        byte[] var3 = new byte[mark];
        System.arraycopy(data, 0, var3, 0, mark);
        return var3;
    }

    public static String insertComma(String s, int len) {
        if(s != null && s.length() >= 1) {
            DecimalFormat formater = null;
            double num = Double.parseDouble(s);
            if(len == 0) {
                formater = new DecimalFormat("###,###");
            } else {
                StringBuffer buff = new StringBuffer();
                buff.append("###,###.");

                for(int i = 0; i < len; ++i) {
                    buff.append("#");
                }

                formater = new DecimalFormat(buff.toString());
            }

            return formater.format(num);
        } else {
            return "";
        }
    }


}
