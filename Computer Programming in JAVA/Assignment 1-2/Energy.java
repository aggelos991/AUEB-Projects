public class Energy extends FixedCostPerUse{



    public Energy(String theCode,String theDescription,double PriceUnit,double UnitAmount,double monthlyFixedCost){
        super(theCode,theDescription,PriceUnit,UnitAmount,monthlyFixedCost);
    }
    public double getEnergy(){
        return getMonthlyCost()+getPriceUnit()*getUnitAmount();
    }

}