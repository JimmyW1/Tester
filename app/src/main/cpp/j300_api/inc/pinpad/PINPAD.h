/****************************************************************************
 * FILE NAME:   PINPAD.H
 * MODULE NAME: PINPAD driver.
 * PROGRAMMER:  Wu Xingyu
 * DESCRIPTION: Public functions prototypes
 * REVISION:    2005-02-21
 ****************************************************************************/

#ifndef __PINPAD_H
#define __PINPAD_H
                 /*==========================================*
                  *         I N T R O D U C T I O N          *
                  *==========================================*/
                 /*==========================================*
                  *             I N C L U D E S              *
                  *==========================================*/
                 /*==========================================*
                  *           D E F I N I T I O N S          *
                  *==========================================*/
                  
#define  PIN_NONE         2   /*get none pin key from pinpad*/
#define  PIN_SUCCESS      1   /*get pin key from pinpad*/
#define  PIN_ESC          0   /*cashier abort by hit bESC or bMENU*/
#define  PIN_ERROR       -1   /*length of pin is not 8*/
#define  PIN_ABORT       -2   /*press bESC on POS*/
#define	 PIN_MKEY_DIFF	 -3	  /*mixed type pinpad, master key is different in vss and pp*/
#define  PIN_TIMEOUT     -4   /*input pin timeout*/
#define  PIN_NO_TOKEN    -5   /*no token available in the bucket*/

/*PINpad����*/
#define INTERNAL_PINPAD				0			/*�����������*/
#define EXTERNAL_PINPAD				1           /*�����������*/
#define MIXED_PINPAD				2           /*�����ϼ���*/

/*����豸����*/
#define EXTERNAL_PP1000se			1			/*����PP1000se�������*/
#define EXTERNAL_PP1000seC			2			/*���ù���PP1000seC�������*/
#define EXTERNAL_Vx820_PINPAD		3           /*����Vx820�������*/
#define EXTERNAL_PP1000seV3			4			/*����PP1000se V3�������*/
#define EXTERNAL_J300				5			/*����J300�������*/

/*PINpadģʽ*/
#define PP1000SE_MODE        0       /*��DES����KLK*/
#define PRC_MODE             1       /*3DES����KLK*/
#define PRC_MODE_CLEAR		 2       /*3DES����KLK*/
#define PP222_DES_MODE       3       /*��DES����KLK*/
#define PP222_3DES_MODE      4       /*3DES����KLK*/
#define SM4_MODE			 5		 /*SM4*/

/*����Կ��ʽ*/
#define CLEAR_FORMAT		 0        /*����*/
#define ENCRYPTED_FORMAT     1        /*����*/


/*����ֵ����*/
#define VS_J300				  4				/* for J300*/
#define VS_820				  3             /* for Vx820 */
#define VS_PP1000SE_V3        2             /* for PP1000SE V3 */
#define VS_PP1000SEC          1             /* for PP1000SEC */
#define VS_SUCCESS            0             /* General purpose error code */
#define VS_ERR              (-1)
#define VS_ERROR            (-1)            /* Operator error */
#define VS_FAILURE          (-2)            /* System-level error */
#define VS_ESCAPE           (-3)            /* Operator quit transaction */
#define VS_TIMEOUT          (-4)            /* Time out */
#define VS_CARRIER          (-5)            /* Modem Lost Carrier */
#define VS_EOB              (-6)            /* End of Batch */
#define VS_ABORT            (-99)           /* Operation Aborted (obsolete) */


#define VS_MAC_ERROR        (-10)			/* PRC decryption/MAC error*/

/*PP1000 ģʽ*/
#define MODE_PP1000SE       '2'
#define MODE_PRC            '4'
#define MODE_222            '7'

/*Pinpad_InputText()����ֵ,ͬATOOL����ֵ*/
#ifndef INPUT_USERABORT
#define INPUT_USERABORT		-1
#endif

#ifndef INPUT_TIMEOUT
#define INPUT_TIMEOUT		-2
#endif

