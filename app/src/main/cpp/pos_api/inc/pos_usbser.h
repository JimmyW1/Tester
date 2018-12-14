//
// Created by wangxx on 2016/12/9.
//

#ifndef USBSERDEMO_POS_USBSER_H
#define USBSERDEMO_POS_USBSER_H

#ifdef __cplusplus
extern "C" {
#endif

#include "stdint.h"

typedef enum {
    POS_USBSER_0,
    POS_USBSER_1,
    POS_USBSER_MAX,
} POS_USBSER_NUM;


int32_t POS_GetUsbserDevName(POS_USBSER_NUM n, char *devName, int32_t devNameBufSize);

#ifdef __cplusplus
}
#endif

#endif //USBSERDEMO_POS_USBSER_H
