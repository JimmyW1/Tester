/*include files for SDK*/
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <stdarg.h>
#include <errno.h>
#include <ctype.h>

#include "scan/libbarcode.h"
#include "common/comm_driver.h"
#include "scan/barcode_util.h"

//================================================
/* ��.c �ļ����������ǹ��������������ڲ�ʹ�á�
* ���������������.h �ж���
*
* ����ǹ��Ϊ����VX680, ����C680, ����USB, ���ô����Ĵ��ࡣ
* ����C680 �ַ�1D��2D ����������
* ����VX680 �д������Ͳ��������������࣬������1D 2D,
*            ��Ϊ1D, 2D ָ��ͳһ��
* ����USBֻ��һ�����ࡣ
* ���ô��ڷ�COM1(9V) COM20(5V)�������ࡣ
* ����J300 ��1D 2D��J300����õ�����ǹ��C680 1D 2D�����´�½��
*/

/*
�������������:
���ļ��ڲ�ʹ�õĺ�������bl_ ����
l: local   b: barcode
*/
//#define LOGSYS_FLAG
#ifdef LOGSYS_FLAG
#define barLog(X...)    dbprintf(X)
#else
#define barLog(X...)
#endif

/*����ǹ����
B: barcode; L: local ����;  M: master ����;
IN: ��������ǹ; EX: ��������ǹ*/
typedef enum
{
    BL_M_IN_VX680 = 0,
    BL_M_IN_C680,
    BL_M_IN_UNKNOW,
    BL_M_EX_USB,
    BL_M_EX_COM,
    BL_M_EX_UNKNOW,
    BL_M_J300_COM1,
    BL_M_J300_COM20,
    BL_M_J300_USB,
    BL_M_NO_INIT   //û��ʼ��
} BL_M_TYPE;


/*����ǹ����
B: barcode; L: local ����; S: sub ����;
*/
typedef enum
{
    BL_S_VX680_KEY = 0, //��������VX680, ��֧��ָ��
    BL_S_VX680_CMD,
    BL_S_C680_1D,
    BL_S_C680_2D,
    BL_S_EX_USB,
    BL_S_EX_COM1,
    BL_S_EX_COM20,
    BL_S_EX_COM_EX, //��չcom4-com10
    BL_S_J300_1D,
    BL_S_J300_2D,
    BL_S_UNKNOW,
    BL_S_C680_2ND, //C680�ڶ���Ӧ��
    BL_S_J300_2ND, //J300 �ڶ���Ӧ�̣���C680�ڶ���Ӧ����ͬ
    BL_S_J300_PLUS, //����J300 PLUSͳһָ��Э��
} BL_S_TYPE;

/*����ǹhandle ������*/
typedef enum
{
    BL_NOT_I_OPENED = 0, //�豸���ǿ�򿪵�
    BL_I_OPENED, //�豸�ǿ�򿪵�
} BL_DEV_STATUS;


typedef struct lb_info
{
    BL_M_TYPE bl_master_type;
    BL_S_TYPE bl_sub_type;
    BL_DEV_STATUS bl_dev_status;
    const char *dev_name;
    int handle;
    int scan_timeout;
    enumChkFlag chk_flag;
} lb_info_t;

/*���ڲ���������ֵ*/

#define BL_SUCCESS  0 //�ɹ�
#define BL_FAILED   -1//ʧ��

#define IS_VALID_HANDLE(handle)    (((handle) > 0)? 1:0)
#define IS_INVALID_HANDLE(handle)   (((handle) <= 0)? 1:0)
#ifndef MAX
# define MAX(a, b) ((a) > (b) ? (a) : (b))
#endif

#ifndef MIN
# define MIN(a, b) ((a) < (b) ? (a) : (b))
#endif

#define IS_J300(type) (((type == BL_S_J300_1D)||(type == BL_S_J300_2D)||(type == BL_S_J300_2ND))? 1:0)
#define IS_J300_J300PLUS(type) (((type == BL_S_J300_1D)||(type == BL_S_J300_2D)||(type == BL_S_J300_2ND)||(type == BL_S_J300_PLUS))? 1:0)

/*j300Э�鳤��
head(1 byte)+cmd(2 bytes)+len(2 bytes)+data(real data len)+crc(2 bytes)*/
#define J300_PROTOCOTOL_HEAD_SIZE    5  //head(1 byte)+cmd(2 bytes)+len(2 bytes)
#define J300_PROTOCOTOL_TAIL_SIZE    2  //crc(2 bytes)
#define J300_PROTOCOTOL_SIZE    (J300_PROTOCOTOL_HEAD_SIZE + J300_PROTOCOTOL_TAIL_SIZE)

#define J300_PROTOCOTOL_HEAD     0  //J300Э�飬��0���ֽڱ�ʾ��ͷ������ǹ��02
#define J300_PROTOCOTOL_CMD_H    1  //J300Э�飬��1���ֽڱ�ʾ����ĸ�λ
#define J300_PROTOCOTOL_CMD_L    2  //J300Э�飬��2���ֽڱ�ʾ����ĵ�λ
#define J300_PROTOCOTOL_LEN_H    3  //J300Э�飬��3���ֽڱ�ʾ���ݳ��ȵĸ��ֽ�
#define J300_PROTOCOTOL_LEN_L    4  //J300Э�飬��4���ֽڱ�ʾ���ݳ��ȵĵ��ֽ�
#define J300_PROTOCOTOL_DATA     5  //J300Э�飬��5���ֽڱ�ʾ����


#define J300_PROTOCOTOL_BARCODE     0x02    //J300Э�飬����ǹ��ͷ��02
#define J300_PROTOCOTOL_CMD_WR      0xE6    //J300Э�飬д����
#define J300_PROTOCOTOL_CMD_RD      0xE7    //J300Э�飬������
#define J300PLUS_PROTOCOTOL_INIT    0xE8    //J300 PLUSЭ�飬��ʼ��
#define J300PLUS_PROTOCOTOL_SCAN    0xE9    //J300 PLUSЭ�飬ɨ��ָ��
#define J300PLUS_PROTOCOTOL_CANCEL  0xEB    //J300 PLUSЭ�飬ȡ��ɨ��ָ��
#define J300PLUS_PROTOCOTOL_RESET   0xEA    //J300 PLUSЭ�飬�ָ���������
#define J300PLUS_PROTOCOTOL_GET     0xEC    //J300 PLUSЭ�飬��ȡɨ������
#define J300_PROTOCOTOL_MASTER      0x00    //J300Э�飬��POS��J300
#define J300_PROTOCOTOL_SLAVE       0x01    //J300Э�飬��J300��POS

#define J300_RESPONSE_TIMEOUT_MS   100
//================================================

lb_info_t g_bl_info;

#ifdef _SHARED_LIB
//VX680 1D 2D ָ��ͳһ
char vx680_reset_cmd[8]; //�ָ�����
char vx680_scan_cmd[8];
char vx680_cancel_cmd[8];
char vx680_wakeup_cmd[8];
char vx680_sleep_cmd[8];
char vx680_cmd_response;

//NewLand_1D
char nl_1D_trigger[9];
char nl_1D_scan[9];
char nl_1D_cancel[9];
char nl_1D_check_model[9];
char nl_1D_factory_reset[9];
char nl_1D_cmd_response[7];

//NewLand_2D
char nl_2D_scan[2];
char nl_2D_cancel[2];
char nl_2D_check_model;
char nl_2D_cmd_response;

//j300
char j300_enable[8];
//char j300_disable[8];
char j300_read_max_data[11];
static unsigned short g_awhalfCrc16CCITT[16];
#else

//VX680 1D 2D ָ��ͳһ
//C680 2nd source ��VX680ָ����ͬ
static char vx680_reset_cmd[8]  = {0x78, 0x00, 0x90, 0x94, 0x01, 0x00, 0x9D, 0x78}; //�ָ�����
static char vx680_scan_cmd[8]   = {0x78, 0x00, 0x90, 0x90, 0x01, 0x00, 0x99, 0x78};
static char vx680_cancel_cmd[8] = {0x78, 0x00, 0x90, 0x9C, 0x01, 0x00, 0xA5, 0x78};
static char vx680_wakeup_cmd[8] = {0x78, 0x00, 0x90, 0x91, 0x01, 0x00, 0x9A, 0x78};
static char vx680_sleep_cmd[8]  = {0x78, 0x00, 0x90, 0x92, 0x01, 0x00, 0x9B, 0x78};
static char vx680_cmd_response  = 0x06;

//NewLand_1D
static char nl_1D_trigger[9]       = {0x7E, 0x00, 0x08, 0x01, 0x00, 0x00, 0xF5, 0xCB, 0x23};
static char nl_1D_scan[9]          = {0x7E, 0x00, 0x08, 0x01, 0x00, 0x02, 0x01, 0x02, 0xDA};
static char nl_1D_cancel[9]        = {0x7E, 0x00, 0x08, 0x01, 0x00, 0x02, 0x02, 0x32, 0xb9};
static char nl_1D_check_model[9]   = {0x7E, 0x00, 0x07, 0x01, 0x00, 0x00, 0x01, 0x00, 0x00};
static char nl_1D_factory_reset[9] = {0x7E, 0x00, 0x08, 0x01, 0x00, 0xd9, 0x80, 0x00, 0x00};
static char nl_1D_cmd_response[7]  = {0x02, 0x00, 0x00, 0x01, 0x00, 0x33, 0x31};

//NewLand_2D
static char nl_2D_scan[2] = {0x1b, 0x31};
static char nl_2D_cancel[2] = {0x1b, 0x30};
static char nl_2D_check_model = '?';
static char nl_2D_cmd_response = 0x06;

//J300
static char j300_enable[8] = {0x02, 0xE1, 0x00, 0x00, 0x01, 0x01, 0xb2, 0x4d};
//static char j300_disable[8] = {0x02, 0xE2, 0x00, 0x00, 0x01, 0x01, 0x5c, 0x9f};
static char j300_read_max_data[11] = {0x02, 0xE7, 0x00, 0x00, 0x04, 0x00, 0x00, 0xFF, 0xFF, 0x23, 0x28};
static unsigned short g_awhalfCrc16CCITT[16] =  /* CRC ���ֽ���ʽ�� */
{
    0x0000, 0x1021, 0x2042, 0x3063, 0x4084, 0x50a5, 0x60c6, 0x70e7,
    0x8108, 0x9129, 0xa14a, 0xb16b, 0xc18c, 0xd1ad, 0xe1ce, 0xf1ef
};


#endif

#ifdef _SHARED_LIB

