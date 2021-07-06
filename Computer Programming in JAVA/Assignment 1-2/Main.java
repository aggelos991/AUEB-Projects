import java.io.*;
import java.util.*;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;
import javax.swing.JTextArea;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import java.awt.Color;
public class Main{
    public static void main(String args[]){
        Scanner in=new Scanner(System.in);
        final Collections list=new Collections();
        boolean done=false;
        String answer;
        final String[] code = new String[1];
        final String[] description = new String[1];
        final String[] address = new String[1];
        final String ganswer;
        final String[] gcode = new String[1];
        final String[] gdescription = new String[1];
        final String[] gaddress = new String[1];
        final double[] gpricezone = new double[1];
        final double[] gsquaremeter = new double[1];
        String used;
        String edit;

        //Diavasma
        String typeCode = null;
        String typeDes=null;

        String fixedCode=null;
        String fixedDes=null;
        double SquarePrice;
        double fixedExpRent,fixedExpClean,fixedExp;
        Building bChosen=null;
        Expenditure e=null;
        int buildingPos=0;

        //Stathera notFixed
        String notFixedCode=null;
        String notFixedDes=null;
        double notFixedExpRent=0;
        double notFixedExpClean=0;
        double notFixedExp=0;
        //Diavasma
        double notFixedUnitAmount=0;
        //Stathera water
        double wPriceAbove100=0;
        double wPriceLower100=0;
        double wmonthlyFixedCost=0;
        double wPrice=0;
        double wUnitAmount=0;
        Water w=null;
        double wExp=0;
        //Statheta telephone
        double tmonthlyFixedCost=0;
        double ttelephoneTax=0;
        double tPrice=0;
        double tUnitAmount=0;
        Telephone t;
        double tExp=0;
        //Stathera Energy
        double enmonthlyFixedCost=0;
        double enmonthlyERT=0;
        double enTaxes=0;
        double enPrice=0;
        double eUnitAmount=0;
        Energy en;
        double enExp=0;

        //4th menu
        final double[] monthlyCost = {0};

        String rentCode=null;
        String rentDes=null;
        String cleanCode=null;
        String cleanDes=null;
        String tCode=null;
        String tDes=null;
        String wCode=null;
        String wDes=null;
        String enCode=null;
        String enDes=null;

        double priceZone;
        double squareMeter = 0;
        list.addExpenditure(null);
        list.addExpenditure(null);
        list.addExpenditure(null);
        list.addExpenditure(null);
        list.addExpenditure(null);


        //Arxikopoihseis
        String fileName="";
//        System.out.print("Enter the name of the file for the expense's type: ");
//        Scanner nameInput=new Scanner(System.in);
//        fileName=nameInput.nextLine();

        JOptionPane.showMessageDialog(null, "Choose the file for the expense's type: ");
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int returnValue = jfc.showOpenDialog(null);
        // int returnValue = jfc.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            fileName=selectedFile.getAbsolutePath();
        }

