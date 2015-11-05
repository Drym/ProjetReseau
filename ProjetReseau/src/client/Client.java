package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class Client {

	public static void main(String[] args) {
		String hostName = "localhost";
		int portNumber = 1337;
		ObjectInputStream ois;
		ObjectOutputStream oos;

		try {
		    Socket socket = new Socket(hostName, portNumber);
			System.out.println("Demande de connexion...");
		    
			ois = new ObjectInputStream(socket.getInputStream());
		    oos = new ObjectOutputStream(socket.getOutputStream());

			String message = (String)ois.readObject();
			System.out.println(message);

			ois.close();
			oos.close();
			socket.close();

		} catch(Exception e) {
			
		}


	}

}
