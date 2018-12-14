#include "sm_cli.h"

#include  "log.h"

#define CLIENT_HELLO_HEAD	"160101002f0100002b0101"
#define TD_CHANGE_CIPHER_SPEC	"140101000101"
#define TD_FINISHED		"1601010050"
#define TD_RECORD		"17010100"

#define TEST_DATA
#ifdef TEST_DATA

//#define TD_CLIENT_HELLO "16010100330100002f010155ba495a078de776c3dea85b1f2c052cf475aad96ad9be7152c13f9c4693a461000002e0130100000400230000"
//#define TD_CLIENT_CERTIFICATE "16010103350b00033100032e0001bc308201b830820160020900e60f8356537d5a06300906072a8648ce3d040130818d310b300906035504061302434e310b30090603550408130253483111300f060355040713085368616e6768616931163014060355040a130d4b6f616c20536f66747761726531123010060355040b130953534c2047726f7570311230100603550403130945434344656d6f4341311e301c06092a864886f70d010901160f6563632d6361406b6f616c2e636f6d301e170d3137303930383036333030365a170d3237303930363036333030365a303d310b300906035504061302434e310b300906035504081302424a3121301f060355040a1318496e7465726e6574205769646769747320507479204c74643059301306072a8648ce3d020106082a8648ce3d030107034200048d7cf78904b08c394d2b4993f83f8860c72850dd609f67c5d32e34741bcbd42ce802471c2402d3fa11a55834bda48dcd08116eef1335e0b736c18156f467e036300906072a8648ce3d0401034700304402206d43e033e0c1851eff0d2bd670c965f602ca9f627c6275f1766d3e84a2170e1402201d85c9a1a7b76449cc1c8b5427c6efe2fd8a15d857c19d75d39901696cf0384700016c308201683082010f020900e60f8356537d5a09300906072a8648ce3d0401303d310b300906035504061302434e310b300906035504080c02424a3121301f060355040a0c18496e7465726e6574205769646769747320507479204c7464301e170d3137303930383036343632345a170d3237303930363036343632345a303d310b300906035504061302434e310b300906035504080c02424a3121301f060355040a0c18496e7465726e6574205769646769747320507479204c74643059301306072a8648ce3d020106082a811ccf5501822d03420004867a9174c18ccc655ce445feeb03395014f8a70977691fc97cfa8cd76f8cb2f5f7812be17b44f5f4c843747bd8f0e1b0fd420d19fb01ad748a14c4ca5ba0f010300906072a8648ce3d040103480030450220029bdf88aa0e013c68fbddbff94bd1ee0fbdc5de3e50c9bc715394a5931b8575022100944d11d87db6570471ab99b5891e04175b9b406bd8ec6e6444a37f6f4e77167b"
//#define TD_CLIENT_KEY_EXCHANGE "16010100a31000009f009d30819a022100a197f8f3cf21a179077da96ca6d9bfe0dba244e9dc0c1f62632e19e51a76d7f0022100d95ef0a7cc38a654f3dc489f91773ef4d8b5a4a5d5ed51b365a6cdb697209e9d042097c601233d2ac7cff1f8274d6ab100dc68e545b8d70a9a10893b9cf41bb3952b0430a6de9432352f21bb0429a3280eeb578a12a6cc7f3189ae12862c28e56480474c5f6c24b7c75a0cd41a8b3794fd578784"
//#define TD_CLIENT_CERTIFICATE_VERIFY "160101004d0f00004900473045022100a95aa7856b86b08505e115053d19ec30a08bf4059a69e331539d6af34d742e39022001ce9fdc3a8b98c7faad4a87d5218b6575bc800c501d87feb2074c22f2d87ecc"

