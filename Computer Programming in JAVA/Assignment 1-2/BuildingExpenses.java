public class BuildingExpenses{
    private Building b;
    private String TypeOfExpense;
    private double monthlyUse;

    public BuildingExpenses(Building b,String TypeOfExpense,double monthlyUse){
        this.b=b;
        this.TypeOfExpense=TypeOfExpense;
        this.monthlyUse=monthlyUse;
    }
    public Building getBuilding(){
        return b;
    }
    public String toString(){
        return b.toString()+"  "+TypeOfExpense.toString();
    }
    public double getMonthlyUse(){return monthlyUse;}
    public String getTypeOfExpense(){return TypeOfExpense;}

    public void setB(Building b) {
        this.b = b;
    }

    public void setTypeOfExpense(String typeOfExpense) {
        TypeOfExpense = typeOfExpense;
    }

    public void setMonthlyUse(double monthlyUse) {
        this.monthlyUse = monthlyUse;
    }
}
	