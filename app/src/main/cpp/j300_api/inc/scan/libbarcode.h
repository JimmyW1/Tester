/*!
 * @file    libbarcode.h
 * @brief   This file contains API of barcode scanner
 * @author  Tony Zhou
 * @date    20161029 
 * @version 1.0.1
 * @par     Copyright (c):
 *      Verifone
 * @par     description:
 * 条码枪API 使用说明\n
 * 只需barcode_init 初始化一次，其余API都是在init之后才有效的。\n
 * 内置条码枪是自动识别的，外置条码枪需要指定设备。\n
 * 如果是外置条码枪或VX680 带按键不支持指令的条码枪，\n
 * 在扫码之前一定要调用barcode_clr_fifo。内置带指令的不需要。\n
 * 然后扫码就用barcode_scan 扫码就可以了。\n
 * barcode_get_model 获取条码枪类型，多用于debug时定位问题。\n
 * barcode_transfer透明传输，只发送或接收数据，不做数据的处理。\n
 *  以下接口不常用，且只在支持带指令功能的内置条码枪\n
 *  barcode_factory_reset \n
 */
#ifndef LIBBARCODE_H
#define LIBBARCODE_H


/*!
 * @brief 条码枪API 返回值
 */
typedef enum
{
	/*! 成功 */
	BAR_SUCCESS = 0,
	
	/*! 失败 */
	BAR_FAILED = 1,
	
	/*! POS 端口被占用或打开失败*/
	BAR_DEV_OPENED = 2,

	/*! 没有找到正确的条码枪 */
	BAR_NO_DEV = 3,
	
	/*! 条码枪不支持该操作 */
	BAR_NOT_SUPPORT = 4,

	/*! 参数错误或内存分配失败 */
	BAR_PARA_ERROR = 5,
	
	/*! 唤醒设备失败 ，只发生在内置 */
	BAR_WAKEUP_FAILED = 6,

	/*! 设置超时失败，只发生在内置 */
	BAR_SET_TIMEOUT_FAILED = 7,

	/*! 与条码枪握手失败，只发生在C680 */
	BAR_HAND_FAILED = 8,
	
	/*! 扫码被取消 */
	BAR_READ_CANCELLED = 9,
	
	/*! 扫码指令发送失败 */
	BAR_READ_CMD_FAILED = 10,
	
	/*! 超时，未读到数据 */
	BAR_READ_TIMEOUT = 11,
	
	/*! 没读完，buffer太小 */
	BAR_READ_OVERSIZE = 12,
	
	/*! 数据接收完成，但结束符不是0D 0A */
	BAR_READ_NO_EOD = 13,

	/*! 使用读码函数时没给回调函数 */
	BAR_READ_NO_CALLBACK = 14,

	/*! 停止扫码失败 */
	BAR_READ_STOP_FAILED = 15,

	/*! 未初始化*/
	BAR_NO_INIT = 16,
	
}enumBarRet;


/*!
 * @brief 获取条码枪型号返回
 */
typedef enum
{
	/*! VX680内置，带按键，不支持指令*/
	BAR_IN_VX680_KEY  = 1,
	
	/*! VX680内置，支持指令，不区分1D 2D*/
	BAR_IN_VX680_CMD  = 2,
	
	/*! C680内置，一维*/
	BAR_IN_C680_1D    = 3,
	
	/*! C680内置，二维*/
	BAR_IN_C680_2D    = 4,

	/*! 内置，型号未知 */
	BAR_IN_UNKNOW     = 5,

	/*! 外置USB */
	BAR_EX_USB        = 6,

	/*! 外置串口COM1 (9V)*/
	BAR_EX_COM1       = 7,

	/*! 外置串口COM20 (5V)*/
	BAR_EX_COM20      = 8,

	/*! 外置串口扩展*/
	BAR_EX_EXTEND_COM  = 9,

	/*! 外置， 端口未知*/
	BAR_EX_UNKONW     = 10,

    /*! 未初始化或初始化失败*/
	BAR_MODEL_NO_INIT = 11,

    /*! J300 1D*/
	BAR_J300_1D     = 12,

    /*! J300 2D*/
	BAR_J300_2D     = 13,

    /*! J300 条码枪没找到*/
	BAR_J300_UNKNOW     = 14,

	/*! C680 2nd 供应商*/
	BAR_IN_C680_2ND = 15,

	/*! J300 2nd 供应商*/
	BAR_J300_2ND = 16,

	/*! 采用J300 plus统一指令协议*/
	BAR_J300_PLUS = 17,
}enumBarModelRet;


