/**
 * sm2_api.c
 */
#include "sm.h"
#include "sm2_api.h"
#include "sm2.h"

int sm2_msg_hash(unsigned char *msg, int msglen, 
	const unsigned char  *pub_key,
	const unsigned short pub_key_len,
	unsigned char *msg_hash)
{
	unsigned char *buf, za[32];
	int len=0;

	//[ENTLa||IDa||a||b||Xg||Yg||Xa||Ya]
	buf = malloc(2+18+32+32+32+32+32+32+msglen);
	asc2bcd(ENTLA, buf+len, strlen(ENTLA)); len += strlen(ENTLA)/2;
	asc2bcd(IDA, buf+len, strlen(IDA)); len += strlen(IDA)/2;
	asc2bcd(SM2_a, buf+len, 64); len += 32;
	asc2bcd(SM2_b, buf+len, 64); len += 32;
	asc2bcd(SM2_Gx, buf+len, 64); len += 32;
	asc2bcd(SM2_Gy, buf+len, 64); len += 32;
	memcpy(buf+len, pub_key, pub_key_len); len += pub_key_len;
	LOG_PRINT_HEX("ENTLA||IDA||a||b||Xg||Yg||Xa||Ya: ", buf, len);

	sm3(buf, len, za);
	LOG_PRINT_HEX("Hash of Za:", za, 32);
	
	memcpy(buf, za, 32); len = 32;
	memcpy(buf+len, msg, msglen); len += msglen;
	LOG_PRINT_HEX("Za||Message: ", buf, len);

	sm3(buf, len, msg_hash);
	LOG_PRINT_HEX("\nHash of Za||message: ", msg_hash, 32);

	free(buf);
	return 0;
}

