#include <stdio.h>  
#include <unistd.h>  
#include <stdlib.h>  
#include <sys/types.h>  
#include <sys/stat.h>  
#include <sys/ioctl.h>  
#include <fcntl.h>  
#include <linux/fs.h>  
#include <errno.h>  
#include <string.h>  
#include <termio.h>
#include <pos_uart.h>

#include "pos_uart.h"
#include "pos_debug.h"


#define TAG "POS_UART"
#define POS_UART_DEBUG_HEX(buf,len) POS_DEBUG_HEX_WITHTAG(TAG,buf,len)
#define POS_UART_DEBUG(...) POS_DEBUG_WITHTAG(TAG,__VA_ARGS__)

static int inx_uart_set_parity(struct termios *p_termptr, POS_SERIAL_PARITY parity_bits)
{
	switch ((int)parity_bits)  
	{    
	case PARITY_N: //����żУ��λ��  
		p_termptr->c_cflag &= ~PARENB;   
		p_termptr->c_iflag &= ~INPCK;      
		break;   
	case PARITY_O://����Ϊ��У��      
		p_termptr->c_cflag |= (PARODD | PARENB);   
		p_termptr->c_iflag |= INPCK;               
		break;   
	case PARITY_E://����ΪżУ��    
		p_termptr->c_cflag |= PARENB;         
		p_termptr->c_cflag &= ~PARODD;         
		p_termptr->c_iflag |= INPCK;        
		break;  
	case PARITY_S: //����Ϊ�ո�   
		p_termptr->c_cflag &= ~PARENB;  
		p_termptr->c_cflag &= ~CSTOPB;  
		break;   
	default:    
		return -1;
	}

	return 0;
}

static int inx_uart_set_speed(struct termios *p_termptr, POS_SERIAL_BAUD_RATE speed)
{
	const int speed_attr[] = {B3000000,B1500000, B115200, B19200, B9600, B4800, B2400, B1200, B300};

	if(speed > sizeof(speed_attr)/sizeof(speed_attr[0]))
	{
		return -1;
	}
	cfsetispeed(p_termptr, speed_attr[speed]);
	cfsetospeed(p_termptr, speed_attr[speed]);
	
	return 0;
}

static int inx_uart_set_databits(struct termios *p_termptr, POS_SERIAL_DATA_BITS data_bits)
{
	p_termptr->c_cflag &= ~CSIZE;  
	switch (data_bits)  
	{    
	case DATA_5:  
		p_termptr->c_cflag |= CS5;  
		break;  
	case DATA_6:  
		p_termptr->c_cflag |= CS6;  
		break;  
	case DATA_7:      
		p_termptr->c_cflag |= CS7;  
		break;  
	case DATA_8:      
		p_termptr->c_cflag |= CS8;  
		break;    
	default:     
		return -1;  
	}  

	return 0;
}

static int inx_uart_set_stopbits(struct termios *p_termptr, POS_SERIAL_STOP_BITS stop_bits)
{
	// ����ֹͣλ   
	switch (stop_bits)  
	{    
	case STOP_1:     
		p_termptr->c_cflag &= ~CSTOPB;
		break;   
	case STOP_2:     
		p_termptr->c_cflag |= CSTOPB;
		break;  
	default:     
		return -1; 
	}  

	return 0;
}

static int inx_uart_param_init(
									int handle,
									POS_SERIAL_BAUD_RATE speed,
									POS_SERIAL_DATA_BITS data_bits,
									POS_SERIAL_STOP_BITS stop_bits,									
									POS_SERIAL_PARITY parity_bits
									)
{
	int result = 0;
	struct termios serial_attr;

	do{
	memset(&serial_attr, 0x00, sizeof(serial_attr));
  	if(tcgetattr(handle, &serial_attr) !=  0)
	{
		result = -1;
		break;
  	}
	cfmakeraw(&serial_attr);
	inx_uart_set_speed(&serial_attr, speed);

	serial_attr.c_cflag |= CLOCAL;  
	serial_attr.c_cflag |= CREAD;  
	serial_attr.c_cflag |= HUPCL;
	//serial_attr.c_cflag |= CRTSCTS;	//ʹ��Ӳ��������

	inx_uart_set_databits(&serial_attr, data_bits);
	inx_uart_set_parity(&serial_attr, parity_bits);
	inx_uart_set_stopbits(&serial_attr, stop_bits);
	serial_attr.c_oflag &= ~OPOST;

	//���õȴ�ʱ�����С�����ַ�  
	serial_attr.c_cc[VTIME] = 0;
	serial_attr.c_cc[VMIN] = 1;
	tcflush(handle,TCIFLUSH);

	if (tcsetattr(handle,TCSANOW,&serial_attr) != 0)    
	{  
		result = -2;
		break; 
	}	
	}while(0);

	return result;
}

