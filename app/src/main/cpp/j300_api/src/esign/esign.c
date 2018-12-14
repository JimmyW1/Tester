#include <common/comm_driver.h>
#include <pos_debug.h>
#include "esign/esign_util.h"
#include "esign/esign.h"

static char ineSign_cComPort[ 16 ]= {"/DEV/COM1"};
static int J300_fd = -1;

int eSign_InitComm(unsigned char Rs232Dev) 
{
    if (J300_fd < 0) {
        J300_fd = POS_OPEN(POS_USBSER_1);
        if (J300_fd < 0) {
            POS_DEBUG_WITHTAG("UsbSerDemo", "Open usbser 1 failed.\n");
            return ESIGN_FAIL;
        }
    }

    return ESIGN_OK;
}

int eSin_CloseComm()
{
    if (J300_fd >= 0) {
        POS_CLOSE(J300_fd);
        J300_fd = -1;
    }

    return 0;
}

int ComDev_Read(const char *device_name, char *buf, int data_len)
{
    return POS_Read(J300_fd, buf, data_len, 50);
}

int ComDev_Write(const char *device_name, char *buffer, int count)
{
    return POS_Write(J300_fd, buffer, count);
}


//发送数据
int eSign_SendData(unsigned char Rs232Dev, unsigned char *Data, unsigned int DataLen)
{
    unsigned char *p, *s;
    unsigned char dbuf[SIGN_MAX_BUF_LEN];
    int len;


    memset(dbuf, 0, sizeof(dbuf));

    while (ComDev_Read(ineSign_cComPort, (char*)dbuf, 1) > 0);

    len = DataLen;
    memset(dbuf, 0, sizeof(dbuf));

    p = (byte*)dbuf;
    s = p;
    *s = 0x02;
    s ++ ;
    *s = (DataLen + 1) / 256;     //长度要包括ETX
    *(s + 1) = (DataLen + 1) % 256;
    s += 2;
    memcpy(s, Data, DataLen);
    s += DataLen;
    *s = 0x03;
    s ++ ;
    *s = BankUtil_GenLrc(p + 1, DataLen + 3);
    len = DataLen + 5;

    if (ComDev_Write( ineSign_cComPort, (char *)p, len) != len)
    {
        return ESIGN_FAIL;
    }

    return ESIGN_OK;
}

//接收数据
int eSign_RecvData(unsigned char Rs232Dev, unsigned char *Data, unsigned int *DataLen, unsigned int Timeout)
{
    int rv;
    int data_len;
    int iOffset;
    long tstart;
    unsigned char rbuf[SIGN_MAX_BUF_LEN];

    tstart = getCurrentTimeMS();
    memset( rbuf, 0, sizeof( rbuf ) );
    *DataLen = 0;

    for ( ; ; )
    {
        if ( getCurrentTimeMS() - tstart > Timeout * 1000 )
        {
            return ESIGN_TIMEOUT;
        }

        rv = ComDev_Read( ineSign_cComPort, (char *)rbuf, 1 );
        if ( rv == 1 && rbuf[ 0 ] == 0x02 )
        {
            SVC_WAIT( 50 );

            rv = ComDev_Read( ineSign_cComPort, (char *)rbuf + 1, 2 );
            if ( rv != 2 )
            {
                return ESIGN_TIMEOUT;
            }
            data_len = rbuf[ 1 ] * 256 + rbuf[ 2 ];
            if ( data_len > sizeof( rbuf ) )
                data_len = sizeof( rbuf );

            iOffset = 0;
            while ( getCurrentTimeMS() - tstart < Timeout * 1000 )
            {
                rv = ComDev_Read( ineSign_cComPort, (char *)rbuf + 3 + iOffset, data_len - iOffset );
                if ( rv > 0 )
                    iOffset += rv;

                if ( iOffset >= data_len )
                    break;

                SVC_WAIT( 50 );
            }

            if ( iOffset < data_len )
            {
                return ESIGN_TIMEOUT;
            }

            //心跳包
            if (rbuf[ 3 ] == 0xD1 )
            {
                memset( rbuf, 0, sizeof( rbuf ) );
                tstart = getCurrentTimeMS();
                continue;
            }

            *DataLen = data_len;
            memcpy( Data, rbuf + 3, data_len );

            // clear com buffer
            ComDev_Read( ineSign_cComPort, (char *)rbuf, sizeof( rbuf ) );

            return ESIGN_OK;
        }
    }
}

