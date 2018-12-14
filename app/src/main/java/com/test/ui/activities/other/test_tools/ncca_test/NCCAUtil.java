package com.test.ui.activities.other.test_tools.ncca_test;

import android.os.Environment;
import android.util.Log;

import com.socsi.exception.SDKException;
import com.socsi.smartposapi.gmalgorithm.GMAlgorithm;
import com.socsi.smartposapi.gmalgorithm.SM2KeyPair;
import com.socsi.utils.StringUtil;
import com.test.util.DialogUtil;
import com.test.util.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by CuncheW1 on 2017/8/14.
 */

public class NCCAUtil {
    private final String TAG = "NCCAUtil";
    private static GMAlgorithm gmAlgorithm;
    private StringBuffer stringBuffer;
    private StringBuffer resultBuffer;

    public NCCAUtil() {
        gmAlgorithm = new GMAlgorithm();
        stringBuffer = new StringBuffer();
        resultBuffer = new StringBuffer();
    }

    public String sm2Encrypt() {
        try {
            gmAlgorithm.init();
            stringBuffer.setLength(0);
            resultBuffer.setLength(0);
            ArrayList<String> list = FileUtilComm.read("SM2验证数据-加密-10.txt");
            if (list.size() <= 0) {
                return "SM2验证数据-解密-10.txt";
            }

            ArrayList<Map<String, String>> mapList = new ArrayList<Map<String,String>>();
            Map<String, String> map = new HashMap<String, String>();
            for (int i=0; i<list.size(); i++) {
                String val = list.get(i).split("=")[1];
                if(i%5==0){
                    map.put("pubkey", val);//公钥
                }else if(i%5==1){
    				map.put("prikey", val);//私钥
                }else if(i%5==2){

                }else if(i%5==3){
                    map.put("ciphertext", val);//密文
                }else if(i%5==4){
                    map.put("clearText", val);//明文
                    LogUtil.d(TAG, "SM2 encrypt:" + val);
                    mapList.add(map);
                    map = new HashMap<String, String>();
                }
            }

            for(int i=0;i<mapList.size();i++){
                map = mapList.get(i);
                String pubkey = map.get("pubkey").trim();
                String clearText = map.get("clearText").trim();
                String ciphertext = map.get("ciphertext").trim();
                String privateKey = map.get("prikey").trim();

                byte[] pubkeyBytes= StringUtil.hexStr2Bytes(pubkey);
                byte[] clearTextBytes= StringUtil.hexStr2Bytes(clearText);
                byte[] resultBytes = gmAlgorithm.SM2((byte)0x00, (byte)0x00, pubkeyBytes, clearTextBytes);
                String result = StringUtil.byte2HexStr(resultBytes);
//                LogUtil.e(TAG, "sm2加密结果："+result);
//                stringBuffer.append("加密成功!公钥模:"+pubkey+"    加密结果："+result+"\r\n");
                stringBuffer.append("公钥= " + pubkey + "\r\n");
                stringBuffer.append("私钥= " + privateKey + "\r\n");
                int len = clearText.length() / 2;
                stringBuffer.append("明文长度= " + String.format("%08x", len) + "\r\n");
                stringBuffer.append("密文= " + result+"\r\n");
                stringBuffer.append("明文= " + clearText + "\r\n\r\n");

                // 可以注释掉，因为SM2每次加密后的数据不相同，所以可以通过这里查看解密后的明文是否和原来明文一致
                byte[] prikeyBytes = StringUtil.hexStr2Bytes(privateKey);
                byte[] decDataBytes = gmAlgorithm.SM2((byte)0x01, (byte)0x01, prikeyBytes, resultBytes);
                String decData = StringUtil.byte2HexStr(decDataBytes);
                LogUtil.d(TAG, "SM2 decrypt:"+ decData);
                if (clearText.equals(decData)) {
                    resultBuffer.append("SM2加密数据" + i + "成功!!\r\n");
                } else {
                    resultBuffer.append("SM2加密数据" + i + "失败!!\r\n");
                }
            }

            FileUtilComm.writeFile("/guomi/test_results/SM2验证数据-加密-10.txt", stringBuffer.toString());
            return resultBuffer.toString();
        } catch (SDKException e) {
            e.printStackTrace();
        }

        return "执行SM2加密失败";
    }

    public String sm2Decrypt() {
        ArrayList<String> list = FileUtilComm.read("SM2验证数据-解密-10.txt");
        if (list.size() <= 0) {
            return "读取SM2验证数据-解密-10.txt";
        }

        try {
            gmAlgorithm.init();
            stringBuffer.setLength(0);
            resultBuffer.setLength(0);
            ArrayList<Map<String, String>> mapList = new ArrayList<Map<String,String>>();
            Map<String, String> map = new HashMap<String, String>();
            for(int i=0;i<list.size();i++){
                String val = list.get(i).split("=")[1];
                if(i%5==0){
    				map.put("pubkey", val);//公钥
                }else if(i%5==1){
                    map.put("prikey", val);//私钥
                }else if(i%5==2){

                }else if(i%5==3){
                    map.put("ciphertext", val);//密文
                }else if(i%5==4){
                    map.put("clearText", val);//明文
                    mapList.add(map);
                    map = new HashMap<String, String>();
                }
            }

            for (int i=0; i<mapList.size(); i++) {
                map = mapList.get(i);
                String prikey = map.get("prikey").trim();
                String clearText = map.get("clearText").trim();
                String ciphertext = map.get("ciphertext").trim();
                String pubkey = map.get("pubkey").trim();

                byte[] prikeyBytes= StringUtil.hexStr2Bytes(prikey);
                byte[] ciphertextBytes= StringUtil.hexStr2Bytes(ciphertext);
                byte[] resultBytes = gmAlgorithm.SM2((byte)0x01, (byte)0x01, prikeyBytes, ciphertextBytes);
                String result = StringUtil.byte2HexStr(resultBytes);
                if(!result.equals(clearText)){
                    Log.e("sdk", "sm2校验解密失败");
                    stringBuffer.append("sm2校验解密失败!私钥:"+prikey+"\r\n");
                    resultBuffer.append("SM2解密数据" + i + "失败!!\r\n");
                } else {
                    Log.e("sdk","密文:"+ciphertext+ "sm2解密正确,结果为:"+result);

                    stringBuffer.append("公钥= " + pubkey + "\r\n");
                    stringBuffer.append("私钥= " + prikey + "\r\n");
                    int len = result.length() / 2;
                    stringBuffer.append("明文长度= " + String.format("%08x", len) + "\r\n");
                    stringBuffer.append("密文= " + ciphertext+"\r\n");
                    stringBuffer.append("明文= " + result + "\r\n\r\n");

                    resultBuffer.append("SM2解密数据" + i + "成功!!\r\n");
                }
            }

            FileUtilComm.writeFile("/guomi/test_results/SM2验证数据-解密-10.txt", stringBuffer.toString());
            return resultBuffer.toString();
        } catch (SDKException e) {
            e.printStackTrace();
        }

        return "执行SM2解密失败!";
    }

