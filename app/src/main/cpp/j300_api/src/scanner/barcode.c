/*
 ��_ ��ͷ��ȫ�ֱ����ͺ�����main ����Ҫ�õ���
 custom_read custom_wirte������������Ҫ����ʵ�ֵ�
*/


#include <stdio.h>
#include <stdlib.h> 
#include <unistd.h>  
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h> 
#include <termios.h>
#include <errno.h>
#include <string.h>
#include <common/comm_driver.h>
#include <pos_debug.h>
#include "scan/barcode_util.h"


#define J300_PROTOCOTOL_HEAD_SIZE    5
#define J300_PROTOCOTOL_TAIL_SIZE    2
#define J300_PROTOCOTOL_SIZE    (J300_PROTOCOTOL_HEAD_SIZE + J300_PROTOCOTOL_TAIL_SIZE)

#define J300_PROTOCOTOL_HEAD     0
#define J300_PROTOCOTOL_CMD_H    1
#define J300_PROTOCOTOL_CMD_L    2
#define J300_PROTOCOTOL_LEN_H    3
#define J300_PROTOCOTOL_LEN_L    4
#define J300_PROTOCOTOL_DATA     5

#define J300_PROTOCOTOL_BARCODE     0x02
#define J300_PROTOCOTOL_CMD_WR      0xE6
#define J300_PROTOCOTOL_CMD_RD      0xE7
#define J300PLUS_PROTOCOTOL_INIT    0xE8
#define J300PLUS_PROTOCOTOL_SCAN    0xE9
#define J300PLUS_PROTOCOTOL_CANCEL  0xEB
#define J300PLUS_PROTOCOTOL_RESET   0xEA
#define J300PLUS_PROTOCOTOL_GET     0xEC
#define J300_PROTOCOTOL_MASTER      0x00
#define J300_PROTOCOTOL_SLAVE       0x01

#define J300_RESPONSE_TIMEOUT_MS   100

static char j300_enable[8] = {0x02, 0xE1, 0x00, 0x00, 0x01, 0x01, 0xb2, 0x4d};
static unsigned short g_awhalfCrc16CCITT[16] =  /* CRC ���ֽ���ʽ�� */
{
    0x0000, 0x1021, 0x2042, 0x3063, 0x4084, 0x50a5, 0x60c6, 0x70e7,
    0x8108, 0x9129, 0xa14a, 0xb16b, 0xc18c, 0xd1ad, 0xe1ce, 0xf1ef
};

static char is_init_barcode = 0;

#define LOGSYS_FLAG
#ifdef LOGSYS_FLAG
#define barLog(X...)    printf(X)
#else
#define barLog(X...)
#endif

static void bl_disp_buf(char * title, char * buf, int size);
static int bl_J300plus_send_cmd(char cmd, char *data, int size);


//��ü�
static int _g_uart_fd = 0;


int Scanner_Disconnect()
{
    POS_CLOSE(_g_uart_fd);
}


int Scanner_Connect()
{
    _g_uart_fd = POS_OPEN(POS_USBSER_1);
    /*
    struct termios opt; 
    
    _g_uart_fd = open("/dev/ttyUSB0", O_RDWR | O_NOCTTY | O_NONBLOCK);
    if(_g_uart_fd == -1)
    {
        printf("open dev error\n");
        return -1;
    }

    tcgetattr(_g_uart_fd, &opt);
    bzero(&opt, sizeof(optopt));
    opt.c_cflag = B115200 | CS8 | CLOCAL | CREAD;
    opt.c_iflag = IGNPAR;
    opt.c_oflag = 0;
    opt.c_lflag = 0;
    
    if(tcsetattr(_g_uart_fd, TCSANOW, &opt) != 0 )
    {     
       printf("tcsetattr error");
       return -1;
    }

    fcntl(_g_uart_fd, F_SETFL, FNDELAY);
     */

    return 0;
}

int custom_write(const void * buf, int count)
{
    return POS_Write(_g_uart_fd, buf, count);
}

int custom_read(void * buf, size_t count)
{
    return POS_Read(_g_uart_fd, buf, count, 10);
}

void clear_uart_buf()
{
    char tmp;

    while(custom_read(&tmp, 1) > 0);
}

