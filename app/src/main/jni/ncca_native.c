//
// Created by CuncheW1 on 2017/9/8.
//

#include <jni.h>
#include "sm_cli.h"
#include "sm2_cert.h"
#include "wifi_perf_client.h"

JNIEXPORT jboolean JNICALL
Java_com_test_ui_activities_other_test_1tools_ncca_1test_NCCASSLTestActivity_startSSLConnect(
        JNIEnv *env, jobject instance, jstring ip_, jstring port_) {
    const char *ip = (*env)->GetStringUTFChars(env, ip_, 0);
    const char *port = (*env)->GetStringUTFChars(env, port_, 0);

    // TODO
    jint ret = sm_main(2, ip, port, NULL);

    (*env)->ReleaseStringUTFChars(env, ip_, ip);
    (*env)->ReleaseStringUTFChars(env, port_, port);

    if (ret < 0) {
        return JNI_FALSE;
    } else {
        return JNI_TRUE;
    }
}

JNIEXPORT void JNICALL
Java_com_test_ui_activities_other_test_1tools_ncca_1test_NCCATestActivity_start_1test_1sm2_1cert_1chain(
        JNIEnv *env, jobject instance) {

    // TODO
    cert_test_main();
}

JNIEXPORT void JNICALL
Java_com_test_ui_activities_other_test_1tools_ncca_1test_NCCAPerformanceActivity_executeWIFICmd(
        JNIEnv *env, jobject instance, jstring ip_, jint port, jint cmd) {
    const char *ip = (*env)->GetStringUTFChars(env, ip_, 0);

    // TODO
    do_task(ip, port, cmd);

    (*env)->ReleaseStringUTFChars(env, ip_, ip);
}