#define TD_CLIENT_HELLO "16010100330100002f010155ba495a078de776c3dea85b1f2c052cf475aad96ad9be7152c13f9c4693a461000002e0130100000400230000"
#define TD_CLIENT_CERTIFICATE "16010106ba0b0006b60006b300023930820235308201d9a003020102020900c1afbc006193d4fd300c06082a811ccf55018375050030818d310b300906035504061302434e310b30090603550408130253483111300f060355040713085368616e6768616931163014060355040a130d4b6f616c20536f66747761726531123010060355040b130953534c2047726f7570311230100603550403130945434344656d6f4341311e301c06092a864886f70d010901160f6563632d6361406b6f616c2e636f6d301e170d3134303430333230313533395a170d3136313232373230313533395a308191310b300906035504061302434e310b30090603550408130253483111300f060355040713085368616e6768616931163014060355040a130d4b6f616c20536f66747761726531123010060355040b130953534c2047726f7570311430120603550403130b4543435369676e557365723120301e06092a864886f70d01090116116563632d75736572406b6f616c2e636f6d3059301306072a8648ce3d020106082a811ccf5501822d034200042231bc461947e699f29ba148fc2d484e727b449e944fe4f8428897a5ba5bcfc21e7096d2785f87055ff482f4a897929bc668fadb97dc61c9661513465ee82c32a31a301830090603551d1304023000300b0603551d0f0404030206c0300c06082a811ccf5501837505000348003045022100aa281a1234a076f7d35470d9449cd9063d082909f5ef91600a87a29ecb02fbd102202af4e2b6d6e280b7942f6e6ebb5cef94565a5d2e06c2f31e83761444285957a400023930820235308201d8a003020102020900c1afbc006193d4fe300c06082a811ccf55018375050030818d310b300906035504061302434e310b30090603550408130253483111300f060355040713085368616e6768616931163014060355040a130d4b6f616c20536f66747761726531123010060355040b130953534c2047726f7570311230100603550403130945434344656d6f4341311e301c06092a864886f70d010901160f6563632d6361406b6f616c2e636f6d301e170d3134303430333230313533395a170d3136313232373230313533395a308190310b300906035504061302434e310b30090603550408130253483111300f060355040713085368616e6768616931163014060355040a130d4b6f616c20536f66747761726531123010060355040b130953534c2047726f7570311330110603550403130a454343456e63557365723120301e06092a864886f70d01090116116563632d75736572406b6f616c2e636f6d3059301306072a8648ce3d020106082a811ccf5501822d03420004867a9174c18ccc655ce445feeb03395014f8a70977691fc97cfa8cd76f8cb2f5f7812be17b44f5f4c843747bd8f0e1b0fd420d19fb01ad748a14c4ca5ba0f010a31a301830090603551d1304023000300b0603551d0f040403020430300c06082a811ccf5501837505000349003046022100bbe86f10a4e1dc7ceac6d12f5756a4d7fb070896d776328b95dc76f291308254022100b203321448b9f522e93455a85cd73bcf832f3429603897e0e5d72870ebe8d45700023830820234308201d8a003020102020900a11022620354de13300c06082a811ccf55018375050030818d310b300906035504061302434e310b30090603550408130253483111300f060355040713085368616e6768616931163014060355040a130d4b6f616c20536f66747761726531123010060355040b130953534c2047726f7570311230100603550403130945434344656d6f4341311e301c06092a864886f70d010901160f6563632d6361406b6f616c2e636f6d301e170d3134303430333230313533395a170d3136313232373230313533395a30818d310b300906035504061302434e310b30090603550408130253483111300f060355040713085368616e6768616931163014060355040a130d4b6f616c20536f66747761726531123010060355040b130953534c2047726f7570311230100603550403130945434344656d6f4341311e301c06092a864886f70d010901160f6563632d6361406b6f616c2e636f6d3059301306072a8648ce3d020106082a811ccf5501822d0342000491432d56c744d85d5779c1c3ee2b4a97eeacdcee0bc022ddbf5d53308e972cad6f7fe3c3545a6b4f4442ecb601470e3ff2c426890bd7c6a3755bae88e0be08eba31d301b300c0603551d13040530030101ff300b0603551d0f040403020186300c06082a811ccf5501837505000348003045022100b9cc1193fbd8117501a73027ed92f96830c779fe5086f9d6d556009b68f7b0f702201f9ddfa67fef9bfb9e7ea6fa3dbf1d317c4539eda63c41b8b4ec02e8b43e92fd"
#define TD_CLIENT_KEY_EXCHANGE "16010100a31000009f009d30819a022100867edc1fdba027200cfcc865f1c2afbacbc571e9041c6ec603b66848068dd05c022100b169b6087c8600c014d223f1e58dd6be2b7e6d02b2ad660814222782775ebf1d0420569eb22a4516f87c8616b3bb5bc09dcc4ef16dd27b019455a1d864e0886f6d55043020222b5ff42acd0415c4ef7b505ee6fa9178c56ef5d1de980d03f229fc11d826d1e2ca86595c3aeda53afffbb12e5aa9"
#define TD_CLIENT_CERTIFICATE_VERIFY "160101004d0f000049004730450221009e797d1bd6015eb29230df22ea14d537e6dfb70f14119fc365b3c7e7eec1045802205110f81ed5b099e4726e8dc28cb3b078686e0ac47226f55f69fe9761d21db701"
#define TD_PRE_MASTER_SECRET "0101c3686ceb4a55dd120cd59b5fadf51adfa86bc508679f88c4d549f60a86933f575d3cae354a13b2efe669a1c9afb6"
#endif

