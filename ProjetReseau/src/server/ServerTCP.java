package server;

import java.net.ServerSocket;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import protocol.InvalidRequestException;
import protocol.Response;
import protocol.services.Service;


public class ServerTCP {

	public static void main(String[] args) {

		Hashtable<String, Set<String>> serverData = initializeServerData();
		
		ServerSocket serverSocket;
		Socket clientSocket;

		System.out.println(" === SERVER SIDE === ");
		
		try {

			serverSocket = new ServerSocket(1337);
			
			System.out.println("Msg:Serveur lancé.");
			for(int i = 1 ;; i++){
				clientSocket = serverSocket.accept();
				System.out.println("Msg:Tâche"+i+" lancée.");
				new Thread(new TaskServerTCP(clientSocket, serverData)).start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static Hashtable<String, Set<String>> initializeServerData(){
		Hashtable<String, Set<String>> map = new Hashtable<>();
		
		for(int i = 1 ; i <= 3 ; i++){
			Set<String> set = new HashSet<>();
			set.add("Surname"+i+".1");
			set.add("Surname"+i+".2");
			set.add("Surname"+i+".3");
			map.put("Name"+i, set);
		}
		
		return map;
	}
}
