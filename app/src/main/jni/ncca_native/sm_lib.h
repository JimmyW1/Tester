#include "sm.h"

void l2c(long l, unsigned char *c);

int KDF(unsigned char *Z, long Zlen, long klen, unsigned char *K);

int P_SM3(unsigned char *secret, int secret_len, unsigned char *seed, int slen, unsigned char *prf, int plen);

int SM_PRF(unsigned char *secret, int secret_len, char *label, int label_len, unsigned char *seed, int seed_len, unsigned char *prf, int plen);

void BNPrintf(BIGNUM* bn);

int asc2bcd(unsigned char*AscBuf, unsigned char *BcdBuf, int Len);

/*************************************************************************************
calc hash of the input message .
	
Parameters:

	Input : 
		msg ¨C The message.
		msglen - The length of the message.
		pucPub_Key ¨C The public key as retrieved from the certificate.
		usPub_key_len ¨C The public key length.

	Output : 
		msg_hash ¨C The message.
		
	Return value: 
		0:   Indicates the digital signature decryption and verification SUCCESS. 
		Any value other than 0:  Indicates failure of the signature verification. 
**************************************************************************************/
int calc_msg_hash(unsigned char *msg, int msglen, 
	const unsigned char  *pucPub_Key,
	const unsigned short usPub_key_len,
	unsigned char *msg_hash);

int sm4_encrypt_cbc(unsigned char *key, unsigned char *iv, unsigned char *plain, unsigned char *cipher, int len);