/*************************************************************************************
sm2_sign():
	Sign the digest sent as part of the API input parameter.
	
Parameters:

	Input : 
		msg �C The buffer which contains the data for which dynamic signature is received.
		msg_len - The length of the data buffer. 
		prv_Key �C The private key as retrieved from the certificate.
		prv_key_len �C The private key length.
		pub_Key �C The public key as retrieved from the certificate.
		pub_key_len �C The public key length.
		sig - The digital signature string as received in the key certificate. 
		sig_len �C The digital signature string length.

	Return value: 
		0:   Indicates the digital signature decryption and verification SUCCESS. 
		Any value other than 0:  Indicates failure of the signature verification. 
**************************************************************************************/
unsigned char sm2_sign(
	const unsigned char *msg,
	const unsigned short msg_len,
	const unsigned char  *prv_key,
	const unsigned short prv_key_len,
	const unsigned char  *pub_key,
	const unsigned short pub_key_len,
	unsigned char *sig,
	unsigned int *sig_len)
{
	BN_CTX *ctx = NULL;
	BIGNUM *p, *a, *b;
	EC_GROUP *group;
	EC_POINT *P;
	BIGNUM *x, *y, *z;
	EC_KEY	*eckey = NULL;
	unsigned char	*signature;
	unsigned char	digest[32], ret; 
	int	b_sig_len;

	//CRYPTO_set_mem_debug_functions(0, 0, 0, 0, 0);
	CRYPTO_mem_ctrl(CRYPTO_MEM_CHECK_ON);
	ERR_load_crypto_strings();
	
	ctx = BN_CTX_new();
	if (!ctx) ABORT;

	memset(digest, 0, sizeof(digest));
	sm2_msg_hash((unsigned char *)msg, msg_len, pub_key, pub_key_len, digest);

	/* Curve SM2 (Chinese National Algorithm) */
	p = BN_new();
	a = BN_new();
	b = BN_new();
	if (!p || !a || !b) ABORT;

	/* applications should use EC_GROUP_new_curve_GFp, so that the library gets to choose the EC_METHOD */
	group = EC_GROUP_new(EC_GFp_mont_method()); 
	if (!group) ABORT;
	if (!BN_hex2bn(&p, SM2_p)) ABORT;
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

	/* create private key */
	if (!BN_bin2bn(prv_key, 32, x)) ABORT;
	EC_KEY_set_private_key(eckey, x);

	/* create BER signature */
	b_sig_len = ECDSA_size(eckey);
	if ((signature = OPENSSL_malloc(b_sig_len)) == NULL) ABORT;

	/* sign */
	if (!SM2_sign(0, digest, 32, signature, sig_len, eckey))
	{
		ret = 0x01;
		goto builtin_err;
	}

	// DER format: 		SEQUENCE TAG=0x30, module TAG=0x20
	// 0x30 len 0x02 len r 0x02 len s
	memcpy(sig, signature, *sig_len);
 	//LOG_PRINTF("Sig length is: %d \n", *sig_len);
	//LOG_PRINTF("SM2 sign OK\n");

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
sm2_verify():
	Validate the SM2 signature for the message sent as part of the API input parameter.
	
Parameters:

	Input : 
		msg �C The buffer which contains the data for which dynamic signature is received.
		msg_len - The length of the data buffer. 
		pub_Key �C The public key as retrieved from the certificate.
		pub_key_len �C The public key length.
		sig - The digital signature string as received in the key certificate. 
		sig_len �C The digital signature string length.

	Return value: 
		0:   Indicates the digital signature decryption and verification SUCCESS. 
		Any value other than 0:  Indicates failure of the signature verification. 
**************************************************************************************/
unsigned char sm2_verify(
	const unsigned char *msg,
	const unsigned short msg_len,
	const unsigned char  *pub_key,
	const unsigned short pub_key_len,
	const unsigned char *sig,
	const unsigned short sig_len)
{
	BN_CTX *ctx = NULL;
	BIGNUM *p, *a, *b;
	EC_GROUP *group;
	EC_POINT *P;
	BIGNUM *x, *y, *z;
	EC_KEY	*eckey = NULL;
	unsigned char	*signature = NULL;
	unsigned char	digest[32], ret; 
	int	b_sig_len = -1;
	ECDSA_SIG *s;

	//CRYPTO_set_mem_debug_functions(0, 0, 0, 0, 0);
	CRYPTO_mem_ctrl(CRYPTO_MEM_CHECK_ON);
	ERR_load_crypto_strings();
	
	ctx = BN_CTX_new();
	if (!ctx) ABORT;

	memset(digest, 0, sizeof(digest));
	sm2_msg_hash((unsigned char *)msg, msg_len, pub_key, pub_key_len, digest);

	/* Curve SM2 (Chinese National Algorithm) */
	p = BN_new();
	a = BN_new();
	b = BN_new();
	if (!p || !a || !b) ABORT;

	/* applications should use EC_GROUP_new_curve_GFp, so that the library gets to choose the EC_METHOD */
	group = EC_GROUP_new(EC_GFp_mont_method()); 
	if (!group) ABORT;
	if (!BN_hex2bn(&p, SM2_p)) ABORT;
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
	if (!BN_bin2bn(pub_key, 32, x)) ABORT;
	if (!BN_bin2bn(pub_key+32, 32, y)) ABORT;
	if (!EC_POINT_set_affine_coordinates_GFp(group, P, x, y, ctx)) ABORT;
	if (!EC_POINT_is_on_curve(group, P, ctx)) ABORT;

	EC_KEY_set_public_key(eckey, P);

	/* create BER signature */
	/*
	b_sig_len = ECDSA_size(eckey);
	if ((signature = OPENSSL_malloc(b_sig_len)) == NULL) ABORT;

	// DER format: 		SEQUENCE TAG=0x30, module TAG=0x20
	// 0x30 len 0x02 len r 0x02 len s
	memcpy(signature, "\x30\x44\x02\x20", 4);
	memcpy(signature+4, sig, 32);
	memcpy(signature+4+32, "\x02\x20", 2);
	memcpy(signature+4+32+2, sig+32, 32);
	*/
#if 0
	s = ECDSA_SIG_new();
	s->r = x;
	s->s = y;
	if (!BN_bin2bn(sig, 32, x)) ABORT;
	if (!BN_bin2bn(sig + 32, 32, y)) ABORT;
	if ((b_sig_len = i2d_ECDSA_SIG(s, &signature)) < 0) ABORT;

	LOG_PRINT_HEX("Sig input: ", signature, b_sig_len);
	/* verify signature */
	if (SM2_verify(1, digest, 32, signature, b_sig_len, eckey) != 1)
	{
		ret = 0x02;
		OPENSSL_free(signature);
		goto builtin_err;
	}
#endif
	LOG_PRINT_HEX("Sig input: ", sig, sig_len);
	/* verify signature */
	if (SM2_verify(1, digest, 32, sig, sig_len, eckey) != 1)
	{
		ret = 0x02;
		OPENSSL_free(signature);
		goto builtin_err;
	}

	//OPENSSL_free(signature);
	LOG_PRINTF("ECVerify OK\n");
	ret = 0x00;
	
builtin_err:
	BN_free(p);
	BN_free(a);
	BN_free(b);
	BN_free(x);
	BN_free(y);
	BN_free(z);
	//ECDSA_SIG_free(s);
	EC_POINT_free(P);
	EC_KEY_free(eckey);
	EC_GROUP_free(group);
	BN_CTX_free(ctx);
	
	return ret;

}

/*************************************************************************************
sm2_pub_key_enc():
	SM2 encrypt with public key.
	
Parameters:

	Input : 
		msg �C The buffer which contains the data for which dynamic signature is received.
		msg_len - The length of the data buffer. 
		pub_Key �C The public key as retrieved from the certificate.
		pub_key_len �C The public key length.
		out - The cypher.
		out_len The cypher string length. 

	Return value: 
		0:   Indicates the digital signature decryption and verification SUCCESS. 
		Any value other than 0:  Indicates failure of the signature verification. 
**************************************************************************************/
unsigned char sm2_pub_key_enc(
	const unsigned char *msg,
	const unsigned short msg_len,
	const unsigned char  *pub_key,
	const unsigned short pub_key_len,
	unsigned char *out,
	int *out_len)
{
	BN_CTX *ctx = NULL;
	BIGNUM *p, *a, *b, *x, *y, *z, *k, *order;
	EC_GROUP *group;
	EC_POINT *P, *point;
	EC_KEY	*eckey = NULL;
	unsigned char	*C1, *C2, *C3;
	unsigned char	*buf, *t;
	unsigned char	ret; 
	int i;

	//CRYPTO_set_mem_debug_functions(0, 0, 0, 0, 0);
	CRYPTO_mem_ctrl(CRYPTO_MEM_CHECK_ON);
	ERR_load_crypto_strings();
	
	ctx = BN_CTX_new();
	if (!ctx) ABORT;

	/* Curve SM2 (Chinese National Algorithm) */
	p = BN_new();
	a = BN_new();
	b = BN_new();
	if (!p || !a || !b) ABORT;

	/* applications should use EC_GROUP_new_curve_GFp, so that the library gets to choose the EC_METHOD */
	group = EC_GROUP_new(EC_GFp_mont_method()); 
	if (!group) ABORT;
	if (!BN_hex2bn(&p, SM2_p)) ABORT;
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
	if (!BN_bin2bn(pub_key, 32, x)) ABORT;
	if (!BN_bin2bn(pub_key+32, 32, y)) ABORT;
	if (!EC_POINT_set_affine_coordinates_GFp(group, P, x, y, ctx)) ABORT;
	if (!EC_POINT_is_on_curve(group, P, ctx)) ABORT;

	EC_KEY_set_public_key(eckey, P);

	k = BN_new();
	order = BN_new();
	if (!k || !order) ABORT;
	if ((point = EC_POINT_new(group)) == NULL) ABORT;
	if (!EC_GROUP_get_order(group, order, ctx)) ABORT;

	/* get random k */
#ifndef TEST
	do {
		if (!BN_rand_range(k, order)) ABORT;
	}while (BN_is_zero(k));

#else
	if (!BN_hex2bn(&k, ENC_RND)) ABORT;
#endif
LOG_PRINTF("get random k:\n");
	BNPrintf(k);

	/* X1, Y1 */
	if (!EC_POINT_mul(group, point, k, NULL, NULL, ctx)) ABORT;

	if (!EC_POINT_point2bn(group, point, POINT_CONVERSION_UNCOMPRESSED, x, ctx)) ABORT;
LOG_PRINTF("point to bn test:\n");
	BNPrintf(x);

	if (!EC_POINT_get_affine_coordinates_GFp(group, point, x, y, ctx)) ABORT;

LOG_PRINTF("x1:\n");
	BNPrintf(x);
LOG_PRINTF("y1:\n");
	BNPrintf(y);

	C1 = malloc(65);
	*C1 = 0x04;
	BN_bn2bin(x, C1+1);
	BN_bn2bin(y, C1+33);
	LOG_PRINT_HEX("C1: ", C1, 65);
/*
	memcpy(out, C1, 65);
	*out_len = 65;
*/
	memcpy(out, C1 + 1, 64);
	*out_len = 64;
	free(C1);

	/* X2, Y2 */
	if (!EC_POINT_mul(group, point, NULL, P, k, ctx)) ABORT;
	if (!EC_POINT_get_affine_coordinates_GFp(group, point, x, y, ctx)) ABORT;
LOG_PRINTF("x2:\n");
	BNPrintf(x);
LOG_PRINTF("y2:\n");
	BNPrintf(y);

	/* t=KDF(x2||y2, klen) */
	buf = malloc(64);
	t = malloc(msg_len);
	BN_bn2bin(x, buf);
	BN_bn2bin(y, buf+32);
	KDF(buf, 64, msg_len, t);
	free(buf);
	LOG_PRINT_HEX("t: ", t, msg_len);


	/* C3=Hash(x2||M||y2) */
	C3 = malloc(32);
	buf = malloc(64 + msg_len);
	BN_bn2bin(x, buf);
	memcpy(buf+32, msg, msg_len);
	BN_bn2bin(y, buf+32+msg_len);
	sm3(buf, msg_len+64, C3);
	free(buf);
	LOG_PRINT_HEX("Hash of C3:", C3, 32);
	memcpy(out + *out_len, C3, 32);
	*out_len += 32;
	free(C3);

	/* C2=M^t */
	C2 = malloc(msg_len);
	for (i=0; i<msg_len; i++)
	{
		C2[i] = msg[i]^t[i];
	}
	free(t);
	LOG_PRINT_HEX("C2: ", C2, msg_len);
	memcpy(out+*out_len, C2, msg_len);
	*out_len += msg_len;
	free(C2);

	LOG_PRINTF("SM2 public key encrypt OK\n");
	ret = 0x00;
	
builtin_err:
	BN_free(p);
	BN_free(a);
	BN_free(b);
	BN_free(x);
	BN_free(y);
	BN_free(z);
	BN_free(k);
	//BN_free(order);
	EC_POINT_free(P);
	EC_POINT_free(point);
	EC_KEY_free(eckey);
	EC_GROUP_free(group);
	BN_CTX_free(ctx);
	
	return ret;

}

/*************************************************************************************
sm2_prv_key_dec():
	SM2 decrypt with private key.
	
Parameters:

	Input : 
		msg �C The buffer which contains the data for which dynamic signature is received.
		msg_len - The length of the data buffer. 
		pub_Key �C The public key as retrieved from the certificate.
		pub_key_len �C The public key length.
		out - The cypher.
		out_len The cypher string length. 

	Return value: 
		0:   Indicates the digital signature decryption and verification SUCCESS. 
		Any value other than 0:  Indicates failure of the signature verification. 
**************************************************************************************/
unsigned char sm2_prv_key_dec(
	const unsigned char *in,
	const unsigned short in_len,
	unsigned char *msg,
	unsigned short *msg_len,
	const unsigned char  *prv_key)
{
	BN_CTX *ctx = NULL;
	BIGNUM *p, *a, *b, *x, *y, *z, *k, *prv;
	EC_GROUP *group;
	EC_POINT *P, *pc1, *point;
	EC_KEY	*eckey = NULL;
	unsigned char	*C1, *C2, *C3;
	unsigned char	*buf, *t, *u;
	unsigned char	ret; 
	int i;

	//CRYPTO_set_mem_debug_functions(0, 0, 0, 0, 0);
	CRYPTO_mem_ctrl(CRYPTO_MEM_CHECK_ON);
	ERR_load_crypto_strings();
	
	ctx = BN_CTX_new();
	if (!ctx) ABORT;

	/* Curve SM2 (Chinese National Algorithm) */
	p = BN_new();
	a = BN_new();
	b = BN_new();
	if (!p || !a || !b) ABORT;

	/* applications should use EC_GROUP_new_curve_GFp, so that the library gets to choose the EC_METHOD */
	group = EC_GROUP_new(EC_GFp_mont_method()); 
	if (!group) ABORT;
	if (!BN_hex2bn(&p, SM2_p)) ABORT;
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

	k = BN_new();
	prv = BN_new();
	if (!k || !prv) ABORT;

	/* create private key */
	if (!BN_bin2bn(prv_key, 32, prv)) ABORT;
	EC_KEY_set_private_key(eckey, prv);


	/* C1 */
	C1 = (unsigned char*)in;
	if (!BN_bin2bn(C1, 65, x)) ABORT;
LOG_PRINTF("xss:\n");
	BNPrintf(x);
	if ((pc1 = EC_POINT_new(group)) == NULL) ABORT;
	if (EC_POINT_bn2point(group, x, pc1, ctx) == NULL) ABORT;
	if (!EC_POINT_is_on_curve(group, pc1, ctx)) ABORT;

	//S=[h]C1
	//if (!EC_POINT_is_at_infinity(group, S)) ABORT;

	/* X2, Y2 */
	if ((point = EC_POINT_new(group)) == NULL) ABORT;
	if (!EC_POINT_mul(group, point, NULL, pc1, prv, ctx)) ABORT;
	if (!EC_POINT_get_affine_coordinates_GFp(group, point, x, y, ctx)) ABORT;
LOG_PRINTF("x2:\n");
	BNPrintf(x);
LOG_PRINTF("y2:\n");
	BNPrintf(y);

	/* t=KDF(x2||y2, klen) */
	buf = malloc(64);
	*msg_len = in_len - 65 - 32;
	t = malloc(*msg_len);
	BN_bn2bin(x, buf);
	BN_bn2bin(y, buf+32);
	KDF(buf, 64, *msg_len, t);
	free(buf);
	LOG_PRINT_HEX("t: ", t, *msg_len);

	/* C2=M^t */
	C2 = (unsigned char*)in + 65;
	for (i=0; i<*msg_len; i++)
	{
		msg[i] = C2[i]^t[i];
	}
	free(t);
	LOG_PRINT_HEX("msg: ", msg, *msg_len);

	/* C3=Hash(x2||M||y2) */
	C3 = (unsigned char*)in + 65 + *msg_len;
	buf = malloc(64 + *msg_len);
	u = malloc(32);
	BN_bn2bin(x, buf);
	memcpy(buf+32, msg, *msg_len);
	BN_bn2bin(y, buf+32+*msg_len);
	sm3(buf, *msg_len+64, u);
	LOG_PRINT_HEX("Hash of u:", u, 32);
	if (memcmp(C3, u, 32)) {
		ret = 0x04;
	}
	else{
		LOG_PRINTF("SM2 private key decrypt OK\n");
		ret = 0x00;
	}
	free(buf);
	free(u);
	
builtin_err:
	BN_free(p);
	BN_free(a);
	BN_free(b);
	BN_free(x);
	BN_free(y);
	BN_free(z);
	BN_free(k);
	BN_free(prv);
	EC_POINT_free(P);
	EC_POINT_free(point);
	EC_POINT_free(pc1);
	EC_KEY_free(eckey);
	EC_GROUP_free(group);
	BN_CTX_free(ctx);
	
	return ret;

}