int pack_record(unsigned char *record, int record_len, unsigned char *cipher, int *cipher_len, char header_type);

unsigned char sbuf[1024 * 4];
unsigned char rbuf[1024 * 4];
unsigned char client_rand[32], server_rand[32];
unsigned char server_enc_cert[1024];
unsigned char pre_master_secret[48];
unsigned char master_secret[48];
int enc_cert_len;
int seq_num;
sm3_context ctx;
session_key skey;


int client_hello(int sockfd)
{
	int slen = 0;

	memset(client_rand, 0, sizeof(client_rand));
	memset(server_rand, 0, sizeof(server_rand));
	memset(server_enc_cert, 0, sizeof(server_enc_cert));

	memset(sbuf, 0, sizeof(sbuf));
	sm3_starts(&ctx);

	slen = strlen(TD_CLIENT_HELLO);
	asc2bcd(TD_CLIENT_HELLO, sbuf, slen);

	//memcpy(client_rand, sbuf + 11, 32);
	rand_bytes(client_rand, 32);
	memcpy(sbuf + 11, client_rand, 32);

	slen /= 2;
	if (send(sockfd, sbuf, slen, 0) <= 0){
		herror("Send msg error!");
		return -1;
	}

	sm3_update(&ctx, sbuf + 5, slen - 5);
	
	LOG_PRINT_HEX("SSL-->client hello: ", sbuf, slen);
	return 0;	
}

int server_hello(int sockfd)
{
	int pack_len = 5;

	memset(rbuf, 0, sizeof(rbuf));
	if (recv_pack(sockfd, rbuf, pack_len) < 0)
		return -1;

	pack_len = rbuf[3] * 256 + rbuf[4];
	if (recv_pack(sockfd, rbuf + 5, pack_len) < 0)
		return -1;

	memcpy(server_rand, rbuf + 11, 32);

	sm3_update(&ctx, rbuf + 5, pack_len);

	LOG_PRINT_HEX("SSL<--server hello: ", rbuf, pack_len + 5);
	return 0;	
}

