public class Building{
    private String code;
    private String description;
    private String address;
    private double priceZone;
    private double squareMeter;

    public Building(String code,String description,String address,double priceZone,double squareMeter){
        this.code=code;
        this.description=description;
        this.address=address;
        this.priceZone=priceZone;
        this.squareMeter=squareMeter;
    }
    public String getCode(){
        return code;
    }
    public String getDescription(){
        return description;
    }
    public String getAddress(){
        return address;
    }
    public double getPriceZone(){
        return priceZone;
    }
    public double getSquareMeter(){
        return squareMeter;
    }
    public String print(){
        return(" Code: "+getCode()+ " Address: "+getAddress()+ " PriceZone: "+getPriceZone()+ " Square Meters: "+getSquareMeter());
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPriceZone(double priceZone) {
        this.priceZone = priceZone;
    }

    public void setSquareMeter(double squareMeter) {
        this.squareMeter = squareMeter;
    }
}