/*!
 * @brief 初始化条码枪种类
 */
typedef enum
{
	/*! 内置条码枪*/
	BAR_INIT_INSIDE  = 0,
	
	/*! 外置USB */
	BAR_INIT_EX_USB  = 1,
	
	/*! 外置串口COM1*/
	BAR_INIT_COM = 2,
	
	/*! 外置串口COM20，沃尔玛专用*/
	BAR_INIT_COM_WMT   = 3,

    /*! J300 连接到POS 的COM1*/
    BAR_INIT_J300_COM1  = 30,

    /*! J300 连接到POS 的COM20, COM20是USB转串口虚拟的*/
    BAR_INIT_J300_COM20 = 31,

    /*! J300 连接到POS 的USB */
    BAR_INIT_J300_USB = 32,
}enumBarInitModel;


/*!
 * @brief 透明传输选项
 */
typedef enum
{
	/*! 透明传输打开设备*/
	BAR_TRAN_OPEN  = 0,
	
	/*! 透明传输关闭设备*/
	BAR_TRAN_CLOSE  = 1,

    /*! 透明传输写*/
	BAR_TRAN_WRITE  = 2,

    /*! 透明传输读*/
	BAR_TRAN_READ  = 3,
}enumBarTranOpt;


/*!
 * @brief 检查结束符功能开关
 */
typedef enum
{
	/*! 检查结束符功能关闭*/
	BAR_CHK_DISABLE  = 0,
	
	/*! 检查结束符功能打开*/
	BAR_CHK_ENABLE  = 1,
}enumChkFlag;


/*! 回调函数类型声明*/
typedef int (*LBarCode_CHK_Cancel)(void); 


/*!
 * @brief 非阻塞扫描选项
 */
typedef enum
{
    BAR_START_SCAN = 0, //!< 开启扫码但不读数据，立即返回
    BAR_GET_DATA = 1, //!< 读数据，无论是否有数据都立即返回。
    BAR_STOP_SCAN = 2, //!< 关闭扫码 
}enumNonblockOpt;


/*!
 * @name 用于动态加载的barcode.lib的函数定义
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
 * @name 用于动态加载的宏定义
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
 * @brief 初始化条码枪
 * @param[in]   enumBarInitModel 条码枪类型
 * @param[in]   scan_timeout_sec 单位秒。\n
 *                         扫码超时时间。扫码开启后，超过timeout时间仍未成功扫码，\n
 *                         则停止扫码。\n
 *                         有效范围是5 ~ 255 s，如果<5 按5处理，>255按255处理
 * @param[in]   wakeup_time_sec 单位秒\n
 *                         该参数只对带指令功能的条码枪起作用。\n
 *                         超过wakeup_time时间未对条码枪操作，其将进入睡眠。\n
 *                         有效范围同scan_timeout_sec
 * @param[in]   chk_flag 类型enumChkFlag\n
 *                         BAR_CHK_DISABLE: 扫码函数不检查数据结束符。保留结束符，将\n
 *                                                    条码枪的原始数据上报给应用。我司定制的USB条码枪\n
 *                                                   结束符是0D, 其它定制条码枪结束符是0D 0A \n
 *                         BAR_CHK_ENABLE: 扫码函数检查数据结束符。并滤掉结束符上报给应用。 \n    
 * @return enumBarRet
 * @see enumBarRet
 * @see enumBarInitModel
 * @see enumChkFlag
 */
enumBarRet barcode_init(enumBarInitModel dev, int scan_timeout_sec, int wakeup_time_sec, enumChkFlag chk_flag);


/*! 
 * @brief 清除条码枪缓存的数据
 * @return enumBarRet
 * @see enumBarRet
 * @note 在barcode_scan之前调用
 */
