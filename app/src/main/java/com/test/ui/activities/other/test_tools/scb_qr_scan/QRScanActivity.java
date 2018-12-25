package com.test.ui.activities.other.test_tools.scb_qr_scan;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.test.logic.service_accesser.VFIServiceAccesser;
import com.test.ui.activities.BaseActivity;
import com.test.ui.activities.R;
import com.test.ui.activities.other.test_tools.scb_qr_scan.json_bean.AlipayData;
import com.test.ui.activities.other.test_tools.scb_qr_scan.json_bean.QRCSData;
import com.test.ui.activities.other.test_tools.scb_qr_scan.json_bean.ThaiQRData;
import com.test.ui.activities.other.test_tools.scb_qr_scan.json_bean.WechatData;
import com.test.util.AndroidUtil;
import com.test.util.LogUtil;
import com.test.util.QrCodeUtil;
import com.test.util.ToastUtil;
import com.vfi.smartpos.deviceservice.aidl.IDeviceService;
import com.vfi.smartpos.deviceservice.aidl.ScannerListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class QRScanActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
    private final String TAG = this.getClass().getSimpleName();
    Button btn_pay;
    Button btn_scan;
    LinearLayout ll_thaiQR;
    LinearLayout ll_QRCS;
    LinearLayout ll_wechat;
    LinearLayout ll_wechat_qr;
    LinearLayout ll_alipay;
    LinearLayout ll_alipay_qr;

    EditText et_thaiqr_amount;
    EditText et_thaiqr_transId;
    EditText et_qrcs_amount;
    EditText et_qrcs_transId;
    EditText et_qrcs_merchantPan;
    EditText et_qrcs_authorizeCode;
    EditText et_url;

    RadioButton rb_thaiqr;
    RadioButton rb_qrcs;
    RadioButton rb_wechat;
    RadioButton rb_alipay;
    RadioButton rb_alipay_gen_qr;
    RadioButton rb_wechat_gen_qr;

    ImageView imageViewAlipay;
    ImageView imageViewWechat;

    TextView tv_alipay_qr_content;
    TextView tv_wechat_qr_content;

    private int scanType = 0;
    private final int TYPE_THAIQR = 0;
    private final int TYPE_QRCS = 1;
    private final int TYPE_WECHAT = 2;
    private final int TYPE_WECHAT_GEN_QR = 3;
    private final int TYPE_ALIPAY = 4;
    private final int TYPE_ALIPAY_GEN_QR = 5;
    private VFIServiceAccesser vfiServiceAccesser;
    IDeviceService deviceService;
    String scanResult = "";

    private static final String SP_KEY = "DATA";
    private static final String KEY_URL = "URL";
    private static final String KEY_QRCODE_NUM = "QRCODE_NUM";
    private static final String KEY_ALIPAY_QRCODE_NUM = "ALIPAY_QRCODE_NUM";
    private static final String KEY_ALIPAY_SCAN_QRCODE_NUM = "ALIPAY_SCAN_QRCODE_NUM";
    private static final String KEY_WECHAT_QRCODE_NUM = "WECHAT_QRCODE_NUM";
    private static final String KEY_WECHAT_SCAN_QRCODE_NUM = "WECHAT_SCAN_QRCODE_NUM";
    private final String URL_TAIL_SAVE_DATA = "saveData";
    private final String URL_TAIL_UPDATE_QR = "updateQR";
    private final String URL_TAIL_UPDATE_ALIPAY_QR = "updateAlipayQR";
    private final String URL_TAIL_UPDATE_WECHAT_QR = "updateWechatQR";
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        vfiServiceAccesser = new VFIServiceAccesser();
        AndroidUtil.bindEntryService(vfiServiceAccesser, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                LogUtil.d(TAG, "onServiceConnected");
                if (vfiServiceAccesser != null && vfiServiceAccesser.getServiceConnection() != null) {
                    vfiServiceAccesser.getServiceConnection().onServiceConnected(name, service);
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                LogUtil.d(TAG, "onServiceDisconnected");
                if (vfiServiceAccesser != null && vfiServiceAccesser.getServiceConnection() != null) {
                    vfiServiceAccesser.getServiceConnection().onServiceDisconnected(name);
                }
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscan);
        initView();
    }

    private void initView() {
        rb_thaiqr = (RadioButton) findViewById(R.id.rb_thaiqr);
        rb_thaiqr.setOnCheckedChangeListener(this);
        rb_qrcs = (RadioButton) findViewById(R.id.rb_qrcs);
        rb_qrcs.setOnCheckedChangeListener(this);
        rb_wechat = (RadioButton) findViewById(R.id.rb_wechat);
        rb_wechat.setOnCheckedChangeListener(this);
        rb_alipay = (RadioButton) findViewById(R.id.rb_alipay);
        rb_alipay.setOnCheckedChangeListener(this);
        rb_wechat_gen_qr = (RadioButton) findViewById(R.id.rb_wechat_qr);
        rb_wechat_gen_qr.setOnCheckedChangeListener(this);
        rb_alipay_gen_qr = (RadioButton) findViewById(R.id.rb_alipay_qr);
        rb_alipay_gen_qr.setOnCheckedChangeListener(this);

        ll_thaiQR = (LinearLayout) findViewById(R.id.linear_ThaiQR);
        ll_QRCS = (LinearLayout) findViewById(R.id.linear_Qrcs);
        ll_wechat = (LinearLayout) findViewById(R.id.linear_Wechat);
        ll_wechat_qr = (LinearLayout) findViewById(R.id.linear_Wechat_generate_qr);

        ll_alipay = (LinearLayout) findViewById(R.id.linear_Alipay);
        ll_alipay_qr = (LinearLayout) findViewById(R.id.linear_Alipay_generate_qr);

        sharedPreferences = getSharedPreferences(SP_KEY, MODE_PRIVATE);

        et_thaiqr_amount = (EditText) findViewById(R.id.thaiqr_amount);
        et_thaiqr_transId = (EditText) findViewById(R.id.thaiqr_transId);
        et_qrcs_amount = (EditText) findViewById(R.id.qrcs_amount);
        et_qrcs_transId = (EditText) findViewById(R.id.qrcs_transId);
        et_qrcs_merchantPan = (EditText) findViewById(R.id.qrcs_merchantPan);
        et_qrcs_authorizeCode = (EditText) findViewById(R.id.qrcs_authorizeCode);
        et_url = (EditText) findViewById(R.id.et_url);
        et_url.setText(sharedPreferences.getString(KEY_URL, "http://10.172.28.78:8080"));

        imageViewAlipay = (ImageView) findViewById(R.id.alipay_qr);
        imageViewWechat = (ImageView) findViewById(R.id.wechat_qr);

        tv_alipay_qr_content = (TextView) findViewById(R.id.alipay_qr_content);
        tv_wechat_qr_content = (TextView) findViewById(R.id.wechat_qr_content);

        btn_scan = (Button) findViewById(R.id.btn_scan);
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vfiServiceAccesser.getServiceBinder() != null) {
                    deviceService = IDeviceService.Stub.asInterface(vfiServiceAccesser.getServiceBinder());
                    try {
                        Bundle bundle = new Bundle();
                        bundle.putString("topTitleString", "SCAN");
                        bundle.putString("upPromptString", "");
                        bundle.putString("downPromptString", "");
                        scanResult = "";
                        deviceService.getScanner(1).startScan(bundle, 60, new ScannerListener.Stub() {
                            @Override
                            public void onSuccess(String barcode) throws RemoteException {
                                scanResult = barcode;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        btn_scan.setVisibility(View.GONE);
                                        btn_pay.setVisibility(View.VISIBLE);
                                        ToastUtil.showLong("Scan success:" + scanResult);
                                    }
                                });
                            }

                            @Override
                            public void onError(int error, String message) throws RemoteException {
                                ToastUtil.showLong("Scan error:" + message);
                            }

                            @Override
                            public void onTimeout() throws RemoteException {
                                ToastUtil.showLong("Scan timeout");
                            }

                            @Override
                            public void onCancel() throws RemoteException {
                                ToastUtil.showLong("Scan canceled");
                            }
                        });
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    ToastUtil.showLong("Service not bind, please retry for a while");
                }
            }
        });

        btn_pay = (Button) findViewById(R.id.pay);
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (et_url.getText() == null || et_url.getText().length() == 0) {
                    ToastUtil.showLong("Please input url.");
                    return;
                }

                sharedPreferences = getSharedPreferences(SP_KEY, MODE_PRIVATE);
                sharedPreferences.edit().putString(KEY_URL, et_url.getText().toString()).commit();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        doHttpPostSaveTransRecord();
                    }
                }).start();

                btn_scan.setVisibility(View.VISIBLE);
                btn_pay.setVisibility(View.GONE);
            }
        });
    }

    private void doHttpPostUpdateQRBitmap() {
        sharedPreferences = getSharedPreferences(SP_KEY, MODE_PRIVATE);
        long qrcodeNum = sharedPreferences.getLong(KEY_QRCODE_NUM, 0);
        long alipayScanQrcodeNum = sharedPreferences.getLong(KEY_ALIPAY_SCAN_QRCODE_NUM, 0);
        long wechatScanQrcodeNum = sharedPreferences.getLong(KEY_WECHAT_SCAN_QRCODE_NUM, 0);
        LogUtil.d(TAG, "current qrcodeNum=" + qrcodeNum);
        LogUtil.d(TAG, "current alipayQrcodeNum=" + alipayScanQrcodeNum);
        LogUtil.d(TAG, "current wechatQrcodeNum=" + wechatScanQrcodeNum);
        String qrcodeContent = String.format("%012d", qrcodeNum);
        String alipayQrcodeContent = String.format("%012d", alipayScanQrcodeNum);
        String wechatQrcodeContent = String.format("%012d", wechatScanQrcodeNum);
        Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.qr_card);
        Bitmap alipayLogo = BitmapFactory.decodeResource(getResources(), R.drawable.ic_alipay);
        Bitmap wechatLogo = BitmapFactory.decodeResource(getResources(), R.drawable.ic_wechatpay);
        Bitmap qrCodeBitmap = QrCodeUtil.createQRImage(qrcodeContent, 500, 500, logo);
        Bitmap alipayQrCodeBitmap = QrCodeUtil.createQRImage(alipayQrcodeContent, 500, 500, alipayLogo);
        Bitmap wechatQrCodeBitmap = QrCodeUtil.createQRImage(wechatQrcodeContent, 500, 500, wechatLogo);
        String qrCodeBase64 = bitmapToBase64(qrCodeBitmap);
        String alipayQrCodeBase64 = bitmapToBase64(alipayQrCodeBitmap);
        String wechatQrCodeBase64 = bitmapToBase64(wechatQrCodeBitmap);

        qrcodeNum++;
        alipayScanQrcodeNum++;
        wechatScanQrcodeNum++;
        sharedPreferences.edit().putLong(KEY_QRCODE_NUM, qrcodeNum).commit();
        sharedPreferences.edit().putLong(KEY_ALIPAY_SCAN_QRCODE_NUM, alipayScanQrcodeNum).commit();
        sharedPreferences.edit().putLong(KEY_WECHAT_SCAN_QRCODE_NUM, wechatScanQrcodeNum).commit();

        LogUtil.d(TAG, "qrCodeBase64 len=" + qrCodeBase64.length());
        LogUtil.d(TAG, "qrCodeBase64 len=" + alipayQrCodeBase64.length());
        LogUtil.d(TAG, "qrCodeBase64 len=" + wechatQrCodeBase64.length());

        final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        try {
            OkHttpClient client = new OkHttpClient()
                    .newBuilder()
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .build();

            // ====================qrcs==========================================
            String url = et_url.getText().toString() + "/" + URL_TAIL_UPDATE_QR;
            LogUtil.d(TAG, "url=[" + url + "]");
            FormBody formBody = new FormBody.Builder()
                    .add("qrId", qrcodeContent)
                    .add("qrCode", qrCodeBase64)
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();

            Response response = client.newCall(request).execute();
            String responseJson = response.body().string();
            LogUtil.d(TAG, "responseJson=" + responseJson);
       } catch (IOException e) {
            LogUtil.d(TAG, "=========update QR error==================");
            e.printStackTrace();
        }

    }

    public String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private void doHttpPostSaveTransRecord() {
        final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        try {
            OkHttpClient client = new OkHttpClient()
                    .newBuilder()
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .build();

            String url = et_url.getText().toString() + "/" + URL_TAIL_SAVE_DATA;
            LogUtil.d(TAG, "url=[" + url + "]");
            String json = getJsonDataByType(scanType);
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            String responseJson = response.body().string();
            LogUtil.d(TAG, "responseJson=" + responseJson);
            if (responseJson.contains("200") || responseJson.contains("success")) {
                ToastUtil.showLong("Pay success.");
            } else {
                ToastUtil.showLong("Pay failed.");
            }
        } catch (IOException e) {
            LogUtil.d(TAG, "=========error==================");
            e.printStackTrace();
        }


    }

    private String getJsonDataByType(int type) {
        String jsonStr = "";
        if (type == TYPE_THAIQR) {
            String amount = et_thaiqr_amount.getText().toString();
            String transId = et_thaiqr_transId.getText().toString();
            ThaiQRData thaiQRData = new ThaiQRData();
            thaiQRData.setQrCode(scanResult);
            thaiQRData.setScanType("ThaiQR");
            thaiQRData.setAmount(amount);
            thaiQRData.setTransid(transId);
            jsonStr = JSONObject.toJSONString(thaiQRData);
        } else if (type == TYPE_QRCS) {
            String amount = et_qrcs_amount.getText().toString();
            String transId = et_qrcs_transId.getText().toString();
            String merchantPan = et_qrcs_merchantPan.getText().toString();
            String authorizeCode = et_qrcs_authorizeCode.getText().toString();
            QRCSData qrcsData = new QRCSData();
            qrcsData.setQrCode(scanResult);
            qrcsData.setScanType("QRCS");
            qrcsData.setAmount(amount);
            qrcsData.setTransid(transId);
            qrcsData.setMerchantpan(merchantPan);
            qrcsData.setAuthorizecode(authorizeCode);
            jsonStr = JSONObject.toJSONString(qrcsData);
        } else if (type == TYPE_WECHAT) {
            WechatData wechatData = new WechatData();
            wechatData.setQrCode(scanResult);
            wechatData.setScanType("Wechat");
            jsonStr = JSONObject.toJSONString(wechatData);
        } else if (type == TYPE_ALIPAY) {
            AlipayData alipayData = new AlipayData();
            alipayData.setQrCode(scanResult);
            alipayData.setScanType("Alipay");
            jsonStr = JSONObject.toJSONString(alipayData);
        }

        LogUtil.d(TAG, "Json=" + jsonStr);

        return jsonStr;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        LogUtil.d(TAG, "isChecked=" + isChecked);
        if (isChecked) {
            ll_thaiQR.setVisibility(View.GONE);
            ll_QRCS.setVisibility(View.GONE);
            ll_wechat.setVisibility(View.GONE);
            ll_wechat_qr.setVisibility(View.GONE);
            ll_alipay.setVisibility(View.GONE);
            ll_alipay_qr.setVisibility(View.GONE);
            switch (buttonView.getId()) {
                case R.id.rb_thaiqr:
                    scanType = TYPE_THAIQR;
                    ll_thaiQR.setVisibility(View.VISIBLE);
                    break;
                case R.id.rb_qrcs:
                    scanType = TYPE_QRCS;
                    ll_QRCS.setVisibility(View.VISIBLE);
                    break;
                case R.id.rb_wechat:
                    scanType = TYPE_WECHAT;
                    ll_wechat.setVisibility(View.VISIBLE);
                    break;
                case R.id.rb_alipay:
                    scanType = TYPE_ALIPAY;
                    ll_alipay.setVisibility(View.VISIBLE);
                    break;
                case R.id.rb_wechat_qr:
                    scanType = TYPE_WECHAT_GEN_QR;
                    ll_wechat_qr.setVisibility(View.VISIBLE);
                    showWechatQR();
                    break;
                case R.id.rb_alipay_qr:
                    scanType = TYPE_ALIPAY_GEN_QR;
                    ll_alipay_qr.setVisibility(View.VISIBLE);
                    showAlipayQR();
                    break;
            }
        }
        LogUtil.d(TAG, "scanType=" + scanType);
    }

    private void showAlipayQR() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (rb_alipay_gen_qr.isChecked()) {
                    SharedPreferences sharedPreferences = getSharedPreferences(SP_KEY, MODE_PRIVATE);
                    long alipayQRNum = sharedPreferences.getLong(KEY_ALIPAY_QRCODE_NUM, 0);
                    alipayQRNum += 1;
                    final String alipayQrCodeContent = "ALIPAY" + String.format("%012d", alipayQRNum);
                    Bitmap alipayLogo = BitmapFactory.decodeResource(getResources(), R.drawable.ic_alipay);
                    final Bitmap alipayQR = QrCodeUtil.createQRImage(alipayQrCodeContent, 500, 500, alipayLogo);
                    sharedPreferences.edit().putLong(KEY_ALIPAY_QRCODE_NUM, alipayQRNum).commit();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageViewAlipay.setImageBitmap(alipayQR);
                            tv_alipay_qr_content.setText(alipayQrCodeContent);
                        }
                    });

                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void showWechatQR() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (rb_wechat_gen_qr.isChecked()) {
                    SharedPreferences sharedPreferences = getSharedPreferences(SP_KEY, MODE_PRIVATE);
                    long wechatQRNum = sharedPreferences.getLong(KEY_WECHAT_QRCODE_NUM, 0);
                    wechatQRNum += 1;
                    final String wechatQrCodeContent = "WECHAT" + String.format("%012d", wechatQRNum);
                    Bitmap wechatLogo = BitmapFactory.decodeResource(getResources(), R.drawable.ic_wechatpay);
                    final Bitmap wechatQR = QrCodeUtil.createQRImage(wechatQrCodeContent, 500, 500, wechatLogo);
                    sharedPreferences.edit().putLong(KEY_WECHAT_QRCODE_NUM, wechatQRNum).commit();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageViewWechat.setImageBitmap(wechatQR);
                            tv_wechat_qr_content.setText(wechatQrCodeContent);
                        }
                    });

                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
