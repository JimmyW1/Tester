/**
 * sm_api.c
 */
#include "sm.h"
#include "sm2.h"
#include "sm3.h"
//#include "sm4.h"
#include "sm_api.h"

#include "log.h"

/* Fixed value of ENTLA and IDA from PBOC3.0 part17 */
#define ENTLA "0080"
#define IDA "31323334353637383132333435363738"

/*
 * Suggested 256 bit elliptic curves.
 * Suggested value of p,a,b,n,Gx,Gy from the standard.c
 */

#define SM2_p "FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFF"
#define SM2_a "FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFC"
#define SM2_b "28E9FA9E9D9F5E344D5A9E4BCF6509A7F39789F515AB8F92DDBCBD414D940E93"
#define SM2_n "FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFF7203DF6B21C6052B53BBF40939D54123"
#define SM2_Gx "32C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7"
#define SM2_Gy "BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0"

#define ZA "\x00\x80\x31\x32\x33\x34\x35\x36\x37\x38\x31\x32\x33\x34\x35\x36\x37\x38\xFF\xFF\xFF\xFE\xFF\xFF\xFF\xFF\xFF\xFF\xFF\xFF\xFF\xFF\xFF\xFF\xFF\xFF\xFF\xFF\x00\x00\x00\x00\xFF\xFF\xFF\xFF\xFF\xFF\xFF\xFC\x28\xE9\xFA\x9E\x9D\x9F\x5E\x34\x4D\x5A\x9E\x4B\xCF\x65\x09\xA7\xF3\x97\x89\xF5\x15\xAB\x8F\x92\xDD\xBC\xBD\x41\x4D\x94\x0E\x93\x32\xC4\xAE\x2C\x1F\x19\x81\x19\x5F\x99\x04\x46\x6A\x39\xC9\x94\x8F\xE3\x0B\xBF\xF2\x66\x0B\xE1\x71\x5A\x45\x89\x33\x4C\x74\xC7\xBC\x37\x36\xA2\xF4\xF6\x77\x9C\x59\xBD\xCE\xE3\x6B\x69\x21\x53\xD0\xA9\x87\x7C\xC6\x2A\x47\x40\x02\xDF\x32\xE5\x21\x39\xF0\xA0"
#ifdef PBOC_TEST_DATA
	#define ZA "\x01\x00\x18\x1B\x3E\x1A\xC6\x84\x6F\x8B\x80\x17\xF2\x8B\xA3\x99\x0E\x41\x7B\xD3\x7E\x3C\x90\x8F\x3E\xDF\x12\xD9\x84\x05\x83\xC0\xAA\xB5\xFF\xFF\xFF\xFE\xFF\xFF\xFF\xFF\xFF\xFF\xFF\xFF\xFF\xFF\xFF\xFF\xFF\xFF\xFF\xFF\x00\x00\x00\x00\xFF\xFF\xFF\xFF\xFF\xFF\xFF\xFC\x28\xE9\xFA\x9E\x9D\x9F\x5E\x34\x4D\x5A\x9E\x4B\xCF\x65\x09\xA7\xF3\x97\x89\xF5\x15\xAB\x8F\x92\xDD\xBC\xBD\x41\x4D\x94\x0E\x93\x32\xC4\xAE\x2C\x1F\x19\x81\x19\x5F\x99\x04\x46\x6A\x39\xC9\x94\x8F\xE3\x0B\xBF\xF2\x66\x0B\xE1\x71\x5A\x45\x89\x33\x4C\x74\xC7\xBC\x37\x36\xA2\xF4\xF6\x77\x9C\x59\xBD\xCE\xE3\x6B\x69\x21\x53\xD0\xA9\x87\x7C\xC6\x2A\x47\x40\x02\xDF\x32\xE5\x21\x39\xF0\xA0"
static unsigned long start_time;
#endif

#define ABORT {\
	ret = 0x01;\
	LOG_PRINTF("Line: %d|%s\n", __LINE__, __FILE__);\
	goto builtin_err;\
}
/*
#ifdef _SHARED_LIB
int main(int argc, char **argv)
{
	// initialize your SM Library initialized static data here

}
#endif
*/