    public String sm2GenerateKeyPair() {
        SM2KeyPair sm2KeyPair = null;
        try {
            gmAlgorithm.init();
            stringBuffer.setLength(0);
            resultBuffer.setLength(0);

            for (int i = 0; i < 10; i++) {
                sm2KeyPair = gmAlgorithm.SM2CreateKeys();
                byte[] priKeyByte = sm2KeyPair.getPrivateKey();
                byte[] pubKeyByte = sm2KeyPair.getPublicKey();
                String priKey = StringUtil.byte2HexStr(priKeyByte);
                String pubKey = StringUtil.byte2HexStr(pubKeyByte);
                Log.i("sdk", "生成私钥priKey=" + priKey);
                Log.i("sdk", "生成公钥pubKey=" + pubKey);
           //     stringBuffer.append("生成私钥对成功!" + "生成私钥priKey=" + priKey + "   生成公钥pubKey=" + pubKey + "\r\n");
                stringBuffer.append("公钥= " + pubKey + "\r\n");
                stringBuffer.append("私钥= " + priKey + "\r\n\r\n");
                resultBuffer.append("SM2产生密钥对" + i + "成功!!\r\n");
            }

            FileUtilComm.writeFile("/guomi/test_results/SM2_密钥对生产-10.txt", stringBuffer.toString());
            return resultBuffer.toString();
        } catch (SDKException e) {
            e.printStackTrace();
        }

        return "生成公私钥对失败!";
    }

    //无预处理签名
    public String sm2SignNoPreSign() {
        ArrayList<String> list = FileUtilComm.read("SM2签名-无预处理-10.txt");
        if (list.size() <= 0) {
            return "读取SM2签名-无预处理-10.txt失败";
        }

        try {
            gmAlgorithm.init();
            stringBuffer.setLength(0);
            resultBuffer.setLength(0);

            ArrayList<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
            Map<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < list.size(); i++) {
                String val = list.get(i).split("=")[1];
                if (i % 6 == 0) {
                    map.put("pubkey", val);//公钥
                } else if (i % 6 == 1) {
                    map.put("prikey", val);//私钥
                } else if (i % 6 == 2) {
                    map.put("signerid", val);//签名者id
                } else if (i % 6 == 3) {

                } else if (i % 6 == 4) {
                    map.put("clearText", val);//签名数据
                } else if (i % 6 == 5) {
                    map.put("ciphertext", val);//结果
                    mapList.add(map);
                    map = new HashMap<String, String>();
                }
            }

            for (int i = 0; i < mapList.size(); i++) {
                map = mapList.get(i);
                String pubkey = map.get("pubkey").trim();
                String prikey = map.get("prikey").trim();
                String signerid = map.get("signerid").trim();
                String clearText = map.get("clearText").trim();
                String ciphertext = map.get("ciphertext").trim();

                byte[] pubkeyBytes = StringUtil.hexStr2Bytes(pubkey);
                byte[] prikeyBytes = StringUtil.hexStr2Bytes(prikey);
                byte[] signeridBytes = StringUtil.hexStr2Bytes(signerid);
                byte[] clearTextBytes = StringUtil.hexStr2Bytes(clearText);
                byte[] resultBytes = gmAlgorithm.SM2Sign(signeridBytes, prikeyBytes, pubkeyBytes, clearTextBytes);
                String result = StringUtil.byte2HexStr(resultBytes);
                Log.i("sdk1", "无预处理签名结果:" + result);
                //stringBuffer.append("无预处理签名结果:" + result + "\r\n");
                stringBuffer.append("公钥= " + pubkey + "\r\n");
                stringBuffer.append("私钥= " + prikey + "\r\n");
                stringBuffer.append("签名者ID= " + signerid + "\r\n");
                int len = clearText.length() / 2;
                stringBuffer.append("签名数据长度= " + String.format("%08x", len) + "\r\n");
                stringBuffer.append("签名数据= " + clearText + "\r\n");
                stringBuffer.append("签名结果= " + result + "\r\n\r\n");

                // 下面两行可以注掉
                boolean resultBoolean = gmAlgorithm.SM2CheckSign(signeridBytes, prikeyBytes, pubkeyBytes, clearTextBytes, resultBytes);
                LogUtil.d(TAG, "SM2 nopre sign verify:" + resultBoolean);
                if (resultBoolean) {
                    resultBuffer.append("SM2无预处理签名" + i + "成功!!\r\n");
                } else {
                    resultBuffer.append("SM2无预处理签名" + i + "失败!!\r\n");
                }
            }
            FileUtilComm.writeFile("/guomi/test_results/SM2签名-无预处理-10.txt", stringBuffer.toString());
            return resultBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "执行无预处理签名失败！";
    }