int server_certificate(int sockfd)
{
	int pack_len = 5, sign_cert_len;

	memset(rbuf, 0, sizeof(rbuf));
	if (recv_pack(sockfd, rbuf, pack_len) < 0)
		return -1;

	pack_len = rbuf[3] * 256 + rbuf[4];
	if (recv_pack(sockfd, rbuf + 5, pack_len) < 0)
		return -1;

	sign_cert_len = rbuf[13] * 256 + rbuf[14];
	enc_cert_len = rbuf[15 + sign_cert_len + 1] * 256 + rbuf[15 + sign_cert_len + 2];
	printf("sign[%d] enc[%d]\n", sign_cert_len, enc_cert_len);
	enc_cert_len += 3;
	memcpy(server_enc_cert, rbuf + 15 + sign_cert_len, enc_cert_len); //with 3 bytes length in the front

	sm3_update(&ctx, rbuf + 5, pack_len);

	LOG_PRINT_HEX("SSL<--server certificate: ", rbuf, pack_len + 5);
	//LOG_PRINT_HEX("server enc certificate: ", server_enc_cert, enc_cert_len);
	return 0;	
}

int server_key_exchange(int sockfd)
{
	int pack_len = 5, msg_len, sig_len, ret;
	unsigned char msg[1024], pub_key[64], sig[100];

	memset(msg, 0, sizeof(msg));
	memset(sig, 0, sizeof(sig));
	memset(rbuf, 0, sizeof(rbuf));
	if (recv_pack(sockfd, rbuf, pack_len) < 0)
		return -1;

	pack_len = rbuf[3] * 256 + rbuf[4];
	if (recv_pack(sockfd, rbuf + 5, pack_len) < 0)
		return -1;

	LOG_PRINT_HEX("SSL<--server key_exchange: ", rbuf, pack_len + 5);

	memcpy(msg, client_rand, 32);
	memcpy(msg + 32, server_rand, 32);
	memcpy(msg + 64, server_enc_cert, enc_cert_len);
	msg_len = enc_cert_len + 64;
	LOG_PRINT_HEX("msg for server key exchange: ", msg, msg_len);
	asc2bcd(SITE_SIGN_PUB_KEY, pub_key, 128);
	sig_len = rbuf[10];
	memcpy(sig, rbuf + 11, sig_len);
	if ((ret = sm2_verify(msg, msg_len, pub_key, 64, sig, sig_len)) != 0)
	{
		printf("server key exchange verify fail[%d]!\n", ret);
		return 1;	
	}

	sm3_update(&ctx, rbuf + 5, pack_len);

	return 0;	
}

int server_certificate_request(int sockfd)
{
	int pack_len = 5;

	memset(rbuf, 0, sizeof(rbuf));
	if (recv_pack(sockfd, rbuf, pack_len) < 0)
		return -1;

	pack_len = rbuf[3] * 256 + rbuf[4];
	if (recv_pack(sockfd, rbuf + 5, pack_len) < 0)
		return -1;

	sm3_update(&ctx, rbuf + 5, pack_len);

	LOG_PRINT_HEX("SSL<--server certificate request: ", rbuf, pack_len + 5);
	return 0;	
}

int server_hello_done(int sockfd)
{
/*
	int pack_len = 5;

	memset(rbuf, 0, sizeof(rbuf));
	if (recv_pack(sockfd, rbuf, pack_len) < 0)
		return -1;

	pack_len = rbuf[3] * 256 + rbuf[4];
	if (recv_pack(sockfd, rbuf + 5, pack_len) < 0)
		return -1;

	pack_len += 5;
	sm3_update(&ctx, rbuf, pack_len);

	LOG_PRINT_HEX("SSL<--server hello done: ", rbuf, pack_len);
*/
	return 0;	
}


int client_certificate(int sockfd)
{
	int slen = 0;

	memset(sbuf, 0, sizeof(sbuf));

#ifdef TEST_DATA
	slen = strlen(TD_CLIENT_CERTIFICATE);
	asc2bcd(TD_CLIENT_CERTIFICATE, sbuf, slen);
#else
#endif

	slen /= 2;
	if (send(sockfd, sbuf, slen, 0) <= 0){
		herror("Send msg error!");
		return -1;
	}

	sm3_update(&ctx, sbuf + 5, slen - 5);

	LOG_PRINT_HEX("SSL-->client certificate: ", sbuf, slen);
	return 0;	
}

