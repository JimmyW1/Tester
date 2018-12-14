package com.test.ui.activities.other.test_tools.ncca_test;

import android.app.ProgressDialog;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.socsi.smartposapi.gmalgorithm.GMAlgorithm;
import com.socsi.utils.StringUtil;
import com.test.ui.activities.BaseActivity;
import com.test.ui.activities.R;
import com.test.util.DialogUtil;
import com.test.util.LogUtil;
import com.test.util.StrUtil;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

public class NCCAPerformanceActivity extends BaseActivity implements View.OnClickListener {
    private final String TAG = "NCCAPerformanceActivity";
    private GMAlgorithm gmAlgorithm = new GMAlgorithm();
    private String read = "";
    private String lineStr = "";
    private boolean isWifiPerformanceTest;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Toast.makeText(NCCAPerformanceActivity.this, "测试完成！", Toast.LENGTH_SHORT).show();
        }
    };

    private final String SERVER_IPADDR = "172.21.230.105";
    private final int SERVER_PORT = 6666;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nccaperformance);
        Button button;
        isWifiPerformanceTest = false;

        String commType = getIntent().getStringExtra("COMMTYPE");
        LogUtil.d(TAG, "commType=" + commType);
        if (commType.equals("WIFI")) {
            isWifiPerformanceTest = true;
            File file = new File(Environment.getExternalStorageDirectory() + "/gmper/" + "ram_data_source1.txt");
            if (file.exists()) {
                file.delete();
            }

            file = new File(Environment.getExternalStorageDirectory() + "/gmper/" + "ram_data_source2.txt");
            if (file.exists()) {
                file.delete();
            }

            file = new File(Environment.getExternalStorageDirectory() + "/gmper/" + "ram_data_source3.txt");
            if (file.exists()) {
                file.delete();
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    /* 第一次连接经常报错没有路由
                    recvFileData(1001, Environment.getExternalStorageDirectory() + "/gmper/" + "ram_data_source1.txt");
                    recvFileData(1002, Environment.getExternalStorageDirectory() + "/gmper/" + "ram_data_source2.txt");
                    recvFileData(1003, Environment.getExternalStorageDirectory() + "/gmper/" + "ram_data_source3.txt");
                    */
                    executeWIFICmd(SERVER_IPADDR, SERVER_PORT, 1001);
                    executeWIFICmd(SERVER_IPADDR, SERVER_PORT, 1002);
                    executeWIFICmd(SERVER_IPADDR, SERVER_PORT, 1003);
                }
            }).start();
        }

        SMPerformanceUtil.isWifiPerformanceTest = isWifiPerformanceTest;

        button = (Button) findViewById(R.id.btn_sm2_ed_1);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn_sm2_ed_2);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn_sm2_ed_3);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn_sm2_kp_1);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn_sm2_kp_2);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn_sm2_kp_3);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn_sm2_sv_1);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn_sm2_sv_2);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn_sm2_sv_3);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn_sm3_hash_1);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn_sm3_hash_2);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn_sm3_hash_3);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn_sm4_cbc1);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn_sm4_cbc2);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn_sm4_cbc3);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn_sm4_ecb1);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn_sm4_ecb2);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn_sm4_ecb3);
        button.setOnClickListener(this);
    }

    public void sm2per(final String str) {
        String fileName = "";
        if (str.equals("1")) {
            fileName = "SM2_加密和解密_1.txt";
        } else if (str.equals("2")) {
            fileName = "SM2_加密和解密_2.txt";
        } else if (str.equals("3")) {
            fileName = "SM2_加密和解密_3.txt";
        }
        // 生成文件
        try {
            String fileCatage = Environment.getExternalStorageDirectory() + "/gmper/test_results/";
            File file = new File(fileCatage + fileName);
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
            }

            FileUtilComm.writeSM2File(SMKeyUtil.sm2_encode_tile + "\r\n", str);
            FileUtilComm.writeSM2File(SMKeyUtil.sm2_encode_tilePri + "\r\n", str);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final ArrayList list = new ArrayList();
        final ProgressDialog proDialog = android.app.ProgressDialog.show(NCCAPerformanceActivity.this, "提示",
                "开始测试...");
        proDialog.setCanceledOnTouchOutside(false);
        Thread thread = new Thread() {
            public void run() {
                try {
                    int numRead = 0;
                    String sourceFileName = "";
                    if (str.equals("1")) {
                        sourceFileName = "ram_data_source1.txt";
                    } else if (str.equals("2")) {
                        sourceFileName = "ram_data_source2.txt";
                    } else if (str.equals("3")) {
                        sourceFileName = "ram_data_source3.txt";
                    }
                    String fileCatage = Environment.getExternalStorageDirectory() + "/gmper/";
                    File file = new File(fileCatage + sourceFileName);

                    FileInputStream fis = new FileInputStream(file);
                    BufferedInputStream in = new BufferedInputStream(fis);
                    BufferedReader fin = new BufferedReader(new InputStreamReader(in, "gbk"));

                    while ((read = fin.readLine()) != null) {
                        if (!"".equals(read)) {
                            list.add(read.toString().trim());
                        }
                    }
                    for (int i = 0; i < list.size(); i++) {
                        lineStr = list.get(i).toString();
                        SMPerformanceUtil.getInstance().sm2EncodeAndDecode(gmAlgorithm, lineStr, i, str);
                    }

                    Double eNum = (double) SMPerformanceUtil.sm2EnTime/ 1000;
                    Double dNum = (double) SMPerformanceUtil.sm2DeTime / 1000;

                    String enStr="SM2算法性能测试,加密（128字节），1024组时间："+eNum+"秒\r\n";
                    String deStr="SM2算法性能测试,解密（128字节），1024组时间："+dNum+"秒\r\n";

                    FileUtilComm.writeSM2File("\r\n", str);
                    FileUtilComm.writeSM2File(enStr, str);
                    FileUtilComm.writeSM2File(deStr, str);


                    Message msg = new Message();
                    msg.arg1 = 0x00;
                    handler.sendMessage(msg);

                    if (isWifiPerformanceTest) {
                        executeWIFICmd(SERVER_IPADDR, SERVER_PORT, FileUtilComm.getCmd(str));
                    }
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                proDialog.dismiss();// 万万不可少这句，否则会程序会卡死。
            }
        };
        thread.start();

    }

    public void sm2Signper(final String str) {
        String fileName = "";
        if (str.equals("11")) {
            fileName = "SM2_签名和验签_1.txt";
        } else if (str.equals("12")) {
            fileName = "SM2_签名和验签_2.txt";
        } else if (str.equals("13")) {
            fileName = "SM2_签名和验签_3.txt";
        }
        // 生成文件
        try {
            String fileCatage = Environment.getExternalStorageDirectory() + "/gmper/test_results/";
            File file = new File(fileCatage + fileName);
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
            }

            FileUtilComm.writeSM2File("公钥= " + SMKeyUtil.SM2SignPublickey + "\r\n", str);
            FileUtilComm.writeSM2File("私钥= " + SMKeyUtil.SM2SignPrivatekey + "\r\n", str);
            FileUtilComm.writeSM2File("签名者ID= " + SMKeyUtil.SM2SignID + "\r\n", str);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final ArrayList list = new ArrayList();
        final ProgressDialog proDialog = android.app.ProgressDialog.show(NCCAPerformanceActivity.this, "提示",
                "开始测试...");
        proDialog.setCanceledOnTouchOutside(false);
        Thread thread = new Thread() {
            public void run() {
                try {
                    int numRead = 0;
                    String sourceFileName = "";
                    if (str.equals("1") || str.equals("11")) {
                        sourceFileName = "ram_data_source1.txt";
                    } else if (str.equals("2") || str.equals("12")) {
                        sourceFileName = "ram_data_source2.txt";
                    } else if (str.equals("3") || str.equals("13")) {
                        sourceFileName = "ram_data_source3.txt";
                    }
                    String fileCatage = Environment.getExternalStorageDirectory() + "/gmper/";
                    File file = new File(fileCatage + sourceFileName);

                    FileInputStream fis = new FileInputStream(file);
                    BufferedInputStream in = new BufferedInputStream(fis);
                    BufferedReader fin = new BufferedReader(new InputStreamReader(in, "gbk"));

                    while ((read = fin.readLine()) != null) {
                        if (!"".equals(read)) {
                            list.add(read.toString().trim());
                        }
                    }
                    for (int i = 0; i < list.size(); i++) {
                        lineStr = list.get(i).toString();
                        SMPerformanceUtil.getInstance().sm2SignAndCheckSign(gmAlgorithm, lineStr, i, str);
                    }


                    Double eNum = (double) SMPerformanceUtil.sm2SignTime / 1000;
                    Double dNum = (double) SMPerformanceUtil.sm2CheckSignTime / 1000;




//
//					String enStrE=SMPerformanceUtil.sm2SignTime / 1000+"."+SMPerformanceUtil.sm2SignTime % 1000;
//					String enStrD=SMPerformanceUtil.sm2CheckSignTime / 1000+"."+SMPerformanceUtil.sm2CheckSignTime % 1000;

                    String enStr="SM2算法性能测试,签名（128字节），1024组时间："+eNum+"秒\r\n";
                    String deStr="SM2算法性能测试,验签（128字节），1024组时间："+dNum+"秒\r\n";
                    FileUtilComm.writeSM2File("\r\n", str);
                    FileUtilComm.writeSM2File(enStr, str);
                    FileUtilComm.writeSM2File(deStr, str);


                    if (isWifiPerformanceTest) {
                        executeWIFICmd(SERVER_IPADDR, SERVER_PORT, FileUtilComm.getCmd(str));
                    }

                    Message msg = new Message();
                    msg.arg1 = 0x00;
                    handler.sendMessage(msg);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                proDialog.dismiss();// 万万不可少这句，否则会程序会卡死。
            }
        };
        thread.start();

    }

    public void sm3Otherper(final String str) {
        String fileName = "";
        if (str.equals("31")) {
            fileName = "SM3_杂凑_1.txt";
        } else if (str.equals("32")) {
            fileName = "SM3_杂凑_2.txt";
        } else if (str.equals("33")) {
            fileName = "SM3_杂凑_3.txt";
        }
        // 生成文件
        try {
            String fileCatage = Environment.getExternalStorageDirectory() + "/gmper/test_results/";
            File file = new File(fileCatage + fileName);
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        final ArrayList list = new ArrayList();
        final ProgressDialog proDialog = android.app.ProgressDialog.show(NCCAPerformanceActivity.this, "提示",
                "开始测试...");
        proDialog.setCanceledOnTouchOutside(false);
        Thread thread = new Thread() {
            public void run() {
                try {
                    int numRead = 0;
                    String sourceFileName = "";
                    if (str.equals("31")) {
                        sourceFileName = "ram_data_source1.txt";
                    } else if (str.equals("32")) {
                        sourceFileName = "ram_data_source2.txt";
                    } else if (str.equals("33")) {
                        sourceFileName = "ram_data_source3.txt";
                    }
                    String fileCatage = Environment.getExternalStorageDirectory() + "/gmper/";
                    File file = new File(fileCatage + sourceFileName);

                    FileInputStream fis = new FileInputStream(file);
                    BufferedInputStream in = new BufferedInputStream(fis);
                    BufferedReader fin = new BufferedReader(new InputStreamReader(in, "gbk"));

                    while ((read = fin.readLine()) != null) {
                        if (!"".equals(read)) {
                            list.add(read.toString().trim());
                        }
                    }
                    for (int i = 0; i < list.size(); i++) {
                        lineStr = list.get(i).toString();
                        SMPerformanceUtil.getInstance().sm3Other(gmAlgorithm, lineStr, i, str);
                    }

                    Double eNum = (double) SMPerformanceUtil.sm3OtherTime / 1000;
                    String enStr="SM3算法性能测试,杂凑（128字节），1024组时间："+eNum+"秒\r\n";
                    FileUtilComm.writeSM2File("\r\n", str);
                    FileUtilComm.writeSM2File(enStr, str);

                    if (isWifiPerformanceTest) {
                        executeWIFICmd(SERVER_IPADDR, SERVER_PORT, FileUtilComm.getCmd(str));
                    }

                    Message msg = new Message();
                    msg.arg1 = 0x00;
                    handler.sendMessage(msg);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                proDialog.dismiss();// 万万不可少这句，否则会程序会卡死。
            }
        };
        thread.start();

    }

    public void sm4CBCper(final String str) {
        String fileName = "";
        if (str.equals("41")) {
            fileName = "SM4_CBC_1.txt";
        } else if (str.equals("42")) {
            fileName = "SM4_CBC_2.txt";
        } else if (str.equals("43")) {
            fileName = "SM4_CBC_3.txt";
        }
        // 生成文件
        try {
            String fileCatage = Environment.getExternalStorageDirectory() + "/gmper/test_results/";
            File file = new File(fileCatage + fileName);
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        final ArrayList list = new ArrayList();
        final ProgressDialog proDialog = android.app.ProgressDialog.show(NCCAPerformanceActivity.this, "提示",
                "开始测试...");
        proDialog.setCanceledOnTouchOutside(false);
        Thread thread = new Thread() {
            public void run() {
                try {
                    int numRead = 0;
                    String sourceFileName = "";
                    if (str.equals("41")) {
                        sourceFileName = "ram_data_source1.txt";
                    } else if (str.equals("42")) {
                        sourceFileName = "ram_data_source2.txt";
                    } else if (str.equals("43")) {
                        sourceFileName = "ram_data_source3.txt";
                    }
                    String fileCatage = Environment.getExternalStorageDirectory() + "/gmper/";
                    File file = new File(fileCatage + sourceFileName);

                    FileInputStream fis = new FileInputStream(file);
                    BufferedInputStream in = new BufferedInputStream(fis);
                    BufferedReader fin = new BufferedReader(new InputStreamReader(in, "gbk"));

                    while ((read = fin.readLine()) != null) {
                        if (!"".equals(read)) {
                            list.add(read.toString().trim());
                        }
                    }
                    for (int i = 0; i < list.size(); i++) {
                        lineStr = list.get(i).toString();
                        SMPerformanceUtil.getInstance().sm4CBCEncodeAndDecode(gmAlgorithm, lineStr, i, str);
                    }


                    Double eNum = (double) SMPerformanceUtil.sm4EnCBCTime / 1000;
                    Double dNum = (double) SMPerformanceUtil.sm4DeCBCTime / 1000;
                    String enStr="SM4算法性能测试,CBC加密，1024组时间："+eNum+"秒\r\n";
                    String deStr="SM4算法性能测试,CBC解密，1024组时间："+dNum+"秒\r\n";
                    FileUtilComm.writeSM2File("\r\n", str);
                    FileUtilComm.writeSM2File(enStr, str);
                    FileUtilComm.writeSM2File(deStr, str);

                    if (isWifiPerformanceTest) {
                        //sendFileData(FileUtilComm.getCmd(str), Environment.getExternalStorageDirectory() + "/gmper/test_results/" + FileUtilComm.getOutPutFileName(str));
                        executeWIFICmd(SERVER_IPADDR, SERVER_PORT, FileUtilComm.getCmd(str));
                    }

                    Message msg = new Message();
                    msg.arg1 = 0x00;
                    handler.sendMessage(msg);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                proDialog.dismiss();// 万万不可少这句，否则会程序会卡死。
            }
        };
        thread.start();

    }

    public void sm4ECBper(final String str) {
        String fileName = "";
        if (str.equals("51")) {
            fileName = "SM4_ECB_1.txt";
        } else if (str.equals("52")) {
            fileName = "SM4_ECB_2.txt";
        } else if (str.equals("53")) {
            fileName = "SM4_ECB_3.txt";
        }
        // 生成文件
        try {
            String fileCatage = Environment.getExternalStorageDirectory() + "/gmper/test_results/";
            File file = new File(fileCatage + fileName);
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        final ArrayList list = new ArrayList();
        final ProgressDialog proDialog = android.app.ProgressDialog.show(NCCAPerformanceActivity.this, "提示",
                "开始测试...");
        proDialog.setCanceledOnTouchOutside(false);
        Thread thread = new Thread() {
            public void run() {
                try {
                    int numRead = 0;
                    String sourceFileName = "";
                    if (str.equals("51")) {
                        sourceFileName = "ram_data_source1.txt";
                    } else if (str.equals("52")) {
                        sourceFileName = "ram_data_source2.txt";
                    } else if (str.equals("53")) {
                        sourceFileName = "ram_data_source3.txt";
                    }
                    String fileCatage = Environment.getExternalStorageDirectory() + "/gmper/";
                    File file = new File(fileCatage + sourceFileName);

                    FileInputStream fis = new FileInputStream(file);
                    BufferedInputStream in = new BufferedInputStream(fis);
                    BufferedReader fin = new BufferedReader(new InputStreamReader(in, "gbk"));

                    while ((read = fin.readLine()) != null) {
                        if (!"".equals(read)) {
                            list.add(read.toString().trim());
                        }
                    }
                    for (int i = 0; i < list.size(); i++) {
                        lineStr = list.get(i).toString();
                        SMPerformanceUtil.getInstance().sm4ECBEncodeAndDecode(gmAlgorithm, lineStr, i, str);
                    }

                    Double eNum = (double) SMPerformanceUtil.sm4EnECBTime / 1000;
                    Double dNum = (double) SMPerformanceUtil.sm4DeECBTime / 1000;
                    String enStr="SM4算法性能测试,ECB加密，1024组时间："+eNum+"秒\r\n";
                    String deStr="SM4算法性能测试,ECB解密，1024组时间："+dNum+"秒\r\n";
                    FileUtilComm.writeSM2File("\r\n", str);
                    FileUtilComm.writeSM2File(enStr, str);
                    FileUtilComm.writeSM2File(deStr, str);

                    if (isWifiPerformanceTest) {
                        executeWIFICmd(SERVER_IPADDR, SERVER_PORT, FileUtilComm.getCmd(str));
                    }

                    Message msg = new Message();
                    msg.arg1 = 0x00;
                    handler.sendMessage(msg);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                proDialog.dismiss();// 万万不可少这句，否则会程序会卡死。
            }
        };
        thread.start();

    }

    public void sm2Keyper(final String str) {
        String fileName = "";
        if (str.equals("61")) {
            fileName = "SM2_密钥对生成_1.txt";
        } else if (str.equals("62")) {
            fileName = "SM2_密钥对生成_2.txt";
        } else if (str.equals("63")) {
            fileName = "SM2_密钥对生成_3.txt";
        }
        // 生成文件
        try {
            String fileCatage = Environment.getExternalStorageDirectory() + "/gmper/test_results/";
            File file = new File(fileCatage + fileName);
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        final ArrayList list = new ArrayList();
        final ProgressDialog proDialog = android.app.ProgressDialog.show(NCCAPerformanceActivity.this, "提示",
                "开始测试...");
        proDialog.setCanceledOnTouchOutside(false);
        Thread thread = new Thread() {
            public void run() {
                try {
                    SMPerformanceUtil.sm2GenKeyPairTime = 0L;
                    for (int i = 0; i < 1024; i++) {
                        SMPerformanceUtil.getInstance().sm2GenKey(gmAlgorithm, "", i, str);
                    }
                    Double tNum = (double) SMPerformanceUtil.sm2GenKeyPairTime / 1000;
                    String timeStr = "SM2算法性能测试,密钥对生成，1024组时间：" + tNum + "秒\r\n";
                    FileUtilComm.writeSM2File(timeStr, str);

                    if (isWifiPerformanceTest) {
                        executeWIFICmd(SERVER_IPADDR, SERVER_PORT, FileUtilComm.getCmd(str));
                    }

                    Message msg = new Message();
                    msg.arg1 = 0x00;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                proDialog.dismiss();// 万万不可少这句，否则会程序会卡死。
            }
        };
        thread.start();

    }

    @Override
    public void onClick(View v) {
        SMPerformanceUtil.getInstance().initTime();
        switch (v.getId()) {
            case R.id.btn_sm2_ed_1:
                sm2per("1");
                break;
            case R.id.btn_sm2_ed_2:
                sm2per("2");
                break;
            case R.id.btn_sm2_ed_3:
                sm2per("3");
                break;
            case R.id.btn_sm2_kp_1:
                sm2Keyper("61");
                break;
            case R.id.btn_sm2_kp_2:
                sm2Keyper("62");
                break;
            case R.id.btn_sm2_kp_3:
                sm2Keyper("63");
                break;
            case R.id.btn_sm2_sv_1:
                sm2Signper("11");
                break;
            case R.id.btn_sm2_sv_2:
                sm2Signper("12");
                break;
            case R.id.btn_sm2_sv_3:
                sm2Signper("13");
                break;
            case R.id.btn_sm3_hash_1:
                sm3Otherper("31");
                break;
            case R.id.btn_sm3_hash_2:
                sm3Otherper("32");
                break;
            case R.id.btn_sm3_hash_3:
                sm3Otherper("33");
                break;
            case R.id.btn_sm4_cbc1:
                sm4CBCper("41");
                break;
            case R.id.btn_sm4_cbc2:
                sm4CBCper("42");
                break;
            case R.id.btn_sm4_cbc3:
                sm4CBCper("43");
                break;
            case R.id.btn_sm4_ecb1:
                sm4ECBper("51");
                break;
            case R.id.btn_sm4_ecb2:
                sm4ECBper("52");
                break;
            case R.id.btn_sm4_ecb3:
                sm4ECBper("53");
                break;
        }
        Button btn = (Button)v;
        btn.setEnabled(false);
    }

    private Socket getConnectSocket(String ip, int port, int cmd) {
        try {
            Socket socket = new Socket();
            SocketAddress address = new InetSocketAddress(ip, port);
            socket.connect(address);
            LogUtil.d(TAG, "isConnected=" + socket.isConnected());
            if (socket.isConnected()) {
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                outputStream.write(StrUtil.intToByteArray(cmd));
                outputStream.flush();
            } else {
                DialogUtil.showConfirmDialog("Error:", "Connect to wifi server failed!", null);
                return null;
            }
            return socket;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void recvFileData(int cmd, String fileName) {
        LogUtil.d(TAG, "Cmd= " + cmd + "Recv file name=" + fileName);
        Socket socket = getConnectSocket(SERVER_IPADDR, SERVER_PORT, cmd);
        if (socket != null && socket.isConnected()) {
            try {
                File file = new File(fileName);
                if (file.exists()) {
                    file.createNewFile();
                }

                FileOutputStream fileOutputStream = new FileOutputStream(file);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String recvData = reader.readLine();
                LogUtil.d(TAG, "recvData len=" + recvData.length());

                while ((recvData = reader.readLine()) != null) {
                    LogUtil.d(TAG, "recvData len=" + recvData.length());
                    fileOutputStream.write(recvData.getBytes());
                    fileOutputStream.write("\n".getBytes());
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendFileData(int cmd, String fileName) {
        LogUtil.d(TAG, "Cmd= " + cmd + "Send file name=" + fileName);
        Socket socket = getConnectSocket(SERVER_IPADDR, SERVER_PORT, cmd);
        if (socket != null && socket.isConnected()) {
            try {
                File file = new File(fileName);
                if (!file.exists()) {
                    DialogUtil.showConfirmDialog("Error:", "未找到结果文件", null);
                    socket.close();
                    return;
                }

                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream, "gbk"));
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                String sendData;
                while ((sendData = reader.readLine()) != null) {
                    sendData += "\n";
                    writer.write(sendData);
                }
                writer.flush();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private native void executeWIFICmd(String ip, int port, int cmd);

    static {
        System.loadLibrary("native-lib");
    }
}
