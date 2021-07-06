import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;
public class Collections {
    private ArrayList<Building> buildings = new ArrayList<Building>();
    private ArrayList<Expenditure> type = new ArrayList<Expenditure>();
    private ArrayList<BuildingExpenses> expenses = new ArrayList<BuildingExpenses>();

    public void addBuilding(Building b) {
        buildings.add(b);
    }

    public void addExpenditure(Expenditure e) {
        type.add(e);
    }

    public void updateExpenditure(int index, Expenditure e) {
        type.set(index, e);
    }

    public void addBuildingExpenses(BuildingExpenses be) {
        expenses.add(be);
    }

    public void ShowBuilding() {
        int i = 0;
        String message = null;
        for (Building b : buildings) {
            System.out.print(i + " : ");
            message = b.print();
            System.out.println(message);
            i += 1;
        }
        System.out.println("Number of buildings: " + buildings.size());
    }

    public Building ChooseBuilding(String code) {
        for (Building b : buildings) {
            if (b.getCode().equals(code)) {
                System.out.println(b.print());
                return b;
            }
        }
        return null;
    }
    public Building getBuilding(int i){
        return buildings.get(i);
    }
    public void removeBuilding(Building b){ buildings.remove(buildings.indexOf(b));}
    public int getBuildingPos(Building b) {
        return buildings.indexOf(b);
    }
    public int getBuildingsLength(){ return buildings.size(); }
    public void printExpenses(Building b) {
        for (int i = 0; i < expenses.size(); i++) {
            if (expenses.get(i).getBuilding().equals(b)) {
                System.out.println("Expense code: " + expenses.get(i).getTypeOfExpense() + " Value: " + expenses.get(i).getMonthlyUse());
            }
        }
    }
    public void removeBuildingExpense(BuildingExpenses be){ expenses.remove(expenses.indexOf(be));}
    public BuildingExpenses[] getBuildingExpense(Building b){
        BuildingExpenses[] expTypes=new BuildingExpenses[expenses.size()];
        for (int i = 0; i < expenses.size(); i++) {
            if (expenses.get(i).getBuilding().equals(b)) {
                expTypes[i]=expenses.get(i);
            }
        }
        return expTypes;
    }

    public double getSumOfExpenses(Building b) {
        double sum = 0;
        for (int i = 0; i < expenses.size(); i++) {
            if (expenses.get(i).getBuilding().equals(b)) {
                sum = sum + expenses.get(i).getMonthlyUse();
            }
        }
        return sum;
    }

    public void printTypeList() {
        for (int i = 0; i < 5; i++) {
            if(type.get(i)!=null){
                System.out.println(i + " : Code: " + type.get(i).getCode() + " Description: " + type.get(i).getDescription());
            }
        }
    }

    public double getSumOfExpense(String code) {
        double sum = 0;
        for (int i = 0; i < expenses.size(); i++) {
            if (expenses.get(i).getTypeOfExpense().equals(code)) {
                sum = sum + expenses.get(i).getMonthlyUse();
            }
        }
        return sum;
    }
    public void printToFile(String filename){
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(filename, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        writer.println("BUILDING_LIST");
        writer.println("{");
        for(int i=0;i<buildings.size();i++){
            writer.println("BUILDING");
            writer.println("{");
            writer.println("BUILDING_CODE " + buildings.get(i).getCode());
            writer.println("BUILDING_DESCR "+ buildings.get(i).getDescription());
            writer.println("ADDRESS " + buildings.get(i).getAddress());
            writer.println("SQUAREMETER " + buildings.get(i).getSquareMeter());
            writer.println("PRICEZONE " + buildings.get(i).getPriceZone());
            int count=0;
            for(int j=0;j<expenses.size();j++){
                if(expenses.get(j).getBuilding().equals(buildings.get(i))) {
                    count++;
                    if (count == 1) {
                        writer.println("EXPENSES");
                        writer.println("{");
                    }
                    writer.println("EXPENSE");
                    writer.println("{");
                    int index=0;
                    for(int k=0;k<type.size();k++){
                        if(type.get(k).getCode().equals(expenses.get(j).getTypeOfExpense())){
                            index=k;
                        }
                    }
                    String nameType="";
                    if(index==0){
                        nameType="Rent";
                    }else if(index==1){
                        nameType="Cleaning";
                    }else if(index==2){
                        nameType="Water";
                    }else if(index==3){
                        nameType="Telephone";
                    }else if(index==4){
                        nameType="Energy";
                    }
                    writer.println("TYPE " + nameType);
                    writer.println("EXPENSE_TYPE_CODE " + expenses.get(j).getTypeOfExpense());
                    if(expenses.get(j).getMonthlyUse()>0) {
                        writer.println("CONSUMPTION " + expenses.get(j).getMonthlyUse());
                    }
                    writer.println("}");

                }
            }
            if(count==expenses.size()-1) {
                count=0;
                writer.println("}");
            }
            writer.println("}");

        }
        writer.println("}");
        writer.close();

    }
}
