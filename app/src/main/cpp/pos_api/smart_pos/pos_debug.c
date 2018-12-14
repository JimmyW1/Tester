#include "stdio.h"
#include "string.h"
#include <stdarg.h> 
#include "jni.h"
#include <Android/Log.h>

#define LOG    		"PosAppJni"
#define LOGD(...)  	__android_log_print(ANDROID_LOG_DEBUG,LOG,__VA_ARGS__)
#define LOGI(...)  		__android_log_print(ANDROID_LOG_INFO,LOG,__VA_ARGS__)
#define LOGW(...)  	__android_log_print(ANDROID_LOG_WARN,LOG,__VA_ARGS__)
#define LOGE(...)  	__android_log_print(ANDROID_LOG_ERROR,LOG,__VA_ARGS__)
#define LOGF(...)  	__android_log_print(ANDROID_LOG_FATAL,LOG,__VA_ARGS__)
#define LOGI_WITH_TAG(tag,...) __android_log_print(ANDROID_LOG_INFO,tag,__VA_ARGS__)

#define POS_LOGHEX_LINE_LEN_MAX		(25)

void POS_Trace(const char *file,const char *func, int line,const char *format,...)
{
	va_list args;
	int lenth = 0;
	int tmplen = 0;
	char logstring[256] = {0};

	tmplen = sprintf(logstring, "<%s,%d>:", func, line);
	va_start(args, format);
	lenth = vsnprintf(logstring+tmplen, sizeof(logstring)-tmplen, format, args);
	if(lenth <= 0 || lenth >= sizeof(logstring)-tmplen)
	{
		
	}
	LOGI("%s", logstring);
	va_end(args);
}


void POS_TraceHex(const char *file, const char *func, int line,const void *data, int len)
{
	char logstring[POS_LOGHEX_LINE_LEN_MAX<<3+1] = {0};
	int tmplen = 0;
	int offset = 0;
	int i = 0;
	char *tmp_ptr = (char *)data;

	if(NULL == tmp_ptr)
	{
		LOGI("<%s,%d>:null", func, line);		
		return;
	}
	LOGI("<%s,%d>:len = 0x%02x", func, line, len);	
	for(;offset < len;)
	{
		tmplen = 0;
		memset(logstring, 0x00, sizeof(logstring));
		for(i = 0; i < POS_LOGHEX_LINE_LEN_MAX && (offset+i) < len; i++)
		{
			tmplen += sprintf(&logstring[tmplen], "%02X ", tmp_ptr[offset+i]);
		}
		LOGI("%s", logstring);
		offset += i;
	}
}

void POS_TraceWithTag(const char *tag,const char *file, int line, const char *func, const char * format, ...)
{
	va_list args;
	int lenth = 0;
	int tmplen = 0;
	char logstring[2048] = {0};

	tmplen = sprintf(logstring, "<%s,%d>:", func, line);
	va_start(args, format);
	lenth = vsnprintf(logstring+tmplen, sizeof(logstring)-tmplen, format, args);
	if(lenth <= 0 || lenth >= sizeof(logstring)-tmplen)
	{

	}
	LOGI_WITH_TAG(tag,"%s", logstring);
	va_end(args);
}
void POS_TraceHexWithTag(const char *tag,const char *file, int line, const char *func, const char *hex,int32_t len)
{
	char logstring[POS_LOGHEX_LINE_LEN_MAX<<3+1] = {0};
	int tmplen = 0;
	int offset = 0;
	int i = 0;
	char *tmp_ptr = (char *)hex;

	if(NULL == tmp_ptr)
	{
		LOGI_WITH_TAG(tag,"<%s,%d>:null", func, line);
		return;
	}
	LOGI_WITH_TAG(tag,"<%s,%d>:len = 0x%02x", func, line, len);
	for(;offset < len;)
	{
		tmplen = 0;
		memset(logstring, 0x00, sizeof(logstring));
		for(i = 0; i < POS_LOGHEX_LINE_LEN_MAX && (offset+i) < len; i++)
		{
			tmplen += sprintf(&logstring[tmplen], "%02X ", tmp_ptr[offset+i]);
		}
		LOGI_WITH_TAG(tag,"%s", logstring);
		offset += i;
	}
}