    //无预处理验签
    public String sm2SignNoPreCheck() {
        ArrayList<String> list = FileUtilComm.read("SM2验签-无预处理-10.txt");
        if (list.size() <= 0) {
            return "读取SM2验签-无预处理-10.txt败";
        }

        try {
            gmAlgorithm.init();
            stringBuffer.setLength(0);
            resultBuffer.setLength(0);

            ArrayList<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
            Map<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < list.size(); i++) {
                String val = list.get(i).split("=")[1];
                if (i % 6 == 0) {
                    map.put("pubkey", val);//公钥
                } else if (i % 6 == 1) {
                    map.put("prikey", val);//私钥
                } else if (i % 6 == 2) {
                    map.put("signerid", val);//签名者id
                } else if (i % 6 == 3) {

                } else if (i % 6 == 4) {
                    map.put("clearText", val);//签名数据
                } else if (i % 6 == 5) {
                    map.put("ciphertext", val);//结果
                    mapList.add(map);
                    map = new HashMap<String, String>();
                }
            }
            for (int i = 0; i < mapList.size(); i++) {
                map = mapList.get(i);
                String pubkey = map.get("pubkey").trim();
                String prikey = map.get("prikey").trim();
                String signerid = map.get("signerid").trim();
                String clearText = map.get("clearText").trim();
                String ciphertext = map.get("ciphertext").trim();

                byte[] pubkeyBytes = StringUtil.hexStr2Bytes(pubkey);
                byte[] prikeyBytes = StringUtil.hexStr2Bytes(prikey);
                byte[] signeridBytes = StringUtil.hexStr2Bytes(signerid);
                byte[] clearTextBytes = StringUtil.hexStr2Bytes(clearText);
                byte[] ciphertextBytes = StringUtil.hexStr2Bytes(ciphertext);
                boolean resultBoolean = gmAlgorithm.SM2CheckSign(signeridBytes, prikeyBytes, pubkeyBytes, clearTextBytes, ciphertextBytes);
                Log.i("sdk1", "无预处理验签结果:" + resultBoolean);
//                stringBuffer.append("无预处理验签结果:" + resultBoolean + "   公钥:" + pubkey + "\r\n");
                if (resultBoolean) {
                    stringBuffer.append("公钥= " + pubkey + "\r\n");
                    stringBuffer.append("私钥= " + prikey + "\r\n");
                    stringBuffer.append("签名者ID= " + signerid + "\r\n");
                    int len = clearText.length() / 2;
                    stringBuffer.append("签名数据长度= " + String.format("%08x", len) + "\r\n");
                    stringBuffer.append("签名数据= " + clearText + "\r\n");
                    stringBuffer.append("签名结果= " + ciphertext + "\r\n\r\n");
                    resultBuffer.append("SM2无预处理验签" + i + "成功!!\r\n");
                } else {
                    stringBuffer.append("    验证结果  " + "验证失败" + "\r\n\r\n");
                    resultBuffer.append("SM2无预处理验签" + i + "失败!!\r\n");
                }
            }

            FileUtilComm.writeFile("/guomi/test_results/SM2验签-无预处理-10.txt", stringBuffer.toString());
            return resultBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "执行无语处理验签失败！！";
    }

    //预处理签名
    public String sm2SignPreSign() {
        ArrayList<String> list = FileUtilComm.read("SM2签名-预处理后-10.txt");
        if (list.size() <= 0) {
            return "读取SM2签名-预处理后-10.txt败";
        }

        try {
            gmAlgorithm.init();
            stringBuffer.setLength(0);
            resultBuffer.setLength(0);

            ArrayList<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
            Map<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < list.size(); i++) {
                String val = list.get(i).split("=")[1];
                if (i % 4 == 0) {
                    map.put("pubkey", val);//公钥
                } else if (i % 4 == 1) {
                    map.put("prikey", val);//私钥
                } else if (i % 4 == 2) {
                    map.put("clearText", val);//签名数据
                } else if (i % 4 == 3) {
                    map.put("ciphertext", val);//结果
                    mapList.add(map);
                    map = new HashMap<String, String>();
                }
            }

            for (int i = 0; i < mapList.size(); i++) {
                map = mapList.get(i);
    			String pubkey = map.get("pubkey").trim();
                String prikey = map.get("prikey").trim();
                String clearText = map.get("clearText").trim();
                String ciphertext = map.get("ciphertext").trim();

    			byte[] pubkeyBytes= StringUtil.hexStr2Bytes(pubkey);
                byte[] prikeyBytes = StringUtil.hexStr2Bytes(prikey);
                byte[] clearTextBytes = StringUtil.hexStr2Bytes(clearText);
                byte[] resultBytes = gmAlgorithm.SM2SignPre(prikeyBytes, clearTextBytes);
                String result = StringUtil.byte2HexStr(resultBytes);
                Log.i("sdk1", "预处理签名结果:" + result);
//                stringBuffer.append("预处理签名结果:" + result + "\r\n");
                stringBuffer.append("公钥= " + pubkey + "\r\n");
                stringBuffer.append("私钥= " + prikey + "\r\n");
                stringBuffer.append("签名数据e= " + clearText + "\r\n");
                stringBuffer.append("签名结果= " + result + "\r\n\r\n");

                // 以下两行可以注掉
                boolean resultBoolean = gmAlgorithm.SM2CheckSignPre(pubkeyBytes, clearTextBytes, resultBytes);
                LogUtil.d(TAG, "SM2 pre sign verify:" + resultBoolean);
                if (resultBoolean) {
                    resultBuffer.append("SM2有预处理签名" + i + "成功!!\r\n");
                } else {
                    resultBuffer.append("SM2有预处理签名" + i + "失败!!\r\n");
                }
            }

            FileUtilComm.writeFile("/guomi/test_results/SM2签名-预处理后-10.txt", stringBuffer.toString());
            return resultBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "执行预处理签名失败！！";
    }

    //预处理验签
    public String sm2SignPreCheck() {
        ArrayList<String> list = FileUtilComm.read("SM2验签-预处理后-10.txt");
        if (list.size() <= 0) {
            return "读取SM2验签-预处理后-10.txt败";
        }

        try {
            gmAlgorithm.init();
            stringBuffer.setLength(0);
            resultBuffer.setLength(0);

            ArrayList<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
            Map<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < list.size(); i++) {
                String val = list.get(i).split("=")[1];
                if (i % 4 == 0) {
                    map.put("pubkey", val);//公钥
                } else if (i % 4 == 1) {
                    map.put("prikey", val);//私钥
                } else if (i % 4 == 2) {
                    map.put("clearText", val);//签名数据
                } else if (i % 4 == 3) {
                    map.put("ciphertext", val);//结果
                    mapList.add(map);
                    map = new HashMap<String, String>();
                }
            }
            for (int i = 0; i < mapList.size(); i++) {
                map = mapList.get(i);
                String pubkey = map.get("pubkey").trim();
                String clearText = map.get("clearText").trim();
                String prikey = map.get("prikey").trim();
                String ciphertext = map.get("ciphertext").trim();

                byte[] pubkeyBytes = StringUtil.hexStr2Bytes(pubkey);
                byte[] clearTextBytes = StringUtil.hexStr2Bytes(clearText);
                byte[] ciphertextBytes = StringUtil.hexStr2Bytes(ciphertext);
                boolean resultBoolean = gmAlgorithm.SM2CheckSignPre(pubkeyBytes, clearTextBytes, ciphertextBytes);
                Log.i("sdk1", "预处理验签结果:" + resultBoolean);
//                stringBuffer.append("预处理验签结果:" + resultBoolean + "   公钥:" + pubkey + "\r\n");
                if (resultBoolean) {
                    stringBuffer.append("公钥= " + pubkey + "\r\n");
                    stringBuffer.append("私钥= " + prikey + "\r\n");
                    stringBuffer.append("签名数据e= " + clearText + "\r\n");
                    stringBuffer.append("签名结果= " + ciphertext + "\r\n\r\n");
                    resultBuffer.append("SM2有预处理验签" + i + "成功!!\r\n");
                } else {
                    stringBuffer.append("有预处理签名验证失败\r\n");
                    resultBuffer.append("SM2有预处理验签" + i + "失败!!\r\n");
                }
            }

            FileUtilComm.writeFile("/guomi/test_results/SM2验签-预处理后-10.txt", stringBuffer.toString());
            return resultBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "执行预处理验签失败！！";
    }