void bl_InitPrm(void)
{
    //VX680 1D 2D ָ��ͳһ
    char _vx680_reset_cmd[8]  = {0x78, 0x00, 0x90, 0x94, 0x01, 0x00, 0x9D, 0x78}; //�ָ�����
    char _vx680_scan_cmd[8]   = {0x78, 0x00, 0x90, 0x90, 0x01, 0x00, 0x99, 0x78};
    char _vx680_cancel_cmd[8] = {0x78, 0x00, 0x90, 0x9C, 0x01, 0x00, 0xA5, 0x78};
    char _vx680_wakeup_cmd[8] = {0x78, 0x00, 0x90, 0x91, 0x01, 0x00, 0x9A, 0x78};
    char _vx680_sleep_cmd[8]  = {0x78, 0x00, 0x90, 0x92, 0x01, 0x00, 0x9B, 0x78};
    
    //NewLand_1D
    char _nl_1D_trigger[9]     = {0x7E, 0x00, 0x08, 0x01, 0x00, 0x00, 0xF5, 0xCB, 0x23};
    char _nl_1D_scan[9]        = {0x7E, 0x00, 0x08, 0x01, 0x00, 0x02, 0x01, 0x02, 0xDA};
    char _nl_1D_cancel[9]      = {0x7E, 0x00, 0x08, 0x01, 0x00, 0x02, 0x02, 0x32, 0xb9};
    char _nl_1D_check_model[9]   = {0x7E, 0x00, 0x07, 0x01, 0x00, 0x00, 0x01, 0x00, 0x00};
    char _nl_1D_factory_reset[9] = {0x7E, 0x00, 0x08, 0x01, 0x00, 0xd9, 0x80, 0x00, 0x00};
    char _nl_1D_cmd_response[7]  = {0x02, 0x00, 0x00, 0x01, 0x00, 0x33, 0x31};
    
    //NewLand_2D
    char _nl_2D_scan[2] = {0x1b, 0x31};
    char _nl_2D_cancel[2] = {0x1b, 0x30};
    
    //j300
    char _j300_enable[8] = {0x02, 0xE1, 0x00, 0x00, 0x01, 0x01, 0xb2, 0x4d};
    //char _j300_disable[8] = {0x02, 0xE2, 0x00, 0x00, 0x01, 0x01, 0x5c, 0x9f};
    char _j300_read_max_data[11] = {0x02, 0xE7, 0x00, 0x00, 0x04, 0x00, 0x00, 0xFF, 0xFF, 0x23, 0x28};
    unsigned short _g_awhalfCrc16CCITT[16] =  /* CRC ���ֽ���ʽ�� */
    {
        0x0000, 0x1021, 0x2042, 0x3063, 0x4084, 0x50a5, 0x60c6, 0x70e7,
        0x8108, 0x9129, 0xa14a, 0xb16b, 0xc18c, 0xd1ad, 0xe1ce, 0xf1ef
    };
    
    /*--------��ȫ�ֱ�����ֵ---------*/
    vx680_cmd_response  = 0x06;
    nl_2D_check_model = '?';
    nl_2D_cmd_response = 0x06;
    
    //VX680 1D 2D ָ��ͳһ
    memcpy(vx680_reset_cmd, _vx680_reset_cmd, 8);
    memcpy(vx680_scan_cmd, _vx680_scan_cmd, 8);
    memcpy(vx680_cancel_cmd, _vx680_cancel_cmd, 8);
    memcpy(vx680_wakeup_cmd, _vx680_wakeup_cmd, 8);
    memcpy(vx680_sleep_cmd, _vx680_sleep_cmd, 8);
    
    //NewLand_1D
    memcpy(nl_1D_trigger, _nl_1D_trigger, 9);
    memcpy(nl_1D_scan, _nl_1D_scan, 9);
    memcpy(nl_1D_cancel, _nl_1D_cancel, 9);
    memcpy(nl_1D_check_model, _nl_1D_check_model, 9);
    memcpy(nl_1D_factory_reset, _nl_1D_factory_reset, 9);
    memcpy(nl_1D_cmd_response, _nl_1D_cmd_response, 7);
    
    //NewLand_2D
    memcpy(nl_2D_scan, _nl_2D_scan, 2);
    memcpy(nl_2D_cancel, _nl_2D_cancel, 2);
    
    //j300
    memcpy(j300_enable, _j300_enable, 8);
    //memcpy(j300_disable, _j300_disable, 8);
    memcpy(j300_read_max_data, _j300_read_max_data, 11);
    memcpy(g_awhalfCrc16CCITT, _g_awhalfCrc16CCITT, 32);
    
    g_bl_info.handle = -1;
    g_bl_info.dev_name = NULL;
    g_bl_info.bl_master_type = BL_M_NO_INIT;
    g_bl_info.bl_sub_type = BL_S_UNKNOW;
    g_bl_info.bl_dev_status = BL_NOT_I_OPENED;
    g_bl_info.scan_timeout = 0;
    g_bl_info.chk_flag = BAR_CHK_DISABLE;
    
    barLog("[Barcode][%s]Init global para done!\n", __FUNCTION__);
}
#endif

/*-----------------------------------------------------------------------
 * FUNCTION NAME: bl_disp_buf
 * DESCRIPTION: ���ڲ�ʹ�á�����ʹ�ã���16���ƴ�ӡ����
 * RETURN:     ��
 * NOTE:
 *----------------------------------------------------------------------*/
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
 * FUNCTION NAME: bl_open_dev
 * DESCRIPTION: ���ڲ�ʹ�á����豸
 * PARAMETER:   nameΪ��:  ��ȫ�ֱ�������豸
 *                       �ǿգ���ָ�����ֵ��豸
 * RETURN:      0: �ɹ�
 *                   -1: �豸��ռ�û��ʧ��
 * NOTE:
 *----------------------------------------------------------------------*/
static int bl_open_dev(void)
{
    g_bl_info.handle = POS_OPEN(POS_USBSER_1);
    return BL_SUCCESS;
}


/*-----------------------------------------------------------------------
 * FUNCTION NAME: bl_close_dev
 * DESCRIPTION: ���ڲ�ʹ�á��ر��豸
 * PARAMETER:   none
 * RETURN:      0: �ɹ�
 *                   -1: ʧ��
 * NOTE:
 *----------------------------------------------------------------------*/
static int bl_close_dev(void)
{
    POS_CLOSE(g_bl_info.handle);
    g_bl_info.handle = -1;
    return BL_SUCCESS;
}

/*-----------------------------------------------------------------------
 * FUNCTION NAME: bl_J300_crc16
 * DESCRIPTION: ���ڲ�ʹ�á�J300 CRC16
 * PARAMETER:   pbyDataIn, ����ָ��
 *                     dwDataLen���ݳ���
 *                     initcrcԭʼУ��ֵ��һ��Ϊ0
 *                     bigmode���ģʽ��һ��Ϊ0
 *                     abyCrcOut���У����
 * RETURN:      ��
 * NOTE: J300�μ�CRCУ�����ʹ��"����"+"���ݳ���"+"��������"����CRC16У������
 *----------------------------------------------------------------------*/
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


/*-----------------------------------------------------------------------
 * FUNCTION NAME: bl_clear_barcode_fifo
 * DESCRIPTION: �ڲ��������Ѵ����������
 * RETURN:      none
 *----------------------------------------------------------------------*/
static void bl_clear_barcode_fifo(void)
{

    char buf[128];

    while (POS_Read(g_bl_info.handle, buf, 128, 10) > 0);
}

/*-----------------------------------------------------------------------
 * FUNCTION NAME: bl_clear_barcode_fifo
 * DESCRIPTION: �ڲ��������Ѵ��ں�J300�������ݶ����
 * RETURN:      none
 *----------------------------------------------------------------------*/
static void bl_J300_clear_barcode_fifo(void)
{
    bl_clear_barcode_fifo();
}



/*-----------------------------------------------------------------------
 * FUNCTION NAME: bl_send_cmd
 * DESCRIPTION: ���ڲ�ʹ�á�����ָ��ķ���
 * RETURN:      -1: ����
 *                       >=0: �ɹ����͵��ֽ���
 *----------------------------------------------------------------------*/
static int bl_send_cmd(char *cmd, int size)
{
    int ret;
    char ioctl_status[4];
    
        
    bl_clear_barcode_fifo();
    ret = POS_Write(g_bl_info.handle, cmd, size);

    SVC_WAIT(10);
    return ret;
}

/*-----------------------------------------------------------------------
 * FUNCTION NAME: bl_recv_response
 * DESCRIPTION: ���ڲ�ʹ�á�����ָ��ķ���
 * RETURN:      -1: ����
 *                       0: ��ʱδ�յ����ݻ��յ����ݲ�ȫ
 *                       >0: �յ����ݵĸ���
 * NOTE: ע��buf_size һ��������32
 *----------------------------------------------------------------------*/
static int bl_recv_response(char *buf, int size, int timeout_msec)
{
    char buf_local[32] = {0};
    int ret;
    unsigned long timeout = 0L;
    int got_data_len = 0;
    
    if((buf == NULL) || (size > 32))
    {
        barLog("[Barcode][%s]Input para err, return!\n", __FUNCTION__);
        return BL_FAILED;
    }

    //������0 ����
    timeout_msec = MAX(timeout_msec, 0);
    timeout = getCurrentTimeMS() + timeout_msec;
    
    do
    {
        ret = POS_Read(g_bl_info.handle, buf_local, size - got_data_len, 10);
        
        if(ret > 0)
        {
            memcpy((buf + got_data_len), buf_local, ret);
            got_data_len += ret;
            
            if(got_data_len == size)
                return got_data_len;
        }
        
        SVC_WAIT(10);
    }
    while(timeout > getCurrentTimeMS());
    
    return BL_SUCCESS;
}


/*-----------------------------------------------------------------------
 * FUNCTION NAME: bl_J300_send_data
 * DESCRIPTION: ���ڲ�ʹ�á���j300��������
 * PARAMETER:  buf����ָ��
 *                    size���ݳ���
 * RETURN:      -1: ����
 *                   >0: �������ݵĸ���
 *----------------------------------------------------------------------*/
static int bl_J300_send_data(char *buf, int size)
{
    char * buf_local = NULL;
    unsigned long timeout = 0L;
    unsigned char crc_ret[2] = {0};
    int ret, got_data_tag = 0;
    char j300_protocol_buf[8] = {0};
    
    /*�����Σ�J300Э�����ݳ���ռ�����ֽ�65535 */
    if((buf == NULL) || (size <= 0) || (size > 65535))
        return BL_FAILED;
        
        
    buf_local = (char *)malloc(size + J300_PROTOCOTOL_SIZE);
    
    if(buf_local <= 0)
        return BL_FAILED;
    else
        memset(buf_local, 0, size + J300_PROTOCOTOL_SIZE);
        
    /*����J300 Э�飬���*/
    /*ǰ5���ֽ�HEAD(1)+CMD(2)+LEN(2)*/
    buf_local[J300_PROTOCOTOL_HEAD] = J300_PROTOCOTOL_BARCODE;
    buf_local[J300_PROTOCOTOL_CMD_H] = J300_PROTOCOTOL_CMD_WR;
    buf_local[J300_PROTOCOTOL_CMD_L] = J300_PROTOCOTOL_MASTER;
    buf_local[J300_PROTOCOTOL_LEN_H] = size / 256;
    buf_local[J300_PROTOCOTOL_LEN_L] = size % 256;
    /*����*/
    memcpy(buf_local + J300_PROTOCOTOL_HEAD_SIZE, buf, size);
    /*�������byte��CRC*/
    bl_J300_crc16((unsigned char *)buf_local + 1, size + J300_PROTOCOTOL_HEAD_SIZE - 1, 0, 0, crc_ret);
    buf_local[size+J300_PROTOCOTOL_HEAD_SIZE] = crc_ret[1];
    buf_local[size+J300_PROTOCOTOL_HEAD_SIZE+1] = crc_ret[0];
    
    bl_clear_barcode_fifo();
    /*Send data to J300*/
    ret = POS_Write(g_bl_info.handle, buf_local, size + J300_PROTOCOTOL_HEAD_SIZE + J300_PROTOCOTOL_TAIL_SIZE);
    bl_disp_buf("[Barcode]J300 SEND", buf_local, size + J300_PROTOCOTOL_HEAD_SIZE + J300_PROTOCOTOL_TAIL_SIZE);
    
    if(buf_local)
        free(buf_local);
        
    if(ret < 0)
    {
        barLog("[Barcode][%s]send failed!\n", __FUNCTION__);
        bl_clear_barcode_fifo();
        return BL_FAILED;
    }
    
    /*��J300 ���շ��͵�response*/
    timeout = getCurrentTimeMS() + J300_RESPONSE_TIMEOUT_MS;
    
    do
    {
        ret = POS_Read(g_bl_info.handle, j300_protocol_buf, 1, 10);
        
        if((ret > 0) && (j300_protocol_buf[0] == J300_PROTOCOTOL_BARCODE))
        {
            /*�յ�J300Э���ͷ*/
            got_data_tag = 1;
            break;
        }
        
        SVC_WAIT(30);
    }
    while(timeout > getCurrentTimeMS());
    
    /*���û�յ���ͷ�����ش���*/
    if(got_data_tag != 1)
    {
        barLog("[Barcode][%s]send no reponse!\n", __FUNCTION__);
        bl_clear_barcode_fifo();
        return BL_FAILED;
    }
    else
    {
        /*���ճ���ͷ�������ಿ��*/
        ret = POS_Read(g_bl_info.handle, j300_protocol_buf + 1, 7, 10);
        
        if(ret >= 0)
            bl_disp_buf("[Barcode]reponse", j300_protocol_buf, ret + 1);
            
        if(ret != 7)
        {
            barLog("[Barcode][%s]send reponse size error ret = %d!\n", __FUNCTION__, ret);
            
            
            bl_clear_barcode_fifo();
            return BL_FAILED;
        }
        else /*Э������ԣ�����У����������ɹ���Ӧ�÷���02 E6 01 00 01 00 B3 0C*/
        {
            if((j300_protocol_buf[J300_PROTOCOTOL_CMD_H] != J300_PROTOCOTOL_CMD_WR) ||
               (j300_protocol_buf[J300_PROTOCOTOL_CMD_L] != J300_PROTOCOTOL_SLAVE) ||
               (j300_protocol_buf[J300_PROTOCOTOL_LEN_H] != 0) ||
               (j300_protocol_buf[J300_PROTOCOTOL_LEN_L] != 1) ||
               (j300_protocol_buf[J300_PROTOCOTOL_DATA] != 0))
            {
                barLog("[Barcode][%s]recv reponse error!\n", __FUNCTION__);
                
                if(ret >= 0)
                    bl_disp_buf("[Barcode]reponse", j300_protocol_buf, ret + 1);
                    
                return BL_FAILED;
            }
        }
    }
    
    return size;
}