#ifndef INPUT_FAILED
#define INPUT_FAILED		-3
#endif

/*Yaping 2014.06.24 Error status */
#define PINPAD_NO_ERROR           0                 /* No error */
#define ERR_COMM_ERROR            (-101)            /* communication error between pinpad and terminal */
#define ERR_MKEY_NOT_EXIST        (-102)            /* Master Key not exist */

/*add by taoz1 20151124*/
#define ECB_MAC_BASE_SM4          (1)
#define CBC_MAC_BASE_SM4          (2)
#define SM4_ENCRYPT_MODE          (1)
#define SM4_DECRYPT_MODE          (2)
/*end*/


typedef struct
{
	unsigned char SPEDVersion[8+1];		/*PINpad����ʱ��ʾ�İ汾�� in format "XX.XX.XX"*/
	unsigned char Secure;				/* 's' = secure version*/
	unsigned char DevName[4+1];			/*�豸���� 1000*/	
	unsigned char CountryCode[2+1];		/*Country Code, for example "US"*/
	unsigned char ModelCode[2+1];		/*Model Code, 50/70 etc.*/
	unsigned char ModelVersion;			/*Model Version*/
	unsigned char HWCode[3+1];			/*Hardware Code*/
	unsigned char SerialNumber[12+1];	/*Serial number*/
	unsigned char ProductDate[8+1];		/*Product Date*/
	unsigned char OSversion[16+1];		/*OS Version*/
	unsigned char FWCheckSum[4+1];		/*Firmware Checksum*/
} PP1000_DEVICE_INFO;


                 /*==========================================*
                  *          P A B L I C   T Y P E S         *
                  *==========================================*/
                 /*==========================================*
                  *        P R I V A T E  D A T A            *
                  *==========================================*/
                 /*==========================================*
                  *        M I S C E L L A N E O U S         *
                  *==========================================*/
                 /*==========================================*
                  *   P U B L I C     F U N C T I O N S      *
                  *==========================================*/

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
void Pinpad_SetPPType(unsigned short pp_type, unsigned short pp_mode, unsigned short key_format);

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_Connect.
 * DESCRIPTION:   sets port's parameters.
 * RETURN:        VS_SUCCESS/VS_ERROR.
 * NOTES:
 * ------------------------------------------------------------------------ */
int Pinpad_Connect(void);

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_Connect2.
 * DESCRIPTION:   sets port's parameters.
 * RETURN:        none.
 * NOTES:
 * ------------------------------------------------------------------------ */
int Pinpad_Connect2(char * device_name);

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_Close.
 * DESCRIPTION:   close pinpad device
 * RETURN:        
 * NOTES:         
 * FORMAT:        
 * ------------------------------------------------------------------------ */
void Pinpad_Close(void);

/* --------------------------------------------------------------------------
 * FUNCTION NAME: PINpad_Idle.
 * DESCRIPTION:   Cancel Session Request / Return to Idle State
 * RETURN:        .
 * NOTES:         none.
 * ------------------------------------------------------------------------ */
void Pinpad_Idle(void);

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_DisplayLogo.
 * DESCRIPTION:   DisplayLogo.
 * PARAMETER:
 *       idx   (in) -- logo idx
 * RETURN:        .
 * NOTES:        For Spos.
 * ------------------------------------------------------------------------ */
int Pinpad_DisplayLogo( unsigned char idx );

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_SetIdleMsg.
 * DESCRIPTION:   Set / Reset Idle Prompt
 * RETURN:        .
 * NOTES:         none.
 * ------------------------------------------------------------------------ */
int Pinpad_SetIdleMsg(char *IdleMsg);

/* --------------------------------------------------------------------------
 * FUNCTION NAME: PINpad_DisplayMsg.
 * DESCRIPTION:   display two messages on two lines of Pinpad until next
                  command received
 * RETURN:        VS_SUCCESS/VS_ERROR       
 * NOTES:         maximum length of message is 16 characters
 * ------------------------------------------------------------------------ */
int Pinpad_DisplayMsg(char *Line1Msg,char *Line2Msg);
				  
