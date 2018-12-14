#ifndef _UTIL_H_
#define _UTIL_H_

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>

#include "user_type.h"
#include "os_string.h"
#include "j300_no_use.h"

#define MAX_BUFFER_SIZE 2048

extern void Crc16CCITT(byte *pbyDataIn, unsigned long dwDataLen, byte abyCrcOut[2]);


#endif /* _UTIL_H_ */