/*-----------------------------------------------------------------------
 * FUNCTION NAME: bl_J300_recv_data
 * DESCRIPTION: ���ڲ�ʹ�á���j300��������
 * PARAMETER:  buf����ָ��
 *                    size���ݳ���
 *                    timeout_ms��ʱ������
 * RETURN:      -1: ����
 *                   >0: �������ݵĸ���
 *----------------------------------------------------------------------*/
static int bl_J300_recv_data(char *buf, int size, int timeout_ms)
{
    unsigned long timeout = 0L;
    unsigned long resp_timeout = 0L;
    int resp_timeout_offset;
    unsigned char crc_ret[2] = {0};
    int ret, got_data_len = 0;
    char j300_protocol_buf[11] = {0};
    char j300_protocol_head_buf[J300_PROTOCOTOL_HEAD_SIZE] = {0};
    
    /*�����Σ�J300Э�����ݳ���ռ�����ֽ�1<<16-1=65535 */
    if((buf == NULL) || (size <= 0) || (size > 65535))
        return BL_FAILED;
        
        
    /*����J300 Э�飬���*/
    /*HEAD(1)+CMD(2)+LEN(2)+data(4)+crc(2)*/
    j300_protocol_buf[J300_PROTOCOTOL_HEAD] = J300_PROTOCOTOL_BARCODE;
    j300_protocol_buf[J300_PROTOCOTOL_CMD_H] = J300_PROTOCOTOL_CMD_RD;
    j300_protocol_buf[J300_PROTOCOTOL_CMD_L] = J300_PROTOCOTOL_MASTER;
    j300_protocol_buf[J300_PROTOCOTOL_LEN_H] = 0;
    j300_protocol_buf[J300_PROTOCOTOL_LEN_L] = 4;
    j300_protocol_buf[5] = 0;
    j300_protocol_buf[6] = 0;
    j300_protocol_buf[7] = size / 256;
    j300_protocol_buf[8] = size % 256;
    /*�������byte��CRC*/
    bl_J300_crc16((unsigned char *)j300_protocol_buf + 1, 8, 0, 0, crc_ret);
    j300_protocol_buf[9] = crc_ret[1];
    j300_protocol_buf[10] = crc_ret[0];
    
    /*��J300 ��������*/
    timeout = getCurrentTimeMS() + timeout_ms;
    resp_timeout_offset = MIN(timeout_ms, J300_RESPONSE_TIMEOUT_MS);
    
    do
    {
        /*clear buffer in uart*/
        bl_clear_barcode_fifo();
        /*���Ͷ�����ָ��*/
        POS_Write(g_bl_info.handle, j300_protocol_buf, sizeof(j300_protocol_buf));
        bl_disp_buf("[Barcode]J300 rd cmd", j300_protocol_buf, sizeof(j300_protocol_buf));
        
        /*�������ݣ��Ƚ���HEAD(5 bytes)*/
        resp_timeout = getCurrentTimeMS() + resp_timeout_offset;
        
        do
        {
            ret = POS_Read(g_bl_info.handle, j300_protocol_head_buf, J300_PROTOCOTOL_HEAD_SIZE, 10);
            
            if(ret > 0)
            {
                if(ret == J300_PROTOCOTOL_HEAD_SIZE)
                {
                    if((j300_protocol_head_buf[J300_PROTOCOTOL_HEAD] == J300_PROTOCOTOL_BARCODE) &&
                       (j300_protocol_head_buf[J300_PROTOCOTOL_CMD_H] == J300_PROTOCOTOL_CMD_RD) &&
                       (j300_protocol_head_buf[J300_PROTOCOTOL_CMD_L] == J300_PROTOCOTOL_SLAVE))
                    {
                        got_data_len = j300_protocol_head_buf[J300_PROTOCOTOL_LEN_H] * 256 + j300_protocol_head_buf[J300_PROTOCOTOL_LEN_L];
                        barLog("[Barcode][%s]got data len = %d!\n", __FUNCTION__, got_data_len);
                        //bl_disp_buf("[Barcode] recv head", j300_protocol_head_buf, J300_PROTOCOTOL_HEAD_SIZE);
                    }
                    
                }
                
                break;
            }
            
            SVC_WAIT(20);
        }
        while(resp_timeout > getCurrentTimeMS());
        
        /*�յ���Ч����*/
        if(got_data_len > 0)
        {
            break;
        }
    }
    while(timeout > getCurrentTimeMS());
    
    if((got_data_len == 0) || (got_data_len > size))
    {
        bl_clear_barcode_fifo();
        barLog("[Barcode][%s]failed got_data_len[%d], size[%d]!\n", __FUNCTION__, got_data_len, size);
        return BL_FAILED;
    }
    
    SVC_WAIT(30);
    ret = POS_Read(g_bl_info.handle, buf, got_data_len, 10);
    bl_disp_buf("[Barcode]J300 recv data:", buf, ret);
    
    //barLog("[Barcode][%s]ret = %d!\n", __FUNCTION__, ret);
    bl_clear_barcode_fifo();
    
    return ret;
}

/*-----------------------------------------------------------------------
 * FUNCTION NAME: bl_J300plus_send_cmd
 * DESCRIPTION: ���ڲ�ʹ�á���j300plus����ָ��
 * PARAMETER:  cmd J300 plus ����
 *                    data J300 plus�������ݲ���
 *                    size ���ݳ���
 *                    timeout_ms ��ʱʱ�䣬��λ����
 * RETURN:      0: �ɹ�
 *                   -1: ʧ��
 *----------------------------------------------------------------------*/
