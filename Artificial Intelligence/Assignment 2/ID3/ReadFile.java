import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ReadFile {

    //Create Vocabulary Map
    public static ArrayList<String> createVoc(File file) throws Exception{
        ArrayList<String> listVoc = new ArrayList<>();
            try {
                FileReader reader = new FileReader(file);
                BufferedReader b = new BufferedReader(reader);
                String str;
                while ((str = b.readLine()) != null) {
                    listVoc.add(str);
                }

                } catch (IOException e) {
                System.out.println("file not found exception");
            }



        return listVoc;
    }

    public static Map<String, Integer> addMap(Map<String, Integer> map1,Map<String, Integer> map2){
        Map<String,Integer> ret = new TreeMap<String,Integer>(map1);
        for (String s : map2.keySet()) {
            if (ret.containsKey(s)) {
                ret.put(s, map2.get(s) + ret.get(s));
            }
        }
        ret=ret.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        return ret;
    }





    public static Map<String, Integer> createVoc2(File dir, int m, int n, int per) throws Exception {
        Map<String, Integer> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        int totalFiles = 12500;
        int counterFile=0;

        //Create dictionary
        for (File file : dir.listFiles()) {
            try {
                if(counterFile==totalFiles*per/100) continue;
                FileReader reader = new FileReader(file);
                BufferedReader b = new BufferedReader(reader);
                String str;
                while ((str = b.readLine()) != null) {
                    String[] words = str.split("\\W+");
                    for (String word : words) {
                        if (map.containsKey(word)) {
                            int counter = map.get(word);
                            counter++;
                            map.put(word, counter);
                        } else {
                            map.put(word, 1);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("file not found exception");
            }
            counterFile++;

        }

        map.remove("br");
        map = map.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).skip(n).limit(m)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        return map;
    }

    //Create vector from Vocabulary

    public static ArrayList<Map> createVector(File dir, ArrayList<String> Voc, int per) {

        ArrayList listMap = new ArrayList();
        int counterFile = 0;
        int totalFiles = 12500;

        for (File file : dir.listFiles()) {
            try {
                if (counterFile == totalFiles * per / 100) break;
                FileReader reader = new FileReader(file);
                BufferedReader b = new BufferedReader(reader);
                String str;

                while ((str = b.readLine()) != null) {
                    String[] words = str.split("\\W+");
                    Map<String, Integer> mapVec = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

                    for (String str1: Voc) {
                        mapVec.put(str1, 0);
                    }
                    ArrayList<String> wordsAsList = new ArrayList<>(Arrays.asList(words));

                    for (String word : wordsAsList) {
                        if (mapVec.containsKey(word)) {
                            if (Voc.contains(word)) {
                                mapVec.put(word, 1);
                            }
                        }
                    }
                    listMap.add(mapVec);
                }
            } catch (IOException e) {
                System.out.println("file not found exception");
            }
            counterFile++;
        }
        //listMap.forEach(System.out::println);
        //System.out.println(listMap.size());

        return listMap;
    }



    public static ArrayList<Map> createVector2(File dir, Map<String, Integer> Voc, int per) {

        ArrayList listMap = new ArrayList();
        int counterFile = 0;
        int totalFiles = 12500;

        for (File file : dir.listFiles()) {
            try {
                if (counterFile == totalFiles * per / 100) break;
                FileReader reader = new FileReader(file);
                BufferedReader b = new BufferedReader(reader);
                String str;

                while ((str = b.readLine()) != null) {
                    String[] words = str.split("\\W+");
                    Map<String, Integer> mapVec = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

                    for (Map.Entry<String, Integer> entry : Voc.entrySet()) {
                        mapVec.put(entry.getKey(), 0);
                    }
                    ArrayList<String> wordsAsList = new ArrayList<>(Arrays.asList(words));
                    ArrayList<String> vocAsList = new ArrayList<>(Voc.keySet());

                    for (String word : wordsAsList) {
                        if (mapVec.containsKey(word)) {
                            if (vocAsList.contains(word)) {
                                mapVec.put(word, 1);
                            }
                        }
                    }
                    listMap.add(mapVec);
                }
            } catch (IOException e) {
                System.out.println("file not found exception");
            }
            counterFile++;
        }
        //listMap.forEach(System.out::println);
        //System.out.println(listMap.size());

        return listMap;
    }
    //Calculate Entropy of every word

    public static double calc_Gain(String key,ArrayList<Map> negMap,ArrayList<Map> posMap,int per) {
        double gain=0.0;
        double p=0.5;
        int totalFiles=25000*per/100;
        double totalEntropy= -p*log2(p)-p*log2(p);

        // counts in how many text files a specific word CAN be found in one of the folder neg
        int counterInNeg = 0;

        // counts in how many text files a specific word CANNOT be found in one of the folder neg
        int counterNotInNeg=0;

        // counts in how many text files a specific word CAN be found in one of the folder neg
        int counterInPos = 0;

        // counts in how many text files a specific word CANNOT be found in one of the folder neg
        int counterNotInPos=0;

        //Search in neg folder

        for (Map<String, Integer> map : negMap) {
            if (map.containsKey(key)) {
                if (map.get(key) == 1) {
                    counterInNeg++;
                }else if(map.get(key)==0){
                    counterNotInNeg++;
                }

            }
        }//END OF LOOP

        //Search in pos folder

        for (Map<String, Integer> map : posMap) {
            if (map.containsKey(key)) {
                if (map.get(key) == 1) {
                    counterInPos++;
                }else if(map.get(key)==0){
                    counterNotInPos++;
                }

            }
        }//END OF LOOP

        //P(C=neg|X=1)
        double p1_neg= (double) counterInNeg / (counterInNeg+counterInPos);

        //P(C=pos|X=1)
        double p1_pos= (double) counterInPos / (counterInNeg+counterInPos);

        //P(C=neg|X=0)
        double p0_neg= (double) counterNotInNeg / (counterNotInNeg+counterNotInPos);

        //P(C=pos|X=0)
        double p0_pos= (double) counterNotInPos / (counterNotInNeg+counterNotInPos);

        // Entropy for X=1
        double entropy1= -p1_pos *log2(p1_pos) -p1_neg*log2(p1_neg);

        // Entropy for X=0;
        double entropy0= -p0_pos *log2(p0_pos) -p0_neg*log2(p0_neg);

        //Probability for X=1
        double p1= (double) (counterInNeg+counterInPos)/totalFiles;

        //Probability for X=0
        double p0= (double) (counterNotInNeg+counterNotInPos)/totalFiles;

        //Total gain
        gain= totalEntropy-(p1*entropy1 + p0*entropy0);

        return gain;

    }

    //Get the word with the best information gain
    public static String get_best_ig(ArrayList<String> Voc,ArrayList<Map> posMap,ArrayList<Map> negMap,int per){
        Map<String, Double> ig = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (String s : Voc) {
            double gain=calc_Gain(s,negMap,posMap,per);
            ig.put(s,gain);
        }
        ig=ig.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        Map.Entry<String,Double> entry = ig.entrySet().iterator().next();
        String key = entry.getKey();

        return key;
    }
    public static double log2(double n) {
        return Math.log10(n) / Math.log10(2);
    }

    public static ArrayList<String> voc_Cleaning(File dirpos,File dirneg,ArrayList<String> Voc,int per,int m,int n) {
        Map<String, Integer> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        int counterFile = 0;
        int totalFiles = dirpos.listFiles().length;

        for (File file : dirpos.listFiles()) {
            try {
                if (counterFile == totalFiles * per / 100) break;
                FileReader reader = new FileReader(file);
                BufferedReader b = new BufferedReader(reader);
                String str;

                while ((str = b.readLine()) != null) {
                    String[] words = str.split("\\W+");
                    for (String word : words) {
                        if (map.containsKey(word)) {
                            int counter = map.get(word);
                            counter++;
                            map.put(word, counter);
                        } else {
                            map.put(word, 1);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("file not found exception");
            }
        }
        counterFile = 0;
        for (File file : dirneg.listFiles()) {
            try {
                if (counterFile == totalFiles * per / 100) break;
                FileReader reader = new FileReader(file);
                BufferedReader b = new BufferedReader(reader);
                String str;

                while ((str = b.readLine()) != null) {
                    String[] words = str.split("\\W+");
                    for (String word : words) {
                        if (map.containsKey(word)) {
                            int counter = map.get(word);
                            counter++;
                            map.put(word, counter);
                        } else {
                            map.put(word, 1);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("file not found exception");
            }

        }

        ArrayList<String> toremove=new ArrayList<String>();

        for(String word:Voc){
            if(!map.containsKey(word)){
                toremove.add(word);
            }
        }
        for(String word:toremove){
            Voc.remove(word);
        }

        Voc=Voc.stream().skip(n).limit(m).collect(Collectors.toCollection(ArrayList::new));

        return Voc;


    }






}



