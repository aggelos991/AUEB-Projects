import java.util.*;
import java.io.*;
import java.text.*;
import java.net.*;
public class Consumer{
	public static void main(String args[]) throws IOException{
		public void register(ArtistName n){
			try{
				Scanner scn=new Scanner(System.in);
				//getting localhost ip
				InetAddress ip=InetAddress.getByName("localhost");
				
				
				//establish connection with server port 5056
				Socket s=new Socket(ip,6969);
				// obtaining input and out streams 
                DataInputStream dis = new DataInputStream(s.getInputStream()); 
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				System.out.println(dis.readUTF());
				System.out.println("enter the name of an artist");
				String tosend=scn.nextLine();
				System.out.println("you have selected songs of"+tosend);
				dos.writeUTF(tosend);
				// If client sends exit,close this connection  
                // and then break from the while loop 
                if(tosend.equals("Exit")) 
                { 
                    System.out.println("Closing this connection : " + s); 
                    s.close(); 
                    System.out.println("Connection closed"); 
                    break; 
                }
				String received = dis.readUTF(); 
                System.out.println(received);
				  scn.close(); 
            dis.close(); 
            dos.close(); 
        }catch(Exception e){ 
            e.printStackTrace(); 
        } 
}
	