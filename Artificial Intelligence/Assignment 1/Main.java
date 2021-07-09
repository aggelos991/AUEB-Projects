import java.util.Scanner;

public class Main {
    public static void main(String[] args)
    {

        System.out.print("Πληκτρολογησε τον αριθμο των χαρακτηρων: ");
        Scanner in=new Scanner(System.in);
        int N=Integer.parseInt(in.nextLine());

        Person [][] people=new  Person [2][N];


        int n;
        for(n=0;n<N;n++){







            int a=n+1;
            System.out.print("Πληκτρολόγησε τον χρονο του χαρακτήρα "+a+" : ");
            int time=Integer.parseInt(in.nextLine());
            people[0][n]=new Person(time,false);
        }
       in.close();



        int sum=0;
        for(int i=0;i<people[0].length;i++){
            sum+=people[0][i].getTime();
        }

        State.sumall=sum;


//this part of the program starts the implementation of algorithm-it makes an initial state and it proceeds to find a terminal one
        State initialState = new State(people);
        initialState.print();
        SpaceSearcher spaceSearcher = new SpaceSearcher();
        State terminalState = null;




        long start = System.currentTimeMillis();
        terminalState = spaceSearcher.A_Star_Algorithm(initialState);
        long end = System.currentTimeMillis();
        if(terminalState == null || terminalState.getScoreG()>sum)
        {
            System.out.println("Could not find solution");

        }
        else
        {


            spaceSearcher.showPath(terminalState);
            System.out.println("To lynei se xrono: "+terminalState.getScoreG());

        }
        System.out.println("A* with open set search time: " + (double)(end - start) / 1000 + " sec.");
    }


}
