#ifndef __POS_TYPE_H__
#define __POS_TYPE_H__

#include <stdint.h>

typedef int32_t BOOL;

#define FALSE 0
#define TRUE 1

#ifndef NULL
#define NULL ((void *)0)
#endif

#define _objsizeof(_struct,_member)  	((unsigned int)sizeof(((_struct *)0)->_member))
#define _offsetof(_struct, _member)     ((unsigned long) &((_struct *)0)->_member)


#endif
