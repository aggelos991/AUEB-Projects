import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class ID3 {
    Node root;



    public Node createTree(ArrayList<Map> listPos,ArrayList<Map> listNeg,ArrayList<String> Voc,int per,int typebranch){

        Node currentNode = new Node();
            String best_ig = "TERMINAL STATE";
            if(Voc.size()==0){
                currentNode.list_node_Pos=listPos;
                currentNode.list_node_Neg=listNeg;
                currentNode.bestIG=best_ig;
                currentNode.typeBranch=typebranch;
                currentNode.total=listPos.size()+listNeg.size();
                currentNode.per_pos=(double)listPos.size()/currentNode.total;
                currentNode.per_neg=(double)listNeg.size()/currentNode.total;


                if(currentNode.list_node_Pos.size()>currentNode.list_node_Neg.size()){
                    currentNode.category=1;
                }else{
                    currentNode.category=0;
                }

               /* if(typebranch==1){
                    System.out.println("best_ig= "+best_ig+" left= "+currentNode.total+" Class= "+currentNode.category+"Per_pos= "+currentNode.per_pos+"Per_neg= "+currentNode.per_neg);
                }else{
                    System.out.println("best_ig= "+best_ig+" right= "+currentNode.total+" Class= "+currentNode.category+"Per_pos= "+currentNode.per_pos+"Per_neg= "+currentNode.per_neg);
                }*/

                return currentNode;


            }if(listPos.size()==0) {

               currentNode.list_node_Pos = listPos;
               currentNode.list_node_Neg = listNeg;
               currentNode.bestIG = best_ig;
               currentNode.typeBranch = typebranch;
               currentNode.total = listPos.size() + listNeg.size();
               currentNode.per_pos = (double) listPos.size() / currentNode.total;
               currentNode.per_neg = (double) listNeg.size() / currentNode.total;
               currentNode.category=0;

           /* if(typebranch==1){
                System.out.println("best_ig= "+best_ig+" left= "+currentNode.total+" Class= "+currentNode.category+"Per_pos= "+currentNode.per_pos+"Per_neg= "+currentNode.per_neg);
            }else{
                System.out.println("best_ig= "+best_ig+" right= "+currentNode.total+" Class= "+currentNode.category+"Per_pos= "+currentNode.per_pos+"Per_neg= "+currentNode.per_neg);
            }*/

               return currentNode;
           }if(listNeg.size()==0){
            currentNode.list_node_Pos = listPos;
            currentNode.list_node_Neg = listNeg;
            currentNode.bestIG = best_ig;
            currentNode.typeBranch = typebranch;
            currentNode.total = listPos.size() + listNeg.size();
            currentNode.per_pos = (double) listPos.size() / currentNode.total;
            currentNode.per_neg = (double) listNeg.size() / currentNode.total;
            currentNode.category=1;

           /* if(typebranch==1){
                System.out.println("best_ig= "+best_ig+" left= "+currentNode.total+" Class= "+currentNode.category+"Per_pos= "+currentNode.per_pos+"Per_neg= "+currentNode.per_neg);
            }else{
                System.out.println("best_ig= "+best_ig+" right= "+currentNode.total+" Class= "+currentNode.category+"Per_pos= "+currentNode.per_pos+"Per_neg= "+currentNode.per_neg);
            }*/

            return currentNode;

        }




            best_ig = ReadFile.get_best_ig(Voc, listPos, listNeg, per);


            ArrayList<Map> newExistListPos = new ArrayList<>();
            ArrayList<Map> newNotExistListPos = new ArrayList<>();

            ArrayList<Map> newExistListNeg = new ArrayList<>();
            ArrayList<Map> newNotExistListNeg = new ArrayList<>();

            if (root == null) {
                root = new Node();
                currentNode=root;
                root.bestIG = best_ig;
                root.list_node_Pos = listPos;
                root.list_node_Neg = listNeg;
                root.total=listPos.size()+listNeg.size();
                root.per_pos=(double)listPos.size()/root.total;
                root.per_neg=(double)listNeg.size()/root.total;
                root.Voc = Voc;
                System.out.println("best_ig= "+root.bestIG+" root total "+root.total);
                for (Map map1 : root.list_node_Pos) {
                    if ((Integer) map1.get(best_ig) == 1) {
                        newExistListPos.add(map1);

                    } else if ((Integer) map1.get(best_ig) == 0) {
                        newNotExistListPos.add(map1);

                    }

                }


                for (Map map1 : root.list_node_Neg) {
                    if ((Integer) map1.get(best_ig) == 1) {
                        newExistListNeg.add(map1);

                    } else if ((Integer) map1.get(best_ig) == 0) {
                        newNotExistListNeg.add(map1);

                    }
                }
                Voc.remove(best_ig);
                ArrayList<String> newVoc=deep_copy(Voc);
                root.left=createTree(newExistListPos,newExistListNeg,newVoc,per,1);
                newVoc=deep_copy(Voc);
                root.right=createTree(newNotExistListPos,newNotExistListNeg,newVoc,per,0);


            } else {

                currentNode.list_node_Pos =listPos;
                currentNode.list_node_Neg = listNeg;
                currentNode.bestIG=best_ig;
                currentNode.typeBranch=typebranch;
                currentNode.total=listPos.size()+listNeg.size();
                currentNode.per_pos=(double)listPos.size()/currentNode.total;
                currentNode.per_neg=(double)listNeg.size()/currentNode.total;

               /* if(typebranch==1){
                    System.out.println("best_ig= "+best_ig+" left= "+currentNode.total);
                }else{
                    System.out.println("best_ig= "+best_ig+" right= "+currentNode.total);
                }*/


                currentNode.Voc = Voc;


                for (Map map1 : currentNode.list_node_Pos) {
                    if ((Integer) map1.get(best_ig) == 1) {
                        newExistListPos.add(map1);

                    } else if ((Integer) map1.get(best_ig) == 0) {
                        newNotExistListPos.add(map1);

                    }
                }

                for (Map map1 : currentNode.list_node_Neg) {
                    if ((Integer) map1.get(best_ig) == 1) {
                        newExistListNeg.add(map1);

                    } else if ((Integer) map1.get(best_ig) == 0) {
                        newNotExistListNeg.add(map1);

                    }
                }
                Voc.remove(best_ig);

                    ArrayList<String> newVoc=deep_copy(Voc);
                    currentNode.left=createTree(newExistListPos,newExistListNeg,newVoc,per,1);
                    newVoc=deep_copy(Voc);
                    currentNode.right=createTree(newNotExistListPos,newNotExistListNeg,newVoc,per,0);


                }


        return currentNode;
    }



    public ArrayList<String> deep_copy(ArrayList<String> Voc){

        ArrayList<String> newVoc=new ArrayList<String>();
        for(String i:Voc){
            newVoc.add(i);
        }
        return newVoc;
    }





    public double evaluate(ArrayList<Map> list_pos,ArrayList<Map> list_neg,Node root,int per) {
        int TP = 0;
        int TN = 0;
        int FP = 0;
        int FN = 0;
        double accuracy=0;
        boolean isit;

        double counter=0;
    for(Map<String,Integer> map:list_pos) {
        if(counter==per*list_pos.size()/100){break;}
        Node currentNode = root;
        isit = true;
        while (isit) {
            String key = currentNode.bestIG;
            if (map.get(key) == 1) {
                currentNode = currentNode.left;
                if (currentNode.category == 1) {
                    TP++;
                    isit = false;
                } else if (currentNode.category == 0) {
                    FN++;
                    isit = false;
                }
            } else if (map.get(key) == 0) {
                currentNode = currentNode.right;
                if (currentNode.category == 1) {
                    TP++;
                    isit = false;
                } else if (currentNode.category == 0) {
                    FN++;
                    isit = false;
                }
            }
        }
        counter++;
    }





       counter=0;
       for(Map<String,Integer> map:list_neg) {
           if(counter==per*list_neg.size()/100){break;}
           Node currentNode = root;
           isit = true;
           while (isit) {
               String key = currentNode.bestIG;

               if (map.get(key) == 1) {
                   currentNode = currentNode.left;
                   if (currentNode.category == 1) {
                       FP++;
                       isit = false;
                   } else if (currentNode.category == 0) {
                       TN++;
                       isit = false;
                   }
               } else if (map.get(key) == 0) {
                   currentNode = currentNode.right;
                   if (currentNode.category == 1) {
                       FP++;
                       isit = false;
                   } else if (currentNode.category == 0) {
                       TN++;
                       isit = false;
                   }
               }
           }
           counter++;
       }
        System.out.println("TN= "+TN);
        System.out.println("TP= "+TP);
        System.out.println("FN= "+FN);
        System.out.println("FP= "+FP);
        accuracy=(double)(TN+TP)/(TN+TP+FN+FP);
        return accuracy;


    }








}//end of class