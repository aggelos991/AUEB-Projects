#include "p3170140-p3170160-p3170118.h"
#include <time.h>

pthread_mutex_t lock;
pthread_cond_t cond=PTHREAD_COND_INITIALIZER;

int Ntel=N_tel;
int Ncash=N_cash;
int seed,rc,i,rc1;
int arraySeatA[Nseat*NzoneA]; //array  for seat plan for zone A
int arraySeatB[Nseat*NzoneB]; //array  for seat plan for zone B
int arraySeatC[Nseat*NzoneC]; //array  for seat plan for zone C
int counter=0;
int ticketCounterA=0;
int ticketCounterB=0;
int ticketCounterC=0;
double val;
int tickets,totalCost,t,income,t1;

int totalWaiting=0; // variables for
int waitingTime1=0; // calculating
int waitingTime2=0;
int totalServing=0; // waiting &
int servingTime=0; // serving time

struct timespec t2,t3,t4,t5,t6;

void *customer(void *x){
	printf("\n");
	int id=(int*) x;
	clock_gettime(CLOCK_REALTIME,&t2);
	rc=pthread_mutex_lock(&lock);
	while( Ntel==0){
		printf("Customer %d didn't find an available caller.Please wait..\n\n",id);
		rc=pthread_cond_wait(&cond,&lock);
	}
	printf("Customer %d is being served...\n\n",id);
	Ntel--;
	rc=pthread_mutex_unlock(&lock);

	srand(time(NULL));
	t= rand_r(&seed)%(Tseathigh -Tseatlow)+Tseatlow;
	sleep(t);

	clock_gettime(CLOCK_REALTIME,&t4);
	waitingTime1=t4.tv_sec-t2.tv_sec;
	totalWaiting+=waitingTime1;

	if(ticketCounterA+ticketCounterB+ticketCounterC>250){
		printf("The reservation has failed because theater is full!\n\n");

		clock_gettime(CLOCK_REALTIME,&t3);
		servingTime=t3.tv_sec-t2.tv_sec;
		totalServing+=servingTime;

		rc1=pthread_cond_signal(&cond);
		rc1=pthread_mutex_unlock(&lock);

		rc=pthread_mutex_lock(&lock);
		Ntel++;

		rc=pthread_cond_signal(&cond);
		rc=pthread_mutex_unlock(&lock);
		pthread_exit(NULL);

	}else{
		val=(double)rand() / RAND_MAX;

	rc1=pthread_mutex_lock(&lock);
	while(Ncash==0){
		printf("Customer %d didn't find an available cashier.Blocked...\n\n",id);
		rc=pthread_cond_wait(&cond,&lock);
		}


	
	if(val< PzoneA){
		tickets= rand_r(&seed)%(Nseathigh+1 -Nseatlow)+Nseatlow;

		if(ticketCounterA+tickets>=Nseat*NzoneA){
			printf("The reservations has been cancelled because there no consecutive positions left in Zone A\n\n");
							

			clock_gettime(CLOCK_REALTIME,&t3);
			servingTime=t3.tv_sec-t2.tv_sec;
			totalServing+=servingTime;


			rc1=pthread_cond_signal(&cond);
			rc1=pthread_mutex_unlock(&lock);

			rc=pthread_mutex_lock(&lock);
			Ntel++;

			rc=pthread_cond_signal(&cond);
			rc=pthread_mutex_unlock(&lock);
			pthread_exit(NULL);

		}else{
		
		//do stuff for Zone A

clock_gettime(CLOCK_REALTIME,&t5);

	printf("Customer %d is being served by the cashier...\n\n",id);
	Ncash--;
	rc1=pthread_mutex_unlock(&lock);

srand(time(NULL));
t1=rand_r(&seed)%( Tcashhigh -Tcashlow)+Tcashlow;
sleep(t1);

clock_gettime(CLOCK_REALTIME,&t6);
waitingTime2=t6.tv_sec-t5.tv_sec;
	totalWaiting+=waitingTime2;

			for(i=ticketCounterA;i<ticketCounterA+tickets;i++){
				arraySeatA[i]=id;
			}

			if(rand() > Pcard*((double) RAND_MAX +1.0)){
				printf("\n\n");
				printf("The reservation has been cancelled because the transaction with the credit card was not accepted!\n\n");
				for(int w=ticketCounterA;w<ticketCounterA+tickets;w++){
					arraySeatA[w]=arraySeatA[w+1];
				}

				clock_gettime(CLOCK_REALTIME,&t3);
		servingTime=t3.tv_sec-t2.tv_sec;
		totalServing+=servingTime;

				rc1=pthread_mutex_lock(&lock);
				Ncash++;
				rc1=pthread_cond_signal(&cond);
				rc1=pthread_mutex_unlock(&lock);

				rc=pthread_mutex_lock(&lock);
				Ntel++;

				rc=pthread_cond_signal(&cond);
				rc=pthread_mutex_unlock(&lock);
				pthread_exit(NULL);


				printf("\n");

			}else{
				printf("\n");
				counter++;
				totalCost=tickets*CzoneA;
				income+=totalCost;
				printf("The reservation has been completed successfully.\n");
				printf("Number of transaction: #%d\n",counter);
				printf("You have bought %d tickets\n",tickets);
				printf("You have reserved seats ");
				for(i=ticketCounterA+1;i<=ticketCounterA+tickets;i++){
					printf("#%d ",i);
				}
				printf("in Zone A");
				printf("\n");
				printf("Total cost of transaction : %d euro\n",totalCost);
				printf("\n");
ticketCounterA+=tickets;
				clock_gettime(CLOCK_REALTIME,&t3);
		servingTime=t3.tv_sec-t2.tv_sec;
		totalServing+=servingTime;


				rc1=pthread_mutex_lock(&lock);
				Ncash++;
				rc1=pthread_cond_signal(&cond);
				rc1=pthread_mutex_unlock(&lock);

				rc=pthread_mutex_lock(&lock);
				Ntel++;

				rc=pthread_cond_signal(&cond);
				rc=pthread_mutex_unlock(&lock);
				pthread_exit(NULL);

			}			
		}
	}else if(val<PzoneA+PzoneB){
		tickets= rand_r(&seed)%(Nseathigh+1 -Nseatlow)+Nseatlow;

		if(ticketCounterB+tickets>=Nseat*NzoneB ){
			printf("The reservations has been cancelled because there no consecutive positions left in Zone B\n\n");
			
clock_gettime(CLOCK_REALTIME,&t3);
		servingTime=t3.tv_sec-t2.tv_sec;
		totalServing+=servingTime;


			rc1=pthread_cond_signal(&cond);
			rc1=pthread_mutex_unlock(&lock);

			rc=pthread_mutex_lock(&lock);
			Ntel++;


			rc=pthread_cond_signal(&cond);
			rc=pthread_mutex_unlock(&lock);
			pthread_exit(NULL);

		}else{

		//do stuff for Zone B
		

			printf("Customer %d is being served by the cashier...\n\n",id);
			Ncash--;
			rc1=pthread_mutex_unlock(&lock);

			srand(time(NULL));
			t1=rand_r(&seed)%( Tcashhigh -Tcashlow)+Tcashlow;
			sleep(t1);

			for(i=ticketCounterB;i<ticketCounterB+tickets;i++){
				arraySeatB[i]=id;
			}


			if(rand() > Pcard*((double) RAND_MAX +1.0)){
				printf("The reservation has been cancelled because the transaction with the credit card was not accepted!\n\n");
				for(int w=ticketCounterB-1;w<ticketCounterB+tickets-1;w++){
					arraySeatB[w]=arraySeatB[w+1];
				}

			clock_gettime(CLOCK_REALTIME,&t3);
		servingTime=t3.tv_sec-t2.tv_sec;
		totalServing+=servingTime;

				rc1=pthread_mutex_lock(&lock);
				Ncash++;
				rc1=pthread_cond_signal(&cond);
				rc1=pthread_mutex_unlock(&lock);

				rc=pthread_mutex_lock(&lock);
				Ntel++;

				rc=pthread_cond_signal(&cond);
				rc=pthread_mutex_unlock(&lock);
				pthread_exit(NULL);


				printf("\n");

			}else{
				printf("\n");
				counter++;
				totalCost=tickets*CzoneB;
				income+=totalCost;
				printf("The reservation has been completed successfully.\n");
				printf("Number of transaction: #%d\n",counter);
				printf("You have bought %d tickets\n",tickets);
				printf("You have reserved seats ");
				for(i=ticketCounterB+1;i<ticketCounterB+tickets+1;i++){
					printf("#%d ",i);
				}
				printf("in Zone B");
				printf("\n");
				printf("Total cost of transaction : %d euro\n",totalCost);
				printf("\n");
ticketCounterB+=tickets;
				clock_gettime(CLOCK_REALTIME,&t3);
		servingTime=t3.tv_sec-t2.tv_sec;
		totalServing+=servingTime;

				rc1=pthread_mutex_lock(&lock);
				Ncash++;
				rc1=pthread_cond_signal(&cond);
				rc1=pthread_mutex_unlock(&lock);

				rc=pthread_mutex_lock(&lock);
				Ntel++;

				rc=pthread_cond_signal(&cond);
				rc=pthread_mutex_unlock(&lock);
				pthread_exit(NULL);


			}			
		}
	}else if(val<PzoneA+PzoneB+PzoneC){
		tickets= rand_r(&seed)%(Nseathigh+1 -Nseatlow)+Nseatlow;

		if(ticketCounterC+tickets>=Nseat*NzoneC){
			printf("The reservations has been cancelled because there no consecutive positions left in Zone C\n\n");
			
			clock_gettime(CLOCK_REALTIME,&t3);
		servingTime=t3.tv_sec-t2.tv_sec;
		totalServing+=servingTime;


			rc1=pthread_cond_signal(&cond);
			rc1=pthread_mutex_unlock(&lock);

			rc=pthread_mutex_lock(&lock);
			Ntel++;


			rc=pthread_cond_signal(&cond);
			rc=pthread_mutex_unlock(&lock);
			pthread_exit(NULL);

		}else{

		//do stuff for Zone C	


printf("Customer %d is being served by the cashier...\n\n",id);
	Ncash--;
	rc1=pthread_mutex_unlock(&lock);

srand(time(NULL));
t1=rand_r(&seed)%( Tcashhigh -Tcashlow)+Tcashlow;
sleep(t1);

		for(i=ticketCounterC;i<ticketCounterC+tickets;i++){
				arraySeatC[i]=id;
			}
		
		if(rand() > Pcard*((double) RAND_MAX +1.0)){

				printf("The reservation has been cancelled because the transaction with the credit card was not accepted!\n\n");
				for(int w=ticketCounterC;w<ticketCounterC+tickets;w++){
					arraySeatC[w]=arraySeatC[w+1];
				}


clock_gettime(CLOCK_REALTIME,&t3);
		servingTime=t3.tv_sec-t2.tv_sec;
		totalServing+=servingTime;

				rc1=pthread_mutex_lock(&lock);
				Ncash++;
				rc1=pthread_cond_signal(&cond);
				rc1=pthread_mutex_unlock(&lock);

				rc=pthread_mutex_lock(&lock);
				Ntel++;

				rc=pthread_cond_signal(&cond);
				rc=pthread_mutex_unlock(&lock);
				pthread_exit(NULL);



				printf("\n");

			}else{
				printf("\n");
				counter++	;
				totalCost=tickets*CzoneC;
				income+=totalCost;
				printf("The reservation has been completed successfully.\n");
				printf("Number of transaction: #%d\n",counter);
				printf("You have bought %d tickets\n",tickets);
				printf("You have reserved seats ");
				for(i=ticketCounterC+1;i<ticketCounterC+tickets+1;i++){
					printf("#%d ",i);
				}
				printf("in Zone C");
				printf("\n");
				printf("Total cost of transaction : %d euro\n",totalCost);
				printf("\n");
ticketCounterC+=tickets;
				clock_gettime(CLOCK_REALTIME,&t3);
				servingTime=t3.tv_sec-t2.tv_sec;
				totalServing+=servingTime;

				rc1=pthread_mutex_lock(&lock);
				Ncash++;
				rc1=pthread_cond_signal(&cond);
				rc1=pthread_mutex_unlock(&lock);

				rc=pthread_mutex_lock(&lock);
				Ntel++;

				rc=pthread_cond_signal(&cond);
				rc=pthread_mutex_unlock(&lock);
				pthread_exit(NULL);

				}			
			}
		}
	}
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
	pthread_t threads[Ncust];
	int id[Ncust];
	pthread_mutex_init(&lock,NULL);
	for(int i=0;i<Ncust;i++){
		id[i]=i+1;
		rc=pthread_create(&threads[i],NULL,customer,id[i]);
	}
	for(int i=0;i<Ncust;i++){
		pthread_join(threads[i],NULL);
	}
	int avgServing=totalServing/Ncust;
	int avgWaiting=totalWaiting/Ncust;
	printf("----------------------------------------------------------------\n");
	printf("----------------------------------------------------------------\n");
	printf("Total Income : %d euro\n",income);
	printf("Average Waiting Time: %d seconds\n",avgWaiting);
	printf("Average Serving Time: %d seconds\n",avgServing);
printf("\n");
	printf("----------------------------------------------------------------\n");
	printf("----------------------------------------------------------------\n");
	printf("Zone A: \n\n");
for(int i=0;i<Nseat*NzoneA;i++){
		for(int j=0;j<Ncust;j++){
			if(arraySeatA[i]==id[j]){
				printf("Position %d -> Customer %d\n",i+1,j+1);
		}
	}
}	
printf("\n");
printf("Zone B: \n\n");
for(int i=0;i<Nseat*NzoneB;i++){
		for(int j=0;j<Ncust;j++){
			if(arraySeatB[i]==id[j]){
				printf("Position %d -> Customer %d\n",i+1,j+1);
		}
	}
}	
printf("\n");
printf("Zone C: \n\n");
for(int i=0;i<Nseat*NzoneC;i++){
		for(int j=0;j<Ncust;j++){
			if(arraySeatC[i]==id[j]){
				printf("Position %d -> Customer %d\n",i+1,j+1);
		}
	}
}	
printf("\n");


	rc=pthread_mutex_destroy(&lock);
	rc=pthread_cond_destroy(&cond);
	return 0;
	

}