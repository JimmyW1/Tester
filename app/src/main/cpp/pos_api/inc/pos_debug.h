#ifndef __POS_DEBUG_H__
#define __POS_DEBUG_H__

#include <stdio.h>
#ifdef __cplusplus
extern "C" {
#endif

void POS_Trace(const char *file, const char *func, int line, const char *format, ...);

void POS_TraceHex(const char *file, const char *func, int line, const void *data, int len);

void POS_TraceWithTag(const char *tag, const char *file, int line, const char *func,
                      const char *format, ...);

void POS_TraceHexWithTag(const char *tag, const char *file, int line, const char *func,
                         const char *hex, int32_t len);


#define POS_DEBUG(...)            //POS_Trace(__FILE__, __FUNCTION__, __LINE__, __VA_ARGS__)
#define POS_DEBUG_HEX(buf, len)    //POS_TraceHex(__FILE__, __FUNCTION__, __LINE__, (const void *)buf, len)

#define POS_PRINTF(...)            //dprintf(__VA_ARGS__)

#define POS_INFO(...)            //POS_DEBUG(__VA_ARGS__)
#define POS_INFO_HEX(buf, len)    //POS_DEBUG_HEX(buf, len)


#define POS_PRINT(...)            //POS_DEBUG(__VA_ARGS__)

#define POS_ASSERT(x)             //if(!(x)){POS_DEBUG("##########assert#########");while(1);}

#define POS_DEBUG_WITHTAG(tag, ...)            POS_TraceWithTag(tag,__FILE__, __LINE__,__FUNCTION__, __VA_ARGS__)
#define POS_DEBUG_HEX_WITHTAG(tag, buf, len)    POS_TraceHexWithTag(tag,__FILE__, __LINE__,__FUNCTION__, (const void *)buf, len)

#ifdef __cplusplus
}
#endif
#endif//__POS_DEBUG_H__