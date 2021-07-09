import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner in = new Scanner(System.in);
        System.out.print("Enter m: ");
        int m = in.nextInt();
        System.out.print("Enter n: ");
        int n = in.nextInt();
        System.out.print("Enter percentage : ");
        int per = in.nextInt();
        System.out.println();
        File dirPos = new File("pos");
        File dirNeg = new File("neg");

        File dirVoc = new File("imdb.vocab");

        File dirPos_dev=new File("test_pos");
        File dirΝeg_dev=new File("test_neg");




        ArrayList<String> Voc = ReadFile.createVoc(dirVoc);

        System.out.println(Voc.size());

        ArrayList<String> Voc2=ReadFile.voc_Cleaning(dirPos,dirNeg,Voc,per,m,n);

        System.out.println(Voc2.size());












        ArrayList<Map> listNegt = ReadFile.createVector(dirNeg, Voc2, per);
        ArrayList<Map> listPost = ReadFile.createVector(dirPos, Voc2, per);



        ArrayList<Map> listNeg_dev=ReadFile.createVector(dirΝeg_dev,Voc2,10);
        ArrayList<Map> listPos_dev=ReadFile.createVector(dirPos_dev,Voc2,10);







        //String cat="Positive";
      /* int counter=0;
        for(Map<String,Integer> map:listPos){
            if(map.get("won't")==1){
                counter++;
            }
        }
        System.out.println(counter);
        counter=0;
        for(Map<String,Integer> map:listNeg){
            if(map.get("won't")==1){
                counter++;
            }
        }
        System.out.println(counter);
*/
        System.out.println("Starting...");
        ID3 id3=new ID3();

        Node root=id3.createTree(listPost,listNegt,Voc2,per,0);



        ArrayList<String> Test=id3.deep_copy(Voc2);
        ArrayList<String> something=new ArrayList<String>();








      /*  System.out.println("Best ig "+root.bestIG);
        System.out.println("Total "+root.total);
        System.out.println("left "+root.left.total);
        System.out.println("right "+root.right.total);

        System.out.println("right.left "+root.right.left.total);
        System.out.println("right.right "+root.right.right.total);
       System.out.println("right.left "+root.right.left.typeBranch);
       */






       double accuracy1=id3.evaluate(listPost,listNegt,root,100);
       double accuracy2=id3.evaluate(listPos_dev,listNeg_dev,root,100);



        System.out.print("accuracy training= "+accuracy1);
        System.out.println("accuracy test= "+accuracy2);
//


    }//end of main
}//end of Main Class