        FileReader rf = null;
        try {
            rf = new FileReader(fileName);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        BufferedReader buffer=new BufferedReader(rf);
        String txt="";

        boolean correctFile=true;
        //READ THE FIRST SHIT
        ArrayList<String> expenditure_types = new ArrayList<String>();
        ArrayList<Integer> key_places = new ArrayList<Integer>();
        //Load everything into the arrayList
        try{
            Scanner s = new Scanner(new File(fileName));
            int count=0;
            while (s.hasNext()) {
//                System.out.println(count);

                txt=s.next();


                if(txt.equals("EXPENSE_TYPE_LIST") || txt.equals("expense_type_list") || txt.equals("{") || txt.equals("}") || txt.equals("expense_type") || txt.equals("EXPENSE_TYPE") ){
                    expenditure_types.add(txt);
                }
                if(txt.equals("EXPENSE_TYPE_CODE") || txt.equals("expense_type_code") || txt.equals("EXPENSE_TYPE_DESCR") || txt.equals("expense_type_descr") || txt.equals("TYPE") || txt.equals("type") || txt.equals("price") || txt.equals("PRICE") || txt.equals("price_above_100") || txt.equals("PRICE_ABOVE_100") || txt.equals("price_lower_100") || txt.equals("PRICE_LOWER_100") ||txt.equals("MONTHLY_FIXED_COST") || txt.equals("monthly_fixed_cost") || txt.equals("UNIT_AMOUNT") || txt.equals("unit_amount") || txt.equals("ert") || txt.equals("ERT") ){
                    expenditure_types.add(txt);
                    if(s.hasNext()) {
                        txt = s.next();
                        expenditure_types.add(txt);
                    }else{
                        correctFile=false;
                        System.out.println("There has been detected a wrong structure in your .txt file!");
                    }
                }
                count++;
            }

        }catch (IOException e3) {
            System.out.println("Error accessing input file!");
        }


//        //Debug test
//        for(int i=0;i<expenditure_types.size();i++){
//            System.out.println(expenditure_types.get(i));
//        }


        if( !(expenditure_types.get(0).equals("EXPENSE_TYPE_LIST") || expenditure_types.get(0).equals("expense_type_list")) || !(expenditure_types.get(expenditure_types.size()-1).equals("}")) || !(expenditure_types.get(1).equals("{")) ){
            correctFile=false;
        }
        for(int i=2;i<expenditure_types.size()-1;i++){
            if(expenditure_types.get(i).equals("EXPENSE_TYPE") || expenditure_types.get(i).equals("expense_type") || expenditure_types.get(i).equals("{") || expenditure_types.get(i).equals("}")){
                key_places.add(i);
            }
        }


        int counter=0;
        for(int key_pos=0;key_pos<key_places.size();key_pos++) {
            if(expenditure_types.get(key_places.get(key_pos)).equals("{")) {
                if (!(expenditure_types.get(key_places.get(key_pos)).equals("{") && (expenditure_types.get(key_places.get(key_pos - 1)).equals("EXPENSE_TYPE") || expenditure_types.get(key_places.get(key_pos - 1)).equals("expense_type")) && expenditure_types.get(key_places.get(key_pos + 1)).equals("}"))) {
                    correctFile = false;
                }
            }
        }
        int key_pos=1;
        while(key_pos<key_places.size()){
            String tempCode="",tempDescr="";
            int tempIndex=0;
            double tempPrice=0;
            boolean foundType=false,foundCode=false,foundDescr=false;
            double tempMonthlyFixedCost=0;
            double tempPriceAbove100=0,tempPriceLower100=0;
            double tempUnitAmount=0;
            double tempErt=0;
            double tempTelephoneTax=0;
            if(key_places.get(key_pos)-key_places.get(key_pos-1)!=1 || !(expenditure_types.get(key_places.get(key_pos)).equals("{") && (expenditure_types.get(key_places.get(key_pos-1)).equals("expense_type") || expenditure_types.get(key_places.get(key_pos-1)).equals("EXPENSE_TYPE") ) && expenditure_types.get(key_places.get(key_pos+1)).equals("}"))){

            }else{
                for(int i=key_places.get(key_pos);i<key_places.get(key_pos+1);i++){
                    txt=expenditure_types.get(i);
                    if(txt.equals("EXPENSE_TYPE_CODE") || txt.equals("expense_type_code")){
                        foundCode=true;
                        //vale to next sto temp code
                        tempCode=expenditure_types.get(i+1);
                    }
                    if(txt.equals("EXPENSE_TYPE_DESCR") || txt.equals("expense_type_descr")){
                        foundDescr=true;
                        //vale to next sto temp descr
                        tempDescr=expenditure_types.get(i+1);
                    }
                    if( txt.equals("TYPE") || txt.equals("type") ){
                        foundType=true;
                        //vres index
                        if(expenditure_types.get(i+1).equals("Rent")){
                            tempIndex=0;
                        }else if(expenditure_types.get(i+1).equals("Cleaning")){
                            tempIndex=1;
                        }else if(expenditure_types.get(i+1).equals("Water")){
                            tempIndex=2;
                        }else if( expenditure_types.get(i+1).equals("Telephone")){
                            tempIndex=3;
                        }else if(expenditure_types.get(i+1).equals("Energy")){
                            tempIndex=4;
                        }
                    }
                    if(txt.equals("price") || txt.equals("PRICE")){
                        // vale price sto temp price
                        tempPrice=Double.parseDouble(expenditure_types.get(i+1));
                    }
                    if(txt.equals("price_above_100") || txt.equals("PRICE_ABOVE_100")){
                        //vale price sto temp above price
                        tempPriceAbove100=Double.parseDouble(expenditure_types.get(i+1));
                    }
                    if(txt.equals("price_lower_100") || txt.equals("PRICE_LOWER_100")){
                        //vale price sto temp lower price
                        tempPriceLower100=Double.parseDouble(expenditure_types.get(i+1));
                    }
                    if(txt.equals("MONTHLY_FIXED_COST") || txt.equals("monthly_fixed_cost") ){
                        //vale month sto temp...
                        tempMonthlyFixedCost=Double.parseDouble(expenditure_types.get(i+1));
                    }
                    if(txt.equals("TELEPHONE_TAX") || txt.equals("telephone_tax") ){
                        //vale month sto temp...
                        tempTelephoneTax=Double.parseDouble(expenditure_types.get(i+1));
                    }
                    if(txt.equals("UNIT_AMOUNT") || txt.equals("unit_amount")){
                        //vale unit sto temp ktl..
                        tempUnitAmount=Double.parseDouble(expenditure_types.get(i+1));
                    }
                    if(txt.equals("ERT") || txt.equals("ert")){
                        tempErt=Double.parseDouble(expenditure_types.get(i+1));
                    }
                    if(foundCode==true && foundDescr==true && foundType==true && (expenditure_types.get(i+1).equals("EXPENSE_TYPE") || expenditure_types.get(i+1).equals("expense_type") || expenditure_types.get(i+1).equals("}"))){
                        //Ta pernas mesa
                        if(tempIndex==0){

                            e = new Expenditure(tempCode, tempDescr);
                            list.updateExpenditure(tempIndex, e);
                            rentCode=tempCode;
                            rentDes=tempDescr;
                        }else if(tempIndex==1){

                            e = new Expenditure(tempCode, tempDescr);
                            list.updateExpenditure(tempIndex, e);
                            cleanCode=tempCode;
                            cleanDes=tempDescr;
                        }else if(tempIndex==2){

                            e = new Expenditure(tempCode, tempDescr);
                            list.updateExpenditure(tempIndex, e);
                            wCode=tempCode;
                            wDes=tempDescr;
                            wPriceAbove100=tempPriceAbove100;
                            wPriceLower100=tempPriceLower100;
                            wmonthlyFixedCost=tempMonthlyFixedCost;
                        }else if(tempIndex==3){

                            e = new Expenditure(tempCode, tempDescr);
                            list.updateExpenditure(tempIndex, e);
                            tCode=tempCode;
                            tDes=tempDescr;
                            tmonthlyFixedCost=tempMonthlyFixedCost;
                            tPrice=tempPrice;
                            ttelephoneTax=tempTelephoneTax;
                        }else if(tempIndex==4){

                            e = new Expenditure(tempCode, tempDescr);
                            list.updateExpenditure(tempIndex, e);
                            enCode=tempCode;
                            enDes=tempDescr;
                            enmonthlyERT=tempErt;
                            enmonthlyFixedCost=tempMonthlyFixedCost;
                        }
                        System.out.println("The entity: " +tempDescr+" has been successfully added to the list!");
                    }
                }
            }
            key_pos=key_pos+1;
            counter++;
        }
        try {
            buffer.close();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        if(correctFile==false){
            System.out.println("The file you entered do not have correct structure!");
        }else{
            System.out.println("The file reading completed successfully");
        }


        //READ THE SECOND SHIT



        //Arxikopoihseis
        String befileName="";
//        System.out.print("Enter the name of the file for the building expenses: ");
//        Scanner benameInput=new Scanner(System.in);
//        befileName=benameInput.nextLine();

        JOptionPane.showMessageDialog(null, "Choose the file for the building expense's type: ");
        JFileChooser jfc2 = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int returnValue2 = jfc2.showOpenDialog(null);
        // int returnValue = jfc.showSaveDialog(null);

        if (returnValue2 == JFileChooser.APPROVE_OPTION) {
            File selectedFile2 = jfc2.getSelectedFile();
            befileName=selectedFile2.getAbsolutePath();
        }

        FileReader berf = null;
        try {
            berf = new FileReader(befileName);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        BufferedReader bebuffer=new BufferedReader(berf);
        String betxt="";

        boolean becorrectFile=true;


        ArrayList<String> building_exp = new ArrayList<String>();
        ArrayList<String> be_key_values = new ArrayList<String>();
        ArrayList<Integer> be_key_places = new ArrayList<Integer>();
        //Load everything into the arrayList
        try{
            Scanner s = new Scanner(new File(befileName));
            int count=0;
            while (s.hasNext()) {
//                System.out.println(count);

                betxt=s.next();
                building_exp.add(betxt);
            }
        }catch (IOException e2) {
            System.out.println("Error accessing input file!");
        }

        String value;
        for(int i=0;i<building_exp.size();i++){
            if(building_exp.get(i).equals("BUILDING_DESCR") || building_exp.get(i).equals("building_descr") || building_exp.get(i).equals("ADDRESS") || building_exp.get(i).equals("address")){
                if(i+2<building_exp.size()) {
                    if ( !( building_exp.get(i + 2).equals("BUILDING_CODE") || building_exp.get(i + 2).equals("building_code") || building_exp.get(i + 2).equals("address") || building_exp.get(i + 2).equals("ADDRESS") || building_exp.get(i + 2).equals("BUILDING_DESCR") || building_exp.get(i + 2).equals("building_descr") || building_exp.get(i + 2).equals("SQUAREMETER") || building_exp.get(i + 2).equals("squaremeter") || building_exp.get(i + 2).equals("PRICEZONE") || building_exp.get(i + 2).equals("pricezone") || building_exp.get(i + 2).equals("EXPENSES") || building_exp.get(i + 2).equals("expenses") || building_exp.get(i + 2).equals("}") )) {
                        value = building_exp.get(i+1) + " " + building_exp.get(i+2);
                        building_exp.set(i+1,value);
                        building_exp.remove(i+2);
                    }
                }
            }
        }


        if( !(building_exp.get(0).equals("BUILDING_LIST") || building_exp.get(0).equals("building_list")) || !(building_exp.get(building_exp.size()-1).equals("}")) || !(building_exp.get(1).equals("{")) ){
            becorrectFile=false;
        }

        for(int i=2;i<building_exp.size()-1;i++){
            if(building_exp.get(i).equals("BUILDING") || building_exp.get(i).equals("building") || building_exp.get(i).equals("{") || building_exp.get(i).equals("}") || building_exp.get(i).equals("EXPENSES") || building_exp.get(i).equals("expenses") || building_exp.get(i).equals("EXPENSE") || building_exp.get(i).equals("expense") ){
                be_key_values.add(building_exp.get(i));
                be_key_places.add(i);
            }
        }

        if( be_key_values.get(0).equals("BUILDING") || (be_key_values.get(0).equals("building"))){
            if (be_key_values.get(1).equals("{")) {
            }else{
                correctFile=false;
            }
        }

        int countopen=0;
        int countclose=0;
        for(int i=0;i<be_key_values.size();i++) {
            if(be_key_values.get(i).equals("{")){
                countopen++;
            }
            if(be_key_values.get(i).equals("}")){
                countclose++;
            }
        }

        if(countclose!=countopen){
            becorrectFile=false;
        }

        int becounter=0;
        for(int i=1;i<be_key_values.size();i++){
            String tempBCode="";
            String tempBDescr="";
            String tempAddress="";
            double tempSquareMeter=-1;
            double tempPriceZone=-1;
            double tempCons=-1;
            boolean buildCode=false;
            boolean buildDescr=false;
            boolean buildAddr=false;
            boolean buildSquare=false;
            boolean buildPriceZ=false;
            String beType="";
            String beCode="";
            boolean buildEType=false;
            boolean buildECode=false;
            boolean buildECons=false;


            if(be_key_values.get(i).equals("{")){

                if( !(((be_key_values.get(i-1).equals("BUILDING") || be_key_values.get(i-1).equals("building") ) && (be_key_values.get(i+1).equals("}") || be_key_values.get(i+1).equals("EXPENSES") || be_key_values.get(i+1).equals("expenses"))) || ((be_key_values.get(i-1).equals("EXPENSES") || be_key_values.get(i-1).equals("expenses") ) && (be_key_values.get(i+1).equals("EXPENSE") || be_key_values.get(i+1).equals("expense"))) || ((be_key_values.get(i-1).equals("EXPENSE")||be_key_values.get(i-1).equals("expense"))&&(be_key_values.get(i+1).equals("}"))))) {
                    becorrectFile=false;
                }
                if(((be_key_values.get(i-1).equals("BUILDING") || be_key_values.get(i-1).equals("building") ) && ( be_key_values.get(i+1).equals("}")) ) ){
                    for(int j=be_key_places.get(i);j<be_key_places.get(i+1);j++){
                        if(building_exp.get(j).equals("BUILDING_CODE") || building_exp.get(j).equals("building_code")){
                            tempBCode=building_exp.get(j+1);
                            buildCode=true;
                        }
                        if(building_exp.get(j).equals("BUILDING_DESCR") || building_exp.get(j).equals("building_descr")){
                            tempBDescr=building_exp.get(j+1);
                            buildDescr=true;
                        }
                        if(building_exp.get(j).equals("ADDRESS") || building_exp.get(j).equals("address")){
                            tempAddress=building_exp.get(j+1);
                            buildAddr=true;
                        }
                        if(building_exp.get(j).equals("SQUAREMETER") || building_exp.get(j).equals("squaremeter")){
                            tempSquareMeter=Double.parseDouble(building_exp.get(j+1));
                            buildSquare=true;
                        }
                        if(building_exp.get(j).equals("PRICEZONE")||building_exp.get(j).equals("pricezone")){
                            tempPriceZone=Double.parseDouble(building_exp.get(j+1));
                            buildPriceZ=true;
                        }
                    }
                    if(buildCode==true && buildDescr==true && buildAddr==true && buildSquare==true && buildPriceZ==true){
                        if(becorrectFile==false){
                            System.out.println("Wrong file structure! Could not create the building!");
                        }else{
                            Building b=new Building(tempBCode,tempBDescr,tempAddress,tempPriceZone,tempSquareMeter);
                            list.addBuilding(b);
                            System.out.println("Building " + tempBDescr + " has been created!");
                        }
                        //Create it
//                        System.out.println("BUILDING: "+tempBCode+ " " + tempBDescr + "" + tempAddress +" "+ tempSquareMeter +"" +tempPriceZone);

                    }else{
                        System.out.println("The entity: "+becounter+1 + " cannot be added to the list as a building ");
                    }
                }else if(((be_key_values.get(i-1).equals("BUILDING") || be_key_values.get(i-1).equals("building") ) && (be_key_values.get(i+1).equals("EXPENSES") || be_key_values.get(i+1).equals("expenses")) ) ){
                    for(int j=be_key_places.get(i);j<be_key_places.get(i+1);j++){
                        if(building_exp.get(j).equals("BUILDING_CODE") || building_exp.get(j).equals("building_code")){
                            tempBCode=building_exp.get(j+1);
                            buildCode=true;
                        }
                        if(building_exp.get(j).equals("BUILDING_DESCR") || building_exp.get(j).equals("building_descr")){
                            tempBDescr=building_exp.get(j+1);
                            buildDescr=true;
                        }
                        if(building_exp.get(j).equals("ADDRESS") || building_exp.get(j).equals("address")){
                            tempAddress=building_exp.get(j+1);
                            buildAddr=true;
                        }
                        if(building_exp.get(j).equals("SQUAREMETER") || building_exp.get(j).equals("squaremeter")){
                            tempSquareMeter=Double.parseDouble(building_exp.get(j+1));
                            buildSquare=true;
                        }
                        if(building_exp.get(j).equals("PRICEZONE")||building_exp.get(j).equals("pricezone")){
                            tempPriceZone=Double.parseDouble(building_exp.get(j+1));
                            buildPriceZ=true;
                        }
                    }
                    if(buildCode==true && buildDescr==true && buildAddr==true && buildSquare==true && buildPriceZ==true){
                        //Create it

                        if(becorrectFile==false){
                            System.out.println("Wrong file structure! Could not create the building!");
                        }else{
                            Building b=new Building(tempBCode,tempBDescr,tempAddress,tempPriceZone,tempSquareMeter);
                            list.addBuilding(b);
                            System.out.println("Building " + tempBDescr + " has been created!");
                        }
//                        System.out.println("BUILDING: "+tempBCode+ " " + tempBDescr + "" + tempAddress +" "+ tempSquareMeter +"" +tempPriceZone);
                    }else{
                        System.out.println("The entity: "+becounter+1 + " cannot be added to the list as a building ");
                    }
                    if(buildCode==true && buildDescr==true && buildAddr==true && buildSquare==true && buildPriceZ==true) {
                        int k = i;
                        do{
                            buildEType = false;
                            buildECode = false;
                            buildECons = false;
                            beType = "";
                            beCode = "";
                            tempCons = -1;
                            k=k+3;
                            for (int j = be_key_places.get(k + 1); j < be_key_places.get(k + 2); j++) {
                                if (building_exp.get(j).equals("TYPE") || building_exp.get(j).equals("type")) {
                                    beType = building_exp.get(j + 1);
                                    buildEType = true;
                                }
                                if (building_exp.get(j).equals("EXPENSE_TYPE_CODE") || building_exp.get(j).equals("expense_type_code")) {
                                    beCode = building_exp.get(j + 1);
                                    buildECode = true;
                                }
                                if (building_exp.get(j).equals("CONSUMPTION") || building_exp.get(j).equals("consumption")) {
                                    tempCons = Double.parseDouble(building_exp.get(j + 1));
                                    buildECons = true;
                                }
                            }
                            if (buildECode == true && buildECons == true && buildEType == true) {
                                //Create it
                                boolean expExists=false;
                                for(int l=0;l<expenditure_types.size();l++){
                                    if(beCode.equals(expenditure_types.get(l))){
                                        expExists=true;
                                    }
                                }
                                if(expExists==true) {
                                    if (becorrectFile == false) {
                                        System.out.println("Wrong file structure! Could not create the building expense!");
                                    } else {
                                        bChosen = list.ChooseBuilding(tempBCode);
                                        if (bChosen != null) {
                                            BuildingExpenses be = new BuildingExpenses(bChosen, beCode, tempCons);
                                            list.addBuildingExpenses(be);
                                            System.out.println("Building Expense for: " + tempBCode + " has been created!");

                                        } else {
                                            System.out.println("Could not create the expense of the building: " + tempBDescr + " because the building could not be found in the data");
                                        }
                                    }
                                }
//                                System.out.println("BUILDINGEE: "+tempBCode+ " " + tempBDescr + "" + tempAddress +" "+ tempSquareMeter +" " +tempPriceZone +" "+ beType + " "+ beCode +" " + tempCons);
                            }

                        } while (be_key_values.get(k + 2).equals("}") && (be_key_values.get(k+3).equals("EXPENSE") || be_key_values.get(k+3).equals("expense") ) );
                    }
                }
            }
            becounter++;
        }
        try {
            bebuffer.close();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        if(becorrectFile==false){
            System.out.println("The file you entered do not have correct structure!");
        }




        //Reading is over...

        while(!done){
            System.out.println("1.Add new building");
            System.out.println("2.Add new type of expense of the building you have chosen");
            System.out.println("3.Show all buildings");
            System.out.println("4.Show the expenses of a specific building");
            System.out.println("5.Calculation of the expenses of a specific building");
            System.out.println("6.Calculation of a specific type of expenses of all buildings");
            System.out.println("7.Print the lists in .txt file");
            System.out.println("8.Use GUI");
            System.out.println("0.Exit");
            System.out.println(" > ");
            answer=in.nextLine();
            if(answer.equals("1")){
                System.out.print("Enter the code: ");
                code[0] =in.nextLine();
                System.out.print("\nEnter the description: ");
                description[0] =in.nextLine();
                System.out.print("\nEnter the address: ");
                address[0] =in.nextLine();
                System.out.print("\nEnter the price zone: ");
                priceZone=in.nextDouble();
                System.out.print("\nEnter the square meters: ");
                squareMeter=in.nextDouble();
                Building b=new Building(code[0], description[0], address[0],priceZone,squareMeter);
                list.addBuilding(b);
                in.nextLine();
            }else if(answer.equals("2")){
                System.out.print("\nEnter the code of the building you want to search: ");
                code[0] =in.nextLine();
                bChosen=list.ChooseBuilding(code[0]);
                buildingPos=list.getBuildingPos(bChosen);
                System.out.print("\nEnter 1 if the building is being used or 2 if the building is not used: ");
                used=in.nextLine();
                if(used.equals("2")){
                    System.out.print("\nDo you want to edit the code and description of rent's expenditure(yes/no)? ");
                    edit=in.nextLine();
                    while(rentCode==null || rentDes==null || edit.equals("yes")) {
                        if(rentCode==null && rentDes==null && edit.equals("no")){
                            System.out.println("Sorry , update is necessary");
                        }
                        System.out.print("\nPlease enter the code: ");
                        typeCode = in.nextLine();
                        System.out.print("\nPlease enter the description: ");
                        typeDes = in.nextLine();
                        rentCode=typeCode;
                        rentDes=typeDes;
                        e = new Expenditure(rentCode, rentDes);
                        list.updateExpenditure(0, e);
                        edit="no";
                        if(rentCode==null || rentDes==null){
                            System.out.print("\nCode and description must have a value!!");
                        }
                    }
                    System.out.print("\nDo you want to edit the code and description cleaning's expenditure(yes/no)? ");
                    edit=in.nextLine();
                    while(cleanCode==null || cleanDes==null || edit.equals("yes")) {
                        if(cleanCode==null && cleanDes==null && edit.equals("no")){
                            System.out.println("Sorry , update is necessary");
                        }
                        System.out.print("\nPlease enter the code: ");
                        typeCode = in.nextLine();
                        System.out.print("\nPlease enter the description: ");
                        typeDes = in.nextLine();
                        cleanCode=typeCode;
                        cleanDes=typeDes;
                        e = new Expenditure(cleanCode, cleanDes);
                        list.updateExpenditure(1, e);
                        edit="no";
                        if(cleanCode==null || cleanDes==null){
                            System.out.print("\nCode and description must have a value!!");
                        }
                    }
                    System.out.print("\nEnter the price per square meter for rent: ");
                    SquarePrice= Double.parseDouble(in.nextLine());
                    FixedCost f1=new FixedCost(rentCode,rentDes,SquarePrice,bChosen.getSquareMeter());
                    fixedExpRent=f1.getSquarePrice();
                    System.out.print("\nEnter the price per square meter for cleaning: ");
                    SquarePrice= Double.parseDouble(in.nextLine());
                    FixedCost f2=new FixedCost(cleanCode,cleanDes,SquarePrice,bChosen.getSquareMeter());
                    fixedExpClean=f2.getSquarePrice();
                    BuildingExpenses be= new BuildingExpenses(bChosen,rentCode,fixedExpRent);
                    list.addBuildingExpenses(be);
                    be=new BuildingExpenses(bChosen,cleanCode,fixedExpClean);
                    list.addBuildingExpenses(be);
                    System.out.println("Process has been completed!!");

                }else if(used.equals("1")){

                    //Water
                    System.out.println("Do you want to edit the code and description of the water's expenditure(yes/no)? ");
                    edit=in.nextLine();
                    while(wCode==null || wDes==null || edit.equals("yes")) {
                        if(wCode==null && wDes==null && edit.equals("no")){
                            System.out.println("Sorry , update is necessary");
                        }
                        System.out.print("\nPlease enter the code: ");
                        typeCode = in.nextLine();
                        System.out.print("\nPlease enter the description: ");
                        typeDes = in.nextLine();
                        wCode=typeCode;
                        wDes=typeDes;
                        e = new Expenditure(wCode, wDes);
                        list.updateExpenditure(2, e);
                        edit="no";
                        if(wCode==null || wDes==null){
                            System.out.println("Code and description must have a value!!");
                        }
                    }

                    System.out.println("Do you want to edit the Price above 100 Units for the water(yes/no)? ");
                    edit=in.nextLine();
                    while(wPriceAbove100==0 || edit.equals("yes")){
                        if(wPriceAbove100==0 && edit.equals("no")){
                            System.out.println("Sorry , update is necessary");
                        }
                        System.out.print("\nPlease enter the price: ");
                        wPriceAbove100=Double.parseDouble(in.nextLine());
                        edit="no";
                        if(wPriceAbove100==0){
                            System.out.println("Please enter a number for the Price above 100 Units!");
                        }
                    }
                    System.out.println("Do you want to edit the Price lower than 100 Units for the water(yes/no)? ");
                    edit=in.nextLine();
                    while(wPriceLower100==0 || edit.equals("yes")){
                        if(wPriceLower100==0 && edit.equals("no")){
                            System.out.println("Sorry , update is necessary");
                        }
                        System.out.print("\nPlease enter the price: ");
                        wPriceLower100=Double.parseDouble(in.nextLine());
                        edit="no";
                        if(wPriceLower100==0){
                            System.out.println("Please enter a number for the Price lower than 100 Units!");
                        }
                    }
                    System.out.println("Do you want to edit the Price for the monthly fixed cost for the water(yes/no)? ");
                    edit=in.nextLine();
                    while(wmonthlyFixedCost==0 || edit.equals("yes")){
                        if(wmonthlyFixedCost==0 && edit.equals("no")){
                            System.out.println("Sorry , update is necessary");
                        }
                        System.out.print("\nPlease enter the cost: ");
                        wmonthlyFixedCost=Double.parseDouble(in.nextLine());
                        edit="no";
                        if(wmonthlyFixedCost==0){
                            System.out.println("Please enter a number for the Price of the montly fixed cost!");
                        }
                    }

                    System.out.println("Do you want to edit the Unit Amount of the water(yes/no)?");
                    edit=in.nextLine();
                    if(edit.equals("yes")) {
                        System.out.print("\nPlease enter the Units of the water: ");
                        wUnitAmount = Double.parseDouble(in.nextLine());
                    }
                    notFixedUnitAmount=wUnitAmount;
                    if(notFixedUnitAmount<=100){
                        wPrice=wPriceLower100;
                    }else if(notFixedUnitAmount>100){
                        wPrice=wPriceAbove100;
                    }
                    w=new Water(wCode,wDes,wPrice,notFixedUnitAmount,wmonthlyFixedCost);
                    wExp=w.getWater();

                    BuildingExpenses be = new BuildingExpenses(bChosen, wCode, wExp);
                    list.addBuildingExpenses(be);
                    //Telephone
                    System.out.println("Do you want to edit the code and description of the telephone's expenditure(yes/no)? ");
                    edit=in.nextLine();
                    while(tCode==null || tDes==null || edit.equals("yes")) {
                        if(tCode==null && tDes==null && edit.equals("no")){
                            System.out.println("Sorry , update is necessary");
                        }
                        System.out.print("\nPlease enter the code: ");
                        typeCode = in.nextLine();
                        System.out.print("\nPlease enter the description: ");
                        typeDes = in.nextLine();
                        tCode=typeCode;
                        tDes=typeDes;
                        e = new Expenditure(tCode, tDes);
                        list.updateExpenditure(3, e);
                        edit="no";
                        if(tCode==null || tDes==null){
                            System.out.println("Code and description must have a value!!");
                        }
                    }

                    System.out.println("Do you want to edit the telephone Price(yes/no)? ");
                    edit=in.nextLine();
                    while(tPrice==0 || edit.equals("yes")){
                        if(tPrice==0 && edit.equals("no")){
                            System.out.println("Sorry , update is necessary");
                        }
                        System.out.print("\nPlease enter the price: ");
                        tPrice=Double.parseDouble(in.nextLine());
                        edit="no";
                        if(tPrice==0){
                            System.out.println("Please enter a number for the Price!");
                        }
                    }
                    System.out.println("Do you want to edit the telephone tax(yes/no)? ");
                    edit=in.nextLine();
                    while(ttelephoneTax==0 || edit.equals("yes")){
                        if(ttelephoneTax==0 && edit.equals("no")){
                            System.out.println("Sorry , update is necessary");
                        }
                        System.out.print("\nPlease enter the tax: ");
                        ttelephoneTax=Double.parseDouble(in.nextLine());
                        edit="no";
                        if(ttelephoneTax==0){
                            System.out.println("Please enter a number for the telephone tax!");
                        }
                    }
                    System.out.println("Do you want to edit the telephone monthly fixed cost(yes/no)? ");
                    edit=in.nextLine();
                    while(tmonthlyFixedCost==0 || edit.equals("yes")){
                        if(tmonthlyFixedCost==0 && edit.equals("no")){
                            System.out.println("Sorry , update is necessary");
                        }
                        System.out.print("\nPlease enter the cost: ");
                        tmonthlyFixedCost=Double.parseDouble(in.nextLine());
                        edit="no";
                        if(tmonthlyFixedCost==0){
                            System.out.println("Please enter a number for the telephone monthly fixed cost!");
                        }
                    }

                    System.out.println("Do you want to edit the Unit Amount of the telephone(yes/no)?");
                    edit=in.nextLine();
                    if(edit.equals("yes")) {
                        System.out.print("\nPlease enter the Units of the telephone: ");
                        tUnitAmount = Double.parseDouble(in.nextLine());
                    }
                    notFixedUnitAmount=tUnitAmount;
                    t=new Telephone(tCode,tDes,tPrice,notFixedUnitAmount,tmonthlyFixedCost+ttelephoneTax);
                    tExp=t.getTelephone();
                    be=new BuildingExpenses(bChosen,tCode,tExp);
                    list.addBuildingExpenses(be);

                    //Energy
                    System.out.println("Do you want to edit the code and description of the energy's expenditure(yes/no)? ");
                    edit=in.nextLine();
                    while(enCode==null || enDes==null || edit.equals("yes")) {
                        if(enCode==null && enDes==null && edit.equals("no")){
                            System.out.println("Sorry , update is necessary");
                        }
                        System.out.print("\nPlease enter the code: ");
                        typeCode = in.nextLine();
                        System.out.print("\nPlease enter the description: ");
                        typeDes = in.nextLine();
                        enCode=typeCode;
                        enDes=typeDes;
                        e = new Expenditure(enCode, enDes);
                        list.updateExpenditure(4, e);
                        edit="no";
                        if(enCode==null || enDes==null){
                            System.out.println("Code and description must have a value!!");
                        }
                    }

                    enTaxes=bChosen.getPriceZone()*bChosen.getSquareMeter();

                    System.out.println("Do you want to edit the energy monthly fixed cost(yes/no)? ");
                    edit=in.nextLine();
                    while(enmonthlyFixedCost==0 || edit.equals("yes")){
                        if(enmonthlyFixedCost==0 && edit.equals("no")){
                            System.out.println("Sorry , update is necessary");
                        }
                        System.out.print("\nPlease enter the cost: ");
                        enmonthlyFixedCost=Double.parseDouble(in.nextLine());
                        edit="no";
                        if(enmonthlyFixedCost==0){
                            System.out.println("Please enter a number for the energy cost!");
                        }
                    }

                    System.out.println("Do you want to edit the monthly ERT cost(yes/no)? ");
                    edit=in.nextLine();
                    while(enmonthlyERT==0 || edit.equals("yes")){
                        if(enmonthlyERT==0 && edit.equals("no")){
                            System.out.println("Sorry , update is necessary");
                        }
                        System.out.print("\nPlease enter the cost: ");
                        enmonthlyERT=Double.parseDouble(in.nextLine());
                        edit="no";
                        if(enmonthlyERT==0){
                            System.out.println("Please enter a number for the ERT cost!");
                        }
                    }

                    System.out.println("Do you want to edit the energy Price(yes/no)? ");
                    edit=in.nextLine();
                    while(enPrice==0 || edit.equals("yes")){
                        if(enPrice==0 && edit.equals("no")){
                            System.out.println("Sorry , update is necessary");
                        }
                        System.out.print("\nPlease enter the price: ");
                        enPrice=Double.parseDouble(in.nextLine());
                        edit="no";
                        if(enPrice==0){
                            System.out.println("Please enter a number for the Price!");
                        }
                    }

                    System.out.println("Do you want to edit the Unit Amount of the energy(yes/no)?");
                    edit=in.nextLine();
                    if(edit.equals("yes")) {
                        System.out.print("\nPlease enter the Units of the water: ");
                        eUnitAmount = Double.parseDouble(in.nextLine());
                    }
                    notFixedUnitAmount=eUnitAmount;
                    en=new Energy(enCode,enDes,enPrice,notFixedUnitAmount,enmonthlyFixedCost+enmonthlyERT+enTaxes);
                    enExp=en.getEnergy();
                    be=new BuildingExpenses(bChosen,enCode,enExp);
                    list.addBuildingExpenses(be);


                    System.out.print("\nDo you want to edit the code and description of rent's expenditure(yes/no)? ");
                    edit=in.nextLine();
                    while(rentCode==null || rentDes==null || edit.equals("yes")) {
                        if(rentCode==null && rentDes==null && edit.equals("no")){
                            System.out.println("Sorry , update is necessary");
                        }
                        System.out.print("\nPlease enter the code: ");
                        typeCode = in.nextLine();
                        System.out.print("\nPlease enter the description: ");
                        typeDes = in.nextLine();
                        rentCode=typeCode;
                        rentDes=typeDes;
                        e = new Expenditure(rentCode, rentDes);
                        list.updateExpenditure(0, e);
                        edit="no";
                        if(rentCode==null || rentDes==null){
                            System.out.print("\nCode and description must have a value!!");
                        }
                    }
                    System.out.print("\nDo you want to edit the code and description cleaning's expenditure(yes/no)? ");
                    edit=in.nextLine();
                    while(cleanCode==null || cleanDes==null || edit.equals("yes")) {
                        if(cleanCode==null && cleanDes==null && edit.equals("no")){
                            System.out.println("Sorry , update is necessary");
                        }
                        System.out.print("\nPlease enter the code: ");
                        typeCode = in.nextLine();
                        System.out.print("\nPlease enter the description: ");
                        typeDes = in.nextLine();
                        cleanCode=typeCode;
                        cleanDes=typeDes;
                        e = new Expenditure(cleanCode, cleanDes);
                        list.updateExpenditure(1, e);
                        edit="no";
                        if(cleanCode==null || cleanDes==null){
                            System.out.print("\nCode and description must have a value!!");
                        }
                    }
                    System.out.print("\nEnter the price per square meter for rent: ");
                    SquarePrice= Double.parseDouble(in.nextLine());
                    FixedCost f1=new FixedCost(rentCode,rentDes,SquarePrice,bChosen.getSquareMeter());
                    notFixedExpRent=f1.getSquarePrice();
                    System.out.print("\nEnter the price per square meter for cleaning: ");
                    SquarePrice= Double.parseDouble(in.nextLine());
                    FixedCost f2=new FixedCost(cleanCode,cleanDes,SquarePrice,bChosen.getSquareMeter());
                    notFixedExpClean=f2.getSquarePrice();
                    be=new BuildingExpenses(bChosen,rentCode,notFixedExpRent);
                    list.addBuildingExpenses(be);
                    be=new BuildingExpenses(bChosen,cleanCode,notFixedExpClean);
                    list.addBuildingExpenses(be);
                    System.out.println("Process has been completed!!");
                }

            }else if(answer.equals("3")){
                list.ShowBuilding();
            }else if(answer.equals("5")){
                list.ShowBuilding();
                System.out.print("\nEnter the code of a building: ");
                code[0] =in.nextLine();
                bChosen=list.ChooseBuilding(code[0]);

                monthlyCost[0] =list.getSumOfExpenses(bChosen);
                System.out.println("The monthly cost of the building you have chosen is: " + monthlyCost[0]);

            }else if(answer.equals("4")){
                System.out.print("\nEnter the code of a building: ");
                code[0] =in.nextLine();
                bChosen=list.ChooseBuilding(code[0]);
                list.printExpenses(bChosen);
            }else if(answer.equals("6")){
                list.printTypeList();
                System.out.print("\nEnter the code of the expense that you want: ");
                code[0] =in.nextLine();
                double sumOfExpense=list.getSumOfExpense(code[0]);
                System.out.println("The total sum of the expense you have chosen is: " + sumOfExpense);
            }else if(answer.equals("7")){
                list.printToFile(befileName);
                System.out.println("Printing has been executed!");
            }else if(answer.equals("8")){
                JFrame frame;
                JFrame Tframe;
                JTextField textField_1;


                frame = new JFrame();
                frame.setBounds(100, 100, 713, 576);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().setLayout(null);

                JLabel lblNewLabel = new JLabel("Types");
                lblNewLabel.setBounds(10, 11, 46, 14);
                frame.getContentPane().add(lblNewLabel);







                int step=0;

                JButton btnNewButton_0 = new JButton("Show More");
                final String finalRentCode = rentCode;
                final String finalRentDes = rentDes;
                btnNewButton_0.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){

                        JFrame frame = new JFrame();
                        frame.setVisible(true);
                        frame.setBounds(400, 400, 450, 300);
                        JTextField txtCode0 = new JTextField();
                        txtCode0.setText(finalRentCode);
                        txtCode0.setBounds(165, 36, 89, 20);
                        frame.getContentPane().add(txtCode0);
                        txtCode0.setColumns(10);
                        JTextField txtDes0 = new JTextField();
                        txtDes0.setText(finalRentDes);
                        txtDes0.setBounds(165, 36+25, 89, 20);
                        frame.getContentPane().add(txtDes0);
                        txtDes0.setColumns(10);
                        frame.getContentPane().setLayout(null);
                        JButton btnCalculateExp = new JButton("Calculate Expenses");
                        btnCalculateExp.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                double sumOfExpense=list.getSumOfExpense(finalRentCode);
                                JOptionPane.showMessageDialog(null, "Total expenses: " + Double.toString(sumOfExpense));
                            }
                        });
                        btnCalculateExp.setBounds(125, 227, 159, 23);
                        frame.getContentPane().add(btnCalculateExp);
                    }
                });
                btnNewButton_0.setBounds(588, 36+step, 99, 20);
                frame.getContentPane().add(btnNewButton_0);
                final JTextField txtCode0 = new JTextField();
                txtCode0.setText(rentCode);
                txtCode0.setBounds(165, 36+step, 89, 20);
                frame.getContentPane().add(txtCode0);
                txtCode0.setColumns(10);
                txtCode0.setVisible(false);
                final JTextField txtDes0 = new JTextField();
                txtDes0.setText(rentDes);
                txtDes0.setBounds(285, 36+step, 109, 20);
                frame.getContentPane().add(txtDes0);
                txtDes0.setColumns(10);
                txtDes0.setVisible(false);
                JButton btnNewButton0=new JButton("Rent");
                btnNewButton0.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        txtCode0.setVisible(true);
                        txtDes0.setVisible(true);
                    }
                });

                btnNewButton0.setBounds(0, 36+step, 89, 23);
                step=step+25;
                frame.getContentPane().add(btnNewButton0);

                //next
                JButton btnNewButton_1 = new JButton("Show More");
                final String finalCleanCode = cleanCode;
                final String finalCleanDes = cleanDes;
                btnNewButton_1.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        JFrame frame = new JFrame();
                        frame.setVisible(true);
                        frame.setBounds(100, 100, 450, 300);
                        JTextField txtCode1 = new JTextField();
                        txtCode1.setText(finalCleanCode);
                        txtCode1.setBounds(165, 36, 89, 20);
                        frame.getContentPane().add(txtCode1);
                        txtCode1.setColumns(10);
                        JTextField txtDes1 = new JTextField();
                        txtDes1.setText(finalCleanDes);
                        txtDes1.setBounds(165, 36+25, 89, 20);
                        frame.getContentPane().add(txtDes1);
                        txtDes1.setColumns(10);
                        frame.getContentPane().setLayout(null);
                        JButton btnCalculateExp = new JButton("Calculate Expenses");
                        btnCalculateExp.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                double sumOfExpense=list.getSumOfExpense(finalCleanCode);
                                JOptionPane.showMessageDialog(null, "Total expenses: " + Double.toString(sumOfExpense));
                            }
                        });
                        btnCalculateExp.setBounds(125, 227, 159, 23);
                        frame.getContentPane().add(btnCalculateExp);
                    }
                });
                btnNewButton_1.setBounds(588, 36+step, 99, 20);
                frame.getContentPane().add(btnNewButton_1);
                final JTextField txtCode1 = new JTextField();
                txtCode1.setText(cleanCode);
                txtCode1.setBounds(165, 36+step, 89, 20);
                frame.getContentPane().add(txtCode1);
                txtCode1.setColumns(10);
                txtCode1.setVisible(false);
                final JTextField txtDes1 = new JTextField();
                txtDes1.setText(cleanDes);
                txtDes1.setBounds(285, 36+step, 109, 20);
                frame.getContentPane().add(txtDes1);
                txtDes1.setColumns(10);
                txtDes1.setVisible(false);
                JButton btnNewButton1=new JButton("Cleaning");
                btnNewButton1.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        txtCode1.setVisible(true);
                        txtDes1.setVisible(true);
                    }
                });

                btnNewButton1.setBounds(0, 36+step, 89, 23);
                step=step+25;
                frame.getContentPane().add(btnNewButton1);


                //next
                JButton btnNewButton_2 = new JButton("Show More");
                final String finalWCode = wCode;
                final String finalWDes = wDes;
                final double finalWPriceAbove10 = wPriceAbove100;
                final double finalWPriceLower10 = wPriceLower100;
                final double finalWmonthlyFixedCost = wmonthlyFixedCost;
                final double finalWPrice = wPrice;
                final double finalWUnitAmount = wUnitAmount;
                btnNewButton_2.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        JFrame frame = new JFrame();
                        frame.setVisible(true);
                        frame.setBounds(100, 100, 450, 300);
                        JTextField txtCode2 = new JTextField();
                        txtCode2.setText(finalWCode);
                        txtCode2.setBounds(165, 36, 89, 20);
                        frame.getContentPane().add(txtCode2);
                        txtCode2.setColumns(10);
                        JTextField txtDes2 = new JTextField();
                        txtDes2.setText(finalWDes);
                        txtDes2.setBounds(165, 36+25, 89, 20);
                        frame.getContentPane().add(txtDes2);
                        txtDes2.setColumns(10);
                        JTextField txtwPriceAbove100 = new JTextField();
                        txtwPriceAbove100.setText(Double.toString(finalWPriceAbove10));
                        txtwPriceAbove100.setBounds(165, 36+50, 89, 20);
                        frame.getContentPane().add(txtwPriceAbove100);
                        txtwPriceAbove100.setColumns(10);
                        frame.getContentPane().add(txtwPriceAbove100);
                        txtwPriceAbove100.setColumns(10);
                        JTextField txtwPriceLower100 = new JTextField();
                        txtwPriceLower100.setText(Double.toString(finalWPriceLower10));
                        txtwPriceLower100.setBounds(165, 36+75, 89, 20);
                        frame.getContentPane().add(txtwPriceLower100);
                        txtwPriceLower100.setColumns(10);
                        frame.getContentPane().add(txtwPriceLower100);
                        txtwPriceLower100.setColumns(10);
                        JTextField txtwmonthlyFixedCost = new JTextField();
                        txtwmonthlyFixedCost.setText(Double.toString(finalWmonthlyFixedCost));
                        txtwmonthlyFixedCost.setBounds(165, 36+100, 89, 20);
                        frame.getContentPane().add(txtwmonthlyFixedCost);
                        txtwmonthlyFixedCost.setColumns(10);
                        frame.getContentPane().add(txtwmonthlyFixedCost);
                        txtwmonthlyFixedCost.setColumns(10);
                        JTextField txtwPrice = new JTextField();
                        txtwPrice.setText(Double.toString(finalWPrice));
                        txtwPrice.setBounds(165, 36+125, 89, 20);
                        frame.getContentPane().add(txtwPrice);
                        txtwPrice.setColumns(10);
                        frame.getContentPane().add(txtwPrice);
                        txtwPrice.setColumns(10);
                        JTextField txtwUnitAmount = new JTextField();
                        txtwUnitAmount.setText(Double.toString(finalWUnitAmount));
                        txtwUnitAmount.setBounds(165, 36+150, 89, 20);
                        frame.getContentPane().add(txtwUnitAmount);
                        txtwUnitAmount.setColumns(10);
                        frame.getContentPane().add(txtwUnitAmount);
                        txtwUnitAmount.setColumns(10);
                        frame.getContentPane().setLayout(null);
                        JButton btnCalculateExp = new JButton("Calculate Expenses");
                        btnCalculateExp.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                double sumOfExpense=list.getSumOfExpense(finalWCode);
                                JOptionPane.showMessageDialog(null, "Total expenses: " + Double.toString(sumOfExpense));
                            }
                        });
                        btnCalculateExp.setBounds(125, 227, 159, 23);
                        frame.getContentPane().add(btnCalculateExp);

                    }
                });
                btnNewButton_2.setBounds(588, 36+step, 99, 20);
                frame.getContentPane().add(btnNewButton_2);
                final JTextField txtCode2 = new JTextField();
                txtCode2.setText(wCode);
                txtCode2.setBounds(165, 36+step, 89, 20);
                frame.getContentPane().add(txtCode2);
                txtCode2.setColumns(10);
                txtCode2.setVisible(false);
                final JTextField txtDes2 = new JTextField();
                txtDes2.setText(wDes);
                txtDes2.setBounds(285, 36+step, 109, 20);
                frame.getContentPane().add(txtDes2);
                txtDes2.setColumns(10);
                txtDes2.setVisible(false);
                JButton btnNewButton2=new JButton("Water");
                btnNewButton2.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        txtCode2.setVisible(true);
                        txtDes2.setVisible(true);
                    }
                });

                btnNewButton2.setBounds(0, 36+step, 89, 23);
                step=step+25;
                frame.getContentPane().add(btnNewButton2);


                //next
                JButton btnNewButton_3 = new JButton("Show More");

                final String finalTCode = tCode;
                final String finalTDes = tDes;
                final double finalTtelephoneTax = ttelephoneTax;
                final double finalTmonthlyFixedCost = tmonthlyFixedCost;
                final double finalTPrice = tPrice;
                final double finalTUnitAmount = tUnitAmount;
                btnNewButton_3.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        JFrame frame = new JFrame();
                        frame.setVisible(true);
                        frame.setBounds(100, 100, 450, 300);
                        JTextField txtCode3 = new JTextField();
                        txtCode3.setText(finalTCode);
                        txtCode3.setBounds(165, 36, 89, 20);
                        frame.getContentPane().add(txtCode3);
                        txtCode3.setColumns(10);
                        JTextField txtDes3 = new JTextField();
                        txtDes3.setText(finalTDes);
                        txtDes3.setBounds(165, 36+25, 89, 20);
                        frame.getContentPane().add(txtDes3);
                        txtDes3.setColumns(10);
                        JTextField txttelephoneTax = new JTextField();
                        txttelephoneTax.setText(Double.toString(finalTtelephoneTax));
                        txttelephoneTax.setBounds(165, 36+50, 89, 20);
                        frame.getContentPane().add(txttelephoneTax);
                        txttelephoneTax.setColumns(10);
                        frame.getContentPane().add(txttelephoneTax);
                        txttelephoneTax.setColumns(10);
                        JTextField txttmonthlyFixedCost = new JTextField();
                        txttmonthlyFixedCost.setText(Double.toString(finalTmonthlyFixedCost));
                        txttmonthlyFixedCost.setBounds(165, 36+75, 89, 20);
                        frame.getContentPane().add(txttmonthlyFixedCost);
                        txttmonthlyFixedCost.setColumns(10);
                        frame.getContentPane().add(txttmonthlyFixedCost);
                        txttmonthlyFixedCost.setColumns(10);
                        JTextField txttPrice = new JTextField();
                        txttPrice.setText(Double.toString(finalTPrice));
                        txttPrice.setBounds(165, 36+100, 89, 20);
                        frame.getContentPane().add(txttPrice);
                        txttPrice.setColumns(10);
                        frame.getContentPane().add(txttPrice);
                        txttPrice.setColumns(10);
                        JTextField txttUnitAmount = new JTextField();
                        txttUnitAmount.setText(Double.toString(finalTUnitAmount));
                        txttUnitAmount.setBounds(165, 36+125, 89, 20);
                        frame.getContentPane().add(txttUnitAmount);
                        txttUnitAmount.setColumns(10);
                        frame.getContentPane().add(txttUnitAmount);
                        txttUnitAmount.setColumns(10);
                        frame.getContentPane().setLayout(null);
                        JButton btnCalculateExp = new JButton("Calculate Expenses");
                        btnCalculateExp.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                double sumOfExpense=list.getSumOfExpense(finalTCode);
                                JOptionPane.showMessageDialog(null, "Total expenses: " + Double.toString(sumOfExpense));
                            }
                        });
                        btnCalculateExp.setBounds(125, 227, 159, 23);
                        frame.getContentPane().add(btnCalculateExp);
                    }
                });
                btnNewButton_3.setBounds(588, 36+step, 99, 20);
                frame.getContentPane().add(btnNewButton_3);
                final JTextField txtCode3 = new JTextField();
                txtCode3.setText(tCode);
                txtCode3.setBounds(165, 36+step, 89, 20);
                frame.getContentPane().add(txtCode3);
                txtCode3.setColumns(10);
                txtCode3.setVisible(false);
                final JTextField txtDes3 = new JTextField();
                txtDes3.setText(tDes);
                txtDes3.setBounds(285, 36+step, 109, 20);
                frame.getContentPane().add(txtDes3);
                txtDes3.setColumns(10);
                txtDes3.setVisible(false);
                JButton btnNewButton3=new JButton("Telephone");
                btnNewButton3.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        txtCode3.setVisible(true);
                        txtDes3.setVisible(true);
                    }
                });

                btnNewButton3.setBounds(0, 36+step, 89, 23);
                step=step+25;
                frame.getContentPane().add(btnNewButton3);


                //next
                JButton btnNewButton_4 = new JButton("Show More");
                final String finalEnCode = enCode;
                final String finalEnDes = enDes;
                final double finalEnmonthlyERT = enmonthlyERT;
                final double finalEnTaxes = enTaxes;
                final double finalEnmonthlyFixedCost = enmonthlyFixedCost;
                final double finalEnPrice = enPrice;
                final double finalEUnitAmount = eUnitAmount;
                btnNewButton_4.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        JFrame frame = new JFrame();
                        frame.setVisible(true);
                        frame.setBounds(100, 100, 450, 300);
                        JTextField txtCode4 = new JTextField();
                        txtCode4.setText(finalEnCode);
                        txtCode4.setBounds(165, 36, 89, 20);
                        frame.getContentPane().add(txtCode4);
                        txtCode4.setColumns(10);
                        JTextField txtDes4 = new JTextField();
                        txtDes4.setText(finalEnDes);
                        txtDes4.setBounds(165, 36+25, 89, 20);
                        frame.getContentPane().add(txtDes4);
                        txtDes4.setColumns(10);
                        JTextField txtenmonthlyERT = new JTextField();
                        txtenmonthlyERT.setText(Double.toString(finalEnmonthlyERT));
                        txtenmonthlyERT.setBounds(165, 36+50, 89, 20);
                        frame.getContentPane().add(txtenmonthlyERT);
                        txtenmonthlyERT.setColumns(10);
                        frame.getContentPane().add(txtenmonthlyERT);
                        txtenmonthlyERT.setColumns(10);
                        JTextField txtenTaxes = new JTextField();
                        txtenTaxes.setText(Double.toString(finalEnTaxes));
                        txtenTaxes.setBounds(165, 36+75, 89, 20);
                        frame.getContentPane().add(txtenTaxes);
                        txtenTaxes.setColumns(10);
                        frame.getContentPane().add(txtenTaxes);
                        txtenTaxes.setColumns(10);
                        JTextField txtenmonthlyFixedCost = new JTextField();
                        txtenmonthlyFixedCost.setText(Double.toString(finalEnmonthlyFixedCost));
                        txtenmonthlyFixedCost.setBounds(165, 36+100, 89, 20);
                        frame.getContentPane().add(txtenmonthlyFixedCost);
                        txtenmonthlyFixedCost.setColumns(10);
                        frame.getContentPane().add(txtenmonthlyFixedCost);
                        txtenmonthlyFixedCost.setColumns(10);
                        JTextField txtenPrice = new JTextField();
                        txtenPrice.setText(Double.toString(finalEnPrice));
                        txtenPrice.setBounds(165, 36+125, 89, 20);
                        frame.getContentPane().add(txtenPrice);
                        txtenPrice.setColumns(10);
                        frame.getContentPane().add(txtenPrice);
                        txtenPrice.setColumns(10);
                        JTextField txtenUnitAmount = new JTextField();
                        txtenUnitAmount.setText(Double.toString(finalEUnitAmount));
                        txtenUnitAmount.setBounds(165, 36+150, 89, 20);
                        frame.getContentPane().add(txtenUnitAmount);
                        txtenUnitAmount.setColumns(10);
                        frame.getContentPane().add(txtenUnitAmount);
                        txtenUnitAmount.setColumns(10);
                        frame.getContentPane().setLayout(null);
                        JButton btnCalculateExp = new JButton("Calculate Expenses");
                        btnCalculateExp.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                double sumOfExpense=list.getSumOfExpense(finalEnCode);
                                JOptionPane.showMessageDialog(null, "Total expenses: " + Double.toString(sumOfExpense));
                            }
                        });
                        btnCalculateExp.setBounds(125, 227, 159, 23);
                        frame.getContentPane().add(btnCalculateExp);
                    }
                });
                btnNewButton_4.setBounds(588, 36+step, 99, 20);
                frame.getContentPane().add(btnNewButton_4);
                final JTextField txtCode4 = new JTextField();
                txtCode4.setText(enCode);
                txtCode4.setBounds(165, 36+step, 89, 20);
                frame.getContentPane().add(txtCode4);
                txtCode4.setColumns(10);
                txtCode4.setVisible(false);
                final JTextField txtDes4 = new JTextField();
                txtDes4.setText(enDes);
                txtDes4.setBounds(285, 36+step, 109, 20);
                frame.getContentPane().add(txtDes4);
                txtDes4.setColumns(10);
                txtDes4.setVisible(false);
                JButton btnNewButton4=new JButton("Energy");
                btnNewButton4.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        txtCode4.setVisible(true);
                        txtDes4.setVisible(true);
                    }
                });

                btnNewButton4.setBounds(0, 36+step, 89, 23);
                step=step+25;
                frame.getContentPane().add(btnNewButton4);

                //next
                JSeparator separator = new JSeparator();
                separator.setForeground(Color.BLACK);
                separator.setBackground(Color.BLACK);
                separator.setBounds(10, 222, 677, 20);
                frame.getContentPane().add(separator);
                //next

                JLabel lblNewLabel_1 = new JLabel("Buildings");
                lblNewLabel_1.setBounds(10, 264, 76, 14);
                frame.getContentPane().add(lblNewLabel_1);

                JButton btnNewButton = new JButton("Save changes to .txt");
                final String finalBefileName = befileName;
                btnNewButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        list.printToFile(finalBefileName);

                    }
                });
                btnNewButton.setBounds(497, 260, 190, 23);
                frame.getContentPane().add(btnNewButton);


                int buildingNum=list.getBuildingsLength(); //allazei auto
                for(int i=0;i<buildingNum;i++){
                    final JTextField txtBcode = new JTextField();
                    txtBcode.setText(list.getBuilding(i).getCode());
                    txtBcode.setBounds(165, 289+i*25, 89, 20);
                    frame.getContentPane().add(txtBcode);
                    txtBcode.setColumns(10);
                    txtBcode.setVisible(false);
                    final JTextField txtBdes = new JTextField();
                    txtBdes.setText(list.getBuilding(i).getDescription());
                    txtBdes.setBounds(285, 289+i*25, 109, 20);
                    txtBdes.setVisible(false);
                    frame.getContentPane().add(txtBdes);
                    txtBdes.setColumns(10);
                    JButton btnNewButtonBuilding = new JButton(list.getBuilding(i).getDescription());
                    btnNewButtonBuilding.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            txtBcode.setVisible(true);
                            txtBdes.setVisible(true);
                        }
                    });
                    btnNewButtonBuilding.setBounds(0, 289+i*25, 89, 23);
                    frame.getContentPane().add(btnNewButtonBuilding);
                    JButton btnShowMore = new JButton("Show More");
                    final int finalI = i;
                    btnShowMore.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            JFrame frame = new JFrame();
                            frame.setVisible(true);
                            frame.setBounds(100, 100, 450, 300);
                            JTextField Bcode = new JTextField();
                            Bcode.setText(list.getBuilding(finalI).getCode());
                            Bcode.setBounds(165, 36, 89, 20);
                            frame.getContentPane().add(Bcode);
                            Bcode.setColumns(10);
                            JTextField Bdes = new JTextField();
                            Bdes.setText(list.getBuilding(finalI).getDescription());
                            Bdes.setBounds(165, 36+25, 89, 20);
                            frame.getContentPane().add(Bdes);
                            Bdes.setColumns(10);
                            JTextField Baddress = new JTextField();
                            Baddress.setText(list.getBuilding(finalI).getAddress());
                            Baddress.setBounds(165, 36+50, 89, 20);
                            frame.getContentPane().add(Baddress);
                            Baddress.setColumns(10);
                            frame.getContentPane().add(Baddress);
                            Baddress.setColumns(10);
                            JTextField BPriceZone = new JTextField();
                            BPriceZone.setText(Double.toString(list.getBuilding(finalI).getPriceZone()));
                            BPriceZone.setBounds(165, 36+75, 89, 20);
                            frame.getContentPane().add(BPriceZone);
                            BPriceZone.setColumns(10);
                            frame.getContentPane().add(BPriceZone);
                            BPriceZone.setColumns(10);
                            JTextField BSquareMeter = new JTextField();
                            BSquareMeter.setText(Double.toString(list.getBuilding(finalI).getSquareMeter()));
                            BSquareMeter.setBounds(165, 36+100, 89, 20);
                            frame.getContentPane().add(BSquareMeter);
                            BSquareMeter.setColumns(10);
                            frame.getContentPane().add(BSquareMeter);
                            BSquareMeter.setColumns(10);
                            frame.getContentPane().setLayout(null);
                            JButton btnNewButtonInsert = new JButton("Insert");
                            btnNewButtonInsert.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    JFrame frame = new JFrame();
                                    frame.setBounds(100, 100, 450, 300);
                                    frame.getContentPane().setLayout(null);

                                    JLabel lblNewLabel = new JLabel("Code");
                                    lblNewLabel.setBounds(36, 39, 46, 14);
                                    frame.getContentPane().add(lblNewLabel);

                                    JLabel lblNewLabel_1 = new JLabel("Description");
                                    lblNewLabel_1.setBounds(36, 82, 46, 14);
                                    frame.getContentPane().add(lblNewLabel_1);

                                    JLabel lblNewLabel_2 = new JLabel("Address");
                                    lblNewLabel_2.setBounds(36, 128, 46, 14);
                                    frame.getContentPane().add(lblNewLabel_2);

                                    JLabel lblNewLabel_3 = new JLabel("Price Zone");
                                    lblNewLabel_3.setBounds(36, 180, 46, 14);
                                    frame.getContentPane().add(lblNewLabel_3);

                                    JLabel lblNewLabel_4 = new JLabel("Square Meters");
                                    lblNewLabel_4.setBounds(36, 223, 46, 14);
                                    frame.getContentPane().add(lblNewLabel_4);

                                    final JTextField textField = new JTextField();
                                    textField.setBounds(174, 36, 86, 20);
                                    frame.getContentPane().add(textField);
                                    textField.setColumns(10);

                                    final JTextField textField_1 = new JTextField();
                                    textField_1.setBounds(174, 79, 86, 20);
                                    frame.getContentPane().add(textField_1);
                                    textField_1.setColumns(10);

                                    final JTextField textField_2 = new JTextField();
                                    textField_2.setBounds(174, 125, 86, 20);
                                    frame.getContentPane().add(textField_2);
                                    textField_2.setColumns(10);

                                    final JTextField textField_3 = new JTextField();
                                    textField_3.setBounds(174, 177, 86, 20);
                                    frame.getContentPane().add(textField_3);
                                    textField_3.setColumns(10);

                                    final JTextField textField_4 = new JTextField();
                                    textField_4.setBounds(174, 220, 86, 20);
                                    frame.getContentPane().add(textField_4);
                                    textField_4.setColumns(10);


                                    JButton btnNewButton = new JButton("OK");
                                    btnNewButton.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e) {
                                            gcode[0] =textField.getText();
                                            gdescription[0] =textField_1.getText();
                                            gaddress[0] =textField_2.getText();
                                            gpricezone[0] =Double.parseDouble(textField_3.getText());
                                            gsquaremeter[0] =Double.parseDouble(textField_4.getText());
                                            Building b=new Building(gcode[0], gdescription[0], gaddress[0],gpricezone[0],gsquaremeter[0]);
                                            list.addBuilding(b);
                                        }
                                    });
                                    btnNewButton.setBounds(335, 11, 89, 23);
                                    frame.getContentPane().add(btnNewButton);
                                    frame.setVisible(true);
                                }
                            });
                            btnNewButtonInsert.setBounds(265, 36, 89, 20);
                            frame.getContentPane().add(btnNewButtonInsert);
                            JButton btnNewButtonDelete = new JButton("Delete");
                            btnNewButtonDelete.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    list.removeBuilding(list.getBuilding(finalI));
                                }
                            });
                            btnNewButtonDelete.setBounds(265, 36+25, 89, 20);
                            frame.getContentPane().add(btnNewButtonDelete);
                            JButton btnNewButtonSum = new JButton("Sum");
                            btnNewButtonSum.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    monthlyCost[0] =list.getSumOfExpenses(list.getBuilding(finalI));
                                    JOptionPane.showMessageDialog(null, "Total expenses: " + Double.toString(monthlyCost[0]));
                                }
                            });
                            btnNewButtonSum.setBounds(265, 36+50, 89, 20);
                            frame.getContentPane().add(btnNewButtonSum);
                            JButton btnNewButtonExpenseManagement = new JButton("Expense Management");
                            btnNewButtonExpenseManagement.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    final BuildingExpenses[] expTypes=list.getBuildingExpense(list.getBuilding(finalI));
                                    JFrame frame = new JFrame();
                                    frame.setBounds(400, 400, 450, 300);
                                    for(int i=0;i<expTypes.length;i++){
                                        JButton btnNewButtonTypeExpense = new JButton(expTypes[i].getTypeOfExpense());
                                        final int finalI1 = i;
                                        btnNewButtonTypeExpense.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent e) {
                                                JFrame frame = new JFrame();
                                                frame.setVisible(true);
                                                frame.setBounds(100, 100, 450, 300);
                                                JTextField BEBuilding = new JTextField();
                                                BEBuilding.setText(expTypes[finalI1].getBuilding().getDescription());
                                                BEBuilding.setBounds(165, 36, 89, 20);
                                                frame.getContentPane().add(BEBuilding);
                                                BEBuilding.setColumns(10);
                                                JTextField BETE = new JTextField();
                                                BETE.setText(expTypes[finalI1].getTypeOfExpense());
                                                BETE.setBounds(165, 36+25, 89, 20);
                                                frame.getContentPane().add(BETE);
                                                BETE.setColumns(10);
                                                JTextField BEMU = new JTextField();
                                                BEMU.setText(Double.toString(expTypes[finalI1].getMonthlyUse()));
                                                BEMU.setBounds(165, 36+50, 89, 20);
                                                frame.getContentPane().add(BEMU);
                                                BEMU.setColumns(10);
                                                frame.getContentPane().add(BEMU);
                                                BEMU.setColumns(10);
                                                frame.getContentPane().setLayout(null);



                                                JButton btnNewButtonDelete = new JButton("Delete");
                                                btnNewButtonDelete.addActionListener(new ActionListener() {
                                                    public void actionPerformed(ActionEvent e) {
                                                        list.removeBuildingExpense(expTypes[finalI1]);
                                                    }
                                                });
                                                btnNewButtonDelete.setBounds(265, 36, 89, 20);
                                                frame.getContentPane().add(btnNewButtonDelete);

                                                JButton btnNewButtonChange = new JButton("Change");
                                                btnNewButtonChange.addActionListener(new ActionListener() {
                                                    public void actionPerformed(ActionEvent e) {
                                                        JFrame frame = new JFrame();
                                                        frame.setBounds(100, 100, 450, 300);
                                                        frame.getContentPane().setLayout(null);

                                                        JLabel lblNewLabel = new JLabel("Code of building");
                                                        lblNewLabel.setBounds(36, 39, 46, 14);
                                                        frame.getContentPane().add(lblNewLabel);

                                                        JLabel lblNewLabel_1 = new JLabel("TypeOfExpense");
                                                        lblNewLabel_1.setBounds(36, 82, 46, 14);
                                                        frame.getContentPane().add(lblNewLabel_1);

                                                        JLabel lblNewLabel_2 = new JLabel("MonthlyCost");
                                                        lblNewLabel_2.setBounds(36, 128, 46, 14);
                                                        frame.getContentPane().add(lblNewLabel_2);


                                                        final JTextField textField = new JTextField();
                                                        textField.setBounds(174, 36, 86, 20);
                                                        frame.getContentPane().add(textField);
                                                        textField.setColumns(10);

                                                        final JTextField textField_1 = new JTextField();
                                                        textField_1.setBounds(174, 79, 86, 20);
                                                        frame.getContentPane().add(textField_1);
                                                        textField_1.setColumns(10);

                                                        final JTextField textField_2 = new JTextField();
                                                        textField_2.setBounds(174, 125, 86, 20);
                                                        frame.getContentPane().add(textField_2);
                                                        textField_2.setColumns(10);


                                                        JButton btnNewButton = new JButton("OK");
                                                        btnNewButton.addActionListener(new ActionListener() {
                                                            public void actionPerformed(ActionEvent e) {
                                                                gcode[0] =textField.getText();
                                                                Building temporaryB=list.ChooseBuilding(gcode[0]);
                                                                String temporaryTE=textField_1.getText();
                                                                Double temporaryMU=Double.parseDouble(textField_2.getText());
                                                                expTypes[finalI1].setB(temporaryB);
                                                                expTypes[finalI1].setMonthlyUse(temporaryMU);
                                                                expTypes[finalI1].setTypeOfExpense(temporaryTE);

                                                            }
                                                        });
                                                        btnNewButton.setBounds(335, 11, 89, 23);
                                                        frame.getContentPane().add(btnNewButton);
                                                        frame.setVisible(true);
                                                    }
                                                });
                                                btnNewButtonChange.setBounds(265, 36+25, 89, 20);
                                                frame.getContentPane().add(btnNewButtonChange);
                                                frame.setVisible(true);


                                            }
                                        });
                                        btnNewButtonTypeExpense.setBounds(0, 89+i*25, 89, 23);
                                        frame.getContentPane().add(btnNewButtonTypeExpense);

                                        JButton btnNewButtonInsert = new JButton("Insert");
                                        btnNewButtonInsert.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent e) {
                                                JFrame frame = new JFrame();
                                                frame.setBounds(100, 100, 450, 300);
                                                frame.getContentPane().setLayout(null);

                                                JLabel lblNewLabel = new JLabel("Code of building");
                                                lblNewLabel.setBounds(36, 39, 46, 14);
                                                frame.getContentPane().add(lblNewLabel);

                                                JLabel lblNewLabel_1 = new JLabel("TypeOfExpense");
                                                lblNewLabel_1.setBounds(36, 82, 46, 14);
                                                frame.getContentPane().add(lblNewLabel_1);

                                                JLabel lblNewLabel_2 = new JLabel("MonthlyCost");
                                                lblNewLabel_2.setBounds(36, 128, 46, 14);
                                                frame.getContentPane().add(lblNewLabel_2);


                                                final JTextField textField = new JTextField();
                                                textField.setBounds(174, 36, 86, 20);
                                                frame.getContentPane().add(textField);
                                                textField.setColumns(10);

                                                final JTextField textField_1 = new JTextField();
                                                textField_1.setBounds(174, 79, 86, 20);
                                                frame.getContentPane().add(textField_1);
                                                textField_1.setColumns(10);

                                                final JTextField textField_2 = new JTextField();
                                                textField_2.setBounds(174, 125, 86, 20);
                                                frame.getContentPane().add(textField_2);
                                                textField_2.setColumns(10);


                                                JButton btnNewButton = new JButton("OK");
                                                btnNewButton.addActionListener(new ActionListener() {
                                                    public void actionPerformed(ActionEvent e) {
                                                        gcode[0] =textField.getText();
                                                        Building temporaryB=list.ChooseBuilding(gcode[0]);
                                                        String temporaryTE=textField_1.getText();
                                                        Double temporaryMU=Double.parseDouble(textField_2.getText());
                                                        BuildingExpenses temporaryBE=new BuildingExpenses(temporaryB,temporaryTE,temporaryMU);
                                                        list.addBuildingExpenses(temporaryBE);

                                                    }
                                                });
                                                btnNewButton.setBounds(335, 11, 89, 23);
                                                frame.getContentPane().add(btnNewButton);
                                                frame.setVisible(true);
                                            }
                                        });
                                        btnNewButtonInsert.setBounds(80, 89, 89, 23);
                                        frame.getContentPane().add(btnNewButtonInsert);
                                        frame.setVisible(true);
                                    }
                                }
                            });
                            btnNewButtonExpenseManagement.setBounds(265, 36+75, 89, 20);
                            frame.getContentPane().add(btnNewButtonExpenseManagement);
                            JButton btnNewButtonChange = new JButton("Change");
                            btnNewButtonChange.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    JFrame frame = new JFrame();
                                    frame.setBounds(100, 100, 450, 300);
                                    frame.getContentPane().setLayout(null);

                                    JLabel lblNewLabel = new JLabel("Code");
                                    lblNewLabel.setBounds(36, 39, 46, 14);
                                    frame.getContentPane().add(lblNewLabel);

                                    JLabel lblNewLabel_1 = new JLabel("Description");
                                    lblNewLabel_1.setBounds(36, 82, 46, 14);
                                    frame.getContentPane().add(lblNewLabel_1);

                                    JLabel lblNewLabel_2 = new JLabel("Address");
                                    lblNewLabel_2.setBounds(36, 128, 46, 14);
                                    frame.getContentPane().add(lblNewLabel_2);

                                    JLabel lblNewLabel_3 = new JLabel("Price Zone");
                                    lblNewLabel_3.setBounds(36, 180, 46, 14);
                                    frame.getContentPane().add(lblNewLabel_3);

                                    JLabel lblNewLabel_4 = new JLabel("Square Meters");
                                    lblNewLabel_4.setBounds(36, 223, 46, 14);
                                    frame.getContentPane().add(lblNewLabel_4);

                                    final JTextField textField = new JTextField();
                                    textField.setBounds(174, 36, 86, 20);
                                    frame.getContentPane().add(textField);
                                    textField.setColumns(10);

                                    final JTextField textField_1 = new JTextField();
                                    textField_1.setBounds(174, 79, 86, 20);
                                    frame.getContentPane().add(textField_1);
                                    textField_1.setColumns(10);

                                    final JTextField textField_2 = new JTextField();
                                    textField_2.setBounds(174, 125, 86, 20);
                                    frame.getContentPane().add(textField_2);
                                    textField_2.setColumns(10);

                                    final JTextField textField_3 = new JTextField();
                                    textField_3.setBounds(174, 177, 86, 20);
                                    frame.getContentPane().add(textField_3);
                                    textField_3.setColumns(10);

                                    final JTextField textField_4 = new JTextField();
                                    textField_4.setBounds(174, 220, 86, 20);
                                    frame.getContentPane().add(textField_4);
                                    textField_4.setColumns(10);

                                    JButton btnNewButton = new JButton("OK");
                                    btnNewButton.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e) {
                                            gcode[0] =textField.getText();
                                            gdescription[0] =textField_1.getText();
                                            gaddress[0] =textField_2.getText();
                                            gpricezone[0] =Double.parseDouble(textField_3.getText());
                                            gsquaremeter[0] =Double.parseDouble(textField_4.getText());
                                            list.getBuilding(finalI).setCode(gcode[0]);
                                            list.getBuilding(finalI).setDescription(gdescription[0]);
                                            list.getBuilding(finalI).setAddress(gaddress[0]);
                                            list.getBuilding(finalI).setPriceZone(gpricezone[0]);
                                            list.getBuilding(finalI).setSquareMeter(gsquaremeter[0]);

                                        }
                                    });
                                    btnNewButton.setBounds(335, 11, 89, 23);
                                    frame.getContentPane().add(btnNewButton);
                                    frame.setVisible(true);
                                }
                            });
                            btnNewButtonChange.setBounds(265, 36+100, 89, 20);
                            frame.getContentPane().add(btnNewButtonChange);
                            frame.setVisible(true);
                        }
                    });
                    btnShowMore.setBounds(588, 289+i*25, 99, 23);
                    frame.getContentPane().add(btnShowMore);
                    frame.setVisible(true);
                }
            }else if(answer.equals("0")){
                list.printToFile(befileName);
                System.out.println("Printing has been executed automatically!");
                done=true;
            }
        }
    }
}