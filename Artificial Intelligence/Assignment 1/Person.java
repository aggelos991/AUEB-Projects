public class Person {

    private String role;
    private int time;
    private boolean lamp=false;

    public Person(int time,boolean lamp){
        this.role="John Doe";
        this.time=time;
        this.lamp=lamp;

    }

    public boolean hasLamp() {
        return lamp;
    }

    public void setLamp(boolean lamp) {
        this.lamp = lamp;
    }

    public int getTime() {
        return time;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setTime(int time) {
        this.time = time;
    }



    public int compareTo(Person p2 ){
        return p2.getTime()-this.getTime();
    }


    public boolean equals(Person obj){
        if( obj!=null){
            return this.role.equals (obj.role);
        }else{
            return false;
        }

    }

    public int max(Person p2){
        if(this.getTime()>p2.getTime()){
            return this.getTime();
        }else{
            return p2.getTime();
        }
    }

}
