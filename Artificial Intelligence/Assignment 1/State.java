import java.util.ArrayList;


public class State {
    private int dimension;
    public Person[][] people;
    private int f_score;
    private int g_score;
    private State father = null;
    public int sum;
    public static  int sumall=0;

//Constructor for State-it sets the dimension of the array and it creates a new array
    public State(Person[][] people) {
        this.f_score =0;
        this.g_score=0;
        this.dimension = people[0].length;
        this.people = new Person[2][this.dimension];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < this.dimension; j++) {
                if(people[i][j]!=null) {
                    this.people[i][j] = new Person(people[i][j].getTime(),people[i][j].hasLamp());
                }
            }
        }


    }

    public int getDimension() {
        return dimension;
    }

    public State getFather() { return father; }

    public int getScoreF() {
        return f_score;
    }

    public int getScoreG(){return g_score;}

    public Person[][] getArena() {
        return people;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public void setFather(State father) {
        this.father = father;
    }

    public void setScoreF(int f_score) {
        this.f_score = f_score;
    }

    public void setScoreG(int g_score){this.g_score=g_score;}

    public void setArena(Person[][] arena) {
        this.people = arena;
    }

//this function checks whether the lamp is in the other side not the initial one-if it is,then only one can return
    public boolean canCrossOnePerson() {
        boolean haslamp = false;

        for (int j = 0; j < people[0].length; j++) {
            if (people[1][j] != null && people[1][j].hasLamp()) {
                haslamp = true;
                people[1][j].setLamp(false);
                break;
            }
        }
        return haslamp;
    }

//
    public boolean cancrossTwoPeople() {
        int counter=0;
        for (int i = 0; i < people[0].length; i++) {
            if(people[0][i]!=null){
                counter++;

            }

        }
        if(counter==people[0].length){
            return true;

        }
        boolean haslamp = false;
        for (int j = 0; j < people[0].length; j++) {
            if (people[0][j] != null && people[0][j].hasLamp()) {
                haslamp = true;
                people[0][j].setLamp(false);
                break;
            }
        }
        return haslamp;
    }

//makes all possible states from the current state moving two people from one side to the other(the one we want
    public ArrayList<State> crossTwoPeople() {
        ArrayList<State> list = new ArrayList<State>();


        State state = new State(people);
        boolean istrue = true;
        int counter = 0;
        for (int i = 0; i < state.people[0].length; i++) {

            for (int j = i+1; j < state.people[0].length; j++) {

                if (state.people[0][i] != null) {


                    if (state.people[0][j] != null) {
                        state.swapTwo(i, j);
                        list.add(state);
                        state = new State(people);
                    } else {
                        state=new State(people);


                    }
                } else {
                    state=new State(people);
                    break;
                }

            }
        }
        return list;
    }

    //makes all possible states from the current state moving one person from one side to the other(initial side)
    public ArrayList<State> crossOnePerson() {
        ArrayList<State> list = new ArrayList<State>();
        State state = new State(people);
        boolean istrue = true;
        int counter = 0;

        for (int j = 0; j < state.people[1].length; j++) {
            if (state.people[1][j] != null) {
                state.swapOne(j);
                list.add(state);
                state = new State(people);
            } else {

                state=new State(people);
            }
        }
        return list;
    }

//checks if the state is terminal-that means that heuristic return 0
    public boolean isTerminal() { return heuristic1() == 0; }

    public int heuristic1() {
       int h_n=sumall;
        for (int i = 0; i < people[0].length; i++) {
            if (people[1][i] != null) {

                h_n-=people[1][i].getTime();
            }
        }
        return h_n;
    }


    public ArrayList<State> getChildren() {
        ArrayList<State> children = new ArrayList<State>();
        if (canCrossOnePerson()) {
            children = crossOnePerson();
        } else if (cancrossTwoPeople()) {
            children = crossTwoPeople();
        }
        return children;
    }

    public void swapOne(int i) {
        Person temp = people[0][i];
        people[0][i] = people[1][i];
        people[1][i] = temp;
        people[0][i].setLamp(true);
        setScoreG(people[0][i].getTime());
    }


    public void swapTwo(int i, int j) {
        Person temp = people[0][i];
        people[0][i] = people[1][i];
        people[1][i] = temp;





        Person temp2 = people[0][j];
        people[0][j] = people[1][j];
        people[1][j] = temp2;

        if(people[1][i].compareTo(people[1][j])>0){
            people[1][j].setLamp(true);
        }else{
            people[1][i].setLamp(true);
        }

        setScoreG(people[1][i].max(people[1][j]));
    }

    public void print(){
        System.out.println("------------------------------------");
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < this.dimension; j++) {
                if(people[i][j]!=null){
                    System.out.print(people[i][j].getTime()+" ");
                }else{
                    System.out.print(0+" ");
                }
            }
            System.out.println();
        }
        System.out.println("------------------------------------");
        System.out.println();


    }
    @Override
    public boolean equals(Object obj){



        for(int i=0;i<people.length;i++) {
            for (int j = 0; j < people.length; j++) {
                if (this.people[i][j] != null) {
                    if (((State) obj).people[i][j] != null) {
                        if (this.people[i][j].hasLamp() != ((State) obj).people[i][j].hasLamp()) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    if (((State) obj).people[i][j] != null) {
                        return false;
                    }
                }
            }
        }

        return true;
    }


}









