import java.util.ArrayList;
import java.util.Map;

public class Node {

    protected String bestIG;
    protected ArrayList<Map> list_node_Pos;
    protected ArrayList<Map> list_node_Neg;
    protected ArrayList<String> Voc;
    protected int typeBranch;
    protected Node left;
    protected Node right;
    protected int total;
    protected double per_pos;
    protected double per_neg;
    protected int category=-1;


}
