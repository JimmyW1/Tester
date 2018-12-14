package com.test.ui.activities.other.test_tools.ncca_test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.os.Environment;
import android.util.Log;

import com.test.util.LogUtil;

public class FileUtilComm {

	private static String outPutFile = "outPutFile.txt";
	private static String outRomFile = "rom_one.txt";

	/**
	 * 读取文件
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<String> read(String fileName) {
		ArrayList list = new ArrayList();

        try {
			String fileCatage = Environment.getExternalStorageDirectory() + "/guomi/";
			File file = new File(fileCatage + fileName);
			if (file.exists() && file.isFile()) {
				FileInputStream fis = new FileInputStream(file);
				BufferedInputStream in = new BufferedInputStream(fis);
				BufferedReader fin = new BufferedReader(new InputStreamReader(in, "gbk"));
				String read = null;
				while ((read = fin.readLine()) != null) {
					if (!"".equals(read)) {
						// Log.i("sdk read",read);
						list.add(read);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 写入txt文件，可以在原文件内容的基础上追加内容(并判断目录是否存在，不存在则生成目录)
	 * 
	 * @param value
	 *            写入文件内容
	 * @throws IOException
	 */
	public static void writeFile(String value) {
		File file = null;
		String fileCatage = Environment.getExternalStorageDirectory() + "/guomi/";
		try {
			file = new File(fileCatage);
            if (file.exists()) {
                if (!file.isDirectory()) {
                    file.delete();
					file.mkdir();
				}
			}

			Log.i("sdk write file", fileCatage + outPutFile);
			file = new File(fileCatage + outPutFile);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(file, true);
			out.write(value.getBytes("gbk"));
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeFile(String fileName, String value) {
		File file = null;
		String fileCatage = Environment.getExternalStorageDirectory() + fileName;
		try {
			file = new File(fileCatage);
			file.createNewFile();
			Log.i("sdk write file", fileCatage);

			FileOutputStream out = new FileOutputStream(file, false);
			out.write(value.getBytes("gbk"));
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//生成随机数文件
	public static void writeRomFile(String value) {
		File file = null;
		String fileCatage = Environment.getExternalStorageDirectory() + "/guomi/";
		try {
			file = new File(fileCatage);
			if (file.exists()) {
				if (!file.isDirectory()) {
					file.delete();
					file.mkdir();
				}
			}

			Log.i("sdk write file", fileCatage + outRomFile);
			file = new File(fileCatage + outRomFile);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(file, true);
			out.write(value.getBytes("gbk"));
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getSplitLine() {
		return "--------------------------------------------------\r\n";
	}

	public static void writeSM2File(String value,String str) {
		
		String fileName= getOutPutFileName(str);
		if (fileName == null) {
			return;
		}

		File file = null;
		String fileCatage = Environment.getExternalStorageDirectory() + "/gmper/test_results/";
		file = new File(fileCatage +fileName);
		try {
			FileOutputStream out = new FileOutputStream(file, true);
			out.write(value.getBytes("gbk"));
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getOutPutFileName(String fileNameIdxStr) {
        String str = fileNameIdxStr;
        String fileName = null;

		if(str.equals("1")){
			fileName="SM2_加密和解密_1.txt";
		}else if(str.equals("2")){
			fileName="SM2_加密和解密_2.txt";
		}else if(str.equals("3")){
			fileName="SM2_加密和解密_3.txt";
		}else if(str.equals("11")){
			fileName="SM2_签名和验签_1.txt";
		}else if(str.equals("12")){
			fileName="SM2_签名和验签_2.txt";
		}else if(str.equals("13")){
			fileName="SM2_签名和验签_3.txt";
		}else if(str.equals("31")){
			fileName="SM3_杂凑_1.txt";
		}else if(str.equals("32")){
			fileName="SM3_杂凑_2.txt";
		}else if(str.equals("33")){
			fileName="SM3_杂凑_3.txt";
		}else if(str.equals("41")){
			fileName="SM4_CBC_1.txt";
		}else if(str.equals("42")){
			fileName="SM4_CBC_2.txt";
		}else if(str.equals("43")){
			fileName="SM4_CBC_3.txt";
		}else if(str.equals("51")){
			fileName="SM4_ECB_1.txt";
		}else if(str.equals("52")){
			fileName="SM4_ECB_2.txt";
		}else if(str.equals("53")){
			fileName="SM4_ECB_3.txt";
		}else if(str.equals("61")){
			fileName="SM2_密钥对生成_1.txt";
		}else if(str.equals("62")){
			fileName="SM2_密钥对生成_2.txt";
		}else if(str.equals("63")){
			fileName="SM2_密钥对生成_3.txt";
		}

		return fileName;
	}

	public static int getCmd(String str) {
		String fileName = null;
		int cmd = -1;

		if(str.equals("1")){
			fileName="SM2_加密和解密_1.txt";
            cmd = 1004;
		}else if(str.equals("2")){
			fileName="SM2_加密和解密_2.txt";
			cmd = 1005;
		}else if(str.equals("3")){
			fileName="SM2_加密和解密_3.txt";
			cmd = 1006;
		}else if(str.equals("11")){
			fileName="SM2_签名和验签_1.txt";
			cmd = 1010;
		}else if(str.equals("12")){
			fileName="SM2_签名和验签_2.txt";
			cmd = 1011;
		}else if(str.equals("13")){
			fileName="SM2_签名和验签_3.txt";
			cmd = 1012;
		}else if(str.equals("31")){
			fileName="SM3_杂凑_1.txt";
			cmd = 1013;
		}else if(str.equals("32")){
			fileName="SM3_杂凑_2.txt";
			cmd = 1014;
		}else if(str.equals("33")){
			fileName="SM3_杂凑_3.txt";
			cmd = 1015;
		}else if(str.equals("41")){
			fileName="SM4_CBC_1.txt";
			cmd = 1016;
		}else if(str.equals("42")){
			fileName="SM4_CBC_2.txt";
			cmd = 1017;
		}else if(str.equals("43")){
			fileName="SM4_CBC_3.txt";
			cmd = 1018;
		}else if(str.equals("51")){
			fileName="SM4_ECB_1.txt";
			cmd = 1019;
		}else if(str.equals("52")){
			fileName="SM4_ECB_2.txt";
			cmd = 1020;
		}else if(str.equals("53")){
			fileName="SM4_ECB_3.txt";
			cmd = 1021;
		}else if(str.equals("61")){
			fileName="SM2_密钥对生成_1.txt";
			cmd = 1007;
		}else if(str.equals("62")){
			fileName="SM2_密钥对生成_2.txt";
			cmd = 1008;
		}else if(str.equals("63")){
			fileName="SM2_密钥对生成_3.txt";
			cmd = 1009;
		}

		return cmd;
	}
}