static int inx_uart_open(const char *port, int mode)  
{  
     int handle = -1;
     int result = 0;

	do{
	//POS_DEBUG("port:%s mode:%d", port, mode);
	handle = open(port, mode);
	if(handle < 0)
	{  
		result = -1;
		break;
	}  
	//�ָ�����Ϊ����״̬                                 
	/*	if(fcntl(handle, F_SETFL, 0) < 0)
	{  
		result = -2;
		break;
	}       
	//�����Ƿ�Ϊ�ն��豸      
	if(0 == isatty(STDIN_FILENO))
	{  
		result = -3;
		break;
	}               */
	}while(0);
	//POS_DEBUG("inx_uart_open handle %d", handle);	

  	return result ?result:handle;  
}  

static void inx_uart_close(int handle)  
{
	close(handle);
}

static int inx_uart_write(int handle, const char *send_buf, int data_len)
{
	int len = 0;  

	POS_UART_DEBUG_HEX(send_buf,data_len);
	tcflush(handle, TCIOFLUSH);
	len = write(handle,send_buf,data_len);

	return len;
}

static int inx_uart_read(int handle, char *rcv_buf, int data_len, int timeout)  
{  
	int offset = 0;
	int fs_sel = 0;  
	int tmplen = 0;
	fd_set fs_read;  
	struct timeval time;  

	time.tv_sec = timeout / 1000;
	time.tv_usec = (timeout % 1000) * 1000;
	FD_ZERO(&fs_read);  
	FD_SET(handle, &fs_read);  
	fs_sel = select(handle+1,&fs_read,NULL,NULL,&time);  
	if(fs_sel <= 0)
	{  
		POS_UART_DEBUG("select fail %d, errno %d, handle %d",fs_sel, errno, handle);
		return -1;
	}  			
	if(!FD_ISSET(handle, &fs_read))
	{
		POS_UART_DEBUG("fd_isset fail %d, errno %d, handle %d",fs_sel, errno, handle);	
		return -2;
	}	
	//for(;data_len > 0; data_len -= tmplen)
	do
	{	
		tmplen = read(handle, rcv_buf+offset, data_len); 
		if(tmplen <= 0)
		{
			break;
		}
		offset += tmplen;
		//POS_DEBUG("offset %d, tmplen %d", offset, tmplen);
	}while(0);
	POS_UART_DEBUG_HEX(rcv_buf, offset);

	return offset;
}  


const char *portMatchTbl[POS_PORT_NUM_MAX] = 
{
	"/dev/ttyS0",
	"/dev/ttyS1",		
};

int32_t pos_uart_open(const char *devFile,POS_SERIAL_PARAM *param)
{
	int32_t handle;

	handle = inx_uart_open((const char *)devFile, O_RDWR|O_NOCTTY|O_NDELAY);
	if(handle >= 0)
	{
		if(0 > inx_uart_param_init(handle, param->baud_rate, param->data_bits, param->stop_bits, param->serial_parity))
		{
			inx_uart_close(handle);
			return -2;
		}
	}

	return handle;
}

int32_t pos_uart_close(int32_t handle)
{
	 inx_uart_close(handle);

	 return 0;
}

int32_t pos_uart_read(int32_t handle,uint8_t *buf,uint32_t maxLen,uint32_t timeout)
{
	struct timeval tmofdata;
	struct timeval now;
	int32_t offset = 0;
	int32_t len;

	gettimeofday(&tmofdata,0);

	while(offset < maxLen)
	{
		gettimeofday(&now,0);
		if(((now.tv_sec - tmofdata.tv_sec) * 1000 + (now.tv_usec - tmofdata.tv_usec) / 1000) > timeout)
		{
			break;
		}
		len = inx_uart_read(handle, buf + offset, maxLen - offset, 100);
		if(len > 0)
		{
			offset += len;
			break; // 修改此处让此read函数读到东西就返回或者超时返回
		}
	}
	return offset;
}

int32_t pos_uart_write(int32_t handle,const uint8_t *data,uint32_t dataLen)
{
	return inx_uart_write(handle, (const char *)data, dataLen);
}

int32_t pos_uart_clearbuff(int32_t handle)
{
	tcflush(handle,TCIFLUSH); 
	tcflush(handle,TCOFLUSH);  
	return 0;
}
