#include <pos_debug.h>
#include "pinpad/util.h"
#include "pinpad/j300_priv.h"
#include "pinpad/j300.h"

#define EXJ300_RX_TIMEOUT	15000		/*ms*/
#define TDES_KEY_LENGTH	16

#define KLK                     0x02
#define FIXED_DATA_KEY          0x08
#define MASTER_KEY              0x10

typedef enum
{
    J300_DISP_MODE=0,		//J300
    V3_DISP_MODE,
} J300_Display_Type;

static unsigned short mkey_format;
static usint CurrentDesFlag;

static int gDispMode;	// 区分J300和V3显示

#define J300_CMD_SHOW_LOGO				0xa914
#define J300_CMD_LCD_CLS                0xa916
#define J300_CMD_SHOW_STRING            0xa918

#define J300_CMD_CLEAR_KEYS				0xb203
#define J300_CMD_CHECK_KEY				0xb201
#define J300_CMD_LOAD_KEY				0xb322
#define J300_CMD_GET_PIN				0xb601
#define J300_CMD_CANCEL_PIN				0xb611
#define J300_CMD_GET_PLAIN_PIN			0xb701
#define J300_CMD_CANCEL_PLAIN_PIN		0xb711
#define J300_CMD_GET_MAC				0xb603
#define J300_CMD_DATA_ENCRYPT_DECRYPT	0xb609
#define J300_CMD_INPUT_TEXT				0xa934
#define J300_CMD_SM4_SUPPORT			0xb703
#define J300_CMD_GET_SN					0xa962

/* --------------------------------------------------------------------------
 * FUNCTION NAME: J300_SetPPType.
 * DESCRIPTION:   设置密码键盘的类型(内置/外置)、模式及主密钥格式
 * PARAMETER:
	pp_type - INTERNAL_PINPAD
            - EXTERNAL_PINPAD
	pp_mode  － PP1000SE_MODE
			 － PRC_MODE
			 － PRC_MODE_CLEAR
			 － PP222_MODE
			 － PP222_MODE_TDES
	key_format - CLEAR_FORMAT
	           - ENCRYPTED_FORMAT
 * RETURN:        无
 * NOTES:
 * ------------------------------------------------------------------------ */
int J300_SetPPType(unsigned short pp_type, unsigned short pp_mode, unsigned short key_format)
{
    switch (pp_mode)
    {
    case PRC_MODE_CLEAR:
    case PRC_MODE:
    case PP222_3DES_MODE:
        CurrentDesFlag = TRIPLE_DES;
        break;
    case PP1000SE_MODE:
    case PP222_DES_MODE:
        CurrentDesFlag = SINGLE_DES;
        break;
    default:
        break;
    }

    mkey_format = key_format;

    return VS_SUCCESS;
}

int J300_Connect(char *devName)
{
    return J300_OpenDevice(devName);
}

