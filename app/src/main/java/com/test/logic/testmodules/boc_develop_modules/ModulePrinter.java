package com.test.logic.testmodules.boc_develop_modules;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.RemoteException;

import com.boc.aidl.constant.Const;
import com.boc.aidl.printer.AidlPrinter;
import com.boc.aidl.printer.AidlPrinterListener;
import com.socsi.smartposapi.printer.Align;
import com.socsi.smartposapi.printer.FontLattice;
import com.socsi.smartposapi.printer.Printer2;
import com.socsi.smartposapi.printer.TextEntity;
import com.test.logic.testmodules.boc_develop_modules.common.DeviceService;
import com.test.ui.activities.MyApplication;
import com.test.ui.activities.R;
import com.test.logic.testmodules.basemodule.BaseModule;
import com.test.util.DialogUtil;
import com.test.util.LogUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CuncheW1 on 2017/3/1.
 */

public class ModulePrinter extends BaseModule {
    AidlPrinter iPrinter;
    private final String TAG = "ModulePrinter";

    public ModulePrinter() {
        this.iPrinter = DeviceService.getPrinter();
    }

    public void API01printBarCode(String barCode,int width, int height, int position, int timeout, AidlPrinterListener listener) {
        try {
            iPrinter.printBarCode(barCode, width, height, position, timeout, listener);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void API02print(String json, Bitmap[] bitmap,  AidlPrinterListener listener) {
        try {
            iPrinter.print(json, bitmap, listener);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void API03paperSkip(int line) {
        try {
            iPrinter.paperSkip(line);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void API04printQrCode(String qrCode, int height, int errorCorrectingLevel, int position, int timeout, AidlPrinterListener listener) {
        try {
            iPrinter.printQrCode(qrCode, height, errorCorrectingLevel, position, timeout, listener);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void API05_printScript(String data, Map map, int timeout, AidlPrinterListener listener) {
        try {
            iPrinter.printScript(data, map, timeout, listener);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void API06_printBitMap(int offset, Bitmap bitmap, int timeout, AidlPrinterListener listener) {
        try {
            iPrinter.printBitMap(offset, bitmap, timeout, listener);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
public boolean CASE_takerPaper_CN002() {
        AidlPrinterListener listener = new AidlPrinterListener.Stub() {
            @Override
            public void onError(int errorCode, String detail) throws RemoteException {
                LogUtil.d(TAG, "onError, detail" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {
                LogUtil.d(TAG, "onFinish");
            }
        };

        API04printQrCode("123", 60, 2, Const.PrintContentPosition.CENTER, 60, listener);
        API03paperSkip(6);
        API01printBarCode("123", 4, 72, Const.PrintContentPosition.LEFT, 60, listener);
        API03paperSkip(6);

        return true;
    }

    public boolean CASE_takerPaper_CN001() {
        AidlPrinterListener listener = new AidlPrinterListener.Stub() {
            @Override
            public void onError(int errorCode, String detail) throws RemoteException {
                LogUtil.d(TAG, "onError, detail" + detail);
                DialogUtil.showConfirmDialog("打印错误", detail, null);
            }

            @Override
            public void onFinish() throws RemoteException {
                LogUtil.d(TAG, "onFinish");
                DialogUtil.showConfirmDialog("", "恭喜打印完成", null);
            }
        };

        API04printQrCode("123", 60, 2, Const.PrintContentPosition.CENTER, 60, listener);
        API03paperSkip(3);
        API04printQrCode("123", 60, 2, Const.PrintContentPosition.CENTER, 60, listener);
        API03paperSkip(-1);
        API04printQrCode("123", 60, 2, Const.PrintContentPosition.CENTER, 60, listener);
        API03paperSkip(-14324);
        API04printQrCode("123", 60, 2, Const.PrintContentPosition.CENTER, 60, listener);
       // API01printBarCode("123", 4, 72, Const.PrintContentPosition.LEFT, 60, listener);

        return true;
    }

    public boolean CASE_printBarCode_CN001() {
        API01printBarCode("2016-12-01 20:27:42", 3, 72, 2, 1, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {

            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        return true;
    }

    public boolean CASE_print_CN001() {
        Bitmap localBitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.mipmap.boclogo);
        Bitmap[] bitmaps = new Bitmap[5];
        bitmaps[0] = localBitmap;

        API02print("{'spos':[{'content-type':'txt','content':'\n\n','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'小票测试','position':'center','bold':'0','height':'-1','size':'3'},{'content-type':'jpg','content':'','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'持卡人存根','position':'right','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'商户：测试测试测试测试','position':'left','bold':'0','height':'-1','size':'3'},{'content-type':'txt','content':'    商户编号：812002110030001','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    终端编号：20150909','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    操作员号：001','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'交易：扫一扫支付','position':'left','bold':'0','height':'-1','size':'3'},{'content-type':'txt','content':'    支付渠道：微信支付','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    日期时间：2016\\/04\\/28  15:27:51','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    参考号：000014103147','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'one-dimension','content':'12312312312424','position':'center','bold':'0','height':'1','size':'3'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'金额：RMB  0.01','position':'left','bold':'1','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'备注：','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'本人确认以上交易，同意将其计入本卡账户 ','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'I ACKNOWLEDGE SATISFACTORY RECEIPT OF RELATIVE GOODS\\/SERVICE','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'two-dimension','content':'头，辛苦啦','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'\n\n\n\n','position':'center','bold':'0','height':'-1','size':'2'}]}", bitmaps, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                getResultTv().showInfoInNewLine("code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });

        return true;
    }

    public boolean CASE_print_CN002() {
        API02print("{'spos':[{'content-type':'txt','content':'\n\n','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'小票测试','position':'center','bold':'0','height':'-1','size':'3'},{'content_type':'jpg','content':'','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'持卡人存根','position':'right','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'商户：测试测试测试测试','position':'left','bold':'0','height':'-1','size':'3'},{'content-type':'txt','content':'    商户编号：812002110030001','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    终端编号：20150909','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    操作员号：001','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'交易：扫一扫支付','position':'left','bold':'0','height':'-1','size':'3'},{'content-type':'txt','content':'    支付渠道：微信支付','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    日期时间：2016\\/04\\/28  15:27:51','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'    参考号：000014103147','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'one-dimension','content':'12312312312424','position':'center','bold':'0','height':'-1','size':'3'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'金额：RMB  0.01','position':'left','bold':'1','height':'-1','size':'2'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'备注：','position':'left','bold':'0','height':'-1','size':'2'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'','position':'left','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'本人确认以上交易，同意将其计入本卡账户 ','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'I ACKNOWLEDGE SATISFACTORY RECEIPT OF RELATIVE GOODS\\/SERVICE','position':'center','bold':'0','height':'-1','size':'1'},{'content-type':'txt','content':'------------------------------------------------','position':'center','bold':'0','height':'-1','size':'2'},{'content-type':'two-dimension','content':'头，辛苦啦','position':'center','bold':'0','height':'-1','size':'200'},{'content-type':'txt','content':'\n\n\n\n','position':'center','bold':'0','height':'-1','size':'2'}]}", null, new AidlPrinterListener.Stub() {


            @Override
            public void onError(int code, String detail) throws RemoteException {
                getResultTv().showInfoInNewLine("code=" + code + " detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });

        return true;
    }

    public boolean CASE_paperSkip_CN001() {
        API03paperSkip(2);
        return true;
    }

    public boolean CASE_paperSkip_CN002() {
        API03paperSkip(0);
        return true;
    }

    public boolean CASE_paperSkip_CN003() {
        API03paperSkip(51);
        return true;
    }

    public boolean CASE_printQrCode_CN001() {
        API04printQrCode("头，辛苦啦～", 300, 2, 2, 20, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int code, String detail) throws RemoteException {

            }

            @Override
            public void onFinish() throws RemoteException {

            }
        });
        return true;
    }

    public boolean CASE_printScript_CN000() {
        TextEntity textAddNewLine = new TextEntity("1111111111111111", FontLattice.SIXTEEN, false, Align.LEFT);
        TextEntity textAddNoNewLine = new TextEntity("1111111111111111", FontLattice.SIXTEEN, false, Align.LEFT);
        textAddNoNewLine.newline = false;
        TextEntity line = new TextEntity("\n", FontLattice.SIXTEEN, false, Align.LEFT);
        line.newline = false;

        Printer2.getInstance().appendTextEntity2(textAddNewLine);
        Printer2.getInstance().appendTextEntity2(line);
        Printer2.getInstance().appendTextEntity2(textAddNoNewLine);
        Printer2.getInstance().appendQrCode(60, 60, 0,"123");
        Printer2.getInstance().appendTextEntity2(line);
        Printer2.getInstance().appendQrCode(60, 60, 0,"123");
        Printer2.getInstance().appendTextEntity2(textAddNewLine);
        Printer2.getInstance().startPrint();


        return true;
    }
    public boolean CASE_printScript_CN001() {
        StringBuffer localStringBuffer = new StringBuffer();
        HashMap localHashMap = new HashMap();
        localHashMap.put("logo", BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.mipmap.boclogo));
        localStringBuffer.append("!hz n\n !asc n\n");
        localStringBuffer.append("!yspace 6\n");
        localStringBuffer.append("*image l 320*89 path:");
        localStringBuffer.append("logo\n");
        localStringBuffer.append("!hz n\n !asc n\n");
        localStringBuffer.append("*line\n");
        localStringBuffer.append("*text l 商户存根/MERCHANT COPY\n");
        localStringBuffer.append("*line\n");
        localStringBuffer.append("*text l 商户名称(MERCHANT NAME):\n");
        localStringBuffer.append("*text l   比亚迪1\n");
        localStringBuffer.append("*text l 商户编号(MERCHANT NO.):\n");
        localStringBuffer.append("*text l   123455432112345\n");
        localStringBuffer.append("*text l 终端编号(TERMINAL NO.):20130717\n");
        localStringBuffer.append("*text l 操作员(OPERATOR NO.):20130717\n");
        localStringBuffer.append("*line\n");
        localStringBuffer.append("*text l 发卡行:工商银行\n");
        localStringBuffer.append("*text l 卡号:\n");
        localStringBuffer.append("!hz l\n !asc l\n");
        localStringBuffer.append("*text l 621226*********1973\n");
        localStringBuffer.append("!hz n\n !asc n\n");
        localStringBuffer.append("*text l 交易类型:\n");
        localStringBuffer.append("*text l 消费/SALE\n");
        localStringBuffer.append("*text l 凭证号:000014\n");
        localStringBuffer.append("*text l 参考号:000029279932\n");
        localStringBuffer.append("*text l 授权码:867785\n");
        localStringBuffer.append("*text l 批次号:000001\n");
        localStringBuffer.append("*text l 票据号:000001\n");
        localStringBuffer.append("*text l 日期时间:16:31\n");
        localStringBuffer.append("*text l 卡 组 织:内卡\n");
        localStringBuffer.append("*text l 金 额:\n");
        localStringBuffer.append("!hz l\n !asc l\n");
        localStringBuffer.append("*text l RMB 1.16\n");
        localStringBuffer.append("!yspace 2\n");
        localStringBuffer.append("!hz n\n !asc n\n");
        localStringBuffer.append("*line\n");
        localStringBuffer.append("!hz l\n !asc l\n");
        localStringBuffer.append("*text l 备注:\n");
        localStringBuffer.append("!yspace 2\n");
        localStringBuffer.append("!hz s\n !asc s\n");
        localStringBuffer.append("*text l ARQC:83FE10558FF2C85F\n");
        localStringBuffer.append("*text l AID:A000000333010101\n");
        localStringBuffer.append("*text l CSN:001 CVM:020301\n");
        localStringBuffer.append("*line\n");
        localStringBuffer.append("*text l 持卡人签名:\n");
        localStringBuffer.append("*feedline 5\n");
        localStringBuffer.append("*text l 本人确认以上交易，同意将其记入本卡账户\n");
        localStringBuffer.append("*text l 服务热线:888888888\n");
        localStringBuffer.append("*text l  - - - - - - - X - - - - - - - X - - - - - - - \n");
        API05_printScript(localStringBuffer.toString(), localHashMap, 60, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int errorCode, String detail) throws RemoteException {
                DialogUtil.showConfirmDialog("打印错误", detail, null);
            }

            @Override
            public void onFinish() throws RemoteException {
                DialogUtil.showConfirmDialog("", "恭喜打印完成", null);
            }
        });

        return true;
    }

    public boolean CASE_printScript_CN002() {
        StringBuffer localStringBuffer = new StringBuffer();
        HashMap localHashMap = new HashMap();
        localHashMap.put("logo", BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.mipmap.boclogo));
        localStringBuffer.append("!BOCFONT 1 5 3\n");
        localStringBuffer.append("*text r aaaabbbbccccdddd\n");
        localStringBuffer.append("*text r aaaabbbbccccdddd\n");
        localStringBuffer.append("!BOCFONT 1 6 3\n");
        localStringBuffer.append("*barcode l fdasdfa\n");
        localStringBuffer.append("*barcode c fdasdfa\n");
        localStringBuffer.append("*barcode r fdasdfa\n");
        localStringBuffer.append("!barcode 2 -1\n");
        localStringBuffer.append("*barcode c ABCabc-$.891110847565611252\n");
        localStringBuffer.append("!BOCFONT 1 6 3\n");
        localStringBuffer.append("*text l aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeee\n");
        localStringBuffer.append("!barcode 2 -1\n");
        localStringBuffer.append("*barcode l 1\n");
        localStringBuffer.append("*barcode l a\n");
        localStringBuffer.append("*barcode l A\n");
        localStringBuffer.append("*barcode l 1111111111\n");
        localStringBuffer.append("*barcode l aaaaaaaaaa\n");
        localStringBuffer.append("*barcode l AAAAAAAAAA\n");

        localStringBuffer.append("!qrcode 60 2\n");
        localStringBuffer.append("*qrcode r fdasdfa\n");
        localStringBuffer.append("*line\n");
        localStringBuffer.append("*qrcode r fdasdfa\n");
        localStringBuffer.append("*feedline 1\n");
        localStringBuffer.append("*qrcode r fdasdfa\n");
        localStringBuffer.append("*feedline 2\n");
        localStringBuffer.append("*qrcode r fdasdfa\n");
        localStringBuffer.append("*text r aaaabbbbccccdddd\n");
        localStringBuffer.append("*feedline 2\n");
        localStringBuffer.append("*text r 商\r户\r存\r\r根\n/MERCHANT COPY\n\n\n");
        localStringBuffer.append("*line\n");
        localStringBuffer.append("*text r 商户名称(MERCHANT NAME):\n");
        localStringBuffer.append("*image c 320*89 path:logo\n");
        localStringBuffer.append("*feedline 2\n");
        API05_printScript(localStringBuffer.toString(), localHashMap, 60, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int errorCode, String detail) throws RemoteException {
                DialogUtil.showConfirmDialog("打印错误", detail, null);
            }

            @Override
            public void onFinish() throws RemoteException {
                DialogUtil.showConfirmDialog("", "恭喜打印完成", null);
            }
        });

        return true;
    }
    public boolean CASE_printScript_CN003() {
        StringBuffer localStringBuffer = new StringBuffer();
        HashMap localHashMap = new HashMap();
        localHashMap.put("logo26", BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.mipmap.boclogo));
        localStringBuffer.append("!BOCFONT 1 12 3\n");
        localStringBuffer.append("!yspace 3\n");
        localStringBuffer.append("*image c 320*89 path:logo26\n");
        localStringBuffer.append("!BOCFONT 1 12 2\n");
        localStringBuffer.append("!yspace 3\n");
        localStringBuffer.append("!BOCFONT 6 6 3\n");
        localStringBuffer.append("*text l ------------------------------------------------\n");
        localStringBuffer.append("*text l 商户存根 MERCHANT COPY\n");
        localStringBuffer.append("*text l ------------------------------------------------\n");
        localStringBuffer.append("!BOCFONT 1 12 3\n");
        localStringBuffer.append("*text l 商户名称(MERCHANT NAME):\n");
        localStringBuffer.append("*text l IC 卡测试\n");
        localStringBuffer.append("*text l 商户编号(MERCHANT NO.):\n");
        localStringBuffer.append("*text l 104650053110011\n");
        localStringBuffer.append("!BOCFONT 1 12 3\n");
        localStringBuffer.append("*text l 终端编号(TERMINAL NO.):65010022\n");
        localStringBuffer.append("*text l 操作员号(OPERATOR NO.):001\n");
        localStringBuffer.append("!BOCFONT 6 6 3\n");
        localStringBuffer.append("*text l ------------------------------------------------\n");
        localStringBuffer.append("!BOCFONT 1 12 3\n");
        localStringBuffer.append("*text l 发 卡 行(ISSUER):中行借记卡\n");
        localStringBuffer.append("*text l 卡 别(CARD TYPE):中行借记卡\n");
        localStringBuffer.append("*text l 卡 号(CARD NO.):\n");
        localStringBuffer.append("!BOCFONT 6 3 2\n");
        localStringBuffer.append("*text l 621669*********9200 /C\n");
        localStringBuffer.append("!BOCFONT 1 12 3\n");
        localStringBuffer.append("*text l 有 效 期(EXP DATE):2027/05\n");
        localStringBuffer.append("*text l 卡序列号(SEQUENCE NO.):001\n");
        localStringBuffer.append("*text l 交易类型(TRANS TYPE):\n");
        localStringBuffer.append("!BOCFONT 1 12 2\n");
        localStringBuffer.append("*text l 消费/SALE\n");
        localStringBuffer.append("!BOCFONT 1 12 3\n");
        localStringBuffer.append("*text l 批次号(BATCH NO.):110609\n");
        localStringBuffer.append("*text l 凭证号(VOUCHER NO.):000323\n");
        localStringBuffer.append("*text l 票据号(INVOICE NO.):000039\n");
        localStringBuffer.append("*text l 授权码(AUTH NO.):250983\n");
        localStringBuffer.append("*text l 参考号(REFER NO.):716099275007\n");
        localStringBuffer.append("*text l 日期时间(DATE/TIME):\n");
        localStringBuffer.append("!BOCFONT 1 12 3\n");
        localStringBuffer.append("*text r 2017/06/09 11:12:12\n");
        localStringBuffer.append("!BOCFONT 1 12 3\n");
        localStringBuffer.append("*text l 金 额(AMOUNT):\n");
        localStringBuffer.append("!BOCFONT 6 3 2\n");
        localStringBuffer.append("*text l RMB: 0.01\n");
        localStringBuffer.append("!BOCFONT 1 12 3\n");
        localStringBuffer.append("!BOCFONT 1 12 3\n");
        localStringBuffer.append("!BOCFONT 1 12 3\n");
        localStringBuffer.append("!BOCFONT 6 3 2\n");
        localStringBuffer.append("!BOCFONT 1 12 3\n");
        localStringBuffer.append("!BOCFONT 6 3 2\n");
        localStringBuffer.append("!BOCFONT 6 6 3\n");
        localStringBuffer.append("*text l ------------------------------------------------\n");
        localStringBuffer.append("!BOCFONT 1 12 3\n");
        localStringBuffer.append("*text l 备注(REFERENCE):\n");
        localStringBuffer.append("!BOCFONT 6 1 3\n");
        localStringBuffer.append("*text l ARQC:A1DC9A71F3AC5F48\n");
        localStringBuffer.append("*text l AID:A000000333010101\n");
        localStringBuffer.append("*text l CSN:001 CVM:020300\n");
        localStringBuffer.append("*text l TSI:F800 TVR:00A004E000\n");
        localStringBuffer.append("*text l ATC:00BF UNPR NO:38E00577\n");
        localStringBuffer.append("*text l AIP:7C00 TermCap:E0F9C8\n");
        localStringBuffer.append("*text l IAD:0702010360A802010A0100000000001BC3F086\n");
        localStringBuffer.append("*text l APPLAB:PBOC DEBIT\n");
        localStringBuffer.append("*text l APPNAME:PBOC DEBIT\n");
        localStringBuffer.append("*text l 卡产品标识(Card Product ID):30313034353830300000000000006000\n");
        localStringBuffer.append("!BOCFONT 6 6 3\n");
        localStringBuffer.append("*text l ------------------------------------------------\n");
        localStringBuffer.append("!BOCFONT 6 1 3\n");
        localStringBuffer.append("*text l 持卡人签名(CARD HOLDER SIGNATURE):\n");
        localStringBuffer.append("*text l\n");
        localStringBuffer.append("*text l\n");
        localStringBuffer.append("*text l\n");
        localStringBuffer.append("*text l\n");
        localStringBuffer.append("*text l 本人确认以上交易，同意将其记入本卡账户\n");
        localStringBuffer.append("*text l I ACKNOWLEDGE SATISFACTORY RECEIPT OF RELATIVE GOODS/SERVICES\n");
        localStringBuffer.append("*text l 服务热线(HOTLINE):95566\n");
        localStringBuffer.append("!BOCFONT 6 6 3\n");
        localStringBuffer.append("*text l - - - - - - - X - - - - - - - X - - - - - - -\n");
        localStringBuffer.append("!BOCPRNOVER\n");
        API05_printScript(localStringBuffer.toString(), localHashMap, 60, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int errorCode, String detail) throws RemoteException {
                DialogUtil.showConfirmDialog("打印错误", detail, null);
            }

            @Override
            public void onFinish() throws RemoteException {
                DialogUtil.showConfirmDialog("", "恭喜打印完成", null);
            }
        });

        return true;
    }

    public boolean CASE_printScript_CN004() {
        StringBuffer localStringBuffer = new StringBuffer();
        HashMap localHashMap = new HashMap();
        localHashMap.put("logo26", BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.mipmap.test2));
        localStringBuffer.append("!BOCFONT 1 12 3\n");
        localStringBuffer.append("!yspace 3\n");
        localStringBuffer.append("*image c 384*89 path:logo26\n");
       API05_printScript(localStringBuffer.toString(), localHashMap, 60, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int errorCode, String detail) throws RemoteException {
                DialogUtil.showConfirmDialog("打印错误", detail, null);
            }

            @Override
            public void onFinish() throws RemoteException {
                DialogUtil.showConfirmDialog("", "恭喜打印完成", null);
            }
        });

        return true;
    }

    public boolean CASE_printScript_CN005() {
        StringBuffer localStringBuffer = new StringBuffer();
        HashMap localHashMap = new HashMap();
        localHashMap.put("logo26", BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.mipmap.boclogo));
        localStringBuffer.append("!BOCFONT 1 12 3\n");
        localStringBuffer.append("!yspace 3\n");
        localStringBuffer.append("*image c 320*89 path:logo26\n");
        localStringBuffer.append("!BOCFONT 1 12 2\n");
        localStringBuffer.append("!yspace 3\n");
        localStringBuffer.append("!BOCFONT 6 6 3\n");
        localStringBuffer.append("*text l ------------------------------------------------\n");
        localStringBuffer.append("*text l 商户存根 MERCHANT COPY\n");
        localStringBuffer.append("*text l ------------------------------------------------\n");
        localStringBuffer.append("!BOCFONT 1 12 3\n");
        localStringBuffer.append("*text l 商户名称(MERCHANT NAME):\n");
        localStringBuffer.append("*text l IC 卡测试\n");
        localStringBuffer.append("*text l 商户编号(MERCHANT NO.):\n");
        localStringBuffer.append("*text l 104650053110011\n");
        localStringBuffer.append("!BOCFONT 1 12 3\n");
        localStringBuffer.append("*text l 终端编号(TERMINAL NO.):65010022\n");
        localStringBuffer.append("*text l 操作员号(OPERATOR NO.):001\n");
        localStringBuffer.append("!BOCFONT 6 6 3\n");
        localStringBuffer.append("*text l ------------------------------------------------\n");
        localStringBuffer.append("!yspace 6\n");
        localStringBuffer.append("!gray 9\n");
        localStringBuffer.append("!BOCFONT 1 12 3\n");
        localStringBuffer.append("*text l 发 卡 行(ISSUER):中行借记卡\n");
        localStringBuffer.append("*text l 卡 别(CARD TYPE):中行借记卡\n");
        localStringBuffer.append("*text l 卡 号(CARD NO.):\n");
        localStringBuffer.append("!BOCFONT 6 3 2\n");
        localStringBuffer.append("*text l 621669*********9200 /C\n");
        localStringBuffer.append("!BOCFONT 1 12 3\n");
        localStringBuffer.append("!yspace 3\n");
        localStringBuffer.append("!gray 5\n");
        localStringBuffer.append("*text l 有 效 期(EXP DATE):2027/05\n");
        localStringBuffer.append("*text l 卡序列号(SEQUENCE NO.):001\n");
        localStringBuffer.append("*text l 交易类型(TRANS TYPE):\n");
        localStringBuffer.append("!BOCFONT 1 12 2\n");
        localStringBuffer.append("*text l 消费/SALE\n");
        localStringBuffer.append("!BOCFONT 1 12 3\n");
        localStringBuffer.append("*text l 批次号(BATCH NO.):110609\n");
        localStringBuffer.append("*text l 凭证号(VOUCHER NO.):000323\n");
        localStringBuffer.append("*text l 票据号(INVOICE NO.):000039\n");
        localStringBuffer.append("!yspace 10\n");
        localStringBuffer.append("!gray 8\n");
        localStringBuffer.append("*text l 授权码(AUTH NO.):250983\n");
        localStringBuffer.append("*text l 参考号(REFER NO.):716099275007\n");
        localStringBuffer.append("*text l 日期时间(DATE/TIME):\n");
        localStringBuffer.append("!BOCFONT 1 12 3\n");
        localStringBuffer.append("*text r 2017/06/09 11:12:12\n");
        localStringBuffer.append("!BOCFONT 1 12 3\n");
        localStringBuffer.append("*text l 金 额(AMOUNT):\n");
        localStringBuffer.append("!gray 5\n");
        localStringBuffer.append("!yspace 3\n");
        localStringBuffer.append("!BOCFONT 6 3 2\n");
        localStringBuffer.append("*text l RMB: 0.01\n");
        localStringBuffer.append("!BOCFONT 1 12 3\n");
        localStringBuffer.append("!BOCFONT 1 12 3\n");
        localStringBuffer.append("!BOCFONT 1 12 3\n");
        localStringBuffer.append("!BOCFONT 6 3 2\n");
        localStringBuffer.append("!BOCFONT 1 12 3\n");
        localStringBuffer.append("!BOCFONT 6 3 2\n");
        localStringBuffer.append("!BOCFONT 6 6 3\n");
        localStringBuffer.append("*text l ------------------------------------------------\n");
        localStringBuffer.append("!BOCFONT 1 12 3\n");
        localStringBuffer.append("*text l 备注(REFERENCE):\n");
        localStringBuffer.append("!BOCFONT 6 1 3\n");
        localStringBuffer.append("*text l ARQC:A1DC9A71F3AC5F48\n");
        localStringBuffer.append("*text l AID:A000000333010101\n");
        localStringBuffer.append("!yspace 13\n");
        localStringBuffer.append("*text l CSN:001 CVM:020300\n");
        localStringBuffer.append("*text l TSI:F800 TVR:00A004E000\n");
        localStringBuffer.append("*text l ATC:00BF UNPR NO:38E00577\n");
        localStringBuffer.append("*text l AIP:7C00 TermCap:E0F9C8\n");
        localStringBuffer.append("*text l IAD:0702010360A802010A0100000000001BC3F086\n");
        localStringBuffer.append("*text l APPLAB:PBOC DEBIT\n");
        localStringBuffer.append("*text l APPNAME:PBOC DEBIT\n");
        localStringBuffer.append("*text l 卡产品标识(Card Product ID):30313034353830300000000000006000\n");
        localStringBuffer.append("!BOCFONT 6 6 3\n");
        localStringBuffer.append("*text l ------------------------------------------------\n");
        localStringBuffer.append("!BOCFONT 6 1 3\n");
        localStringBuffer.append("*text l 持卡人签名(CARD HOLDER SIGNATURE):\n");
        localStringBuffer.append("*text l\n");
        localStringBuffer.append("*text l\n");
        localStringBuffer.append("*text l\n");
        localStringBuffer.append("*text l\n");
        localStringBuffer.append("*text l 本人确认以上交易，同意将其记入本卡账户\n");
        localStringBuffer.append("*text l I ACKNOWLEDGE SATISFACTORY RECEIPT OF RELATIVE GOODS/SERVICES\n");
        localStringBuffer.append("*text l 服务热线(HOTLINE):95566\n");
        localStringBuffer.append("!BOCFONT 6 6 3\n");
        localStringBuffer.append("*text l - - - - - - - X - - - - - - - X - - - - - - -\n");
        localStringBuffer.append("!barcode 3 72\n");
        localStringBuffer.append("*barcode l 123456\n");
        localStringBuffer.append("!yspace 2\n");
        localStringBuffer.append("*text l 卡产品标识(Card Product ID):30313034353830300000000000006000\n");
        localStringBuffer.append("*barcode c 123456\n");
        localStringBuffer.append("*barcode r 123456\n");
        localStringBuffer.append("!qrcode 100 3\n");
        localStringBuffer.append("*qrcode l 123\n");
        localStringBuffer.append("*qrcode c 123\n");
        localStringBuffer.append("*qrcode r 123\n");
        localStringBuffer.append("!BOCPRNOVER\n");
        API05_printScript(localStringBuffer.toString(), localHashMap, 60, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int errorCode, String detail) throws RemoteException {
                DialogUtil.showConfirmDialog("打印错误", detail, null);
            }

            @Override
            public void onFinish() throws RemoteException {
                DialogUtil.showConfirmDialog("", "恭喜打印完成", null);
            }
        });

        return true;
    }

    public boolean CASE_printScript_CN_yspace() {
        StringBuffer localStringBuffer = new StringBuffer();
        HashMap localHashMap = new HashMap();
        localStringBuffer.append("!BOCFONT 1 12 3\n");
        localStringBuffer.append("!yspace 3\n");
        localStringBuffer.append("*text l 11111111111111111111111111");
        localStringBuffer.append("*text l 11111111111111111111111111");
        localStringBuffer.append("*text l 11111111111111111111111111");
        localStringBuffer.append("*text l 11111111111111111111111111");
        localStringBuffer.append("!yspace 60\n");
        localStringBuffer.append("*text l 11111111111111111111111111");
        localStringBuffer.append("*text l 11111111111111111111111111");
        localStringBuffer.append("*text l 11111111111111111111111111");
        localStringBuffer.append("*text l 11111111111111111111111111");
        localStringBuffer.append("*text l 11111111111111111111111111");
        localStringBuffer.append("!BOCFONT 6 6 3\n");
        API05_printScript(localStringBuffer.toString(), localHashMap, 60, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int errorCode, String detail) throws RemoteException {
                DialogUtil.showConfirmDialog("打印错误", detail, null);
            }

            @Override
            public void onFinish() throws RemoteException {
                DialogUtil.showConfirmDialog("", "恭喜打印完成", null);
            }
        });

        return true;
    }

    public boolean CASE_printScript_CN_gray() {
        StringBuffer localStringBuffer = new StringBuffer();
        HashMap localHashMap = new HashMap();
        localStringBuffer.append("!BOCFONT 1 12 3\n");
        localStringBuffer.append("!gray 1\n");
        localStringBuffer.append("*text l 11111111111111111111111111");
        localStringBuffer.append("*text l 11111111111111111111111111");
        localStringBuffer.append("*text l --------------------------");
        localStringBuffer.append("!gray 2\n");
        localStringBuffer.append("*text l 11111111111111111111111111");
        localStringBuffer.append("*text l 11111111111111111111111111");
        localStringBuffer.append("*text l --------------------------");
        localStringBuffer.append("!gray 3\n");
        localStringBuffer.append("*text l 11111111111111111111111111");
        localStringBuffer.append("*text l 11111111111111111111111111");
        localStringBuffer.append("*text l --------------------------");
        localStringBuffer.append("!gray 4\n");
        localStringBuffer.append("*text l 11111111111111111111111111");
        localStringBuffer.append("*text l 11111111111111111111111111");
        localStringBuffer.append("*text l --------------------------");
        localStringBuffer.append("!gray 5\n");
        localStringBuffer.append("*text l 11111111111111111111111111");
        localStringBuffer.append("*text l 11111111111111111111111111");
        localStringBuffer.append("*text l --------------------------");
        localStringBuffer.append("!gray 6\n");
        localStringBuffer.append("*text l 11111111111111111111111111");
        localStringBuffer.append("*text l 11111111111111111111111111");
        localStringBuffer.append("*text l --------------------------");
        localStringBuffer.append("!gray 7\n");
        localStringBuffer.append("*text l 11111111111111111111111111");
        localStringBuffer.append("*text l 11111111111111111111111111");
        localStringBuffer.append("*text l --------------------------");
        localStringBuffer.append("!gray 8\n");
        localStringBuffer.append("*text l 11111111111111111111111111");
        localStringBuffer.append("*text l 11111111111111111111111111");
        localStringBuffer.append("*text l --------------------------");
        localStringBuffer.append("!gray 9\n");
        localStringBuffer.append("*text l 11111111111111111111111111");
        localStringBuffer.append("*text l 11111111111111111111111111");
        localStringBuffer.append("*text l --------------------------");
        localStringBuffer.append("!gray 10\n");
        localStringBuffer.append("*text l 11111111111111111111111111");
        localStringBuffer.append("*text l 11111111111111111111111111");
        localStringBuffer.append("*text l --------------------------");
        API05_printScript(localStringBuffer.toString(), localHashMap, 60, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int errorCode, String detail) throws RemoteException {
                DialogUtil.showConfirmDialog("打印错误", detail, null);
            }

            @Override
            public void onFinish() throws RemoteException {
                DialogUtil.showConfirmDialog("", "恭喜打印完成", null);
            }
        });

        return true;
    }

