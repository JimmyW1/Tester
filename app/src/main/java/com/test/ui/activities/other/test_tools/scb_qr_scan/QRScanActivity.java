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
import android.widget.LinearLayout;
import android.widget.RadioButton;

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
    LinearLayout ll_thaiQR;
    LinearLayout ll_QRCS;
    LinearLayout ll_wechat;
    LinearLayout ll_alipay;

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

    private int scanType = 0;
    private final int TYPE_THAIQR = 0;
    private final int TYPE_QRCS = 1;
    private final int TYPE_WECHAT = 2;
    private final int TYPE_ALIPAY = 3;
    private VFIServiceAccesser vfiServiceAccesser;
    IDeviceService deviceService;
    String scanResult = "";

    private static final String SP_KEY = "DATA";
    private static final String KEY_URL = "URL";
    private static final String KEY_QRCODE_NUM = "QRCODE_NUM";
    private final String URL_TAIL_SAVE_DATA = "saveData";
    private final String URL_TAIL_UPDATE_QR = "updateQR";
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

        ll_thaiQR = (LinearLayout) findViewById(R.id.linear_ThaiQR);
        ll_QRCS = (LinearLayout) findViewById(R.id.linear_Qrcs);
        ll_wechat = (LinearLayout) findViewById(R.id.linear_Wechat);
        ll_alipay = (LinearLayout) findViewById(R.id.linear_Alipay);

        sharedPreferences = getSharedPreferences(SP_KEY, MODE_PRIVATE);

        et_thaiqr_amount = (EditText) findViewById(R.id.thaiqr_amount);
        et_thaiqr_transId = (EditText) findViewById(R.id.thaiqr_transId);
        et_qrcs_amount = (EditText) findViewById(R.id.qrcs_amount);
        et_qrcs_transId = (EditText) findViewById(R.id.qrcs_transId);
        et_qrcs_merchantPan = (EditText) findViewById(R.id.qrcs_merchantPan);
        et_qrcs_authorizeCode = (EditText) findViewById(R.id.qrcs_authorizeCode);
        et_url = (EditText) findViewById(R.id.et_url);
        et_url.setText(sharedPreferences.getString(KEY_URL, "http://10.172.28.78:8080"));

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
//                                ToastUtil.showLong("Scan success:" + barcode);
                                scanResult = barcode;
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        doHttpPostSaveTransRecord();
                                    }
                                }).start();
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
    }

    private void doHttpPostUpdateQRBitmap() {
        sharedPreferences = getSharedPreferences(SP_KEY, MODE_PRIVATE);
        long qrcodeNum = sharedPreferences.getLong(KEY_QRCODE_NUM, 0);
        LogUtil.d(TAG, "current qrcodeNum=" + qrcodeNum);
        String qrcodeContent = String.format("%012d", qrcodeNum);
        Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.qr_card);
        Bitmap qrCodeBitmap = QrCodeUtil.createQRImage(qrcodeContent, 500, 500, logo);
        String qrCodeBase64 = bitmapToBase64(qrCodeBitmap);

        qrcodeNum++;
        sharedPreferences.edit().putLong(KEY_QRCODE_NUM, qrcodeNum).commit();

        LogUtil.d(TAG, "qrCodeBase64 len=" + qrCodeBase64.length());

        final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();

        String url = et_url.getText().toString() + "/" + URL_TAIL_UPDATE_QR;
        LogUtil.d(TAG, "url=[" + url + "]");
        FormBody formBody = new FormBody.Builder()
                .add("qrCode", qrCodeBase64)
                .build();
//        String json = "{\"qrCode\":\"" + qrCodeBase64 + "\"}";
//        LogUtil.d(TAG, "json=[" + json + "]");
        try {
//            RequestBody body = RequestBody.create(JSON, json);
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

        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();

        String url = et_url.getText().toString() + "/" + URL_TAIL_SAVE_DATA;
        LogUtil.d(TAG, "url=[" + url + "]");
        String json = getJsonDataByType(scanType);
        try {
            FormBody formBody = new FormBody.Builder()
                    .add("name", "android")
                    .add("price", "50")
                    .build();
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

        new Thread(new Runnable() {
            @Override
            public void run() {
                doHttpPostUpdateQRBitmap();
            }
        }).start();
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
            LogUtil.d(TAG, "jsonStr=[" + jsonStr + "]");
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
            LogUtil.d(TAG, "jsonStr=[" + jsonStr + "]");
        } else if (type == TYPE_WECHAT) {
            WechatData wechatData = new WechatData();
            wechatData.setQrCode(scanResult);
            wechatData.setScanType("Wechat");
        } else if (type == TYPE_ALIPAY) {
            AlipayData alipayData = new AlipayData();
            alipayData.setQrCode(scanResult);
            alipayData.setScanType("Alipay");
        }

        return jsonStr;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        LogUtil.d(TAG, "isChecked=" + isChecked);
        if (isChecked) {
            ll_thaiQR.setVisibility(View.GONE);
            ll_QRCS.setVisibility(View.GONE);
            ll_wechat.setVisibility(View.GONE);
            ll_alipay.setVisibility(View.GONE);
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
            }
        }
        LogUtil.d(TAG, "scanType=" + scanType);
    }
}