//握手
int eSign_HandShake(unsigned char Rs232Dev, unsigned int TimeOut, unsigned char *State)
{
    unsigned char    buf[SIGN_MAX_BUF_LEN];
    unsigned int  len;
    int ret;

    if (State == NULL)
    {
        return ESIGN_FAIL;
    }

    ret = eSign_InitComm(Rs232Dev);

    if (ret != ESIGN_OK)
    {
        return ret;
    }

    memset(buf, 0, sizeof(buf));
    buf[0] = SIGN_HANDSHAKE_REQ;
    len = 1;
    ret = eSign_SendData(Rs232Dev, buf, len);

    if (ret != ESIGN_OK)
    {
        return ret;
    }

    memset(buf, 0, sizeof(buf));
    len = sizeof(buf);
    ret = eSign_RecvData(Rs232Dev, buf, &len, TimeOut);

    if (ret != ESIGN_OK)
    {

        return ret;
    }

    if (buf[0] != SIGN_HANDSHAKE_RES)
    {
        return ESIGN_FAIL;
    }

    *State = buf[1];
    return ESIGN_OK;
}

int eSign_SetReSignTimes(unsigned char Rs232Dev,byte uReSignTimes,int timeout)
{
    unsigned char    buf[SIGN_MAX_BUF_LEN];
    unsigned int len;
    int   ret;


    ret = eSign_InitComm(Rs232Dev);

    if (ret != ESIGN_OK)
    {
        return ret;
    }

    memset(buf, 0, sizeof(buf));
    buf[0] = SIGN_RESIGN_TIME;
    buf[1] = uReSignTimes;
    len = 2;
    ret = eSign_SendData(Rs232Dev, buf, len);

    if (ret != ESIGN_OK)
    {
        return ret;
    }

    memset(buf, 0, sizeof(buf));
    len = sizeof(buf);
    ret = eSign_RecvData(Rs232Dev, buf, &len, timeout);

    if (ret != ESIGN_OK)
    {
        return ret;
    }

    if (buf[0] != SIGN_RESIGN_TIME)  //签字失败
    {
        return ESIGN_FAIL;
    }

    if (buf[1] != 0)
    {
        return ESIGN_FAIL;
    }


    return ESIGN_OK;
}

int eSign_SetByPassFlag(unsigned char Rs232Dev,boolean bByPassFlag,int timeout)
{
    unsigned char    buf[SIGN_MAX_BUF_LEN];
    unsigned int len;
    int   ret;


    ret = eSign_InitComm(Rs232Dev);

    if (ret != ESIGN_OK)
    {
        return ret;
    }

    memset(buf, 0, sizeof(buf));
    buf[0] = SIGN_BYPASS;
    buf[1] = bByPassFlag;
    len = 2;
    ret = eSign_SendData(Rs232Dev, buf, len);

    if (ret != ESIGN_OK)
    {
        return ret;
    }

    memset(buf, 0, sizeof(buf));
    len = sizeof(buf);
    ret = eSign_RecvData(Rs232Dev, buf, &len, timeout);

    if (ret != ESIGN_OK)
    {
        return ret;
    }

    if (buf[0] != SIGN_BYPASS)  //签字失败
    {
        return ESIGN_FAIL;
    }

    if (buf[1] != 0)
    {
        return ESIGN_FAIL;
    }


    return ESIGN_OK;
}

