#ifndef _BARCODE1_H_
#define _BARCODE1_H_

extern int Scanner_Start(char * buf, int len, int *get_data_len, long timeout_ms);
extern int Scanner_Disconnect();
extern int Scanner_Connect();

#endif /* _BARCODE1_H_ */