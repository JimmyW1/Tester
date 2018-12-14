/**
 * sm2_api.h
 */
#ifndef SM2_API_H
#define SM2_API_H

#ifdef TEST
/* test data from sm2 spec */
#define ENTLA "0090"
#define IDA "414C494345313233405941484F4F2E434F4D"
#define SM2_p "8542D69E4C044F18E8B92435BF6FF7DE457283915C45517D722EDB8B08F1DFC3"
#define SM2_a "787968B4FA32C3FD2417842E73BBFEFF2F3C848B6831D7E0EC65228B3937E498"
#define SM2_b "63E4C6D3B23B0C849CF84241484BFE48F61D59A5B16BA06E6E12D1DA27C5249A"
#define SM2_n "8542D69E4C044F18E8B92435BF6FF7DD297720630485628D5AE74EE7C32E79B7"
#define SM2_Gx "421DEBD61B62EAB6746434EBC3CC315E32220B3BADD50BDC4C4E6C147FEDD43D"
#define SM2_Gy "0680512BCBB42C07D47349D2153B70C4E5D7FDFCBFA36EA1A85841B9E46E09A2"
#define ENC_RND "4C62EEFD6ECFC2B95B92FD6C3D9575148AFA17425546D49018E5388D49DD7B4F"

#define Pa_Xa "0AE4C7798AA0F119471BEE11825BE46202BB79E2A5844495E97C04FF4DF2548A"
#define Pa_Ya "7C0240F88F1CD4E16352A73C17B7F16F07353E53A176D684A9FE0C6BB798E857"
#define Pb_Xb "435B39CCA8F3B508C1488AFC67BE491A0F7BA07E581A0E4849A5CF70628A7E0A"
#define Pb_Yb "75DDBA78F15FEECB4C7895E2C1CDF5FE01DEBB2CDBADF45399CCF77BBA076A42"
#define DA    "128B2FA8BD433C6C068C8D803DFF79792A519A55171B1B650C23661D15897263"
#define DB    "1649AB77A00637BD5E2EFE283FBF353534AA7F7CB89463F208DDBC2920BB0DA0"

#else

#define ENTLA "0080"
#define IDA "31323334353637383132333435363738"
#define SM2_p "FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFF"
#define SM2_a "FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFC"
#define SM2_b "28E9FA9E9D9F5E344D5A9E4BCF6509A7F39789F515AB8F92DDBCBD414D940E93"
#define SM2_n "FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFF7203DF6B21C6052B53BBF40939D54123"
#define SM2_Gx "32C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7"
#define SM2_Gy "BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0"
#endif

#define ZA "\x00\x80\x31\x32\x33\x34\x35\x36\x37\x38\x31\x32\x33\x34\x35\x36\x37\x38\xFF\xFF\xFF\xFE\xFF\xFF\xFF\xFF\xFF\xFF\xFF\xFF\xFF\xFF\xFF\xFF\xFF\xFF\xFF\xFF\x00\x00\x00\x00\xFF\xFF\xFF\xFF\xFF\xFF\xFF\xFC\x28\xE9\xFA\x9E\x9D\x9F\x5E\x34\x4D\x5A\x9E\x4B\xCF\x65\x09\xA7\xF3\x97\x89\xF5\x15\xAB\x8F\x92\xDD\xBC\xBD\x41\x4D\x94\x0E\x93\x32\xC4\xAE\x2C\x1F\x19\x81\x19\x5F\x99\x04\x46\x6A\x39\xC9\x94\x8F\xE3\x0B\xBF\xF2\x66\x0B\xE1\x71\x5A\x45\x89\x33\x4C\x74\xC7\xBC\x37\x36\xA2\xF4\xF6\x77\x9C\x59\xBD\xCE\xE3\x6B\x69\x21\x53\xD0\xA9\x87\x7C\xC6\x2A\x47\x40\x02\xDF\x32\xE5\x21\x39\xF0\xA0"

