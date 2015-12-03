package server;

import java.net.ServerSocket;
import java.io.*;
import java.net.Socket;


public class ServerTCP {

	public static void main(String[] args) {

		// Structure de données Thread-safe contenant les noms et les surnoms associés
		
		
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
				new Thread(new TaskServerTCP(clientSocket)).start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