    public String sm3CheckMac(){
        try {
            ArrayList<String> list = FileUtilComm.read("SM3杂凑验证-10.txt");
            if (list.size() <= 0) {
                return "读取SM3杂凑验证-10.txt失败!!";
            }

            gmAlgorithm.init();
            stringBuffer.setLength(0);
            resultBuffer.setLength(0);

            ArrayList<Map<String, String>> mapList = new ArrayList<Map<String,String>>();
            Map<String, String> map = new HashMap<String, String>();
            for(int i=0;i<list.size();i++){
                String val = list.get(i).split("=")[1];
                if(i%3==0){
                }else if(i%3==1){
                    map.put("message", val);
                }else if(i%3==2){
                    map.put("result", val);
                    mapList.add(map);
                    map = new HashMap<String, String>();
                }
            }

            for(int i=0;i<mapList.size();i++){
                map = mapList.get(i);
                String toCheckMessage = map.get("message").trim();
                String toCheckResult = map.get("result").trim();
                byte[] messBytes= StringUtil.hexStr2Bytes(toCheckMessage);
                byte[] result = gmAlgorithm.SM3(messBytes);
                String hash = StringUtil.byte2HexStr(result);
                if(!hash.equals(toCheckResult)){
//                    stringBuffer.append("sm3校验失败！\n消息："+toCheckMessage+"\n预期摘要结果："+toCheckResult+"\n实际摘要结果："+hash+"\r\n");
                    stringBuffer.append("sm3校验失败！\r\n消息："+toCheckMessage+"\n预期摘要结果："+toCheckResult+"\n实际摘要结果："+hash+"\r\n");
                    resultBuffer.append("SM3校验" + i + "失败\r\n");
                }else{
                    Log.i("sdk1","pass!");
//                    stringBuffer.append("sm3校验成功!消息："+toCheckMessage+"\r\n");
                    int len = toCheckMessage.length() / 2;
                    stringBuffer.append("消息长度= " + String.format("%08x", len) + "\r\n");
                    stringBuffer.append("消息= " + toCheckMessage + "\r\n");
                    stringBuffer.append("杂凑值= " + hash + "\r\n\r\n");
                    resultBuffer.append("SM3校验" + i + "成功\r\n");
                }
            }

            FileUtilComm.writeFile("/guomi/test_results/SM3杂凑验证-10.txt", stringBuffer.toString());
            return resultBuffer.toString();
        } catch (Exception e) {
            Log.e("sdk", e.toString());
        }

        return "执行sm3 杂凑验证失败！！";
    }

    //验证mac
    public String sm4CheckMac() {
        ArrayList<String> list = FileUtilComm.read("SM4验证数据-10-MAC.txt");
        if (list.size() <= 0) {
            return "读取SM4验证数据-10-MAC.txt败";
        }

        try {
            gmAlgorithm.init();
            stringBuffer.setLength(0);
            resultBuffer.setLength(0);

            ArrayList<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
            Map<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < list.size(); i++) {
                String val = list.get(i).split("=")[1];
                if (i % 5 == 0) {
                    map.put("key", val);//密钥
                } else if (i % 5 == 1) {
                    map.put("iv", val);//IV
                } else if (i % 5 == 2) {

                } else if (i % 5 == 3) {
                    map.put("clearText", val);//明文
                } else if (i % 5 == 4) {
                    map.put("mac", val);//MAC
                    mapList.add(map);
                    map = new HashMap<String, String>();
                }
            }
            for (int i = 0; i < mapList.size(); i++) {
                map = mapList.get(i);
                String key = map.get("key").trim();
                String iv = map.get("iv").trim();
                String clearText = map.get("clearText").trim();
                String mac = map.get("mac").trim();

                byte[] keyBytes = StringUtil.hexStr2Bytes(key);
                byte[] ivBytes = StringUtil.hexStr2Bytes(iv);
                byte[] clearTextBytes = StringUtil.hexStr2Bytes(clearText);
                byte[] resultBytes = gmAlgorithm.SM4CalculateMAC(keyBytes, clearTextBytes, ivBytes);
                String result = StringUtil.byte2HexStr(resultBytes);
                if (!result.equals(mac)) {
                    Log.e("sdk", "验证数据mac失败!秘钥：" + key);
                    stringBuffer.append("验证数据mac失败!秘钥：" + key + "\r\n");
                    resultBuffer.append("SM4验证MAC" + i + "失败!!\r\n");
                } else {
                    Log.i("sdk", "验证数据mac成功!mac:" + result);
//                    stringBuffer.append("验证数据mac成功!mac:" + result + "\r\n");
                    stringBuffer.append("密钥= " + key + "\r\n");
                    stringBuffer.append("IV= " + iv + "\r\n");
                    int len = clearText.length() / 2;
                    stringBuffer.append("明文长度= " + String.format("%08x", len) + "\r\n");
                    stringBuffer.append("明文= " + clearText + "\r\n");
                    stringBuffer.append("MAC值= " + result + "\r\n\r\n");
                    resultBuffer.append("SM4验证MAC" + i + "成功!!\r\n");
                }
            }

            FileUtilComm.writeFile("/guomi/test_results/SM4验证数据-10-MAC.txt", stringBuffer.toString());
            return resultBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "执行SM4 杂凑验证失败！！";
    }

