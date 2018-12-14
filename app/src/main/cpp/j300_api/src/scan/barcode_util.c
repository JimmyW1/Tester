#include "scan/barcode_util.h"

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
