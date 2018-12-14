#ifndef _USER_TYPE_H_
#define _USER_TYPE_H_

#define TRUE 1
#define FALSE 0
typedef unsigned long  ulint;
typedef long           lint;
typedef unsigned short usint;
typedef short          sint;
typedef short              boolean; 
typedef unsigned char  byte;
typedef unsigned short Ushort;

#define  PIN_NONE         2   /*get none pin key from pinpad*/
#define  PIN_SUCCESS      1   /*get pin key from pinpad*/
#define  PIN_ESC          0   /*cashier abort by hit bESC or bMENU*/
#define  PIN_ERROR       -1   /*length of pin is not 8*/
#define  PIN_ABORT       -2   /*press bESC on POS*/
#define	 PIN_MKEY_DIFF	 -3	  /*mixed type pinpad, master key is different in vss and pp*/
#define  PIN_TIMEOUT     -4   /*input pin timeout*/
#define  PIN_NO_TOKEN    -5   /*no token available in the bucket*/

/*PINpad类型*/
#define INTERNAL_PINPAD				0			/*内置密码键盘*/
#define EXTERNAL_PINPAD				1           /*外置密码键盘*/
#define MIXED_PINPAD				2           /*内外混合键盘*/

/*外接设备类型*/
#define EXTERNAL_PP1000se			1			/*外置PP1000se密码键盘*/
#define EXTERNAL_PP1000seC			2			/*外置国产PP1000seC密码键盘*/
#define EXTERNAL_Vx820_PINPAD		3           /*外置Vx820密码键盘*/
#define EXTERNAL_PP1000seV3			4			/*外置PP1000se V3密码键盘*/
#define EXTERNAL_J300				5			/*外置J300密码键盘*/

/*PINpad模式*/
#define PP1000SE_MODE        0       /*单DES，无KLK*/
#define PRC_MODE             1       /*3DES，有KLK*/
#define PRC_MODE_CLEAR		 2       /*3DES，无KLK*/
#define PP222_DES_MODE       3       /*单DES，有KLK*/
#define PP222_3DES_MODE      4       /*3DES，有KLK*/
#define SM4_MODE			 5		 /*SM4*/

/*主密钥格式*/
#define CLEAR_FORMAT		 0        /*明文*/
#define ENCRYPTED_FORMAT     1        /*密文*/


/*返回值定义*/
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

/*PP1000 模式*/
#define MODE_PP1000SE       '2'
#define MODE_PRC            '4'
#define MODE_222            '7'

/*Pinpad_InputText()返回值,同ATOOL返回值*/
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

#define  DEFAULT_TRANSPORT_KEY	"\x24\x94\xE8\x89\xB7\x8C\x6F\x89\x55\x93\x72\x8E\x49\x90\xEE\x21"
#define  DEFAULT_SECRET_KEY		"\xF0\x88\xA1\x4D\x26\x65\xEA\x00\x9F\x1D\x0C\x33\x67\x12\xA7\x86"
#define  DEFAULT_PIN_KEY		"\x12\x34\x56\x78\x9A\xBC\xDE\xF0\x0F\xED\xCB\xA9\x87\x65\x43\x21"

typedef enum
{
  DES_FIXED_KEY = '1',
  TDES_FIXED_KEY,
  DES_MASTER_KEY,
  TDES_MASTER_KEY,
  DES_MAC_KEY,
  TDES_MAC_KEY,
  TRANSPORT_KEY = '8',
  DES_DATA_KEY = 'B',
  TDES_DATA_KEY,
} Key_Type;

typedef enum
{
  SINGLE_DES = 0,
  TRIPLE_DES,
  SM4_ALG,
} Alg_Type;


#endif /* _USER_TYPE_H_ */