int client_key_exchange(int sockfd)
{
	int slen = 0, clen = 0, buf_len = 0;
	unsigned char buf[1024], pub_key[64], cipher[512];

	memset(buf, 0, sizeof(buf));
	memset(sbuf, 0, sizeof(sbuf));

	slen = strlen(TD_CLIENT_KEY_EXCHANGE);
	asc2bcd(TD_CLIENT_KEY_EXCHANGE, sbuf, slen);

	//asc2bcd(TD_PRE_MASTER_SECRET, pre_master_secret, 96);
	memcpy(pre_master_secret, "\x01\x01", 2);
	rand_bytes(pre_master_secret + 2, 46);

	asc2bcd(SITE_ENC_PUB_KEY, pub_key, 128);
	if (sm2_pub_key_enc(pre_master_secret, 48, pub_key, 64, cipher, &clen))
		return 1;

	LOG_PRINT_HEX("cipher: ", cipher, clen);

	/* Add C1 with DER format */
	buf[buf_len] = 0x02;	/* INTEGER */
	buf_len += 1;
	if (cipher[0] > 127){
		memcpy(buf + buf_len, "\x21\x00", 2);	/* Add leading 0x00 if the high order bit is set to 1 */
		buf_len += 2;
	}
	else {
		memcpy(buf + buf_len, "\x20", 1);
		buf_len += 1;
	}
	memcpy(buf + buf_len, cipher, 32);
	buf_len += 32;

	buf[buf_len] = 0x02;	/* INTEGER */
	buf_len += 1;
	if (cipher[32] > 127){
		memcpy(buf + buf_len, "\x21\x00", 2);
		buf_len += 2;
	}
	else {
		memcpy(buf + buf_len, "\x20", 1);
		buf_len += 1;
	}
	memcpy(buf + buf_len, cipher + 32, 32);
	buf_len += 32;

	/* Add C3 with DER format */
	buf[buf_len] = 0x04;	/* OCTET STRING */
	buf_len += 1;
	buf[buf_len] = 32;
	buf_len += 1;
	memcpy(buf + buf_len, cipher + 64, 32);
	buf_len += 32;

	/* Add C2 with DER format */
	buf[buf_len] = 0x04;	/* OCTET STRING */
	buf_len += 1;
	buf[buf_len] = clen - 64 - 32;
	buf_len += 1;
	memcpy(buf + buf_len, cipher + 64 + 32, clen - 64 - 32);
	buf_len += clen -64 - 32;

	sbuf[4] = buf_len + 9;
	sbuf[8] = buf_len + 5;
	sbuf[10] = buf_len + 3;
	sbuf[13] = buf_len;
	memcpy(sbuf + 14, buf, buf_len);

	slen = buf_len + 14;
	//slen /= 2;
	if (send(sockfd, sbuf, slen, 0) <= 0){
		herror("Send msg error!");
		return -1;
	}
	
	sm3_update(&ctx, sbuf + 5, slen - 5);

	LOG_PRINT_HEX("SSL-->client key exchange: ", sbuf, slen);
	return 0;	
}