/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_LoadKLK
 * DESCRIPTION:   ������Կ������Կ
 * PARAMETER:
 *   Input: KLK - 16bytes 
 * RETURN:        VS_SUCCESS/VS_ERROR
 * NOTES:         none.
 * ------------------------------------------------------------------------ */
int Pinpad_LoadKLK(unsigned char *KLK);

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_CheckKLK
 * DESCRIPTION:   �����Կ������Կ�Ƿ����
 * PARAMETER:
 * RETURN:		VS_SUCCESS/VS_ERROR
 * NOTES:         none.
 * ------------------------------------------------------------------------ */
int Pinpad_CheckKLK(void);

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_LoadMasterKey
 * DESCRIPTION:   ��ָ��λ����װ����Կ
 * PARAMETER:
 *                number  - ����Կ������00��99
 *                Mkey    - ����Կ
 * RETURN:        VS_SUCCESS/VS_ERROR
 * NOTES:         none.
 * ------------------------------------------------------------------------ */
int Pinpad_LoadMasterKey(unsigned short number, unsigned char *MKey);

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_SelectKey
 * DESCRIPTION:   ѡ������Կ
 * PARAMETER:
 *                number  - ����Կ������00��99
 * RETURN:        VS_SUCCESS/VS_ERROR
 * NOTES:         none.
 * ------------------------------------------------------------------------ */
int Pinpad_SelectKey(unsigned short number);

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_CheckMKey
 * DESCRIPTION:   ���ָ��λ���Ƿ��������Կ
 * PARAMETER:
 *                number  - ����Կ������00��99
 * RETURN:        VS_SUCCESS/VS_ERROR
 * NOTES:         none.
 * ------------------------------------------------------------------------ */
int Pinpad_CheckMKey(unsigned short number);

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_GetPin.
 * DESCRIPTION:   displays the amount and welcomes to enter the PIN
                  displays "processing" until the CancelSess Reg sent
                  Pin length: 1 to 12
 * RETURN:        PIN_SUCCESS: ��������ɹ�
                  PIN_ESC���û���ȡ����
                  PIN_NONE:�û���ȷ�ϼ�����������������
                  PIN_ERROR������ʧ��
 * NOTES:         .
 * ------------------------------------------------------------------------ */
int Pinpad_GetPin(unsigned char *PIN, char *am, unsigned char *PAN, unsigned char *PINKey, 
				  unsigned char MinPINLen, unsigned char MaxPINLen, unsigned short timeout, unsigned short KeyIndex);

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
                  unsigned char MinPINLen, unsigned char MaxPINLen, unsigned short timeout, unsigned short KeyIndex);

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
					   unsigned char MinPINLen, unsigned char MaxPINLen, unsigned short timeout, unsigned short KeyIndex);

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_GetPlainPIN_WithMsg.
 * DESCRIPTION:   Get plain PIN from pinpad, return length and PIN
 * RETURN:        PIN_SUCCESS     ��������ɹ�
				 PIN_ESC    �û���ȡ����
				 PIN_NONE   �û���ȷ�ϼ�����������������
				 PIN_ERROR  ����ʧ��
 * NOTES:
 * FORMAT:
 * ------------------------------------------------------------------------ */
int Pinpad_GetPlainPIN_WithMsg(unsigned char *inPIN, unsigned short *len, char *prompt1, char *prompt2, char *prompt3, char *am,
                       unsigned char MinPINLen, unsigned char MaxPINLen, unsigned short timeout, unsigned short KeyIndex);

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
												 unsigned char *MacKey);

/* --------------------------------------------------------------------------
 * FUNCTION NAME: PINpad_DesByMKey.
 * DESCRIPTION:   ������Կ����/��������
 * RETURN:        VS_SUCCESS/VS_ERROR
 * NOTES:         Type: 0 - ����
                        1 - ����
 * FORMAT:        
 * ------------------------------------------------------------------------ */