void init_barcode(void)
{
    unsigned char j300plus_init_data[8] = {0};

        //60s
    j300plus_init_data[3] = (unsigned char)(0xFF & 0x3c);
    j300plus_init_data[7] = (unsigned char)(0xFF & 0x3c);

    custom_write(j300_enable, sizeof(j300_enable));
    sleep(0.5);
    clear_uart_buf();
    bl_J300plus_send_cmd(J300PLUS_PROTOCOTOL_INIT, j300plus_init_data, 8);
    sleep(3.5);
    clear_uart_buf();
    //sleep(1);
}

static void bl_J300_crc16(unsigned char *pbyDataIn, unsigned long dwDataLen, unsigned int initcrc, unsigned int bigmode, unsigned char abyCrcOut[2])
{
    //unsigned short wCrc = initcrc;
    unsigned char byTemp;
    
    while(dwDataLen-- != 0)
    {
        byTemp = ((unsigned char)(initcrc >> 8)) >> 4;
        initcrc <<= 4;
        initcrc ^= g_awhalfCrc16CCITT[byTemp ^(*pbyDataIn / 16)];
        byTemp = ((unsigned char)(initcrc >> 8)) >> 4;
        initcrc <<= 4;
        initcrc ^= g_awhalfCrc16CCITT[byTemp ^(*pbyDataIn & 0x0f)];
        pbyDataIn++;
    }
    
    if(bigmode)
    {
        abyCrcOut[0] = initcrc / 256;
        abyCrcOut[1] = initcrc % 256;
    }
    else
    {
        abyCrcOut[0] = initcrc % 256;
        abyCrcOut[1] = initcrc / 256;
    }
}


static int bl_J300plus_send_cmd(char cmd, char *data, int size)
{
    char buf_local[15] = {0};//j300 plus ָ���15��bytes
    unsigned char crc_ret[2] = {0};
    int ret;
    
        
    /*����J300 Э�飬���*/
    /*ǰ5���ֽ�HEAD(1)+CMD(2)+LEN(2)*/
    buf_local[J300_PROTOCOTOL_HEAD] = J300_PROTOCOTOL_BARCODE;
    buf_local[J300_PROTOCOTOL_CMD_H] = cmd;
    buf_local[J300_PROTOCOTOL_CMD_L] = J300_PROTOCOTOL_MASTER;
    buf_local[J300_PROTOCOTOL_LEN_H] = 0;
    buf_local[J300_PROTOCOTOL_LEN_L] = (unsigned char)size;
    /*����*/
    memcpy(buf_local + J300_PROTOCOTOL_HEAD_SIZE, data, size);
    /*�������byte��CRC*/
    bl_J300_crc16((unsigned char *)buf_local + 1, size + J300_PROTOCOTOL_HEAD_SIZE - 1, 0, 0, crc_ret);
    buf_local[size+J300_PROTOCOTOL_HEAD_SIZE] = crc_ret[1];
    buf_local[size+J300_PROTOCOTOL_HEAD_SIZE+1] = crc_ret[0];
    
    /*Send data to J300*/
    ret = custom_write(buf_local, size + J300_PROTOCOTOL_HEAD_SIZE + J300_PROTOCOTOL_TAIL_SIZE);
    //bl_disp_buf("sent cmd", buf_local, size + J300_PROTOCOTOL_HEAD_SIZE + J300_PROTOCOTOL_TAIL_SIZE);

}

static void bl_disp_buf(char * title, char * buf, int size)
{
    int i;
    
    barLog("%s\n", title);
    
    if(buf == NULL)
        return;
        
    for(i = 0; i < size; i++)
    {
        barLog("%02x ", buf[i]);
    }
    
    barLog("\n");
}



/*-----------------------------------------------------------------------
 * FUNCTION NAME: bl_J300plus_get_scancode
 * DESCRIPTION: ���ڲ�ʹ�á���j300 plus��������
 * PARAMETER:  buf����ָ��
 *                    size���ݳ���
 *                    timeout_ms��ʱ������
 * RETURN:      -1: ����
 *                   >0: �������ݵĸ���
 *----------------------------------------------------------------------*/