int client_certificate_verify(int sockfd)
{
	int slen = 0, ret = 0, siglen;
	unsigned char pub_key[64], prv_key[32];
	unsigned char sm3_hash[32], digest[32], signature[100];
	sm3_context ctx1;

	memset(sbuf, 0, sizeof(sbuf));

	memcpy((unsigned char *)&ctx1, (unsigned char *)&ctx, sizeof(sm3_context));
	sm3_finish(&ctx1, sm3_hash);
	LOG_PRINT_HEX("client cert verify hash msg: ", sm3_hash, 32);

	asc2bcd(USER_SIGN_PUB_KEY, pub_key, 128);
	asc2bcd(USER_SIGN_PRV_KEY, prv_key, 64);
	ret = sm2_sign(sm3_hash, 32, prv_key, 32, pub_key, 64, signature, &siglen);
	LOG_PRINT_HEX("signature: ", signature, siglen);

	slen = strlen(TD_CLIENT_CERTIFICATE_VERIFY);
	asc2bcd(TD_CLIENT_CERTIFICATE_VERIFY, sbuf, slen);
	sbuf[4] = siglen + 6;
	sbuf[8] = siglen + 2;
	sbuf[10] = siglen;
	memcpy(sbuf + 11, signature, siglen);

	slen = siglen + 11;
	if (send(sockfd, sbuf, slen, 0) <= 0){
		herror("Send msg error!");
		return -1;
	}

	sm3_update(&ctx, sbuf + 5, slen - 5);

	LOG_PRINT_HEX("SSL-->client certificate verify: ", sbuf, slen);
	return 0;	
}

int client_change_cipher_spec(int sockfd)
{
	int slen = 0;

	memset(sbuf, 0, sizeof(sbuf));

	slen = strlen(TD_CHANGE_CIPHER_SPEC);
	asc2bcd(TD_CHANGE_CIPHER_SPEC, sbuf, slen);

	slen /= 2;
	if (send(sockfd, sbuf, slen, 0) <= 0){
		herror("Send msg error!");
		return -1;
	}

	LOG_PRINT_HEX("SSL-->client change cipher spec: ", sbuf, slen);
	return 0;	
}

int client_finished(int sockfd)
{
	int slen = 0, record_len = 0;
	unsigned char seed[64], sm3_hash[32], verify_data[12];
	unsigned char finished[80];

	seq_num = 0;
	memset(sbuf, 0, sizeof(sbuf));

	asc2bcd(TD_FINISHED, sbuf, 10);

	sm3_finish(&ctx, sm3_hash);
	LOG_PRINT_HEX("client finished hash msg: ", sm3_hash, 32);

	memcpy(seed, client_rand, 32);
	memcpy(seed + 32, server_rand, 32);
	SM_PRF(pre_master_secret, 48, "master secret", 13, seed, 64, master_secret, 48);
	LOG_PRINT_HEX("pre master secret: ", pre_master_secret, 48);
	LOG_PRINT_HEX("master secret: ", master_secret, 48);

	SM_PRF(master_secret, 48, "client finished", 15, sm3_hash, 32, verify_data, 12);
	LOG_PRINT_HEX("verify data: ", verify_data, 12);

	memcpy(seed, server_rand, 32);
	memcpy(seed + 32, client_rand, 32);
	SM_PRF(master_secret, 48, "key expansion", 13, seed, 64, (unsigned char *)&skey, 128);
	LOG_PRINT_HEX("clien write mac key: ", skey.client_write_mac_secret, 32);
	LOG_PRINT_HEX("server write mac key: ", skey.server_write_mac_secret, 32);
	LOG_PRINT_HEX("clien write key: ", skey.client_write_key, 16);
	LOG_PRINT_HEX("server write key: ", skey.server_write_key, 16);
	LOG_PRINT_HEX("clien write iv: ", skey.client_write_iv, 16);
	LOG_PRINT_HEX("server write iv: ", skey.server_write_iv, 16);

	rand_bytes(finished, 16);
	memcpy(finished + 16, "\x14\x00\x00\x0C", 4);
	memcpy(finished + 20, verify_data, 12);

	pack_record(finished, 32, sbuf + 5, &record_len, 0x16);

	sbuf[4] = record_len;
	slen = 5 + record_len;
	if (send(sockfd, sbuf, slen, 0) <= 0){
		herror("Send msg error!");
		return -1;
	}

	LOG_PRINT_HEX("SSL-->client finished: ", sbuf, slen);
	return 0;	
}

