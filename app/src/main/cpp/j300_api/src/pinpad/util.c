#include "pinpad/util.h"

void Crc16CCITT(byte *pbyDataIn, unsigned long dwDataLen, byte abyCrcOut[2])
{
    unsigned short wCrc = 0;
    byte byTemp;
    const unsigned short g_awhalfCrc16CCITT[16]=  /* CRC 半字节余式表 */
    {
        0x0000, 0x1021, 0x2042, 0x3063, 0x4084, 0x50a5, 0x60c6, 0x70e7,
        0x8108, 0x9129, 0xa14a, 0xb16b, 0xc18c, 0xd1ad, 0xe1ce, 0xf1ef
    };
    while (dwDataLen-- != 0)
    {
        byTemp = ((byte)(wCrc>>8))>>4;
        wCrc <<= 4;
        wCrc ^= g_awhalfCrc16CCITT[byTemp^(*pbyDataIn/16)];
        byTemp = ((byte)(wCrc>>8))>>4;
        wCrc <<= 4;
        wCrc ^= g_awhalfCrc16CCITT[byTemp^(*pbyDataIn&0x0f)];
        pbyDataIn++;
    }
    abyCrcOut[0] = wCrc/256;
    abyCrcOut[1] = wCrc%256;
}

