/*!
 * @file    libbarcode.h
 * @brief   This file contains API of barcode scanner
 * @author  Tony Zhou
 * @date    20161029 
 * @version 1.0.1
 * @par     Copyright (c):
 *      Verifone
 * @par     description:
 * ����ǹAPI ʹ��˵��\n
 * ֻ��barcode_init ��ʼ��һ�Σ�����API������init֮�����Ч�ġ�\n
 * ��������ǹ���Զ�ʶ��ģ���������ǹ��Ҫָ���豸��\n
 * �������������ǹ��VX680 ��������֧��ָ�������ǹ��\n
 * ��ɨ��֮ǰһ��Ҫ����barcode_clr_fifo�����ô�ָ��Ĳ���Ҫ��\n
 * Ȼ��ɨ�����barcode_scan ɨ��Ϳ����ˡ�\n
 * barcode_get_model ��ȡ����ǹ���ͣ�������debugʱ��λ���⡣\n
 * barcode_transfer͸�����䣬ֻ���ͻ�������ݣ��������ݵĴ���\n
 *  ���½ӿڲ����ã���ֻ��֧�ִ�ָ��ܵ���������ǹ\n
 *  barcode_factory_reset \n
 */
#ifndef LIBBARCODE_H
#define LIBBARCODE_H


/*!
 * @brief ����ǹAPI ����ֵ
 */
typedef enum
{
	/*! �ɹ� */
	BAR_SUCCESS = 0,
	
	/*! ʧ�� */
	BAR_FAILED = 1,
	
	/*! POS �˿ڱ�ռ�û��ʧ��*/
	BAR_DEV_OPENED = 2,

	/*! û���ҵ���ȷ������ǹ */
	BAR_NO_DEV = 3,
	
	/*! ����ǹ��֧�ָò��� */
	BAR_NOT_SUPPORT = 4,

	/*! ����������ڴ����ʧ�� */
	BAR_PARA_ERROR = 5,
	
	/*! �����豸ʧ�� ��ֻ���������� */
	BAR_WAKEUP_FAILED = 6,

	/*! ���ó�ʱʧ�ܣ�ֻ���������� */
	BAR_SET_TIMEOUT_FAILED = 7,

	/*! ������ǹ����ʧ�ܣ�ֻ������C680 */
	BAR_HAND_FAILED = 8,
	
	/*! ɨ�뱻ȡ�� */
	BAR_READ_CANCELLED = 9,
	
	/*! ɨ��ָ���ʧ�� */
	BAR_READ_CMD_FAILED = 10,
	
	/*! ��ʱ��δ�������� */
	BAR_READ_TIMEOUT = 11,
	
	/*! û���꣬buffer̫С */
	BAR_READ_OVERSIZE = 12,
	
	/*! ���ݽ�����ɣ�������������0D 0A */
	BAR_READ_NO_EOD = 13,

	/*! ʹ�ö��뺯��ʱû���ص����� */
	BAR_READ_NO_CALLBACK = 14,

	/*! ֹͣɨ��ʧ�� */
	BAR_READ_STOP_FAILED = 15,

	/*! δ��ʼ��*/
	BAR_NO_INIT = 16,
	
}enumBarRet;


/*!
 * @brief ��ȡ����ǹ�ͺŷ���
 */
typedef enum
{
	/*! VX680���ã�����������֧��ָ��*/
	BAR_IN_VX680_KEY  = 1,
	
	/*! VX680���ã�֧��ָ�������1D 2D*/
	BAR_IN_VX680_CMD  = 2,
	
	/*! C680���ã�һά*/
	BAR_IN_C680_1D    = 3,
	
	/*! C680���ã���ά*/
	BAR_IN_C680_2D    = 4,

	/*! ���ã��ͺ�δ֪ */
	BAR_IN_UNKNOW     = 5,

	/*! ����USB */
	BAR_EX_USB        = 6,

	/*! ���ô���COM1 (9V)*/
	BAR_EX_COM1       = 7,

	/*! ���ô���COM20 (5V)*/
	BAR_EX_COM20      = 8,

	/*! ���ô�����չ*/
	BAR_EX_EXTEND_COM  = 9,

	/*! ���ã� �˿�δ֪*/
	BAR_EX_UNKONW     = 10,

    /*! δ��ʼ�����ʼ��ʧ��*/
	BAR_MODEL_NO_INIT = 11,

    /*! J300 1D*/
	BAR_J300_1D     = 12,

    /*! J300 2D*/
	BAR_J300_2D     = 13,

    /*! J300 ����ǹû�ҵ�*/
	BAR_J300_UNKNOW     = 14,

	/*! C680 2nd ��Ӧ��*/
	BAR_IN_C680_2ND = 15,

	/*! J300 2nd ��Ӧ��*/
	BAR_J300_2ND = 16,

	/*! ����J300 plusͳһָ��Э��*/
	BAR_J300_PLUS = 17,
}enumBarModelRet;


/*!
 * @brief ��ʼ������ǹ����
 */
