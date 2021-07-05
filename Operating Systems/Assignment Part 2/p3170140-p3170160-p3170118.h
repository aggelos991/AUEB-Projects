#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>
#include <unistd.h>

#define N_tel 8
#define Tseatlow 5
#define Tseathigh 10
#define Nseatlow 1
#define Nseathigh 5
#define Pcard 0.90
#define N_cash 4
#define Nseat 10
#define NzoneA 5
#define NzoneB 10
#define NzoneC 10
#define PzoneA 0.2
#define PzoneB 0.4
#define PzoneC 0.4
#define CzoneA 30
#define CzoneB 25
#define CzoneC 20
#define Tcashlow 2
#define Tcashhigh 4

void *customer(void* x);