int eSign_SetPicZone(unsigned char Rs232Dev,int PicHeight,int PicWidth,int timeout)
{
    unsigned char    buf[SIGN_MAX_BUF_LEN];
    unsigned int len;
    int   ret;
    int nZoneHeight = 160;
    int nZoneWidth = 480;

    ret = eSign_InitComm(Rs232Dev);

    if (ret != ESIGN_OK)
    {
        return ret;
    }
    // 根据配置文件，重新设置区域
//    nZoneHeight = LIBConfigList[ATOOL_CFG_ExSign_Show_H].paramValue;  // AABLVA-41, 适应J300的签名区域
//    nZoneWidth  = LIBConfigList[ATOOL_CFG_ExSign_Show_W].paramValue;   // AABLVA-41, 适应J300的签名区域
    nZoneHeight = 120;
    nZoneWidth = 240;

    memset(buf, 0, sizeof(buf));
    buf[0] = SIGN_SETPIC_ZONE;
    buf[1] = nZoneWidth/256;
    buf[2] = nZoneWidth%256;
    buf[3] = nZoneHeight/256;
    buf[4] = nZoneHeight%256;
    buf[5] = PicWidth/256;
    buf[6] = PicWidth%256;
    buf[7] = PicHeight/256;
    buf[8] = PicHeight%256;
    len = 9;
    ret = eSign_SendData(Rs232Dev, buf, len);

    if (ret != ESIGN_OK)
    {
        return ret;
    }

    memset(buf, 0, sizeof(buf));
    len = sizeof(buf);
    ret = eSign_RecvData(Rs232Dev, buf, &len, timeout);
    if (ret != ESIGN_OK)
    {
        return ret;
    }
    if (buf[0] != SIGN_SETPIC_ZONE)  //签字失败
    {
        return ESIGN_FAIL;
    }

    if (buf[1] != 0)
    {
        // return buf[1];
        return ESIGN_ZONEFAIL;  // AABLVA-41, 重置返回值。
    }
    return ESIGN_OK;
}

/*签名开始
输入参数：
*       Rs232Dev    串口号
*       ConditionCode   交易特征码
*       TimeOut     超时
*
* 输出参数：
*       SeqNum   电子签字编号
*       Data     电子签字数据
*       DataLen  电子签字数据长度
*/
int eSign_SignatureBegin(unsigned char Rs232Dev, unsigned char *ConditionCode, unsigned int TimeOut, unsigned int *SeqNum, unsigned char *Data, unsigned int *DataLen)
{
    unsigned char buf[SIGN_MAX_BUF_LEN];
    unsigned int len;
    int   ret;

    if ((SeqNum == NULL) || (DataLen == NULL)
            || (Data == NULL))
    {
        return ESIGN_FAIL;
    }

    ret = eSign_InitComm(Rs232Dev);

    if (ret != ESIGN_OK)
    {
        return ret;
    }

    memset(buf, 0, sizeof(buf));
    buf[0] = SIGN_SIGNATURE_BEGIN_REQ;
    memcpy(buf + 1, ConditionCode, 8);
    len = 9;
    ret = eSign_SendData(Rs232Dev, buf, len);

    if (ret != ESIGN_OK)
    {
        return ret;
    }

    memset(buf, 0, sizeof(buf));
    len = sizeof(buf);
    ret = eSign_RecvData(Rs232Dev, buf, &len, TimeOut);

    if (ret != ESIGN_OK)
    {
        return ret;
    }

    if (buf[0] != SIGN_SIGNATURE_BEGIN_RES_OK)  //签字失败
    {
        return ESIGN_FAIL;
    }

    *SeqNum = (buf[1] << 16) + (buf[2] << 8) + (buf[3]);
    *DataLen = len - 4;
    memcpy(Data, buf + 4, *DataLen);

    return ESIGN_OK;
}

/*
* 电子签字结束
* 输入参数：
*       Rs232Dev    串口号
*       TimeOut     超时
*
* 输出参数：无
*/
int eSign_SignatureEnd(unsigned char Rs232Dev, unsigned int TimeOut)
{
    unsigned char    buf[SIGN_MAX_BUF_LEN];
    unsigned int len;
    int ret;

    ret = eSign_InitComm(Rs232Dev);

    if (ret != ESIGN_OK)
    {
        return ret;
    }

    memset(buf, 0, sizeof(buf));
    buf[0] = SIGN_SIGNATURE_END_REQ;
    len = 1;
    ret = eSign_SendData(Rs232Dev, buf, len);

    if (ret != ESIGN_OK)
    {
        return ret;
    }

    memset(buf, 0, sizeof(buf));
    len = sizeof(buf);
    ret = eSign_RecvData(Rs232Dev, buf, &len, TimeOut);

    if (ret != ESIGN_OK)
    {
        return ret;
    }

    if (buf[0] != SIGN_SIGNATURE_END_RES)
    {
        return ESIGN_FAIL;
    }

    return ESIGN_OK;
}

