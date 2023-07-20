package pack1;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MyClient {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your message: ");
		String msg= sc.nextLine();
		try {
			Socket s = new Socket("localhost",4444);
			DataOutputStream dos= new DataOutputStream(s.getOutputStream());
			
			dos.writeUTF(msg);
			dos.flush();
			dos.close();
			s.close();
			sc.close();
			
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

}
