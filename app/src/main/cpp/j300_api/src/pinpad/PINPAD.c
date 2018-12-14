/****************************************************************************
 * FILE NAME:   PINPAD.C
 * MODULE NAME: PINPAD driver.
 * PROGRAMMER:  Wu Xingyu
 * DESCRIPTION:
 * REVISION:    Feb-21-2005
 ****************************************************************************/
/*==========================================*
 *         I N T R O D U C T I O N          *
 *==========================================*/
/* void */

/*==========================================*
 *             I N C L U D E S              *
 *==========================================*/

#include <pos_debug.h>
#include "pinpad/PINPAD.h"
#include "pinpad/util.h"
#include "pinpad/j300.h"

/*==========================================*
 *        D E F I N I T I O N S             *
 *==========================================*/
typedef enum
{
    PP1000se = 1,
    PP1000seC,
    PP1000seV3,
    PP820,
    PPJ300,
} EXTERN_PP_TYPE;

unsigned short pinpad_type;
unsigned short pinpad_mode;
unsigned short mkey_format;
int pin_handle;

static short PP1000_type;
int vappad_init;          /*�Ƿ��ѳ�ʼ��vappad*/

/*=========================================*
*     P U B L I C   F U N C T I O N S     *
*=========================================*/

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_SetPPType.
 * DESCRIPTION:   ����������̵�����(����/����)��ģʽ������Կ��ʽ
 * PARAMETER:
	pp_type - INTERNAL_PINPAD
            - EXTERNAL_PINPAD
	pp_mode  �� PP1000SE_MODE
			 �� PRC_MODE
			 �� PRC_MODE_CLEAR
			 �� PP222_MODE
			 �� PP222_MODE_TDES
	key_format - CLEAR_FORMAT
	           - ENCRYPTED_FORMAT
 * RETURN:        ��
 * NOTES:         �ڵ�������PINPAD����ǰ��������øú�����ȷ��PINPAD����(Ĭ��ΪINTERNAL_PINPAD)��
 *                ģʽ(Ĭ��ΪPP222_DES_MODE)������Կ��ʽ(Ĭ��ΪCLEAR_FORMAT)��
 * ------------------------------------------------------------------------ */