void J300_Disconnect()
{
    J300_CloseDevice();
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: J300_Idle.
 * DESCRIPTION:   Cancel Session Request / Return to Idle State
 * RETURN:        .
 * NOTES:         none.
 * ------------------------------------------------------------------------ */
int J300_Idle(void)
{
    J300_LcdCls();
    return J300_LcdDisplayLogo(0);
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: J300_SetIdleMsg.
 * DESCRIPTION:   Set / Reset Idle Prompt
 * RETURN:        .
 * NOTES:         none.
 * ------------------------------------------------------------------------ */
int J300_SetIdleMsg(char *IdleMsg)
{
    J300_LcdCls();
    return J300_LcdDisplayLogo(0);
}

/* --------------------------------------------------------------------------
* FUNCTION NAME:  J300_SetDispMode
* DESCRIPTION:	set J300 display mode
* INPUTS:  mode: 'D' - j300; 'C' -PP1000seC V3
* OUTPUTS:
* RETURN:
* ------------------------------------------------------------------------ */
void J300_SetDispMode(byte mode)
{
    if (mode == 'D')
        gDispMode = J300_DISP_MODE;
    else if (mode == 'C')
        gDispMode = V3_DISP_MODE;
    else
        gDispMode = -1;
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: J300_DisplayMsg.
 * DESCRIPTION:   display two messages on two lines of Pinpad until next
				 command received
 * RETURN:        VS_SUCCESS/VS_ERROR
 * NOTES:         maximum length of message is 16 characters
 * ------------------------------------------------------------------------ */
int J300_DisplayMsg(char *Line1Msg, char *Line2Msg)
{
    int ret = 0;
    int y1 = 0, y2 = 0;

    if (gDispMode == J300_DISP_MODE)
    {
        y1 = 30;
        y2 = 30*3;
    }
    else if (gDispMode == V3_DISP_MODE)
    {
        y1 = 8;
        y2 = 32;
    }

    J300_LcdCls();
    if (Line1Msg)
    {
        ret = J300_LcdDispStr (0, y1, Line1Msg);
        LOG_PRINTF("J300_LcdDispStr=[%d]", ret);

        if (ret != VS_SUCCESS)
            return ret;
    }

    if (Line2Msg)
    {
        ret = J300_LcdDispStr (0, y2, Line2Msg);
        LOG_PRINTF("J300_LcdDispStr=[%d]", ret);
        return ret;
    }

    return VS_SUCCESS;
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: J300_LoadKLK
 * DESCRIPTION:   下载密钥传输密钥
 * PARAMETER:
 *     Input: KLK - 16bytes
 * RETURN:		VS_SUCCESS/VS_ERROR
 * NOTES:         none.
 * ------------------------------------------------------------------------ */
int J300_LoadKLK(unsigned char *klk)
{
#if 0
    J300_ClearKeys();
    get_char();
#endif

    if (mkey_format == CLEAR_FORMAT)
    {
        return VS_SUCCESS;
    }

    return J300_LoadKey(KLK, 0, klk);
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: J300_CheckKLK
 * DESCRIPTION:   检测密钥传输密钥是否存在
 * PARAMETER:
 * RETURN:		VS_SUCCESS/VS_ERROR
 * NOTES:         none.
 * ------------------------------------------------------------------------ */
int J300_CheckKLK(void)
{
    int iLength, ret;
    unsigned char cSendData[MAX_BUFFER_SIZE];

    memset(cSendData,0,sizeof(cSendData));
    cSendData[0] = 0;				//密钥索引
    cSendData[1] = KLK;				//密钥类型:KLK
    ret = J300_Crc16SendPacket(cSendData, 2, J300_CMD_CHECK_KEY);
    if (VS_SUCCESS != ret)
    {
        return VS_ERROR;
    }

    memset(cSendData, 0, sizeof(cSendData));
    ret = J300_Crc16RecvPacket(J300_CMD_CHECK_KEY, cSendData, &iLength, EXJ300_RX_TIMEOUT, sizeof(cSendData), FALSE);
    if (ret < 0)
        return ret;

    if ((0 == cSendData[5]) && (8 == iLength))
        return VS_SUCCESS;
    else
        return VS_ERROR;
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: J300_LoadMasterKey
 * DESCRIPTION:   向指定位置下装主密钥
 * PARAMETER:
 *                number  - 主密钥索引号00－99
 *                Mkey    - 主密钥
 * RETURN:        VS_SUCCESS/VS_ERROR
 * NOTES:         none.
 * ------------------------------------------------------------------------ */
int J300_LoadMasterKey(unsigned short number, unsigned char *MKey)
{
    POS_DEBUG_WITHTAG("UsbserDemo","======================");
    return J300_LoadKey(MASTER_KEY, number, MKey);
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: J300_SelectKey
 * DESCRIPTION:   选择主密钥
 * PARAMETER:
 *                number  - 主密钥索引号00－99
 * RETURN:        VS_SUCCESS/VS_ERROR
 * NOTES:         none.
 * ------------------------------------------------------------------------ */
int J300_SelectKey(unsigned short number)
{
    int iLength, ret;
    unsigned char cSendData[MAX_BUFFER_SIZE];

    memset(cSendData,0,sizeof(cSendData));
    cSendData[0] = number;			//密钥索引
    cSendData[1] = MASTER_KEY;		//密钥类型:Master Key
    ret = J300_Crc16SendPacket(cSendData, 2, J300_CMD_CHECK_KEY);
    if (VS_SUCCESS != ret)
    {
        return VS_ERROR;
    }

    memset(cSendData, 0, sizeof(cSendData));
    ret = J300_Crc16RecvPacket(J300_CMD_CHECK_KEY, cSendData, &iLength, EXJ300_RX_TIMEOUT, sizeof(cSendData), FALSE);
    if (ret < 0)
        return ret;

    if ((0 == cSendData[5]) && (8 == iLength))
    {
        PP222_SetErrorStatus(PINPAD_NO_ERROR);
        return VS_SUCCESS;
    }
    else if (1 == cSendData[5])		/*密钥不存在*/
    {
        PP222_SetErrorStatus(ERR_MKEY_NOT_EXIST);
        return VS_ERROR;
    }
    else
        return VS_ERROR;
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: J300_GetPin.
 * DESCRIPTION:   displays the amount and welcomes to enter the PIN
				 displays "processing" until the CancelSess Reg sent
				 Pin length: 0, 4 to 12
 * RETURN:        PIN_SUCCESS     输入密码成功
				 PIN_ESC    用户按取消键
				 PIN_NONE   用户按确认键，但是无密码输入
				 PIN_ERROR  操作失败
 * NOTES:
 * ------------------------------------------------------------------------ */
int J300_GetPin(unsigned char *PIN, char *am, unsigned char *PAN, unsigned char *PINKey, unsigned char MinPINLen, unsigned char MaxPINLen, unsigned short timeout, unsigned short KeyIndex)
{
    int iLength, ret, offset = 0;
    unsigned char cSendData[MAX_BUFFER_SIZE];

    if (MinPINLen < 4)
    {
        MinPINLen = 0;
    }

    if (MaxPINLen > 12)
    {
        MaxPINLen = 12;
    }

    memset(cSendData,0,sizeof(cSendData));
    cSendData[offset++] = MinPINLen;
    cSendData[offset++] = MaxPINLen;
    cSendData[offset++] = 0;		/*加密模式: 0=X9.8*/
    cSendData[offset++] = timeout;
    memcpy(cSendData+offset, PAN, strlen((char *)PAN) > 19 ? 19 : strlen((char *)PAN));
    offset+=20;
    if (PP222_str2long(am) != 0)
    {
        cSendData[offset++] = 0x01;		/*金额信息显示-有*/
        memcpy(cSendData+offset, am, strlen(am) > 16 ? 16 : strlen(am));
        offset+=16;
    }
    else
    {
        cSendData[offset++] = 0;		/*金额信息显示-无*/
    }
    cSendData[offset++] = TDES_KEY_LENGTH+3;
    cSendData[offset++] = 2;		/*2-TDES*/
    cSendData[offset++] = KeyIndex;
    cSendData[offset++] = TDES_KEY_LENGTH;
    if (CurrentDesFlag == TRIPLE_DES)
    {
        memcpy(cSendData+offset, PINKey, TDES_KEY_LENGTH);
    }
    else
    {
        memcpy(cSendData+offset, PINKey, 8);
        memcpy(cSendData+offset+8, PINKey, 8);
    }
    offset+=TDES_KEY_LENGTH;

    ret = J300_Crc16SendPacket(cSendData, offset, J300_CMD_GET_PIN);
    if (VS_SUCCESS != ret)
    {
        return PIN_ERROR;
    }

    memset(cSendData, 0, sizeof(cSendData));
    ret = J300_Crc16RecvPacket(J300_CMD_GET_PIN, cSendData, &iLength, timeout*1000+EXJ300_RX_TIMEOUT, sizeof(cSendData), TRUE);
    if (ret == VS_ABORT)
    {
        ret = J300_Get_PIN_Cancel(1);
        if (ret == VS_SUCCESS)
            return PIN_ABORT;
        else
            return PIN_ERROR;
    }
    else if (ret < 0)
        return PIN_ERROR;

    if (cSendData[5] == 5)		/*5:取消输入*/
        return PIN_ESC;
    if (cSendData[5] == 6)		/*6:直接按确认*/
        return PIN_NONE;
    else if (cSendData[5] == 4)	/*4:输入过频繁*/
        return PIN_NO_TOKEN;
    else if (cSendData[5] == 0x0a)
        return PIN_TIMEOUT;
    else if (cSendData[5] != 0)
        return PIN_ERROR;

    memcpy(PIN, cSendData+6, 8);
    return PIN_SUCCESS;
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: J300_GetPlainPIN.
 * DESCRIPTION:   Get plain PIN from pinpad, return length and PIN
 * RETURN:        PIN_SUCCESS     输入密码成功
				 PIN_ESC    用户按取消键
				 PIN_NONE   用户按确认键，但是无密码输入
				 PIN_ERROR  操作失败
 * NOTES:
 * FORMAT:
 * ------------------------------------------------------------------------ */
int J300_GetPlainPIN(unsigned char *inPIN, unsigned short *len, char *am, unsigned char MinPINLen, unsigned char MaxPINLen, unsigned short timeout, unsigned short KeyIndex)
{
    int iLength, ret, offset = 0;
    unsigned char cSendData[MAX_BUFFER_SIZE];
    byte PINblock[8];
    unsigned char OflinePinKey[24]=
    {
        0xfd, 0x99, 0x4d, 0xec, 0x32, 0x7a, 0x79, 0x94,
        0xac, 0x97, 0x87, 0xde, 0x76, 0x1f, 0x0b, 0x22,
        0x07, 0x7a ,0x17, 0xf3, 0x33, 0xbd, 0x59, 0xd9
    };

    if (MinPINLen < 4)
    {
        MinPINLen = 0;
    }

    if (MaxPINLen > 12)
    {
        MaxPINLen = 12;
    }

    memset(cSendData,0,sizeof(cSendData));
    cSendData[offset++] = 0;			/*0: 第一次输入脱机pin加密*/
    cSendData[offset++] = 3;			/*剩余多少次脱机PIN输入*/
    cSendData[offset++] = MinPINLen;
    cSendData[offset++] = MaxPINLen;
    cSendData[offset++] = timeout;
    if (PP222_str2long(am) != 0)
    {
        cSendData[offset++] = 0x01;		/*金额信息显示-有*/
        memcpy(cSendData+offset, am, strlen(am) > 16 ? 16 : strlen(am));
        offset+=16;
    }
    else
    {
        cSendData[offset++] = 0;
    }

    ret = J300_Crc16SendPacket(cSendData, offset, J300_CMD_GET_PLAIN_PIN);
    if (VS_SUCCESS != ret)
    {
        return PIN_ERROR;
    }

    memset(cSendData, 0, sizeof(cSendData));
    ret = J300_Crc16RecvPacket(J300_CMD_GET_PLAIN_PIN, cSendData, &iLength, timeout*1000+EXJ300_RX_TIMEOUT, sizeof(cSendData), TRUE);
    if (ret == VS_ABORT)
    {
        ret = J300_Get_PIN_Cancel(2);
        if (ret == VS_SUCCESS)
            return PIN_ABORT;
        else
            return PIN_ERROR;
    }
    else if (ret < 0)
        return PIN_ERROR;

    if (cSendData[5] == 5)		/*5:取消输入*/
        return PIN_ESC;
    if (cSendData[5] == 6)		/*6:直接按确认*/
        return PIN_NONE;
    else if (cSendData[5] == 4)	/*4:输入过频繁*/
        return PIN_NO_TOKEN;
    else if (cSendData[5] == 0x0a)
        return PIN_TIMEOUT;
    else if (cSendData[5] != 0)
        return PIN_ERROR;

    memcpy(PINblock, cSendData+6, 8);

    LOG_PRINTF("PIN BLOCK e:[%X %X %X %X %X %X %X %X]", PINblock[0], PINblock[1], PINblock[2], PINblock[3], PINblock[4], PINblock[5], PINblock[6], PINblock[7]);
//    Des_3DES_3KEY(PINblock, PINblock, OflinePinKey, FALSE);
    LOG_PRINTF("PIN BLOCK p:[%X %X %X %X %X %X %X %X]", PINblock[0], PINblock[1], PINblock[2], PINblock[3], PINblock[4], PINblock[5], PINblock[6], PINblock[7]);

    *len = (usint)(usint)(PINblock[0] & 0x0F);

    PP222_Bin2Ascii(inPIN, &PINblock[1], 7);

    return PIN_SUCCESS;
}

/* ------------------------------------------------------------------------
 * FUNCTION NAME: J300_GetCBCMAC
 * DESCRIPTION:   execute CBC MAC Algorithm.
 *                a)Divided the entire data into blocks of 8 bytes(A1, A2,...,
 *                  An).If DataLen can't be divided by 8, this function will
 *                  use 0x00 to fill it.
 *                b)(A1)e = DES(XOR(A1, '00000000'), TAKs) (Binary'0', TAKs
 *                            is Encrypted MakKey)
 *                c)(Am)e = DES(XOR((Am-1)e, Am),TAKs) (2<=m<=n)
 *                d)MACe = (An)e  (n is counter of blocks,
 *                                  Am is No. m block byte)
 * PARAMETER:     MAC     - (out) the result of caculate
 *                Data    - (in) the entire data of input
 *                DataLen - (in) the data length of input data
 *                KeyIndex - (in) master key index
 *                MacKey  - (in) the encrypt MAC key
 * RETURN:        on success:VS_SUCCESS
 *                on failure:VS_ERROR
 * NOTE:
 * -----------------------------------------------------------------------*/
int J300_GetCBCMAC(unsigned char *MAC, unsigned char *Data, unsigned short DataLen, unsigned short KeyIndex, unsigned char *MacKey)
{
    int iLength, ret, offset = 0;
    unsigned char *cSendData;

    cSendData = (unsigned char*)malloc(DataLen+100);

    memset(cSendData, 0, DataLen+100);
    cSendData[offset++] = TDES_KEY_LENGTH+3;
    cSendData[offset++] = 2;					//2-TDES
    cSendData[offset++] = KeyIndex;
    cSendData[offset++] = TDES_KEY_LENGTH;
    if (CurrentDesFlag == TRIPLE_DES)
    {
        memcpy(cSendData+offset, MacKey, TDES_KEY_LENGTH);
    }
    else
    {
        memcpy(cSendData+offset, MacKey, 8);
        memcpy(cSendData+offset+8, MacKey, 8);
    }
    offset+=TDES_KEY_LENGTH;

    cSendData[offset++] = 2; /*算法3: DES -> XOR -> DES ... ->XOR -> TDES*/
    memcpy(cSendData+offset, Data, DataLen);
    offset+=DataLen;
    ret = J300_Crc16SendPacket(cSendData, offset, J300_CMD_GET_MAC);
    if (VS_SUCCESS != ret)
    {
        free(cSendData);
        return VS_ERROR;
    }

    memset(cSendData, 0, DataLen+100);
    ret = J300_Crc16RecvPacket(J300_CMD_GET_MAC, cSendData, &iLength, EXJ300_RX_TIMEOUT, DataLen+100, FALSE);
    if (ret < 0)
    {
        free(cSendData);
        return ret;
    }

    if ((0 == cSendData[5]) && (16 == iLength))
    {
        memcpy(MAC, cSendData+6, 8);
        free(cSendData);
        return VS_SUCCESS;
    }
    else
    {
        free(cSendData);
        return VS_ERROR;
    }
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: J300_DesByMKey.
 * DESCRIPTION:   用主密钥加密/解密数据
 * RETURN:        VS_SUCCESS/VS_ERROR
 * NOTES:         Type: 0 - 解密
						1 - 加密
 * FORMAT:
 * ------------------------------------------------------------------------ */
int J300_DesByMKey(unsigned char *In, unsigned char *Out, unsigned char Type, unsigned short KeyIndex)
{
    int iRet;
    int wLen;
    unsigned char cSendData[MAX_BUFFER_SIZE];

    memset(cSendData,0,sizeof(cSendData));
    cSendData[0] = KeyIndex;		/*密钥索引号*/
    cSendData[1] = Type;			/*1-加密/0-解密*/
    memcpy(cSendData+2, In, 8);
    iRet = J300_Crc16SendPacket(cSendData, 10, J300_CMD_DATA_ENCRYPT_DECRYPT);
    if (VS_SUCCESS != iRet)
    {
        return VS_ERROR;
    }

    memset(cSendData, 0, sizeof(cSendData));
    iRet = J300_Crc16RecvPacket(J300_CMD_DATA_ENCRYPT_DECRYPT, cSendData, &wLen, EXJ300_RX_TIMEOUT, sizeof(cSendData), FALSE);

    if (iRet < 0)
        return iRet;

    if ((0 == cSendData[5]) && (16 == wLen))
    {
        memcpy(Out, cSendData+6, 8);
        return VS_SUCCESS;
    }
    else
        return VS_ERROR;
}

/* --------------------------------------------------------------------------
* FUNCTION NAME: J300_InputText
* DESCRIPTION:   输入字符串(字母和数字)
* PARAMETERS:
*   msg_disp_line (in)  -- 提示信息显示行数
*   msgPrompt (in)		-- 提示信息
*   str_disp_line (in)  -- 输出字符串显示行数
*   str			  (out)	-- 输出字符串
*   min           (in)	-- 输入数据最小长度
*   max           (in)	-- 输入数据最大长度
*   disp_mode     (in)	-- 输入数据显示模式(参考DISP_FORMAT定义)
*   timeout       (in)	-- 输入超时(秒)
* RETURN:
*			返回值大于0:输入数据长度
*			返回值小于0:INPUT_FAILED/INPUT_TIMEOUT/INPUT_USERABORT
* NOTE:
* -------------------------------------------------------------------------*/
int J300_InputText(int msg_disp_line, char *msgPrompt, int str_disp_line, char *str, usint min, usint max, int disp_mode, usint timeout)
{
    int iRet;
    int wLen = 0,  offset = 0, msglen = 0;
    unsigned char cSendData[MAX_BUFFER_SIZE];
    byte mode = disp_mode;

    LOG_PRINTF("mode=[0x%02X] [0x%02X]", mode, (mode&0x0F)|0x20);
    msglen = strlen(msgPrompt);

    memset(cSendData, 0, sizeof(cSendData));
    cSendData[offset++] = msglen;
    memcpy(cSendData+offset, msgPrompt, msglen);
    offset+=msglen;
    cSendData[offset++] = min;
    cSendData[offset++] = max;
    cSendData[offset++] = (mode&0x0F)|0x20;			/*mode: 0 - 左对齐；1 - 右对齐 ；2 - 居中对齐*/
    cSendData[offset++] = timeout;

    iRet = J300_Crc16SendPacket(cSendData, offset, J300_CMD_INPUT_TEXT);
    if (VS_SUCCESS != iRet)
    {
        return VS_ERROR;
    }

    memset(cSendData, 0, sizeof(cSendData));
    iRet = J300_Crc16RecvPacket(J300_CMD_INPUT_TEXT, cSendData, &wLen, timeout*1000+EXJ300_RX_TIMEOUT, sizeof(cSendData), FALSE);
    if (iRet < 0)
        return iRet;

    if (cSendData[5] == 0xFF)
        return INPUT_TIMEOUT;
    else if (cSendData[5] == 0xFE)
        return INPUT_USERABORT;
    else if (cSendData[5] == 0)
    {
        memcpy(str, cSendData+6, wLen-8);
        LOG_PRINTF("str=[%s]", str);
        return (wLen-8);
    }
    else
        return INPUT_FAILED;
}

int J300_LcdDisplayLogo (unsigned char index)
{
    int iRet;
    int wLen;
    unsigned char cSendData[MAX_BUFFER_SIZE];

    memset(cSendData,0,sizeof(cSendData));
    cSendData[0] = index;
    iRet = J300_Crc16SendPacket(cSendData, 1, J300_CMD_SHOW_LOGO);
    if (VS_SUCCESS != iRet)
    {
        return VS_ERROR;
    }

    memset(cSendData, 0, sizeof(cSendData));
    iRet = J300_Crc16RecvPacket(J300_CMD_SHOW_LOGO, cSendData, &wLen, EXJ300_RX_TIMEOUT, sizeof(cSendData), FALSE);

    if (iRet < 0)
        return iRet;

    if ((0 == cSendData[5]) && (8==wLen))
        return VS_SUCCESS;
    else
        return VS_ERROR;
}

int J300_LcdCls(void)
{
    int iRet;
    int wLen;
    unsigned char cSendData[MAX_BUFFER_SIZE];

    memset(cSendData,0,sizeof(cSendData));
    iRet = J300_Crc16SendPacket(cSendData, 0, J300_CMD_LCD_CLS);
    if (VS_SUCCESS != iRet)
    {
        return VS_ERROR;
    }

    memset(cSendData, 0, sizeof(cSendData));
    iRet = J300_Crc16RecvPacket(J300_CMD_LCD_CLS, cSendData, &wLen, EXJ300_RX_TIMEOUT, sizeof(cSendData), FALSE);
    if (iRet < 0)
        return iRet;

    if ((0 == cSendData[5]) && (8==wLen))
        return VS_SUCCESS;
    else
        return VS_ERROR;
}

int J300_LcdDispStr(byte x, byte y, char *str)
{
    int iRet,len;
    int wLen;
    unsigned char cSendData[MAX_BUFFER_SIZE];

    memset(cSendData,0,sizeof(cSendData));
    cSendData[0] = x;
    cSendData[1] = y;
    len = strlen(str);
    memcpy(cSendData+2, str, len);
    iRet = J300_Crc16SendPacket(cSendData, len+2, J300_CMD_SHOW_STRING);
    if (VS_SUCCESS != iRet)
    {
        return VS_ERROR;
    }

    memset(cSendData, 0, sizeof(cSendData));
    iRet = J300_Crc16RecvPacket(J300_CMD_SHOW_STRING, cSendData, &wLen, EXJ300_RX_TIMEOUT, sizeof(cSendData), FALSE);
    if (iRet < 0)
        return iRet;

    if ((0 == cSendData[5]) && (8 == wLen))
        return VS_SUCCESS;
    else
        return VS_ERROR;
}

int J300_Get_PIN_Cancel(int flag)
{
    int iRet;
    int wLen;
    unsigned char cSendData[MAX_BUFFER_SIZE];
    unsigned short wCmd = J300_CMD_CANCEL_PIN;

    if (flag == 1)
        wCmd = J300_CMD_CANCEL_PIN;
    else if (flag == 2)
        wCmd = J300_CMD_CANCEL_PLAIN_PIN;

    memset(cSendData,0,sizeof(cSendData));
    iRet = J300_Crc16SendPacket(cSendData, 0, wCmd);
    if (VS_SUCCESS != iRet)
    {
        return VS_ERROR;
    }

    memset(cSendData, 0, sizeof(cSendData));
    iRet = J300_Crc16RecvPacket(wCmd, cSendData, &wLen, EXJ300_RX_TIMEOUT, sizeof(cSendData), FALSE);

    if (iRet < 0)
        return iRet;

    if ((0x0c == cSendData[5]) && (8==wLen))
        return VS_SUCCESS;
    else
        return VS_ERROR;
}

int J300_LoadKey(char KeyType, unsigned short Key_index, unsigned char *Key_value)
{
    int iLength, ret, offset = 0;
    unsigned char cSendData[MAX_BUFFER_SIZE];

    POS_DEBUG_WITHTAG("UsbserDemo","======================");
    memset(cSendData,0,sizeof(cSendData));
    if ((KeyType == KLK) || (mkey_format == CLEAR_FORMAT))
        cSendData[offset++] = 0;					//0-明文KEY
    else
        cSendData[offset++] = 2;					//2-TDES加密
    cSendData[offset++] = KeyType;					//密钥类型:Master Key
    cSendData[offset++] = TDES_KEY_LENGTH;			//密钥长度
    cSendData[offset++] = Key_index;				//密钥索引
    if ((KeyType == KLK) || (CurrentDesFlag == TRIPLE_DES))
    {
        memcpy(cSendData+offset, Key_value, TDES_KEY_LENGTH);
    }
    else
    {
        memcpy(cSendData+offset, Key_value, 8);
        memcpy(cSendData+offset+8, Key_value, 8);
    }
    offset+=TDES_KEY_LENGTH;

    ret = J300_Crc16SendPacket(cSendData, offset, J300_CMD_LOAD_KEY);
    if (VS_SUCCESS != ret)
    {
        return VS_ERROR;
    }

    memset(cSendData, 0, sizeof(cSendData));
    ret = J300_Crc16RecvPacket(J300_CMD_LOAD_KEY, cSendData, &iLength, EXJ300_RX_TIMEOUT, sizeof(cSendData), FALSE);
    if (ret < 0)
        return ret;

    if ((0 == cSendData[5]) && (8 == iLength))
        return VS_SUCCESS;
    else
        return VS_ERROR;
}

int J300_ClearKeys(void)
{
    int iLength, ret;
    unsigned char cSendData[MAX_BUFFER_SIZE];

    memset(cSendData,0,sizeof(cSendData));
    ret = J300_Crc16SendPacket(cSendData, 0, J300_CMD_CLEAR_KEYS);
    if (VS_SUCCESS != ret)
    {
        return VS_ERROR;
    }

    memset(cSendData, 0, sizeof(cSendData));
    ret = J300_Crc16RecvPacket(J300_CMD_CLEAR_KEYS, cSendData, &iLength, 100*1000, sizeof(cSendData), FALSE);
    if (ret < 0)
        return ret;

    if ((0 == cSendData[5]) && (8 == iLength))
        return VS_SUCCESS;
    else
        return VS_ERROR;
}
