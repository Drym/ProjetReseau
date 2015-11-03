package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

	public static void main(String[] args) {
		String hostName = "localhost";
		int portNumber = 1337;

		try {
		    Socket socket = new Socket(hostName, portNumber);
		    
		    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
		    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		} catch(Exception e) {
			
		}
	}

}
