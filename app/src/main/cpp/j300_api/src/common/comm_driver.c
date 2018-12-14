#include <pos_uart.h>
#include <pos_usbser.h>
#include <pos_debug.h>
#include <errno.h>
#include "common/comm_driver.h"

int POS_Write(int fd, const void *buf, size_t count)
{
    if (fd >= 0) {
        return pos_uart_write(fd, buf, count);
    }

    return 0;
}

int POS_Read(int fd, void *buf, size_t count, int timeout_ms)
{
    if (fd >= 0) {
        return pos_uart_read(fd, buf, count, timeout_ms);
    }

    return 0;
}

int POS_OPEN(POS_USBSER_NUM usbser_num)
{
    char dev_name[64] = {0};
    POS_SERIAL_PARAM param;
    int fd = -1;

    param.baud_rate = BD115200; //BD19200;
    param.data_bits = DATA_8;
    param.stop_bits = STOP_1;
    param.serial_parity = PARITY_N;

    if(POS_GetUsbserDevName(usbser_num, dev_name,sizeof(dev_name)) < 0)
    {
        POS_DEBUG_WITHTAG("UsbserDemo","GetUsberDevName failed, dev_name %s",dev_name);
        fd = -1;
        return fd;
    }

    POS_DEBUG_WITHTAG("UsbserDemo","dev_name %s",dev_name);

    fd = pos_uart_open(dev_name,&param);
    if (fd < 0) {
        POS_DEBUG_WITHTAG("UsbSerDemo", "dev_fs %s,fd = %d,errno = %d\r\n",dev_name, fd, errno);
    }

    return fd;
}

void POS_CLOSE(int fd)
{
    pos_uart_close(fd);
}