int server_change_cipher_spec(int sockfd)
{
	int pack_len = 5;

	memset(rbuf, 0, sizeof(rbuf));
	if (recv_pack(sockfd, rbuf, pack_len) < 0)
		return -1;

	pack_len = rbuf[3] * 256 + rbuf[4];
	if (recv_pack(sockfd, rbuf + 5, pack_len) < 0)
		return -1;

	pack_len += 5;

	LOG_PRINT_HEX("SSL<--server change cipher spec: ", rbuf, pack_len);
	return 0;	
}

int server_finished(int sockfd)
{
	int pack_len = 5;

	memset(rbuf, 0, sizeof(rbuf));
	if (recv_pack(sockfd, rbuf, pack_len) < 0)
		return -1;

	pack_len = rbuf[3] * 256 + rbuf[4];
	if (recv_pack(sockfd, rbuf + 5, pack_len) < 0)
		return -1;

	pack_len += 5;

	LOG_PRINT_HEX("SSL<--server finished: ", rbuf, pack_len);
	return 0;	
}

int client_send_record(int sockfd, unsigned char *msg, int msg_len)
{
	int slen = 0, record_len;
	unsigned char buf[128];

	memset(sbuf, 0, sizeof(sbuf));

	asc2bcd(TD_RECORD, sbuf, 8);

	rand_bytes(buf, 16);
	memcpy(buf + 16, msg, msg_len);
	pack_record(buf, msg_len + 16, sbuf + 5, &record_len, 0x17);

	sbuf[4] = record_len;
	slen = 5 + record_len;
	if (send(sockfd, sbuf, slen, 0) <= 0){
		herror("Send msg error!");
		return -1;
	}

	LOG_PRINT_HEX("SSL-->client send record: ", sbuf, slen);
	return 0;	
}

int recv_pack(int sockfd, unsigned char *buf, int pack_len)
{
	int  ret = 0, rlen = pack_len;

	while (1){
		if ((ret = recv(sockfd, buf + (pack_len - rlen), rlen, 0)) <= 0){
			herror("Recv msg error!");
			return -1;
		}

		rlen -= ret;

		if (rlen > 0)
			continue;
		else
			break;
	}
	
	return 0;	
}

/*
 * IV 16
 * record
 * MAC 32
 * Padding
 */
int pack_record(unsigned char *record, int record_len, unsigned char *cipher, int *cipher_len, char header_type)
{
	int len = record_len;
	unsigned char padding;
	unsigned char *plain, *header;

printf("record len[%d]", record_len);
	plain = malloc(record_len + 32 + 16);
	header = malloc(13 + record_len);

	memcpy(plain, record, record_len);
LOG_PRINT_HEX("record plaintext: ", plain, len);

	// Calc MAC
	memset(header, 0, 7);
	header[7] = seq_num ++; //only work for seq num < 256
	header[8] = header_type; // type
	header[9] = 0x01; // version
	header[10] = 0x01; // version
	header[11] = 0x00; // length
	header[12] = record_len - 16; // length
	memcpy(header + 13, record + 16, record_len - 16);
LOG_PRINT_HEX("header: ", header, 13 + len - 16);

	sm3_hmac(skey.client_write_mac_secret, 32, header, 13 + record_len - 16, plain + record_len);
	len += 32;

	// Padding
	padding = 16 - (record_len % 16) - 1;
	memset(plain + len, padding, (int)padding + 1);
	len += padding + 1;

	// Encrypt
	sm4_encrypt_cbc(skey.client_write_key, skey.client_write_iv, plain, cipher, len);
	*cipher_len = len;

	LOG_PRINT_HEX("record plaintext: ", plain, len);
	LOG_PRINT_HEX("record cipher: ", cipher, len);

	free(plain);
	free(header);
}
