import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ReadFile {

    //Create Vocabulary Map
    public static ArrayList<String> createVoc(File file, int m, int n) throws Exception{
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

        listVoc=listVoc.stream().skip(n).limit(m).collect(Collectors.toCollection(ArrayList::new));

        return listVoc;
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

                    for (String s1: Voc) {
                        mapVec.put(s1, 0);
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
        return listMap;
    }
}