void Pinpad_SetPPType(unsigned short pp_type, unsigned short pp_mode, unsigned short key_format)
{
    pinpad_type = pp_type;
    pinpad_mode = pp_mode;
    mkey_format = key_format;

    if (key_format != CLEAR_FORMAT && key_format != ENCRYPTED_FORMAT)
    {
        mkey_format = CLEAR_FORMAT;
    }

    
    J300_SetPPType(pp_type, pp_mode, key_format);
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_SetNewExPPType.
 * DESCRIPTION:   ����������̵����͡�ģʽ������Կ��ʽ
 * PARAMETER:
	pp_type   - EXTERNAL_Vx680_PINPAD
	pp_mode  �� PP222_MODE
			 �� PP222_MODE_TDES
	key_format - CLEAR_FORMAT
	           - ENCRYPTED_FORMAT
	device_name - DEV_COM1, DEV_USBSER
 * RETURN:        ��
 * NOTES:         �ڵ�������PINPAD����ǰ��������øú�����ȷ��PINPAD����(Ĭ��ΪINTERNAL_PINPAD)��
 *                ģʽ(Ĭ��ΪPP222_DES_MODE)������Կ��ʽ(Ĭ��ΪCLEAR_FORMAT)��
 * ------------------------------------------------------------------------ */
int Pinpad_SetNewExPPType(unsigned short pp_type, unsigned short pp_mode, unsigned short key_format, char * device_name)
{
    return VS_SUCCESS;
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_SetListenPort.
 * DESCRIPTION:   sets listen port's device name
 * RETURN:        none.
 * NOTES:
 * ------------------------------------------------------------------------ */
void Pinpad_SetListenPort(char * device_name)
{
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_Connect.
 * DESCRIPTION:   sets port's parameters.
 * RETURN:        none.
 * NOTES:
 * ------------------------------------------------------------------------ */
int Pinpad_Connect(void)
{
    return Pinpad_Connect2("/dev/ttyS0");
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_Connect2.
 * DESCRIPTION:   sets port's parameters.
 * RETURN:        none.
 * NOTES:
 * ------------------------------------------------------------------------ */
int Pinpad_Connect2(char * device_name)
{
    int handle;

    handle = J300_Connect(device_name);
    if (handle < 0)
    {
        POS_DEBUG_WITHTAG("UsbserDemo","J300_Connect failed.");
        return VS_ERROR;
    }


    POS_DEBUG_WITHTAG("UsbserDemo","J300_Connect Success.");
    return VS_SUCCESS;
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_Idle.
 * DESCRIPTION:   Cancel Session Request / Return to Idle State
 * RETURN:        .
 * NOTES:         none.
 * ------------------------------------------------------------------------ */
void Pinpad_Idle(void)
{
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_DisplayLogo.
 * DESCRIPTION:   DisplayLogo.
 * PARAMETER:
 *       idx   (in) -- logo idx
 * RETURN:        .
 * NOTES:        For Spos.
 * ------------------------------------------------------------------------ */
int Pinpad_DisplayLogo( unsigned char idx )
{
    return J300_LcdDisplayLogo(idx);
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_SetIdleMsg.
 * DESCRIPTION:   Set / Reset Idle Prompt
 * RETURN:        .
 * NOTES:         none.
 * ------------------------------------------------------------------------ */
int Pinpad_SetIdleMsg(char *IdleMsg)
{
    return J300_SetIdleMsg(IdleMsg);
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: PINpad_DisplayMsg.
 * DESCRIPTION:   display two messages on two lines of Pinpad until next
				 command received
 * RETURN:        VS_SUCCESS/VS_ERROR
 * NOTES:         maximum length of message is 16 characters
 * ------------------------------------------------------------------------ */
int Pinpad_DisplayMsg(char *Line1Msg, char *Line2Msg)
{
    if (!Line1Msg && !Line2Msg)
    {
        LOG_PRINTF("NULL Msg");
        return VS_ERROR;
    }

    return J300_DisplayMsg(Line1Msg, Line2Msg);
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_LoadKLK
 * DESCRIPTION:   ������Կ������Կ
 * PARAMETER:
 *     Input: KLK - 16bytes
 * RETURN:		VS_SUCCESS/VS_ERROR
 * NOTES:         none.
 * ------------------------------------------------------------------------ */
int Pinpad_LoadKLK(unsigned char *KLK)
{
    return J300_LoadKLK(KLK);
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_CheckKLK
 * DESCRIPTION:   �����Կ������Կ�Ƿ����
 * PARAMETER:
 * RETURN:		VS_SUCCESS/VS_ERROR
 * NOTES:         none.
 * ------------------------------------------------------------------------ */
int Pinpad_CheckKLK(void)
{
    return J300_CheckKLK();
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_LoadMasterKey
 * DESCRIPTION:   ��ָ��λ����װ����Կ
 * PARAMETER:
 *                number  - ����Կ������00��99
 *                Mkey    - ����Կ
 * RETURN:        VS_SUCCESS/VS_ERROR
 * NOTES:         none.
 * ------------------------------------------------------------------------ */
int Pinpad_LoadMasterKey(unsigned short number, unsigned char *MKey)
{
    POS_DEBUG_WITHTAG("UsbserDemo","======================");
    return J300_LoadMasterKey(number, MKey);
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_SelectKey
 * DESCRIPTION:   ѡ������Կ
 * PARAMETER:
 *                number  - ����Կ������00��99
 * RETURN:        VS_SUCCESS/VS_ERROR
 * NOTES:         none.
 * ------------------------------------------------------------------------ */
int Pinpad_SelectKey(unsigned short number)
{
    return J300_SelectKey(number);
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_CheckMKey
 * DESCRIPTION:   ���ָ��λ���Ƿ��������Կ
 * PARAMETER:
 *                number  - ����Կ������00��99
 * RETURN:        VS_SUCCESS/VS_ERROR
 * NOTES:         none.
 * ------------------------------------------------------------------------ */
int Pinpad_CheckMKey(usint number)
{
    return J300_SelectKey(number);
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_GetPin.
 * DESCRIPTION:   displays the amount and welcomes to enter the PIN
				 displays "processing" until the CancelSess Reg sent
				 Pin length: 0, 4 to 12
 * RETURN:        PIN_SUCCESS     ��������ɹ�
				 PIN_ESC    �û���ȡ����
				 PIN_NONE   �û���ȷ�ϼ�����������������
				 PIN_ERROR  ����ʧ��
 * NOTES:
 * ------------------------------------------------------------------------ */
int Pinpad_GetPin(unsigned char *PIN, char *am, unsigned char *PAN, unsigned char *PINKey,
                  unsigned char MinPINLen, unsigned char MaxPINLen, unsigned short timeout, unsigned short KeyIndex)
{
    return J300_GetPin(PIN, am, PAN, PINKey, MinPINLen, MaxPINLen, timeout, KeyIndex);
}

//added by baijz 20131206 ��ʾ��Ϣ����Ӧ�ô���

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_GetPin_WithMsg.
 * DESCRIPTION:   displays the amount and welcomes to enter the PIN
				 displays "processing" until the CancelSess Reg sent
				 Pin length: 0, 4 to 12
 * RETURN:        PIN_SUCCESS     ��������ɹ�
				 PIN_ESC    �û���ȡ����
				 PIN_NONE   �û���ȷ�ϼ�����������������
				 PIN_ERROR  ����ʧ��
 * NOTES:
 * ------------------------------------------------------------------------ */
int Pinpad_GetPin_WithMsg(unsigned char *PIN, char *prompt1, char *prompt2, char *prompt3, char *am, unsigned char *PAN, unsigned char *PINKey,
                          unsigned char MinPINLen, unsigned char MaxPINLen, unsigned short timeout, unsigned short KeyIndex)
{
    return PIN_SUCCESS;			//��ʱδʵ��
}

//ended 20131206

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_GetPlainPIN.
 * DESCRIPTION:   Get plain PIN from pinpad, return length and PIN
 * RETURN:        PIN_SUCCESS     ��������ɹ�
				 PIN_ESC    �û���ȡ����
				 PIN_NONE   �û���ȷ�ϼ�����������������
				 PIN_ERROR  ����ʧ��
 * NOTES:
 * FORMAT:
 * ------------------------------------------------------------------------ */
int Pinpad_GetPlainPIN(unsigned char *inPIN, unsigned short *len, char *am,
                       unsigned char MinPINLen, unsigned char MaxPINLen, unsigned short timeout, unsigned short KeyIndex)
{
    return J300_GetPlainPIN(inPIN, len, am, MinPINLen, MaxPINLen, timeout, KeyIndex);
}

//added by baijz 20131206 ��ʾ��Ϣ����Ӧ�ô���

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_GetPlainPIN2.
 * DESCRIPTION:   Get plain PIN from pinpad, return length and PIN
 * RETURN:        PIN_SUCCESS     ��������ɹ�
				 PIN_ESC    �û���ȡ����
				 PIN_NONE   �û���ȷ�ϼ�����������������
				 PIN_ERROR  ����ʧ��
 * NOTES:
 * FORMAT:
 * ------------------------------------------------------------------------ */
int Pinpad_GetPlainPIN_WithMsg(unsigned char *inPIN, unsigned short *len, char *prompt1, char *prompt2, char *prompt3, char *am,
                               unsigned char MinPINLen, unsigned char MaxPINLen, unsigned short timeout, unsigned short KeyIndex)
{
    return PIN_SUCCESS;			//��ʱδʵ��
}

//ended 20131206

/* ------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_GetCBCMAC
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
int Pinpad_GetCBCMAC(unsigned char *MAC, unsigned char *Data, unsigned short DataLen, unsigned short KeyIndex,
                     unsigned char *MacKey)
{
    return J300_GetCBCMAC(MAC, Data, DataLen, KeyIndex, MacKey);
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_DesByMKey.
 * DESCRIPTION:   ������Կ����/��������
 * RETURN:        VS_SUCCESS/VS_ERROR
 * NOTES:         Type: 0 - ����
						1 - ����
 * FORMAT:
 * ------------------------------------------------------------------------ */
int Pinpad_DesByMKey(unsigned char *In, unsigned char *Out, unsigned char Type, unsigned short KeyIndex)
{
    return J300_DesByMKey(In, Out, Type, KeyIndex);
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_close.
 * DESCRIPTION:   close pinpad device
 * RETURN:
 * NOTES:
 * FORMAT:
 * ------------------------------------------------------------------------ */
void Pinpad_Close(void)
{
    J300_Disconnect();
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_CheckMode.
 * DESCRIPTION:   check PINPad mode
 * RETURN:
 MODE_PP1000SE/MODE_PRC/MODE_222
 * NOTES:
 * FORMAT:
 * ------------------------------------------------------------------------ */
int Pinpad_CheckMode(char * device_name)
{
    return VS_SUCCESS;
}


/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_Switch2Reader.
 * DESCRIPTION:   PP1000se switch to CTLS reader
 * RETURN:
		VS_SUCCESS/VS_ERR
 * NOTES:
 * FORMAT:
 * ------------------------------------------------------------------------ */
int Pinpad_Switch2Reader(void)
{
    return VS_SUCCESS;
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_Ping.
 * DESCRIPTION:   Ping PINpad device
 * RETURN:
		VS_SUCCESS/VS_ERR
 * NOTES:
 * FORMAT:
 * ------------------------------------------------------------------------ */
int Pinpad_Ping(void)
{
    return VS_SUCCESS;
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_LoadMACKey_ICBC.
 * DESCRIPTION:   Load MAC Key to VSS. for ICBC
 * RETURN:
		VS_SUCCESS/VS_ERR
 * NOTES:
 * FORMAT:
 * ------------------------------------------------------------------------ */
int Pinpad_LoadMACKey_ICBC(unsigned short KeyIndex, unsigned char *MacKey)
{
    return VS_SUCCESS;
}

/* ------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_GetMAC_ICBC
 * DESCRIPTION:   execute CBC MAC Algorithm.
 * PARAMETER:     MAC     - (out) the result of caculate
 *                Data    - (in) the entire data of input
 *                DataLen - (in) the data length of input data
 * RETURN:        on success:VS_SUCCESS
 *                on failure:VS_ERROR
 * NOTE:
 * -----------------------------------------------------------------------*/
int Pinpad_GetMAC_ICBC(unsigned char *MAC, unsigned char *Data, int DataLen)
{
    return VS_SUCCESS;
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_GetExDeviceType.
 * DESCRIPTION:   Get Extern PINpad device type
 * RETURN:
		VS_ERR/EXTERNAL_PP1000se/EXTERNAL_PP1000seC/EXTERNAL_PP1000seV3
 * NOTES:
 * FORMAT:
 * ------------------------------------------------------------------------ */
int Pinpad_GetExDeviceType(void)
{
    return EXTERNAL_J300;
}

/* --------------------------------------------------------------------------
* FUNCTION NAME: Pinpad_InputText
* DESCRIPTION:   �����ַ���(��ĸ������)
* PARAMETERS:
*   msg_disp_line (in)  -- ��ʾ��Ϣ��ʾ����
*   msgPrompt (in)		-- ��ʾ��Ϣ
*   str_disp_line (in)  -- ����ַ�����ʾ����
*   str			  (out)	-- ����ַ���
*   min           (in)	-- ����������С����
*   max           (in)	-- ����������󳤶�
*   disp_mode     (in)	-- ����������ʾģʽ(�ο�DISP_FORMAT����)
*   timeout       (in)	-- ���볬ʱ(��)
* RETURN:
*			����ֵ����0:�������ݳ���
*			����ֵС��0:INPUT_FAILED/INPUT_TIMEOUT/INPUT_USERABORT
* NOTE:
* -------------------------------------------------------------------------*/
int Pinpad_InputText(int msg_disp_line, char *msgPrompt, int str_disp_line, char *str, unsigned short min, unsigned short max, int disp_mode, unsigned short timeout)
{
    return J300_InputText(msg_disp_line, msgPrompt, str_disp_line, str, min, max, disp_mode, timeout);
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_GetErrorSatus.
 * DESCRIPTION:   ��ȡ����״̬��
 * RETURN:
		PINPAD_NO_ERROR/ERR_COMM_ERROR/ERR_MKEY_NOT_EXIST
 * NOTES:
 * FORMAT:
 * ------------------------------------------------------------------------ */
int Pinpad_GetErrorSatus(void)
{
    return PINPAD_NO_ERROR;
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_GetDeviceInfo.
 * DESCRIPTION:   ��ȡ����PP1000se�豸��Ϣ(model number, S/N, OS version��)
 * RETURN:
		VS_SUCCESS/VS_ERR
 * NOTES:
 * FORMAT:
 * ------------------------------------------------------------------------ */
int Pinpad_GetDeviceInfo(PP1000_DEVICE_INFO *pInfo)
{
    return VS_SUCCESS;
}

/*add by taoz1 20151117*/
/* --------------------------------------------------------------------------
* FUNCTION NAME: Pinpad_SM4_GetSupportFlag
* DESCRIPTION:   ��ȡPinpad�Ƿ�֧��SM4�㷨��ʶ
* PARAMETERS:
*   flag      (out)    -- ֧�ֱ�־ (1:֧��,0:��֧��)
* RETURN:
*			����ֵ����0:  �ӿڵ��óɹ�
*			����ֵ����-1: �ӿڵ���ʧ��
* NOTE:     none.
* -------------------------------------------------------------------------*/

int Pinpad_SM4_GetSupportFlag(int* flag)
{
    return VS_SUCCESS;
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_SM4_LoadKLK
 * DESCRIPTION:   ������Կ������Կ
 * PARAMETER:
 *     Input: KLK - 16bytes
 * RETURN:		VS_SUCCESS/VS_ERROR
 * NOTES:         none.
 * ------------------------------------------------------------------------ */
int Pinpad_SM4_LoadKLK(unsigned char *KLK)
{
    return VS_SUCCESS;
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_SM4_LoadMKey
 * DESCRIPTION:   ��ָ��λ����װ����Կ
 * PARAMETER:
 *                index   - ����Կ������0��99
 *                Mkey    - ����Կ
 *
 * RETURN:
 *			 ����ֵ����0:  �ӿڵ��óɹ�
 *			 ����ֵ����-1: �ӿڵ���ʧ��
 * NOTES:         none.
 * ------------------------------------------------------------------------ */
int Pinpad_SM4_LoadMKey(unsigned short index, unsigned char *MKey)
{
    return VS_SUCCESS;
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_SM4_GetPin.
 * DESCRIPTION:   displays the amount and welcomes to enter the PIN
                  displays "processing" until the CancelSess Reg sent
                  Pin length: 0, 4 to 12
 * PARAMETERS:
 *	 PIN 			(out) -- PINBLOCK
 *	 am 			(in)  -- ���׽��
 *	 PAN 			(in)  -- ����
 *	 wkey			(in)  -- Pin������Կ,16�ֽ�
 *	 MinPINLen		(in)  -- ����������С����
 *	 MaxPINLen		(in)  -- ����������󳤶�
 *	 timeout		(in)  -- ���볬ʱ(��)
 *	 mkey_idx		(in)  -- SM4����Կ����
 * RETURN:        PIN_SUCCESS: ��������ɹ�
                  PIN_ESC���û���ȡ����
                  PIN_NONE:�û���ȷ�ϼ�����������������
                  PIN_ERROR������ʧ��
 * NOTES:         .
 * ------------------------------------------------------------------------ */
int Pinpad_SM4_GetPin(unsigned char *PIN, char *am, unsigned char *PAN, unsigned char *wkey,
                      unsigned char MinPINLen, unsigned char MaxPINLen, unsigned short timeout, unsigned short mkey_idx)
{
    return VS_SUCCESS;
}

/* ------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_SM4_GetMAC
 * DESCRIPTION:   execute MAC Algorithm base on SM4.
 * PARAMETER:     MAC     - (out) MAC���(ECBģʽ 8bytes,CBCģʽ 16bytes)
 *                Data    - (in)  ��������
 *                DataLen - (in)  �������ݳ��� <=2048bytes
 *                mkey_idx- (in)  SM4����Կ����
 *                MacKey  - (in)  MAC������Կ,16�ֽ�
  *               mode    - (in)  MAC�㷨����ģʽ(1:ECB,2:CBC)
 * RETURN:        on success:0
 *                on failure:-1
 * NOTE: CBC-MAC is not support
 * -----------------------------------------------------------------------*/
int Pinpad_SM4_GetMAC(unsigned char *MAC, unsigned char *Data, unsigned short DataLen,
                      unsigned short mkey_idx, unsigned char *MacKey,unsigned char mode)
{
    return VS_SUCCESS;
}
/* ------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_SM4_Crypto
 * DESCRIPTION:   excute SM4 Encrypt/Decrypt Algorithm(SM4-ECBģʽ).
 * PARAMETER:     outlen     - (out) ������ݳ���
 *                outdata    - (out) �������
 *                inlen      - (in)  �������ݳ��� <=2048bytes
 *                indata     - (in)  ��������
 *                wkey       - (in)  ���ļ���/������Կ,16�ֽ�
 *                mkey_index - (in)  SM4����Կ����
 *                mode       - (in)  ����ģʽ(1:����,2:����)
 * RETURN:        on success:0
 *                on failure:-1
 * NOTE:
 * -----------------------------------------------------------------------*/
int Pinpad_SM4_Crypto(unsigned short* outlen,unsigned char *outdata, unsigned short inlen,
                      unsigned char *indata,unsigned char *wkey,unsigned short mkey_index,unsigned char mode)
{
    return VS_SUCCESS;
}
/*end 20151117*/

/* ------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_GetExDeviceSN
 * DESCRIPTION:   ��ȡ�����������SN(Ŀǰֻ֧��V3)
 * PARAMETER:     SN          - (out) �������SN
 *                       SN_len     - (out) �������SN�ĳ���
 * RETURN:        VS_SUCCESS/VS_ERR
 * NOTE:
 * -----------------------------------------------------------------------*/
int Pinpad_GetExDeviceSN(byte *SN, int *SN_len)
{
    return VS_SUCCESS;
}



