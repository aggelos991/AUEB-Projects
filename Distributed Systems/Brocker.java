import java.util.*;
import java.io.*;
import java.text.*;
import java.net.*;
public class Brocker extends Node{
	public static void main(String args[]){
		
	}//end main
	public void BrockerServer(){
		while(true){
			Socket s=null;
			try{
				s.accept();
				System.out.println("A new customer is connected"+s);
				DataInputStream dis = new DataInputStream(s.getInputStream()); 
				DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
				System.out.println("Assigning new thread for this client"); 
				// create a new thread object 
                Thread t = new ClientHandler(s, dis, dos); 
				// Invoking the start() method 
                t.start();
				} 
            catch (Exception e){ 
                s.close(); 
                e.printStackTrace(); 
            }
		}
	}	
	public String encryptThisString(String ArtistName,) 
    { 
}