/*************************************************************************************
calc hash of the input message .
	
Parameters:

	Input : 
		msg �C The message.
		msglen - The length of the message.
		pucPub_Key �C The public key as retrieved from the certificate.
		usPub_key_len �C The public key length.

	Output : 
		msg_hash �C The message.
		
	Return value: 
		0:   Indicates the digital signature decryption and verification SUCCESS. 
		Any value other than 0:  Indicates failure of the signature verification. 
**************************************************************************************/
#if 0
calc_msg_hash(unsigned char *msg, int msglen, 
	const unsigned char  *pucPub_Key,
	const unsigned short usPub_key_len,
	unsigned char *msg_hash)
{
	unsigned char *buf, za[32];
	int len;

	//[ENTLa||IDa||a||b||Xg||Yg||Xa||Ya]
	buf = malloc(2+16+32+32+32+32+32+32+msglen);
	memcpy(buf, ZA, 146); len = 146;
	memcpy(buf+len, pucPub_Key, usPub_key_len); len += usPub_key_len;
	LOG_PRINT_HEX("ENTLA||IDA||a||b||Xg||Yg||Xa||Ya: ", buf, len);

	ucSM3Hash(buf, len, za);
	LOG_PRINT_HEX("Hash of Za:", za, 32);
	
	memcpy(buf, za, 32); len = 32;
	memcpy(buf+len, msg, msglen); len += msglen;
	LOG_PRINT_HEX("Message: ", buf, len);

	ucSM3Hash(buf, len, msg_hash);
	LOG_PRINT_HEX("\nHash of Za||message: ", msg_hash, 32);

	free(buf);
	return 0;
}
#endif

/*************************************************************************************
Provide  the version number of  the  shared SM  library. The format of the version 
	number string should be ��SMxx.yy.zz��. Eg: ��SM01.00.00��

Parameters:  
	Output: 
		pucver_string  �C This output buffer will be populated with the version number of the SM library.

	Return value: 
		0:   Indicates the digital signature decryption and verification SUCCESS. 
		Any value other than 0 indicates failure in retrieval of the version number. 
**************************************************************************************/
unsigned char ucSMGetVersion(unsigned char *pucver_string)
{
	sprintf(pucver_string, "SM00.01.00");
	return 0x00;
}

/*************************************************************************************
Validate the SM2 signature for the message sent as part of the API input parameter.
	
Parameters:

	Input : 
		pucMsg �C The buffer which contains the data for which dynamic signature is received.
		usMsg_len - The length of the data buffer. 
		pucPub_Key �C The public key as retrieved from the certificate.
		usPub_key_len �C The public key length.
		pucDigital_sig - The digital signature string as received in the key certificate. 
		usDyn_sig_len �C The digital signature string length.
		ucAlgo_indicator �C The algorithm indicator indicating the value of the algorithm used for digital signature generation.

	Return value: 
		0:   Indicates the digital signature decryption and verification SUCCESS. 
		Any value other than 0:  Indicates failure of the signature verification. 
**************************************************************************************/
unsigned char ucSM2Validate(
	const unsigned char *pucMsg,
	const unsigned short usMsg_len,
	const unsigned char  *pucPub_Key,
	const unsigned short usPub_key_len,
	const unsigned char *pucDigital_sig,
	const unsigned short usDyn_sig_len,
	unsigned char ucAlgo_indicator)
{
	BN_CTX *ctx = NULL;
	BIGNUM *p, *a, *b;
	EC_GROUP *group;
	EC_POINT *P;
	BIGNUM *x, *y, *z;
	EC_KEY	*eckey = NULL;
	unsigned char	*signature;
	unsigned char	digest[32], ret; 
	int	sig_len;
	//ECDSA_SIG *ecsig = ECDSA_SIG_new();

	CRYPTO_set_mem_debug_functions(0, 0, 0, 0, 0);
	CRYPTO_mem_ctrl(CRYPTO_MEM_CHECK_ON);
	ERR_load_crypto_strings();
	
	ctx = BN_CTX_new();
	if (!ctx) ABORT;

	memset(digest, 0, sizeof(digest));
	calc_msg_hash((unsigned char *)pucMsg, usMsg_len, pucPub_Key, usPub_key_len, digest);

	/* Curve SM2 (Chinese National Algorithm) */
	p = BN_new();
	a = BN_new();
	b = BN_new();
	if (!p || !a || !b) ABORT;

	/* applications should use EC_GROUP_new_curve_GFp, so that the library gets to choose the EC_METHOD */
	group = EC_GROUP_new(EC_GFp_mont_method()); 
	if (!group) ABORT;
	if (!BN_hex2bn(&p, SM2_p)) ABORT;
	//	if (1 != BN_is_prime_ex(p, BN_prime_checks, ctx, NULL)) ABORT;
	if (!BN_hex2bn(&a, SM2_a)) ABORT;
	if (!BN_hex2bn(&b, SM2_b)) ABORT;

	if (!EC_GROUP_set_curve_GFp(group, p, a, b, ctx)) ABORT;

	P = EC_POINT_new(group);
	if (!P) ABORT;

	x = BN_new();
	y = BN_new();
	z = BN_new();
	if (!x || !y || !z) ABORT;

	if (!BN_hex2bn(&x, SM2_Gx)) ABORT;
	if (!EC_POINT_set_compressed_coordinates_GFp(group, P, x, 0, ctx)) ABORT;
	if (!EC_POINT_is_on_curve(group, P, ctx)) ABORT;
	if (!BN_hex2bn(&z, SM2_n)) ABORT;
	if (!EC_GROUP_set_generator(group, P, z, BN_value_one())) ABORT;

	/* create new ecdsa key */
	if ((eckey = EC_KEY_new()) == NULL)
		goto builtin_err;

	if (EC_KEY_set_group(eckey, group) == 0) ABORT;

	/* create public key */
	//if (!BN_hex2bn(&x, Pa_Xa)) ABORT;
	//if (!BN_hex2bn(&y, Pa_Ya)) ABORT;
	if (!BN_bin2bn(pucPub_Key, 32, x)) ABORT;
	if (!BN_bin2bn(pucPub_Key+32, 32, y)) ABORT;
	if (!EC_POINT_set_affine_coordinates_GFp(group, P, x, y, ctx)) ABORT;
	if (!EC_POINT_is_on_curve(group, P, ctx)) ABORT;

	EC_KEY_set_public_key(eckey, P);
	/* check key */
	/*
	if (!EC_KEY_check_key(eckey))
	{
		LOG_PRINTF(" failed\n");
		goto builtin_err;
	}
	*/

	/* create BER signature */
	sig_len = ECDSA_size(eckey);
 	LOG_PRINTF("Siglength is: %d \n",sig_len);
	if ((signature = OPENSSL_malloc(sig_len)) == NULL) ABORT;

	// DER format: 		SEQUENCE TAG=0x30, module TAG=0x20
	// 0x30 len 0x02 len r 0x02 len s
	memcpy(signature, "\x30\x44\x02\x20", 4);
	memcpy(signature+4, pucDigital_sig, 32);
	memcpy(signature+4+32, "\x02\x20", 2);
	memcpy(signature+4+32+2, pucDigital_sig+32, 32);

	/* verify signature */
	if (SM2_verify(1, digest, 32, signature, sig_len, eckey) != 1)
	{
		ret = 0x02;
		goto builtin_err;
	}

	LOG_PRINTF("ECVerify OK\n");
	/*
	dbprintf("ECVerify OK\n     r = 0x");
	d2i_ECDSA_SIG(&ecsig, &signature, sig_len);	//Will change signature and cause system error when free signature.
	BNPrintf(ecsig->r);
	dbprintf("\n     s = 0x");
	BNPrintf(ecsig->s);
	dbprintf("\n");
	*/

	ret = 0x00;
	
builtin_err:
	OPENSSL_free(signature);
	BN_free(p);
	BN_free(a);
	BN_free(b);
	BN_free(x);
	BN_free(y);
	BN_free(z);
	EC_POINT_free(P);
	EC_KEY_free(eckey);
	EC_GROUP_free(group);
	BN_CTX_free(ctx);
	
	return ret;

}


