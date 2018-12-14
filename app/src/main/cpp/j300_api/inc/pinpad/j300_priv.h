#ifndef _J300_PRIV_H_
#define _J300_PRIV_H_

extern int J300_OpenDevice(char *devName);
extern void J300_CloseDevice();
extern int J300_Crc16SendPacket(byte *pbSendData, int wDataLen, unsigned short wCmd);
extern int J300_Crc16RecvPacket(unsigned short wCmd, byte *pbyRecvData, int *pwPacketetLen, int dwTimeoutMs, int PackSize, int fCancel);
#endif /* _J300_PRIV_H_ */
