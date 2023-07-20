package pack1;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {

	public static void main(String[] args) {
		try {
			ServerSocket ss = new ServerSocket(4444);
			Socket s = ss.accept();
			DataInputStream dis= new DataInputStream(s.getInputStream());
			String msg= dis.readUTF();
			System.out.println("message: "+msg);
			ss.close();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}

}
