package com.test.ui.activities.other.test_tools;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Xml;
import android.view.View;
import android.widget.Button;

import com.test.ui.activities.BaseActivity;
import com.test.ui.activities.MyApplication;
import com.test.ui.activities.R;
import com.test.ui.activities.other.test_tools.j300_test.J300TestActivity;
import com.test.ui.activities.other.test_tools.ncca_test.NCCATestActivity;
import com.test.ui.activities.other.test_tools.scb_qr_scan.QRScanActivity;
import com.test.ui.activities.other.test_tools.study.demon.fragments.first_example.FragmentExample1Activity;
import com.test.ui.activities.other.test_tools.study.demon.rxjava.RxJavaTestActivity;
import com.test.ui.activities.other.test_tools.study.demon.toolbar.ToolBarDemonActivity;
import com.test.util.DialogUtil;
import com.test.util.ToastUtil;
import com.test.util.xmlparse.CardConfigParser;
import com.test.util.xmlparse.EmvApplicationsConfigParser;
import com.test.util.xmlparse.EmvKeysConfigParser;
import com.test.util.xmlparse.HostConfigParser;
import com.test.util.xmlparse.QrParameterConfigParser;
import com.test.util.xmlparse.TerminalConfigParser;
import com.test.util.xmlparse.XmlParser;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestToolsActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tools);
        Button btn = (Button) findViewById(R.id.btn_ncca_test);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_j300_test);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_study);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_qr_scan);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn_generate_tms_configs);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_ncca_test:
                intent = new Intent(MyApplication.getContext(), NCCATestActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_j300_test:
                intent = new Intent(MyApplication.getContext(), J300TestActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_study:
//                intent = new Intent(MyApplication.getContext(), StudyTestActivity.class);
//                intent = new Intent(MyApplication.getContext(), TestOnMeasureOnlayActivity.class);
//                intent = new Intent(MyApplication.getContext(), TestSelfViewPager.class);
//                intent = new Intent(MyApplication.getContext(), TestScrollCollision1Activity.class);
//                intent = new Intent(MyApplication.getContext(), TestScrollCollision2Activity.class);
//                intent = new Intent(MyApplication.getContext(), TestMyCircleViewActivity.class);
//                intent = new Intent(MyApplication.getContext(), TestNotificationActivity.class);
//                intent = new Intent(MyApplication.getContext(), AnimationTest1Activity.class);
//                intent = new Intent(MyApplication.getContext(), TestMyTestViewActivity.class);
//                intent = new Intent(MyApplication.getContext(), ToolBarDemonActivity.class);
//                intent = new Intent(MyApplication.getContext(), FragmentExample1Activity.class);
                intent = new Intent(MyApplication.getContext(), RxJavaTestActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_qr_scan:
                intent = new Intent(MyApplication.getContext(), QRScanActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_generate_tms_configs:
                DialogUtil.showInputDialog("Please input db version", new DialogUtil.InputDialogListener() {
                    @Override
                    public void setInputString(String inputStr, boolean isConfirm) {
                        if (inputStr == null) {
                            inputStr = "";
                        }
                        inputStr = inputStr.trim();
                        try {
                            startGenerateTmsXml(Integer.parseInt(inputStr));
                        } catch (Exception e) {
                            e.printStackTrace();
                            DialogUtil.showConfirmDialog("Error", "Please input a correct db version(int).", null);
                        }
                    }
                });
                break;
        }
    }

    private void startGenerateTmsXml(final int dbVersion) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String sdcardPath = Environment.getExternalStorageDirectory().getPath();
                File file = new File(sdcardPath + "/xml/TMS_parameters.xml");

                FileOutputStream fos = null;
                XmlSerializer tmsXmlSerializer = Xml.newSerializer();
                try {
                    fos = new FileOutputStream(file);
                    tmsXmlSerializer.setOutput(fos, "utf-8");
                    tmsXmlSerializer.startDocument("utf-8", true);
                    tmsXmlSerializer.startTag(null, "appParameters");

                    tmsXmlSerializer.startTag(null, "parameter");
                    tmsXmlSerializer.startTag(null, "databaseFieldName");
                    tmsXmlSerializer.text("AP-DB_VERSION-DB_VERSION");
                    tmsXmlSerializer.endTag(null, "databaseFieldName");
                    tmsXmlSerializer.startTag(null, "name");
                    tmsXmlSerializer.text("DB_VERSION");
                    tmsXmlSerializer.endTag(null, "name");
                    tmsXmlSerializer.startTag(null, "value");
                    tmsXmlSerializer.text("" + dbVersion);
                    tmsXmlSerializer.endTag(null, "value");
                    tmsXmlSerializer.endTag(null, "parameter");

                    XmlParser xmlParser = new XmlParser();
                    EmvApplicationsConfigParser emvAppConfigParser = new EmvApplicationsConfigParser();
//                    xmlParser.parserConfigFile(emvAppConfigParser, tmsXmlSerializer);
                    EmvKeysConfigParser emvKeysConfigParser = new EmvKeysConfigParser();
//                    xmlParser.parserConfigFile(emvKeysConfigParser, tmsXmlSerializer);
                    HostConfigParser hostConfigParser = new HostConfigParser();
//                    xmlParser.parserConfigFile(hostConfigParser, tmsXmlSerializer);
                    CardConfigParser cardConfigParser = new CardConfigParser();
//                    xmlParser.parserConfigFile(cardConfigParser, tmsXmlSerializer);
                    TerminalConfigParser terminalConfigParser = new TerminalConfigParser();
//                    xmlParser.parserConfigFile(terminalConfigParser, tmsXmlSerializer);
                    QrParameterConfigParser qrParameterConfigParser = new QrParameterConfigParser();
                    xmlParser.parserConfigFile(qrParameterConfigParser, tmsXmlSerializer);

                    tmsXmlSerializer.endTag(null, "appParameters");
                    tmsXmlSerializer.endDocument();

                    new Handler(getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showLong("Generate tms parameter xml finished.");
                        }
                    });

                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