    public boolean CASE_printScript_CN_hz_asc() {
        StringBuffer localStringBuffer = new StringBuffer();
        HashMap localHashMap = new HashMap();
        localStringBuffer.append("!hz s\n");
        localStringBuffer.append("!asc s\n");
        localStringBuffer.append("*text l aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        localStringBuffer.append("*text l AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        localStringBuffer.append("*text l 你你你你你你你你你你你你你你你你你你你你你你你你");
        API05_printScript(localStringBuffer.toString(), localHashMap, 60, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int errorCode, String detail) throws RemoteException {
                DialogUtil.showConfirmDialog("打印错误", detail, null);
            }

            @Override
            public void onFinish() throws RemoteException {
                DialogUtil.showConfirmDialog("", "恭喜打印完成", null);
            }
        });

        return true;
    }
    public boolean CASE_printScript_CN_hzasc() {
        StringBuffer localStringBuffer = new StringBuffer();
        HashMap localHashMap = new HashMap();
        localStringBuffer.append("!hz s\n");
        localStringBuffer.append("!asc s\n");
        localStringBuffer.append("*text l aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeee\n");
        localStringBuffer.append("*text l 你你你你你你你你你你你你你你你你你你你你你你你你\n");

        localStringBuffer.append("!hz n\n");
        localStringBuffer.append("!asc n\n");
        localStringBuffer.append("*text l aaaaaaaaaabbbbbbbbbbccccccccccdd\n");
        localStringBuffer.append("*text l 你你你你你你你你你你你你你你你你\n");

        localStringBuffer.append("!hz l\n");
        localStringBuffer.append("!asc l\n");
        localStringBuffer.append("*text l aaaaaaaaaabbbbbbbbbbcccc\n");
        localStringBuffer.append("*text l 你你你你你你你你你你你你\n");

        localStringBuffer.append("!hz sn\n");
        localStringBuffer.append("!asc sn\n");
        localStringBuffer.append("*text l aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeee\n");
        localStringBuffer.append("*text l 你你你你你你你你你你你你你你你你你你你你你你你你\n");

        localStringBuffer.append("!hz sl\n");
        localStringBuffer.append("!asc sl\n");
        localStringBuffer.append("*text l aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeee\n");
        localStringBuffer.append("*text l 你你你你你你你你你你你你你你你你你你你你你你你你\n");

        localStringBuffer.append("!hz nl\n");
        localStringBuffer.append("!asc nl\n");
        localStringBuffer.append("*text l aaaaaaaaaabbbbbbbbbbccccccccccdd\n");
        localStringBuffer.append("*text l 你你你你你你你你你你你你你你你你\n");

        API05_printScript(localStringBuffer.toString(), localHashMap, 60, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int errorCode, String detail) throws RemoteException {
                DialogUtil.showConfirmDialog("打印错误", detail, null);
            }

            @Override
            public void onFinish() throws RemoteException {
                DialogUtil.showConfirmDialog("", "恭喜打印完成", null);
            }
        });

        return true;
    }

    public boolean CASE_printScript_CN_print1() {
        StringBuffer localStringBuffer = new StringBuffer();
        HashMap localHashMap = new HashMap();
        localStringBuffer.append("!BOCFONT 1 12 3\n");
        localStringBuffer.append("*text c *******  饿了么订单 *********");
        localStringBuffer.append("*text l ");
        localStringBuffer.append("*text c 小石灶");
        localStringBuffer.append("!BOCFONT 3 12 1\n");
        localStringBuffer.append("*text c -已在线支付-");
        localStringBuffer.append("!BOCFONT 1 12 3\n");
        localStringBuffer.append("*text l ");
        localStringBuffer.append("*text l ---------------------------");
        localStringBuffer.append("*text l ");
        localStringBuffer.append("*text l 下单时间：06-18 19:46");
        localStringBuffer.append("*text l ");
        localStringBuffer.append("*text c ----------1号篮子----------");
        localStringBuffer.append("*text l 土豆烧牛腩      ×1      38");
        localStringBuffer.append("*text l 红烧肉          ×1      42");
        localStringBuffer.append("*text l 宫保鸡菌养生汤  ×1      56");
        localStringBuffer.append("*text l 米饭            ×2      4");
        localStringBuffer.append("*text c ----------其他费用---------");
        localStringBuffer.append("*text l 餐盒            ×1      5");
        localStringBuffer.append("*text c ---------------------------");
        localStringBuffer.append("!BOCFONT 3 12 1\n");
        localStringBuffer.append("*text l 已付   145.00");
        localStringBuffer.append("!BOCFONT 1 12 3\n");
        localStringBuffer.append("*text l ---------------------------");
        localStringBuffer.append("*text l ");
        localStringBuffer.append("!BOCFONT 3 12 1\n");
        localStringBuffer.append("*text l 平乐园小区南磨房路平乐园104号楼305");
        localStringBuffer.append("*text l 王存澈（先生）");
        localStringBuffer.append("*text l 180-1010-5748");
        localStringBuffer.append("*text l ");
        localStringBuffer.append("*text c *******  完 *********");
        API05_printScript(localStringBuffer.toString(), localHashMap, 60, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int errorCode, String detail) throws RemoteException {
                DialogUtil.showConfirmDialog("打印错误", detail, null);
            }

            @Override
            public void onFinish() throws RemoteException {
                DialogUtil.showConfirmDialog("", "恭喜打印完成", null);
            }
        });

        return true;
    }

    public boolean CASE_printScript_CN_line() {
        StringBuffer localStringBuffer = new StringBuffer();
        HashMap localHashMap = new HashMap();
        localStringBuffer.append("*line\n");
        localStringBuffer.append("!BOCPRNOVER\n");
        API05_printScript(localStringBuffer.toString(), localHashMap, 60, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int errorCode, String detail) throws RemoteException {
                DialogUtil.showConfirmDialog("打印错误", detail, null);
            }

            @Override
            public void onFinish() throws RemoteException {
                DialogUtil.showConfirmDialog("", "恭喜打印完成", null);
            }
        });
        return  true;
    }

    public boolean CASE_printScript_CN_barcode() {
        StringBuffer localStringBuffer = new StringBuffer();
        HashMap localHashMap = new HashMap();
        localStringBuffer.append("!barcode 1 8\n");
        localStringBuffer.append("*barcode c 123\n");
        localStringBuffer.append("!barcode 2 64\n");
        localStringBuffer.append("*barcode l 123\n");
        localStringBuffer.append("!barcode 4 128\n");
        localStringBuffer.append("*barcode r 123\n");
        localStringBuffer.append("!barcode 6 128\n");
        localStringBuffer.append("*barcode c 123\n");
        localStringBuffer.append("!barcode 8 128\n");
        localStringBuffer.append("*barcode l 123\n");
        localStringBuffer.append("!BOCPRNOVER\n");
        API05_printScript(localStringBuffer.toString(), localHashMap, 60, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int errorCode, String detail) throws RemoteException {
                DialogUtil.showConfirmDialog("打印错误", detail, null);
            }

            @Override
            public void onFinish() throws RemoteException {
                DialogUtil.showConfirmDialog("", "恭喜打印完成", null);
            }
        });
        return  true;
    }

    public boolean CASE_printBitMap_CNoffset_intmax() {
        HashMap localHashMap = new HashMap();
        localHashMap.put("logo26", BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.mipmap.test2));
        API06_printBitMap(2144444, (Bitmap)localHashMap.get("logo26"), 60, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int errorCode, String detail) throws RemoteException {
                LogUtil.d(TAG, "onError errorCode=" + errorCode);
                LogUtil.d(TAG, "onError detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {
                LogUtil.d(TAG, "onFinish");
            }
        });
        return  true;
    }
    public boolean CASE_printBitMap_CNoffset_intmin() {
        HashMap localHashMap = new HashMap();
        localHashMap.put("logo26", BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), R.mipmap.test2));
        API06_printBitMap(-2144444, (Bitmap)localHashMap.get("logo26"), 60, new AidlPrinterListener.Stub() {
            @Override
            public void onError(int errorCode, String detail) throws RemoteException {
                LogUtil.d(TAG, "onError errorCode=" + errorCode);
                LogUtil.d(TAG, "onError detail=" + detail);
            }

            @Override
            public void onFinish() throws RemoteException {
                LogUtil.d(TAG, "onFinish");
            }
        });
        return  true;
    }
}
