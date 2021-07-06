public class Telephone extends FixedCostPerUse{



    public Telephone(String theCode,String theDescription,double PriceUnit,double UnitAmount,double monthlyFixedCost){
        super(theCode,theDescription,PriceUnit,UnitAmount,monthlyFixedCost);

    }
    public double getTelephone(){
        return getMonthlyCost() + getPriceUnit()*getUnitAmount();
    }
}
	
	