package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import protocol.Response;
import protocol.services.Ajouter;
import protocol.services.Lister;

public class Client {

	public static void main(String[] args) {
		String hostName = "localhost";
		int portNumber = 1337;
		ObjectInputStream ois;
		ObjectOutputStream oos;
		
		System.out.println(" === CLIENT SIDE === ");

		try {
		    Socket socket = new Socket(hostName, portNumber);
			System.out.println("Msg:Demande de connexion au serveur.");
		    
			oos = new ObjectOutputStream(socket.getOutputStream());
			Set<String> nicknames = new HashSet<>();
			nicknames.add("ThibThib");
			nicknames.add("Oberyn");
			nicknames.add("Draykoon");
			nicknames.add("Paltoquet");
			oos.writeObject(new Ajouter("Thibault", nicknames));
			System.out.println("Msg:Envoi d'un ajout de l'utilisateur Thibault au serveur.");
			oos.flush();
			
			ois = new ObjectInputStream(socket.getInputStream());
			Response response = (Response)ois.readObject();
			System.out.println("Msg:Réception d'une réponse du serveur.");
			if(response.getStatus()){
				System.out.println("Msg:Utilisateur ajouté.");
			}else{
				System.out.println(response.getMessage());
			}
			
			// Part 2
			
			oos.writeObject(new Ajouter("Thibault", nicknames));
			System.out.println("Msg2:Envoi d'un ajout de l'utilisateur Thibault au serveur.");
			oos.flush();
			
			response = (Response)ois.readObject();
			System.out.println("Msg2:Réception d'une réponse du serveur.");
			if(response.getStatus()){
				System.out.println("Msg2:Utilisateur ajouté.");
			}else{
				System.out.println(response.getMessage());
			}
			
			// Part 3
			
			oos.writeObject(new Lister());
			System.out.println("Msg3:Envoi d'une requête LIST au serveur.");
			oos.flush();
			
			response = (Response)ois.readObject();
			System.out.println("Msg3:Réception d'une réponse du serveur.");
			if(response.getStatus()){
				System.out.println("Msg3:Affichage d'une partie des données reçues:");
				for (String string : response.getData().keySet()) {
					System.out.println("	"+string);
				}
			}else{
				System.out.println(response.getMessage());
			}
			
			oos.close();
			ois.close();
			socket.close();
			
			System.out.println("Msg:Client déconnecté.");

		} catch(IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}


	}

}