    //cbc加密
    public String sm4CheckCbcEncrypt () {
        ArrayList<String> list = FileUtilComm.read("SM4验证数据-加密-10-CBC.txt");
        if (list.size() <= 0) {
            return "读取SM4验证数据-加密-10-CBC.txt失败";
        }

        try {
            gmAlgorithm.init();
            stringBuffer.setLength(0);
            resultBuffer.setLength(0);

            ArrayList<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
            Map<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < list.size(); i++) {
                String val = list.get(i).split("=")[1];
                if (i % 5 == 0) {
                    map.put("key", val);//密钥
                } else if (i % 5 == 1) {
                    map.put("iv", val);//IV
                } else if (i % 5 == 2) {

                } else if (i % 5 == 3) {
                    map.put("clearText", val);//明文
                    LogUtil.d(TAG, "SM4 encrypt: " + val);
                } else if (i % 5 == 4) {
                    map.put("mac", val);//密文
                    mapList.add(map);
                    map = new HashMap<String, String>();
                }
            }
            for (int i = 0; i < mapList.size(); i++) {
                map = mapList.get(i);
                String key = map.get("key").trim();
                String iv = map.get("iv").trim();
                String clearText = map.get("clearText").trim();
                String mac = map.get("mac").trim();

                byte[] keyBytes = StringUtil.hexStr2Bytes(key);
                byte[] ivBytes = StringUtil.hexStr2Bytes(iv);
                byte[] clearTextBytes = StringUtil.hexStr2Bytes(clearText);
                byte[] resultBytes = gmAlgorithm.SM4((byte) 0x00, (byte) 0x01, keyBytes, clearTextBytes, ivBytes);
                String result = StringUtil.byte2HexStr(resultBytes);
                if (!result.equals(mac)) {
                    Log.e("sdk", "验证cbc失败!密文：" + mac);
                    stringBuffer.append("验证cbc失败!密文：" + mac + "\r\n");
                    resultBuffer.append("SM4CBC加密" + i + "失败!!\r\n");
                } else {
                    Log.i("sdk", "验证cbc成功!密文:" + result);
//                    stringBuffer.append("验证cbc成功!密文:" + result + "\r\n");
                    stringBuffer.append("密钥= " + key + "\r\n");
                    stringBuffer.append("IV= " + iv + "\r\n");
                    int len = clearText.length() / 2;
                    stringBuffer.append("明文长度= " + String.format("%08x", len) + "\r\n");
                    stringBuffer.append("明文= " + clearText + "\r\n");
                    stringBuffer.append("密文= " + result + "\r\n\r\n");
                    resultBuffer.append("SM4CBC加密" + i + "成功!!\r\n");
                }

                byte[] decDataBytes = gmAlgorithm.SM4((byte)0x01, (byte)0x01, keyBytes, resultBytes, ivBytes);
                LogUtil.d(TAG, "SM4 decrypt: " + StringUtil.byte2HexStr(decDataBytes));
            }

            FileUtilComm.writeFile("/guomi/test_results/SM4验证数据-加密-10-CBC.txt", stringBuffer.toString());
            return resultBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "执行验证SM4 cbc加密失败";
    }

    //cbc解密
    public String sm4CheckCbcDecrypt() {
        ArrayList<String> list = FileUtilComm.read("SM4验证数据-解密-10-CBC.txt");
        if (list.size() <= 0) {
            return "读取SM4验证数据-解密-10-CBC.txt失败";
        }

        try {
            gmAlgorithm.init();
            stringBuffer.setLength(0);
            resultBuffer.setLength(0);

            ArrayList<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
            Map<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < list.size(); i++) {
                String val = list.get(i).split("=")[1];
                if (i % 5 == 0) {
                    map.put("key", val);//密钥
                } else if (i % 5 == 1) {
                    map.put("iv", val);//IV
                } else if (i % 5 == 2) {

                } else if (i % 5 == 3) {
                    map.put("mac", val);//密文
                } else if (i % 5 == 4) {
                    map.put("clearText", val);//明文
                    LogUtil.d(TAG, "SM4 cbc decrypt clear data:" + val);
                    mapList.add(map);
                    map = new HashMap<String, String>();
                }
            }
            for (int i = 0; i < mapList.size(); i++) {
                map = mapList.get(i);
                String key = map.get("key").trim();
                String iv = map.get("iv").trim();
                String mac = map.get("mac").trim();
                String clearText = map.get("clearText").trim();
                byte[] keyBytes = StringUtil.hexStr2Bytes(key);
                byte[] ivBytes = StringUtil.hexStr2Bytes(iv);
                byte[] macBytes = StringUtil.hexStr2Bytes(mac);
                byte[] resultBytes = gmAlgorithm.SM4((byte) 0x01, (byte) 0x01, keyBytes, macBytes, ivBytes);
                String result = StringUtil.byte2HexStr(resultBytes);

                if (!result.equals(clearText)) {
//                    Log.e("sdk", "cbc解密失败!明文：" + clearText);
                    stringBuffer.append("cbc解密失败!明文：" + clearText + "\r\n");
                    resultBuffer.append("SM4CBC解密" + i + "失败!!\r\n");
                } else {
//                    stringBuffer.append("cbc解密成功!明文:" + result + "\r\n");
//                    Log.i("sdk", "cbc解密成功!明文:" + result);

                    stringBuffer.append("密钥= " + key + "\r\n");
                    stringBuffer.append("IV= " + iv + "\r\n");
                    int len = result.length() / 2;
                    stringBuffer.append("明文长度= " + String.format("%08x", len) + "\r\n");
                    stringBuffer.append("密文= " + mac + "\r\n");
                    stringBuffer.append("明文= " + result + "\r\n\r\n");
                    resultBuffer.append("SM4CBC解密" + i + "成功!!\r\n");
                }
            }

            FileUtilComm.writeFile("/guomi/test_results/SM4验证数据-解密-10-CBC.txt", stringBuffer.toString());
            return resultBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "执行SM4 cbc解密失败！！";
    }

    //ecb加密
    public String sm4CheckEcbEncrypt() {
        ArrayList<String> list = FileUtilComm.read("SM4验证数据-加密-10-ECB.txt");
        if (list.size() <= 0) {
            return "读取SM4验证数据-加密-10-ECB.txt失败";
        }

        try {
            gmAlgorithm.init();
            stringBuffer.setLength(0);
            resultBuffer.setLength(0);

            ArrayList<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
            Map<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < list.size(); i++) {
                String val = list.get(i).split("=")[1];
                if (i % 4 == 0) {
                    map.put("key", val);//密钥
                } else if (i % 4 == 1) {

                } else if (i % 4 == 2) {
                    map.put("clearText", val);//明文
                    LogUtil.d(TAG, "SM4 ecb encrypt clear data:" + val);
                } else if (i % 4 == 3) {
                    map.put("mac", val);//密文
                    mapList.add(map);
                    map = new HashMap<String, String>();
                }
            }
            for (int i = 0; i < mapList.size(); i++) {
                map = mapList.get(i);
                String key = map.get("key").trim();
                String clearText = map.get("clearText").trim();
                String mac = map.get("mac").trim();

                byte[] keyBytes = StringUtil.hexStr2Bytes(key);
                byte[] clearTextBytes = StringUtil.hexStr2Bytes(clearText);
                byte[] a = null;
                byte[] resultBytes = gmAlgorithm.SM4((byte) 0x00, (byte) 0x00, keyBytes, clearTextBytes, a);
                String result = StringUtil.byte2HexStr(resultBytes);
                if (!result.equals(mac)) {
//                    Log.e("sdk", "ecb加密失败!密文：" + mac);
                    stringBuffer.append("ecb加密失败!密文：" + mac + "\r\n");
                    resultBuffer.append("SM4 ECB加密" + i + "失败!!\r\n");
                } else {
//                    Log.i("sdk", "ecb加密成功!密文:" + result);
//                    stringBuffer.append("ecb加密成功!密文:" + result + "\r\n");
                    stringBuffer.append("密钥= " + key + "\r\n");
                    int len = clearText.length() / 2;
                    stringBuffer.append("明文长度= " + String.format("%08x", len) + "\r\n");
                    stringBuffer.append("明文= " + clearText + "\r\n");
                    stringBuffer.append("密文= " + result + "\r\n\r\n");
                    resultBuffer.append("SM4 ECB加密" + i + "成功!!\r\n");
                }

                // 以下可以删除
                byte[] decryptDataBytes = gmAlgorithm.SM4((byte)0x01, (byte)0x00, keyBytes, resultBytes, a);
                LogUtil.d(TAG, "SM4 ecb encrypt dec result data:" + StringUtil.byte2HexStr(decryptDataBytes));
            }

            FileUtilComm.writeFile("/guomi/test_results/SM4验证数据-加密-10-ECB.txt", stringBuffer.toString());
            return resultBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "执行SM4 ecb加密失败！！";
    }

