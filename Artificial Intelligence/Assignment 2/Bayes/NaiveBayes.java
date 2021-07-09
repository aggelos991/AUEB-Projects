import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class NaiveBayes {
    public static Map<String,Double> training(ArrayList<String> Voc,ArrayList<Map> listMap,int per) {
        int totalFiles = 12500 * per / 100;

        //P(C=Neg)=P(C=Pos)=1/2
        double p = 0.5;

        //P(Xi=1 | C=c)

        double p_x;
        Map<String, Double> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        //Counting total appearances of a word in one of the folders
        for (String str: Voc) {
            int counter=0;
            for(Map map1: listMap) {
                if ((Integer)map1.get(str)==1) {
                    counter++;
                }
            }
            p_x=(counter+1.0)/(totalFiles+2.0);
            map.put(str,p_x);
        }

        return map;
    }//end of method

    public static String prediction(File file,Map<String, Double> mapPos,Map<String,Double> mapNeg){
        String str1="";
        double prob_Pos=0.0;
        double prob_Neg=0.0;

        double totalProb_Pos=1.0;
        double totalProb_Neg=1.0;


        //Read a single test file
            try {
                FileReader reader = new FileReader(file);
                BufferedReader b = new BufferedReader(reader);
                String str;
                while ((str = b.readLine()) != null) {
                    String[] words = str.split("\\W+");
                    for (String word : words) {
                        if (mapPos.containsKey(word)) {
                           prob_Pos= mapPos.get(word);
                        } else {
                            //Laplace
                            prob_Pos=0.5;
                        }
                        totalProb_Pos*=prob_Pos;

                        if (mapNeg.containsKey(word)) {
                            prob_Neg= mapNeg.get(word);
                        } else {
                            //Laplace
                            prob_Neg=0.5;
                        }
                        totalProb_Neg*=prob_Neg;
                    }
                }

                if(totalProb_Pos>totalProb_Neg){
                     str1="Positive";
                }else{
                    str1="Negative";
                }
            } catch (IOException e) {
                System.out.println("file not found exception");
            }
            return str1;

    }//end of method

    public static String predictionThresh(File file,Map<String, Double> mapPos,Map<String,Double> mapNeg,double thresh,int i){
        String str1="";
        double prob_Pos=0.0;
        double prob_Neg=0.0;

        double totalProb_Pos=1.0;
        double totalProb_Neg=1.0;

        if(i==0){
            try {
                FileReader reader = new FileReader(file);
                BufferedReader b = new BufferedReader(reader);
                String str;
                while ((str = b.readLine()) != null) {
                    String[] words = str.split("\\W+");
                    for (String word : words) {
                        if (mapPos.containsKey(word)) {
                            prob_Pos= mapPos.get(word);
                        } else {
                            //Laplace
                            prob_Pos=0.5;
                        }
                        totalProb_Pos*=prob_Pos;

                        if (mapNeg.containsKey(word)) {
                            prob_Neg= mapNeg.get(word);
                        } else {
                            //Laplace
                            prob_Neg=0.5;
                        }
                        totalProb_Neg*=prob_Neg;
                    }
                }
                if(totalProb_Neg>thresh){
                    str1="Negative";
                }else{
                    str1="Positive";
                }
            } catch (IOException e) {
                System.out.println("file not found exception");
            }
            return str1;

        }else{
            try {
                FileReader reader = new FileReader(file);
                BufferedReader b = new BufferedReader(reader);
                String str;
                while ((str = b.readLine()) != null) {
                    String[] words = str.split("\\W+");
                    for (String word : words) {
                        if (mapPos.containsKey(word)) {
                            prob_Pos= mapPos.get(word);
                        } else {
                            //Laplace
                            prob_Pos=0.5;
                        }
                        totalProb_Pos*=prob_Pos;

                        if (mapNeg.containsKey(word)) {
                            prob_Neg= mapNeg.get(word);
                        } else {
                            //Laplace
                            prob_Neg=0.5;
                        }
                        totalProb_Neg*=prob_Neg;
                    }
                }

                if(totalProb_Pos>thresh){
                    str1="Positive";
                }else{
                    str1="Negative";
                }
            } catch (IOException e) {
                System.out.println("file not found exception");
            }
            return str1;


        }
        //Read a single test file


    }

    public static double evaluate(Map<String,Double> mapNeg,Map<String,Double> mapPos,File fileNeg,File filePos,int per1){
        int trueNegative=0;
        int falseNegative=0;
        int truePositive=0;
        int falsePositive=0;
        int counterNegFiles=0;
        int counterPosFiles=0;
        int totalFiles = 11250;

        for (File file : fileNeg.listFiles()) {
            if(counterNegFiles==totalFiles*per1/100) continue;
            String predict = NaiveBayes.prediction(file, mapPos, mapNeg);
            if(predict.equals("Negative")){
                trueNegative++;
            }else if(predict.equals("Positive")){
                falsePositive++;
            }
            counterNegFiles++;
        }

        for (File file : filePos.listFiles()) {
            if(counterPosFiles==totalFiles*per1/100) continue;
            String predict = NaiveBayes.prediction(file, mapPos, mapNeg);
            if(predict.equals("Positive")){
                truePositive++;
            }else if(predict.equals("Negative")){
                falseNegative++;
            }
            counterPosFiles++;
        }
        int b=1;
        double accuracy= (double) (truePositive+trueNegative)/(truePositive+trueNegative+falseNegative+falsePositive);
        double precision=(double) truePositive/(truePositive+falsePositive);
        double recall=(double) truePositive/(truePositive+falseNegative);
        double f1= ((b*b+1)*precision*recall)/((b*b)*precision+recall);
        System.out.println("truePositive= "+truePositive);
        System.out.println("trueNegative= "+trueNegative);
        System.out.println("FalseNegative= "+falseNegative);
        System.out.println("FalsePositive= "+falsePositive);
        System.out.println("Accuracy: "+accuracy);
        System.out.println("Precision: "+precision);
        System.out.println("Recall: "+recall);
        System.out.println("F1: "+f1);
        return accuracy;
    }


    public static void Compute_PR(Map<String,Double> mapNeg,Map<String,Double> mapPos,File fileNeg,File filePos,int per1,double thresh){
        int trueNegative=0;
        int falseNegative=0;
        int truePositive=0;
        int falsePositive=0;
        int counterNegFiles=0;
        int counterPosFiles=0;
        int totalFiles = 12500;

        for (File file : fileNeg.listFiles()) {
            if(counterNegFiles==totalFiles*per1/100) continue;
            String predict = NaiveBayes.predictionThresh(file, mapPos, mapNeg,thresh,0);
            if(predict.equals("Negative")){
                trueNegative++;
            }else if(predict.equals("Positive")){
                falsePositive++;
            }
            counterNegFiles++;
        }

        for (File file : filePos.listFiles()) {
            if(counterPosFiles==totalFiles*per1/100) continue;
            String predict = NaiveBayes.predictionThresh(file, mapPos, mapNeg,thresh,1);
            if(predict.equals("Positive")){
                truePositive++;
            }else if(predict.equals("Negative")){
                falseNegative++;
            }
            counterPosFiles++;
        }
        //System.out.println("truePositive= "+truePositive);
       // System.out.println("trueNegative= "+trueNegative);
     //   System.out.println("FalseNegative= "+falseNegative);
       // System.out.println("FalsePositive= "+falsePositive);
        double precision_pos=truePositive/(truePositive+falsePositive);
        System.out.println(precision_pos);
        double recallPos=0;
        if(truePositive+falseNegative==0){
            recallPos=0;

        }else{
            recallPos=truePositive/(truePositive+falseNegative);
        }

        double precision_neg=trueNegative/(trueNegative+falseNegative);
        System.out.println(precision_neg);
        double recallNeg=0;
        if(trueNegative+falsePositive==0){
            recallNeg=0;
        }else{
            recallNeg=(trueNegative/(trueNegative+falsePositive));
        }



        double precision=(precision_pos+precision_neg)/2;
        double recal=(recallPos+recallNeg)/2;

        System.out.println("for threshhold= "+thresh+"precision= "+precision+" recall= "+recal);


    }
}//end of class
