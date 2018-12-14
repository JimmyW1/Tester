#include "sm.h"
#include "sm2.h"
#include "sm4.h"
#include "sm2_api.h"

#include  "log.h"

const int i = 1;
#define is_bigendian() ((*(char*)&i) == 0)

void l2c(long l, unsigned char *c)
{
	if (is_bigendian()) {
		memcpy(c, (unsigned char *)&l, 4);
	}
	else {
		*((c)++)=(unsigned char)(((l)>>24)&0xff);
		*((c)++)=(unsigned char)(((l)>>16)&0xff);
		*((c)++)=(unsigned char)(((l)>> 8)&0xff);
		*((c)++)=(unsigned char)(((l)    )&0xff);
	}
}

int rand_bytes(unsigned char *buf, int num)
{
	//RAND_bytes(buf, num);
	// change by wangcunche
	FILE *fp = NULL;
	int ret = 0;

	if (!buf)
	{
		return -1;
	}

	fp = fopen("/dev/urandom", "r");
	if (fp)
	{
		ret = fread(buf, num, 1, fp);
	}
	else
	{

	}

	fclose(fp);

	return ret;
}

int KDF(unsigned char *Z, long Zlen, long klen, unsigned char *K)
{
	long ct = 0x00000001;
	long loop = klen / 32 + 1;
	unsigned char *cct, *buf, ha[32], cctbuf[4];

	buf = malloc(Zlen + 4);
	memset(buf, 0, sizeof(buf));
	memcpy(buf, Z, Zlen);
	cct = cctbuf;

	for (; ct<=loop; ct++)
	{
		l2c(ct, cct);
		memcpy(buf+Zlen, cctbuf, 4);
		//LOG_PRINT_HEX("cctbuf: ", cctbuf, 4);

		sm3(buf, Zlen+4, ha);
		if (ct == loop){
			if (klen % 32 == 0)
				memcpy(K+(ct-1)*32, ha, 32);
			else
				memcpy(K+(ct-1)*32, ha, klen % 32);
		}
		else
			memcpy(K+(ct-1)*32, ha, 32);
	}
	free(buf);
}

int P_SM3(unsigned char *secret, int secret_len, unsigned char *seed, int slen, unsigned char *prf, int plen)
{
	int alen, i = 1;
	unsigned char *A, *s, *p = prf;

	A = malloc(32 + slen);
	sm3_hmac(secret, secret_len, seed, slen, A);
	alen = 32;

	s = malloc(32 + slen);
	memcpy(s + 32, seed, slen);
	slen += 32;

	do {
		memcpy(s, A, 32);
		//printf("A(%d)", i++);
		//LOG_PRINT_HEX(": ", A, alen);
		//LOG_PRINT_HEX("S: ", s, slen);

		if (plen > 32) {
			sm3_hmac(secret, secret_len, s, slen, p);
			sm3_hmac(secret, secret_len, A, 32, A);
			p += 32;
			plen -= 32;
		}
		else {
			sm3_hmac(secret, secret_len, s, slen, A);
			memcpy(p, A, plen);
			plen -= 32;
		}

		//printf("HMAC(secret, A(%d)+seed)", i - 1);
		//LOG_PRINT_HEX(": ", p - 32, 32);

	} while (plen > 0);

	free(s);
}

int SM_PRF(unsigned char *secret, int secret_len, char *label, int label_len, unsigned char *seed, int seed_len, unsigned char *prf, int plen)
{
	unsigned char *buf;

	buf = malloc(label_len + seed_len);
	memcpy(buf, label, label_len);
	memcpy(buf + label_len, seed, seed_len);

	P_SM3(secret, secret_len, buf, label_len + seed_len, prf, plen);
	free(buf);
}
/* modify by wangcunche
void BNPrintf(BIGNUM* bn)
{
	char *p=NULL;
	p=BN_bn2hex(bn);
	printf("%s\n",p);
	OPENSSL_free(p);
}
*/

int asc2bcd(unsigned char*AscBuf, unsigned char *BcdBuf, int Len)
{
    int  i;
    unsigned char  str[2];
	
    memset(str, 0, sizeof(str));
	
    for (i = 0; i < Len; i += 2)
    {
        if ((AscBuf[i] >= 'a') && (AscBuf[i] <= 'f'))
        {
            str[0] = AscBuf[i] - 'a' + 0x0A;
        }
        else if ((AscBuf[i] >= 'A') && (AscBuf[i] <= 'F'))
        {
            str[0] = AscBuf[i] - 'A' + 0x0A;
        }
        else if (AscBuf[i] >= '0')
        {
            str[0] = AscBuf[i] - '0';
        }
        else
        {
            str[0] = 0;
        }
		
        if ((AscBuf[i+1] >= 'a') && (AscBuf[i+1] <= 'f'))
        {
            str[1] = AscBuf[i+1] - 'a' + 0x0A;
        }
        else if ((AscBuf[i+1] >= 'A') && (AscBuf[i+1] <= 'F'))
        {
            str[1] = AscBuf[i+1] - 'A' + 0x0A;
        }
        else if (AscBuf[1] >= '0')
        {
            str[1] = AscBuf[i+1] - '0';
        }
        else
        {
            str[1] = 0;
        }
		
        BcdBuf[i/2] = (str[0] << 4) | (str[1] & 0x0F);
    }
	
    return 0;
}


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
	unsigned char *msg_hash)
{
	unsigned char *buf, za[32];
	int len;

	//[ENTLa||IDa||a||b||Xg||Yg||Xa||Ya]
	buf = malloc(2+16+32+32+32+32+32+32+msglen);
	memcpy(buf, ZA, 146); len = 146;
	memcpy(buf+len, pucPub_Key, usPub_key_len); len += usPub_key_len;
	LOG_PRINT_HEX("ENTLA||IDA||a||b||Xg||Yg||Xa||Ya: ", buf, len);

	sm3(buf, len, za);
	LOG_PRINT_HEX("Hash of Za:", za, 32);
	
	memcpy(buf, za, 32); len = 32;
	memcpy(buf+len, msg, msglen); len += msglen;
	LOG_PRINT_HEX("Message: ", buf, len);

	sm3(buf, len, msg_hash);
	LOG_PRINT_HEX("\nHash of Za||message: ", msg_hash, 32);

	free(buf);
	return 0;
}

int sm4_encrypt_cbc(unsigned char *key, unsigned char *iv, unsigned char *plain, unsigned char *cipher, int len)
{
	sm4_context ctx;

	sm4_setkey_enc(&ctx, key);
	sm4_crypt_cbc(&ctx, 1, len, iv, plain, cipher);
}

void BNPrintf(BIGNUM* bn)
{
	char *p=NULL;
	p=BN_bn2hex(bn);
	printf("%s\n",p);
	OPENSSL_free(p);
}