typedef enum
{
	/*! ��������ǹ*/
	BAR_INIT_INSIDE  = 0,
	
	/*! ����USB */
	BAR_INIT_EX_USB  = 1,
	
	/*! ���ô���COM1*/
	BAR_INIT_COM = 2,
	
	/*! ���ô���COM20���ֶ���ר��*/
	BAR_INIT_COM_WMT   = 3,

    /*! J300 ���ӵ�POS ��COM1*/
    BAR_INIT_J300_COM1  = 30,

    /*! J300 ���ӵ�POS ��COM20, COM20��USBת���������*/
    BAR_INIT_J300_COM20 = 31,

    /*! J300 ���ӵ�POS ��USB */
    BAR_INIT_J300_USB = 32,
}enumBarInitModel;


/*!
 * @brief ͸������ѡ��
 */
typedef enum
{
	/*! ͸��������豸*/
	BAR_TRAN_OPEN  = 0,
	
	/*! ͸������ر��豸*/
	BAR_TRAN_CLOSE  = 1,

    /*! ͸������д*/
	BAR_TRAN_WRITE  = 2,

    /*! ͸�������*/
	BAR_TRAN_READ  = 3,
}enumBarTranOpt;


/*!
 * @brief �����������ܿ���
 */
typedef enum
{
	/*! �����������ܹر�*/
	BAR_CHK_DISABLE  = 0,
	
	/*! �����������ܴ�*/
	BAR_CHK_ENABLE  = 1,
}enumChkFlag;


/*! �ص�������������*/
typedef int (*LBarCode_CHK_Cancel)(void); 


/*!
 * @brief ������ɨ��ѡ��
 */
typedef enum
{
    BAR_START_SCAN = 0, //!< ����ɨ�뵫�������ݣ���������
    BAR_GET_DATA = 1, //!< �����ݣ������Ƿ������ݶ��������ء�
    BAR_STOP_SCAN = 2, //!< �ر�ɨ�� 
}enumNonblockOpt;


/*!
 * @name ���ڶ�̬���ص�barcode.lib�ĺ�������
 * @{
 */
typedef int (*LBarCode_Init)(enumBarInitModel, int, int, enumChkFlag); // 1
typedef int (*LBarCode_Clr_Fifo)(void);      // 2
typedef int (*LBarCode_Scan)(char *, int, int *, LBarCode_CHK_Cancel); // 3
typedef int (*LBarCode_Get_Model)(void);     // 4
typedef int (*LBarCode_Factory_Reset)(void); //5
typedef int (*LBarCode_Transfer)(char *, enumBarTranOpt, int, int *); //6
typedef int (*Lbarcode_Scan_Nonblock)(char *, int, int *, enumNonblockOpt); //7
/*! @} */

/*!
 * @name ���ڶ�̬���صĺ궨��
 * @{
 */
#define LBARCODE_IDX_INIT           1
#define LBARCODE_IDX_CLR_FIFO       2
#define LBARCODE_IDX_SCAN           3
#define LBARCODE_IDX_GET_MODEL      4
#define LBARCODE_IDX_FACTORY_RESET  5
#define LBARCODE_IDX_TRANSFER       6
#define LBARCODE_IDX_NONBLOCK        7
/*! @} */


/*! 
 * @brief ��ʼ������ǹ
 * @param[in]   enumBarInitModel ����ǹ����
 * @param[in]   scan_timeout_sec ��λ�롣\n
 *                         ɨ�볬ʱʱ�䡣ɨ�뿪���󣬳���timeoutʱ����δ�ɹ�ɨ�룬\n
 *                         ��ֹͣɨ�롣\n
 *                         ��Ч��Χ��5 ~ 255 s�����<5 ��5����>255��255����
 * @param[in]   wakeup_time_sec ��λ��\n
 *                         �ò���ֻ�Դ�ָ��ܵ�����ǹ�����á�\n
 *                         ����wakeup_timeʱ��δ������ǹ�������佫����˯�ߡ�\n
 *                         ��Ч��Χͬscan_timeout_sec
 * @param[in]   chk_flag ����enumChkFlag\n
 *                         BAR_CHK_DISABLE: ɨ�뺯����������ݽ���������������������\n
 *                                                    ����ǹ��ԭʼ�����ϱ���Ӧ�á���˾���Ƶ�USB����ǹ\n
 *                                                   ��������0D, ������������ǹ��������0D 0A \n
 *                         BAR_CHK_ENABLE: ɨ�뺯��������ݽ����������˵��������ϱ���Ӧ�á� \n    
 * @return enumBarRet
 * @see enumBarRet
 * @see enumBarInitModel
 * @see enumChkFlag
 */
enumBarRet barcode_init(enumBarInitModel dev, int scan_timeout_sec, int wakeup_time_sec, enumChkFlag chk_flag);


/*! 
 * @brief �������ǹ���������
 * @return enumBarRet
 * @see enumBarRet
 * @note ��barcode_scan֮ǰ����
 */
