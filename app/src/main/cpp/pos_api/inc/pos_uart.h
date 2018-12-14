#ifndef __POS_UART_H__
#define __POS_UART_H__


#include "stdint.h"
#include "pos_type.h"

#ifdef __cplusplus
extern "C" {
#endif

typedef enum {
	POS_PORT_NUM_COM1 = 0x00,
	POS_PORT_NUM_COM2,
	POS_PORT_NUM_MAX
} POS_SERPORT_NUM_E;

typedef enum {
	BD3000000 = 0x00,
	BD1500000,
	BD115200,
	BD19200,
	BD9600,
	BD4800,
	BD2400,
	BD1200,
	BD300,
} POS_SERIAL_BAUD_RATE;

typedef enum {
	DATA_5,
	DATA_6,
	DATA_7,
	DATA_8
} POS_SERIAL_DATA_BITS;

typedef enum {
	STOP_1 = 0x00,
	STOP_2,
} POS_SERIAL_STOP_BITS;

typedef enum {
	PARITY_N = 'N',
	PARITY_O = 'O',
	PARITY_E = 'E',
	PARITY_S = 'S',
} POS_SERIAL_PARITY;

typedef struct {
	POS_SERIAL_BAUD_RATE baud_rate;
	POS_SERIAL_DATA_BITS data_bits;
	POS_SERIAL_STOP_BITS stop_bits;
	POS_SERIAL_PARITY serial_parity;
} POS_SERIAL_PARAM;


int32_t pos_uart_open(const char *devFile, POS_SERIAL_PARAM *param);

int32_t pos_uart_close(int32_t handle);

int32_t pos_uart_read(int32_t handle, uint8_t *buf, uint32_t maxLen, uint32_t timeout);

int32_t pos_uart_write(int32_t handle, const uint8_t *data, uint32_t dataLen);

int32_t pos_uart_clearbuff(int32_t handle);


#ifdef __cplusplus
}
#endif
#endif//__POS_UART_H__
