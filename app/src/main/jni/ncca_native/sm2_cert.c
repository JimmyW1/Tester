#include <stdio.h>
#include <string.h>
#include <openssl/bio.h>
#include <openssl/x509.h>
#include <openssl/pem.h>
#include <openssl/err.h>
#include <openssl/asn1.h>
#include <openssl/safestack.h>

#include "sm.h"
#include "sm_api.h"
#include "sm2_cert.h"

#include "log.h"

#define ROOT_CERT_NAME "/sdcard/cert/topsecca.pem.cer"
#define VERI_CERT_NAME "/sdcard/cert/topsecca1.pem.cer"
#define ROOT_CERT 0
#define VERI_CERT 1

#define CERT_DEBUG      1
#define FORMAT_PEM      3

static const char *mon[12] = {
    "01", "02", "03", "04", "05", "06",
    "07", "08", "09", "10", "11", "12"
};

typedef char *OPENSSL_STRING;
/*
#define CHECKED_STACK_OF(type, p) \
    ((STACK*) (1 ? p : (STACK_OF(type)*)0))
*/

#define sk_OPENSSL_STRING_value(st, i) ((OPENSSL_STRING)sk_value(CHECKED_STACK_OF(OPENSSL_STRING, st), i))
#define sk_OPENSSL_STRING_num(st) SKM_sk_num(OPENSSL_STRING, st)
#define sk_OPENSSL_STRING_free(st) SKM_sk_free(OPENSSL_STRING, st)

int cert_test_main()
{
	cert_parse(ROOT_CERT_NAME);	
	cert_parse(VERI_CERT_NAME);

	cert_verify(ROOT_CERT_NAME);	
	cert_verify(VERI_CERT_NAME);

}


int gen_der(char *infile,char* derfile)
{
    int i, offset = 0, ret = 1, j;
    unsigned int length = 0;
    long num, tmplen;
    BIO *in = NULL, *out = NULL, *b64 = NULL, *derout = NULL;
    int informat;
    char *str = NULL;
    unsigned char *tmpbuf;
    const unsigned char *ctmpbuf;
    BUF_MEM *buf = NULL;
    STACK_OF(OPENSSL_STRING) *osk = NULL;
    ASN1_TYPE *at = NULL;

    informat = FORMAT_PEM;
	
    in = BIO_new_file(infile,"rb");
    	
    if (derfile) {
    	derout = BIO_new_file(derfile, "wb");
        if (!derout) {
            printf("problems opening %s\n", derfile);
            //BIO_printf(bio_err, "problems opening %s\n", derfile);
            //ERR_print_errors(bio_err);
            goto end;
        }
    }

    if ((buf = BUF_MEM_new()) == NULL)
        goto end;
    if (!BUF_MEM_grow(buf, BUFSIZ * 8))
        goto end;               /* Pre-allocate :-) */


    if (informat == FORMAT_PEM) {
        BIO *tmp;

        if ((b64 = BIO_new(BIO_f_base64())) == NULL)
            goto end;
        BIO_push(b64, in);
        tmp = in;
        in = b64;
        b64 = tmp;
    }

    num = 0;
    for (;;) {
        if (!BUF_MEM_grow(buf, (int)num + BUFSIZ))
            goto end;
        i = BIO_read(in, &(buf->data[num]), BUFSIZ);
        if (i <= 0)
            break;
        num += i;
    }
    
    str = buf->data;

    /* If any structs to parse go through in sequence */

    if (sk_OPENSSL_STRING_num(osk)) {
        tmpbuf = (unsigned char *)str;
        tmplen = num;
        for (i = 0; i < sk_OPENSSL_STRING_num(osk); i++) {
            ASN1_TYPE *atmp;
            int typ;
            j = atoi((char*)sk_OPENSSL_STRING_value(osk, i));
            if (j == 0) {
                //BIO_printf(bio_err, "'%s' is an invalid number\n",
                //           sk_OPENSSL_STRING_value(osk, i));
                continue;
            }
            tmpbuf += j;
            tmplen -= j;
            atmp = at;
            ctmpbuf = tmpbuf;
            at = d2i_ASN1_TYPE(NULL, &ctmpbuf, tmplen);
            ASN1_TYPE_free(atmp);
            if (!at) {
                //BIO_printf(bio_err, "Error parsing structure\n");
                //ERR_print_errors(bio_err);
                goto end;
            }
            typ = ASN1_TYPE_get(at);
            if ((typ == V_ASN1_OBJECT)
                || (typ == V_ASN1_NULL)) {
                //BIO_printf(bio_err, "Can't parse %s type\n",
                //           typ == V_ASN1_NULL ? "NULL" : "OBJECT");
                //ERR_print_errors(bio_err);
                goto end;
            }
            /* hmm... this is a little evil but it works */
            tmpbuf = at->value.asn1_string->data;
            tmplen = at->value.asn1_string->length;
        }
        str = (char *)tmpbuf;
        num = tmplen;
    }

    if (offset >= num) {
        //BIO_printf(bio_err, "Error: offset too large\n");
        goto end;
    }

    num -= offset;

    if ((length == 0) || ((long)length > num))
        length = (unsigned int)num;
    if (derout) {
        if (BIO_write(derout, str + offset, length) != (int)length) {
            //BIO_printf(bio_err, "Error writing output\n");
            //ERR_print_errors(bio_err);
            goto end;
        }
    }

	
    ret = 0;
 end:
    BIO_free(derout);
    if (in != NULL)
        BIO_free(in);
    if (out != NULL)
        BIO_free_all(out);
    if (b64 != NULL)
        BIO_free(b64);
    //if (ret != 0)
        //ERR_print_errors(bio_err);
    if (buf != NULL)
        BUF_MEM_free(buf);
    if (at != NULL)
        ASN1_TYPE_free(at);
    if (osk != NULL)
        sk_OPENSSL_STRING_free(osk);
    OBJ_cleanup();
    return ret;
}

