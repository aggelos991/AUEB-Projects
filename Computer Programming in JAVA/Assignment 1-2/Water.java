public class Water extends FixedCostPerUse{


    public Water(String theCode,String theDescription,double PriceUnit,double UnitAmount,double monthlyFixedCost){
        super(theCode,theDescription,PriceUnit,UnitAmount,monthlyFixedCost);

    }
    public double getWater(){

        return getMonthlyCost()+ getPriceUnit()*getUnitAmount();
    }

}