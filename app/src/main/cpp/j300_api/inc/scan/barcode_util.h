#ifndef _BARCODE_UTIL_H_
#define _BARCODE_UTIL_H_

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/time.h>

extern long getCurrentTimeMS();
extern void SVC_WAIT(long timeout_ms);

#endif /* _BARCODE_UTIL_H_ */