enumBarRet barcode_clr_fifo(void);

 
/*! 
 * @brief 扫码
 * @param[out]   p_buf 内存指针\n
 *                         存放扫码返回的数据。\n
 *                         建议空间2k
 * @param[in]   buf_size 整型数，表示应用开辟buffer的大小
 * @param[out]   get_len 整型指针，返回扫码字节数\n
 *                         该参数只对带指令功能的条码枪起作用。\n
 *                         超过wakeup_time时间未对条码枪操作，其将进入睡眠。\n
 *                         有效范围同scan_timeout_sec
 * @param[in]   p_callback 回调函数指针，\n
 *                        其返回值用来判断是否取消扫码。\n
 *				 回调函数返回值：\n
 *				 0：未取消\n
 *				 非0： 取消扫码
 * @return enumBarRet
 * @see enumBarRet
 * @see LBarCode_CHK_Cancel
 * @note 注意buf_size 一定大于0 \n
 *          注意len 不能大于应用开辟buffer的大小\n
 *           如果是外置条码枪或VX680 带按键不支持指令的条码枪，\n
 *           在扫码之前一定要调用barcode_clr_fifo。之所以有这用要求\n
 *           就是防止用户提前扫码带来的错误。带指令的不需要\n
 *           clear fifo 是因为库在发送扫码指令前可以clear, 但不支持指令的\n
 *           库无法控制什么时间扫码。
 */
enumBarRet barcode_scan(char * p_buf, int buf_size, int * get_len, LBarCode_CHK_Cancel p_callback);


/*! 
 * @brief 获取条码枪型号
 * @return enumBarModelRet
 * @see enumBarModelRet
 * @note 要在barcode_init， 才有效，以后增加条码枪，enumBarModelRet需修改
 */
enumBarModelRet barcode_get_model(void);


/*! 
 * @brief 恢复出厂设置
 * @return enumBarRet
 * @see enumBarRet
 */
enumBarRet barcode_factory_reset(void);

 
/*! 
 * @brief 透明传输
 * @param[out]   p_buf 内存空间的指针\n
 *                         存放发送或接收到的数据。
 * @param[in]   optn 透明传输选项
 * @param[in]   len 要发送或接收字节个数
 * @param[out]   get_len  返回实际发送或接收的字节个数 
 * @return enumBarRet
 * @see enumBarRet
 * @see enumBarTranOpt
 * @note 注意len 不能大于应用开辟buffer的大小\n
 *           应用打开设备后一定要关闭设备
 */
enumBarRet barcode_transfer(char * p_buf, enumBarTranOpt optn, int len, int * get_len);


/*! 
 * @brief 非阻塞扫码
 * @param[out]   p_buf 内存指针，只有当第四个参数是BAR_GET_DATA 时才有意义\n
 *                         存放扫码返回的数据。\n
 *                         建议空间2k
 * @param[in]   buf_size 整型数，只有当第四个参数是BAR_GET_DATA 时才有意义\n
 *                   表示应用开辟buffer的大小
 * @param[out]   get_len 整型指针，返回扫码字节数\n
 *                         只有当第四个参数是BAR_GET_DATA 时才有意义\n
 *                         该参数只对带指令功能的条码枪起作用。\n
 *                         超过wakeup_time时间未对条码枪操作，其将进入睡眠。\n
 *                         有效范围同scan_timeout_sec
 * @param[in]   optn 操作选项
 * @return enumBarRet
 * @see enumNonblockOpt
 * @see enumBarRet
 * @see barcode_scan
 * @note 非阻塞扫码API使用方法: \n
 *           1, 通过barcode_scan_nonblock(0, 0, 0, BAR_START_SCAN)使条码枪处于扫码状态，\n
 *           此时应用可以做其它事情，条码枪随时可以扫码(扫码开启时间由\n
 *           init时设置的timeout决定) \n
 *           2, 通过barcode_scan_nonblock(p_buf, buf_size, get_len, BAR_GET_DATA);来读数据，有数据\n
 *           gen_len返回数据个数。没有数据gen_len为0。
 *           3, 通过barcode_scan_nonblock(0, 0, 0, BAR_START_STOP)可以随时关闭扫码。\n     
 *           注意: 用此函数开启扫码后，在调用除本函数以外的条码枪\n
 *                        API之前，需要关闭扫码(BAR_START_STOP)。
 */
enumBarRet barcode_scan_nonblock(char * p_buf, int buf_size, int * get_len, enumNonblockOpt optn);

#endif