int Pinpad_DesByMKey(unsigned char *In, unsigned char *Out, unsigned char Type, unsigned short KeyIndex);

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_CheckMode.
 * DESCRIPTION:   �������PP1000��ǰģʽ
 * RETURN:
 MODE_PP1000SE/MODE_PRC/MODE_222
 * NOTES:         
 * FORMAT:        
 * ------------------------------------------------------------------------ */
int Pinpad_CheckMode(char * device_name);

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_Switch2Reader.
 * DESCRIPTION:   PP1000se switch to CTLS reader
 * RETURN:
		VS_SUCCESS/VS_ERR
 * NOTES:
 * FORMAT:
 * ------------------------------------------------------------------------ */
int Pinpad_Switch2Reader(void);

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_SetNewExPPType.
 * DESCRIPTION:   ����������̵����͡�ģʽ������Կ��ʽ
 * PARAMETER:
	pp_type   - EXTERNAL_Vx820_PINPAD
	pp_mode  �� PP222_MODE
			 �� PP222_MODE_TDES
	key_format - CLEAR_FORMAT
	           - ENCRYPTED_FORMAT
	device_name - DEV_COM1, DEV_USBSER
 * RETURN:        ��
 * NOTES:         �ڵ�������PINPAD����ǰ��������øú�����ȷ��PINPAD����(Ĭ��ΪINTERNAL_PINPAD)��
 *                ģʽ(Ĭ��ΪPP222_DES_MODE)������Կ��ʽ(Ĭ��ΪCLEAR_FORMAT)��
 * ------------------------------------------------------------------------ */
int Pinpad_SetNewExPPType(unsigned short pp_type, unsigned short pp_mode, unsigned short key_format, char * device_name);

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_SetListenPort.
 * DESCRIPTION:   sets listen port's device name
 * PARAMETER:
	device_name - DEV_COM1, DEV_USBD
 * RETURN:        none.
 * NOTES:
 * ------------------------------------------------------------------------ */
void Pinpad_SetListenPort(char * device_name);

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_Ping.
 * DESCRIPTION:   Ping PINpad device
 * RETURN:
		VS_SUCCESS/VS_ERR
 * NOTES:
 * FORMAT:
 * ------------------------------------------------------------------------ */
int Pinpad_Ping(void);

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_LoadMACKey_ICBC.
 * DESCRIPTION:   Load MAC Key to VSS. for ICBC
 * RETURN:
		VS_SUCCESS/VS_ERR
 * NOTES: ���ô˽ӿ�ʱ��Ҫ��PP1000se����PINpadģʽ��222ģʽ
 * FORMAT:
 * ------------------------------------------------------------------------ */
int Pinpad_LoadMACKey_ICBC(unsigned short KeyIndex, unsigned char *MacKey);

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
int Pinpad_GetMAC_ICBC(unsigned char *MAC, unsigned char *Data, int DataLen);

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_GetExDeviceType.
 * DESCRIPTION:   Get Extern PINpad device type
 * RETURN:
		VS_ERR/EXTERNAL_PP1000se/EXTERNAL_PP1000seC/EXTERNAL_PP1000seV3
 * NOTES:
 * FORMAT:
 * ------------------------------------------------------------------------ */
int Pinpad_GetExDeviceType(void);

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
int Pinpad_InputText(int msg_disp_line, char *msgPrompt, int str_disp_line, char *str, unsigned short min, unsigned short max, int disp_mode, unsigned short timeout);

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_GetErrorSatus.
 * DESCRIPTION:   ��ȡ����״̬��
 * RETURN:
		PINPAD_NO_ERROR/ERR_COMM_ERROR/ERR_MKEY_NOT_EXIST
 * NOTES:
 * FORMAT:
 * ------------------------------------------------------------------------ */
int Pinpad_GetErrorSatus(void);

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_GetDeviceInfo.
 * DESCRIPTION:   ��ȡ����PP1000se�豸��Ϣ(model number, S/N, OS version��)
 * RETURN:
		VS_SUCCESS/VS_ERR
 * NOTES:
 * FORMAT:
 * ------------------------------------------------------------------------ */
