#ifndef _LOG_H_
#define _LOG_H_

#include <android/log.h>

#define LOG_TAG  "GmJni"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

#define printf LOGI


#endif /* _LOG_H_ */
