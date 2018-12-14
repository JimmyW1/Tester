#include "pos_file.h"
#include "pos_debug.h"
#include <string.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <fcntl.h>
#include <dirent.h>

#define ANDROID_POS_FILE_PATH "/data/data/com.pos.posapp/data"

const int fileModeTbl[POS_FILE_MODE_MAX] = 
{
	O_RDONLY,
	O_RDWR,
	O_CREAT | O_RDWR
};


const int fileSeekTbl[POS_FILE_SEEK_MAX] = 
{
	SEEK_SET,SEEK_CUR,SEEK_END
};

int32_t POS_FileOpen(const char *fileName,POS_FILE_MODE mode)
{
	char fn[128];
	struct DIR *dirptr = NULL;


	if(NULL == (dirptr = opendir(ANDROID_POS_FILE_PATH)))
	{
		if(mkdir(ANDROID_POS_FILE_PATH,0777) < 0)
		{
			return -5;
		}
	}
	else
	{
		closedir(dirptr);
	}
	if(mode >= POS_FILE_MODE_MAX)
	{
		return -2;
	}

	
	sprintf(fn,"%s%s",ANDROID_POS_FILE_PATH,fileName);
	
	return open(fn,fileModeTbl[mode],0777);
}
int32_t POS_FileWrite(int32_t fd,void *data, int32_t len)
{
	return write(fd,data,len);
}
int32_t POS_FileRead(int32_t fd,void *data, int32_t len)
{
	return read(fd,data,len);
}
int32_t POS_FileSeek(int32_t fd,int32_t offset, POS_FILE_SEEK seek)
{
	if(seek >= POS_FILE_SEEK_MAX)
	{
		return -4;
	}
	return lseek(fd,offset,fileSeekTbl[seek]);
}
int32_t POS_FileChsize(int32_t fd,  int32_t size)
{

	ftruncate(fd, size);
	return 0;
}
int32_t POS_FileClose(int32_t fd)
{
	close(fd);
	return 0;
}

int32_t POS_FileTruncate(const char *filename, int32_t size)
{
	char fn[128];
	
	memset(fn,0,sizeof(fn));
	sprintf(fn,"%s%s",ANDROID_POS_FILE_PATH,filename);
	truncate(filename,size);
	return 0;
}

int32_t POS_FileRemove(const char *name)
{
	
	char fn[128];
	
	memset(fn,0,sizeof(fn));
	sprintf(fn,"%s%s",ANDROID_POS_FILE_PATH,name);
	unlink(name);
	return 0;
}


int32_t POS_FileSize(const char *fileName)
{
	int32_t fd;
	int32_t size = 0;

	
	fd = POS_FileOpen(fileName,POS_FILE_MODE_RDNOLY);
	if(fd > 0)
	{
		size = POS_FileSeek(fd,0,POS_FILE_SEEK_END);
		POS_FileClose(fd);
	}
	return size;
}


