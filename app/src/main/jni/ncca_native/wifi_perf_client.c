#include <netinet/in.h>    // for sockaddr_in
#include <sys/types.h>    // for socket
#include <sys/socket.h>    // for socket
#include <stdio.h>        // for printf
#include <stdlib.h>        // for exit
#include <string.h>        // for bzero
#include "unistd.h"
#include <arpa/inet.h>
#include "log.h"

#define HELLO_WORLD_SERVER_PORT    6666 
#define BUFFER_SIZE 1024
#define FILE_NAME_MAX_SIZE 512

#define CLIENT_CMD_1    1001 /* get ram data file 1 */
#define CLIENT_CMD_2    1002 /* get ram data file 2 */
#define CLIENT_CMD_3    1003 /* get ram data file 3 */
#define CLIENT_CMD_4    1004 /* send sm2 enc1 result file */
#define CLIENT_CMD_5    1005 /* send sm2 enc2 result file */
#define CLIENT_CMD_6    1006 /* send sm2 enc3 result file */
#define CLIENT_CMD_7    1007 /* send sm2 genkeypairs1 result file */
#define CLIENT_CMD_8    1008 /* send sm2 genkeypairs2 result file */
#define CLIENT_CMD_9    1009 /* send sm2 genkeypairs3 result file */
#define CLIENT_CMD_10    1010 /* send sm2 sign_check1 result file */
#define CLIENT_CMD_11    1011 /* send sm2 sign_check2 result file */
#define CLIENT_CMD_12    1012 /* send sm2 sign_check3 result file */
#define CLIENT_CMD_13    1013 /* send sm3 mac1 result file */
#define CLIENT_CMD_14    1014 /* send sm3 mac2 result file */
#define CLIENT_CMD_15    1015 /* send sm3 mac3 result file */
#define CLIENT_CMD_16    1016 /* send sm4 cbc1 result file */
#define CLIENT_CMD_17    1017 /* send sm4 cbc2 result file */
#define CLIENT_CMD_18    1018 /* send sm4 cbc3 result file */
#define CLIENT_CMD_19    1019 /* send sm4 ecb1 result file */
#define CLIENT_CMD_20    1020 /* send sm4 ecb2 result file */
#define CLIENT_CMD_21    1021 /* send sm4 ecb3 result file */

#define FILE_NAME_1 "/sdcard/gmper/ram_data_source1.txt"
#define FILE_NAME_2 "/sdcard/gmper/ram_data_source2.txt"
#define FILE_NAME_3 "/sdcard/gmper/ram_data_source3.txt"
#define FILE_NAME_4 "/sdcard/gmper/test_results/SM2_加密和解密_1.txt"
#define FILE_NAME_5 "/sdcard/gmper/test_results/SM2_加密和解密_2.txt"
#define FILE_NAME_6 "/sdcard/gmper/test_results/SM2_加密和解密_3.txt"
#define FILE_NAME_7 "/sdcard/gmper/test_results/SM2_密钥对生成_1.txt"
#define FILE_NAME_8 "/sdcard/gmper/test_results/SM2_密钥对生成_2.txt"
#define FILE_NAME_9 "/sdcard/gmper/test_results/SM2_密钥对生成_3.txt"
#define FILE_NAME_10 "/sdcard/gmper/test_results/SM2_签名和验签_1.txt"
#define FILE_NAME_11 "/sdcard/gmper/test_results/SM2_签名和验签_2.txt"
#define FILE_NAME_12 "/sdcard/gmper/test_results/SM2_签名和验签_3.txt"
#define FILE_NAME_13 "/sdcard/gmper/test_results/SM3_杂凑_1.txt"
#define FILE_NAME_14 "/sdcard/gmper/test_results/SM3_杂凑_2.txt"
#define FILE_NAME_15 "/sdcard/gmper/test_results/SM3_杂凑_3.txt"
#define FILE_NAME_16 "/sdcard/gmper/test_results/SM4_CBC_1.txt"
#define FILE_NAME_17 "/sdcard/gmper/test_results/SM4_CBC_2.txt"
#define FILE_NAME_18 "/sdcard/gmper/test_results/SM4_CBC_3.txt"
#define FILE_NAME_19 "/sdcard/gmper/test_results/SM4_ECB_1.txt"
#define FILE_NAME_20 "/sdcard/gmper/test_results/SM4_ECB_2.txt"
#define FILE_NAME_21 "/sdcard/gmper/test_results/SM4_ECB_3.txt"

void do_sendfile(int socket_fd, char *file_name);
void do_recvfile(int socket_fd, char *file_name);

