#include   <sys/stat.h>
#include   <sys/types.h>
#include   <sys/socket.h>
#include   <stdio.h>
#include   <malloc.h>
#include   <netdb.h>
#include   <fcntl.h>
#include   <unistd.h>
#include   <netinet/in.h>
#include   <arpa/inet.h>

#include "sm.h"
#include "sm3.h"
#include "sm4.h"
#include "sm_lib.h"
#include "sm2_api.h"

#define CLIENT_HELLO			1
#define SERVER_HELLO			2
#define SERVER_CERTIFICATE		3
#define SERVER_KEY_EXCHANGE		4
#define SERVER_CERTIFICATE_REQUEST	5
#define SERVER_HELLO_DONE		6
#define CLIENT_CERTIFICATE		7
#define CLIENT_KEY_EXCHANGE		8
#define CLIENT_CERTIFICATE_VERIFY	9
#define CLIENT_CHANGE_CIPHER_SPEC	10
#define CLIENT_FINISHED			11
#define SERVER_CHANGE_CIPHER_SPEC	12
#define SERVER_FINISHED			13
#define CLIENT_SEND_RECORD		14

typedef struct session_key_st {
	unsigned char client_write_mac_secret[32];
	unsigned char server_write_mac_secret[32];
	unsigned char client_write_key[16];
	unsigned char server_write_key[16];
	unsigned char client_write_iv[16];
	unsigned char server_write_iv[16];
}session_key;

extern int sm_main(int argc, char* ip, char *port, char *msg);


