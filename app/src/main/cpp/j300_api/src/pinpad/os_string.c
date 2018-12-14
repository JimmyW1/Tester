#include "pinpad/util.h"
#include "pinpad/os_string.h"


int Utils_Print_Hex(unsigned char *msg, unsigned char *input, int len)
{
#ifdef LOGSYS_FLAG
    int i;
    int line_num = len/16;
    int remainder = len%16;
    unsigned char* input_exp = NULL;
    char xbuf2[1024], xbuf1[4];

    if (len < 40)
    {
        memset(xbuf2, 0, sizeof(xbuf2));
        for (i = 0; i < len; i++)
        {
            sprintf(xbuf1, "%02X ", input[i]);
            strcat(xbuf2, xbuf1);
        }
        LOG_PRINTF(("%s len:%02d,data:[%s]", msg, len, xbuf2));
        return 0;
    }

    if (remainder != 0)
    {
        line_num += 1;
    }
    else
    {
        remainder = 16;
    }

    input_exp = (unsigned char*)malloc(line_num*16);
    if (input_exp == NULL)
    {
        return 0;
    }
    memset(input_exp, 0, line_num*16);
    memcpy(input_exp, input, len);
    LOG_PRINTF(("%s[len=%d]: ", msg, len));
    for (i = 0; i < line_num - 1; i++)
    {
        LOG_PRINTF(("%02X|%02X %02X %02X %02X %02X %02X %02X %02X %02X %02X %02X %02X %02X %02X %02X %02X",
                    i,
                    input_exp[16*i  ],input_exp[16*i+1],input_exp[16*i+2],input_exp[16*i+3],
                    input_exp[16*i+4],input_exp[16*i+5],input_exp[16*i+6],input_exp[16*i+7],
                    input_exp[16*i+8],input_exp[16*i+9],input_exp[16*i+10],input_exp[16*i+11],
                    input_exp[16*i+12],input_exp[16*i+13],input_exp[16*i+14],input_exp[16*i+15]));
    }

    memset(xbuf2, 0, sizeof(xbuf2));
    for (i = 0 ; i < remainder; i++)
    {
        sprintf(xbuf1, "%02X ", input_exp[16*(line_num-1)+i]);
        strcat(xbuf2, xbuf1);
    }
    LOG_PRINTF(("%02X|%s", line_num-1, xbuf2));

    free(input_exp);
#endif
    return 0;
}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: PP222_str2long.
 * DESCRIPTION:   converts str to long.
 * RETURN:        none.
 * NOTES:         none.
 * ------------------------------------------------------------------------ */
long PP222_str2long (char *string)
{
    long ret_val = 0L;
    /** make a copy of the string to preserve original **/
    char temp [20];
    unsigned i;

    // DCS #1045 : Included following 2 lines
    if ( string == NULL)
        return -1;

    strcpy (temp, string);

    // If we get a buffer with no valid digits then return
//    if (1 > str2digit (temp) || 12 < strlen (temp))
    if (12 < strlen (temp))
        return ret_val;

    // Convert to a long number. Skip the sign
    if (temp[0] == '-')
        i = 1;
    else
        i = 0;
    for (; i < strlen(temp); i++)
        ret_val = (ret_val * 10) + (temp[i] - 48);

    // Return appropriate value depending on the sign
    return ((temp[0] == '-') ? -ret_val : ret_val);

}

/* --------------------------------------------------------------------------
 * FUNCTION NAME: PP202_Bin2Ascii
 * DESCRIPTION:   covert binary n byte length data into ASCII string
 *                len: length of binary data
 * RETURN:        none.
 * NOTES:         none.
 * ------------------------------------------------------------------------ */
void PP222_Bin2Ascii(byte *out, byte *in, usint len)
{
    sint i;
    for (i = 0; i < len; i++)
    {
        out[ 2 * i ] = (in[ i ] >> 4) + '0';
        if (out[ 2 * i ] > '9')
            out[ 2 * i ] += 7;
        out[ 2 * i + 1 ] = (in[ i ] & 0xF) + '0';
        if (out[ 2 * i + 1 ] > '9')
            out[ 2 * i + 1 ] += 7;
    }
}
