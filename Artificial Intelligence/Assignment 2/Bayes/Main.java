import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter m: ");
        int m = in.nextInt();
        System.out.print("Enter n: ");
        int n = in.nextInt();
        System.out.print("Enter percentage for training : ");
        int per = in.nextInt();
        //System.out.print("Enter percentage for testing : ");
       // int per2 = in.nextInt();
       // System.out.println();

        File trainPos = new File("pos");
        File trainNeg = new File("neg");

        File valPos=new File("dev_set_pos");
        File valNeg=new File("dev_set_neg");

        File testPos=new File("test_pos");
        File testNeg=new File("test_neg");

        File dirVoc = new File("imdb.vocab");
        System.out.println("Making Vocavulary");
        ArrayList<String> Voc = ReadFile.createVoc(dirVoc,m,n);
        System.out.println("Done");

        System.out.println("Creating Positive Vector");
        ArrayList<Map> listNeg = ReadFile.createVector(trainNeg, Voc, per);
        System.out.println("Done");

        System.out.println("Creating Negaive Vector");
        ArrayList<Map> listPos = ReadFile.createVector(trainPos, Voc, per);
        System.out.println("Done");

        System.out.println("Naives MapNeg");
        Map<String,Double> mapNeg=NaiveBayes.training(Voc,listNeg,per);
        System.out.println("Naives MapPos");
        Map<String,Double> mapPos=NaiveBayes.training(Voc,listPos,per);

        System.out.println("---------------------------------------------------------------------");
        System.out.println("Using Training data for validation");
        System.out.println();
      //  double acc1=NaiveBayes.evaluate(mapNeg,mapPos,testNeg,testPos,10);
      //  System.out.println("Dev loss  : "+(1.0-acc1));

        System.out.println("------------------------");
      //  double acc2=NaiveBayes.evaluate(mapNeg,mapPos,trainNeg,trainPos,10);
      //  System.out.println("Dev loss : "+(1.0-acc2));
        //System.out.println("---------------------------------------------------------------------");
        //System.out.println("Using Test data for evaluation");
        //System.out.println();
        //NaiveBayes.evaluate(mapNeg,mapPos,testNeg,testPos,per2);


        for(double thresh=0.1;thresh<1.0;thresh=thresh+0.1) {
             NaiveBayes.Compute_PR(mapNeg, mapPos, testNeg, testPos, 10, thresh);
         }





    }
}
