package com.test.logic.testmodules.boc_modules;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.RemoteException;
import android.util.Log;

import com.boc.aidl.printer.AidlPrinter;
import com.boc.aidl.deviceService.AidlDeviceService;
import com.boc.aidl.printer.AidlPrinterListener;
import com.boc.aidl.scanner.AidlScanner;
import com.boc.aidl.scanner.AidlScannerListener;
import com.test.logic.annotations.TestDetailCaseAnnotation;
import com.test.logic.testmodules.basemodule.BaseModule;
import com.test.logic.testmodules.boc_modules.common.DeviceService;
import com.test.ui.activities.MyApplication;
import com.test.ui.activities.R;
import com.test.util.ToastUtil;

import java.util.HashMap;

public class PrinterTestCase extends BaseModule {
    static final String TAG = "VerifoneTestClient";
//    public static final int DEFAULT_CODE =  -10000;
//    public static final int DEFAULT_STATUS =  -20000;
//    int code_ret = DEFAULT_CODE;
//    int  status_ret = DEFAULT_STATUS;


    public void API01printBarCode(String barCode,int width, int height, int position, int timeout, AidlPrinterListener listener) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                AidlPrinter.Stub.asInterface(handle.getPrinter()).printBarCode(barCode, width, height, position, timeout, listener);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void API02print(String json, Bitmap[] bitmap, AidlPrinterListener listener) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                AidlPrinter.Stub.asInterface(handle.getPrinter()).print(json, bitmap, listener);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void API03paperSkip(int line) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                AidlPrinter.Stub.asInterface(handle.getPrinter()).paperSkip(line);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void API04printQrCode(String qrCode, int height, int errorCorrectingLevel, int position, int timeout, AidlPrinterListener listener) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                AidlPrinter.Stub.asInterface(handle.getPrinter()).printQrCode(qrCode, height, errorCorrectingLevel, position, timeout, listener);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public int API05getStatus() {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                int status =  AidlPrinter.Stub.asInterface(handle.getPrinter()).getStatus();
                switch(status){
                    case 0x00:
                        ToastUtil.showLong("没有错误，一切正常");
                        break;
                    case 0x01:
                        ToastUtil.showLong("打印数据解析错误");
                        break;
                    case 0x02:
                        ToastUtil.showLong("打印机缺纸");
                        break;
                    case 0x03:
                        ToastUtil.showLong("打印机温度高");
                        break;
                    case 0x04:
                        ToastUtil.showLong("打印机繁忙");
                        break;
                    case 0x05:
                        ToastUtil.showLong("硬件错误");
                        break;
                    case 0x06:
                        ToastUtil.showLong("打印机卡纸");
                        break;
                    case 0xFF:
                        ToastUtil.showLong("其他错误");
                        break;
                }
                return status;

            }
            return -1;
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void API06printBitMap(int offset, Bitmap bitmap, int timeout, AidlPrinterListener listener) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                AidlPrinter.Stub.asInterface(handle.getPrinter()).printBitMap(offset, bitmap, timeout, listener);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void API07printScript(String data, java.util.Map map, int timeout, AidlPrinterListener listener) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                AidlPrinter.Stub.asInterface(handle.getPrinter()).printScript(data, map, timeout, listener);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private Bitmap[] getBitmap()
    {
        Bitmap localBitmap = BitmapFactory.decodeResource(MyApplication.getContext().getResources(), R.drawable.boclogo);
        Bitmap[] bitmaps = new Bitmap[5];
        bitmaps[0] = localBitmap;
        return bitmaps;
    }


    String scan_barcode=null;
    boolean b_status_ret =false;
    AidlScannerListener getNewScannerListener() {
        return new AidlScannerListener.Stub() {
            @Override
            public void onScanResult(String[] barcode) throws RemoteException {
                Log.i(TAG,"barcode=" + barcode[0]);
                scan_barcode=barcode[0];
                b_status_ret = true;
            }


            @Override
            public void onFinish() throws RemoteException {
                Log.i(TAG,"Finish input");
                b_status_ret = true;
            }
        };
    }

    public boolean API01startScan_1(int scanType,int timeout, AidlScannerListener listener) {
        b_status_ret = false;
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                AidlScanner.Stub.asInterface(handle.getScan()).startScan(scanType,timeout, listener);
            }
            for(int i=0; i<timeout/1000; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(b_status_ret)
                {
                    return b_status_ret;
                }
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return b_status_ret;
    }

