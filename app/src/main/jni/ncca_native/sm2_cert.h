//
// Created by CuncheW1 on 2017/9/12.
//

#ifndef SERVICETESTER_SM2_CERT_H
#define SERVICETESTER_SM2_CERT_H

extern int cert_test_main();
extern void cert_parse(char* pem_cert);
extern int cert_verify(char* pem_cert);

#endif //SERVICETESTER_SM2_CERT_H