typedef int char_io (void *arg, const void *buf, int len);

static int send_bio_chars(void *arg, const void *buf, int len)
{
    if (!arg)
        return 1;
    if (BIO_write(arg, buf, len) != len)
        return 0;
    return 1;
}

static int do_hex_dump(char_io *io_ch, void *arg, unsigned char *buf,
                       int buflen)
{
    static const char hexdig[] = "0123456789ABCDEF";
    unsigned char *p, *q;
    char hextmp[2];
    if (arg) {
        p = buf;
        q = buf + buflen;
        while (p != q) {
            hextmp[0] = hexdig[*p >> 4];
            hextmp[1] = hexdig[*p & 0xf];
            if (!io_ch(arg, hextmp, 2))
                return -1;
            p++;
        }
    }
    return buflen << 1;
}

static int ASN1_GENERALIZEDTIME_print_SM2(BIO *bp, const ASN1_GENERALIZEDTIME *tm)
{
    char *v;
    int gmt = 0;
    int i;
    int y = 0, M = 0, d = 0, h = 0, m = 0, s = 0;
    char *f = NULL;
    int f_len = 0;

    i = tm->length;
    v = (char *)tm->data;

    if (i < 12)
        goto err;
    if (v[i - 1] == 'Z')
        gmt = 1;
    for (i = 0; i < 12; i++)
        if ((v[i] > '9') || (v[i] < '0'))
            goto err;
    y = (v[0] - '0') * 1000 + (v[1] - '0') * 100
        + (v[2] - '0') * 10 + (v[3] - '0');
    M = (v[4] - '0') * 10 + (v[5] - '0');
    if ((M > 12) || (M < 1))
        goto err;
    d = (v[6] - '0') * 10 + (v[7] - '0');
    h = (v[8] - '0') * 10 + (v[9] - '0');
    m = (v[10] - '0') * 10 + (v[11] - '0');
    if (tm->length >= 14 &&
        (v[12] >= '0') && (v[12] <= '9') &&
        (v[13] >= '0') && (v[13] <= '9')) {
        s = (v[12] - '0') * 10 + (v[13] - '0');
        /* Check for fractions of seconds. */
        if (tm->length >= 15 && v[14] == '.') {
            int l = tm->length;
            f = &v[14];         /* The decimal point. */
            f_len = 1;
            while (14 + f_len < l && f[f_len] >= '0' && f[f_len] <= '9')
                ++f_len;
        }
    }

    if (BIO_printf(bp, "%d-%s-%2d %02d:%02d:%02d%.*s %s",
                   y,mon[M - 1], d, h, m, s, f_len, f, 
                   (gmt) ? " 格林尼治标准时间" : "") <= 0)
        return (0);
    else
        return (1);
 err:
    BIO_write(bp, "Bad time value", 14);
    return (0);
}

