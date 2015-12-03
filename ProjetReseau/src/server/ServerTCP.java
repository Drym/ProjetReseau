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

		// Structure de données Thread-safe contenant les noms et les surnoms associés
		Hashtable<String, Set<String>> serverData = initializeServerData();
		
		ServerSocket serverSocket;
		Socket clientSocket;

		System.out.println(" === SERVER SIDE === ");
		
		try {
			// Création de la socket serveur
			serverSocket = new ServerSocket(1337);
			
			// Boucle infinie permettant aux clients de se connecter
			System.out.println("Msg:Serveur lancé.");
			for(int i = 1 ;; i++){
				// Attente d'une connexion client
				clientSocket = serverSocket.accept();
				System.out.println("Msg:Tâche"+i+" lancée.");
				// Lorsqu'un client est connecté, un nouveau Thread est créé et exécute une nouvelle tâche dédié au client
				// Cela permet à d'autres clients de se connecter simultanément au serveur
				new Thread(new TaskServerTCP(clientSocket, serverData)).start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Initialise la structure de données avec des données par défaut
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
