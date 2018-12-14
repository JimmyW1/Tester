#ifndef _COMM_DRIVER_H_
#define _COMM_DRIVER_H_

#include <stdio.h>
#include <pos_usbser.h>

extern int POS_Write(int fd, const void *buf, size_t count);
extern int POS_Read(int fd, void *buf, size_t count, int timeout_ms);
extern int POS_OPEN(POS_USBSER_NUM usbser_num);
extern void POS_CLOSE(int fd);

#endif /* _COMM_DRIVER_H_ */