static int ASN1_UTCTIME_print_SM2(BIO *bp, const ASN1_UTCTIME *tm)
{
    const char *v;
    int gmt = 0;
    int i;
    int y = 0, M = 0, d = 0, h = 0, m = 0, s = 0;

    i = tm->length;
    v = (const char *)tm->data;

    if (i < 10)
        goto err;
    if (v[i - 1] == 'Z')
        gmt = 1;
    for (i = 0; i < 10; i++)
        if ((v[i] > '9') || (v[i] < '0'))
            goto err;
    y = (v[0] - '0') * 10 + (v[1] - '0');
    if (y < 50)
        y += 100;
    M = (v[2] - '0') * 10 + (v[3] - '0');
    if ((M > 12) || (M < 1))
        goto err;
    d = (v[4] - '0') * 10 + (v[5] - '0');
    h = (v[6] - '0') * 10 + (v[7] - '0');
    m = (v[8] - '0') * 10 + (v[9] - '0');
    if (tm->length >= 12 &&
        (v[10] >= '0') && (v[10] <= '9') && (v[11] >= '0') && (v[11] <= '9'))
        s = (v[10] - '0') * 10 + (v[11] - '0');

    if (BIO_printf(bp, "%d-%s-%2d %02d:%02d:%02d %s",
                   y + 1900,mon[M - 1], d, h, m, s, 
                   (gmt) ? "格林尼治标准时间" : "") <= 0)
        return (0);
    else
        return (1);
 err:
    BIO_write(bp, "Bad time value", 14);
    return (0);
}

static int ASN1_TIME_print_SM2(BIO *bp, const ASN1_TIME *tm)
{
    if (tm->type == V_ASN1_UTCTIME)
        return ASN1_UTCTIME_print_SM2(bp, tm);
    if (tm->type == V_ASN1_GENERALIZEDTIME)
        return ASN1_GENERALIZEDTIME_print_SM2(bp, tm);
    BIO_write(bp, "Bad time value", 14);
    return (0);
}

int X509_dump(BIO *bp, const ASN1_STRING *sig, int start,int indent)
{
    const unsigned char *s;
    int n;

    n = sig->length;
    s = sig->data;
    
	do_hex_dump(send_bio_chars,bp,(unsigned char *)(s+start),n-start);
	
    return 1;
}

int X509_print_cert_code(char* pem_cert,BIO* bp)
{
	BIO* d;
	char cert_der[80];
	char buf[20];
	int  real_bytes = 0;

	memset(cert_der,0,sizeof(cert_der));
	memcpy(cert_der,pem_cert,strlen(pem_cert));
	sprintf(cert_der+strlen(pem_cert),".der");
	
	gen_der(pem_cert,cert_der);
	d=BIO_new_file(cert_der,"rb");
	
	real_bytes = BIO_read(d,(void*)buf,20);
	while(real_bytes > 0)
	{	
		do_hex_dump(send_bio_chars,bp,(unsigned char *)buf,real_bytes);	
		real_bytes = BIO_read(d,(void*)buf,20);
	}

	BIO_free(d);
	return 1;
}

