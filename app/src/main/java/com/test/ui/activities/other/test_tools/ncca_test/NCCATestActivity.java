package com.test.ui.activities.other.test_tools.ncca_test;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.socsi.smartposapi.gmalgorithm.GMAlgorithm;
import com.test.ui.activities.BaseActivity;
import com.test.ui.activities.MyApplication;
import com.test.ui.activities.R;
import com.test.util.FileUtil;
import com.test.util.LogUtil;
import com.test.util.StringUtil;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;

public class NCCATestActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nccatest);
        // 检查测试环境，测试数据源等
        checkTestEnv();
        Button button;
        button = (Button) findViewById(R.id.btn_ncca_alg_test);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn_ncca_perf_test);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn_ncca_wifi_perf_test);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn_ncca_ssl_test);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn_ncca_simulation_test);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.btn_ncca_cert_chain_test);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_ncca_alg_test:
                intent = new Intent(MyApplication.getContext(), NCCAAlgorithmTestctivity.class);
                startActivity(intent);
                break;
            case R.id.btn_ncca_perf_test:
                intent = new Intent(MyApplication.getContext(), NCCAPerformanceActivity.class);
                intent.putExtra("COMMTYPE", "USB");
                startActivity(intent);
                break;
            case R.id.btn_ncca_wifi_perf_test:
                intent = new Intent(MyApplication.getContext(), NCCAPerformanceActivity.class);
                intent.putExtra("COMMTYPE", "WIFI");
                startActivity(intent);
                break;
            case R.id.btn_ncca_ssl_test:
                intent = new Intent(MyApplication.getContext(), NCCASSLTestActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_ncca_simulation_test:
                intent = new Intent(MyApplication.getContext(), NCCALoadTEKActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_ncca_cert_chain_test:
                doNCCACertChainTest();
                break;
        }
    }

    /**
     * 检查测试环境，即算法正确性、性能测试、随机数生成所需要的数据源文件和生成目标目录等是否存在.
     * 1. 算法正确性环境(如果未正确配置，会从res/assets/ncca_alg_data.zip解压到/sdcard/guomi/目录下)
     *      (1) 需要在/sdcard/目录下存在guomi目录，并且在目录
     *      (2) 在/sdcard/guomi/目录中需要存在验算数据源文件,包括如下:
     *          SM2验证数据-加密-10.txt, SM2验证数据-解密-10.txt, SM2签名-无预处理-10.txt, SM2签名-预处理后-10.txt, SM2验签-无预处理-10.txt, SM2验签-预处理后-10.txt
     *          SM3杂凑验证-10.txt
     *          SM4验证数据-10-MAC.txt
     *          SM4验证数据-加密-10-CBC.txt SM4验证数据-加密-10-ECB.txt SM4验证数据-解密-10-CBC.txt SM4验证数据-解密-10-ECB.txt
     *          RSA2048-加密-10.txt, RSA2048-解密-10.txt, RSA2048-签名-10.txt, RSA2048-签名-10.txt
     *      (3) 验证结果会输出到/sdcard/guomi/test_results/目录，将目录中的内容拷给国密检测人员验证即可，内容格式请不要改动。
     *      (4) 国密检查人员验证通过后，根据test_result里面的内容填写到《算法正确性.docx》文档中，然后提交文档给工作人员.
     *
     * 2. 性能测试 (如果用户不是用自己的测试数据，此函数自己生产测试数据，需等待几秒钟)
     *      (1) 如果用户要使用自己的测试数据，请把测试数据放于/sdcard/gmper/目录下.
     *      (2) 测试数据源文件命名规则为: ram_data_source1.txt ram_data_source2.txt ram_data_source3.txt
     *      (3) 测试数据源文件内容规定：一行128字节随机数转换成Hex String,一共1024行
     *      (4) 性能测试结果存放于/sdcard/gmper/test_results/，其中测试项单项平均时间没有要求，但是如下两个比值需正确：
     *          SM2加密：SM2解密 尽量比值接近 1：2
     *          SM2无预处理签名：无预处理验签 尽量比值接近 2：1
     *          如果自测数据满足大概满足如上比值，可将目录中的内容拷给国密测试人员验证。
     *      注意： 国密测试性能要求数据源是存放在PC端，测试程序从PC端读取数据源后再POS端测试统计时间再存回PC端，并且如果有多种通信方式需测试多组数据。
     *            X970测试只给国密测试人员说支持USB方式，别的都不支持。测试的时候连着USB线即可，国密测试人员不会查看你是否是从PC端读取数据，存回PC端。
     *            但如果人家问起，你说是通过USB这种方式来测试的。
     *
     * 3. 生产128M随机数
     *      (1) 产生随机数存放目录：/sdcard/rombin/
     *      (2) 随机数产生好后，找老吴要随机数检查程序，先自己检查，不要直接提交，自己检测通过后再提交，否则提交数据如果有问题，将会收到一个整改通知，很麻烦
     *      (3) 随机数检测程序目录名叫sts-2.1.1-release，使用方法cgwin进入目录运行脚本random_test.sh 大概几个小时检测完毕
     *      (4) 其中rt1.sh能并行测试，测试时间大概1个小时，但2017.09 x970测试的时候只有老吴PC环境能使用。
     */
    private void checkTestEnv() {
        try {
            // 算法正确性环境检查
            String fileCatage = Environment.getExternalStorageDirectory() + "/";
            File file = new File(fileCatage + "guomi");
            if (!file.exists()) {
                file.mkdir();
                File resultDir = new File(fileCatage + "guomi/test_results");
                resultDir.mkdirs();
                InputStream inputStream = this.getAssets().open("ncca_alg_data.zip");
                FileUtil.Unzip(inputStream, "/sdcard/guomi/");
            } else if (!file.isDirectory()) {
                file.delete();
                file.mkdir();
                File resultDir = new File(fileCatage + "guomi/test_results");
                resultDir.mkdirs();
                InputStream inputStream = this.getAssets().open("ncca_alg_data.zip");
                FileUtil.Unzip((FileInputStream) inputStream, "/sdcard/guomi/");
            }

            file = new File(fileCatage + "guomi/test_results");
            if (!file.exists()) {
                file.mkdirs();
            } else if (!file.isDirectory()) {
                file.delete();
                file.mkdirs();
            }

            // 性能检测环境检查
            file = new File(fileCatage + "gmper");
            if (!file.exists()) {
                file.mkdir();
                File resultDir = new File(fileCatage + "gmper/test_results");
                resultDir.mkdirs();
                createPerformTestData("gmper/ram_data_source1.txt");
                createPerformTestData("gmper/ram_data_source2.txt");
                createPerformTestData("gmper/ram_data_source3.txt");
            } else if (!file.isDirectory()) {
                file.delete();
                file.mkdir();
                File resultDir = new File(fileCatage + "gmper/test_results");
                resultDir.mkdirs();
                createPerformTestData("gmper/ram_data_source1.txt");
                createPerformTestData("gmper/ram_data_source2.txt");
                createPerformTestData("gmper/ram_data_source3.txt");
            }

            file = new File(fileCatage + "gmper/ram_data_source1.txt");
            if (!file.exists()) {
                createPerformTestData("gmper/ram_data_source1.txt");
            }

            file = new File(fileCatage + "gmper/ram_data_source2.txt");
            if (!file.exists()) {
                createPerformTestData("gmper/ram_data_source2.txt");
            }

            file = new File(fileCatage + "gmper/ram_data_source3.txt");
            if (!file.exists()) {
                createPerformTestData("gmper/ram_data_source3.txt");
            }

            file = new File(fileCatage + "gmper/test_results");
            if (!file.exists()) {
                file.mkdirs();
            } else if (!file.isDirectory()) {
                file.delete();
                file.mkdirs();
            }

            // 128M随机数目录检查
            file = new File(fileCatage + "rombin");
            if (!file.exists()) {
                file.mkdir();
            } else if (!file.isDirectory()) {
                file.delete();
                file.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createPerformTestData(final String fileName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String fileCatage = Environment.getExternalStorageDirectory() + "/";
                    File file = new File(fileCatage + fileName);
                    file.createNewFile();
                    FileOutputStream fos = new FileOutputStream(file);
                    BufferedOutputStream out = new BufferedOutputStream(fos);
                    BufferedWriter fout = new BufferedWriter(new OutputStreamWriter(out));
                    GMAlgorithm gmAlgorithm = new GMAlgorithm();
                    gmAlgorithm.init();
                    for (int i = 0; i < 1024; i++) {
                        byte[] resultBytes  = gmAlgorithm.getRandom(128);
                        String tmp = StringUtil.byte2HexStr(resultBytes) + "\n";
                        out.write(tmp.getBytes());
                        out.flush();
                    }
                    fout.close();
                    out.close();
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void doNCCACertChainTest() {
        start_test_sm2_cert_chain();
    }

    private native void start_test_sm2_cert_chain();

    static {
        System.loadLibrary("native-lib");
    }
}
