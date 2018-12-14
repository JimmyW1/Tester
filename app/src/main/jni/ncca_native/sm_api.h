/**
 * sm_api.h
 */
#ifndef SM_API_H
#define SM_API_H

typedef enum
{
	SM_SUCCESS = 0,

	SM_MEM_ERROR,
}SM_RET;

/*************************************************************************************
ucSM2Validate():
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
unsigned char ucSM2Validate(
	const unsigned char *pucMsg,
	const unsigned short usMsg_len,
	const unsigned char  *pucPub_Key,
	const unsigned short usPub_key_len,
	const unsigned char *pucDigital_sig,
	const unsigned short usDyn_sig_len,
	unsigned char ucAlgo_indicator);


/*************************************************************************************
ucSM3Hash():
	Generate the HASH output for a given input data using SM3 algorithm.

Parameters:  
	Input: 
		pucinput_digest 每 The input message buffer for hashing. 
		ulinput_length  -  The length of the input buffer data in bytes.
		
	Output: 
		pucoutput_digest  每 The 32 byte SM3 hash output buffer. 
		
	Return value: 
		0:   Indicates the digital signature decryption and verification SUCCESS. 
		Any value other than 0:  Indicates failure of the hashing process. If the failure is received then the 
		output hash buffer will not be used. 
**************************************************************************************/
unsigned char ucSM3Hash(
	unsigned char *pucinput_digest,
	unsigned long ulinput_length,
	unsigned char *pucoutput_digest);


/*************************************************************************************
Encrypt or decrypt the given input data using SM4 algorithm. 

Parameters:  
	Input: 
		ucmode -  1: encrypt, 0: decrypt the input digest. 
		puckey -  key.
		pucinput_digest 每 The input message buffer for encryption/decryption. 
		ulinput_length  -  The length of the input buffer data in bytes. 
 
	Output: 
		pucoutput_digest  每 The encrypted/decrypted output. 
		uloutput_length 每 The length of the output buffer data in bytes.
		
	Return value: 
		0:   Indicates the digital signature decryption and verification SUCCESS. 
		Any value other than 0:  Indicates failure of the encryption/decryption process. If the failure is 
		received then the output buffer will not be used. 
**************************************************************************************/
unsigned char ucSM4EncryptDecrypt(
	unsigned char ucmode,
	unsigned char *puckey,
	unsigned char *pucinput_digest,
	unsigned long ulinput_length,
	unsigned char *pucoutput_digest,
	unsigned long uloutput_length );


/*************************************************************************************
ucSMGetVersion():
	Provide  the version number of  the  shared SM  library. The format of the version 
	number string should be ※SMxx.yy.zz§. Eg: ※SM01.00.00§ 

Parameters:  
	Output: 
		pucver_string  每 This output buffer will be populated with the version number of the SM library. 

	Return value: 
		0:   Indicates the digital signature decryption and verification SUCCESS. 
		Any value other than 0 indicates failure in retrieval of the version number. 
**************************************************************************************/
unsigned char ucSMGetVersion(unsigned char *pucver_string);


#endif /* sm_api.h */
