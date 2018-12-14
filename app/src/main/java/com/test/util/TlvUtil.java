package com.test.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by CuncheW1 on 2017/4/22.
 */

public class TlvUtil {
    public static final int CODE_VALUE_OVERLENGTH = 51;
    public static final int CODE_LENGTH_OVERLENGTH = 52;
    public static final int CODE_PARAMS_INEXISTENCE = 53;

    public TlvUtil() {
    }

    public static Map<String, String> tlvToMap(String tlv) {
        return tlvToMap(hexStringToByte(tlv));
    }

    public static String mapToTlvStr(Map<String, String> map) {
        return bcd2str(mapToTlv(map));
    }

    public static byte[] mapToTlv(Map<String, String> map) {
        if(map == null) {
            throw new RuntimeException("map数据不能为null");
        } else {
            int len = 0;
            Iterator tlvData = map.entrySet().iterator();

            while(tlvData.hasNext()) {
                Map.Entry pos = (Map.Entry)tlvData.next();
                if(pos.getValue() != null) {
                    int lenght = ((String)pos.getValue()).length() / 2;
                    if(lenght > 0) {
                        if(lenght > '\uffff') {
                            throw new RuntimeException("value长度不能超过65535*2");
                        }

                        if(lenght <= 127) {
                            len += 2;
                        }

                        if(lenght > 127 && lenght <= 255) {
                            len += 4;
                        }

                        if(lenght > 255 && lenght <= '\uffff') {
                            len += 6;
                        }

                        len += ((String)pos.getValue()).length();
                        len += ((String)pos.getKey()).length();
                    }
                }
            }

            byte[] var9 = new byte[len / 2];
            int var10 = 0;
            Iterator var11 = map.entrySet().iterator();

            while(var11.hasNext()) {
                Map.Entry entry = (Map.Entry)var11.next();
                if(entry.getValue() != null) {
                    byte[] value = hexStringToByte((String)entry.getValue());
                    int lenght1 = value.length;
                    if(lenght1 > 0) {
                        if(lenght1 > '\uffff') {
                            throw new RuntimeException("value长度不能超过65535*2");
                        }

                        byte[] key = hexStringToByte((String)entry.getKey());
                        System.arraycopy(key, 0, var9, var10, key.length);
                        var10 += key.length;
                        if(lenght1 <= 127 && lenght1 > 0) {
                            var9[var10] = (byte)lenght1;
                            ++var10;
                        }

                        if(lenght1 > 127 && lenght1 <= 255) {
                            var9[var10] = -127;
                            ++var10;
                            var9[var10] = (byte)lenght1;
                            ++var10;
                        }

                        if(lenght1 > 255 && lenght1 <= '\uffff') {
                            var9[var10] = -126;
                            ++var10;
                            var9[var10] = (byte)(lenght1 >> 8 & 255);
                            ++var10;
                            var9[var10] = (byte)(lenght1 & 255);
                            ++var10;
                        }

                        System.arraycopy(value, 0, var9, var10, lenght1);
                        var10 += lenght1;
                    }
                }
            }

            return var9;
        }
    }

    public static Map<String, String> tlvToMap(byte[] tlv) {
        if(tlv == null) {
            throw new RuntimeException("tlv数据不能为null");
        } else {
            HashMap map = new HashMap();
            int index = 0;

            while(true) {
                while(index < tlv.length) {
                    byte[] tag;
                    if((tlv[index] & 31) == 31 && (tlv[index + 1] & 128) == 128) {
                        tag = new byte[3];
                        System.arraycopy(tlv, index, tag, 0, 3);
                        index += 3;
                        index = copyData(tlv, map, index, tag);
                    } else if((tlv[index] & 31) == 31) {
                        tag = new byte[2];
                        System.arraycopy(tlv, index, tag, 0, 2);
                        index += 2;
                        index = copyData(tlv, map, index, tag);
                    } else {
                        tag = new byte[1];
                        System.arraycopy(tlv, index, tag, 0, 1);
                        ++index;
                        index = copyData(tlv, map, index, tag);
                    }
                }

                return map;
            }
        }
    }

    private static int copyData(byte[] tlv, Map<String, String> map, int index, byte[] tag) {
        int length = 0;
        if(tlv[index] >> 7 == 0) {
            length = tlv[index];
            ++index;
        } else {
            int value = tlv[index] & 127;
            ++index;
            if(value > 2) {
                throw new RuntimeException("tlvL字段字节长度不大于3");
            }

            for(int i = 0; i < value; ++i) {
                length <<= 8;
                length += tlv[index] & 255;
                ++index;
            }
        }

        byte[] var7 = new byte[length];
        System.arraycopy(tlv, index, var7, 0, length);
        index += length;
        map.put(bcd2str(tag), bcd2str(var7));
        return index;
    }

    public static String bcd2str(byte[] bcds) {
        char[] ascii = "0123456789abcdef".toCharArray();
        byte[] temp = new byte[bcds.length * 2];

        for(int res = 0; res < bcds.length; ++res) {
            temp[res * 2] = (byte)(bcds[res] >> 4 & 15);
            temp[res * 2 + 1] = (byte)(bcds[res] & 15);
        }

        StringBuffer var5 = new StringBuffer();

        for(int i = 0; i < temp.length; ++i) {
            var5.append(ascii[temp[i]]);
        }

        return var5.toString().toUpperCase();
    }

    public static byte[] hexStringToByte(String hex) {
        hex = hex.toUpperCase();
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
        return (byte)"0123456789ABCDEF".indexOf(c);
    }

    public static void main(String[] args) {
        HashMap map = new HashMap();
        map.put("5F11", (Object)null);
        map.put("5F12", "00120100");
        map.put("5F13", (Object)null);
        Object tlv = null;
        byte[] tlv1 = mapToTlv(map);
        System.out.println(bcd2str(tlv1));
        Map map1 = null;
        map1 = tlvToMap(tlv1);
        Iterator var4 = map1.keySet().iterator();

        while(var4.hasNext()) {
            String key = (String)var4.next();
            System.out.print("key = " + key);
            System.out.println(" ||  value = " + (String)map1.get(key));
        }

    }

    public static class TlvExcetion extends Exception {
        private static final long serialVersionUID = 5876132721837945560L;
        private int errCode;

        public TlvExcetion(String msg) {
            this(0, msg);
        }

        public TlvExcetion(int code, String msg) {
            super(msg);
            this.errCode = code;
        }

        public int getErrCode() {
            return this.errCode;
        }
    }
}
