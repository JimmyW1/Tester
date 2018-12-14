//
// Created by wangxx on 2016/12/9.
//

#include "stdlib.h"



void POS_ChargeEnable(void)
{
    system("echo \"1\" > /sys/class/power_supply/battery/charging_enabled");
}

void POS_ChargeDisable(void)
{
    system("echo \"0\" > /sys/class/power_supply/battery/charging_enabled");
}