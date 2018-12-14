#ifndef _ESIGN_UTIL_H_
#define _ESIGN_UTIL_H_ 

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/time.h>


typedef unsigned char byte;
typedef unsigned int sint;
typedef unsigned short int usint;
typedef int boolean;

extern long getCurrentTimeMS();
extern byte BankUtil_GenLrc(byte *Data, usint DataLen);
extern void SVC_WAIT(long timeout_ms);

#endif /* _ESIGN_UTIL_H_ */
