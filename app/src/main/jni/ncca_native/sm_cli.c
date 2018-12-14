#include  "sm_cli.h"
#include  "log.h"

int sm_main(int argc, char* ip, char *port, char *msg)
{

	LOGI("sm_main--------");

	int	sockfd;
	int	ssl_status = 1;
	int is_ssl_connect_success = 0;

	if (!ip || !port){
		printf("usage: sm_cli addr port [send message]\n");
		return -1;
	}

	if ((sockfd = connect_host(ip, atoi(port))) < 0){
		printf("Connect host %s:%s failed\n", ip, port);
        return -1;
	}

	is_ssl_connect_success = 0;
	while (1){
		switch(ssl_status){
			case CLIENT_HELLO:
				client_hello(sockfd);
				ssl_status ++;
				break;
			case SERVER_HELLO:
				server_hello(sockfd);
				ssl_status ++;
				break;
			case SERVER_CERTIFICATE:
				server_certificate(sockfd);
				ssl_status ++;
				break;
			case SERVER_KEY_EXCHANGE:
				server_key_exchange(sockfd);
				ssl_status ++;
				break;
			case SERVER_CERTIFICATE_REQUEST:
				server_certificate_request(sockfd);
				ssl_status ++;
				break;
			case SERVER_HELLO_DONE:
				server_hello_done(sockfd);
				ssl_status ++;
				break;
			case CLIENT_CERTIFICATE:
				client_certificate(sockfd);
				ssl_status ++;
				break;
			case CLIENT_KEY_EXCHANGE:
				client_key_exchange(sockfd);
				ssl_status ++;
				break;
			case CLIENT_CERTIFICATE_VERIFY:
				client_certificate_verify(sockfd);
				ssl_status ++;
				break;
			case CLIENT_CHANGE_CIPHER_SPEC:
				client_change_cipher_spec(sockfd);
				ssl_status ++;
				break;
			case CLIENT_FINISHED:
				client_finished(sockfd);
				ssl_status ++;
				break;
			case SERVER_CHANGE_CIPHER_SPEC:
				server_change_cipher_spec(sockfd);
				ssl_status ++;
				break;
			case SERVER_FINISHED:
				server_finished(sockfd);
				ssl_status ++;
				break;
			case CLIENT_SEND_RECORD:
				printf("---Exec CLIENT_SEND_RECORD--\n");
//				if (argc == 3)
//					client_send_record(sockfd, NULL, 0);
//				else

				client_send_record(sockfd, "Hello",  5);
                is_ssl_connect_success = 1;

				ssl_status ++;
				printf("---Exec CLIENT_SEND_RECORD END--\n");
				break;
			default:
                if (is_ssl_connect_success) {
					return 0;
				} else {
					return -1;
				}
		}
	}
}

int connect_host(char *ip, int port)
{
	int	sockfd = 0;
	struct	sockaddr_in addr;
	struct	hostent *phost;

	if ((sockfd = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
		printf("Init socket error! errno=%s\n", strerror(errno));
		return -1;
	}

	bzero(&addr,sizeof(addr));
	addr.sin_family=AF_INET;
	addr.sin_port=htons(port);
	addr.sin_addr.s_addr=inet_addr(ip);
	if(addr.sin_addr.s_addr == INADDR_NONE) {
		phost = (struct hostent*)gethostbyname(ip);
		if(phost==NULL) {
			printf("Init socket s_addr error! errno=%s\n", strerror(errno));
			return -1;
		}
		addr.sin_addr.s_addr = ((struct in_addr*)phost->h_addr)->s_addr;
	}
	if (connect(sockfd, (struct sockaddr*)&addr, sizeof(addr)) < 0){
		printf("Connect host error! errno=%s\n", strerror(errno));
		return -1;
	}
	else
		return sockfd;

}