/*************************************************************************************
Generate the HASH output for a given input data using SM3 algorithm.

Parameters:  
	Input: 
		pucinput_digest �C The input message buffer for hashing.
		ulinput_length  -  The length of the input buffer data in bytes.
		
	Output: 
		pucoutput_digest  �C The 32 byte SM3 hash output buffer.
		
	Return value: 
		0:   Indicates the digital signature decryption and verification SUCCESS. 
		Any value other than 0:  Indicates failure of the hashing process. If the failure is received then the 
		output hash buffer will not be used. 
**************************************************************************************/
unsigned char ucSM3Hash(
	unsigned char *pucinput_digest,
	unsigned long ulinput_length,
	unsigned char *pucoutput_digest)
{
	sm3(pucinput_digest, ulinput_length, pucoutput_digest);
	return 0;
}

/*************************************************************************************
Encrypt or decrypt the given input data using SM4 algorithm. 

Parameters:  
	Input: 
		ucmode -  1: encrypt, 0: decrypt the input digest. 
		puckey -  key.
		pucinput_digest �C The input message buffer for encryption/decryption.
		ulinput_length  -  The length of the input buffer data in bytes. 
 
	Output: 
		pucoutput_digest  �C The encrypted/decrypted output.
		uloutput_length �C The length of the output buffer data in bytes.
		
	Return value: 
		0:   Indicates the digital signature decryption and verification SUCCESS. 
		Any value other than 0:  Indicates failure of the encryption/decryption process. If the failure is 
		received then the output buffer will not be used. 
**************************************************************************************/
/*
unsigned char ucSM4EncryptDecrypt(
	unsigned char ucmode,
	unsigned char *puckey,
	unsigned char *pucinput_digest,
	unsigned long ulinput_length,
	unsigned char *pucoutput_digest,
	unsigned long uloutput_length )
{
	sm4_context ctx;

	if (ucmode)
	{
		sm4_setkey_enc(&ctx, puckey);
		sm4_crypt_ecb(&ctx, 1, 16, pucinput_digest, pucoutput_digest);
	}
	else
	{
		sm4_setkey_dec(&ctx, puckey);
		sm4_crypt_ecb(&ctx, 0, 16, pucinput_digest, pucoutput_digest);
	}
	return 0x00;
}
*/