static int bl_J300plus_send_cmd(char cmd, char *data, int size, int timeout_ms)
{
    char buf_local[15] = {0};//j300 plus ָ���15��bytes
    unsigned long timeout = 0L;
    unsigned char crc_ret[2] = {0};
    int ret, got_data_tag = 0;
    char j300_protocol_buf[8] = {0};
    
    if(IS_INVALID_HANDLE(g_bl_info.handle))
        return BL_FAILED;
        
    if((size < 0) || (size > 8))
        return BL_FAILED;
        
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
    
    bl_clear_barcode_fifo();
    /*Send data to J300*/
    ret = POS_Write(g_bl_info.handle, buf_local, size + J300_PROTOCOTOL_HEAD_SIZE + J300_PROTOCOTOL_TAIL_SIZE);
    bl_disp_buf("[Barcode]J300plus SEND", buf_local, size + J300_PROTOCOTOL_HEAD_SIZE + J300_PROTOCOTOL_TAIL_SIZE);
    
    if(ret < 0)
    {
        barLog("[Barcode][%s]send failed!\n", __FUNCTION__);
        bl_clear_barcode_fifo();
        return BL_FAILED;
    }
    
    /*��J300 ���շ��͵�response*/
    timeout = getCurrentTimeMS() + timeout_ms;
    
    do
    {
        ret = POS_Read(g_bl_info.handle, j300_protocol_buf, 1, 10);
        
        if((ret > 0) && (j300_protocol_buf[0] == J300_PROTOCOTOL_BARCODE))
        {
            /*�յ�J300Э���ͷ*/
            got_data_tag = 1;
            break;
        }
        
        SVC_WAIT(30);
    }
    while(timeout > getCurrentTimeMS());
    
    barLog("[Barcode][%s]cost time = %d ms\n", __FUNCTION__, (int)(getCurrentTimeMS() - (timeout - timeout_ms)));
    
    /*���û�յ���ͷ�����ش���*/
    if(got_data_tag != 1)
    {
        barLog("[Barcode][%s]send no reponse!\n", __FUNCTION__);
        bl_clear_barcode_fifo();
        return BL_FAILED;
    }
    else
    {
        /*���ճ���ͷ�������ಿ��*/
        ret = POS_Read(g_bl_info.handle, j300_protocol_buf + 1, 7, 10);
        
        if(ret >= 0)
            bl_disp_buf("[Barcode]j300plus reponse", j300_protocol_buf, ret + 1);
            
        if(ret != 7)
        {
            barLog("[Barcode][%s]send reponse size error ret = %d!\n", __FUNCTION__, ret);
            
            
            bl_clear_barcode_fifo();
            return BL_FAILED;
        }
        else /*Э������ԣ�����У����������ɹ���Ӧ�÷���02 E6 01 00 01 00 B3 0C*/
        {
            //��get_scan_codeָ��αװ��̽��ָ��(�ǲ���J300 Plus)
            if(cmd == J300PLUS_PROTOCOTOL_GET)
            {
                if((j300_protocol_buf[J300_PROTOCOTOL_CMD_L] == J300_PROTOCOTOL_SLAVE) ||
                   (j300_protocol_buf[J300_PROTOCOTOL_LEN_H] == 0) ||
                   (j300_protocol_buf[J300_PROTOCOTOL_LEN_L] == 1))
                    return BL_SUCCESS; //����ָ���з�����˵����J300 plus
                else
                    return BL_FAILED;
            }
            
            if((j300_protocol_buf[J300_PROTOCOTOL_CMD_H] != cmd) ||
               (j300_protocol_buf[J300_PROTOCOTOL_CMD_L] != J300_PROTOCOTOL_SLAVE) ||
               (j300_protocol_buf[J300_PROTOCOTOL_LEN_H] != 0) ||
               (j300_protocol_buf[J300_PROTOCOTOL_LEN_L] != 1) ||
               (j300_protocol_buf[J300_PROTOCOTOL_DATA] != 0))
            {
                barLog("[Barcode][%s]recv reponse error!\n", __FUNCTION__);
                
                if(ret >= 0)
                    bl_disp_buf("[Barcode]j300plus reponse", j300_protocol_buf, ret + 1);
                    
                return BL_FAILED;
            }
        }
    }
    
    return BL_SUCCESS;
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
static int bl_J300plus_get_scancode(char *buf, int size, int timeout_ms)
{
    unsigned long timeout = 0L;
    unsigned long resp_timeout = 0L;
    int resp_timeout_offset;
    unsigned char crc_ret[2] = {0};
    int ret, len, got_data_len = 0;
    char j300_protocol_buf[8] = {0};
    char j300_protocol_head_buf[J300_PROTOCOTOL_HEAD_SIZE] = {0};
    char j300plus_getcode_ret;
    
    /*�����Σ�J300Э�����ݳ���ռ�����ֽ�1<<16-1=65535 */
    if((buf == NULL) || (size <= 0) || (size > 65535))
        return BL_FAILED;
        
    if(IS_INVALID_HANDLE(g_bl_info.handle))
        return BL_FAILED;
        
    /*����J300 Э�飬���*/
    /*HEAD(1)+CMD(2)+LEN(2)+data(4)+crc(2)*/
    j300_protocol_buf[J300_PROTOCOTOL_HEAD] = J300_PROTOCOTOL_BARCODE;
    j300_protocol_buf[J300_PROTOCOTOL_CMD_H] = J300PLUS_PROTOCOTOL_GET;
    j300_protocol_buf[J300_PROTOCOTOL_CMD_L] = J300_PROTOCOTOL_MASTER;
    j300_protocol_buf[J300_PROTOCOTOL_LEN_H] = 0;
    j300_protocol_buf[J300_PROTOCOTOL_LEN_L] = 1;
    j300_protocol_buf[5] = 0x01;
    /*�������byte��CRC*/
    bl_J300_crc16((unsigned char *)j300_protocol_buf + 1, J300_PROTOCOTOL_HEAD_SIZE, 0, 0, crc_ret);
    j300_protocol_buf[6] = crc_ret[1];
    j300_protocol_buf[7] = crc_ret[0];
    
    /*��J300 ��������*/
    timeout = getCurrentTimeMS() + timeout_ms;
    resp_timeout_offset = MIN(timeout_ms, J300_RESPONSE_TIMEOUT_MS);
    
    do
    {
        len = 0;
        /*clear buffer in uart*/
        bl_clear_barcode_fifo();
        /*���Ͷ�����ָ��*/
        POS_Write(g_bl_info.handle, j300_protocol_buf, sizeof(j300_protocol_buf));
        bl_disp_buf("[Barcode]J300 plus send read cmd", j300_protocol_buf, sizeof(j300_protocol_buf));
        
        /*�������ݣ��Ƚ���HEAD(5 bytes)*/
        resp_timeout = getCurrentTimeMS() + resp_timeout_offset;
        
        do
        {
            ret = POS_Read(g_bl_info.handle, j300_protocol_head_buf, J300_PROTOCOTOL_HEAD_SIZE, 10);
            
            if(ret > 0)
            {
                if(ret == J300_PROTOCOTOL_HEAD_SIZE)
                {
                    if((j300_protocol_head_buf[J300_PROTOCOTOL_HEAD] == J300_PROTOCOTOL_BARCODE) &&
                       (j300_protocol_head_buf[J300_PROTOCOTOL_CMD_H] == J300PLUS_PROTOCOTOL_GET) &&
                       (j300_protocol_head_buf[J300_PROTOCOTOL_CMD_L] == J300_PROTOCOTOL_SLAVE))
                    {
                        //J300 plus ���ݽṹ: ���(1byte) + ��������
                        len = (unsigned char)j300_protocol_head_buf[J300_PROTOCOTOL_LEN_H] * 256 + (unsigned char)j300_protocol_head_buf[J300_PROTOCOTOL_LEN_L];
                        got_data_len = len - 1; //��ȥһ���ֽڵ�ɨ����
                        bl_disp_buf("bl_J300plus_get_scancode recv head", j300_protocol_head_buf, J300_PROTOCOTOL_HEAD_SIZE);
                    }
                }
                
                break;
            }
            
            SVC_WAIT(20);
        }
        while(resp_timeout > getCurrentTimeMS());
        
        /*�յ���Ч����*/
        if(got_data_len > 0)
        {
            break;
        }
    }
    while(timeout > getCurrentTimeMS());
    
    if((got_data_len == 0) || (got_data_len > size))
    {
        barLog("[Barcode][%s]failed got_data_len[%d], size[%d]!\n", __FUNCTION__, got_data_len, size);
        bl_clear_barcode_fifo();
        return BL_FAILED;
    }
    
    SVC_WAIT(30);
    //��һ���ֽڱ�ʾ�Ƿ�ɹ�
    ret = POS_Read(g_bl_info.handle, &j300plus_getcode_ret, 1, 10);
    
    if((ret != 1) || (j300plus_getcode_ret != 0x0))  //0x0 ���ݶ���
    {
        barLog("[Barcode][%s]failed ret=%d, j300plus_getcode_ret[0x%02x]!\n", __FUNCTION__, ret, j300plus_getcode_ret);
        bl_clear_barcode_fifo();
        return BL_FAILED;
    }
    
    ret = POS_Read(g_bl_info.handle, buf, got_data_len, 10);
    
    if(ret != got_data_len)
    {
        barLog("[Barcode][%s]failed read len=%d!\n", __FUNCTION__, ret);
        ret = BL_FAILED;
    }
    
    //barLog("[Barcode][%s]ret = %d!\n", __FUNCTION__, ret);
    bl_clear_barcode_fifo();
    
    return ret;
}

/*-----------------------------------------------------------------------
 * FUNCTION NAME: bl_J300plus_cancel_scan
 * DESCRIPTION: ���ڲ�ʹ�á�J300 plus ȡ��ɨ��, Ҳ�����j300���������
 * RETURN:      -1: ����
 *                   0: �ɹ�
 *----------------------------------------------------------------------*/
static int bl_J300plus_cancel_scan(void)
{
    char j300plus_cancel = 0x01;
    return bl_J300plus_send_cmd(J300PLUS_PROTOCOTOL_CANCEL, &j300plus_cancel, 1, 2000);
}


/*-----------------------------------------------------------------------
 * FUNCTION NAME: bl_J300_1d2d
 * DESCRIPTION: ���ڲ�ʹ�á��ж�J300����ǹ��1D ����2D
 * RETURN:     enumBarRet
 *----------------------------------------------------------------------*/
static enumBarRet bl_J300_1D2D(void)
{
    char buf[32] = {0};
    int ret;
    char j300_enable_rep[8] = {0x02, 0xE1, 0x01, 0x00, 0x01, 0x00, 0xD4, 0xD8};
    char J300_plus_cmd_data = 0x01;
    
    g_bl_info.bl_sub_type = BL_S_UNKNOW;
    
    if(bl_open_dev())
        return BAR_DEV_OPENED;
        
    bl_clear_barcode_fifo();
    //j300 barcode enable
    bl_send_cmd(j300_enable, sizeof(j300_enable));
    bl_recv_response(buf, 8, 200);
    
    if(memcmp(buf, j300_enable_rep, sizeof(j300_enable_rep)))
        return BAR_NO_DEV;
        
    //�ǲ���J300 PLUS
    ret = bl_J300plus_send_cmd(J300PLUS_PROTOCOTOL_GET, &J300_plus_cmd_data, 1, J300_RESPONSE_TIMEOUT_MS);
    
    if(ret == BL_SUCCESS)
    {
        g_bl_info.bl_sub_type = BL_S_J300_PLUS;
        return BAR_SUCCESS;
    }
    
    //�ǲ���1D
    bl_J300_send_data(nl_1D_check_model, sizeof(nl_1D_check_model));
    ret = bl_J300_recv_data(buf, 7, 350);
    
    //newland 1D ���ַ���02 00 00 XX XX XX XX
    if((ret == 7) && (buf[0] == 0x02) && (buf[1] == 0x00) && (buf[2] == 0x00))
    {
        g_bl_info.bl_sub_type = BL_S_J300_1D;
        return BAR_SUCCESS;
    }
    
    //�ǲ���2D
    bl_J300_send_data(&nl_2D_check_model, 1);
    ret = bl_J300_recv_data(buf, 1, 350);
    
    //newland 2D ���ַ���'!'
    if((ret == 1) && (buf[0] == '!'))
    {
        g_bl_info.bl_sub_type = BL_S_J300_2D;
        return BAR_SUCCESS;
    }
    
    
    //�ǲ���2nd��Ӧ��ģ��
    bl_J300_send_data(vx680_wakeup_cmd, sizeof(vx680_wakeup_cmd));
    ret = bl_J300_recv_data(buf, 1, 650);
    
    if((ret > 0) && (buf[0] == vx680_cmd_response))
    {
        g_bl_info.bl_sub_type = BL_S_J300_2ND;
        return BAR_SUCCESS;
    }
    
    return BAR_NO_DEV;
}



/*-----------------------------------------------------------------------
 * FUNCTION NAME: bl_check_external
 * DESCRIPTION: ���ڲ�ʹ�á������������ǹ
 * RETURN:      enumBarRet
 *----------------------------------------------------------------------*/
static enumBarRet bl_check_external(enumBarInitModel dev)
{
    enumBarRet ret = BAR_SUCCESS;

    return ret;
}


/*-----------------------------------------------------------------------
 * FUNCTION NAME: bl_set_timeout
 * DESCRIPTION: ���ڲ�ʹ�á����ó�ʱ
 * RETURN:
 *                       0: �ɹ�
 *                       -1: ʧ��
 *----------------------------------------------------------------------*/
static int bl_set_timeout(int scan_timeout_sec, int wakeup_time_sec)
{
    char buf[32] = {0};
    int ret, wake_ret = BL_FAILED;
    int retry = 3;
    
    /*��ʱ����Ч��Χ��5-255s*/
    scan_timeout_sec = MIN(scan_timeout_sec, 255);
    scan_timeout_sec = MAX(scan_timeout_sec, 5);
    wakeup_time_sec = MIN(wakeup_time_sec, 255);
    wakeup_time_sec = MAX(wakeup_time_sec, 5);
    
    
    /*����ʱ�䲻��С��ɨ�볬ʱʱ��*/
    wakeup_time_sec = MAX(wakeup_time_sec, scan_timeout_sec);
    
    g_bl_info.scan_timeout = scan_timeout_sec;
    
    barLog("[Barcode][%s]scantimeout=%d, wakeup=%d\n", __FUNCTION__, scan_timeout_sec, wakeup_time_sec);
    
    
    if((g_bl_info.bl_sub_type == BL_S_VX680_CMD) || (g_bl_info.bl_sub_type == BL_S_C680_2ND))
    {
        char vx680_wakeup_time_cmd[] = {0x78, 0x00, 0x90, 0x93, 0, 0, 0x9C, 0x78};
        char vx680_scan_timeout_cmd[] = {0x78, 0x00, 0x90, 0x95, 0, 0, 0x9E, 0x78};
        
        vx680_wakeup_time_cmd[4] = (unsigned char)(0xFF & (wakeup_time_sec / 256));
        vx680_wakeup_time_cmd[5] = (unsigned char)(0xFF & (wakeup_time_sec % 256));
        vx680_scan_timeout_cmd[4] = (unsigned char)(0xFF & (scan_timeout_sec / 256));
        vx680_scan_timeout_cmd[5] = (unsigned char)(0xFF & (scan_timeout_sec % 256));
        
        do
        {
            bl_send_cmd(vx680_wakeup_cmd, sizeof(vx680_wakeup_cmd));
            ret = bl_recv_response(buf, 1, 650);
            
            //���ѳɹ��ٷ�ɨ��ָ��
            if((ret > 0) && (buf[0] == vx680_cmd_response))
            {
                wake_ret = BL_SUCCESS;
                break;
            }
        }
        while(retry-- > 0);
        
        if(wake_ret != BL_SUCCESS)
        {
            barLog("[Barcode][%s]Wakeup VX680 failed!\n", __FUNCTION__);
            return BL_FAILED;
        }
        
        /*����֮����Ҫ��500ms, ���ܴ���ָ��*/
        SVC_WAIT(500);
        
        //����ɨ�볬ʱ
        bl_send_cmd(vx680_scan_timeout_cmd, sizeof(vx680_scan_timeout_cmd));
        ret = bl_recv_response(buf, 1, 500);
        
        if((ret <= 0) || (buf[0] != vx680_cmd_response))
        {
            barLog("[Barcode][%s]set vx680 timeout failed\n", __FUNCTION__);
            return BL_FAILED;
        }
        
        //����ǹҪ������������Ҫ��300ms ���ܼ���������
        SVC_WAIT(300);
        
        //���û���ʱ��
        bl_send_cmd(vx680_wakeup_time_cmd, sizeof(vx680_wakeup_time_cmd));
        ret = bl_recv_response(buf, 1, 500);
        
        if((ret <= 0) || (buf[0] != vx680_cmd_response))
        {
            barLog("[Barcode][%s]set vx680 wakeup time failed\n", __FUNCTION__);
            return BL_FAILED;
        }
        
        //����ǹҪ������������Ҫ��300ms ���ܼ���������
        SVC_WAIT(300);
        
    }
    /*C680 1Dû�����û���ʱ���ָ��*/
    else if(g_bl_info.bl_sub_type == BL_S_C680_1D)
    {
        char c680_1d_timeout_cmd[9] = {0x7E, 0, 0x08, 0x01, 0, 0x06, 0, 0, 0};
        
        c680_1d_timeout_cmd[6] = (unsigned char)(0xFF & (scan_timeout_sec % 256));
        
        /*C680 1D ���õ���ɨ�볬ʱ*/
        bl_send_cmd(c680_1d_timeout_cmd, sizeof(c680_1d_timeout_cmd));
        ret = bl_recv_response(buf, sizeof(nl_1D_cmd_response), 350);
        
        if((ret <= 0) || \
           (memcmp(buf, nl_1D_cmd_response, sizeof(nl_1D_cmd_response)) != 0))
        {
            barLog("[Barcode][%s]set c680 1d timeout failed\n", __FUNCTION__);
            return BL_FAILED;
        }
    }
    /*C680 2Dû�����û���ʱ���ָ��*/
    else if(g_bl_info.bl_sub_type == BL_S_C680_2D)
    {
        //�ڷ��͡�nls0313000=��ʱʱ��;������ʱʱ�䵥λΪ ms
        char c680_timeout_buf[32] = {0};
        
        //���������빦��
        bl_send_cmd("nls0006010;", 11);
        
        //�������ó�ʱָ��
        sprintf(c680_timeout_buf, "nls0313000=%d;", scan_timeout_sec * 1000);
        bl_send_cmd(c680_timeout_buf, sizeof(c680_timeout_buf));
        
        ret = bl_recv_response(buf, 1, 350);
        
        if((ret <= 0) || (buf[0] != nl_2D_cmd_response))
        {
            barLog("[Barcode][%s]set c680 2d timeout failed\n", __FUNCTION__);
            return BL_FAILED;
        }
        
        //�ر������빦��
        bl_send_cmd("nls0006000;", 11);
        //����ǹҪ������������Ҫ��300ms ���ܼ���������
        SVC_WAIT(300);
    }
    /*j300 1Dû�����û���ʱ���ָ��*/
    else if(g_bl_info.bl_sub_type == BL_S_J300_1D)
    {
        char c680_1d_timeout_cmd[9] = {0x7E, 0, 0x08, 0x01, 0, 0x06, 0, 0, 0};
        
        c680_1d_timeout_cmd[6] = (unsigned char)(0xFF & (scan_timeout_sec % 256));
        
        /*C680 1D ���õ���ɨ�볬ʱ*/
        bl_J300_send_data(c680_1d_timeout_cmd, sizeof(c680_1d_timeout_cmd));
        ret = bl_J300_recv_data(buf, sizeof(nl_1D_cmd_response), 350);
        
        if((ret <= 0) || \
           (memcmp(buf, nl_1D_cmd_response, sizeof(nl_1D_cmd_response)) != 0))
        {
            barLog("[Barcode][%s]set c680 1d timeout failed\n", __FUNCTION__);
            return BL_FAILED;
        }
    }
    /*J300 2Dû�����û���ʱ���ָ��*/
    else if(g_bl_info.bl_sub_type == BL_S_J300_2D)
    {
        int data_len;
        //�ڷ��͡�nls0313000=��ʱʱ��;������ʱʱ�䵥λΪ ms
        char c680_timeout_buf[64] = {0};
        
        //�������ó�ʱָ��
        sprintf(c680_timeout_buf, "nls0006010;nls0313000=%d;nls0006000;", scan_timeout_sec * 1000);
        data_len = strlen(c680_timeout_buf);
        
        bl_J300_send_data(c680_timeout_buf, data_len);
        
        //����ǹҪ������������Ҫ��300ms ���ܼ���������
        SVC_WAIT(300);
    }
    else if(g_bl_info.bl_sub_type == BL_S_J300_2ND)
    {
        char vx680_wakeup_time_cmd[] = {0x78, 0x00, 0x90, 0x93, 0, 0, 0x9C, 0x78};
        char vx680_scan_timeout_cmd[] = {0x78, 0x00, 0x90, 0x95, 0, 0, 0x9E, 0x78};
        
        vx680_wakeup_time_cmd[4] = (unsigned char)(0xFF & (wakeup_time_sec / 256));
        vx680_wakeup_time_cmd[5] = (unsigned char)(0xFF & (wakeup_time_sec % 256));
        vx680_scan_timeout_cmd[4] = (unsigned char)(0xFF & (scan_timeout_sec / 256));
        vx680_scan_timeout_cmd[5] = (unsigned char)(0xFF & (scan_timeout_sec % 256));
        
        do
        {
            bl_J300_send_data(vx680_wakeup_cmd, sizeof(vx680_wakeup_cmd));
            ret = bl_J300_recv_data(buf, 1, 650);
            
            //���ѳɹ��ٷ�ɨ��ָ��
            if((ret > 0) && (buf[0] == vx680_cmd_response))
            {
                wake_ret = BL_SUCCESS;
                break;
            }
        }
        while(retry-- > 0);
        
        if(wake_ret != BL_SUCCESS)
        {
            barLog("[Barcode][%s]Wakeup VX680 failed!\n", __FUNCTION__);
            return BL_FAILED;
        }
        
        /*����֮����Ҫ��500ms, ���ܴ���ָ��*/
        SVC_WAIT(500);
        
        //����ɨ�볬ʱ
        bl_J300_send_data(vx680_scan_timeout_cmd, sizeof(vx680_scan_timeout_cmd));
        ret = bl_J300_recv_data(buf, 1, 500);
        
        if((ret <= 0) || (buf[0] != vx680_cmd_response))
        {
            barLog("[Barcode][%s]set vx680 timeout failed\n", __FUNCTION__);
            return BL_FAILED;
        }
        
        //����ǹҪ������������Ҫ��300ms ���ܼ���������
        SVC_WAIT(300);
        
        //���û���ʱ��
        bl_J300_send_data(vx680_wakeup_time_cmd, sizeof(vx680_wakeup_time_cmd));
        ret = bl_J300_recv_data(buf, 1, 500);
        
        if((ret <= 0) || (buf[0] != vx680_cmd_response))
        {
            barLog("[Barcode][%s]set vx680 wakeup time failed\n", __FUNCTION__);
            return BL_FAILED;
        }
        
        //����ǹҪ������������Ҫ��300ms ���ܼ���������
        SVC_WAIT(300);
    }
    else if(g_bl_info.bl_sub_type == BL_S_J300_PLUS)
    {
        char j300plus_init_data[8] = {0};
        
        j300plus_init_data[3] = (unsigned char)(0xFF & scan_timeout_sec);
        j300plus_init_data[7] = (unsigned char)(0xFF & wakeup_time_sec);
        
        //J300 ���������Ӳ������δ֪���������5s��ʱ��Ŀǰʵ��
        //�ϵ��һ�γ�ʼ���3.6s
        ret = bl_J300plus_send_cmd(J300PLUS_PROTOCOTOL_INIT, j300plus_init_data, 8, 5000);
        barLog("[Barcode][%s]J300 plus init return: %d\n", __FUNCTION__, ret);
        return ret;
    }
    
    return BL_SUCCESS;
}

/*-----------------------------------------------------------------------
 * FUNCTION NAME: bl_set_default_para
 * DESCRIPTION: ���ڲ�ʹ�á���������ǹĬ�ϲ���
 * RETURN:
 *                       0: �ɹ�
 *                       -1: ʧ��
 *----------------------------------------------------------------------*/
static int bl_set_default_para(void)
{
    char C680_2D_code39_para[] = "nls0006010;nls0408080;nls0006000;";
    
    if(g_bl_info.bl_sub_type == BL_S_C680_2D)
    {
        //ɨcode39 �룬��ͷ��β����*
        bl_send_cmd(C680_2D_code39_para, sizeof(C680_2D_code39_para));
        
        //����ǹҪ������������Ҫ��300ms ���ܼ���������
        SVC_WAIT(300);
    }
    else if(g_bl_info.bl_sub_type == BL_S_J300_2D)
    {
        //ɨcode39 �룬��ͷ��β����*
        bl_J300_send_data(C680_2D_code39_para, sizeof(C680_2D_code39_para));
        
        //����ǹҪ������������Ҫ��300ms ���ܼ���������
        SVC_WAIT(300);
        
        bl_J300_clear_barcode_fifo();
    }
    
    return BL_SUCCESS;
}


/*-----------------------------------------------------------------------
 * FUNCTION NAME: bl_send_scan_cmd
 * DESCRIPTION: ���ڲ�ʹ�á�����ɨ��ָ��
 * RETURN:
 *                       0: �ɹ�
 *                       -1: ʧ��
 *----------------------------------------------------------------------*/
static int bl_send_scan_cmd(void)
{
    int ret, retVal = BL_FAILED;
    char buf[32] = {0};
    int retry = 3;
    
    //VX680 ֻ��֧��ָ��Ĳ��÷�ɨ��ָ��
    if((g_bl_info.bl_sub_type == BL_S_VX680_CMD) || (g_bl_info.bl_sub_type == BL_S_C680_2ND))
    {
        do
        {
            //�Ȼ���
            bl_send_cmd(vx680_wakeup_cmd, sizeof(vx680_wakeup_cmd));
            ret = bl_recv_response(buf, 1, 650);
            
            if(ret > 0)
                break;
        }
        while(retry-- > 0);
        
        //���ѳɹ��ٷ�ɨ��ָ��
        if((ret > 0) && (buf[0] == vx680_cmd_response))
        {
            //����֮����Ҫ��500 ms
            SVC_WAIT(500);
            bl_send_cmd(vx680_scan_cmd, sizeof(vx680_scan_cmd));
            //�˴���ʱ��Ϊ500ms, ���С������2D ���ղ�������
            ret = bl_recv_response(buf, 1, 500);
            
            //�ɹ�������ɨ��ָ��
            if((ret > 0) && (buf[0] == vx680_cmd_response))
            {
                retVal = BL_SUCCESS;
            }
        }
    }
    else if(g_bl_info.bl_sub_type == BL_S_C680_1D)
    {
        do
        {
            //�Ȼ���
            bl_send_cmd(nl_1D_trigger, sizeof(nl_1D_trigger));
            ret = bl_recv_response(buf, sizeof(nl_1D_cmd_response), 350);
            
            if(ret > 0)
            {
                //���ѳɹ��ٷ�ɨ��ָ��
                if(!memcmp(buf, nl_1D_cmd_response, sizeof(nl_1D_cmd_response)))
                {
                    bl_send_cmd(nl_1D_scan, sizeof(nl_1D_scan));
                    ret = bl_recv_response(buf, sizeof(nl_1D_cmd_response), 350);
                    
                    if(ret > 0)
                    {
                        //�ɹ�������ɨ��ָ��
                        if(!memcmp(buf, nl_1D_cmd_response, sizeof(nl_1D_cmd_response)))
                        {
                            retVal = BL_SUCCESS;
                            break;
                        }
                    }
                }
            }
        }
        while(retry-- > 0);
    }
    else if(g_bl_info.bl_sub_type == BL_S_C680_2D)
    {
        do
        {
            bl_send_cmd(nl_2D_scan, sizeof(nl_2D_scan));
            ret = bl_recv_response(buf, 1, 350);
            
            if((ret > 0) && (buf[0] == nl_2D_cmd_response))
            {
                retVal = BL_SUCCESS;
                break;
            }
        }
        while(retry-- > 0);
    }
    else if(g_bl_info.bl_sub_type == BL_S_J300_1D)
    {
        do
        {
            //�Ȼ���
            bl_J300_send_data(nl_1D_trigger, sizeof(nl_1D_trigger));
            ret = bl_J300_recv_data(buf, sizeof(nl_1D_cmd_response), 350);
            
            if(ret > 0)
            {
                //���ѳɹ��ٷ�ɨ��ָ��
                if(!memcmp(buf, nl_1D_cmd_response, sizeof(nl_1D_cmd_response)))
                {
                    bl_J300_send_data(nl_1D_scan, sizeof(nl_1D_scan));
                    ret = bl_J300_recv_data(buf, sizeof(nl_1D_cmd_response), 350);
                    
                    if(ret > 0)
                    {
                        //�ɹ�������ɨ��ָ��
                        if(!memcmp(buf, nl_1D_cmd_response, sizeof(nl_1D_cmd_response)))
                        {
                            retVal = BL_SUCCESS;
                            break;
                        }
                    }
                }
            }
        }
        while(retry-- > 0);
    }
    else if(g_bl_info.bl_sub_type == BL_S_J300_2D)
    {
        do
        {
            bl_J300_send_data(nl_2D_scan, sizeof(nl_2D_scan));
            ret = bl_J300_recv_data(buf, 1, 350);
            
            if((ret > 0) && (buf[0] == nl_2D_cmd_response))
            {
                retVal = BL_SUCCESS;
                break;
            }
        }
        while(retry-- > 0);
    }
    else if(g_bl_info.bl_sub_type == BL_S_J300_2ND)
    {
        do
        {
            //�Ȼ���
            bl_J300_send_data(vx680_wakeup_cmd, sizeof(vx680_wakeup_cmd));
            ret = bl_J300_recv_data(buf, 1, 650);
            
            if(ret > 0)
                break;
        }
        while(retry-- > 0);
        
        //���ѳɹ��ٷ�ɨ��ָ��
        if((ret > 0) && (buf[0] == vx680_cmd_response))
        {
            //����֮����Ҫ��500 ms
            SVC_WAIT(500);
            bl_J300_send_data(vx680_scan_cmd, sizeof(vx680_scan_cmd));
            //�˴���ʱ��Ϊ500ms, ���С������2D ���ղ�������
            ret = bl_J300_recv_data(buf, 1, 500);
            
            //�ɹ�������ɨ��ָ��
            if((ret > 0) && (buf[0] == vx680_cmd_response))
            {
                retVal = BL_SUCCESS;
            }
        }
    }
    else if(g_bl_info.bl_sub_type == BL_S_J300_PLUS)
    {
        char j300plus_scan_type = 0x02;//������
        retVal = bl_J300plus_send_cmd(J300PLUS_PROTOCOTOL_SCAN, &j300plus_scan_type, 1, 2000);
    }
    //����Ҫ����ָ��ģ������سɹ�
    else
    {
        retVal = BL_SUCCESS;
    }
    
    return retVal;
}

/*-----------------------------------------------------------------------
 * FUNCTION NAME: bl_force_sleep
 * DESCRIPTION: �ڲ�������ǿ������ǹ˯��
  * PARAMETER:  none
 * RETURN:      0: ˯�߳ɹ�
 *                      -1: ˯��ʧ��
 *----------------------------------------------------------------------*/
static int bl_force_sleep(void)
{
    char buf[32] = {0};
    int ret;
    
    if((g_bl_info.bl_sub_type == BL_S_VX680_CMD) || (g_bl_info.bl_sub_type == BL_S_C680_2ND))
    {
        bl_send_cmd(vx680_sleep_cmd, sizeof(vx680_sleep_cmd));
        ret = bl_recv_response(buf, 1, 350);
        
        if((ret <= 0) || (buf[0] != vx680_cmd_response))
            return BL_FAILED;
    }
    else if(g_bl_info.bl_sub_type == BL_S_J300_2ND)
    {
        bl_J300_send_data(vx680_sleep_cmd, sizeof(vx680_sleep_cmd));
        ret = bl_J300_recv_data(buf, 1, 350);
        
        if((ret <= 0) || (buf[0] != vx680_cmd_response))
            return BL_FAILED;
    }
    
#if 0
    else if(g_bl_info.bl_master_type == BL_M_IN_C680)
    {
        if(g_bl_info.bl_sub_type == BL_S_C680_1D)
        {
            /*C680 1D û��˯�ߺͻ���ָ�ƽʱ����˯�ߣ�
            �յ�ɨ��ָ���Զ�����*/
            return BL_SUCCESS;
        }
        else if(g_bl_info.bl_sub_type == BL_S_C680_2D)
        {
            /*C680 2D û��˯�ߺͻ���ָ�ƽʱ����˯�ߣ�
            �յ�ɨ��ָ���Զ�����*/
            return BL_SUCCESS;
        }
    }
    
#endif
    
    return BL_SUCCESS;
}

/*-----------------------------------------------------------------------
 * FUNCTION NAME: barcode_cancel_scan
 * DESCRIPTION: �ڲ�������ȡ��ɨ��
 * PARAMETER:  none
 * RETURN:      0: ȡ���ɹ�
 *                      -1: ȡ��ʧ��
 *----------------------------------------------------------------------*/
static int bl_cancel_scan(void)
{
    char buf[32] = {0};
    int ret;
    
    if((g_bl_info.bl_sub_type == BL_S_VX680_CMD) || (g_bl_info.bl_sub_type == BL_S_C680_2ND))
    {
        bl_send_cmd(vx680_cancel_cmd, sizeof(vx680_cancel_cmd));
        ret = bl_recv_response(buf, 1, 350);
        
        if((ret <= 0) || (buf[0] != vx680_cmd_response))
            return BL_FAILED;
    }
    else if(g_bl_info.bl_sub_type == BL_S_C680_2D)
    {
        bl_send_cmd(nl_2D_cancel, sizeof(nl_2D_cancel));
        ret = bl_recv_response(buf, 1, 350);
        
        if((ret <= 0) || (buf[0] != nl_2D_cmd_response))
            return BL_FAILED;
    }
    else if(g_bl_info.bl_sub_type == BL_S_J300_2D)
    {
        bl_J300_send_data(nl_2D_cancel, sizeof(nl_2D_cancel));
        ret = bl_J300_recv_data(buf, 1, 350);
        
        if((ret <= 0) || (buf[0] != nl_2D_cmd_response))
            return BL_FAILED;
    }
    else if(g_bl_info.bl_sub_type == BL_S_J300_2ND)
    {
        bl_J300_send_data(vx680_cancel_cmd, sizeof(vx680_cancel_cmd));
        ret = bl_J300_recv_data(buf, 1, 350);
        
        if((ret <= 0) || (buf[0] != vx680_cmd_response))
            return BL_FAILED;
    }
    
    return BL_SUCCESS;
}

/*-----------------------------------------------------------------------
 * FUNCTION NAME: bl_NL_1D_stop
 * DESCRIPTION: �ڲ�������NewLand 1D ��ɨ��
  * PARAMETER:  none
 * RETURN:      0: �ɹ�
 *                      -1: ʧ��
 *----------------------------------------------------------------------*/
static int bl_NL_1D_stop(void)
{
    char buf[32] = {0};
    int ret;
    int retry = 3;
    
    do
    {
        if(g_bl_info.bl_sub_type == BL_S_J300_1D)
        {
            bl_J300_send_data(nl_1D_cancel, sizeof(nl_1D_cancel));
            ret = bl_J300_recv_data(buf, sizeof(nl_1D_cmd_response), 3000);
        }
        else
        {
            bl_send_cmd(nl_1D_cancel, sizeof(nl_1D_cancel));
            ret = bl_recv_response(buf, sizeof(nl_1D_cmd_response), 3000);
        }
        
        if(ret > 0)
        {
            if(!memcmp(buf, nl_1D_cmd_response, sizeof(nl_1D_cmd_response)))
            {
                return BL_SUCCESS;
            }
        }
    }
    while(retry-- > 0);
    
    return BL_FAILED;
}


enumBarRet barcode_init(enumBarInitModel dev, int scan_timeout_sec, int wakeup_time_sec, enumChkFlag chk_flag)
{
    enumBarRet ret;
    
    /*���Ӧ��ʹ��͸��������豸����û���豸�������
      ���ó�ʼ������ô���豸�ص�*/
    bl_close_dev();
    
    /*���ó�ʼ��������ǰ�Ĳ����ָ���ԭʼֵ*/
    g_bl_info.handle = -1;
    g_bl_info.dev_name = NULL;
    g_bl_info.bl_master_type = BL_M_NO_INIT;
    g_bl_info.bl_sub_type = BL_S_UNKNOW;
    g_bl_info.bl_dev_status = BL_NOT_I_OPENED;
    g_bl_info.scan_timeout = 0;
    g_bl_info.chk_flag = BAR_CHK_DISABLE;
    
    /*��ʼ������������־*/
    if((chk_flag != BAR_CHK_DISABLE) && (chk_flag != BAR_CHK_ENABLE))
    {
        barLog("[Barcode][%s]Input chk_flag error!\n", __FUNCTION__);
        return BAR_PARA_ERROR;
    }
    else
    {
        g_bl_info.chk_flag = chk_flag;
    }
    
    /*-----------------�������ǹ���ͣ���ʼ-----------------*/

//    /*���ã��Զ�ʶ��*/
//    if(dev == BAR_INIT_INSIDE)
//    {
//        barLog("[Barcode][%s]Internal barcode is set.\n", __FUNCTION__);
//        ret = bl_check_internal();
//    }
//    /*���ã���ָ���豸*/
//    else
//    {
//        barLog("[Barcode][%s]External barcode is set %d\n", __FUNCTION__, dev);
        ret = bl_check_external(dev);
//    }
//    
    if(ret != BAR_SUCCESS)
    {
        barLog("[Barcode][%s]init ret = %d\n", __FUNCTION__, ret);
        return ret;
    }
    
    /*-----------------�������ǹ���ͣ�����-----------------*/
    
    //���ó�ʱ����
    if(bl_set_timeout(scan_timeout_sec, wakeup_time_sec))
        ret = BAR_SET_TIMEOUT_FAILED;
        
    /* ��Щ����ǹ��Ĭ�ϲ������ԣ���Ҫ�������� */
    if(bl_set_default_para() != BL_SUCCESS)
        ret = BAR_FAILED;
        
    //�ر��豸
    if(bl_close_dev())
    {
        barLog("[Barcode][%s]Close dev failed\n", __FUNCTION__);
        ret = BAR_FAILED;
    }
    
    return ret;
}


enumBarRet barcode_clr_fifo(void)
{
    /*���û��ʼ����ֱ�ӷ���*/
    if(g_bl_info.bl_master_type == BL_M_NO_INIT)
        return BAR_NO_INIT;
        
    if((g_bl_info.bl_master_type == BL_M_IN_UNKNOW) ||
       (g_bl_info.bl_master_type == BL_M_EX_UNKNOW) ||
       (g_bl_info.bl_sub_type == BL_S_UNKNOW))
        return BAR_NO_DEV;
        
    if(bl_open_dev())
        return BAR_DEV_OPENED;
        
    if(IS_J300(g_bl_info.bl_sub_type))
        bl_J300_clear_barcode_fifo();
    else
        bl_clear_barcode_fifo();
        
    bl_close_dev();
    
    return BAR_SUCCESS;
}


enumBarRet barcode_scan(char * p_buf, int buf_size, int * get_len, LBarCode_CHK_Cancel p_callback)
{
    int ret, break_tag = 0;
    unsigned long timeout = 0L;
    int got_data_len = 0;
    int stop_ret = 0;
    
    if(get_len == NULL)
    {
        barLog("[Barcode][%s]Input para err, return!\n", __FUNCTION__);
        return BAR_PARA_ERROR;
    }
    else
    {
        *get_len = 0;
    }
    
    /*������*/
    if((p_buf == NULL) || (buf_size <= 0))
    {
        barLog("[Barcode][%s]Input para err, return!\n", __FUNCTION__);
        return BAR_PARA_ERROR;
    }
    
    memset(p_buf, 0, buf_size);
    
    /*���û��ʼ����ֱ�ӷ���*/
    if(g_bl_info.bl_master_type == BL_M_NO_INIT)
        return BAR_NO_INIT;
        
    if(p_callback == NULL)
    {
        barLog("[Barcode][%s]Callback is null!\n", __FUNCTION__);
        return BAR_READ_NO_CALLBACK;
    }
    
    //����豸�Բ���
    if((g_bl_info.bl_master_type == BL_M_IN_UNKNOW) ||
       (g_bl_info.bl_master_type == BL_M_EX_UNKNOW) ||
       (g_bl_info.bl_sub_type == BL_S_UNKNOW))
    {
        return BAR_NO_DEV;
    }
    
    if(bl_open_dev())
        return BAR_DEV_OPENED;
        
    //����ɨ��ָ��
    if(bl_send_scan_cmd())
    {
        if(g_bl_info.bl_sub_type == BL_S_J300_PLUS)
            bl_J300plus_cancel_scan();
            
        if(IS_J300(g_bl_info.bl_sub_type))
            bl_J300_clear_barcode_fifo();
        else
            bl_clear_barcode_fifo();
            
        bl_close_dev();
        return BAR_READ_CMD_FAILED;
    }
    
    /*-----------------������ǹ�����ݿ�ʼ-----------------
     * ����һ�ζ�һ���ֽڵĺô��Ǽ�СCPU ռ����
     *û�ý���������Ϊֹͣ���ı�־���ô��Ƿ�ֹ���
     *û�н�������ÿ�ζ��ó�ʱ�����˳�*/
    timeout = getCurrentTimeMS() + g_bl_info.scan_timeout * 1000;
    
    do
    {
        /*����û�ȡ��*/
        break_tag = p_callback();
        
        if(break_tag)
            break;
            
        if(IS_J300(g_bl_info.bl_sub_type))
            ret = bl_J300_recv_data(p_buf + got_data_len, buf_size, J300_RESPONSE_TIMEOUT_MS);
        else if(g_bl_info.bl_sub_type == BL_S_J300_PLUS)
            ret = bl_J300plus_get_scancode(p_buf + got_data_len, buf_size, J300_RESPONSE_TIMEOUT_MS);
        else
            ret = POS_Read(g_bl_info.handle, p_buf, buf_size, 10);
            
        if(ret > 0)
        {
            got_data_len = ret;
            break;
        }
        
        if(!(IS_J300_J300PLUS(g_bl_info.bl_sub_type)))
            SVC_WAIT(100);
    }
    while(timeout > getCurrentTimeMS());
    
    /* һ��ɨ��������п����Ƿֶ�η���POS�ģ�
           ������֮��ļ�����ܴ���300 ms , J300 plus����ֶ�η���*/
    if((got_data_len > 0) && (got_data_len < buf_size) &&
       (break_tag == 0) && (g_bl_info.bl_sub_type != BL_S_J300_PLUS))
    {
        do
        {
            if(!(IS_J300(g_bl_info.bl_sub_type)))
                SVC_WAIT(100);
                
            /*��ȡ�Ĵ�СΪbuf_size ʣ��ռ��С*/
            if(IS_J300(g_bl_info.bl_sub_type))
                ret = bl_J300_recv_data(p_buf + got_data_len, (buf_size - got_data_len), J300_RESPONSE_TIMEOUT_MS);
            else
                ret = POS_Read(g_bl_info.handle, p_buf + got_data_len, (buf_size - got_data_len), 10);
                
            if(ret > 0)
                got_data_len += ret;
                
            /*û���������ݻ�buffer���˾�ֹͣ����*/
            if((ret <= 0) || (got_data_len == buf_size))
                break;
                
            /*����û�ȡ��*/
            break_tag = p_callback();
        }
        while(break_tag == 0);
    }
    
    /*-----------------������ǹ�����ݽ���-----------------*/
    
    /*����û�ȡ����, �ر�ɨ��*/
    if(break_tag)
    {
        stop_ret = bl_cancel_scan();
    }
    
    /* J300 plus ɨ����ɶ�Ҫ��һ��cancel */
    if(g_bl_info.bl_sub_type == BL_S_J300_PLUS)
        bl_J300plus_cancel_scan();
        
    /*�´�½ 1D����ÿ���ֶ���ɨ��*/
    if((g_bl_info.bl_sub_type == BL_S_C680_1D) ||
       (g_bl_info.bl_sub_type == BL_S_J300_1D))
    {
        stop_ret = bl_NL_1D_stop();
    }
    
    /*ɨ�����˯*/
    /*��һ������2D, sleep�޷��أ�������ʱ����˯�߽��*/
    ret = bl_force_sleep();
    
    if(IS_J300(g_bl_info.bl_sub_type))
        bl_J300_clear_barcode_fifo();
    else
        bl_clear_barcode_fifo();
        
    bl_close_dev();
    
    /*�������ȡ��ָ��ʧ�ܣ�ֱ�ӷ��ش���*/
    if(stop_ret)
        return BAR_READ_STOP_FAILED;
        
    /*-----------------�������ݿ�ʼ-----------------*/
    /*����û�ȡ����*/
    if(break_tag)
    {
        memset(p_buf, 0, buf_size);
        p_buf[0] = (char)(break_tag & 0xFF);
        *get_len = 1;
        barLog("[Barcode][%s]--- scan_cancel ---\n", __FUNCTION__);
        return BAR_READ_CANCELLED;
    }
    
    if(got_data_len < 1)
    {
        return BAR_READ_TIMEOUT;
    }
    
    /*�����������*/
    if(g_bl_info.chk_flag == BAR_CHK_DISABLE)
    {
        *get_len = got_data_len;
        return BAR_SUCCESS;
    }
    else /*���������*/
    {
        /*USB����0D��β��������0D 0A ��β*/
        if(g_bl_info.bl_master_type == BL_M_EX_USB)
        {
            /*����ֽ���0D, ȥ��������Ӧ��*/
            if(p_buf[got_data_len - 1] == 0x0D)
            {
                p_buf[got_data_len - 1] = 0;
                *get_len = got_data_len - 1;
                return BAR_SUCCESS;
            }
            else
            {
                *get_len = got_data_len;
                
                /*����*/
                if(got_data_len == buf_size)
                {
                    return BAR_READ_OVERSIZE;
                }
                else
                {
                    return BAR_READ_NO_EOD;
                }
            }
        }
        else
        {
            /*��������ֽ���0D 0A, ȥ������Ӧ��*/
            if((p_buf[got_data_len - 2] == 0x0D) &&
               (p_buf[got_data_len - 1] == 0x0A))
            {
                p_buf[got_data_len - 2] = 0;
                p_buf[got_data_len - 1] = 0;
                *get_len = got_data_len - 2;
                return BAR_SUCCESS;
            }
            else
            {
                *get_len = got_data_len;
                
                /*����*/
                if(got_data_len == buf_size)
                {
                    return BAR_READ_OVERSIZE;
                }
                else
                {
                    return BAR_READ_NO_EOD;
                }
            }
        }
    }
    
    /*-----------------�������ݽ���-----------------*/
}


enumBarModelRet barcode_get_model(void)
{
    enumBarModelRet ret = BAR_MODEL_NO_INIT;
    
    switch(g_bl_info.bl_master_type)
    {
        case BL_M_IN_VX680:
        {
            if(g_bl_info.bl_sub_type == BL_S_VX680_CMD)
                ret = BAR_IN_VX680_CMD;
            else if(g_bl_info.bl_sub_type == BL_S_VX680_KEY)
                ret = BAR_IN_VX680_KEY;
            else
                ret = BAR_IN_UNKNOW;
                
            break;
        }
        case BL_M_IN_C680:
        {
            if(g_bl_info.bl_sub_type == BL_S_C680_1D)
                ret = BAR_IN_C680_1D;
            else if(g_bl_info.bl_sub_type == BL_S_C680_2D)
                ret = BAR_IN_C680_2D;
            else if(g_bl_info.bl_sub_type == BL_S_C680_2ND)
                ret = BAR_IN_C680_2ND;
            else
                ret = BAR_IN_UNKNOW;
                
            break;
        }
        case BL_M_IN_UNKNOW:
        {
            ret = BAR_IN_UNKNOW;
            break;
        }
        case BL_M_EX_USB:
        {
            ret = BAR_EX_USB;
            break;
        }
        case BL_M_EX_COM:
        {
            if(g_bl_info.bl_sub_type == BL_S_EX_COM1)
                ret = BAR_EX_COM1;
            else if(g_bl_info.bl_sub_type == BL_S_EX_COM20)
                ret = BAR_EX_COM20;
            else if(g_bl_info.bl_sub_type == BL_S_EX_COM_EX)
                ret = BAR_EX_EXTEND_COM;
            else
                ret = BAR_EX_UNKONW;
                
            break;
            
        }
        case BL_M_EX_UNKNOW:
        {
            ret = BAR_EX_UNKONW;
            break;
        }
        case BL_M_NO_INIT:
        {
            ret = BAR_MODEL_NO_INIT;
            break;
        }
        case BL_M_J300_COM1:
        case BL_M_J300_COM20:
        case BL_M_J300_USB:
        {
            if(g_bl_info.bl_sub_type == BL_S_J300_1D)
                ret = BAR_J300_1D;
            else if(g_bl_info.bl_sub_type == BL_S_J300_2D)
                ret = BAR_J300_2D;
            else if(g_bl_info.bl_sub_type == BL_S_J300_2ND)
                ret = BAR_J300_2ND;
            else if(g_bl_info.bl_sub_type == BL_S_J300_PLUS)
                ret = BAR_J300_PLUS;
            else
                ret = BAR_J300_UNKNOW;
                
            break;
        }
        default:
            break;
    }
    
    return ret;
}


enumBarRet barcode_factory_reset(void)
{
    enumBarRet retVal = BAR_FAILED;
    char buf[32] = {0};
    int ret;
    int retry = 3;
    
    /*���û��ʼ����ֱ�ӷ���*/
    if(g_bl_info.bl_master_type == BL_M_NO_INIT)
        return BAR_NO_INIT;
        
    /*ֻ�д�ָ���֧�ָֻ�����*/
    if((g_bl_info.bl_sub_type != BL_S_VX680_CMD) &&
       (g_bl_info.bl_sub_type != BL_S_C680_1D) &&
       (g_bl_info.bl_sub_type != BL_S_C680_2D) &&
       (g_bl_info.bl_sub_type != BL_S_J300_1D) &&
       (g_bl_info.bl_sub_type != BL_S_J300_2D) &&
       (g_bl_info.bl_sub_type != BL_S_C680_2ND) &&
       (g_bl_info.bl_sub_type != BL_S_J300_2ND) &&
       (g_bl_info.bl_sub_type != BL_S_J300_PLUS)
      )
        return BAR_NOT_SUPPORT;
        
    if(bl_open_dev())
        return BAR_DEV_OPENED;
        
        
    if((g_bl_info.bl_sub_type == BL_S_VX680_CMD) || (g_bl_info.bl_sub_type == BL_S_C680_2ND))
    {
        do
        {
            bl_send_cmd(vx680_wakeup_cmd, sizeof(vx680_wakeup_cmd));
            SVC_WAIT(500);
            bl_send_cmd(vx680_reset_cmd, sizeof(vx680_reset_cmd));
            ret = bl_recv_response(buf, 1, 350);
            
            if((ret > 0) && (buf[0] == vx680_cmd_response))
            {
                retVal = BAR_SUCCESS;
                break;
            }
        }
        while(retry-- > 0);
        
        //��Ҫ�ȴ�300ms ���ܷ���һ������
        //SVC_WAIT( 300 );
    }
    else if(g_bl_info.bl_sub_type == BL_S_C680_1D)
    {
        do
        {
            /*C680 1D �ָ���������ָ��*/
            bl_send_cmd(nl_1D_factory_reset, sizeof(nl_1D_factory_reset));
            ret = bl_recv_response(buf, sizeof(nl_1D_cmd_response), 3000);
            
            if(ret > 0)
            {
                if(!memcmp(buf, nl_1D_cmd_response, sizeof(nl_1D_cmd_response)))
                {
                    retVal = BAR_SUCCESS;
                    break;
                }
            }
        }
        while(retry-- > 0);
    }
    else if(g_bl_info.bl_sub_type == BL_S_C680_2D)
    {
        do
        {
            //���������빦��
            bl_send_cmd("nls0006010;", 11);
            
            //���ͻָ���������ָ��
            bl_send_cmd("nls0001000;", 11);
            ret = bl_recv_response(buf, 1, 350);
            
            if((ret > 0) && (buf[0] == nl_2D_cmd_response))
            {
                retVal = BAR_SUCCESS;
                break;
            }
            
            //��Ҫ�ȴ�50ms ���ܷ���һ������
            //SVC_WAIT( 50 );
        }
        while(retry-- > 0);
    }
    
    else if(g_bl_info.bl_sub_type == BL_S_J300_1D)
    {
        do
        {
            /*C680 1D �ָ���������ָ��*/
            bl_J300_send_data(nl_1D_factory_reset, sizeof(nl_1D_factory_reset));
            ret = bl_J300_recv_data(buf, sizeof(nl_1D_cmd_response), 3000);
            
            if(ret > 0)
            {
                if(!memcmp(buf, nl_1D_cmd_response, sizeof(nl_1D_cmd_response)))
                {
                    retVal = BAR_SUCCESS;
                    break;
                }
            }
        }
        while(retry-- > 0);
    }
    else if(g_bl_info.bl_sub_type == BL_S_J300_2D)
    {
        do
        {
            //���������빦��
            bl_J300_send_data("nls0006010;", 11);
            bl_J300_recv_data(buf, 1, 350);
            //���ͻָ���������ָ��
            bl_J300_send_data("nls0001000;", 11);
            ret = bl_J300_recv_data(buf, 1, 350);
            
            if((ret > 0) && (buf[0] == nl_2D_cmd_response))
            {
                retVal = BAR_SUCCESS;
                break;
            }
            
            //��Ҫ�ȴ�50ms ���ܷ���һ������
            //SVC_WAIT( 50 );
        }
        while(retry-- > 0);
    }
    else if(g_bl_info.bl_sub_type == BL_S_J300_2ND)
    {
        do
        {
            bl_J300_send_data(vx680_wakeup_cmd, sizeof(vx680_wakeup_cmd));
            SVC_WAIT(500);
            bl_J300_send_data(vx680_reset_cmd, sizeof(vx680_reset_cmd));
            ret = bl_J300_recv_data(buf, 1, 350);
            
            if((ret > 0) && (buf[0] == vx680_cmd_response))
            {
                retVal = BAR_SUCCESS;
                break;
            }
        }
        while(retry-- > 0);
    }
    else if(g_bl_info.bl_sub_type == BL_S_J300_PLUS)
    {
        char j300plus_reset = 0x01;
        ret = bl_J300plus_send_cmd(J300PLUS_PROTOCOTOL_RESET, &j300plus_reset, 1, 5000);
        
        if(ret == BL_SUCCESS)
            retVal = BAR_SUCCESS;
    }
    
    bl_close_dev();
    
    /*����ָ������ɹ���ȫ�ֱ�����ԭ*/
    if(retVal == BAR_SUCCESS)
    {
        g_bl_info.handle = -1;
        g_bl_info.dev_name = NULL;
        g_bl_info.bl_master_type = BL_M_NO_INIT;
        g_bl_info.bl_sub_type = BL_S_UNKNOW;
        g_bl_info.bl_dev_status = BL_NOT_I_OPENED;
    }
    
    return retVal;
}


enumBarRet barcode_transfer(char * p_buf, enumBarTranOpt optn, int len, int * get_len)
{
    enumBarRet retVal = BAR_SUCCESS;
    int ret = 0;
    char ioctl_status[4];
    
    
    /*����豸�Բ���*/
    if((g_bl_info.bl_master_type == BL_M_IN_UNKNOW) ||
       (g_bl_info.bl_master_type == BL_M_EX_UNKNOW) ||
       (g_bl_info.bl_sub_type == BL_S_UNKNOW))
    {
        retVal = BAR_NO_DEV;
    }
    
    /*�Ƿ��ʼ��*/
    if(g_bl_info.bl_master_type == BL_M_NO_INIT)
        retVal = BAR_NO_INIT;
        
    /*�����˳�ǰ��get_len ��0*/
    if(retVal != BAR_SUCCESS)
    {
        if(get_len != NULL)
            get_len = 0;
            
        return retVal;
    }
    
    switch(optn)
    {
        case BAR_TRAN_OPEN:
        {
            return bl_open_dev() ? BAR_DEV_OPENED : BAR_SUCCESS;
        }
        case BAR_TRAN_CLOSE:
        {
            return bl_close_dev() ? BAR_FAILED : BAR_SUCCESS;
        }
        case BAR_TRAN_WRITE:
        {
            /*������*/
            if(get_len == NULL)
            {
                barLog("[Barcode][%s]Input para err, return!\n", __FUNCTION__);
                return BAR_PARA_ERROR;
            }
            else
            {
                *get_len = 0;
            }
            
            /*������*/
            if((p_buf == NULL) || (len <= 0))
            {
                barLog("[Barcode][%s]Input para err, return!\n", __FUNCTION__);
                return BAR_PARA_ERROR;
            }
            
            if(IS_INVALID_HANDLE(g_bl_info.handle))
                return BAR_DEV_OPENED;
                
            if(IS_J300_J300PLUS(g_bl_info.bl_sub_type))
            {
                ret = bl_J300_send_data(p_buf, len);
            }
            else
            {
                /*POS_Write data*/
                ret = POS_Write(g_bl_info.handle, p_buf, len);
            }
            
            *get_len = ret;
            
            if(ret != len)
            {
                return BAR_FAILED;
            }
            
            SVC_WAIT(10);
                
            return BAR_SUCCESS;
        }
        case BAR_TRAN_READ:
        {
            /*������*/
            if(get_len == NULL)
            {
                barLog("[Barcode][%s]Input para err, return!\n", __FUNCTION__);
                return BAR_PARA_ERROR;
            }
            else
            {
                *get_len = 0;
            }
            
            /*������*/
            if((p_buf == NULL) || (len <= 0))
            {
                barLog("[Barcode][%s]Input para err, return!\n", __FUNCTION__);
                return BAR_PARA_ERROR;
            }
            
            /*read data*/
            memset(p_buf, 0, len);
            
            if(IS_INVALID_HANDLE(g_bl_info.handle))
                return BAR_DEV_OPENED;
                
            if(IS_J300_J300PLUS(g_bl_info.bl_sub_type))
            {
                ret = bl_J300_recv_data(p_buf, len, J300_RESPONSE_TIMEOUT_MS);
            }
            else
            {
                ret = POS_Read(g_bl_info.handle, p_buf, len, 10);
            }
            
            if(ret > 0)
            {
                *get_len = ret;
                return BAR_SUCCESS;
            }
            else
            {
                return BAR_READ_TIMEOUT;
            }
        }
        default:
            return BAR_PARA_ERROR;
    }
}


enumBarRet barcode_scan_nonblock(char * p_buf, int buf_size, int * get_len, enumNonblockOpt optn)
{
    int ret;
    int got_data_len = 0;
    
    //����豸�Բ���
    if((g_bl_info.bl_master_type == BL_M_IN_UNKNOW) || \
       (g_bl_info.bl_master_type == BL_M_EX_UNKNOW) || \
       (g_bl_info.bl_sub_type == BL_S_UNKNOW) || \
       (g_bl_info.bl_master_type == BL_M_NO_INIT))
    {
        if(get_len != NULL)
            *get_len = 0;
            
        return BAR_NO_DEV;
    }
    
    if(BAR_START_SCAN == optn)
    {
        if(bl_open_dev())
            return BAR_DEV_OPENED;
            
        bl_cancel_scan();
        
        if(g_bl_info.bl_sub_type == BL_S_J300_PLUS)
            bl_J300plus_cancel_scan();
            
        /*�´�½ 1D����ÿ���ֶ���ɨ��*/
        if((g_bl_info.bl_sub_type == BL_S_C680_1D) ||
           (g_bl_info.bl_sub_type == BL_S_J300_1D))
        {
            bl_NL_1D_stop();
        }
        
        if(IS_J300(g_bl_info.bl_sub_type))
            bl_J300_clear_barcode_fifo();
        else
            bl_clear_barcode_fifo();
            
        //����ɨ��ָ��
        if(bl_send_scan_cmd())
        {
            if(g_bl_info.bl_sub_type == BL_S_J300_PLUS)
                bl_J300plus_cancel_scan();
                
            if(IS_J300(g_bl_info.bl_sub_type))
                bl_J300_clear_barcode_fifo();
            else
                bl_clear_barcode_fifo();
                
            bl_close_dev();
            return BAR_READ_CMD_FAILED;
        }
    }
    else if(BAR_GET_DATA == optn)
    {
        if(get_len == NULL)
        {
            barLog("[Barcode][%s]Input para err, return!\n", __FUNCTION__);
            return BAR_PARA_ERROR;
        }
        else
        {
            *get_len = 0;
        }
        
        /*������*/
        if((p_buf == NULL) || (buf_size <= 0))
        {
            barLog("[Barcode][%s]Input para err, return!\n", __FUNCTION__);
            return BAR_PARA_ERROR;
        }
        
        memset(p_buf, 0, buf_size);
        
        /*-----------------������ǹ�����ݿ�ʼ-----------------*/
        while(1)
        {
            /*��ȡ�Ĵ�СΪbuf_size ʣ��ռ��С*/
            if(IS_J300(g_bl_info.bl_sub_type))
            {
                ret = bl_J300_recv_data(p_buf + got_data_len, (buf_size - got_data_len), J300_RESPONSE_TIMEOUT_MS);
            }
            else if(g_bl_info.bl_sub_type == BL_S_J300_PLUS)
            {
                ret = bl_J300plus_get_scancode(p_buf + got_data_len, (buf_size - got_data_len), J300_RESPONSE_TIMEOUT_MS);
            }
            else
            {
                ret = POS_Read(g_bl_info.handle, p_buf + got_data_len, (buf_size - got_data_len), 10);
                /* ���Ӧ��Ƶ�����ã������豸æ�������ղ�ȫ������wait 20 ms
                              ��һ�ַ�����ͨ��event�ж��Ƿ�������(set_event_bit) ���漰���滻�����豸��
                              Ĭ��event����Ҫ��ÿ���豸��֤�ɿ��ԣ�������ʱδ���á�*/
                SVC_WAIT(20);
            }
            
            if(ret > 0)
                got_data_len += ret;
                
            /*û���������ݻ�buffer���˾�ֹͣ����*/
            if((ret <= 0) || (got_data_len == buf_size))
                break;
        }
        
        /*-----------------������ǹ�����ݽ���-----------------*/
        
        /*-----------------�������ݿ�ʼ-----------------*/
        if(got_data_len < 1)
        {
            return BAR_SUCCESS;
        }
        
        /*�����������*/
        if(g_bl_info.chk_flag == BAR_CHK_DISABLE)
        {
            *get_len = got_data_len;
            return BAR_SUCCESS;
        }
        else /*���������*/
        {
            /*USB����0D��β��������0D 0A ��β*/
            if(g_bl_info.bl_master_type == BL_M_EX_USB)
            {
                /*����ֽ���0D, ȥ��������Ӧ��*/
                if(p_buf[got_data_len - 1] == 0x0D)
                {
                    p_buf[got_data_len - 1] = 0;
                    *get_len = got_data_len - 1;
                    return BAR_SUCCESS;
                }
                else
                {
                    *get_len = got_data_len;
                    
                    /*����*/
                    if(got_data_len == buf_size)
                    {
                        return BAR_READ_OVERSIZE;
                    }
                    else
                    {
                        return BAR_READ_NO_EOD;
                    }
                }
            }
            else
            {
                /*��������ֽ���0D 0A, ȥ������Ӧ��*/
                if((p_buf[got_data_len - 2] == 0x0D) &&
                   (p_buf[got_data_len - 1] == 0x0A))
                {
                    p_buf[got_data_len - 2] = 0;
                    p_buf[got_data_len - 1] = 0;
                    *get_len = got_data_len - 2;
                    return BAR_SUCCESS;
                }
                else
                {
                    *get_len = got_data_len;
                    
                    /*����*/
                    if(got_data_len == buf_size)
                    {
                        return BAR_READ_OVERSIZE;
                    }
                    else
                    {
                        return BAR_READ_NO_EOD;
                    }
                }
            }
        }
        
        /*-----------------�������ݽ���-----------------*/
    }
    else if(BAR_STOP_SCAN == optn)
    {
        /*����û�ȡ����, �ر�ɨ��*/
        bl_cancel_scan();
        
        if(g_bl_info.bl_sub_type == BL_S_J300_PLUS)
            bl_J300plus_cancel_scan();
            
        /*�´�½ 1D����ÿ���ֶ���ɨ��*/
        if((g_bl_info.bl_sub_type == BL_S_C680_1D) ||
           (g_bl_info.bl_sub_type == BL_S_J300_1D))
        {
            bl_NL_1D_stop();
        }
        
        /*ɨ�����˯*/
        /*��һ������2D, sleep�޷��أ�������ʱ����˯�߽��*/
        ret = bl_force_sleep();
        
        if(IS_J300(g_bl_info.bl_sub_type))
            bl_J300_clear_barcode_fifo();
        else
            bl_clear_barcode_fifo();
            
        bl_close_dev();
    }
    else
    {
        barLog("[Barcode][%s]optn err, return!\n", __FUNCTION__);
        return BAR_PARA_ERROR;
    }
    
    return BAR_SUCCESS;
}


#ifdef _SHARED_LIB
int main(int argc, char **argv)
{
    // initialize your barcode Library initialized static data here
    bl_InitPrm();
    return 0;
}
#endif

