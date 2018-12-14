#ifndef _J300_H_
#define _J300_H_

extern int J300_SetPPType(unsigned short pp_type, unsigned short pp_mode, unsigned short key_format);
extern int J300_Connect(char *devName);
extern void J300_Disconnect();
extern int J300_Idle(void);
extern int J300_SetIdleMsg(char *IdleMsg);
extern void J300_SetDispMode(byte mode);
extern int J300_DisplayMsg(char *Line1Msg, char *Line2Msg);

extern int J300_LoadKLK(unsigned char *klk);
extern int J300_CheckKLK(void);
extern int J300_LoadMasterKey(unsigned short number, unsigned char *MKey);
extern int J300_SelectKey(unsigned short number);
extern int J300_GetPin(unsigned char *PIN, char *am, unsigned char *PAN, unsigned char *PINKey, unsigned char MinPINLen, unsigned char MaxPINLen, unsigned short timeout, unsigned short KeyIndex);
extern int J300_GetPlainPIN(unsigned char *inPIN, unsigned short *len, char *am, unsigned char MinPINLen, unsigned char MaxPINLen, unsigned short timeout, unsigned short KeyIndex);
extern int J300_GetCBCMAC(unsigned char *MAC, unsigned char *Data, unsigned short DataLen, unsigned short KeyIndex, unsigned char *MacKey);
extern int J300_DesByMKey(unsigned char *In, unsigned char *Out, unsigned char Type, unsigned short KeyIndex);
extern int J300_InputText(int msg_disp_line, char *msgPrompt, int str_disp_line, char *str, usint min, usint max, int disp_mode, usint timeout);
extern int J300_LcdDisplayLogo (unsigned char index);
extern int J300_LcdCls(void);
extern int J300_LcdDispStr(byte x, byte y, char *str);
extern int J300_Get_PIN_Cancel(int flag);
extern int J300_LoadKey(char KeyType, unsigned short Key_index, unsigned char *Key_value);
extern int J300_ClearKeys(void);


#endif /* _J300_H_ */