    /**
     * 打印脚本案例
     */

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN001", resDialogTitle =
            "CASE_print_CN001", APIName = "print")
    public void CASE_print_CN001() {
        Log.i(TAG, "CASE_print_CN001");
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.boclogo);
        Bitmap[] bitmaps = new Bitmap[5];
        bitmaps[0] = bitmap;

        API02print("{'spos':[{'content-type':'txt','content':'\n\n','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'小票测试','position':'center','bold':'0','height':'-1','size':'3'},{'content-type':'jpg','content':'','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'持卡人存根','position':'right','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'商户：测试测试测试测试','position':'left','bold':'0','height':'-1','size':'3'},{'content-type':'txt','content':'    商户编号：812002110030001','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    终端编号：20150909','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    操作员号：001','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'交易：扫一扫支付','position':'left','bold':'0','height':'-1','size':'3'},{'content-type':'txt','content':'    支付渠道：微信支付','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    日期时间：2016\\/04\\/28  15:27:51','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    参考号：000014103147','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'one-dimension','content':'12312312312424','position':'center','bold':'0','height':'1','size':'3'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'金额：RMB  0.01','position':'left','bold':'1','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'备注：','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'本人确认以上交易，同意将其计入本卡账户 ','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'I ACKNOWLEDGE SATISFACTORY RECEIPT OF RELATIVE GOODS\\/SERVICE','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'two-dimension','content':'头，辛苦啦','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'\n\n\n\n','position':'center','bold':'0','height':'-1','size':'2'}]}", bitmaps, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN001: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN002", resDialogTitle =
            "CASE_print_CN002", APIName = "print")
    public void CASE_print_CN002() {
        Log.i(TAG, "CASE_print_CN002");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'txt'," +
                "'content':'中国银行智能项目测试程序'," +
                "'position':'center'," +
                "'bold':'0'," +
                "'offset':'40'," +
                "'size':'1'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN002: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {
                int status=API05getStatus();
            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN003", resDialogTitle =
            "CASE_print_CN003", APIName = "print")
    public void CASE_print_CN003() {
        Log.i(TAG, "CASE_print_CN003");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'txt'," +
                "'content':'中国银行智能项目测试程序(左对齐)'," +
                "'position':'left'," +
                "'bold':'0'," +
                "'offset':'0'," +
                "'size':'2'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN003: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN004", resDialogTitle =
            "CASE_print_CN004", APIName = "print")
    public void CASE_print_CN004() {
        Log.i(TAG, "CASE_print_CN004");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'txt'," +
                "'content':'中国银行智能项目测试程序（右对齐）'," +
                "'position':'right'," +
                "'bold':'0'," +
                "'offset':'0'," +
                "'size':'3'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN004: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN005", resDialogTitle =
            "CASE_print_CN005", APIName = "print")
    public void CASE_print_CN005() {
        Log.i(TAG, "CASE_print_CN005");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'txt'," +
                "'content':'中国银行智能项目测试程序（居中对齐）'," +
                "'position':'center'," +
                "'bold':'1'," +
                "'offset':'0'," +
                "'size':'3'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN005: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN006", resDialogTitle =
            "CASE_print_CN006", APIName = "print")
    public void CASE_print_CN006() {
        Log.i(TAG, "CASE_print_CN006");
        CASE_print_CN003();
        API02print("{'spos':[" +

                "{" +
                "'content-type':'txt'," +
                "'content':'中国银行智能项目测试程序（距离左边界2.5个汉字）'," +
                "'position':'left'," +
                "'bold':'0'," +
                "'offset':'40'," +
                "'size':'2'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN006: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN007", resDialogTitle =
            "CASE_print_CN007", APIName = "print")
    public void CASE_print_CN007() {
        Log.i(TAG, "CASE_print_CN007");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'txt'," +
                "'content':'中国银行\n智能POS\r测试程序'," +
                "'position':'center'," +
                "'bold':'0'," +
                "'offset':'0'," +
                "'size':'3'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN007: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN008", resDialogTitle =
            "CASE_print_CN008", APIName = "print")
    public void CASE_print_CN008() {
        Log.i(TAG, "CASE_print_CN008");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'txt'," +
                "'content':'中国银行12345ABCDabcd???'," +
                "'position':'center'," +
                "'bold':'0'," +
                "'offset':'0'," +
                "'size':'3'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN008: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN009", resDialogTitle =
            "CASE_print_CN009", APIName = "print")
    public void CASE_print_CN009() {
        Log.i(TAG, "CASE_print_CN009");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'txt'," +
                "'content':'囧槑（mei）烎(yin)氼（ni）嘂（jiao）嘦（jiao）嫑（biao）圐（ku）圙（lue）玊（su）孖（ma）丼（jing）嬛(huan)甯(ning)寗(ning)'," +
        "'position':'center'," +
                "'bold':'0'," +
                "'offset':'0'," +
                "'size':'3'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN009: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN010", resDialogTitle =
            "CASE_print_CN010", APIName = "print")
    public void CASE_print_CN010() {
        Log.i(TAG, "CASE_print_CN010");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'txt'," +
                "'content':'~！·#￥%……—*（）——+{}｜：“《》？[]、；‘，。、'," +
                "'position':'center'," +
                "'bold':'0'," +
                "'offset':'0'," +
                "'size':'3'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN010: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN011", resDialogTitle =
            "CASE_print_CN011", APIName = "print")
    public void CASE_print_CN011() {
        Log.i(TAG, "CASE_print_CN011");
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.boclogo);
        Bitmap[] bitmaps = new Bitmap[5];
        bitmaps[0] = bitmap;
        API02print("{'spos':[" +

                "{" +
                "'content-type':'jpg'," +
                "'position':'center'," +
                "'height':'-1'," +
                "'size':'1'" +
                "}]}", bitmaps, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN011: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN012", resDialogTitle =
            "CASE_print_CN012", APIName = "print")
    public void CASE_print_CN012() {
        Log.i(TAG, "CASE_print_CN012");
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.boclogo);
        Bitmap[] bitmaps = new Bitmap[5];
        bitmaps[0] = bitmap;

        API02print("{'spos':[" +

                "{" +
                "'content-type':'jpg'," +
                "'position':'left'," +
                "'height':'-1'," +
                "'size':'1'" +
                "}]}", bitmaps, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN012: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN013", resDialogTitle =
            "CASE_print_CN013", APIName = "print")
    public void CASE_print_CN013() {
        Log.i(TAG, "CASE_print_CN013");
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.boclogo);
        Bitmap[] bitmaps = new Bitmap[5];
        bitmaps[0] = bitmap;
        API02print("{'spos':[" +

                "{" +
                "'content-type':'jpg'," +
                "'position':'right'," +
                "'height':'-1'," +
                "'size':'1'" +
                "}]}", bitmaps, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN013: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN014", resDialogTitle =
            "CASE_print_CN014", APIName = "print")
    public void CASE_print_CN014() {
        Log.i(TAG, "CASE_print_CN014");
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.boclogo);
        Bitmap[] bitmaps = new Bitmap[5];
        bitmaps[0] = bitmap;
        API02print("{'spos':[" +

                "{" +
                "'content-type':'jpg'," +
                "'position':'left'," +
                "'offset':'40'," +
                "'height':'-1'," +
                "'size':'1'" +
                "}]}", bitmaps, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN014: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN015", resDialogTitle =
            "CASE_print_CN015", APIName = "print")
    public void CASE_print_CN015() {
        Log.i(TAG, "CASE_print_CN015");
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.bitmap_1);
        Bitmap[] bitmaps = new Bitmap[5];
        bitmaps[0] = bitmap;
        API02print("{'spos':[" +

                "{" +
                "'content-type':'jpg'," +
                "'position':'center'," +
                "'height':'-1'," +
                "'size':'1'" +
                "}]}", bitmaps, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN015: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN016", resDialogTitle =
            "CASE_print_CN016", APIName = "print")
    public void CASE_print_CN016() {
        Log.i(TAG, "CASE_print_CN016");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'one-dimension'," +
                "'content':'891110847565611252'," +
                "'position':'center'," +
                "'offset':'0'," +
                "'height':'-1'," +
                "'size':'-1'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN016: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN017", resDialogTitle =
            "CASE_print_CN017", APIName = "print")
    public void CASE_print_CN017() {
        Log.i(TAG, "CASE_print_CN017");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'one-dimension'," +
                "'content':'891110847565611252'," +
                "'position':'left'," +
                "'offset':'0'," +
                "'height':'-1'," +
                "'size':'-1'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN017: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN018", resDialogTitle =
            "CASE_print_CN018", APIName = "print")
    public void CASE_print_CN018() {
        Log.i(TAG, "CASE_print_CN018");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'one-dimension'," +
                "'content':'891110847565611252'," +
                "'position':'right'," +
                "'offset':'0'," +
                "'height':'-1'," +
                "'size':'-1'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN018: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN019", resDialogTitle =
            "CASE_print_CN019", APIName = "print")
    public void CASE_print_CN019() {
        Log.i(TAG, "CASE_print_CN019");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'one-dimension'," +
                "'content':'ABCabc-$.891110847565611252'," +
                "'position':'center'," +
                "'offset':'0'," +
                "'height':'-1'," +
                "'size':'2'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN019: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN020", resDialogTitle =
            "CASE_print_CN020", APIName = "print")
    public void CASE_print_CN020() {
        Log.i(TAG, "CASE_print_CN020");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'one-dimension'," +
                "'content':'891110847565611252'," +
                "'position':'left'," +
                "'offset':'40'," +
                "'height':'-1'," +
                "'size':'2'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN020: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN021", resDialogTitle =
            "CASE_print_CN021", APIName = "print")
    public void CASE_print_CN021() {
        Log.i(TAG, "CASE_print_CN021");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'one-dimension'," +
                "'content':'891110847565611252'," +
                "'position':'center'," +
                "'offset':'0'," +
                "'height':'1'," +
                "'size':'-1'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN021: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN022", resDialogTitle =
            "CASE_print_CN022", APIName = "print")
    public void CASE_print_CN022() {
        Log.i(TAG, "CASE_print_CN022");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'one-dimension'," +
                "'content':'891110847565611252'," +
                "'position':'center'," +
                "'offset':'0'," +
                "'height':'320'," +
                "'size':'-1'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN022: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN023", resDialogTitle =
            "CASE_print_CN023", APIName = "print")
    public void CASE_print_CN023() {
        Log.i(TAG, "CASE_print_CN023");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'two-dimension'," +
                "'content':'中国银行智能项目测试'," +
                "'position':'center'," +
                "'offset':'0'," +
                "'size':'150'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN023: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN024", resDialogTitle =
            "CASE_print_CN024", APIName = "print")
    public void CASE_print_CN024() {
        Log.i(TAG, "CASE_print_CN024");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'two-dimension'," +
                "'content':'中国银行智能项目测试'," +
                "'position':'left'," +
                "'offset':'0'," +
                "'size':'250'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN024: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN025", resDialogTitle =
            "CASE_print_CN025", APIName = "print")
    public void CASE_print_CN025() {
        Log.i(TAG, "CASE_print_CN025");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'two-dimension'," +
                "'content':'中国银行智能项目测试'," +
                "'position':'right'," +
                "'offset':'0'," +
                "'size':'350'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN025: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN026", resDialogTitle =
            "CASE_print_CN026", APIName = "print")
    public void CASE_print_CN026() {
        Log.i(TAG, "CASE_print_CN026");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'two-dimension'," +
                "'content':'中国银行\n智能POS\r测试'," +
                "'position':'center'," +
                "'offset':'0'," +
                "'size':'350'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN026: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN027", resDialogTitle =
            "CASE_print_CN027", APIName = "print")
    public void CASE_print_CN027() {
        Log.i(TAG, "CASE_print_CN027");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'two-dimension'," +
                "'content':'中国银行智能项目测试'," +
                "'position':'left'," +
                "'offset':'40'," +
                "'size':'150'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN027: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN028", resDialogTitle =
            "CASE_print_CN028", APIName = "print")
    public void CASE_print_CN028() {
        Log.i(TAG, "CASE_print_CN028");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'two-dimension'," +
                "'content':'囧槑（mei）烎(yin)氼（ni）嘂（jiao）嘦（jiao）嫑（biao）圐（ku）圙（lue）玊（su）孖（ma）丼（jing）嬛(huan)甯(ning)寗(ning)'," +
        "'position':'center'," +
                "'offset':'0'," +
                "'size':'150'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN028: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN029", resDialogTitle =
            "CASE_print_CN029", APIName = "print")
    public void CASE_print_CN029() {
        Log.i(TAG, "CASE_print_CN029");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'two-dimension'," +
                "'content':'中行测试ABCabc123<>'," +
                "'position':'center'," +
                "'offset':'0'," +
                "'size':'150'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN029: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN030", resDialogTitle =
            "CASE_print_CN030", APIName = "print")
    public void CASE_print_CN030() {
        Log.i(TAG, "CASE_print_CN030");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'two-dimension'," +
                "'content':'~！·#￥%……—*（）——+{}｜：“《》？，。、；‘[]、－＝'," +
                "'position':'center'," +
                "'offset':'0'," +
                "'size':'150'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN030: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    /*@TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN032", resDialogTitle =
            "CASE_print_CN032", APIName = "print")
    public void CASE_print_CN032() {
        Log.i(TAG, "CASE_print_CN032");
        API02print("{'':[" +

                "{" +
                "}]}", getBitmap(), new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN032: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN033", resDialogTitle =
            "CASE_print_CN033", APIName = "print")
    public void CASE_print_CN033() {
        Log.i(TAG, "CASE_print_CN033");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'othertype'," +
                "'content':'中国银行智能POS测试'," +
                "'position':'center'," +
                "'Offset':'0'," +
                "'size':'350'" +
                "}]}", getBitmap(), new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN033: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN034", resDialogTitle =
            "CASE_print_CN034", APIName = "print")
    public void CASE_print_CN034() {
        Log.i(TAG, "CASE_print_CN034");
        API02print("{'spos':[" +

                "{" +
                "'content-type':''," +
                "'content':'中国银行智能POS测试'," +
                "'position':'center'," +
                "'Offset':'0'," +
                "'size':'350'" +
                "}]}", getBitmap(), new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN034: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN035", resDialogTitle =
            "CASE_print_CN035", APIName = "print")
    public void CASE_print_CN035() {
        Log.i(TAG, "CASE_print_CN035");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'txt'," +
                "'content':''," +
                "'position':'center'," +
                "'bold':'0'," +
                "'Offset':'0'," +
                "'size':'1'" +
                "}]}", getBitmap(), new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN035: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN036", resDialogTitle =
            "CASE_print_CN036", APIName = "print")
    public void CASE_print_CN036() {
        Log.i(TAG, "CASE_print_CN036");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'jpg'," +
                "'position':'center'," +
                "'height':'-1'," +
                "'size':'1'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN036: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN037", resDialogTitle =
            "CASE_print_CN037", APIName = "print")
    public void CASE_print_CN037() {
        Log.i(TAG, "CASE_print_CN037");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'one-dimension'," +
                "'content':''," +
                "'position':'center'," +
                "'Offset':'0'," +
                "'height':'-1'," +
                "'size':'-1'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN037: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN038", resDialogTitle =
            "CASE_print_CN038", APIName = "print")
    public void CASE_print_CN038() {
        Log.i(TAG, "CASE_print_CN038");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'two-dimension'," +
                "'content':''," +
                "'position':'center'," +
                "'Offset':'0'," +
                "'size':'150'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN038: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN039", resDialogTitle =
            "CASE_print_CN039", APIName = "print")
    public void CASE_print_CN039() {
        Log.i(TAG, "CASE_print_CN039");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'txt'," +
                "'content':'中国银行智能POS测试程序V1.0'," +
                "'position':'center'," +
                "'bold':'0'," +
                "'Offset':'0'," +
                "'size':'0'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN039: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN040", resDialogTitle =
            "CASE_print_CN040", APIName = "print")
    public void CASE_print_CN040() {
        Log.i(TAG, "CASE_print_CN040");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'txt'," +
                "'content':'中国银行智能POS测试程序V1.0'," +
                "'position':'center'," +
                "'bold':'0'," +
                "'Offset':'0'," +
                "'size':'4'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN040: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN041", resDialogTitle =
            "CASE_print_CN041", APIName = "print")
    public void CASE_print_CN041() {
        Log.i(TAG, "CASE_print_CN041");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'txt'," +
                "'content':'中国银行智能POS测试程序V1.0'," +
                "'position':'center'," +
                "'bold':'0'," +
                "'Offset':'0'," +
                "'size':'-1'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN041: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN042", resDialogTitle =
            "CASE_print_CN042", APIName = "print")
    public void CASE_print_CN042() {
        Log.i(TAG, "CASE_print_CN042");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'txt'," +
                "'content':'中国银行智能项目测试程序V1.0'," +
                "'position':'center'," +
                "'bold':'0'," +
                "'Offset':'0'," +
                "'size':'384'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN042: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN043", resDialogTitle =
            "CASE_print_CN043", APIName = "print")
    public void CASE_print_CN043() {
        Log.i(TAG, "CASE_print_CN043");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'txt'," +
                "'content':'中国银行智能项目测试程序V1.0'," +
                "'position':'center'," +
                "'bold':'0'," +
                "'Offset':'0'," +
                "'size':'385'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN043: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN044", resDialogTitle =
            "CASE_print_CN044", APIName = "print")
    public void CASE_print_CN044() {
        Log.i(TAG, "CASE_print_CN044");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'two-dimension'," +
                "'content':'中国银行智能POS测试程序V1.0'," +
                "'position':'center'," +
                "'bold':'0'," +
                "'Offset':'0'," +
                "'size':'1'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN044: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN045", resDialogTitle =
            "CASE_print_CN045", APIName = "print")
    public void CASE_print_CN045() {
        Log.i(TAG, "CASE_print_CN045");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'two-dimension'," +
                "'content':'中国银行智能POS测试程序V1.0'," +
                "'position':'center'," +
                "'bold':'0'," +
                "'Offset':'0'," +
                "'size':'0'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN045: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN046", resDialogTitle =
            "CASE_print_CN046", APIName = "print")
    public void CASE_print_CN046() {
        Log.i(TAG, "CASE_print_CN046");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'two-dimension'," +
                "'content':'中国银行智能POS测试程序V1.0'," +
                "'position':'center'," +
                "'bold':'0'," +
                "'Offset':'0'," +
                "'size':'-1'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN046: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN047", resDialogTitle =
            "CASE_print_CN047", APIName = "print")
    public void CASE_print_CN047() {
        Log.i(TAG, "CASE_print_CN047");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'two-dimension'," +
                "'content':'中国银行智能POS测试程序V1.0'," +
                "'position':'center'," +
                "'bold':'0'," +
                "'Offset':'0'," +
                "'size':'384'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN047: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN048", resDialogTitle =
            "CASE_print_CN048", APIName = "print")
    public void CASE_print_CN048() {
        Log.i(TAG, "CASE_print_CN048");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'two-dimension'," +
                "'content':'中国银行智能POS测试程序V1.0'," +
                "'position':'center'," +
                "'bold':'0'," +
                "'Offset':'0'," +
                "'size':'385'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN048: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN049", resDialogTitle =
            "CASE_print_CN049", APIName = "print")
    public void CASE_print_CN049() {
        Log.i(TAG, "CASE_print_CN049");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'one-dimension'," +
                "'content':'12345678901234567890'," +
                "'position':'center'," +
                "'bold':'0'," +
                "'Offset':'0'," +
                "'size':'0'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN049: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN050", resDialogTitle =
            "CASE_print_CN050", APIName = "print")
    public void CASE_print_CN050() {
        Log.i(TAG, "CASE_print_CN050");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'one-dimension'," +
                "'content':'12345678901234567890'," +
                "'position':'center'," +
                "'bold':'0'," +
                "'Offset':'0'," +
                "'size':'9'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN050: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN051", resDialogTitle =
            "CASE_print_CN051", APIName = "print")
    public void CASE_print_CN051() {
        Log.i(TAG, "CASE_print_CN051");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'one-dimension'," +
                "'content':'12345678901234567890'," +
                "'position':'center'," +
                "'bold':'0'," +
                "'Offset':'0'," +
                "'size':'385'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN051: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN052", resDialogTitle =
            "CASE_print_CN052", APIName = "print")
    public void CASE_print_CN052() {
        Log.i(TAG, "CASE_print_CN052");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'txt'," +
                "'content':'中国银行智能POS测试程序V1.0'," +
                "'position':''," +
                "'bold':'0'," +
                "'Offset':'0'," +
                "'size':'-1'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN052: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN053", resDialogTitle =
            "CASE_print_CN053", APIName = "print")
    public void CASE_print_CN053() {
        Log.i(TAG, "CASE_print_CN053");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'txt'," +
                "'content':'中国银行智能POS测试程序V1.0'," +
                "'position':'other'," +
                "'bold':'0'," +
                "'Offset':'0'," +
                "'size':'-1'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN053: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN054", resDialogTitle =
            "CASE_print_CN054", APIName = "print")
    public void CASE_print_CN054() {
        Log.i(TAG, "CASE_print_CN054");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'txt'," +
                "'content':'中国银行智能POS测试程序V1.0'," +
                "'position':'left" +
                "'bold':'0'," +
                "'Offset':'-1'," +
                "'size':'1'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN054: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN055", resDialogTitle =
            "CASE_print_CN055", APIName = "print")
    public void CASE_print_CN055() {
        Log.i(TAG, "CASE_print_CN055");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'txt'," +
                "'content':'中国银行智能POS测试程序V1.0'," +
                "'position':'left'," +
                "'bold':'0'," +
                "'Offset':'384'," +
                "'size':'1'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN055: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN056", resDialogTitle =
            "CASE_print_CN056", APIName = "print")
    public void CASE_print_CN056() {
        Log.i(TAG, "CASE_print_CN056");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'txt'," +
                "'content':'中国银行智能POS测试程序V1.0'," +
                "'position':'left'," +
                "'bold':'0'," +
                "'Offset':'385'," +
                "'size':'1'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN056: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN057", resDialogTitle =
            "CASE_print_CN057", APIName = "print")
    public void CASE_print_CN057() {
        Log.i(TAG, "CASE_print_CN057");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'txt'," +
                "'content':'中国银行智能POS测试程序V1.0'," +
                "'position':'right'," +
                "'bold':'0'," +
                "'Offset':'40'," +
                "'size':'-1'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN057: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN058", resDialogTitle =
            "CASE_print_CN058", APIName = "print")
    public void CASE_print_CN058() {
        Log.i(TAG, "CASE_print_CN058");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'txt'," +
                "'content':'中国银行智能POS测试程序V1.0'," +
                "'position':'center'," +
                "'bold':'0'," +
                "'Offset':'40'," +
                "'size':'-1'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN058: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN059", resDialogTitle =
            "CASE_print_CN059", APIName = "print")
    public void CASE_print_CN059() {
        Log.i(TAG, "CASE_print_CN059");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'txt'," +
                "'content':'中国银行智能POS测试程序V1.0'," +
                "'position':'center'," +
                "'bold':'2'," +
                "'Offset':'0'," +
                "'size':'1'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN059: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN060", resDialogTitle =
            "CASE_print_CN060", APIName = "print")
    public void CASE_print_CN060() {
        Log.i(TAG, "CASE_print_CN060");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'txt'," +
                "'content':'中国银行智能POS测试程序V1.0'," +
                "'position':'center'," +
                "'bold':'-1'," +
                "'Offset':'0'," +
                "'size':'1'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN060: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN061", resDialogTitle =
            "CASE_print_CN061", APIName = "print")
    public void CASE_print_CN061() {
        Log.i(TAG, "CASE_print_CN061");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'one-dimension'," +
                "'content':'1234567812345678'," +
                "'position':'center'," +
                "'bold':'0'," +
                "'Offset':'0'," +
                "'height':'0'," +
                "'size':'-1'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN061: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN062", resDialogTitle =
            "CASE_print_CN062", APIName = "print")
    public void CASE_print_CN062() {
        Log.i(TAG, "CASE_print_CN062");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'one-dimension'," +
                "'content':'1234567812345678'," +
                "'position':'center'," +
                "'bold':'0'," +
                "'Offset':'0'," +
                "'height':'-1'," +
                "'size':'-1'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN062: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN063", resDialogTitle =
            "CASE_print_CN063", APIName = "print")
    public void CASE_print_CN063() {
        Log.i(TAG, "CASE_print_CN063");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'one-dimension'," +
                "'content':'1234567812345678'," +
                "'position':'center'," +
                "'bold':'0'," +
                "'Offset':'0'," +
                "'height':'328'," +
                "'size':'-1'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN063: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN064", resDialogTitle =
            "CASE_print_CN064", APIName = "print")
    public void CASE_print_CN064() {
        Log.i(TAG, "CASE_print_CN064");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'one-dimension'," +
                "'content':'1234567812345678'," +
                "'position':'center'," +
                "'bold':'0'," +
                "'Offset':'0'," +
                "'height':'7'," +
                "'size':'-1'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN064: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN065", resDialogTitle =
            "CASE_print_CN065", APIName = "print")
    public void CASE_print_CN065() {
        Log.i(TAG, "CASE_print_CN065");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'one-dimension'," +
                "'content':'1234567812345678'," +
                "'position':'center'," +
                "'bold':'0'," +
                "'Offset':'0'," +
                "'height':'中国文字'," +
                "'size':'-1'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN065: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN066", resDialogTitle =
            "CASE_print_CN066", APIName = "print")
    public void CASE_print_CN066() {
        Log.i(TAG, "CASE_print_CN066");
        API02print("", getBitmap(), new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN066: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN067", resDialogTitle =
            "CASE_print_CN067", APIName = "print")
    public void CASE_print_CN067() {
        Log.i(TAG, "CASE_print_CN067");
        API02print(null, getBitmap(), new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN067: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN068", resDialogTitle =
            "CASE_print_CN068", APIName = "print")
    public void CASE_print_CN068() {
        Log.i(TAG, "CASE_print_CN068");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'jpg'," +
                "'position':'center'," +
                "'height':'-1'," +
                "'size':'1'" +
                "}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN068: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    //图片不存在，获得的Bitmap[]应为null
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN069", resDialogTitle =
            "CASE_print_CN069", APIName = "print")
    public void CASE_print_CN069() {
        Log.i(TAG, "CASE_print_CN069");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'jpg'," +
                "'position':'center'," +
                "'height':'-1'," +
                "'size':'1'" +
                "}]}", null, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN069: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN070", resDialogTitle =
            "CASE_print_CN070", APIName = "print")
    public void CASE_print_CN070() {
        Log.i(TAG, "CASE_print_CN070");
        Bitmap localBitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.bitmap_6);
        Bitmap[] bitmaps = new Bitmap[5];
        bitmaps[0] = localBitmap;
        API02print("{'spos':[" +

                "{" +
                "'content-type':'jpg'," +
                "'position':'center'," +
                "'height':'-1'," +
                "'size':'1'" +
                "}]}", bitmaps, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN070: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN071", resDialogTitle =
            "CASE_print_CN071", APIName = "print")
    public void CASE_print_CN071() {
        Log.i(TAG, "CASE_print_CN071");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'jpg'," +
                "'position':'center'," +
                "'height':'-1'," +
                "'size':'1'" +
                "}]}", new Bitmap[]{}, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN071: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN072", resDialogTitle =
            "CASE_print_CN072", APIName = "print")
    public void CASE_print_CN072() {
        Log.i(TAG, "CASE_print_CN072");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'txt'," +
                "'content':'中国银行智能POS测试程序V1.0'," +
                "'position':'center'," +
                "'bold':'0'," +
                "'Offset':'0'," +
                "'size':'1'" +
                "}]}", null, null);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN073", resDialogTitle =
            "CASE_print_CN073", APIName = "print")
    public void CASE_print_CN073() {
        Log.i(TAG, "CASE_print_CN073");
        API02print(null, null, null);
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN074", resDialogTitle =
            "CASE_print_CN074", APIName = "print")
    public void CASE_print_CN074() {
        Log.i(TAG, "CASE_print_CN074");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'txt'," +
                "'content':'中国银行智能POS测试程序V1.0'," +
                "'position':'center'," +
                "'bold':'0'," +
                "'Offset':'0'," +
                "'size':'1'" +
                "}]}", null, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN074: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN075", resDialogTitle =
            "CASE_print_CN075", APIName = "print")
    public void CASE_print_CN075() {
        Log.i(TAG, "CASE_print_CN075");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'txt'," +
                "'content':'中国银行智能POS测试程序V1.0'," +
                "'position':'center'," +
                "'bold':'0'," +
                "'Offset':'0'," +
                "'size':'1'" +
                "}]}", null, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN075: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN076", resDialogTitle =
            "CASE_print_CN076", APIName = "print")
    public void CASE_print_CN076() {
        Log.i(TAG, "CASE_print_CN076");
        API02print("{'spos':[" +

                "{" +
                "'content-type':''," +
                "'content':'中国银行智能POS测试程序V1.0'," +
                "'position':'center'," +
                "'bold':'0'," +
                "'Offset':'0'," +
                "'size':'1'" +
                "}]}", null, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN076: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN077", resDialogTitle =
            "CASE_print_CN077", APIName = "print")
    public void CASE_print_CN077() {
        Log.i(TAG, "CASE_print_CN077");
        new Thread(new Runnable() {
            @Override
            public void run() {
                API02print("{'spos':[" +

                        "{" +
                        "'content-type':'txt'," +
                        "'content':'中国银行智能POS测试程序V1.0'," +
                        "'position':'center'," +
                        "'bold':'0'," +
                        "'Offset':'0'," +
                        "'size':'1'" +
                        "}]}", null, new AidlPrinterListener.Stub() {
                    @Override
                    public void onError(int code, String detail) throws RemoteException {
                        Log.i(TAG, "CASE_print_CN077_1: "+"code=" + code + " detail=" + detail);
                    }

                    @Override
                    public void onFinish() throws RemoteException {
                        Log.i(TAG, "CASE_print_CN077_1_onFinish: ");
                    }
                });
            }
        }).start();

        API02print("{'spos':[" +

                "{" +
                "'content-type':'txt'," +
                "'content':'中国银行智能POS测试程序V1.0'," +
                "'position':'center'," +
                "'bold':'0'," +
                "'Offset':'0'," +
                "'size':'1'" +
                "}]}", null, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN077_2: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {
                Log.i(TAG, "CASE_print_CN077_2_onFinish: ");

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN078", resDialogTitle =
            "CASE_print_CN078", APIName = "print")
    public void CASE_print_CN078() {
        Log.i(TAG, "CASE_print_CN078");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'txt'," +
                "'content':'中国银行智能POS测试程序V1.0'," +
                "'position':'center'," +
                "'bold':'0'," +
                "'Offset':'0'," +
                "'size':'1'" +
                "}]}", null, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN078: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN079", resDialogTitle =
            "CASE_print_CN079", APIName = "print")
    public void CASE_print_CN079() {
        Log.i(TAG, "CASE_print_CN079");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'txt'," +
                "'content':'中国银行智能POS测试程序V1.0'," +
                "'position':'center'," +
                "'bold':'0'," +
                "'Offset':'0'," +
                "'size':'1'" +
                "}]}", null, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN079: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_print_CN080", resDialogTitle =
            "CASE_print_CN080", APIName = "print")
    public void CASE_print_CN080() {
        Log.i(TAG, "CASE_print_CN080");
        API02print("{'spos':[" +

                "{" +
                "'content-type':'txt'," +
                "'content':'中国银行智能POS测试程序V1.0'," +
                "'position':'center'," +
                "'bold':'0'," +
                "'Offset':'0'," +
                "'size':'1'" +
                "}]}", null, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_print_CN080: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }*/
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN001", resDialogTitle =
            "CASE_printBarCode_CN001", APIName = "printBarCode")
    public void CASE_printBarCode_CN001() {
        Log.i(TAG, "CASE_printBarCode_CN001");
        API01printBarCode("123456781234567812345678",2, 64, 2, 30, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN001: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN002", resDialogTitle =
            "CASE_printBarCode_CN002", APIName = "printBarCode")
    public void CASE_printBarCode_CN002() {
        Log.i(TAG, "CASE_printBarCode_CN002");
        API01printBarCode("ABCDEF",2, 8, 2, 60, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN002: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN003", resDialogTitle =
            "CASE_printBarCode_CN003", APIName = "printBarCode")
    public void CASE_printBarCode_CN003() {
        Log.i(TAG, "CASE_printBarCode_CN003");
        API01printBarCode("abcdef",2, 320, 2, 90, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN003: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN004", resDialogTitle =
            "CASE_printBarCode_CN004", APIName = "printBarCode")
    public void CASE_printBarCode_CN004() {
        Log.i(TAG, "CASE_printBarCode_CN004");
        API01printBarCode("ABCabc123",2, 64, 2, 60, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN004: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN005", resDialogTitle =
            "CASE_printBarCode_CN005", APIName = "printBarCode")
    public void CASE_printBarCode_CN005() {
        Log.i(TAG, "CASE_printBarCode_CN005");
        API01printBarCode("12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678",2, 64, 2, 60, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN005: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN006", resDialogTitle =
            "CASE_printBarCode_CN006", APIName = "printBarCode")
    public void CASE_printBarCode_CN006() {
        Log.i(TAG, "CASE_printBarCode_CN006");
        API01printBarCode("○☺√★→",2, 64, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN006: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN007", resDialogTitle =
            "CASE_printBarCode_CN007", APIName = "printBarCode")
    public void CASE_printBarCode_CN007() {
        Log.i(TAG, "CASE_printBarCode_CN007");
        API01printBarCode("1",2, 5, 2, 60, new AidlPrinterListener.Stub() {

            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN007: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN008", resDialogTitle =
            "CASE_printBarCode_CN008", APIName = "printBarCode")
    public void CASE_printBarCode_CN008() {
        Log.i(TAG, "CASE_printBarCode_CN008");
        API01printBarCode("www.baidu.com",1, 1, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN008: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN009", resDialogTitle =
            "CASE_printBarCode_CN009", APIName = "printBarCode")
    public void CASE_printBarCode_CN009() {
        Log.i(TAG, "CASE_printBarCode_CN009");
        API01printBarCode("12345678901234567890",2, 1, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN009: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN010", resDialogTitle =
            "CASE_printBarCode_CN010", APIName = "printBarCode")
    public void CASE_printBarCode_CN010() {
        Log.i(TAG, "CASE_printBarCode_CN010");
        API01printBarCode("12345678901234567890",1, 1, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN010: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN011", resDialogTitle =
            "CASE_printBarCode_CN011", APIName = "printBarCode")
    public void CASE_printBarCode_CN011() {
        Log.i(TAG, "CASE_printBarCode_CN011");
        API01printBarCode("12345678901234567890",8, 1, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN010: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN012", resDialogTitle =
            "CASE_printBarCode_CN012", APIName = "printBarCode")
    public void CASE_printBarCode_CN012() {
        Log.i(TAG, "CASE_printBarCode_CN012");
        API01printBarCode("12345678901234567890",2, 64, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN012: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN013", resDialogTitle =
            "CASE_printBarCode_CN013", APIName = "printBarCode")
    public void CASE_printBarCode_CN013() {
        Log.i(TAG, "CASE_printBarCode_CN013");
        API01printBarCode("12345678901234567890",2, 8, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN013: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN014", resDialogTitle =
            "CASE_printBarCode_CN014", APIName = "printBarCode")
    public void CASE_printBarCode_CN014() {
        Log.i(TAG, "CASE_printBarCode_CN014");
        API01printBarCode("12345678901234567890",2, 320, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN014: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN015", resDialogTitle =
            "CASE_printBarCode_CN015", APIName = "printBarCode")
    public void CASE_printBarCode_CN015() {
        Log.i(TAG, "CASE_printBarCode_CN015");
        API01printBarCode("12345678901234567890",2, 64, 1, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN015: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN016", resDialogTitle =
            "CASE_printBarCode_CN016", APIName = "printBarCode")
    public void CASE_printBarCode_CN016() {
        Log.i(TAG, "CASE_printBarCode_CN016");
        API01printBarCode("12345678901234567890",2, 64, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN016: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN017", resDialogTitle =
            "CASE_printBarCode_CN017", APIName = "printBarCode")
    public void CASE_printBarCode_CN017() {
        Log.i(TAG, "CASE_printBarCode_CN017");
        API01printBarCode("12345678901234567890",2, 64, 3, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN017: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN018", resDialogTitle =
            "CASE_printBarCode_CN018", APIName = "printBarCode")
    public void CASE_printBarCode_CN018() {
        Log.i(TAG, "CASE_printBarCode_CN018");
        API01printBarCode("12345678901234567890",2, 64, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN018: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    /*@TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN019", resDialogTitle =
            "CASE_printBarCode_CN019", APIName = "printBarCode")
    public void CASE_printBarCode_CN019() {
        Log.i(TAG, "CASE_printBarCode_CN019");
        API01printBarCode("？？？！！！",8, 64, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN019: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN020", resDialogTitle =
            "CASE_printBarCode_CN020", APIName = "printBarCode")
    public void CASE_printBarCode_CN020() {
        Log.i(TAG, "CASE_printBarCode_CN020");
        API01printBarCode("中国汉字",8, 64, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN020: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN021", resDialogTitle =
            "CASE_printBarCode_CN021", APIName = "printBarCode")
    public void CASE_printBarCode_CN021() {
        Log.i(TAG, "CASE_printBarCode_CN021");
        API01printBarCode("1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890",8, 64, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN021: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN022", resDialogTitle =
            "CASE_printBarCode_CN022", APIName = "printBarCode")
    public void CASE_printBarCode_CN022() {
        Log.i(TAG, "CASE_printBarCode_CN022");
        API01printBarCode("",8, 64, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN022: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN023", resDialogTitle =
            "CASE_printBarCode_CN023", APIName = "printBarCode")
    public void CASE_printBarCode_CN023() {
        Log.i(TAG, "CASE_printBarCode_CN023");
        API01printBarCode(null,8, 64, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN023: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN024", resDialogTitle =
            "CASE_printBarCode_CN024", APIName = "printBarCode")
    public void CASE_printBarCode_CN024() {
        Log.i(TAG, "CASE_printBarCode_CN024");
        API01printBarCode("12345678901234567890",0, 64, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN024: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN025", resDialogTitle =
            "CASE_printBarCode_CN025", APIName = "printBarCode")
    public void CASE_printBarCode_CN025() {
        Log.i(TAG, "CASE_printBarCode_CN025");
        API01printBarCode("12345678901234567890",9, 64, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN025: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN026", resDialogTitle =
            "CASE_printBarCode_CN026", APIName = "printBarCode")
    public void CASE_printBarCode_CN026() {
        Log.i(TAG, "CASE_printBarCode_CN026");
        API01printBarCode("12345678901234567890",-1, 64, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN026: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN027", resDialogTitle =
            "CASE_printBarCode_CN027", APIName = "printBarCode")
    public void CASE_printBarCode_CN027() {
        Log.i(TAG, "CASE_printBarCode_CN027");
        API01printBarCode("12345678901234567890",Integer.MAX_VALUE, 64, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN027: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN028", resDialogTitle =
            "CASE_printBarCode_CN028", APIName = "printBarCode")
    public void CASE_printBarCode_CN028() {
        Log.i(TAG, "CASE_printBarCode_CN028");
        API01printBarCode("12345678901234567890",Integer.MIN_VALUE, 64, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN028: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN029", resDialogTitle =
            "CASE_printBarCode_CN029", APIName = "printBarCode")
    public void CASE_printBarCode_CN029() {
        Log.i(TAG, "CASE_printBarCode_CN029");
        API01printBarCode("12345678901234567890",2, 0, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN029: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN030", resDialogTitle =
            "CASE_printBarCode_CN030", APIName = "printBarCode")
    public void CASE_printBarCode_CN030() {
        Log.i(TAG, "CASE_printBarCode_CN030");
        API01printBarCode("12345678901234567890",2, 321, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN030: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN031", resDialogTitle =
            "CASE_printBarCode_CN031", APIName = "printBarCode")
    public void CASE_printBarCode_CN031() {
        Log.i(TAG, "CASE_printBarCode_CN031");
        API01printBarCode("12345678901234567890",2, -1, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN031: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN032", resDialogTitle =
            "CASE_printBarCode_CN032", APIName = "printBarCode")
    public void CASE_printBarCode_CN032() {
        Log.i(TAG, "CASE_printBarCode_CN032");
        API01printBarCode("12345678901234567890",2, 63, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN032: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN033", resDialogTitle =
            "CASE_printBarCode_CN033", APIName = "printBarCode")
    public void CASE_printBarCode_CN033() {
        Log.i(TAG, "CASE_printBarCode_CN033");
        API01printBarCode("12345678901234567890",2, 64, 0, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN033: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN034", resDialogTitle =
            "CASE_printBarCode_CN034", APIName = "printBarCode")
    public void CASE_printBarCode_CN034() {
        Log.i(TAG, "CASE_printBarCode_CN034");
        API01printBarCode("12345678901234567890",2, 64, 4, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN034: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN035", resDialogTitle =
            "CASE_printBarCode_CN035", APIName = "printBarCode")
    public void CASE_printBarCode_CN035() {
        Log.i(TAG, "CASE_printBarCode_CN035");
        API01printBarCode("12345678901234567890",2, 64, -1, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN035: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN036", resDialogTitle =
            "CASE_printBarCode_CN036", APIName = "printBarCode")
    public void CASE_printBarCode_CN036() {
        Log.i(TAG, "CASE_printBarCode_CN036");
        API01printBarCode("12345678901234567890",2, 64, 2, 0, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN036: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN037", resDialogTitle =
            "CASE_printBarCode_CN037", APIName = "printBarCode")
    public void CASE_printBarCode_CN037() {
        Log.i(TAG, "CASE_printBarCode_CN037");
        API01printBarCode("12345678901234567890",2, 64, 2, -1, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN037: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN038", resDialogTitle =
            "CASE_printBarCode_CN038", APIName = "printBarCode")
    public void CASE_printBarCode_CN038() {
        Log.i(TAG, "CASE_printBarCode_CN038");
        API01printBarCode("12345678901234567890",2, 64, 2, 1, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBarCode_CN038: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 1, itemDetailName = "CASE_printBarCode_CN039", resDialogTitle =
            "CASE_printBarCode_CN039", APIName = "printBarCode")
    public void CASE_printBarCode_CN039() {
        Log.i(TAG, "CASE_printBarCode_CN039");
        API01printBarCode("12345678901234567890",2, 64, 2, 60, null);
    }*/
    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_printQrCode_CN001", resDialogTitle =
            "CASE_printQrCode_CN001", APIName = "printQrCode")
    public void CASE_printQrCode_CN001() {
        Log.i(TAG, "CASE_printQrCode_CN001");
        API04printQrCode("1", 100, 0, 2, 30, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printQrCode_CN001: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_printQrCode_CN002", resDialogTitle =
            "CASE_printQrCode_CN002", APIName = "printQrCode")
    public void CASE_printQrCode_CN002() {
        Log.i(TAG, "CASE_printQrCode_CN002");
        API04printQrCode("123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890", 200, 0, 2, 30, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printQrCode_CN002: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_printQrCode_CN003", resDialogTitle =
            "CASE_printQrCode_CN003", APIName = "printQrCode")
    public void CASE_printQrCode_CN003() {
        Log.i(TAG, "CASE_printQrCode_CN003");
        API04printQrCode("`!-=+{}[];:'\",.<>?/\\|~`@#$%^&*()", 100, 3, 2, 120, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printQrCode_CN003: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_printQrCode_CN004", resDialogTitle =
            "CASE_printQrCode_CN004", APIName = "printQrCode")
    public void CASE_printQrCode_CN004() {
        Log.i(TAG, "CASE_printQrCode_CN004");
        API04printQrCode("囧槑（mei）烎(yin)氼（ni）嘂（jiao）嘦（jiao）嫑（biao）圐（ku）圙（lue）玊（su）孖（ma）丼（jing）嬛(huan)甯(ning)寗(ning)", 200, 2, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printQrCode_CN004: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_printQrCode_CN005", resDialogTitle =
            "CASE_printQrCode_CN005", APIName = "printQrCode")
    public void CASE_printQrCode_CN005() {
        Log.i(TAG, "CASE_printQrCode_CN005");
        API04printQrCode("二维码打印测试", 100, 2, 2, 30, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printQrCode_CN005: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_printQrCode_CN006", resDialogTitle =
            "CASE_printQrCode_CN006", APIName = "printQrCode")
    public void CASE_printQrCode_CN006() {
        Log.i(TAG, "CASE_printQrCode_CN006");
        API04printQrCode("二维码打印测试", 1, 2, 2, 30, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printQrCode_CN006: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_printQrCode_CN007", resDialogTitle =
            "CASE_printQrCode_CN007", APIName = "printQrCode")
    public void CASE_printQrCode_CN007() {
        Log.i(TAG, "CASE_printQrCode_CN007");
        API04printQrCode("二维码打印测试", 384, 2, 2, 30, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printQrCode_CN007: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_printQrCode_CN008", resDialogTitle =
            "CASE_printQrCode_CN008", APIName = "printQrCode")
    public void CASE_printQrCode_CN008() {
        Log.i(TAG, "CASE_printQrCode_CN008");
        API04printQrCode("二维码打印测试", 100, 2, 2, 30, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printQrCode_CN008: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_printQrCode_CN009", resDialogTitle =
            "CASE_printQrCode_CN009", APIName = "printQrCode")
    public void CASE_printQrCode_CN009() {
        Log.i(TAG, "CASE_printQrCode_CN009");
        API04printQrCode("二维码打印测试", 100, 0, 2, 30, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printQrCode_CN009: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_printQrCode_CN010", resDialogTitle =
            "CASE_printQrCode_CN010", APIName = "printQrCode")
    public void CASE_printQrCode_CN010() {
        Log.i(TAG, "CASE_printQrCode_CN010");
        API04printQrCode("二维码打印测试", 100, 3, 2, 30, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printQrCode_CN010: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_printQrCode_CN011", resDialogTitle =
            "CASE_printQrCode_CN011", APIName = "printQrCode")
    public void CASE_printQrCode_CN011() {
        Log.i(TAG, "CASE_printQrCode_CN011");
        API04printQrCode("二维码打印测试", 100, 2, 1, 30, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printQrCode_CN011: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_printQrCode_CN012", resDialogTitle =
            "CASE_printQrCode_CN012", APIName = "printQrCode")
    public void CASE_printQrCode_CN012() {
        Log.i(TAG, "CASE_printQrCode_CN012");
        API04printQrCode("二维码打印测试", 100, 2, 2, 30, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printQrCode_CN012: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_printQrCode_CN013", resDialogTitle =
            "CASE_printQrCode_CN013", APIName = "printQrCode")
    public void CASE_printQrCode_CN013() {
        Log.i(TAG, "CASE_printQrCode_CN013");
        API04printQrCode("二维码打印测试", 100, 2, 3, 30, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printQrCode_CN013: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_printQrCode_CN014", resDialogTitle =
            "CASE_printQrCode_CN014", APIName = "printQrCode")
    public void CASE_printQrCode_CN014() {
        Log.i(TAG, "CASE_printQrCode_CN014");
        API04printQrCode("二维码打印测试", 100, 2, 2, 1, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printQrCode_CN014: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_printQrCode_CN015", resDialogTitle =
            "CASE_printQrCode_CN015", APIName = "printQrCode")
    public void CASE_printQrCode_CN015() {
        Log.i(TAG, "CASE_printQrCode_CN015");
        API04printQrCode("二维码打印测试", 100, 2, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printQrCode_CN015: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }
    /*@TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_printQrCode_CN016", resDialogTitle =
            "CASE_printQrCode_CN016", APIName = "printQrCode")
    public void CASE_printQrCode_CN016() {
        Log.i(TAG, "CASE_printQrCode_CN016");
        API04printQrCode("", 100, 2, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printQrCode_CN016: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_printQrCode_CN017", resDialogTitle =
            "CASE_printQrCode_CN017", APIName = "printQrCode")
    public void CASE_printQrCode_CN017() {
        Log.i(TAG, "CASE_printQrCode_CN017");
        API04printQrCode("1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890", 100, 2, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printQrCode_CN017: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_printQrCode_CN018", resDialogTitle =
            "CASE_printQrCode_CN018", APIName = "printQrCode")
    public void CASE_printQrCode_CN018() {
        Log.i(TAG, "CASE_printQrCode_CN018");
        API04printQrCode(null, 100, 2, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printQrCode_CN018: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_printQrCode_CN019", resDialogTitle =
            "CASE_printQrCode_CN019", APIName = "printQrCode")
    public void CASE_printQrCode_CN019() {
        Log.i(TAG, "CASE_printQrCode_CN019");
        API04printQrCode("二维码打印测试", 0, 2, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printQrCode_CN019: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_printQrCode_CN020", resDialogTitle =
            "CASE_printQrCode_CN020", APIName = "printQrCode")
    public void CASE_printQrCode_CN020() {
        Log.i(TAG, "CASE_printQrCode_CN020");
        API04printQrCode("二维码打印测试", 385, 2, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printQrCode_CN020: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_printQrCode_CN021", resDialogTitle =
            "CASE_printQrCode_CN021", APIName = "printQrCode")
    public void CASE_printQrCode_CN021() {
        Log.i(TAG, "CASE_printQrCode_CN021");
        API04printQrCode("二维码打印测试", -1, 2, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printQrCode_CN021: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_printQrCode_CN022", resDialogTitle =
            "CASE_printQrCode_CN022", APIName = "printQrCode")
    public void CASE_printQrCode_CN022() {
        Log.i(TAG, "CASE_printQrCode_CN022");
        API04printQrCode("二维码打印测试", 100, -1, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printQrCode_CN022: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_printQrCode_CN023", resDialogTitle =
            "CASE_printQrCode_CN023", APIName = "printQrCode")
    public void CASE_printQrCode_CN023() {
        Log.i(TAG, "CASE_printQrCode_CN023");
        API04printQrCode("二维码打印测试", 100, 4, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printQrCode_CN023: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_printQrCode_CN024", resDialogTitle =
            "CASE_printQrCode_CN024", APIName = "printQrCode")
    public void CASE_printQrCode_CN024() {
        Log.i(TAG, "CASE_printQrCode_CN024");
        API04printQrCode("二维码打印测试", 100, 2, 0, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printQrCode_CN024: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_printQrCode_CN025", resDialogTitle =
            "CASE_printQrCode_CN025", APIName = "printQrCode")
    public void CASE_printQrCode_CN025() {
        Log.i(TAG, "CASE_printQrCode_CN025");
        API04printQrCode("二维码打印测试", 100, 2, 4, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printQrCode_CN025: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_printQrCode_CN026", resDialogTitle =
            "CASE_printQrCode_CN026", APIName = "printQrCode")
    public void CASE_printQrCode_CN026() {
        Log.i(TAG, "CASE_printQrCode_CN026");
        API04printQrCode("二维码打印测试", 100, 2, -1, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printQrCode_CN026: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_printQrCode_CN027", resDialogTitle =
            "CASE_printQrCode_CN027", APIName = "printQrCode")
    public void CASE_printQrCode_CN027() {
        Log.i(TAG, "CASE_printQrCode_CN027");
        API04printQrCode("二维码打印测试", 100, 2, 2, 0, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printQrCode_CN027: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_printQrCode_CN028", resDialogTitle =
            "CASE_printQrCode_CN028", APIName = "printQrCode")
    public void CASE_printQrCode_CN028() {
        Log.i(TAG, "CASE_printQrCode_CN028");
        API04printQrCode("二维码打印测试", 100, 2, 2, -1, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printQrCode_CN028: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_printQrCode_CN029", resDialogTitle =
            "CASE_printQrCode_CN029", APIName = "printQrCode")
    public void CASE_printQrCode_CN029() {
        Log.i(TAG, "CASE_printQrCode_CN029");
        API04printQrCode("二维码打印测试", 100, 2, 2, 60, null);
    }
    @TestDetailCaseAnnotation(id = 2, itemDetailName = "CASE_printQrCode_CN030", resDialogTitle =
            "CASE_printQrCode_CN030", APIName = "printQrCode")
    public void CASE_printQrCode_CN030() {
        Log.i(TAG, "CASE_printQrCode_CN030");
        API01startScan_1(0, 60000, getNewScannerListener());
        API04printQrCode(scan_barcode, 100, 2, 2, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printQrCode_CN030: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }*/
    @TestDetailCaseAnnotation(id = 3, itemDetailName = "CASE_getStatus_CN001", resDialogTitle =
            "", APIName = "getStatus")
    public void CASE_getStatus_CN001() {
        Log.i(TAG, "CASE_getStatus_CN001");
        API05getStatus();
    }
    @TestDetailCaseAnnotation(id = 3, itemDetailName = "CASE_getStatus_CN002", resDialogTitle =
            "", APIName = "getStatus")
    public void CASE_getStatus_CN002() {
        Log.i(TAG, "CASE_getStatus_CN002");
        API02print("{'spos':[{'content':'txt','content':'商户编号','position':'left','bold':'0','height':'-1','size':'2'}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_getStatus_CN002: "+"code=" + code + " detail=" + detail);
                if(code == 1)
                {
                    ToastUtil.showLong("打印数据解析错误");
                }else{
                    ToastUtil.showLong("code:" + code + "detail:" + detail);
                }

            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 3, itemDetailName = "CASE_getStatus_CN003", resDialogTitle =
            "", APIName = "getStatus")
    public boolean CASE_getStatus_CN003() {
        Log.i(TAG, "CASE_getStatus_CN003");
        new Thread(new Runnable() {
            @Override
            public void run() {
                //CASE_print_CN001();
                API02print("{'spos':[{'content-type':'txt','content':'\n\n','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'小票测试','position':'center','bold':'0','height':'-1','size':'3'},{'content-type':'jpg','content':'','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'持卡人存根','position':'right','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'商户：测试测试测试测试','position':'left','bold':'0','height':'-1','size':'3'},{'content-type':'txt','content':'    商户编号：812002110030001','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    终端编号：20150909','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    操作员号：001','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'交易：扫一扫支付','position':'left','bold':'0','height':'-1','size':'3'},{'content-type':'txt','content':'    支付渠道：微信支付','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    日期时间：2016\\/04\\/28  15:27:51','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    参考号：000014103147','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'one-dimension','content':'12312312312424','position':'center','bold':'0','height':'1','size':'3'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'金额：RMB  0.01','position':'left','bold':'1','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'备注：','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'本人确认以上交易，同意将其计入本卡账户 ','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'I ACKNOWLEDGE SATISFACTORY RECEIPT OF RELATIVE GOODS\\/SERVICE','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'two-dimension','content':'头，辛苦啦','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'\n\n\n\n','position':'center','bold':'0','height':'-1','size':'2'}]}", null, new AidlPrinterListener.Stub() {


                    @Override
                    public void onError(int code, String detail) throws RemoteException {
                        Log.i(TAG, "CASE_getStatus_CN003: "+"code=" + code + " detail=" + detail);
                    }

                    @Override
                    public void onFinish() throws RemoteException {

                    }
                });
            }
        }).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int status = API05getStatus();
        return (API05getStatus() == 4);
    }
    @TestDetailCaseAnnotation(id = 3, itemDetailName = "CASE_getStatus_CN004", resDialogTitle =
            "打印机过热", APIName = "getStatus")
    public boolean CASE_getStatus_CN004() {
        Log.i(TAG, "CASE_getStatus_CN004");
        return (API05getStatus() == 3);
    }
    @TestDetailCaseAnnotation(id = 3, itemDetailName = "CASE_getStatus_CN005", resDialogTitle =
            "未放置打印纸", APIName = "getStatus")
    public boolean CASE_getStatus_CN005() {
        Log.i(TAG, "CASE_getStatus_CN005");
        int status = API05getStatus();
        return (API05getStatus() == 2);
    }
    @TestDetailCaseAnnotation(id = 3, itemDetailName = "CASE_getStatus_CN006", resDialogTitle =
            "打印机卡纸", APIName = "getStatus")
    public void CASE_getStatus_CN006() {
        Log.i(TAG, "CASE_getStatus_CN006");
        API02print("{'spos':[{'content-type':'txt','content':'\n\n','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'小票测试','position':'center','bold':'0','height':'-1','size':'3'},{'content-type':'jpg','content':'','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'持卡人存根','position':'right','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'商户：测试测试测试测试','position':'left','bold':'0','height':'-1','size':'3'},{'content-type':'txt','content':'    商户编号：812002110030001','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    终端编号：20150909','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    操作员号：001','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'交易：扫一扫支付','position':'left','bold':'0','height':'-1','size':'3'},{'content-type':'txt','content':'    支付渠道：微信支付','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    日期时间：2016\\/04\\/28  15:27:51','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    参考号：000014103147','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'one-dimension','content':'12312312312424','position':'center','bold':'0','height':'1','size':'3'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'金额：RMB  0.01','position':'left','bold':'1','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'备注：','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'本人确认以上交易，同意将其计入本卡账户 ','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'I ACKNOWLEDGE SATISFACTORY RECEIPT OF RELATIVE GOODS\\/SERVICE','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'two-dimension','content':'头，辛苦啦','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'\n\n\n\n','position':'center','bold':'0','height':'-1','size':'2'}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_getStatus_CN006: "+"code=" + code + " detail=" + detail);
                ToastUtil.showLong("PrintStatus:" + API05getStatus() + "\n" + detail);;
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });

    }
    @TestDetailCaseAnnotation(id = 3, itemDetailName = "CASE_getStatus_CN007", resDialogTitle =
            "无法模拟错误场景", APIName = "getStatus")
    public boolean CASE_getStatus_CN007() {
        Log.i(TAG, "CASE_getStatus_CN007");
        return (API05getStatus() == 0xFF);
    }
    @TestDetailCaseAnnotation(id = 3, itemDetailName = "CASE_getStatus_CN008", resDialogTitle =
            "CASE_getStatus_CN008", APIName = "getStatus")
    public void CASE_getStatus_CN008() {
        Log.i(TAG, "CASE_getStatus_CN008");
        API02print("{'spos':[{'content-type':'txt','content':'\n\n','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'小票测试','position':'center','bold':'0','height':'-1','size':'3'},{'content-type':'jpg','content':'','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'持卡人存根','position':'right','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'商户：测试测试测试测试','position':'left','bold':'0','height':'-1','size':'3'},{'content-type':'txt','content':'    商户编号：812002110030001','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    终端编号：20150909','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    操作员号：001','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'交易：扫一扫支付','position':'left','bold':'0','height':'-1','size':'3'},{'content-type':'txt','content':'    支付渠道：微信支付','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    日期时间：2016\\/04\\/28  15:27:51','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    参考号：000014103147','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'one-dimension','content':'12312312312424','position':'center','bold':'0','height':'1','size':'3'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'金额：RMB  0.01','position':'left','bold':'1','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'备注：','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'本人确认以上交易，同意将其计入本卡账户 ','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'I ACKNOWLEDGE SATISFACTORY RECEIPT OF RELATIVE GOODS\\/SERVICE','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'two-dimension','content':'头，辛苦啦','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'\n\n\n\n','position':'center','bold':'0','height':'-1','size':'2'}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_getStatus_CN008: "+"code=" + code + " detail=" + detail);
                ToastUtil.showLong("code:" + code + "detail:" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        API05getStatus();
    }
    @TestDetailCaseAnnotation(id = 3, itemDetailName = "CASE_getStatus_CN009", resDialogTitle =
            "CASE_getStatus_CN009", APIName = "getStatus")
    public void CASE_getStatus_CN009() {
        Log.i(TAG, "CASE_getStatus_CN009");
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getContext().getResources(), R.drawable.boclogo);
        Bitmap[] bitmaps = new Bitmap[5];
        bitmaps[0] = bitmap;

        API06printBitMap(0, bitmap, 3, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_getStatus_CN009: "+"code=" + code + " detail=" + detail);
                ToastUtil.showLong("code:" + code + "detail:" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        API05getStatus();

    }
    @TestDetailCaseAnnotation(id = 3, itemDetailName = "CASE_getStatus_CN010", resDialogTitle =
            "CASE_getStatus_CN010", APIName = "getStatus")
    public void CASE_getStatus_CN010() {
        Log.i(TAG, "CASE_getStatus_CN010");
        API01printBarCode("123456781234567812345678",2, 64, 2, 30, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_getStatus_CN010: "+"code=" + code + " detail=" + detail);
                ToastUtil.showLong("code:" + code + "detail:" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        API05getStatus();
    }
    @TestDetailCaseAnnotation(id = 3, itemDetailName = "CASE_getStatus_CN011", resDialogTitle =
            "CASE_getStatus_CN011", APIName = "getStatus")
    public boolean CASE_getStatus_CN011() {
        Log.i(TAG, "CASE_getStatus_CN011");
        API04printQrCode("1", 100, 0, 2, 30, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_getStatus_CN011: "+"code=" + code + " detail=" + detail);
                ToastUtil.showLong("code:" + code + "detail:" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return (API05getStatus() == 0);
    }
    @TestDetailCaseAnnotation(id = 3, itemDetailName = "CASE_getStatus_CN012", resDialogTitle =
            "CASE_getStatus_CN012", APIName = "getStatus")
    public boolean CASE_getStatus_CN012() {
        Log.i(TAG, "CASE_getStatus_CN012");
        API03paperSkip(20);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return (API05getStatus() == 0);
    }
    @TestDetailCaseAnnotation(id = 3, itemDetailName = "CASE_getStatus_CN013", resDialogTitle =
            "", APIName = "getStatus")
    public void CASE_getStatus_CN013() {
        Log.i(TAG, "CASE_getStatus_CN013");
        API02print("{'spos':[{'content-type':'txt','content':'\n\n','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'小票测试','position':'center','bold':'0','height':'-1','size':'3'},{'content-type':'jpg','content':'','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'持卡人存根','position':'right','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'商户：测试测试测试测试','position':'left','bold':'0','height':'-1','size':'3'},{'content-type':'txt','content':'    商户编号：812002110030001','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    终端编号：20150909','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    操作员号：001','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'交易：扫一扫支付','position':'left','bold':'0','height':'-1','size':'3'},{'content-type':'txt','content':'    支付渠道：微信支付','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    日期时间：2016\\/04\\/28  15:27:51','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    参考号：000014103147','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'one-dimension','content':'12312312312424','position':'center','bold':'0','height':'1','size':'3'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'金额：RMB  0.01','position':'left','bold':'1','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'备注：','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'本人确认以上交易，同意将其计入本卡账户 ','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'I ACKNOWLEDGE SATISFACTORY RECEIPT OF RELATIVE GOODS\\/SERVICE','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'two-dimension','content':'头，辛苦啦','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'\n\n\n\n','position':'center','bold':'0','height':'-1','size':'2'}]}",null, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_getStatus_CN013: "+"code=" + code + " detail=" + detail);
                ToastUtil.showLong("code:" + code + "detail:" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        API05getStatus();
    }
    @TestDetailCaseAnnotation(id = 4, itemDetailName = "CASE_paperSkip_CN001", resDialogTitle =
            "CASE_paperSkip_CN001", APIName = "paperSkip")
    public void CASE_paperSkip_CN001() {
        Log.i(TAG, "CASE_paperSkip_CN001");
        API03paperSkip(1);
    }
    @TestDetailCaseAnnotation(id = 4, itemDetailName = "CASE_paperSkip_CN002", resDialogTitle =
            "CASE_paperSkip_CN002", APIName = "paperSkip")
    public void CASE_paperSkip_CN002() {
        Log.i(TAG, "CASE_paperSkip_CN002");
        API03paperSkip(50);
    }
    /*@TestDetailCaseAnnotation(id = 4, itemDetailName = "CASE_paperSkip_CN003", resDialogTitle =
            "CASE_paperSkip_CN003", APIName = "paperSkip")
    public void CASE_paperSkip_CN003() {
        Log.i(TAG, "CASE_paperSkip_CN003");
        API03paperSkip(51);
    }
    @TestDetailCaseAnnotation(id = 4, itemDetailName = "CASE_paperSkip_CN004", resDialogTitle =
            "CASE_paperSkip_CN004", APIName = "paperSkip")
    public void CASE_paperSkip_CN004() {
        Log.i(TAG, "CASE_paperSkip_CN004");
        API03paperSkip(0);
    }
    @TestDetailCaseAnnotation(id = 4, itemDetailName = "CASE_paperSkip_CN005", resDialogTitle =
            "CASE_paperSkip_CN005", APIName = "paperSkip")
    public void CASE_paperSkip_CN005() {
        Log.i(TAG, "CASE_paperSkip_CN005");
        API03paperSkip(-1);
    }
    @TestDetailCaseAnnotation(id = 4, itemDetailName = "CASE_paperSkip_CN006", resDialogTitle =
            "CASE_paperSkip_CN006", APIName = "paperSkip")
    public void CASE_paperSkip_CN006() {
        Log.i(TAG, "CASE_paperSkip_CN006");
        API03paperSkip(Integer.MAX_VALUE);
    }
    @TestDetailCaseAnnotation(id = 4, itemDetailName = "CASE_paperSkip_CN007", resDialogTitle =
            "CASE_paperSkip_CN007", APIName = "paperSkip")
    public void CASE_paperSkip_CN007() {
        Log.i(TAG, "CASE_paperSkip_CN007");
        API03paperSkip(Integer.MAX_VALUE+1);
    }
    @TestDetailCaseAnnotation(id = 4, itemDetailName = "CASE_paperSkip_CN008", resDialogTitle =
            "CASE_paperSkip_CN008", APIName = "paperSkip")
    public void CASE_paperSkip_CN008() {
        Log.i(TAG, "CASE_paperSkip_CN008");
        API03paperSkip(Integer.MIN_VALUE);
    }
    @TestDetailCaseAnnotation(id = 4, itemDetailName = "CASE_paperSkip_CN009", resDialogTitle =
            "CASE_paperSkip_CN009", APIName = "paperSkip")
    public void CASE_paperSkip_CN009() {
        Log.i(TAG, "CASE_paperSkip_CN009");
        API03paperSkip(Integer.MIN_VALUE-1);
    }
    @TestDetailCaseAnnotation(id = 4, itemDetailName = "CASE_paperSkip_CN010", resDialogTitle =
            "CASE_paperSkip_CN010", APIName = "paperSkip")
    public void CASE_paperSkip_CN010() {
        Log.i(TAG, "CASE_paperSkip_CN010");
        CASE_print_CN001();
        API03paperSkip(5);
    }
    @TestDetailCaseAnnotation(id = 4, itemDetailName = "CASE_paperSkip_CN011", resDialogTitle =
            "CASE_paperSkip_CN011", APIName = "paperSkip")
    public void CASE_paperSkip_CN011() {
        Log.i(TAG, "CASE_paperSkip_CN011");
        CASE_printBitMap_CN001();
        API03paperSkip(10);
    }
    @TestDetailCaseAnnotation(id = 4, itemDetailName = "CASE_paperSkip_CN012", resDialogTitle =
            "CASE_paperSkip_CN012", APIName = "paperSkip")
    public void CASE_paperSkip_CN012() {
        Log.i(TAG, "CASE_paperSkip_CN012");
        CASE_printQrCode_CN001();
        API03paperSkip(15);
    }
    @TestDetailCaseAnnotation(id = 4, itemDetailName = "CASE_paperSkip_CN013", resDialogTitle =
            "CASE_paperSkip_CN013", APIName = "paperSkip")
    public void CASE_paperSkip_CN013() {
        Log.i(TAG, "CASE_paperSkip_CN013");
        CASE_printBarCode_CN001();
        API03paperSkip(20);
    }
    @TestDetailCaseAnnotation(id = 4, itemDetailName = "CASE_paperSkip_CN014", resDialogTitle =
            "CASE_paperSkip_CN014", APIName = "paperSkip")
    public void CASE_paperSkip_CN014() {
        Log.i(TAG, "CASE_paperSkip_CN014");
        CASE_printScript_CN001();
        API03paperSkip(25);
    }

    @TestDetailCaseAnnotation(id = 4, itemDetailName = "CASE_paperSkip_CN015", resDialogTitle =
            "CASE_paperSkip_CN015", APIName = "paperSkip")
    public void CASE_paperSkip_CN015() {
        Log.i(TAG, "CASE_paperSkip_CN015");
        CASE_printQrCode_CN001();
        API03paperSkip(10);
        CASE_printBarCode_CN001();
        API03paperSkip(10);
    }

    @TestDetailCaseAnnotation(id = 4, itemDetailName = "CASE_paperSkip_CN016", resDialogTitle =
            "CASE_paperSkip_CN016", APIName = "paperSkip")
    public void CASE_paperSkip_CN016() {
        Log.i(TAG, "CASE_paperSkip_CN016");
        for(int i=0;i<100;i++)
        {
            CASE_printQrCode_CN001();
            CASE_paperSkip_CN001();
        }
    }

    public void API_beep(int times, float frequency, float voice) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                AidlBeeper.Stub.asInterface(handle.getBeeper()).beep(times, frequency, voice);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    @TestDetailCaseAnnotation(id = 4, itemDetailName = "CASE_otherTest_CN001", resDialogTitle =
            "CASE_otherTest_CN001", APIName = "otherTest")
    public void CASE_otherTest_CN001() {
        Log.i(TAG, "CASE_otherTest_CN001");
        new Thread(new Runnable() {
            @Override
            public void run() {
                API02print("{'spos':[{'content-type':'txt','content':'\n\n','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'小票测试','position':'center','bold':'0','height':'-1','size':'3'},{'content-type':'jpg','content':'','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'持卡人存根','position':'right','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'商户：测试测试测试测试','position':'left','bold':'0','height':'-1','size':'3'},{'content-type':'txt','content':'    商户编号：812002110030001','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    终端编号：20150909','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    操作员号：001','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'交易：扫一扫支付','position':'left','bold':'0','height':'-1','size':'3'},{'content-type':'txt','content':'    支付渠道：微信支付','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    日期时间：2016\\/04\\/28  15:27:51','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    参考号：000014103147','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'one-dimension','content':'12312312312424','position':'center','bold':'0','height':'1','size':'3'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'金额：RMB  0.01','position':'left','bold':'1','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'备注：','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'本人确认以上交易，同意将其计入本卡账户 ','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'I ACKNOWLEDGE SATISFACTORY RECEIPT OF RELATIVE GOODS\\/SERVICE','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'two-dimension','content':'头，辛苦啦','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'\n\n\n\n','position':'center','bold':'0','height':'-1','size':'2'}]}", null, new AidlPrinterListener.Stub() {


                    @Override
                    public void onError(int code, String detail) throws RemoteException {
                        Log.i(TAG, "CASE_otherTest_CN001: "+"code=" + code + " detail=" + detail);
                    }

                    @Override
                    public void onFinish() throws RemoteException {

                    }
                });
            }
        }).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        API_beep(1,1,1);
    }
    public boolean API_led(int color, int operation, int times) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                return AidlLED.Stub.asInterface(handle.getLed()).ledOperation(color, operation, times);
            }
            return false;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }
    @TestDetailCaseAnnotation(id = 4, itemDetailName = "CASE_otherTest_CN002", resDialogTitle =
            "CASE_otherTest_CN002", APIName = "otherTest")
    public void CASE_otherTest_CN002() {
        Log.i(TAG, "CASE_otherTest_CN002");
        new Thread(new Runnable() {
            @Override
            public void run() {
                API02print("{'spos':[{'content-type':'txt','content':'\n\n','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'小票测试','position':'center','bold':'0','height':'-1','size':'3'},{'content-type':'jpg','content':'','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'持卡人存根','position':'right','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'商户：测试测试测试测试','position':'left','bold':'0','height':'-1','size':'3'},{'content-type':'txt','content':'    商户编号：812002110030001','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    终端编号：20150909','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    操作员号：001','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'交易：扫一扫支付','position':'left','bold':'0','height':'-1','size':'3'},{'content-type':'txt','content':'    支付渠道：微信支付','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    日期时间：2016\\/04\\/28  15:27:51','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    参考号：000014103147','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'one-dimension','content':'12312312312424','position':'center','bold':'0','height':'1','size':'3'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'金额：RMB  0.01','position':'left','bold':'1','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'备注：','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'本人确认以上交易，同意将其计入本卡账户 ','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'I ACKNOWLEDGE SATISFACTORY RECEIPT OF RELATIVE GOODS\\/SERVICE','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'two-dimension','content':'头，辛苦啦','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'\n\n\n\n','position':'center','bold':'0','height':'-1','size':'2'}]}", null, new AidlPrinterListener.Stub() {


                    @Override
                    public void onError(int code, String detail) throws RemoteException {
                        Log.i(TAG, "CASE_otherTest_CN002: "+"code=" + code + " detail=" + detail);
                    }

                    @Override
                    public void onFinish() throws RemoteException {

                    }
                });
            }
        }).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        API_led(1,1,1);
    }
    @TestDetailCaseAnnotation(id = 4, itemDetailName = "CASE_otherTest_CN003", resDialogTitle =
            "CASE_otherTest_CN003", APIName = "otherTest")
    public void CASE_otherTest_CN003() {
        Log.i(TAG, "CASE_otherTest_CN003");
        new Thread(new Runnable() {
            @Override
            public void run() {
                API02print("{'spos':[{'content-type':'txt','content':'\n\n','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'小票测试','position':'center','bold':'0','height':'-1','size':'3'},{'content-type':'jpg','content':'','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'持卡人存根','position':'right','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'商户：测试测试测试测试','position':'left','bold':'0','height':'-1','size':'3'},{'content-type':'txt','content':'    商户编号：812002110030001','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    终端编号：20150909','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    操作员号：001','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'交易：扫一扫支付','position':'left','bold':'0','height':'-1','size':'3'},{'content-type':'txt','content':'    支付渠道：微信支付','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    日期时间：2016\\/04\\/28  15:27:51','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    参考号：000014103147','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'one-dimension','content':'12312312312424','position':'center','bold':'0','height':'1','size':'3'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'金额：RMB  0.01','position':'left','bold':'1','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'备注：','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'本人确认以上交易，同意将其计入本卡账户 ','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'I ACKNOWLEDGE SATISFACTORY RECEIPT OF RELATIVE GOODS\\/SERVICE','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'two-dimension','content':'头，辛苦啦','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'\n\n\n\n','position':'center','bold':'0','height':'-1','size':'2'}]}", null, new AidlPrinterListener.Stub() {


                    @Override
                    public void onError(int code, String detail) throws RemoteException {
                        Log.i(TAG, "CASE_otherTest_CN003: "+"code=" + code + " detail=" + detail);
                    }

                    @Override
                    public void onFinish() throws RemoteException {

                    }
                });
            }
        }).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        API01startScan_1(0, 60000, getNewScannerListener());
    }
    AidlCardReaderListener getNewCardReaderListener() {
        return new AidlCardReaderListener.Stub() {
            @Override
            public void onError(int errorCode, String errorDescription) throws RemoteException {
                Log.i(TAG, "Code=" + errorCode);
                Log.i(TAG, "Description=" + errorDescription);
            }

            @Override
            public void onFindingCard(int cardType) throws RemoteException {
                Log.i(TAG, "Card type=" + cardType);
            }
        };
    }
    public void API01openCardReader(int openCardType, boolean isAllowfallback,boolean isMSDChecking, int timeout, AidlCardReaderListener cardReaderListener) {
        try {
            AidlDeviceService handle = DeviceService.getDeviceService();
            if(handle != null) {
                AidlCardReader.Stub.asInterface(handle.getCardReader()).openCardReader(openCardType, isAllowfallback, isMSDChecking, timeout, cardReaderListener);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    @TestDetailCaseAnnotation(id = 4, itemDetailName = "CASE_otherTest_CN004", resDialogTitle =
            "CASE_otherTest_CN004", APIName = "otherTest")
    public void CASE_otherTest_CN004() {
        Log.i(TAG, "CASE_otherTest_CN004");
        new Thread(new Runnable() {
            @Override
            public void run() {
                API02print("{'spos':[{'content-type':'txt','content':'\n\n','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'小票测试','position':'center','bold':'0','height':'-1','size':'3'},{'content-type':'jpg','content':'','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'持卡人存根','position':'right','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'商户：测试测试测试测试','position':'left','bold':'0','height':'-1','size':'3'},{'content-type':'txt','content':'    商户编号：812002110030001','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    终端编号：20150909','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    操作员号：001','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'交易：扫一扫支付','position':'left','bold':'0','height':'-1','size':'3'},{'content-type':'txt','content':'    支付渠道：微信支付','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    日期时间：2016\\/04\\/28  15:27:51','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    参考号：000014103147','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'one-dimension','content':'12312312312424','position':'center','bold':'0','height':'1','size':'3'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'金额：RMB  0.01','position':'left','bold':'1','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'备注：','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'本人确认以上交易，同意将其计入本卡账户 ','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'I ACKNOWLEDGE SATISFACTORY RECEIPT OF RELATIVE GOODS\\/SERVICE','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'two-dimension','content':'头，辛苦啦','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'\n\n\n\n','position':'center','bold':'0','height':'-1','size':'2'}]}", null, new AidlPrinterListener.Stub() {


                    @Override
                    public void onError(int code, String detail) throws RemoteException {
                        Log.i(TAG, "CASE_otherTest_CN004: "+"code=" + code + " detail=" + detail);
                    }

                    @Override
                    public void onFinish() throws RemoteException {

                    }
                });
            }
        }).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        API01openCardReader(0x01, false, false, 60000, getNewCardReaderListener());
    }
    @TestDetailCaseAnnotation(id = 4, itemDetailName = "CASE_otherTest_CN005", resDialogTitle =
            "CASE_otherTest_CN005", APIName = "otherTest")
    public void CASE_otherTest_CN005() {
        Log.i(TAG, "CASE_otherTest_CN005");
        new Thread(new Runnable() {
            @Override
            public void run() {
                API02print("{'spos':[{'content-type':'txt','content':'\n\n','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'小票测试','position':'center','bold':'0','height':'-1','size':'3'},{'content-type':'jpg','content':'','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'持卡人存根','position':'right','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'商户：测试测试测试测试','position':'left','bold':'0','height':'-1','size':'3'},{'content-type':'txt','content':'    商户编号：812002110030001','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    终端编号：20150909','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    操作员号：001','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'交易：扫一扫支付','position':'left','bold':'0','height':'-1','size':'3'},{'content-type':'txt','content':'    支付渠道：微信支付','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    日期时间：2016\\/04\\/28  15:27:51','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    参考号：000014103147','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'one-dimension','content':'12312312312424','position':'center','bold':'0','height':'1','size':'3'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'金额：RMB  0.01','position':'left','bold':'1','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'备注：','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'本人确认以上交易，同意将其计入本卡账户 ','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'I ACKNOWLEDGE SATISFACTORY RECEIPT OF RELATIVE GOODS\\/SERVICE','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'two-dimension','content':'头，辛苦啦','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'\n\n\n\n','position':'center','bold':'0','height':'-1','size':'2'}]}", null, new AidlPrinterListener.Stub() {


                    @Override
                    public void onError(int code, String detail) throws RemoteException {
                        Log.i(TAG, "CASE_otherTest_CN005: "+"code=" + code + " detail=" + detail);
                    }

                    @Override
                    public void onFinish() throws RemoteException {

                    }
                });
            }
        }).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        API01openCardReader(0x02, false, false, 60000, getNewCardReaderListener());
    }
    @TestDetailCaseAnnotation(id = 4, itemDetailName = "CASE_otherTest_CN006", resDialogTitle =
            "CASE_otherTest_CN006", APIName = "otherTest")
    public void CASE_otherTest_CN006() {
        Log.i(TAG, "CASE_otherTest_CN006");
        new Thread(new Runnable() {
            @Override
            public void run() {
                API02print("{'spos':[{'content-type':'txt','content':'\n\n','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'小票测试','position':'center','bold':'0','height':'-1','size':'3'},{'content-type':'jpg','content':'','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'持卡人存根','position':'right','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'商户：测试测试测试测试','position':'left','bold':'0','height':'-1','size':'3'},{'content-type':'txt','content':'    商户编号：812002110030001','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    终端编号：20150909','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    操作员号：001','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'交易：扫一扫支付','position':'left','bold':'0','height':'-1','size':'3'},{'content-type':'txt','content':'    支付渠道：微信支付','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    日期时间：2016\\/04\\/28  15:27:51','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    参考号：000014103147','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'one-dimension','content':'12312312312424','position':'center','bold':'0','height':'1','size':'3'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'金额：RMB  0.01','position':'left','bold':'1','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'备注：','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'本人确认以上交易，同意将其计入本卡账户 ','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'I ACKNOWLEDGE SATISFACTORY RECEIPT OF RELATIVE GOODS\\/SERVICE','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'two-dimension','content':'头，辛苦啦','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'\n\n\n\n','position':'center','bold':'0','height':'-1','size':'2'}]}", null, new AidlPrinterListener.Stub() {


                    @Override
                    public void onError(int code, String detail) throws RemoteException {
                        Log.i(TAG, "CASE_otherTest_CN006: "+"code=" + code + " detail=" + detail);
                    }

                    @Override
                    public void onFinish() throws RemoteException {

                    }
                });
            }
        }).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        API01openCardReader(0x04, false, false, 60000, getNewCardReaderListener());
    }*/

    /**
     * 打印图片案例
     */
    @TestDetailCaseAnnotation(id = 5, itemDetailName = "CASE_printBitMap_CN001", resDialogTitle =
            "CASE_printBitMap_CN001", APIName = "printBitMap")
    public void CASE_printBitMap_CN001() {
        Log.i(TAG, "CASE_printBitMap_CN001");
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.bitmap_2);
        API06printBitMap(0, bitmap, 60, new AidlPrinterListener.Stub() {

            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBitMap_CN001: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }

    @TestDetailCaseAnnotation(id = 5, itemDetailName = "CASE_printBitMap_CN002", resDialogTitle =
            "CASE_printBitMap_CN002", APIName = "printBitMap")
    public void CASE_printBitMap_CN002() {
        Log.i(TAG, "CASE_printBitMap_CN002");
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.bitmap_2);
        API06printBitMap(50, bitmap, 60, new AidlPrinterListener.Stub() {

            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBitMap_CN002: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }

    @TestDetailCaseAnnotation(id = 5, itemDetailName = "CASE_printBitMap_CN003", resDialogTitle =
            "CASE_printBitMap_CN003", APIName = "printBitMap")
    public void CASE_printBitMap_CN003() {
        Log.i(TAG, "CASE_printBitMap_CN003");
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.bitmap_2);
        API06printBitMap(184, bitmap, 60, new AidlPrinterListener.Stub() {

            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBitMap_CN003: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }

    @TestDetailCaseAnnotation(id = 5, itemDetailName = "CASE_printBitMap_CN004", resDialogTitle =
            "CASE_printBitMap_CN004", APIName = "printBitMap")
    public void CASE_printBitMap_CN004() {
        Log.i(TAG, "CASE_printBitMap_CN004");
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.bitmap_4);
        API06printBitMap(0, bitmap, 60, new AidlPrinterListener.Stub() {

            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBitMap_CN004: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }

    @TestDetailCaseAnnotation(id = 5, itemDetailName = "CASE_printBitMap_CN005", resDialogTitle =
            "CASE_printBitMap_CN005", APIName = "printBitMap")
    public void CASE_printBitMap_CN005() {
        Log.i(TAG, "CASE_printBitMap_CN005");
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.bitmap_5);
        API06printBitMap(50, bitmap, 60, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBitMap_CN005: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(3);
    }

    @TestDetailCaseAnnotation(id = 5, itemDetailName = "CASE_printBitMap_CN006", resDialogTitle =
            "CASE_printBitMap_CN006", APIName = "printBitMap")
    public void CASE_printBitMap_CN006() {
        Log.i(TAG, "CASE_printBitMap_CN006");
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.bitmap_2);
        API06printBitMap(0, bitmap, 1, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBitMap_CN006: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 5, itemDetailName = "CASE_printBitMap_CN007", resDialogTitle =
            "CASE_printBitMap_CN007", APIName = "printBitMap")
    public void CASE_printBitMap_CN007() {
        Log.i(TAG, "CASE_printBitMap_CN007");
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.bitmap_2);
        API06printBitMap(0, bitmap, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBitMap_CN007: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    /*@TestDetailCaseAnnotation(id = 5, itemDetailName = "CASE_printBitMap_CN008", resDialogTitle =
            "CASE_printBitMap_CN008", APIName = "printBitMap")
    public void CASE_printBitMap_CN008() {
        Log.i(TAG, "CASE_printBitMap_CN008");
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.boclogo);
        API06printBitMap(-1, bitmap, 90, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBitMap_CN008: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 5, itemDetailName = "CASE_printBitMap_CN009", resDialogTitle =
            "CASE_printBitMap_CN009", APIName = "printBitMap")
    public void CASE_printBitMap_CN009() {
        Log.i(TAG, "CASE_printBitMap_CN009");
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.bitmap_2);
        API06printBitMap(384, bitmap, 90, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBitMap_CN009: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 5, itemDetailName = "CASE_printBitMap_CN010", resDialogTitle =
            "CASE_printBitMap_CN010", APIName = "printBitMap")
    public void CASE_printBitMap_CN010() {
        Log.i(TAG, "CASE_printBitMap_CN010");
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.bitmap_2);
        API06printBitMap(Integer.MAX_VALUE, bitmap, 90, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBitMap_CN010: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 5, itemDetailName = "CASE_printBitMap_CN011", resDialogTitle =
            "CASE_printBitMap_CN011", APIName = "printBitMap")
    public void CASE_printBitMap_CN011() {
        Log.i(TAG, "CASE_printBitMap_CN011");
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.bitmap_2);
        API06printBitMap(Integer.MIN_VALUE, bitmap, 90, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBitMap_CN011: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 5, itemDetailName = "CASE_printBitMap_CN012", resDialogTitle =
            "CASE_printBitMap_CN012", APIName = "printBitMap")
    public void CASE_printBitMap_CN012() {
        Log.i(TAG, "CASE_printBitMap_CN012");
        //没有这个图片
        Bitmap bm = BitmapFactory.decodeFile("/storage/emulated/legacy/Download/asdfgh.JPG");

        API06printBitMap(0, bm, 30, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBitMap_CN012: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 5, itemDetailName = "CASE_printBitMap_CN013", resDialogTitle =
            "CASE_printBitMap_CN013", APIName = "printBitMap")
    public void CASE_printBitMap_CN013() {
        Log.i(TAG, "CASE_printBitMap_CN013");
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.bitmap_1);
        API06printBitMap(0, bitmap, 30, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBitMap_CN013: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 5, itemDetailName = "CASE_printBitMap_CN014", resDialogTitle =
            "CASE_printBitMap_CN014", APIName = "printBitMap")
    public void CASE_printBitMap_CN014() {
        Log.i(TAG, "CASE_printBitMap_CN014");
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.bitmap_2);
        API06printBitMap(0, bitmap, 0, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBitMap_CN013: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 5, itemDetailName = "CASE_printBitMap_CN015", resDialogTitle =
            "CASE_printBitMap_CN015", APIName = "printBitMap")
    public void CASE_printBitMap_CN015() {
        Log.i(TAG, "CASE_printBitMap_CN015");
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.bitmap_2);
        API06printBitMap(0, bitmap, -1, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBitMap_CN015: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 5, itemDetailName = "CASE_printBitMap_CN016", resDialogTitle =
            "CASE_printBitMap_CN016", APIName = "printBitMap")
    public void CASE_printBitMap_CN016() {
        Log.i(TAG, "CASE_printBitMap_CN016");
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.bitmap_2);
        API06printBitMap(0, bitmap, Integer.MAX_VALUE, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBitMap_CN016: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 5, itemDetailName = "CASE_printBitMap_CN017", resDialogTitle =
            "CASE_printBitMap_CN017", APIName = "printBitMap")
    public void CASE_printBitMap_CN017() {
        Log.i(TAG, "CASE_printBitMap_CN017");
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.bitmap_2);
        API06printBitMap(0, bitmap, Integer.MIN_VALUE, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBitMap_CN017: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 5, itemDetailName = "CASE_printBitMap_CN018", resDialogTitle =
            "CASE_printBitMap_CN018", APIName = "printBitMap")
    public void CASE_printBitMap_CN018() {
        Log.i(TAG, "CASE_printBitMap_CN018");
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.bitmap_2);
        API06printBitMap(0, bitmap, 60, null);
    }
    @TestDetailCaseAnnotation(id = 5, itemDetailName = "CASE_printBitMap_CN019", resDialogTitle =
            "CASE_printBitMap_CN019", APIName = "printBitMap")
    public void CASE_printBitMap_CN019() {
        Log.i(TAG, "CASE_printBitMap_CN019");
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.bitmap_2);
        API06printBitMap(0, bitmap, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBitMap_CN019_1: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {
                Log.i(TAG, "CASE_printBitMap_CN019_onFinish_1:");
            }
        });
        API03paperSkip(5);
        API06printBitMap(0, bitmap, 60, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBitMap_CN019_2: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {
                Log.i(TAG, "CASE_printBitMap_CN019_onFinish_2:");
            }
        });
    }
    @TestDetailCaseAnnotation(id = 5, itemDetailName = "CASE_printBitMap_CN020", resDialogTitle =
            "CASE_printBitMap_CN020", APIName = "printBitMap")
    public void CASE_printBitMap_CN020() {
        Log.i(TAG, "CASE_printBitMap_CN020");
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.bitmap_2);
        API06printBitMap(0, bitmap, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBitMap_CN020_1: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {
                Log.i(TAG, "CASE_printBitMap_CN020_onFinish_1:");
            }
        });
        API06printBitMap(0, bitmap, 60, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBitMap_CN020_2: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {
                Log.i(TAG, "CASE_printBitMap_CN020_onFinish_2:");
            }
        });
    }
    @TestDetailCaseAnnotation(id = 5, itemDetailName = "CASE_printBitMap_CN021", resDialogTitle =
            "CASE_printBitMap_CN021", APIName = "printBitMap")
    public void CASE_printBitMap_CN021() {
        Log.i(TAG, "CASE_printBitMap_CN021");
        CASE_print_CN011();
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.bitmap_2);
        API06printBitMap(0, bitmap, 90, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBitMap_CN021: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {
                Log.i(TAG, "CASE_printBitMap_CN021_onFinish:");
            }
        });
    }
    @TestDetailCaseAnnotation(id = 5, itemDetailName = "CASE_printBitMap_CN022", resDialogTitle =
            "CASE_printBitMap_CN022", APIName = "printBitMap")
    public void CASE_printBitMap_CN022() {
        Log.i(TAG, "CASE_printBitMap_CN022");
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.bitmap_2);
        API06printBitMap(0, bitmap, 60, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBitMap_CN022_1: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {
                Log.i(TAG, "CASE_printBitMap_CN022_onFinish_1:");
            }
        });
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Bitmap bitmap1 = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.boclogo);
        API06printBitMap(0, bitmap1, 60, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBitMap_CN022_2: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {
                Log.i(TAG, "CASE_printBitMap_CN022_onFinish_2:");
            }
        });
    }
    @TestDetailCaseAnnotation(id = 5, itemDetailName = "CASE_printBitMap_CN023", resDialogTitle =
            "CASE_printBitMap_CN023", APIName = "printBitMap")
    public void CASE_printBitMap_CN023() {
        Log.i(TAG, "CASE_printBitMap_CN023");
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.boclogo);
        final Bitmap finalBm = bitmap;
        new Thread(new Runnable() {
            @Override
            public void run() {
                API06printBitMap(0, finalBm, 60, new AidlPrinterListener.Stub() {


                    @Override
                    public void onError(int code, String detail) throws RemoteException {
                        Log.i(TAG, "CASE_printBitMap_CN023_1: "+"code=" + code + " detail=" + detail);
                    }

                    @Override
                    public void onFinish() throws RemoteException {
                        Log.i(TAG, "CASE_printBitMap_CN023_onFinish_1:");
                    }
                });
            }
        }).start();
        API06printBitMap(0, bitmap, 60, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printBitMap_CN023_2: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {
                Log.i(TAG, "CASE_printBitMap_CN023_onFinish_2:");
            }
        });
    }*/
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN001", resDialogTitle =
            "CASE_printScript_CN001", APIName = "printScript")
    public void CASE_printScript_CN001() {
        Log.i(TAG, "CASE_printScript_CN001");
        API07printScript("!hz s!asc n!gray 5!yspace 6*text l 中国银行智能项目中国银行智能项目中国银行智能项目!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN001: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN002", resDialogTitle =
            "CASE_printScript_CN002", APIName = "printScript")
    public void CASE_printScript_CN002() {
        Log.i(TAG, "CASE_printScript_CN002");
        API07printScript("!hz n!asc n!gray 5!yspace 6*text l 中国银行智能项目中国银行智能项目!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN002: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN003", resDialogTitle =
            "CASE_printScript_CN003", APIName = "printScript")
    public void CASE_printScript_CN003() {
        Log.i(TAG, "CASE_printScript_CN003");
        API07printScript("!hz l!asc n!gray 5!yspace 6*text l 中国银行智能项目中国银行!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN003: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN004", resDialogTitle =
            "CASE_printScript_CN004", APIName = "printScript")
    public void CASE_printScript_CN004() {
        Log.i(TAG, "CASE_printScript_CN004");
        API07printScript("!hz sn!asc n!gray 5!yspace 6*text l 中国银行智能项目中国银行智能项目中国银行智能项目!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN004: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN005", resDialogTitle =
            "CASE_printScript_CN005", APIName = "printScript")
    public void CASE_printScript_CN005() {
        Log.i(TAG, "CASE_printScript_CN005");
        API07printScript("!hz sl!asc n!gray 5!yspace 6*text l 中国银行智能项目中国银行智能项目中国银行智能项目!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN005: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN006", resDialogTitle =
            "CASE_printScript_CN006", APIName = "printScript")
    public void CASE_printScript_CN006() {
        Log.i(TAG, "CASE_printScript_CN006");
        API07printScript("!hz nl!asc n!gray 5!yspace 6*text l 中国银行智能项目中国银行智能项目!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN006: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN007", resDialogTitle =
            "CASE_printScript_CN007", APIName = "printScript")
    public void CASE_printScript_CN007() {
        Log.i(TAG, "CASE_printScript_CN007");
        API07printScript("!hz n!asc s!gray 5!yspace 6*text l ABCDEFGHABCDEFGHABCDEFGHABCDEFGHABCDEFGHABCDEFGH!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN007: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN008", resDialogTitle =
            "CASE_printScript_CN008", APIName = "printScript")
    public void CASE_printScript_CN008() {
        Log.i(TAG, "CASE_printScript_CN008");
        API07printScript("!hz n!asc n!gray 5!yspace 6*text l ABCDEFGHABCDEFGHABCDEFGHABCDEFGH!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN008: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN009", resDialogTitle =
            "CASE_printScript_CN009", APIName = "printScript")
    public void CASE_printScript_CN009() {
        Log.i(TAG, "CASE_printScript_CN009");
        API07printScript("!hz n!asc l!gray 5!yspace 6*text l ABCDEFGHABCDEFGHABCDEFGH!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN009: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN010", resDialogTitle =
            "CASE_printScript_CN010", APIName = "printScript")
    public void CASE_printScript_CN010() {
        Log.i(TAG, "CASE_printScript_CN010");
        API07printScript("!hz n!asc sn!gray 5!yspace 6*text l ABCDEFGHABCDEFGHABCDEFGHABCDEFGHABCDEFGHABCDEFGH!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN010: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN011", resDialogTitle =
            "CASE_printScript_CN011", APIName = "printScript")
    public void CASE_printScript_CN011() {
        Log.i(TAG, "CASE_printScript_CN011");
        API07printScript("!hz n!asc sl!gray 5!yspace 6*text l ABCDEFGHABCDEFGHABCDEFGHABCDEFGHABCDEFGHABCDEFGH!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN011: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN012", resDialogTitle =
            "CASE_printScript_CN012", APIName = "printScript")
    public void CASE_printScript_CN012() {
        Log.i(TAG, "CASE_printScript_CN012");
        API07printScript("!hz n!asc nl!gray 5!yspace 6*text l ABCDEFGHABCDEFGHABCDEFGHABCDEFGH!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN012: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN013", resDialogTitle =
            "CASE_printScript_CN013", APIName = "printScript")
    public void CASE_printScript_CN013() {
        Log.i(TAG, "CASE_printScript_CN013");
        API07printScript("!hz n!asc n!gray 5!yspace 6*text c 商户名称： 中国银行智能项目!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN013: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN014", resDialogTitle =
            "CASE_printScript_CN014", APIName = "printScript")
    public void CASE_printScript_CN014() {
        Log.i(TAG, "CASE_printScript_CN014");
        API07printScript("!hz n!asc n!gray 1!yspace 6*text c 商户名称： 中国银行智能项目!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN014: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN015", resDialogTitle =
            "CASE_printScript_CN015", APIName = "printScript")
    public void CASE_printScript_CN015() {
        Log.i(TAG, "CASE_printScript_CN015");
        API07printScript("!hz n!asc n!gray 10!yspace 6*text c 商户名称： 中国银行智能项目!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN015: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN016", resDialogTitle =
            "CASE_printScript_CN016", APIName = "printScript")
    public void CASE_printScript_CN016() {
        Log.i(TAG, "CASE_printScript_CN016");
        API07printScript("!hz n!asc n!gray 5!yspace 6*text c 商户名称： 中国银行智能项目 *text c 商户名称： 中国银行智能项目!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN016: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN017", resDialogTitle =
            "CASE_printScript_CN017", APIName = "printScript")
    public void CASE_printScript_CN017() {
        Log.i(TAG, "CASE_printScript_CN017");
        API07printScript("!hz n!asc n!gray 5!yspace 0*text c 商户名称： 中国银行智能项目*text c 商户名称： 中国银行智能项目!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN017: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN018", resDialogTitle =
            "CASE_printScript_CN018", APIName = "printScript")
    public void CASE_printScript_CN018() {
        Log.i(TAG, "CASE_printScript_CN018");
        API07printScript("!hz n!asc n!gray 5!yspace 60*text c 商户名称： 中国银行智能项目*text c 商户名称： 中国银行智能项目!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN018: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN019", resDialogTitle =
            "CASE_printScript_CN019", APIName = "printScript")
    public void CASE_printScript_CN019() {
        Log.i(TAG, "CASE_printScript_CN019");
        API07printScript("!barcode 2 64*barcode c \"891110847565611252\"!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN019: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN020", resDialogTitle =
            "CASE_printScript_CN020", APIName = "printScript")
    public void CASE_printScript_CN020() {
        Log.i(TAG, "CASE_printScript_CN020");
        API07printScript("!barcode 1 8*barcode c \"891110847565611252\"!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN020: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN021", resDialogTitle =
            "CASE_printScript_CN021", APIName = "printScript")
    public void CASE_printScript_CN021() {
        Log.i(TAG, "CASE_printScript_CN021");
        API07printScript("!barcode 8 320*barcode c \"891110847565611252\"!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN021: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN022", resDialogTitle =
            "CASE_printScript_CN022", APIName = "printScript")
    public void CASE_printScript_CN022() {
        Log.i(TAG, "CASE_printScript_CN022");
        API07printScript("!qrcode 100 2*qrcode c \"中国银行项目测试\"!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN022: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN023", resDialogTitle =
            "CASE_printScript_CN023", APIName = "printScript")
    public void CASE_printScript_CN023() {
        Log.i(TAG, "CASE_printScript_CN023");
        API07printScript("!qrcode 1 0*qrcode c \"中国银行项目测试\"!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN023: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN024", resDialogTitle =
            "CASE_printScript_CN024", APIName = "printScript")
    public void CASE_printScript_CN024() {
        Log.i(TAG, "CASE_printScript_CN024");
        API07printScript("!qrcode 384 3*qrcode c \"中国银行项目测试\"!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN024: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN025", resDialogTitle =
            "CASE_printScript_CN025", APIName = "printScript")
    public void CASE_printScript_CN025() {
        Log.i(TAG, "CASE_printScript_CN025");
        API07printScript("!hz n!asc n!gray 5!yspace 6!BOCFONT 1 1 0*text c 中国银行智能项目 *text c SMARTPOSSMARTPOSSMARTPOS!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN025: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN026", resDialogTitle =
            "CASE_printScript_CN026", APIName = "printScript")
    public void CASE_printScript_CN026() {
        Log.i(TAG, "CASE_printScript_CN026");
        API07printScript("!hz n!asc n!gray 5!yspace 6!BOCFONT 2 2 1*text c 中国银行智能项目中国银行 *text c SMARTPOSSMAR!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN026: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN027", resDialogTitle =
            "CASE_printScript_CN027", APIName = "printScript")
    public void CASE_printScript_CN027() {
        Log.i(TAG, "CASE_printScript_CN027");
        API07printScript("!hz n!asc n!gray 5!yspace 6!BOCFONT 3 3 2*text c 中国银行智能项目中国银行 *text c SMARTPOSSMARTPOSSMARTPOS!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {

            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN027: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN028", resDialogTitle =
            "CASE_printScript_CN028", APIName = "printScript")
    public void CASE_printScript_CN028() {
        Log.i(TAG, "CASE_printScript_CN028");

        API07printScript("!hz n!asc n!gray 5!yspace 6!BOCFONT 4 4 3*text c 中国银行智能项目中国银行 *text c SMARTPOSSMARTPOS!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {

            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN028: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN029", resDialogTitle =
            "CASE_printScript_CN029", APIName = "printScript")
    public void CASE_printScript_CN029() {
        Log.i(TAG, "CASE_printScript_CN029");

        API07printScript("!hz n!asc n!gray 5!yspace 6!BOCFONT 5 5 3*text c 中国银行智能项目中国银行 *text c SMARTPOSSMARTPOSSMARTPOSSMARTPOSSMARTPOSSMARTPOSSMARTPOSSMARTPOS!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {

            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN029: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN030", resDialogTitle =
            "CASE_printScript_CN030", APIName = "printScript")
    public void CASE_printScript_CN030() {
        Log.i(TAG, "CASE_printScript_CN030");

        API07printScript("!hz n!asc n!gray 5!yspace 6!BOCFONT 6 6 3*text c 中国银行智能项目中国银行智能项目中国银行智能项目 *text c SMARTPOSSMARTPOSSMARTPOSSMARTPOSSMARTPOSSMARTPOS!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {

            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN030: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN031", resDialogTitle =
            "CASE_printScript_CN031", APIName = "printScript")
    public void CASE_printScript_CN031() {
        Log.i(TAG, "CASE_printScript_CN031");

        API07printScript("!hz n!asc n!gray 5!yspace 6!BOCFONT 7 7 3*text c 中国银行智能项目中国银行智能项目中国银行智能项目 *text c SMARTPOSSMARTPOSSMARTPOSSMARTPOSSMARTPOSSMARTPOSSMARTPOSSMARTPOSSMARTPOSABCD!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {

            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN031: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN032", resDialogTitle =
            "CASE_printScript_CN032", APIName = "printScript")
    public void CASE_printScript_CN032() {
        Log.i(TAG, "CASE_printScript_CN032");

        API07printScript("!hz n!asc n!gray 5!yspace 6!BOCFONT 8 8 3*text c 中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目 *text c SMARTPOSSMARTPOSSMARTPOSSMARTPOSSMARTPOSSMARTPOSSMARTPOSSMARTPOSSMARTPOSABCD!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {

            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN032: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN033", resDialogTitle =
            "CASE_printScript_CN033", APIName = "printScript")
    public void CASE_printScript_CN033() {
        Log.i(TAG, "CASE_printScript_CN033");

        API07printScript("!hz n!asc n!gray 5!yspace 6!BOCFONT 8 9 3*text c 中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目 *text c SMARTPOSSMARTPOSSMARTPOSSMARTPOSSMARTP!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {

            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN033: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN034", resDialogTitle =
            "CASE_printScript_CN034", APIName = "printScript")
    public void CASE_printScript_CN034() {
        Log.i(TAG, "CASE_printScript_CN034");

        API07printScript("!hz n!asc n!gray 5!yspace 6!BOCFONT 8 10 3*text c 中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目 *text c SMARTPOSSMARTPOSSMARTPOSSMARTPOSSMARTP!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {

            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN034: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN035", resDialogTitle =
            "CASE_printScript_CN035", APIName = "printScript")
    public void CASE_printScript_CN035() {
        Log.i(TAG, "CASE_printScript_CN035");

        API07printScript("!hz n!asc n!gray 5!yspace 6!BOCFONT 8 11 3*text c 中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目 *text c SMARTPOSSMARTPOSSMARTPOSSMARTPOS!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {

            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN035: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN036", resDialogTitle =
            "CASE_printScript_CN036", APIName = "printScript")
    public void CASE_printScript_CN036() {
        Log.i(TAG, "CASE_printScript_CN036");

        API07printScript("!hz n!asc n!gray 5!yspace 6!BOCFONT 8 12 3*text c 中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目 *text c SMARTPOSSMARTPOSSMARTPOSSMARTPOS!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {

            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN036: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN037", resDialogTitle =
            "CASE_printScript_CN037", APIName = "printScript")
    public void CASE_printScript_CN037() {
        Log.i(TAG, "CASE_printScript_CN037");

        API07printScript("!hz n!asc n!gray 5!yspace 6!BOCFONT 8 13 3*text c 中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目 *text c SMARTPOSSMARTPOSSMARTPOS!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {

            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN037: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN038", resDialogTitle =
            "CASE_printScript_CN038", APIName = "printScript")
    public void CASE_printScript_CN038() {
        Log.i(TAG, "CASE_printScript_CN038");

        API07printScript("!hz n!asc n!gray 5!yspace 6!BOCFONT 8 14 3*text c 中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目 *text c SMARTPOSSMARTPOSSMARTPOSSMARTPOS!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {

            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN038: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN039", resDialogTitle =
            "CASE_printScript_CN039", APIName = "printScript")
    public void CASE_printScript_CN039() {
        Log.i(TAG, "CASE_printScript_CN039");

        API07printScript("!hz n!asc n!gray 5!yspace 6!BOCFONT 8 15 3*text c 中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目 *text c SMARTPOSSMARTPOSSMARTPOSSMARTPOS!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {

            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN039: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN040", resDialogTitle =
            "CASE_printScript_CN040", APIName = "printScript")
    public void CASE_printScript_CN040() {
        Log.i(TAG, "CASE_printScript_CN040");

        API07printScript("!hz n!asc n!gray 5!yspace 6!BOCFONT 8 16 3*text c 中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目 *text c SMARTPOSSMARTPOSSMARTPOS!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {

            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN040: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN041", resDialogTitle =
            "CASE_printScript_CN041", APIName = "printScript")
    public void CASE_printScript_CN041() {
        Log.i(TAG, "CASE_printScript_CN041");

        API07printScript("!hz n!asc n!gray 5!yspace 6!BOCFONT 8 17 3*text c 中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目 *text c SMARTPOSSMARTPOSSMARTPOSSMARTPOS!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {

            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN041: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN042", resDialogTitle =
            "CASE_printScript_CN042", APIName = "printScript")
    public void CASE_printScript_CN042() {
        Log.i(TAG, "CASE_printScript_CN042");

        API07printScript("!hz n!asc n!gray 5!yspace 6!BOCFONT 8 18 3*text c 中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目 *text c SMARTPOSSMARTPOSSMARTPOSSMARTPOS!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {

            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN042: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN043", resDialogTitle =
            "CASE_printScript_CN043", APIName = "printScript")
    public void CASE_printScript_CN043() {
        Log.i(TAG, "CASE_printScript_CN043");

        API07printScript("!hz n!asc n!gray 5!yspace 6!BOCFONT 8 19 3*text c 中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目中国银行智能项目 *text c SMARTPOSSMARTPOSSMARTPOS!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {

            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN043: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN044", resDialogTitle =
            "CASE_printScript_CN044", APIName = "printScript")
    public void CASE_printScript_CN044() {
        Log.i(TAG, "CASE_printScript_CN044");
        API07printScript("!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN044: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN045", resDialogTitle =
            "CASE_printScript_CN045", APIName = "printScript")
    public void CASE_printScript_CN045() {
        Log.i(TAG, "CASE_printScript_CN045");

        API07printScript("!hz n!asc n!gray 5!yspace 6*text l 中国银行智能项目!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {

            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN045: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN046", resDialogTitle =
            "CASE_printScript_CN046", APIName = "printScript")
    public void CASE_printScript_CN046() {
        Log.i(TAG, "CASE_printScript_CN046");

        API07printScript("!hz n!asc n!gray 5!yspace 6*text c 中国银行智能项目!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {

            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN046: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN047", resDialogTitle =
            "CASE_printScript_CN047", APIName = "printScript")
    public void CASE_printScript_CN047() {
        Log.i(TAG, "CASE_printScript_CN047");

        API07printScript("!hz n!asc n!gray 5!yspace 6*text r 中国银行智能项目!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {

            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN047: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN048", resDialogTitle =
            "CASE_printScript_CN048", APIName = "printScript")
    public void CASE_printScript_CN048() {
        Log.i(TAG, "CASE_printScript_CN048");
        API07printScript("!barcode 2 64*barcode l \"891110847565611252\"!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN048: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN049", resDialogTitle =
            "CASE_printScript_CN049", APIName = "printScript")
    public void CASE_printScript_CN049() {
        Log.i(TAG, "CASE_printScript_CN049");
        API07printScript("!barcode 2 64*barcode c \"891110847565611252\"!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN049: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN050", resDialogTitle =
            "CASE_printScript_CN050", APIName = "printScript")
    public void CASE_printScript_CN050() {
        Log.i(TAG, "CASE_printScript_CN050");
        API07printScript("!barcode 2 64*barcode r \"891110847565611252\"!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN050: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN051", resDialogTitle =
            "CASE_printScript_CN051", APIName = "printScript")
    public void CASE_printScript_CN051() {
        Log.i(TAG, "CASE_printScript_CN051");
        API07printScript("!qrcode 100 2*qrcode l \"中国银行项目测试\"!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN051: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN052", resDialogTitle =
            "CASE_printScript_CN052", APIName = "printScript")
    public void CASE_printScript_CN052() {
        Log.i(TAG, "CASE_printScript_CN052");
        API07printScript("!qrcode 100 2*qrcode c \"中国银行项目测试\"!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN052: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN053", resDialogTitle =
            "CASE_printScript_CN053", APIName = "printScript")
    public void CASE_printScript_CN053() {
        Log.i(TAG, "CASE_printScript_CN053");
        API07printScript("!qrcode 100 2*qrcode r \"中国银行项目测试\"!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN053: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN054", resDialogTitle =
            "CASE_printScript_CN054", APIName = "printScript")
    public void CASE_printScript_CN054() {
        Log.i(TAG, "CASE_printScript_CN054");
        HashMap localHashMap =new HashMap();
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.bitmap_2);
        localHashMap.put("logo", bitmap);
        API07printScript("*image l 100*100 path:logo!BOCPRNOVER", localHashMap, 30, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN054: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN055", resDialogTitle =
            "CASE_printScript_CN055", APIName = "printScript")
    public void CASE_printScript_CN055() {
        Log.i(TAG, "CASE_printScript_CN055");
        HashMap localHashMap =new HashMap();
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.bitmap_2);
        localHashMap.put("logo", bitmap);

        API07printScript("*image c 100*100 path:logo!BOCPRNOVER", localHashMap, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN055: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN056", resDialogTitle =
            "CASE_printScript_CN056", APIName = "printScript")
    public void CASE_printScript_CN056() {
        Log.i(TAG, "CASE_printScript_CN056");
        HashMap localHashMap =new HashMap();

        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.bitmap_2);
        localHashMap.put("logo", bitmap);

        API07printScript("*image r 100*100 path:logo!BOCPRNOVER", localHashMap, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN056: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        API03paperSkip(5);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN057", resDialogTitle =
            "CASE_printScript_CN057", APIName = "printScript")
    public void CASE_printScript_CN057() {
        Log.i(TAG, "CASE_printScript_CN057");
        HashMap localHashMap =new HashMap();

        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.boclogo);
        localHashMap.put("logo", bitmap);
        API07printScript("!hz n!asc n!gray 10!yspace 8!barcode 2 64!qrcode 100 2*text c 中国银行智能项目*image c 320*89 path:logo*barcode c \"891110847565611252\"*qrcode c \"中国银行项目测试\" !BOCPRNOVER ", localHashMap, 30, new AidlPrinterListener.Stub() {

            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN057: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN058", resDialogTitle =
            "CASE_printScript_CN058", APIName = "printScript")
    public void CASE_printScript_CN058() {
        Log.i(TAG, "CASE_printScript_CN058");
        API07printScript("*feedline 10", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN058: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN059", resDialogTitle =
            "CASE_printScript_CN059", APIName = "printScript")
    public void CASE_printScript_CN059() {
        Log.i(TAG, "CASE_printScript_CN059");
        API07printScript("*feedline -10", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN059: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN060", resDialogTitle =
            "CASE_printScript_CN060", APIName = "printScript")
    public void CASE_printScript_CN060() {
        Log.i(TAG, "CASE_printScript_CN060");
        API07printScript("*line !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN060: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN061", resDialogTitle =
            "CASE_printScript_CN061", APIName = "printScript")
    public void CASE_printScript_CN061() {
        Log.i(TAG, "CASE_printScript_CN061");
        HashMap localHashMap =new HashMap();

        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.boclogo);
        localHashMap.put("logo", bitmap);
        API07printScript("!hz n!asc n!gray 10!yspace 8!barcode 2 64!qrcode 100 2*text c 商户名称：中国银行智能项目\n *image c 320*89 path:logo *barcode c \"891110847565611252\n\" *qrcode c \"中国银行项目测试\n\" !BOCPRNOVER", localHashMap, 30, new AidlPrinterListener.Stub() {

            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN061: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    /*@TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN062", resDialogTitle =
            "CASE_printScript_CN062", APIName = "printScript")
    public void CASE_printScript_CN062() {
        Log.i(TAG, "CASE_printScript_CN062");
        HashMap localHashMap =new HashMap();
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.boclogo);
        localHashMap.put("logo", bitmap);
        API07printScript("hz n asc n gray 10 yspace 8 barcode 2 64 qrcode 100 2 *text c 商户名称： 中国银行智能POS项目 *image c 图片尺寸 path:logo *barcode c \"891110847565611252\" *qrcode c \"中国银行项目测试\" !BOCPRNOVER ", localHashMap, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN062: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN063", resDialogTitle =
            "CASE_printScript_CN063", APIName = "printScript")
    public void CASE_printScript_CN063() {
        Log.i(TAG, "CASE_printScript_CN063");
        HashMap localHashMap =new HashMap();
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.boclogo);
        localHashMap.put("logo", bitmap);
        API07printScript("!hz n !asc n !gray 10 !yspace 8 text c 商户名称： 中国银行智能POS项目 image c 图片尺寸 path:logo barcode c \"891110847565611252\" qrcode c \"中国银行项目测试\" !BOCPRNOVER ", localHashMap, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN063: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN064", resDialogTitle =
            "CASE_printScript_CN064", APIName = "printScript")
    public void CASE_printScript_CN064() {
        Log.i(TAG, "CASE_printScript_CN064");
        API07printScript("!hz n !asc n !gray 10 !yspace 8 *textc商户名称： 中国银行智能POS项目!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN064: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN065", resDialogTitle =
            "CASE_printScript_CN065", APIName = "printScript")
    public void CASE_printScript_CN065() {
        Log.i(TAG, "CASE_printScript_CN065");
        API07printScript("!hz a !asc n !gray 10 !yspace 8 !BOCFONT 1 1 3 *text c 商户名称： 中国银行智能POS项目!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN065: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN066", resDialogTitle =
            "CASE_printScript_CN066", APIName = "printScript")
    public void CASE_printScript_CN066() {
        Log.i(TAG, "CASE_printScript_CN066");
        API07printScript("!hz !asc n !gray 10 !yspace 8 !BOCFONT 1 1 3 *text c 商户名称： 中国银行智能POS项目 !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN066: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN067", resDialogTitle =
            "CASE_printScript_CN067", APIName = "printScript")
    public void CASE_printScript_CN067() {
        Log.i(TAG, "CASE_printScript_CN067");
        API07printScript("!hz n !asc a !gray 10 !yspace 8 !BOCFONT 1 1 3 *text c 商户名称： 中国银行智能POS项目 !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN067: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN068", resDialogTitle =
            "CASE_printScript_CN068", APIName = "printScript")
    public void CASE_printScript_CN068() {
        Log.i(TAG, "CASE_printScript_CN068");
        API07printScript("!hz n !asc !gray 10 !yspace 8 !BOCFONT 1 1 3 *text c 商户名称： 中国银行智能POS项目 !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN068: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN069", resDialogTitle =
            "CASE_printScript_CN069", APIName = "printScript")
    public void CASE_printScript_CN069() {
        Log.i(TAG, "CASE_printScript_CN069");
        API07printScript("!hz n !asc n !gray 0 !yspace 8 !BOCFONT 1 1 3 *text c 商户名称： 中国银行智能POS项目 !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN069: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN070", resDialogTitle =
            "CASE_printScript_CN070", APIName = "printScript")
    public void CASE_printScript_CN070() {
        Log.i(TAG, "CASE_printScript_CN070");
        API07printScript("!hz n !asc n !gray 11 !yspace 8 !BOCFONT 1 1 3 *text c 商户名称： 中国银行智能POS项目 !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN070: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN072", resDialogTitle =
            "CASE_printScript_CN072", APIName = "printScript")
    public void CASE_printScript_CN072() {
        Log.i(TAG, "CASE_printScript_CN072");
        API07printScript("!hz n !asc n !gray -1 !yspace 6 !BOCFONT 1 1 3 *text c 商户名称： 中国银行智能POS项目 !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN072: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN073", resDialogTitle =
            "CASE_printScript_CN073", APIName = "printScript")
    public void CASE_printScript_CN073() {
        Log.i(TAG, "CASE_printScript_CN073");
        API07printScript("!hz n !asc n !gray !yspace 6 !BOCFONT 1 1 3 *text c 商户名称： 中国银行智能POS项目 !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN073: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN074", resDialogTitle =
            "CASE_printScript_CN074", APIName = "printScript")
    public void CASE_printScript_CN074() {
        Log.i(TAG, "CASE_printScript_CN074");
        API07printScript("!hz n !asc n !gray 5 !yspace -1 !BOCFONT 1 1 3 *text c 商户名称： 中国银行智能POS项目 !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN074: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN075", resDialogTitle =
            "CASE_printScript_CN075", APIName = "printScript")
    public void CASE_printScript_CN075() {
        Log.i(TAG, "CASE_printScript_CN075");
        API07printScript("!hz n !asc n !gray 5 !yspace 61 !BOCFONT 1 1 3 *text c 商户名称： 中国银行智能POS项目 !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN075: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN076", resDialogTitle =
            "CASE_printScript_CN076", APIName = "printScript")
    public void CASE_printScript_CN076() {
        Log.i(TAG, "CASE_printScript_CN076");
        API07printScript("!hz n !asc n !gray 5 !yspace !BOCFONT 1 1 3 *text c 商户名称： 中国银行智能POS项目 !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN076: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN077", resDialogTitle =
            "CASE_printScript_CN077", APIName = "printScript")
    public void CASE_printScript_CN077() {
        Log.i(TAG, "CASE_printScript_CN077");
        API07printScript("!gray 10 !barcode 0 64 *barcode c \"891110847565611252\" !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN077: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN078", resDialogTitle =
            "CASE_printScript_CN078", APIName = "printScript")
    public void CASE_printScript_CN078() {
        Log.i(TAG, "CASE_printScript_CN078");
        API07printScript("!gray 10 !barcode 9 64 *barcode c \"891110847565611252\" !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN078: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN079", resDialogTitle =
            "CASE_printScript_CN079", APIName = "printScript")
    public void CASE_printScript_CN079() {
        Log.i(TAG, "CASE_printScript_CN079");
        API07printScript("!gray 10 !barcode -1 64 *barcode c \"891110847565611252\" !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN079: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN080", resDialogTitle =
            "CASE_printScript_CN080", APIName = "printScript")
    public void CASE_printScript_CN080() {
        Log.i(TAG, "CASE_printScript_CN080");
        API07printScript("!gray 10 !barcode 2 1 *barcode c \"891110847565611252\" !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN080: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN081", resDialogTitle =
            "CASE_printScript_CN081", APIName = "printScript")
    public void CASE_printScript_CN081() {
        Log.i(TAG, "CASE_printScript_CN081");
        API07printScript("!gray 10 !barcode 2 0 *barcode c \"891110847565611252\" !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN081: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN082", resDialogTitle =
            "CASE_printScript_CN082", APIName = "printScript")
    public void CASE_printScript_CN082() {
        Log.i(TAG, "CASE_printScript_CN082");
        API07printScript("!gray 10 !barcode 2 321 *barcode c \"891110847565611252\" !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN082: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN083", resDialogTitle =
            "CASE_printScript_CN083", APIName = "printScript")
    public void CASE_printScript_CN083() {
        Log.i(TAG, "CASE_printScript_CN083");
        API07printScript("!gray 10 !barcode 2 -1 *barcode c \"891110847565611252\" !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN083: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN084", resDialogTitle =
            "CASE_printScript_CN084", APIName = "printScript")
    public void CASE_printScript_CN084() {
        Log.i(TAG, "CASE_printScript_CN084");
        API07printScript("!gray 10 !barcode *barcode c \"891110847565611252\" !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN084: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN085", resDialogTitle =
            "CASE_printScript_CN085", APIName = "printScript")
    public void CASE_printScript_CN085() {
        Log.i(TAG, "CASE_printScript_CN085");
        API07printScript("!gray 10 !qrcode 0 2 *qrcode c \"中国银行智能POS项目\" !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN085: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN086", resDialogTitle =
            "CASE_printScript_CN086", APIName = "printScript")
    public void CASE_printScript_CN086() {
        Log.i(TAG, "CASE_printScript_CN086");
        API07printScript("!gray 10 !qrcode 385 2 *qrcode c \"中国银行智能POS项目\" !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN086: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN087", resDialogTitle =
            "CASE_printScript_CN087", APIName = "printScript")
    public void CASE_printScript_CN087() {
        Log.i(TAG, "CASE_printScript_CN087");
        API07printScript("!gray 10 !qrcode -1 2 *qrcode c \"中国银行智能POS项目\" !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN087: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN088", resDialogTitle =
            "CASE_printScript_CN088", APIName = "printScript")
    public void CASE_printScript_CN088() {
        Log.i(TAG, "CASE_printScript_CN088");
        API07printScript("!gray 10 !qrcode 100 -1 *qrcode c \"中国银行智能POS项目\" !BOCPRNOVER ", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN088: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN089", resDialogTitle =
            "CASE_printScript_CN089", APIName = "printScript")
    public void CASE_printScript_CN089() {
        Log.i(TAG, "CASE_printScript_CN089");
        API07printScript("!gray 10 !qrcode 100 4 *qrcode c \"中国银行智能POS项目\" !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN089: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN090", resDialogTitle =
            "CASE_printScript_CN090", APIName = "printScript")
    public void CASE_printScript_CN090() {
        Log.i(TAG, "CASE_printScript_CN090");
        API07printScript("!gray 10 !qrcode *qrcode c \"中国银行智能POS项目\" !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN090: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN091", resDialogTitle =
            "CASE_printScript_CN091", APIName = "printScript")
    public void CASE_printScript_CN091() {
        Log.i(TAG, "CASE_printScript_CN091");
        API07printScript("!hz n !asc n !gray 10 !yspace 8 !BOCFONT 0 1 0 *text c \"中国银行智能POS项目\" !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN091: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN092", resDialogTitle =
            "CASE_printScript_CN092", APIName = "printScript")
    public void CASE_printScript_CN092() {
        Log.i(TAG, "CASE_printScript_CN092");
        API07printScript("!hz n !asc n !gray 10 !yspace 8 !BOCFONT 9 1 0 *text c \"中国银行智能POS项目\" !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN092: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN093", resDialogTitle =
            "CASE_printScript_CN093", APIName = "printScript")
    public void CASE_printScript_CN093() {
        Log.i(TAG, "CASE_printScript_CN093");
        API07printScript("!hz n !asc n !gray 10 !yspace 8 !BOCFONT -1 1 0 *text c \"中国银行智能POS项目\" !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN093: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN094", resDialogTitle =
            "CASE_printScript_CN094", APIName = "printScript")
    public void CASE_printScript_CN094() {
        Log.i(TAG, "CASE_printScript_CN094");
        API07printScript("!hz n !asc n !gray 10 !yspace 8 !BOCFONT 1 0 0 *text c \"中国银行智能POS项目\" !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN094: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN095", resDialogTitle =
            "CASE_printScript_CN095", APIName = "printScript")
    public void CASE_printScript_CN095() {
        Log.i(TAG, "CASE_printScript_CN095");
        API07printScript("!hz n !asc n !gray 10 !yspace 8 !BOCFONT 1 20 0 *text c \"中国银行智能POS项目\" !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN094: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN096", resDialogTitle =
            "CASE_printScript_CN096", APIName = "printScript")
    public void CASE_printScript_CN096() {
        Log.i(TAG, "CASE_printScript_CN096");
        API07printScript("!hz n !asc n !gray 10 !yspace 8 !BOCFONT 1 -1 0 *text c \"中国银行智能POS项目\" !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN096: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN097", resDialogTitle =
            "CASE_printScript_CN097", APIName = "printScript")
    public void CASE_printScript_CN097() {
        Log.i(TAG, "CASE_printScript_CN097");
        API07printScript("!hz n !asc n !gray 10 !yspace 8 !BOCFONT 1 1 -1 *text c \"中国银行智能POS项目\" !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN097: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN098", resDialogTitle =
            "CASE_printScript_CN098", APIName = "printScript")
    public void CASE_printScript_CN098() {
        Log.i(TAG, "CASE_printScript_CN098");
        API07printScript("!hz n !asc n !gray 10 !yspace 8 !BOCFONT 1 1 4 *text c \"中国银行智能POS项目\" !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN098: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN099", resDialogTitle =
            "CASE_printScript_CN099", APIName = "printScript")
    public void CASE_printScript_CN099() {
        Log.i(TAG, "CASE_printScript_CN099");
        API07printScript(" !gray 10 !yspace 8 !BOCFONT  *text c \"中国银行智能POS项目\" !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN099: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN100", resDialogTitle =
            "CASE_printScript_CN100", APIName = "printScript")
    public void CASE_printScript_CN100() {
        Log.i(TAG, "CASE_printScript_CN100");
        API07printScript("!hz n !asc n !gray 10 !yspace 8 !BOCFONT 1 1 0 *text c \"中国银行智能POS项目\" !BOCPRNOVER *text c \"正在测试\" !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN100: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN101", resDialogTitle =
            "CASE_printScript_CN101", APIName = "printScript")
    public void CASE_printScript_CN101() {
        Log.i(TAG, "CASE_printScript_CN101");
        API07printScript("!BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN101: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN102", resDialogTitle =
            "CASE_printScript_CN102", APIName = "printScript")
    public void CASE_printScript_CN102() {
        Log.i(TAG, "CASE_printScript_CN102");
        API07printScript("!hz n !asc n !gray 10 !yspace 8 !BOCFONT 1 1 0 *text a \"中国银行智能POS项目\" !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN102: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN103", resDialogTitle =
            "CASE_printScript_CN103", APIName = "printScript")
    public void CASE_printScript_CN103() {
        Log.i(TAG, "CASE_printScript_CN103");
        API07printScript("!hz n !asc n !gray 10 !yspace 8 !BOCFONT 1 1 0 *text null \"中国银行智能POS项目\" !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN103: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN104", resDialogTitle =
            "CASE_printScript_CN104", APIName = "printScript")
    public void CASE_printScript_CN104() {
        Log.i(TAG, "CASE_printScript_CN104");
        API07printScript("!hz n !asc n !gray 10 !yspace 8 !BOCFONT 1 1 0 *text c \"囧槑（mei）烎(yin)氼（ni）嘂（jiao）嘦（jiao）嫑（biao）圐（ku）圙（lue）玊（su）孖（ma）丼（jing）嬛(huan)\"甯(ning)寗(ning)\" !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN104: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN105", resDialogTitle =
            "CASE_printScript_CN105", APIName = "printScript")
    public void CASE_printScript_CN105() {
        Log.i(TAG, "CASE_printScript_CN105");
        API07printScript("!hz n !asc n !gray 10 !yspace 8 !BOCFONT 1 1 0 *text c \"~!@#$%^&*()_+{}|:\"<>?`-=[]\\;',./\" !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN105: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN106", resDialogTitle =
            "CASE_printScript_CN106", APIName = "printScript")
    public void CASE_printScript_CN106() {
        Log.i(TAG, "CASE_printScript_CN106");
        API07printScript("!hz n !asc n !gray 10 !yspace 8 !BOCFONT 1 1 0 *text c \"\" !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN106: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN107", resDialogTitle =
            "CASE_printScript_CN107", APIName = "printScript")
    public void CASE_printScript_CN107() {
        Log.i(TAG, "CASE_printScript_CN107");
        API07printScript("!hz n !asc n !gray 10 !yspace 8 !BOCFONT 1 1 0 *text c !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN107: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN108", resDialogTitle =
            "CASE_printScript_CN108", APIName = "printScript")
    public void CASE_printScript_CN108() {
        Log.i(TAG, "CASE_printScript_CN108");
        API07printScript("!feedline 0", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN108: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN109", resDialogTitle =
            "CASE_printScript_CN109", APIName = "printScript")
    public void CASE_printScript_CN109() {
        Log.i(TAG, "CASE_printScript_CN109");
        API07printScript("!barcode 2 64 *barcode a \"891110847565611252\" !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN109: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN110", resDialogTitle =
            "CASE_printScript_CN110", APIName = "printScript")
    public void CASE_printScript_CN110() {
        Log.i(TAG, "CASE_printScript_CN110");
        API07printScript("!barcode 2 64 *barcode null \"891110847565611252\" !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN110: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN111", resDialogTitle =
            "CASE_printScript_CN111", APIName = "printScript")
    public void CASE_printScript_CN111() {
        Log.i(TAG, "CASE_printScript_CN111");
        API07printScript("!barcode 2 64 *barcode c  !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN111: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN112", resDialogTitle =
            "CASE_printScript_CN112", APIName = "printScript")
    public void CASE_printScript_CN112() {
        Log.i(TAG, "CASE_printScript_CN112");
        API07printScript("!barcode 2 64 *barcode c  !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN112: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN113", resDialogTitle =
            "CASE_printScript_CN113", APIName = "printScript")
    public void CASE_printScript_CN113() {
        Log.i(TAG, "CASE_printScript_CN113");
        API07printScript("!qrcode 100 2 *qrcode a \"中国银行智能POS项目\" !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN113: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN114", resDialogTitle =
            "CASE_printScript_CN114", APIName = "printScript")
    public void CASE_printScript_CN114() {
        Log.i(TAG, "CASE_printScript_CN114");
        API07printScript("!qrcode 100 2 *qrcode null \"中国银行智能POS项目\" !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN114: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN115", resDialogTitle =
            "CASE_printScript_CN115", APIName = "printScript")
    public void CASE_printScript_CN115() {
        Log.i(TAG, "CASE_printScript_CN115");
        API07printScript("!qrcode 100 2 *qrcode c \"\" !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN115: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN116", resDialogTitle =
            "CASE_printScript_CN116", APIName = "printScript")
    public void CASE_printScript_CN116() {
        Log.i(TAG, "CASE_printScript_CN116");
        API07printScript("!qrcode 100 2 *qrcode c  !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN116: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN117", resDialogTitle =
            "CASE_printScript_CN117", APIName = "printScript")
    public void CASE_printScript_CN117() {
        Log.i(TAG, "CASE_printScript_CN117");
        HashMap localHashMap =new HashMap();
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.boclogo);
        localHashMap.put("logo", bitmap);
        API07printScript("*image a 320*89 path:logo !BOCPRNOVER", localHashMap, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN117: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }

    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN118", resDialogTitle =
            "CASE_printScript_CN118", APIName = "printScript")
    public void CASE_printScript_CN118() {
        Log.i(TAG, "CASE_printScript_CN118");
        HashMap localHashMap =new HashMap();
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.boclogo);
        localHashMap.put("logo", bitmap);
        API07printScript("*image null 320*89 path:logo !BOCPRNOVER", localHashMap, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN118: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN119", resDialogTitle =
            "CASE_printScript_CN119", APIName = "printScript")
    public void CASE_printScript_CN119() {
        Log.i(TAG, "CASE_printScript_CN119");
        HashMap localHashMap =new HashMap();
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.boclogo);
        localHashMap.put("logo", bitmap);
        API07printScript("*image c 0*0 path:logo !BOCPRNOVER", localHashMap, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN119: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN120", resDialogTitle =
            "CASE_printScript_CN120", APIName = "printScript")
    public void CASE_printScript_CN120() {
        Log.i(TAG, "CASE_printScript_CN120");
        HashMap localHashMap =new HashMap();
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.bitmap_5);
        localHashMap.put("logo", bitmap);
        API07printScript("*image c 100*100 path:logo !BOCPRNOVER", localHashMap, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN120: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN121", resDialogTitle =
            "CASE_printScript_CN121", APIName = "printScript")
    public void CASE_printScript_CN121() {
        Log.i(TAG, "CASE_printScript_CN121");
        HashMap localHashMap =new HashMap();
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.boclogo);
        localHashMap.put("logo", bitmap);
        API07printScript("*image c null path:logo !BOCPRNOVER", localHashMap, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN121: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN122", resDialogTitle =
            "CASE_printScript_CN122", APIName = "printScript")
    public void CASE_printScript_CN122() {
        Log.i(TAG, "CASE_printScript_CN122");
        HashMap localHashMap =new HashMap();
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.boclogo);
        localHashMap.put("logo", bitmap);
        API07printScript("*image c 500*500 path:logo !BOCPRNOVER", localHashMap, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN122: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN123", resDialogTitle =
            "CASE_printScript_CN123", APIName = "printScript")
    public void CASE_printScript_CN123() {
        Log.i(TAG, "CASE_printScript_CN123");
        HashMap localHashMap =new HashMap();
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.drawable.boclogo);
        localHashMap.put("logo", bitmap);
        API07printScript("*image c 320*89 path:logo !BOCPRNOVER", null, 30, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN123: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN124", resDialogTitle =
            "CASE_printScript_CN124", APIName = "printScript")
    public void CASE_printScript_CN124() {
        Log.i(TAG, "CASE_printScript_CN124");
        API07printScript("!hz n !asc n !gray 10 !yspace 8 !BOCFONT 1 1 0 *text c \"中国银行智能POS项目\" !BOCPRNOVER", null, 1, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN124: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN125", resDialogTitle =
            "CASE_printScript_CN125", APIName = "printScript")
    public void CASE_printScript_CN125() {
        Log.i(TAG, "CASE_printScript_CN125");
        API07printScript("!hz n !asc n !gray 10 !yspace 8 !BOCFONT 1 1 0 *text c \"中国银行智能POS项目\" !BOCPRNOVER", null, 0, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN125: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN126", resDialogTitle =
            "CASE_printScript_CN126", APIName = "printScript")
    public void CASE_printScript_CN126() {
        Log.i(TAG, "CASE_printScript_CN126");
        API07printScript("!hz n !asc n !gray 10 !yspace 8 !BOCFONT 1 1 0 *text c \"中国银行智能POS项目\" !BOCPRNOVER", null, -1, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN126: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN127", resDialogTitle =
            "CASE_printScript_CN127", APIName = "printScript")
    public void CASE_printScript_CN127() {
        Log.i(TAG, "CASE_printScript_CN127");
        API07printScript("!hz n !asc n !gray 10 !yspace 8 !BOCFONT 1 1 0 *text c \"中国银行智能POS项目\" !BOCPRNOVER", null, Integer.MAX_VALUE, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN127: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN128", resDialogTitle =
            "CASE_printScript_CN128", APIName = "printScript")
    public void CASE_printScript_CN128() {
        Log.i(TAG, "CASE_printScript_CN128");
        API07printScript("!hz n !asc n !gray 10 !yspace 8 !BOCFONT 1 1 0 *text c \"中国银行智能POS项目\" !BOCPRNOVER", null, Integer.MIN_VALUE, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {
                Log.i(TAG, "CASE_printScript_CN128: "+"code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
    }
    @TestDetailCaseAnnotation(id = 0, itemDetailName = "CASE_printScript_CN129", resDialogTitle =
            "CASE_printScript_CN129", APIName = "printScript")
    public void CASE_printScript_CN129() {
        Log.i(TAG, "CASE_printScript_CN129");
        API07printScript("!hz n !asc n !gray 10 !yspace 8 !BOCFONT 1 1 0 *text c \"中国银行智能POS项目\" !BOCPRNOVER", null, 60, null);
    }*/
}