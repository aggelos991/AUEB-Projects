#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>
#include <unistd.h>

#define Nseat 250
#define N_tel 8
#define Tseatlow 5
#define  Tseathigh 10
#define   Nseatlow 1
#define   Nseathigh 5
#define  Pcard 0.90
#define  Cseat 20

void *customer(void* x);