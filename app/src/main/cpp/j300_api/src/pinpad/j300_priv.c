#include <pos_uart.h>
#include <pos_usbser.h>
#include <pos_debug.h>
#include <common/comm_driver.h>
#include "pinpad/util.h"
#include "pinpad/j300_priv.h"

static int J300_handle = -1;

int J300_OpenDevice(char *devName)
{
    J300_handle =  POS_OPEN(POS_USBSER_1);
    return  J300_handle;
}

void J300_CloseDevice()
{
    POS_CLOSE(J300_handle);
}

int J300_Crc16SendPacket(byte *pbSendData, int wDataLen, unsigned short wCmd)
{
    byte abyBuff[MAX_BUFFER_SIZE+128];
    int iRet;
    char buf2[128];

    POS_DEBUG_WITHTAG("UsbSerDemo", "Send packet\n");

    if (wDataLen > MAX_BUFFER_SIZE+100)
    {
        POS_DEBUG_WITHTAG("UsbSerDemo", "wDataLen is too big\n");
        //LOG_PRINTF(("wDataLen is too big"));
        return VS_ERR;
    }

    abyBuff[0] = 0x02;
    abyBuff[1] = wCmd/256;
    abyBuff[2] = wCmd%256;
    abyBuff[3] = wDataLen/256;
    abyBuff[4] = wDataLen%256;
    memcpy(&abyBuff[5], pbSendData, wDataLen);
    Crc16CCITT(&abyBuff[1], wDataLen+4, &abyBuff[wDataLen+5]);

    //先清空buffer;很重要!!!!
    while ((iRet = POS_Read(J300_handle, buf2, 100, 100)) > 0)
    {
        Utils_Print_Hex("EPP-->POS:", (byte *)buf2, iRet);
    }

    Utils_Print_Hex("POS-->EPP:", abyBuff, wDataLen+7);

    if (POS_Write(J300_handle, (char *)abyBuff, wDataLen+7) != wDataLen+7)
    {
        //LOG_PRINTF(("POS_Write error!"));
        POS_DEBUG_WITHTAG("UsbSerDemo", "POS_Write error!\n");
        PP222_SetErrorStatus(ERR_COMM_ERROR);
        return VS_ERR;
    }

    PP222_SetErrorStatus(PINPAD_NO_ERROR);
    return VS_SUCCESS;
}

int J300_Crc16RecvPacket(unsigned short wCmd, byte *pbyRecvData, int *pwPacketetLen, int dwTimeoutMs, int PackSize, int fCancel)
{
    unsigned long timeouts;
    char xbuf[MAX_BUFFER_SIZE] = {0};
    int expect_len, sz;

    memset(xbuf, 0, sizeof(xbuf));
    sz = 0;
    expect_len = 5;

    POS_DEBUG_WITHTAG("UsbSerDemo", "dwTimeoutMs =%d!\n", dwTimeoutMs);

    int sleep_time_ms = 0;
    while(TRUE) {
        sz += POS_Read(J300_handle, xbuf+sz, MAX_BUFFER_SIZE, dwTimeoutMs);
        POS_DEBUG_WITHTAG("UsbSerDemo", "Read data len=%d\n", sz);
        if ( sz >= 5 && expect_len == 5)
        {
            expect_len =  xbuf[3]*256 + xbuf[4] + 7;

            if ( expect_len > MAX_BUFFER_SIZE )
            {
                POS_DEBUG_WITHTAG("UsbSerDemo", "response data len is error!\n");
                //LOG_PRINTF(("response data len is error"));
                return VS_ABORT;
            }
        }

        if ( sz >= expect_len)
        {
            //check the command code is the same with the send command.
            if ((xbuf[1] != wCmd/256) || (xbuf[2] != (wCmd%256+1)))
            {
                POS_DEBUG_WITHTAG("UsbSerDemo", "response data len is error!\n");
                //LOG_PRINTF(("response command code is error"));
                return VS_ERROR;
            }

            *pwPacketetLen = (sz > PackSize) ? PackSize : sz;
            memcpy(pbyRecvData, xbuf, *pwPacketetLen);

            Utils_Print_Hex("EPP-->POS:", pbyRecvData, sz);

            PP222_SetErrorStatus(PINPAD_NO_ERROR);
            return VS_SUCCESS;
        }

        usleep(100000);
        sleep_time_ms += 100;
        if (sleep_time_ms >= dwTimeoutMs) {
            break;
        }
    }


    POS_DEBUG_WITHTAG("UsbSerDemo", "read data timeout!\n");
    PP222_SetErrorStatus(ERR_COMM_ERROR);
    return VS_TIMEOUT;  //Timeout
}


