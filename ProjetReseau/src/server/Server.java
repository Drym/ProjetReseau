package server;

import java.net.ServerSocket;
import java.io.*;
import java.net.Socket;


public class Server {

	public static void main(String[] args) {

		ServerSocket serverSocket;
		Socket clientSocket;
		ObjectInputStream ois;
		ObjectOutputStream oos;

		try {

			serverSocket = new ServerSocket(1337);
			clientSocket = serverSocket.accept();

			System.out.println("Connected");

			ois = new ObjectInputStream(clientSocket.getInputStream());
			oos = new ObjectOutputStream(clientSocket.getOutputStream());

			oos.writeObject("Vous etes connected");
			oos.flush();

			ois.close();
			oos.close();
			serverSocket.close();
			clientSocket.close();

		} catch (IOException e) {

		}

	}

}
