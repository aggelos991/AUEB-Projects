public class Expenditure{
    private String code=null;
    private String description=null;




    public Expenditure(String theCode, String theDescription){
        code=theCode;
        description=theDescription;
    }

    public String getCode(){
        return code;
    }
    public String getDescription(){
        return description;
    }

}