int Pinpad_GetDeviceInfo(PP1000_DEVICE_INFO *pInfo);

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

int Pinpad_SM4_GetSupportFlag(int* flag);

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
int Pinpad_SM4_LoadMKey(unsigned short index, unsigned char *MKey);

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
                 unsigned char MinPINLen, unsigned char MaxPINLen, unsigned short timeout, unsigned short mkey_idx);

/* ------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_SM4_GetMAC
 * DESCRIPTION:   excute MAC Algorithm base on SM4.
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
               unsigned short mkey_idx, unsigned char *MacKey,unsigned char mode);
/* ------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_SM4_Crypto
 * DESCRIPTION:   execute SM4 Encrypt/Decrypt Algorithm(SM4-ECBģʽ).
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
            unsigned char *indata,unsigned char *wkey,unsigned short mkey_index,unsigned char mode);

/* --------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_SM4_LoadKLK
 * DESCRIPTION:   ������Կ������Կ
 * PARAMETER:
 *     Input: KLK - 16bytes
 * RETURN:		VS_SUCCESS/VS_ERROR
 * NOTES:         none.
 * ------------------------------------------------------------------------ */
int Pinpad_SM4_LoadKLK(unsigned char *KLK);
                                                  
/*end 20151117*/

/* ------------------------------------------------------------------------
 * FUNCTION NAME: Pinpad_GetExDeviceSN
 * DESCRIPTION:   ��ȡ����������̵�SN(Ŀǰֻ֧��V3)
 * PARAMETER:     SN          - (out) �������SN
 *                       SN_len     - (out) �������SN�ĳ���
 * RETURN:        VS_SUCCESS/VS_ERR
 * NOTE:
 * -----------------------------------------------------------------------*/
int Pinpad_GetExDeviceSN(unsigned char *SN, int *SN_len);


#define SN_BUF_LEN 21

/* --------------------------------------------------------------------------
 * FUNCTION NAME: LoadSNKeyClear
 * DESCRIPTION:   �����������к���Կ
 * PARAMETER:
 *     Input: SNKey - 16bytes
 * RETURN:		VS_SUCCESS/VS_ERROR
 * NOTES:         none.
 * ------------------------------------------------------------------------ */
int LoadSNKeyClear(unsigned char *SNKey);
//LoadSNKey�������û��ʵ�֣���Ҫ����
int LoadSNKey(unsigned char *SNKey);
/* --------------------------------------------------------------------------
 * FUNCTION NAME: CheckSNKey
 * DESCRIPTION:   ������к���Կ�Ƿ����
 * PARAMETER:	none.
 * RETURN:		VS_SUCCESS/VS_ERROR
 * NOTES:         none.
 * ------------------------------------------------------------------------ */
int CheckSNKey(void);
/* --------------------------------------------------------------------------
 * FUNCTION NAME: SNGetMAC
 * DESCRIPTION:   ����MAC
 * PARAMETER:
 *     Input: Rand - �������
 *     Input: Rand_len - ������ӵĳ���
 *     Output: MAC - MACֵ
 * RETURN:		VS_SUCCESS/VS_ERROR
 * NOTES:         none.
 * ------------------------------------------------------------------------ */
int SNGetMAC(unsigned char *MAC, unsigned char *Rand, unsigned short Rand_len);
//SNCrypto�������û��ʵ�֣���Ҫ����
int SNCrypto(unsigned short* encrypted_len, unsigned char *encrypted, unsigned short Rand_len,
				 unsigned char *Rand, unsigned char mode);
/* --------------------------------------------------------------------------
 * FUNCTION NAME: GETSN
 * DESCRIPTION:   ���Ӳ�����кţ���'0'��β����ͷ������'V'
 * PARAMETER:
 *     Output: SNKey - 21bytes (SN_BUF_LEN)
 * RETURN:		VS_SUCCESS/VS_ERROR
 * NOTES:         none.
 * ------------------------------------------------------------------------ */
int GETSN(unsigned char *outdata);

#endif  /*__PINPAD_H*/
