import java.util.*;
import java.io.*;
import java.text.*;
import java.net.*;
public class Node {
	private Socket socket=null;
	private DataInputStream in=null;
	private DataOutputStream out=null;
	private String address;
	private int port;
 List<Broker> brockers=new ArrayList<Brocker>();
 public List<Broker> getBrockers(){
	 return brockers;
 }
 public void connect(){//establish connection
	try{
		socket=new Socket(address,port);
		System.out.println("Connected"); 
		in= new DataInputStream(System.in);
		//send output to socket
		out=new DataOutputStream(socket.getOutputStream());
	}catch(IOException e){
		System.out.println(e);
	}
}
 public void disconnect(){
	 try{
		 in.close();
		 out.close();
		 socket.close();
		 System.out.println("Closing the connection");
	 }catch(IOException e){
		 System.out.println(e);
	 }
 }
 public void updateNodes(){
	
 }
 public void init(int i){
	 
	 
 }
	
 
}