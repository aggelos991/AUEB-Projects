#include "test.h"
#include <time.h>

pthread_mutex_t lock;  //mutex 
pthread_cond_t cond=PTHREAD_COND_INITIALIZER;

int income,i,j;
int counter=0;
int ticketCounter=1;
int seed,totalCost;
int Ntel=N_tel;

struct timespec t1,t2,t3;

int totalWaiting=0; // variables for
int waitingTime=0; // calculating
int totalServing=0; // waiting &
int servingTime=0; // serving time

int arraySeat[Nseat]; //array for seat plan


void *customer(void *x){
	int id=(int *)x;
	int rc,rc1;
	clock_gettime(CLOCK_REALTIME,&t1);
	rc=pthread_mutex_lock(&lock);
	while( Ntel==0){
		printf("Customer %d didn't find an available caller.Blocked...\n",id);
		rc=pthread_cond_wait(&cond,&lock);
	}
	printf("Customer %d is being served...\n",id);
	Ntel--;
	rc=pthread_mutex_unlock(&lock);

	clock_gettime(CLOCK_REALTIME,&t2);
	waitingTime=t2.tv_sec-t1.tv_sec;
	totalWaiting+=waitingTime;
	
	int tickets= rand_r(&seed)%(Nseathigh+1 -Nseatlow)+Nseatlow;
	srand(time(NULL));
	int t= rand_r(&seed)%(Tseathigh -Tseatlow)+Tseatlow;
	sleep(t);
	printf("\n");
	if(Nseat<ticketCounter+ tickets && ticketCounter<250){
		printf("The reservation has failed because there are not enough tickets left!\n\n");
		clock_gettime(CLOCK_REALTIME,&t3);
		rc=pthread_cond_signal(&cond);
		rc=pthread_mutex_unlock(&lock);
		pthread_exit(NULL);

	}else if(ticketCounter>=250){
		printf("The reservation has failed because theater is full!\n\n");
		clock_gettime(CLOCK_REALTIME,&t3);
		rc=pthread_cond_signal(&cond);
		rc=pthread_mutex_unlock(&lock);
		pthread_exit(NULL);

	}else{

	rc=pthread_mutex_lock(&lock);
	Ntel++;
	int k;

	for(k=ticketCounter-1;k<ticketCounter+tickets-1;k++){
		arraySeat[k]=id;
	}
	
	if(rand() > Pcard*((double) RAND_MAX +1.0)){
			printf("The reservation has been cancelled because the transaction with the credit card was not accepted!\n\n");
			for(int w=ticketCounter-1;w<ticketCounter+tickets-1;w++){
				arraySeat[w]=arraySeat[w+1];
			}
			
			clock_gettime(CLOCK_REALTIME,&t3);
			servingTime=t3.tv_sec-t1.tv_sec;
			totalServing+=servingTime;

			rc=pthread_cond_signal(&cond);
			rc=pthread_mutex_unlock(&lock);
			
			pthread_exit(NULL);
		printf("\n");
	}else{
		counter++;
		int totalCost=tickets*Cseat;
		income+=totalCost;
		printf("The reservation has been completed successfully.\n");
		printf("Number of transaction: #%d\n",counter);
		printf("You have bought %d tickets\n",tickets);
		printf("You have reserved seats ");
		for(i=ticketCounter;i<ticketCounter+tickets;i++){
			printf("#%d ",i);
		}
		ticketCounter+=tickets;
		printf("\n");
		printf("Total cost of transaction : %d euro\n",totalCost);
		printf("\n");
	
		clock_gettime(CLOCK_REALTIME,&t3);
		servingTime=t3.tv_sec-t1.tv_sec;
		totalServing+=servingTime;

	rc=pthread_cond_signal(&cond);
	rc=pthread_mutex_unlock(&lock);
	pthread_exit(NULL);
	}
	}
	rc=pthread_cond_signal(&cond);
	rc=pthread_mutex_unlock(&lock);
	pthread_exit(NULL);
}

int main(int argc,char *argv[]){
	if(argc !=3){
		printf("ERROR: You must enter two arguments!");
		exit(-1);
	}
	int Ncust=atoi(argv[1]);
	seed=atoi(argv[2]);
	
	if(Ncust<0){
		printf("ERROR: You must enter at least one customer!");
		exit(-1);
	} 

	int rc;
	income=0;
	pthread_t threads[Ncust];
	int id[Ncust];
	pthread_mutex_init(&lock,NULL);
	int i=0;
	for(int i=0;i<Ncust;i++){
		id[i]=i+1;
		rc=pthread_create(&threads[i],NULL,customer,id[i]);
	}
	for(int i=0;i<Ncust;i++){
		pthread_join(threads[i],NULL);
	}
	int avgWaiting=totalWaiting/Ncust;
	int avgServing=totalServing/Ncust;
	printf("----------------------------------------------------------------\n");
	printf("----------------------------------------------------------------\n");
	printf("Total Income : %d euro\n",income);
	printf("Average Waiting Time: %d seconds\n",avgWaiting);
	printf("Average Serving Time: %d seconds\n",avgServing);
	printf("\n");
	for(int i=0;i<Nseat;i++){
		for(int j=0;j<Ncust;j++){
			if(arraySeat[i]==id[j]){
				printf("Position %d -> Customer %d\n",i+1,j+1);
				
		}
	}
}	

	printf("\n");
	printf("----------------------------------------------------------------\n");
	printf("----------------------------------------------------------------\n");
	pthread_mutex_destroy(&lock);
	pthread_cond_destroy(&cond);
	return 0;
}