static int bl_J300plus_get_scancode(char *buf, int size)
{
    unsigned long timeout = 0L;
    unsigned long resp_timeout = 0L;
    int resp_timeout_offset;
    unsigned char crc_ret[2] = {0};
    int ret, len, got_data_len = 0;
    char j300_protocol_buf[8] = {0};
    unsigned char j300_protocol_head_buf[J300_PROTOCOTOL_HEAD_SIZE] = {0};
    char j300plus_getcode_ret = 0;
    

    int head_len = 0;
    while(1)
    {

        ret = custom_read(j300_protocol_head_buf + head_len, J300_PROTOCOTOL_HEAD_SIZE - head_len);
        if(ret > 0)
        {
            head_len += ret;
            POS_DEBUG_WITHTAG("UsbserDemo","head ret=%d, head_len=%d\n", ret, head_len);
            //printf("---ret=%d\n", ret);
            //bl_disp_buf("---bl_J300plus_get_scancode recv head", j300_protocol_head_buf, J300_PROTOCOTOL_HEAD_SIZE);
            if(head_len == J300_PROTOCOTOL_HEAD_SIZE)
            {
                if((j300_protocol_head_buf[J300_PROTOCOTOL_HEAD] == J300_PROTOCOTOL_BARCODE) &&
                   (j300_protocol_head_buf[J300_PROTOCOTOL_CMD_H] == J300PLUS_PROTOCOTOL_SCAN) &&
                   (j300_protocol_head_buf[J300_PROTOCOTOL_CMD_L] == J300_PROTOCOTOL_SLAVE))
                {
                    //J300 plus ���ݽṹ: ���(1byte) + ���ݳ���
                    len = (unsigned char)j300_protocol_head_buf[J300_PROTOCOTOL_LEN_L];
                    got_data_len = len - 1; //��ȥһ���ֽڵ�ɨ����
                    bl_disp_buf("bl_J300plus_get_scancode recv head", j300_protocol_head_buf, J300_PROTOCOTOL_HEAD_SIZE);
                }
                head_len = 0;
                break;
            } else {
                continue;
            }
        }
        
    }
        

//    custom_read(&j300plus_getcode_ret, 1);
    POS_Read(_g_uart_fd, &j300plus_getcode_ret, 1, 1000);
    if (j300plus_getcode_ret != 0) {
        POS_DEBUG_WITHTAG("UsbserDemo","j300plus_getcode_ret=0x%02X\n", j300plus_getcode_ret);
        barLog("scan failed, ret=0x%02x", j300plus_getcode_ret);
        return -1;
    }
    

    //barLog("start read data len=%d\n", got_data_len);

    int recv_data_len = 0;
    do {
        ret = custom_read(buf + recv_data_len, got_data_len - recv_data_len);

        if(ret > 0)
            recv_data_len += ret;
        
    }while(recv_data_len != got_data_len);
    
    clear_uart_buf();
    
    return got_data_len;
}



/*-----------------------------------------------------------------------
 * FUNCTION NAME: scan
 * DESCRIPTION: ɨ�룬��ʱ60s
 * PARAMETER:   buf[in] �ڴ�ָ��
 *                     len[in] �ڴ泤��
 *                     get_data_len[out] ����ɨ�����ݳ���
 * RETURN:      0: �ɹ�
 *                  -1: ���豸ʧ��
 *                  -2: ��ʱû��ɨ����
 *----------------------------------------------------------------------*/
int scan(char * buf, int len, int *get_data_len)
{
    char j300plus_scan_type = 0x01; //����
    
    int ret;

    if ((buf == NULL) || (get_data_len == NULL))
        return -2;

    *get_data_len = 0;

    if (!is_init_barcode) {
        init_barcode();
        clear_uart_buf();
        is_init_barcode = 1;
    }
    clear_uart_buf();

    bl_J300plus_send_cmd(J300PLUS_PROTOCOTOL_SCAN, &j300plus_scan_type, 1);

    ret = bl_J300plus_get_scancode(buf, len);
    if (ret <= 0) {
        barLog("rec data = 0\n");
        return -2;
    }

    *get_data_len = ret;

    return 0;
}

int Scanner_Start(char * buf, int len, int *get_data_len, long timeout_ms)
{
    long start_timems = getCurrentTimeMS();

    while (1) {
        scan(buf, len, get_data_len);
        if (*get_data_len > 0) {
            break;
        }
        SVC_WAIT(3000);

        if (getCurrentTimeMS() >= start_timems + timeout_ms) {
            break;
        }
    }
}


/*
int main()
{
    char buf[100] = {0};
    int ret;
    int get_data_len;

    printf("Hello World\n");

    ret = _open_uart();
    if (ret != 0) {
       printf("Open dev failed\n");
       return 0;
    }
    
    while(1)
    {
        printf("\nPlease Scan ...\n");
        ret = scan(buf, 100, &get_data_len);
        if (ret == 0) {
            printf("(%d)%s\n", get_data_len, buf);
        } else if (ret == -1){
            printf("Open device failed!\n");
        } else {
            printf("Timeout!\n");
        }
        sleep(3);
    }
}
*/