    //ecb解密
    public String sm4CheckEcbDecrypt() {
        ArrayList<String> list = FileUtilComm.read("SM4验证数据-解密-10-ECB.txt");
        if (list.size() <= 0) {
            return "读取SM4验证数据-解密-10-ECB.txt失败";
        }

        try {
            gmAlgorithm.init();
            stringBuffer.setLength(0);
            resultBuffer.setLength(0);

            int num = list.size() / 4;
            ArrayList<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
            Map<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < list.size(); i++) {
                String val = list.get(i).split("=")[1];
                if (i % 4 == 0) {
                    map.put("key", val);//密钥
                    num++;
                } else if (i % 4 == 1) {

                } else if (i % 4 == 2) {
                    map.put("mac", val);//密文
                } else if (i % 4 == 3) {
                    map.put("clearText", val);//明文
                    mapList.add(map);
                    map = new HashMap<String, String>();
                }
            }
            for (int i = 0; i < mapList.size(); i++) {
                map = mapList.get(i);
                String key = map.get("key").trim();
                String mac = map.get("mac").trim();
                String clearText = map.get("clearText").trim();

                byte[] keyBytes = StringUtil.hexStr2Bytes(key);
                byte[] macBytes = StringUtil.hexStr2Bytes(mac);
                byte[] a = null;
                byte[] resultBytes = gmAlgorithm.SM4((byte) 0x01, (byte) 0x00, keyBytes, macBytes, a);
                String result = StringUtil.byte2HexStr(resultBytes);

                if (!result.equals(clearText)) {
                    Log.e("sdk", "ecb解密失败!密文：" + clearText);
                    stringBuffer.append("ecb解密失败!密文：" + clearText + "\r\n");
                    resultBuffer.append("SM4 ECB解密" + i + "失败!!\r\n");
                } else {
                    Log.i("sdk", "ecb解密成功!明文:" + clearText + "\r\n" + "对应密文:" + result);
                    //stringBuffer.append("ecb解密成功!明文:" + clearText + "\r\n" + "对应密文:" + result + "\r\n");
                    stringBuffer.append("密钥= " + key + "\r\n");
                    int len = result.length() / 2;
                    stringBuffer.append("明文长度= " + String.format("%08x", len) + "\r\n");
                    stringBuffer.append("密文= " + mac + "\r\n");
                    stringBuffer.append("明文= " + result + "\r\n\r\n");
                    resultBuffer.append("SM4 ECB解密" + i + "成功!!\r\n");
                }
            }

            FileUtilComm.writeFile("/guomi/test_results/SM4验证数据-解密-10-ECB.txt", stringBuffer.toString());
            return resultBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "执行SM4 ecb解密失败！！";
    }

    //加密
    public String rsaEncrypt() {
        ArrayList<String> list = FileUtilComm.read("RSA2048-加密-10.txt");
        if (list.size() <= 0) {
            return "读取RSA2048-加密-10.txt失败";
        }

        try {
            gmAlgorithm.init();
            stringBuffer.setLength(0);
            resultBuffer.setLength(0);

            ArrayList<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
            Map<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < list.size(); i++) {
                String val = list.get(i).split("=")[1];
                if (i % 5 == 0) {
    				map.put("keySize", val);//公私钥对位长
                } else if (i % 5 == 1) {
                    map.put("publicE", val);//公钥指数
                } else if (i % 5 == 2) {
                    map.put("key", val);//秘钥（包含公钥模、私钥 。前512公钥模，后的512私钥）
                } else if (i % 5 == 3) {
                    map.put("clearText", val);//明文
                    LogUtil.d(TAG, "RSA encrypt clear data:" + val);
                } else if (i % 5 == 4) {
                    map.put("ciphertext", val);//密文
                    mapList.add(map);
                    map = new HashMap<String, String>();
                }
            }
            for (int i = 0; i < mapList.size(); i++) {
                map = mapList.get(i);
                String publicE = map.get("publicE").trim();
                String keyStr = map.get("key").trim();
                String key = map.get("key").trim().substring(0, 512).trim();//取公钥模
                String prikey = map.get("key").trim().substring(512, 1024).trim();//取私钥
                String clearText = map.get("clearText").trim();
                String ciphertext = map.get("ciphertext").trim();
                String keySizeStr = map.get("keySize").trim();

                byte[] publicEBytes = StringUtil.hexStr2Bytes(publicE);
                byte[] keyBytes = StringUtil.hexStr2Bytes(key);
                byte[] prikeyBytes = StringUtil.hexStr2Bytes(prikey);
                byte[] clearTextBytes = StringUtil.hexStr2Bytes(clearText);
                byte[] resultBytes = gmAlgorithm.RSAPublicKeyComputation(publicEBytes, keyBytes, clearTextBytes);
                String result = StringUtil.byte2HexStr(resultBytes);
                if (!result.equals(ciphertext)) {
                    Log.e("sdk", "验证RSA加密失败!公钥：" + key);
                    stringBuffer.append("验证RSA加密失败!公钥：" + key + "\r\n");
                    resultBuffer.append("RSA加密" + i + "失败!!\r\n");
                } else {
                    Log.i("sdk", "验证RSA加密成功!密文:" + ciphertext + "\r\n" + "对应公钥:" + key);
                    stringBuffer.append("RSA位数= " + keySizeStr + "\r\n");
                    stringBuffer.append("公钥E= " + publicE + "\r\n");
                    stringBuffer.append("密钥= " + keyStr + "\r\n");
                    stringBuffer.append("明文= " + clearText + "\r\n");
                    stringBuffer.append("密文= " + result + "\r\n\r\n");
                    resultBuffer.append("RSA加密" + i + "成功!!\r\n");
                }

                // 以下两行可以不要
                byte[] decDataBytes = gmAlgorithm.RSAPrivateKeyComputation(publicEBytes, prikeyBytes, keyBytes, resultBytes);
                LogUtil.d(TAG, "RSA encrypt dec result data:" + StringUtil.byte2HexStr(decDataBytes));
            }

            FileUtilComm.writeFile("/guomi/test_results/RSA2048-加密-10.txt", stringBuffer.toString());
            return resultBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "执行RSA加密失败！！";
    }

