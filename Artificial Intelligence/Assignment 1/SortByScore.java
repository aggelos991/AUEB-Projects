import java.util.*;

public class SortByScore implements Comparator<State> {

    public int compare(State a,State b){
        return a.getScoreF()-b.getScoreF();
    }
}