int X509_print_ex_sm2(char* pem_cert,BIO *bp, X509 *x, unsigned long nmflags,
                  unsigned long cflag)
{
    int ret = 0;
    char *m = NULL;
    int nmindent = 0;
    X509_CINF *ci;

    if ((nmflags & XN_FLAG_SEP_MASK) == XN_FLAG_SEP_MULTILINE) {
        nmindent = 12;
    }

    if (nmflags == X509_FLAG_COMPAT)
        nmindent = 16;

    ci = x->cert_info;
    if (!(cflag & X509_FLAG_NO_HEADER)) {
        if (BIO_printf(bp, "证书 %s\r\n",pem_cert) <= 0)
            goto err;
        /*if (BIO_write(bp, "    Data:\n", 10) <= 0)
            goto err;*/
    }
    
    
    if (!(cflag & X509_FLAG_NO_SUBJECT)) {
        if (BIO_printf(bp, "持有者\r\n") <= 0)
            goto err;
        if (X509_NAME_print_ex
            (bp, X509_get_subject_name(x), nmindent, nmflags) < 0)
            goto err;
        if (BIO_printf(bp, "\r\n") <= 0)
            goto err;
    }

    if (!(cflag & X509_FLAG_NO_ISSUER)) {
        if (BIO_printf(bp, "签发者\r\n") <= 0)
            goto err;
        if (X509_NAME_print_ex(bp, X509_get_issuer_name(x), nmindent, nmflags)
            < 0)
            goto err;
        if (BIO_printf(bp, "\r\n") <= 0)
            goto err;
    }
    
    if (!(cflag & X509_FLAG_NO_VALIDITY)) {
        if (BIO_printf(bp, "有效期\r\n") <= 0)
            goto err;
        if (BIO_printf(bp, "有效起始日期：") <= 0)
            goto err;
        if (!ASN1_TIME_print_SM2(bp, X509_get_notBefore(x)))
            goto err;
        if (BIO_printf(bp, "\r\n有效终止日期：") <= 0)
            goto err;
        if (!ASN1_TIME_print_SM2(bp, X509_get_notAfter(x)))
            goto err;
        if (BIO_printf(bp, "\r\n") <= 0)
            goto err;
    }

    if (!(cflag & X509_FLAG_NO_PUBKEY)) {
        if (BIO_printf(bp, "公钥\r\n") <= 0)
            goto err;

		if (X509_dump(bp, ci->key->public_key,1,0) <= 0)
			goto err;
		/*if (X509_signature_dump(bp, ci->key->public_key,12) <= 0)
            goto err;*/
        if (BIO_printf(bp, "\r\n") <= 0)
            goto err;        
    }

	if (BIO_printf(bp, "证书编码\r\n") <= 0)
		goto err;

	X509_print_cert_code(pem_cert,bp);
    if (BIO_puts(bp, "\r\n") <= 0)
        goto err; 
    
    ret = 1;
 err:
    if (m != NULL)
        OPENSSL_free(m);
    return (ret);
}

int X509_print_sm2(char* pem_cert,BIO *bp, X509 *x)
{
    return X509_print_ex_sm2(pem_cert, bp, x, XN_FLAG_SEP_CPLUS_SPC,X509_FLAG_COMPAT);
}

void cert_parse(char* pem_cert)
{
	/*char* pem_cert = "topsecca.pem.cer";*/
	char cert_txt[80];
	X509* x;
	BIO* b;
	char buf[256];

	printf("cert parse\n");
	if(0 == strcmp(pem_cert,ROOT_CERT_NAME))
	{
		sprintf (buf, "root cert");
	}
	else
	{
		sprintf (buf, "level 2 cert");
	}	
	//printf("buf %s\n", buf);
	printf("parsing...");
	
	b=BIO_new_file(pem_cert,"rb");
	x=PEM_read_bio_X509(b,NULL,NULL,NULL);
	BIO_free(b);
	memcpy(cert_txt,pem_cert,strlen(pem_cert));
	sprintf(cert_txt+strlen(pem_cert),".txt");
	b=BIO_new_file(cert_txt,"wb");
	X509_print_sm2(pem_cert,b,x);
	BIO_free(b);
	X509_free(x);

	printf("cert parse done\n");
}

char szBuffer_tmp[2048];
char szBuffer_bcd[2048];
char szPubKey[128];
char szSignResult[128];
char szSplitStr[12] = {0x30,0x0C,0x06,0x08,0x2A,0x81,0x1C,0xCF,0x55,0x01,0x83,0x75};

