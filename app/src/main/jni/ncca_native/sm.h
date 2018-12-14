#include <stdlib.h>
#include <string.h>
#include <stdarg.h>
#include <stdio.h>
#include <errno.h>
#include <limits.h>
/*
#include <openssl/sha.h>
#include <openssl/rsa.h>
#include <openssl/objects.h>
*/

#include <openssl/bn.h>
#include <openssl/rand.h>
#include <openssl/err.h>
#include <openssl/ec.h>
#include <openssl/ecdsa.h>
#include <openssl/ecdh.h>



#ifdef SM_DEBUG
#define LOG_PRINTF(...) printf(__VA_ARGS__)
#define LOG_PRINT_HEX(a, b, c)\
{\
	int i;\
	LOG_PRINTF("%s%d\n", a, c);\
	for(i=0; i<c; i++)\
	{\
		LOG_PRINTF("%02X ", *(b+i));\
		if (!((i+1) % 16)) LOG_PRINTF("\n");\
	}\
	LOG_PRINTF("\n");\
}
#else
#define LOG_PRINTF(...)
#define LOG_PRINT_HEX(a, b, c)
#endif
