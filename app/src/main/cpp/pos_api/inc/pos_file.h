#ifndef __POS_FILE_H__
#define __POS_FILE_H__
#include "pos_type.h"


#define POS_FILES_DIR			"appfs"

typedef enum
{
	POS_FILE_SEEK_SET = 0, 	    /* start of stream             */
	POS_FILE_SEEK_CUR = 1, 	    /* current position in stream  */
	POS_FILE_SEEK_END = 2, 	    /*  end of stream              */
	POS_FILE_SEEK_MAX,
}POS_FILE_SEEK;


typedef enum
{
	POS_FILE_MODE_RDNOLY,
	POS_FILE_MODE_RDWR,
	POS_FILE_MODE_CREATE,
	POS_FILE_MODE_MAX,
}POS_FILE_MODE;


int32_t POS_FileOpen(const char *fileName,POS_FILE_MODE mode);
int32_t POS_FileWrite(int32_t fd,void *data, int32_t len);
int32_t POS_FileRead(int32_t fd,void *data, int32_t len);
int32_t POS_FileSeek(int32_t fd,int32_t offset, POS_FILE_SEEK seek);
int32_t POS_FileTruncate(const char *filename, int32_t size);
int32_t POS_FileChsize(int32_t fd,  int32_t size);
int32_t POS_FileClose(int32_t fd);
int32_t POS_FileRemove(const char *name);
int32_t POS_FileSize(const char *fileName);



#endif