    //解密
    public String rsaDecrypt() {
        ArrayList<String> list = FileUtilComm.read("RSA2048-解密-10.txt");
        if (list.size() <= 0) {
            return "读取RSA2048-解密-10.txt失败";
        }

        try {
            gmAlgorithm.init();
            stringBuffer.setLength(0);
            resultBuffer.setLength(0);

            ArrayList<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
            Map<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < list.size(); i++) {
                String val = list.get(i).split("=")[1];
                if (i % 5 == 0) {
					map.put("keySize", val);//公私钥对位长
                } else if (i % 5 == 1) {
                    map.put("publicE", val);//公钥指数
                } else if (i % 5 == 2) {
                    map.put("key", val);//密钥（包含公钥模、私钥 。前512公钥模，后的512私钥）
                } else if (i % 5 == 3) {
                    map.put("ciphertext", val);//密文
                } else if (i % 5 == 4) {
                    map.put("clearText", val);//明文
                    mapList.add(map);
                    map = new HashMap<String, String>();
                }
            }
            for (int i = 0; i < mapList.size(); i++) {
                map = mapList.get(i);
                String publicE = map.get("publicE").trim();
                String pubkey = map.get("key").trim().substring(0, 512).trim();//取公钥模
                String prikey = map.get("key").trim().substring(512, 1024).trim();//取私钥
                String clearText = map.get("clearText").trim();
                String ciphertext = map.get("ciphertext").trim();
                String keySizeStr = map.get("keySize").trim();
                String keyStr = map.get("key").trim();

                byte[] publicEBytes = StringUtil.hexStr2Bytes(publicE);
                byte[] pubkeyBytes = StringUtil.hexStr2Bytes(pubkey);
                byte[] prikeyBytes = StringUtil.hexStr2Bytes(prikey);
                byte[] ciphertextBytes = StringUtil.hexStr2Bytes(ciphertext);
                byte[] resultBytes = gmAlgorithm.RSAPrivateKeyComputation(publicEBytes, prikeyBytes, pubkeyBytes, ciphertextBytes);
                String result = StringUtil.byte2HexStr(resultBytes);
                if (!result.equals(clearText)) {
                    Log.e("sdk", "验证RSA解密失败!公钥模：" + pubkey);
                    stringBuffer.append("验证RSA解密失败!公钥模：" + pubkey + "\r\n");
                    resultBuffer.append("RSA解密" + i + "失败!!\r\n");
                } else {
                    Log.i("sdk", "验证RSA解密成功!明文：" + clearText + "\r\n" + "对应公钥模:" + pubkey);
                    stringBuffer.append("RSA位数= " + keySizeStr + "\r\n");
                    stringBuffer.append("公钥E= " + publicE + "\r\n");
                    stringBuffer.append("密钥= " + keyStr + "\r\n");
                    stringBuffer.append("密文= " + ciphertext + "\r\n");
                    stringBuffer.append("明文= " + result + "\r\n\r\n");
                    resultBuffer.append("RSA解密" + i + "成功!!\r\n");
                }
            }

            FileUtilComm.writeFile("/guomi/test_results/RSA2048-解密-10.txt", stringBuffer.toString());
            return resultBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "执行RSA解密失败！！";
    }

    //签名
    public String rsaSign() {
        ArrayList<String> list = FileUtilComm.read("RSA2048-签名-10.txt");
        if (list.size() <= 0) {
            return "读取RSA2048-签名-10.txt失败";
        }

        try {
            gmAlgorithm.init();
            stringBuffer.setLength(0);
            resultBuffer.setLength(0);

            ArrayList<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
            Map<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < list.size(); i++) {
                String val = list.get(i).split("=")[1];
                if (i % 5 == 0) {
    				map.put("keySize", val);//公私钥对位长
                } else if (i % 5 == 1) {
                    map.put("publicE", val);//公钥指数
                } else if (i % 5 == 2) {
                    map.put("key", val);//密钥（包含公钥模、私钥 。前512公钥模，后的512私钥,接着256私钥因子1，私钥因子2，私钥因子指数1，私钥因子指数2，CRT系数）
                } else if (i % 5 == 3) {
                    map.put("clearText", val);//消息
                } else if (i % 5 == 4) {
                    map.put("ciphertext", val);//签名结果
                    mapList.add(map);
                    map = new HashMap<String, String>();
                }
            }
            for (int i = 0; i < mapList.size(); i++) {
                map = mapList.get(i);
                String publicE = map.get("publicE").trim();
                String pubkey = map.get("key").trim().substring(0, 512).trim();//取公钥模
                String prikey = map.get("key").trim().substring(512, 1024).trim();//取私钥
                String clearText = map.get("clearText").trim();
                String ciphertext = map.get("ciphertext").trim();
                String keySizeStr = map.get("keySize").trim();
                String keyStr = map.get("key").trim();

                byte[] publicEBytes = StringUtil.hexStr2Bytes(publicE);
                byte[] pubkeyBytes = StringUtil.hexStr2Bytes(pubkey);
                byte[] prikeyBytes = StringUtil.hexStr2Bytes(prikey);
                byte[] clearTextBytes = StringUtil.hexStr2Bytes(clearText);
                byte[] resultBytes = gmAlgorithm.RSAPrivateKeyComputation(publicEBytes, prikeyBytes, pubkeyBytes, clearTextBytes);

                String result = StringUtil.byte2HexStr(resultBytes);
                if (!result.equals(ciphertext)) {
                    Log.e("sdk", "RSA签名失败!公钥模：" + pubkey);
                    stringBuffer.append("RSA签名失败!公钥模：" + pubkey + "\r\n");
                    resultBuffer.append("RSA签名" + i + "失败!!\r\n");
                } else {
                    Log.i("sdk", "验证RSA签名成功!签名：" + ciphertext + "\r\n" + "对应公钥模:" + pubkey);
                    stringBuffer.append("RSA位数= " + keySizeStr + "\r\n");
                    stringBuffer.append("公钥E= " + publicE + "\r\n");
                    stringBuffer.append("密钥= " + keyStr + "\r\n");
                    stringBuffer.append("消息= " + clearText + "\r\n");
                    stringBuffer.append("签名结果= " + result + "\r\n\r\n");
                    resultBuffer.append("RSA签名" + i + "成功!!\r\n");
                }
            }

            FileUtilComm.writeFile("/guomi/test_results/RSA2048-签名-10.txt", stringBuffer.toString());
            return resultBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "执行RSA签名失败！！";
    }

