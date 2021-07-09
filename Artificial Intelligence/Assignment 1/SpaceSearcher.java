import java.util.ArrayList;
import java.util.Collections;


public class SpaceSearcher
{
    private ArrayList<State> states;
   // private HashSet<State> closedSet;
    private ArrayList<State> openSet;

    SpaceSearcher()
    {
        this.states = new ArrayList<State>();
       // this.closedSet = new HashSet<State>();
        this.openSet=new ArrayList<State>();
    }




    public State A_Star_Algorithm(State initialState) {
        State node_current = null;
        State node_successor = null;
        //put the starting node on the open
        // list (you can leave its f at zero)
        initialState.setScoreF(0);
        openSet.add(initialState);


        while (openSet.size() != 0) {
            // find the node with the least
            // f on
            // the open list
            Collections.sort(openSet, new SortByScore());
            //pop q off the open list
            node_current = openSet.remove(0);

            //generate node_currents's successors and


            //generate node_current's children,add them in the list and set their
            // parents to
            states.addAll(node_current.getChildren());
            for (State child : states) {
                child.setFather(node_current);

            }






            //for each successor
            for (State state : states) {
                node_successor = state;






                node_successor.setScoreG(node_current.getScoreG() + node_successor.getScoreG());
                node_successor.setScoreF(node_successor.getScoreG()+node_successor.heuristic1());
                // if successor is the goal, stop search
                if (node_successor.isTerminal()) {
                    return node_successor;
                }

                //if a node with the same position as
                //successor is in the OPEN list which has a
                //lower f than successor, skip this successor
                for(State something:openSet){
                    if(something.equals(node_successor)){
                        if(something.getScoreF()<node_successor.getScoreF()){
                            break;
                        }
                    }
                }


                //otherwise, add  the node to the open list
                openSet.add(node_successor);

            }
            states=new ArrayList<State>();



        }
        return null;

    }





    //it shows the path from the initial state to the terminal state
    public void showPath(State terminalState){
        ArrayList<State> states=new ArrayList<State>();
        State temp=terminalState;
        while(temp!=null) {
            states.add(temp);
            temp = temp.getFather();
        }
        Collections.reverse(states);

        for(State state:states){
            System.out.println(state.getScoreG());
            state.print();
        }


    }



}