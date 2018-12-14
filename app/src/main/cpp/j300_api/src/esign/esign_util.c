#include "esign/esign_util.h"

/* --------------------------------------------------------------------------
* FUNCTION NAME: BankUtil_GenLrc
* DESCRIPTION:   Generate LRC, upper function should add STX, etc for
*                asyn data.
* PARAMETERS:    Data - input data buffer,
*                DataLen - data length.
* RETURN:        LRC byte.
* ------------------------------------------------------------------------ */
byte BankUtil_GenLrc(byte *Data, usint DataLen)
{
    sint  i;
    byte  lrc = 0;

    for (i = 0; i < DataLen; i++)
    {
        lrc ^= Data[i];
    }

    return lrc;
}

long getCurrentTimeMS()
{
    struct timeval now;

    gettimeofday(&now,0);

    return now.tv_sec * 1000 + now.tv_usec / 1000;
}

void SVC_WAIT(long timeout_ms) 
{
    usleep(timeout_ms * 1000);
}
