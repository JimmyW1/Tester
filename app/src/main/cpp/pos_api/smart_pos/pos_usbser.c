//
// Created by wangxx on 2016/12/9.
//

#include <string.h>

#include <stdio.h>
#include <dirent.h>
#include "pos_usbser.h"

#define USBSER_PORT_01 "/sys/bus/usb/drivers/pl2303/1-1.1:1.0"
#define USBSER_PORT_02 "/sys/bus/usb/drivers/pl2303/1-1.2:1.0"


int32_t POS_GetUsbserDevName(POS_USBSER_NUM n,char *devName,int32_t devNameBufSize)
{
    const char *dirPath[] = {USBSER_PORT_01,USBSER_PORT_02};
    DIR *dir;
    struct dirent *ptr;
    char *str = "no devices";
    int32_t ret = 0;


    if(n >= POS_USBSER_MAX)
    {
        return -1;
    }


    if ((dir=opendir(dirPath[n])) == NULL) {
        return -2;
    }
    ret = -3;
    while ((ptr=readdir(dir)) != NULL)
    {
        str = strstr(ptr->d_name, "ttyUSB");
        if (str != NULL)
        {
            if(strlen(str) + strlen("/dev/") < devNameBufSize - 1)
            {
                strcpy(devName,"/dev/");
                strcat(devName,str);
                ret = 0;
                break;
            }
        }
    }
    closedir(dir);
    return ret;
}