int eSign_ExternalSign(char * code, int len, int timeout, char *f_bitmap,int times,boolean bypass)
{
    int ret;
    unsigned int SeqNum;
    byte State;
    unsigned int  bmp_datalen, pbm_datalen;
    byte bmp_data[SIGN_MAX_BUF_LEN] = {0};

    ret = eSign_HandShake(1, 3, &State);
    if (ret != ESIGN_OK)
    {
        //MmiUtil_Warning3("签名板通迅失败", 5);
        return ret;
    }

    ret = eSign_SetReSignTimes(1,times,2);
    if (ret != ESIGN_OK)
    {
        return ret;
    }

//    if( LIBConfigList[ATOOL_CFG_ExSign_NotChk_ByPass].paramValue )
    if(1) 
    {
        // 不检测返回值，此时设置超时是1秒
        ret = eSign_SetByPassFlag(1,bypass,1);
//        LOG_PRINTF_LIB(LIB_TRACE_LV_3, "Skip check eSign_SetByPassFlag ret[%d] with timeout[%d]", ret, 1 );
    }
    else
    {
        // 原有逻辑
        ret = eSign_SetByPassFlag(1,bypass,2);
        if (ret != ESIGN_OK)
        {
            return ret;
        }
    }
//    ret = eSign_SetPicZone(1,LIBConfigList[ATOOL_CFG_ExSign_File_H].paramValue, LIBConfigList[ATOOL_CFG_ExSign_File_W].paramValue,2);    // AABLVA-41, 适应J300的签名区域
    ret = eSign_SetPicZone(1, 120, 240, 2);    // AABLVA-41, 适应J300的签名区域
    if (ret != ESIGN_OK)
    {
        return ret;
    }


    bmp_datalen = sizeof(bmp_data);
    memset(bmp_data, 0, sizeof(bmp_data));

    POS_DEBUG_WITHTAG("UsbSerDemo", "Start signature=================================.\n");
    ret = eSign_SignatureBegin(1, (unsigned char *)code, timeout, &SeqNum, bmp_data, &bmp_datalen);
    POS_DEBUG_WITHTAG("UsbSerDemo", "End signature=================================.\n");
    if (ret != ESIGN_OK)
    {
        //根据规范,异常时应发送签字结束请求
        eSign_SignatureEnd(1, 2);
        return ret;
    }

    POS_DEBUG_WITHTAG("UsbSerDemo", "Fopen=================================.\n");
    FILE *fp = fopen("/sdcard/esign.bmp", "w");
    POS_DEBUG_WITHTAG("UsbSerDemo", "Fwrite=================================.\n");
    fwrite(bmp_data, bmp_datalen, 1, fp);
    POS_DEBUG_WITHTAG("UsbSerDemo", "Fclose=================================.\n");
    fclose(fp);

    ret = eSign_SignatureEnd(1, 2);
    if (ret != ESIGN_OK)
    {
        if ( State == 0 )
        {
            return ret;
        }
    }
    return ESIGN_OK;

    /*
    if( LIBConfigList[ATOOL_CFG_ExSign_SaveBMP].paramString[0] != 0 )
    {
        // 设置了临时保存bmp图片的文件名
        eSign_SavePbm( bmp_data, bmp_datalen, LIBConfigList[ATOOL_CFG_ExSign_SaveBMP].paramString );
    }

    State = 0;
    if ( memcmp( bmp_data, "VX820-ESIGN", 11 ) == 0 )
    {
        State = 1;
        pbm_datalen = bmp_datalen - 11;
        memcpy( pbm_data, bmp_data + 11, bmp_datalen );
    }
    else
    {
        //convert bmp to pbm.
        memset(pbm_data,0,sizeof(pbm_data));
        pbm_datalen = sizeof(pbm_data);
        ret = eSign_ConvertBMPtoPBM((char *)bmp_data, bmp_datalen,(char *)pbm_data, &pbm_datalen);
        if (ret != ESIGN_OK)
        {
            eSign_SignatureEnd(1, 2);
            return ret;
        }
    }

    ret = eSign_SavePbm( pbm_data, pbm_datalen, f_bitmap);
    if (ret != ESIGN_OK)
    {
        eSign_SignatureEnd(1, 2);
        return ret;
    }

    ret = eSign_SignatureEnd(1, 2);
    if (ret != ESIGN_OK)
    {
        if ( State == 0 )
        {
            return ret;
        }
    }
    return ESIGN_OK;
    */
}