#define CA_PRV_KEY "43F9483DBDC8FDBCE09A97B675F088FBA55EB74656C4DFBFD53813A807A31A2F"
#define CA_PUB_KEY "91432D56C744D85D5779C1C3EE2B4A97EEACDCEE0BC022DDBF5D53308E972CAD6F7FE3C3545A6B4F4442ECB601470E3FF2C426890BD7C6A3755BAE88E0BE08EB"
#define SITE_ENC_PRV_KEY "3E75F3C4DF19DE410DF52123E80EF0103A3DD955909810DF2B56FB75F60F3C07"
#define SITE_ENC_PUB_KEY "F5C59CE542DDDB3A6B2E40E7F84CDF0087608169C1D9EB1D42BE310F24697AB01715115175DE8A8D07A9617440A0982788669E9235E192D24667CF0C1F0023FF"
#define SITE_SIGN_PRV_KEY "96555B72687BF492089F9ECD7F1B236363AD66FDD53F220BC927999DA06B0DBE"
#define SITE_SIGN_PUB_KEY "585EFE623ABBD7FCB9A672FE11170F17E2F6274E39D29EFA229ECF1C2FA4079F88F0480224A059A2B0CEDFFFED760B7B250D3CDA2B6CD52E1FC41BE868EBFF35"
#define USER_ENC_PRV_KEY "C0DA11F3C894983BD3EA2EC42AFDA8EB07D926D476A2E4EF6318309567399A93"
#define USER_ENC_PUB_KEY "867A9174C18CCC655CE445FEEB03395014F8A70977691FC97CFA8CD76F8CB2F5F7812BE17B44F5F4C843747BD8F0E1B0FD420D19FB01AD748A14C4CA5BA0F010"
#define USER_SIGN_PRV_KEY "0D99B595CF5C16CFEF6EC928D2C505FB305B9F738036E97E2E397EA0A11E5190"
#define USER_SIGN_PUB_KEY "2231BC461947E699F29BA148FC2D484E727B449E944FE4F8428897A5BA5BCFC21E7096D2785F87055FF482F4A897929BC668FADB97DC61C9661513465EE82C32"


#define ABORT {\
	ret = 0x01;\
	LOG_PRINTF("Line: %d|%s\n", __LINE__, __FILE__);\
	goto builtin_err;\
}


/*************************************************************************************
sm2_sign():
	Sign the digest sent as part of the API input parameter.
	
Parameters:

	Input : 
		msg 每 The buffer which contains the data for which dynamic signature is received. 
		msg_len - The length of the data buffer. 
		prv_Key 每 The private key as retrieved from the certificate. 
		prv_key_len 每 The private key length. 
		pub_Key 每 The public key as retrieved from the certificate. 
		pub_key_len 每 The public key length. 
		sig - The digital signature string as received in the key certificate. 
		sig_len 每 The digital signature string length. 

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
	unsigned int *sig_len);


/*************************************************************************************
sm2_verify():
	Validate the SM2 signature for the message sent as part of the API input parameter.
	
Parameters:

	Input : 
		pucMsg 每 The buffer which contains the data for which dynamic signature is received. 
		usMsg_len - The length of the data buffer. 
		pucPub_Key 每 The public key as retrieved from the certificate. 
		usPub_key_len 每 The public key length. 
		pucDigital_sig - The digital signature string as received in the key certificate. 
		usDyn_sig_len 每 The digital signature string length. 
		ucAlgo_indicator 每 The algorithm indicator indicating the value of the algorithm used for digital signature generation. 

	Return value: 
		0:   Indicates the digital signature decryption and verification SUCCESS. 
		Any value other than 0:  Indicates failure of the signature verification. 
**************************************************************************************/
unsigned char sm2_verify(
	const unsigned char *msg,
	const unsigned short msg_len,
	const unsigned char  *pub_Key,
	const unsigned short pub_key_len,
	const unsigned char *sig,
	const unsigned short sig_len);

/*************************************************************************************
sm2_pub_key_enc():
	SM2 encrypt with public key.
	
Parameters:

	Input : 
		msg 每 The buffer which contains the data for which dynamic signature is received. 
		msg_len - The length of the data buffer. 
		pub_Key 每 The public key as retrieved from the certificate. 
		pub_key_len 每 The public key length. 
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
	int *out_len);

/*************************************************************************************
sm2_prv_key_dec():
	SM2 decrypt with private key.
	
Parameters:

	Input : 
		msg 每 The buffer which contains the data for which dynamic signature is received. 
		msg_len - The length of the data buffer. 
		pub_Key 每 The public key as retrieved from the certificate. 
		pub_key_len 每 The public key length. 
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
	const unsigned char  *prv_key);

#endif /* sm2_api.h */
