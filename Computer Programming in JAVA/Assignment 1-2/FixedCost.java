public class FixedCost extends Expenditure{

    private double PricePerSquare;
    private double SquareMeter;
    public FixedCost(String theCode,String theDescription,double SquarePrice,double SquareMeter){
        super(theCode,theDescription);
        PricePerSquare=SquarePrice;
        this.SquareMeter=SquareMeter;
    }
    public double getSquarePrice(){
        return PricePerSquare * SquareMeter;
    }
}