    //验签
    public String rsaCheckSign() {
        ArrayList<String> list = FileUtilComm.read("RSA2048-验签-10.txt");
        if (list.size() <= 0) {
            return "读取RSA2048-验签-10.txt失败";
        }

        try {
            gmAlgorithm.init();
            stringBuffer.setLength(0);
            resultBuffer.setLength(0);

            ArrayList<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
            Map<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < list.size(); i++) {
                String val = list.get(i).split("=")[1];
                if (i % 5 == 0) {
					map.put("keySize", val);//公私钥对位长
                } else if (i % 5 == 1) {
                    map.put("publicE", val);//公钥指数
                } else if (i % 5 == 2) {
                    map.put("key", val);//密钥（包含公钥模、私钥 。前512公钥模，后的512私钥,接着256私钥因子1，私钥因子2，私钥因子指数1，私钥因子指数2，CRT系数）
                } else if (i % 5 == 3) {
                    map.put("clearText", val);//消息
                } else if (i % 5 == 4) {
                    map.put("ciphertext", val);//签名结果
                    mapList.add(map);
                    map = new HashMap<String, String>();
                }
            }
            for (int i = 0; i < mapList.size(); i++) {
                map = mapList.get(i);
                String publicE = map.get("publicE").trim();
                String pubkey = map.get("key").trim().substring(0, 512).trim();//取公钥模
                String prikey = map.get("key").trim().substring(512, 1024).trim();//取私钥
                String clearText = map.get("clearText").trim();
                String ciphertext = map.get("ciphertext").trim();
                byte[] publicEBytes = StringUtil.hexStr2Bytes(publicE);
                byte[] pubkeyBytes = StringUtil.hexStr2Bytes(pubkey);
                byte[] prikeyBytes = StringUtil.hexStr2Bytes(prikey);
                byte[] ciphertextBytes = StringUtil.hexStr2Bytes(ciphertext);
                String keySizeStr = map.get("keySize").trim();
                String keyStr = map.get("key").trim();

                byte[] resultBytes = gmAlgorithm.RSAPublicKeyComputation(publicEBytes, pubkeyBytes, ciphertextBytes);
                String result = StringUtil.byte2HexStr(resultBytes);
                if (!result.equals(clearText)) {
                    Log.e("sdk", "RSA验签失败!公钥模：" + pubkey);
                    stringBuffer.append("RSA验签失败!公钥模：" + pubkey + "\r\n");
                    resultBuffer.append("RSA验签" + i + "失败!!\r\n");
                } else {
                    Log.i("sdk", "RSA验签成功!明文：" + clearText + "\r\n" + "对应公钥模:" + pubkey);
                    stringBuffer.append("RSA位数= " + keySizeStr + "\r\n");
                    stringBuffer.append("公钥E= " + publicE + "\r\n");
                    stringBuffer.append("密钥= " + keyStr + "\r\n");
                    stringBuffer.append("消息= " + result + "\r\n");
                    stringBuffer.append("签名结果= " + ciphertext + "\r\n\r\n");
                    resultBuffer.append("RSA验签" + i + "成功!!\r\n");
                }
            }

            FileUtilComm.writeFile("/guomi/test_results/RSA2048-验签-10.txt", stringBuffer.toString());
            return resultBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "执行RSA验签失败！！";
    }

    public String getRandomNumber(int numBits){
        if (numBits <= 0 || numBits > 128) {
            return "测试随机数最大不能超过128，谢谢！";
        }

        try {
            gmAlgorithm.init();
            stringBuffer.setLength(0);

            byte[] resultBytes  = gmAlgorithm.getRandom(numBits);
            String result = StringUtil.byte2HexStr(resultBytes);
            Log.i(TAG, "获得的随机数为："+result);
            stringBuffer.append("获得的随机数为："+result);

            FileUtilComm.writeFile(stringBuffer.toString());
            return stringBuffer.toString();
        } catch (Exception e) {
            Log.e("sdk", e.toString());
        }

        return "获得的随机数失败!!";
    }

    public void gen128MRam() {
        // 大约4小时
        final int MAX_THREAD_NUM = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREAD_NUM);

        for (int i = 0; i < 1000; i++) {
            final String fileName = "Random" + String.format("%04d", i) + ".bin";
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    gen128KRomFile(fileName);
                }
            });
        }
    }

    private void gen128KRomFile(String romFileName) {
        LogUtil.d(TAG, "Rom file name=" + romFileName);
        final int ROM_BLOCK_LEN = 128;
        final int ROM_FILE_MAX_LEN = 128*1024;
        long len = 0;
        FileOutputStream out;
        File rFile;

        String fileFullName = Environment.getExternalStorageDirectory() + "/rombin/"+ romFileName;
        try {
            rFile = new File(fileFullName);
            if (!rFile.exists()) {
                rFile.createNewFile();
            }

            out = new FileOutputStream(rFile, true);
            len = rFile.length();
            LogUtil.d(TAG, "len=" + len);
        } catch (IOException e) {
            LogUtil.d(TAG, "Create romfile failed. fileName=" + romFileName);
            e.printStackTrace();
            return;
        }

        GMAlgorithm gmAlgorithm = new GMAlgorithm();
        try {
            gmAlgorithm.init();
        } catch (SDKException e) {
            e.printStackTrace();
        }

        while (len < ROM_FILE_MAX_LEN) {
            byte[] resultBytes;
            try {
                resultBytes = gmAlgorithm.getRandom(ROM_BLOCK_LEN);
                try {
                    out.write(resultBytes);
                    len += resultBytes.length;
                } catch (IOException e) {
                    e.printStackTrace();
                    len = rFile.length();
                }
            } catch (SDKException e) {
                e.printStackTrace();
            }
        }
    }
}