int cert_verify(char* pem_cert)
{
	int  ret = -1;
 	X509* x;
	BIO* b,*out_b;
	int type;
	int real_bytes;
	char cert_der[80];
	char veri_out[80];
	char* current_pos;
	int i,len;
	int counter = 0;
	char buf[256] = {0};
	
	printf("cert chain verify\n");
	if(0 == strcmp(pem_cert,ROOT_CERT_NAME))
	{
		type = ROOT_CERT;
		sprintf (buf, "root cert\n");
	}
	else
	{
		type = VERI_CERT;
		sprintf (buf, "level 2 cert\n");
	}
	//printf(buf);
	printf("verifying...");

	memcpy(veri_out,pem_cert,strlen(pem_cert));
	sprintf(veri_out+strlen(pem_cert)-4,".vfy.txt");	
	out_b = BIO_new_file(veri_out,"wb");
	
	/*get pubkey*/
	b=BIO_new_file("/sdcard/cert/topsecca.pem.cer","rb");
	x=PEM_read_bio_X509(b,NULL,NULL,NULL);
	BIO_free(b);
#if CERT_DEBUG
	printf("key_len:%d.\n", x->cert_info->key->public_key->length);
	LOG_PRINT_HEX("key",x->cert_info->key->public_key->data,x->cert_info->key->public_key->length);
#endif
	memset(szPubKey,0,sizeof(szPubKey));
	memcpy(szPubKey,x->cert_info->key->public_key->data+1,x->cert_info->key->public_key->length-1);
	X509_free(x);
#if CERT_DEBUG	
	LOG_PRINT_HEX("szPubKey",szPubKey,70);
#endif
	BIO_printf(out_b,"公钥\r\n");
	do_hex_dump(send_bio_chars,out_b,(unsigned char *)szPubKey,64);
	BIO_printf(out_b,"\r\n");

	BIO_printf(out_b,"签名者ID\r\n");
	BIO_printf(out_b,"31323334353637383132333435363738");
	BIO_printf(out_b,"\r\n");
	
	/*get signature*/
	b=BIO_new_file(pem_cert,"rb");
	x=PEM_read_bio_X509(b,NULL,NULL,NULL);
	BIO_free(b);
#if CERT_DEBUG
	printf("sign_len:%d.\n", x->signature->length);
	LOG_PRINT_HEX("sign",x->signature->data,x->signature->length);
#endif
	memset(szSignResult,0,sizeof(szSignResult));
	if(type == ROOT_CERT)
	{
		/*R*/
		memcpy(szSignResult,x->signature->data+5,32);
		/*S*/
		memcpy(szSignResult+32,x->signature->data+5+34,32);
	}
	else if(type == VERI_CERT)
	{
		/*R*/
		memcpy(szSignResult,x->signature->data+4,32);
		/*S*/
		memcpy(szSignResult+32,x->signature->data+4+34,32);
	}
	X509_free(x);
#if CERT_DEBUG
	LOG_PRINT_HEX("szSignResult",szSignResult,70);
#endif
	
	/*get signature data*/
	memset(cert_der,0,sizeof(cert_der));
	memcpy(cert_der,pem_cert,strlen(pem_cert));
	sprintf(cert_der+strlen(pem_cert),".der");

	memset(szBuffer_tmp,0,sizeof(szBuffer_tmp));
	b=BIO_new_file(cert_der,"rb");
	current_pos = szBuffer_tmp;
	real_bytes = BIO_read(b,(void*)current_pos,20);
	while(real_bytes > 0)
	{
		current_pos += real_bytes;
		real_bytes = BIO_read(b,(void*)(current_pos),20);
	}
	BIO_free(b);

	len = current_pos-szBuffer_tmp;
#if CERT_DEBUG
	LOG_PRINT_HEX("szBuffer_tmp",szBuffer_tmp,len);
#endif
	/*search pattern char-set*/
	current_pos = szBuffer_tmp;
	len -=12;
	for(i = 0; i < len; i++)
	{
		if(memcmp(current_pos,szSplitStr,12) == 0)
		{
			counter++;
		}
		if(counter == 2)
		{
			break;
		}
		current_pos++;
	}
	
	if(counter != 2)
	{
		//LOG_PRINTF_LIB(1,"pattern char-set not found!\n");
		return ret;
	}

	len = current_pos - szBuffer_tmp - 4;
	memset(szBuffer_bcd,0,sizeof(szBuffer_bcd));
	memcpy(szBuffer_bcd,szBuffer_tmp+4,len);
#if CERT_DEBUG
	LOG_PRINT_HEX("szBuffer_bcd",szBuffer_bcd,len);
#endif
	BIO_printf(out_b,"签名数据\r\n");
	do_hex_dump(send_bio_chars,out_b,(unsigned char *)szBuffer_bcd,len);
	BIO_printf(out_b,"\r\n");

	BIO_printf(out_b,"签名结果\r\n");
	do_hex_dump(send_bio_chars,out_b,(unsigned char *)szSignResult,64);
	BIO_printf(out_b,"\r\n");
	
	ret = ucSM2Validate((const unsigned char *)szBuffer_bcd,
				  		len,
				  		(const unsigned char *)szPubKey,
				  		64,
				  		(const unsigned char *)szSignResult,
				  		64,0);
				  		
	BIO_printf(out_b,"验证结果\r\n");
	if(!ret)
	{
		printf("Cert Sign Verify Ok.\n");
		BIO_printf(out_b,"成功");
	}
	else
	{
		printf("Cert Sign Verify Fail(%d).\n",ret);
		BIO_printf(out_b,"失败");
	}	
	BIO_printf(out_b,"\r\n");

	BIO_free(out_b);
	
	return ret;	
}

