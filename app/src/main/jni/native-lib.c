#include <jni.h>
#include "pinpad/util.h"
#include <pos_debug.h>
#include "pinpad/PINPAD.h"
#include "esign/esign.h"
#include "scanner/barcode.h"


JNIEXPORT jstring JNICALL
 Java_com_test_ui_activities_developer_1test_j300_1test_J300NativeApis_startJ300PinInput(JNIEnv *env,
                                                                                         jobject instance) {

    // TODO
    char buf[128] = {0};
    char ret_str[128] = {0};
    int ret = -1;

    Pinpad_SetPPType(5, PP222_3DES_MODE, CLEAR_FORMAT);
    Pinpad_Connect();
    Pinpad_Idle();
    ret = Pinpad_LoadMasterKey(0, "\x11\x11\x11\x11\x11\x11\x11\x11\x11\x11\x11\x11\x11\x11\x11\x11");
    POS_DEBUG_WITHTAG("UsbserDemo","LoadMasterKey Ret=%d\n", ret);

    ret = Pinpad_SelectKey(0);
    POS_DEBUG_WITHTAG("UsbserDemo","SelectKey Ret=%d\n", ret);
    memset(buf, '\0', sizeof(buf));
    ret = Pinpad_GetPin(buf, "RMB:       1.00 ", "1234567890123456", "\x89\xB0\x7B\x35\xA1\xB3\xF4\x7E\x89\xB0\x7B\x35\xA1\xB3\xF4\x88", 0, 12, 120, 0);
    POS_DEBUG_WITHTAG("UsbserDemo","GetPlainPin Ret=%d\n", ret);
    POS_DEBUG_HEX_WITHTAG("UsbserDemo", buf, 128);
    Pinpad_Close();

    char *p = ret_str;
    sprintf(p, "%02X%02X%02X%02X%02X%02X%02X%02X", buf[0], buf[1], buf[2], buf[3], buf[4], buf[5], buf[6], buf[7]);
    POS_DEBUG_WITHTAG("UsbserDemo","Pinblock Ret=%s\n", ret_str);

    return (*env)->NewStringUTF(env, ret_str);
 }

JNIEXPORT void JNICALL
Java_com_test_ui_activities_developer_1test_j300_1test_J300NativeApis_startJ300Esign(JNIEnv *env,
                                                                                     jobject instance) {

    // TODO
    eSign_ExternalSign("1A2B3C4D", 8, 150, "esign.bmp", 1, FALSE);
    eSin_CloseComm();
}

JNIEXPORT jstring JNICALL
Java_com_test_ui_activities_developer_1test_j300_1test_J300NativeApis_startJ300Scanner(JNIEnv *env,
                                                                                       jobject instance) {

   // TODO
    char buf[128] = {0};
    int ret_len = 128;
    char result_buf[128] = {0};
    Scanner_Connect();
    Scanner_Start(buf, 128, &ret_len, 120000);
    Scanner_Disconnect();
    POS_DEBUG_WITHTAG("UsbserDemo","result len=%d\n", ret_len);
    POS_DEBUG_WITHTAG("UsbserDemo","result code=%s\n", buf);

   return (*env)->NewStringUTF(env, buf);
}

JNIEXPORT void JNICALL
Java_com_test_ui_activities_other_test_1tools_j300_1test_J300NativeApis_startCtls(JNIEnv *env,
                                                                                  jobject instance) {


    // TODO

    jclass tmpclass = (*env)->GetObjectClass(env, instance);
    if (tmpclass == NULL) {
        POS_DEBUG_WITHTAG("UsbserDemo","==class serialManager not found!=======================");
        return;
    }

    jmethodID show_id = (*env)->GetMethodID(env, tmpclass, "jShow", "()V");
    if (show_id == NULL) {
        POS_DEBUG_WITHTAG("UsbserDemo","==jShow not found!1=======================");
        return;
    }
}