int do_task(char *ip, int port, int cmd)
{
    char buffer[BUFFER_SIZE];
    if (ip == NULL || port < 0) {
        printf("Invalid args..\n");
        return -1;
    }

    //设置一个socket地址结构client_addr,代表客户机internet地址, 端口
    struct sockaddr_in client_addr;
    bzero(&client_addr,sizeof(client_addr)); //把一段内存区的内容全部设置为0
    client_addr.sin_family = AF_INET;    //internet协议族
    client_addr.sin_addr.s_addr = htons(INADDR_ANY);//INADDR_ANY表示自动获取本机地址
    client_addr.sin_port = htons(0);    //0表示让系统自动分配一个空闲端口
    //创建用于internet的流协议(TCP)socket,用client_socket代表客户机socket
    int client_socket = socket(AF_INET,SOCK_STREAM,0);
    if( client_socket < 0)
    {
        printf("Create Socket Failed!\n");
        return -1;
    }
    //把客户机的socket和客户机的socket地址结构联系起来
    if( bind(client_socket,(struct sockaddr*)&client_addr,sizeof(client_addr)))
    {
        printf("Client Bind Port Failed!\n"); 
        return -1;
    }

    //设置一个socket地址结构server_addr,代表服务器的internet地址, 端口
    struct sockaddr_in server_addr;
    bzero(&server_addr,sizeof(server_addr));
    server_addr.sin_family = AF_INET;
    if(inet_aton(ip, &server_addr.sin_addr) == 0) //服务器的IP地址来自程序的参数
    {
        printf("Server IP Address Error!\n");
        return -1;
    }
    server_addr.sin_port = htons(HELLO_WORLD_SERVER_PORT);
    socklen_t server_addr_length = sizeof(server_addr);

    //向服务器发起连接,连接成功后client_socket代表了客户机和服务器的一个socket连接
    if(connect(client_socket,(struct sockaddr*)&server_addr, server_addr_length) < 0)
    {
        printf("Can Not Connect To Server!\n");
        return -1;
    }

    int *p_num = (int *)buffer;
    *p_num = (int)htonl(cmd);
    if(send(client_socket, buffer, sizeof(int),0)<0) {
        printf("send cmd = %d failed.\n", cmd);
        return -1;
    }
    printf("send cmd = %d success.\n", cmd);

    switch(cmd) {
        case CLIENT_CMD_1:
            do_recvfile(client_socket, FILE_NAME_1);
            break;
        case CLIENT_CMD_2:
            do_recvfile(client_socket, FILE_NAME_2);
            break;
        case CLIENT_CMD_3:
            do_recvfile(client_socket, FILE_NAME_3);
            break;
        case CLIENT_CMD_4:
            do_sendfile(client_socket, FILE_NAME_4);
            break;
        case CLIENT_CMD_5:
            do_sendfile(client_socket, FILE_NAME_5);
            break;
        case CLIENT_CMD_6:
            do_sendfile(client_socket, FILE_NAME_6);
            break;
        case CLIENT_CMD_7:
            do_sendfile(client_socket, FILE_NAME_7);
            break;
        case CLIENT_CMD_8:
            do_sendfile(client_socket, FILE_NAME_8);
            break;
        case CLIENT_CMD_9:
            do_sendfile(client_socket, FILE_NAME_9);
            break;
        case CLIENT_CMD_10:
            do_sendfile(client_socket, FILE_NAME_10);
            break;
        case CLIENT_CMD_11:
            do_sendfile(client_socket, FILE_NAME_11);
            break;
        case CLIENT_CMD_12:
            do_sendfile(client_socket, FILE_NAME_12);
            break;
        case CLIENT_CMD_13:
            do_sendfile(client_socket, FILE_NAME_13);
            break;
        case CLIENT_CMD_14:
            do_sendfile(client_socket, FILE_NAME_14);
            break;
        case CLIENT_CMD_15:
            do_sendfile(client_socket, FILE_NAME_15);
            break;
        case CLIENT_CMD_16:
            do_sendfile(client_socket, FILE_NAME_16);
            break;
        case CLIENT_CMD_17:
            do_sendfile(client_socket, FILE_NAME_17);
            break;
        case CLIENT_CMD_18:
            do_sendfile(client_socket, FILE_NAME_18);
            break;
        case CLIENT_CMD_19:
            do_sendfile(client_socket, FILE_NAME_19);
            break;
        case CLIENT_CMD_20:
            do_sendfile(client_socket, FILE_NAME_20);
            break;
        case CLIENT_CMD_21:
            do_sendfile(client_socket, FILE_NAME_21);
            break;
    }

    //关闭socket
    close(client_socket);
    return 0;
}

void do_sendfile(int socket_fd, char *file_name) 
{
    char buffer[BUFFER_SIZE];
    FILE * fp = fopen(file_name,"r");

    if(NULL == fp )
    {
        printf("File:\t%s Not Found\n", file_name);
    }
    else
    {
        bzero(buffer, BUFFER_SIZE);
        int file_block_length = 0;
        //            while( (file_block_length = read(fp,buffer,BUFFER_SIZE))>0)
        while( (file_block_length = fread(buffer,sizeof(char),BUFFER_SIZE,fp))>0)
        {
            printf("file_block_length = %d\n",file_block_length);
            //发送buffer中的字符串到client_socket,实际是给客户端
            if(send(socket_fd,buffer,file_block_length,0)<0)
            {
                printf("Send File:\t%s Failed\n", file_name);
                break;
            }
            bzero(buffer, BUFFER_SIZE);
        }
        fclose(fp);
        printf("File:\t%s Transfer Finished\n",file_name);
    }
}

void do_recvfile(int socket_fd, char *file_name)
{
    char buffer[BUFFER_SIZE];
    FILE * fp = fopen(file_name,"w");

    if(NULL == fp )
    {
        printf("File:\t%s Cannot open to write data.\n", file_name);
    }
    else
    {
        bzero(buffer, BUFFER_SIZE);
        

        int length = 0;
        while( length = recv(socket_fd, buffer, BUFFER_SIZE,0))
        {
            if(length < 0)
            {
                printf("Recieve Data From client Failed!\n");
                break;
            }
            //        int write_length = write(fp, buffer,length);
            int write_length = fwrite(buffer,sizeof(char),length,fp);
            if (write_length<length)
            {
                printf("File:\t%s Write Failed\n", file_name);
                break;
            }
            bzero(buffer,BUFFER_SIZE);    
        }
        printf("Recieve File:\t %s From client Finished\n",file_name);
        fclose(fp);
    }
}
