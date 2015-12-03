package server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import protocol.InvalidRequestException;
import protocol.Response;
import protocol.services.Service;

public class ServerUDP {

	public static void main(String[] args) {		
		DatagramSocket serverSocket;
		DatagramPacket incomingPacket;
		
		System.out.println(" === SERVER SIDE === ");
		
		try {

			// Création de la socket pour UDP
			serverSocket = new DatagramSocket(1337);
			// Structure de données permettant de contenir les données du paquet
			byte[] incomingData = new byte[1024];

			ByteArrayInputStream in;
			ObjectInputStream ois;
			Service request = null;

			// Boucle infinie permettant de gérer les requêtes client
			for(int i = 1 ;; i++){
				
				// Création du paquet qui recevra le paquet du client
				incomingPacket = new DatagramPacket(incomingData, incomingData.length);
				// Attente de la réception d'un paquet
				serverSocket.receive(incomingPacket);
				// Récupération des données contenues dans le paquet
				byte[] incData = incomingPacket.getData();
				
				// Ouverture des flux d'entrée - sortie
				in = new ByteArrayInputStream(incData);
				ois = new ObjectInputStream(in);
								
				Response response = null;
				try{
					// Récupération de la requête client sur le flux en entrée
					request = (Service)ois.readObject();
					System.out.println("Msg n°"+i+" :Réception d'un objet envoyé par le client.");
					// Exécution de la requête client et création de la réponse
					response = request.exec();
					
				} catch(EOFException eof){
					incomingPacket = new DatagramPacket(incomingData, incomingData.length);
					serverSocket.receive(incomingPacket);
					byte[] incomData = incomingPacket.getData();
					in = new ByteArrayInputStream(incomData);
					ois = new ObjectInputStream(in);
					continue;
				
				// Gestion des requêtes invalides
				} catch (InvalidRequestException e) {
					String message = request.getServiceName() + " : " + e.getMessageError();
					response = new Response(false, message, null);
					
				// Gestion lorsqu'un objet de type autre que Service est reçu
				} catch(ClassCastException cce) {
					response = new Response(false, "ERREUR : requête non conforme au protocole.");
				
				// Gestion lors de la réception d'un objet non sérializable
				} catch(NotSerializableException nse){
					response = new Response(false, "Objet non sérializable.");
				
				// Lorsque l'objet envoyé n'est pas reconnu
				} catch(ClassNotFoundException cnfe){
					response = new Response(false, "Classe non trouvée.");
				
				// Gestion des déconnexions imprévues
				} catch (IOException ioe) {
					System.out.println("Déconnexion inattendue du client !");
					i = 1;
					incomingPacket = new DatagramPacket(incomingData, incomingData.length);
					serverSocket.receive(incomingPacket);
					byte[] incomData = incomingPacket.getData();
					in = new ByteArrayInputStream(incomData);
					ois = new ObjectInputStream(in);
					continue;
				}

				InetAddress IPAddress = incomingPacket.getAddress();
				int port = incomingPacket.getPort();
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(outputStream);
				oos.writeObject(response);
				
				byte[] replyData = outputStream.toByteArray();
				DatagramPacket sendPacket = new DatagramPacket(replyData, replyData.length, IPAddress, port);
				serverSocket.send(sendPacket);


				System.out.println("Msg n°" + i + " :Envoi d'une réponse au client.");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