enumBarRet barcode_clr_fifo(void);

 
/*! 
 * @brief ɨ��
 * @param[out]   p_buf �ڴ�ָ��\n
 *                         ���ɨ�뷵�ص����ݡ�\n
 *                         ����ռ�2k
 * @param[in]   buf_size ����������ʾӦ�ÿ���buffer�Ĵ�С
 * @param[out]   get_len ����ָ�룬����ɨ���ֽ���\n
 *                         �ò���ֻ�Դ�ָ��ܵ�����ǹ�����á�\n
 *                         ����wakeup_timeʱ��δ������ǹ�������佫����˯�ߡ�\n
 *                         ��Ч��Χͬscan_timeout_sec
 * @param[in]   p_callback �ص�����ָ�룬\n
 *                        �䷵��ֵ�����ж��Ƿ�ȡ��ɨ�롣\n
 *				 �ص���������ֵ��\n
 *				 0��δȡ��\n
 *				 ��0�� ȡ��ɨ��
 * @return enumBarRet
 * @see enumBarRet
 * @see LBarCode_CHK_Cancel
 * @note ע��buf_size һ������0 \n
 *          ע��len ���ܴ���Ӧ�ÿ���buffer�Ĵ�С\n
 *           �������������ǹ��VX680 ��������֧��ָ�������ǹ��\n
 *           ��ɨ��֮ǰһ��Ҫ����barcode_clr_fifo��֮����������Ҫ��\n
 *           ���Ƿ�ֹ�û���ǰɨ������Ĵ��󡣴�ָ��Ĳ���Ҫ\n
 *           clear fifo ����Ϊ���ڷ���ɨ��ָ��ǰ����clear, ����֧��ָ���\n
 *           ���޷�����ʲôʱ��ɨ�롣
 */
enumBarRet barcode_scan(char * p_buf, int buf_size, int * get_len, LBarCode_CHK_Cancel p_callback);


/*! 
 * @brief ��ȡ����ǹ�ͺ�
 * @return enumBarModelRet
 * @see enumBarModelRet
 * @note Ҫ��barcode_init�� ����Ч���Ժ���������ǹ��enumBarModelRet���޸�
 */
enumBarModelRet barcode_get_model(void);


/*! 
 * @brief �ָ���������
 * @return enumBarRet
 * @see enumBarRet
 */
enumBarRet barcode_factory_reset(void);

 
/*! 
 * @brief ͸������
 * @param[out]   p_buf �ڴ�ռ��ָ��\n
 *                         ��ŷ��ͻ���յ������ݡ�
 * @param[in]   optn ͸������ѡ��
 * @param[in]   len Ҫ���ͻ�����ֽڸ���
 * @param[out]   get_len  ����ʵ�ʷ��ͻ���յ��ֽڸ��� 
 * @return enumBarRet
 * @see enumBarRet
 * @see enumBarTranOpt
 * @note ע��len ���ܴ���Ӧ�ÿ���buffer�Ĵ�С\n
 *           Ӧ�ô��豸��һ��Ҫ�ر��豸
 */
enumBarRet barcode_transfer(char * p_buf, enumBarTranOpt optn, int len, int * get_len);


/*! 
 * @brief ������ɨ��
 * @param[out]   p_buf �ڴ�ָ�룬ֻ�е����ĸ�������BAR_GET_DATA ʱ��������\n
 *                         ���ɨ�뷵�ص����ݡ�\n
 *                         ����ռ�2k
 * @param[in]   buf_size ��������ֻ�е����ĸ�������BAR_GET_DATA ʱ��������\n
 *                   ��ʾӦ�ÿ���buffer�Ĵ�С
 * @param[out]   get_len ����ָ�룬����ɨ���ֽ���\n
 *                         ֻ�е����ĸ�������BAR_GET_DATA ʱ��������\n
 *                         �ò���ֻ�Դ�ָ��ܵ�����ǹ�����á�\n
 *                         ����wakeup_timeʱ��δ������ǹ�������佫����˯�ߡ�\n
 *                         ��Ч��Χͬscan_timeout_sec
 * @param[in]   optn ����ѡ��
 * @return enumBarRet
 * @see enumNonblockOpt
 * @see enumBarRet
 * @see barcode_scan
 * @note ������ɨ��APIʹ�÷���: \n
 *           1, ͨ��barcode_scan_nonblock(0, 0, 0, BAR_START_SCAN)ʹ����ǹ����ɨ��״̬��\n
 *           ��ʱӦ�ÿ������������飬����ǹ��ʱ����ɨ��(ɨ�뿪��ʱ����\n
 *           initʱ���õ�timeout����) \n
 *           2, ͨ��barcode_scan_nonblock(p_buf, buf_size, get_len, BAR_GET_DATA);�������ݣ�������\n
 *           gen_len�������ݸ�����û������gen_lenΪ0��
 *           3, ͨ��barcode_scan_nonblock(0, 0, 0, BAR_START_STOP)������ʱ�ر�ɨ�롣\n     
 *           ע��: �ô˺�������ɨ����ڵ��ó����������������ǹ\n
 *                        API֮ǰ����Ҫ�ر�ɨ��(BAR_START_STOP)��
 */
enumBarRet barcode_scan_nonblock(char * p_buf, int buf_size, int * get_len, enumNonblockOpt optn);

#endif
