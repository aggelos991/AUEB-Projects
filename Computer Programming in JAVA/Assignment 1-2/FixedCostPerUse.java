public class FixedCostPerUse extends Expenditure{

    private double PriceUnit,monthlyFixedCost,UnitAmount=0;

    public FixedCostPerUse(String theCode,String theDescription,double PriceUnit,double UnitAmount,double monthlyFixedCost){
        super(theCode,theDescription);
        this.PriceUnit=PriceUnit;
        this.monthlyFixedCost=monthlyFixedCost;
        this.UnitAmount=UnitAmount;
    }


    public double getPriceUnit(){
        return PriceUnit;
    }
    public double getUnitAmount(){
        return  UnitAmount;
    }
    public double getMonthlyCost(){
        return monthlyFixedCost;
    }
}
