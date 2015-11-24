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
import java.util.*;

import protocol.InvalidRequestException;
import protocol.Response;
import protocol.services.Service;

public class ServerUDP {

	public static void main(String[] args) {
		HashMap<String, Set<String>> serverData = initializeServerData();
		
		DatagramSocket serverSocket;
		DatagramPacket incomingPacket;
		
		System.out.println(" === SERVER SIDE === ");
		
		try {

			serverSocket = new DatagramSocket(1337);
			byte[] incomingData = new byte[1024];

			ByteArrayInputStream in;
			ObjectInputStream ois;
			Service request = null;

			for(int i = 1 ;; i++){
				
				incomingPacket = new DatagramPacket(incomingData, incomingData.length);
				serverSocket.receive(incomingPacket);
				byte[] incData = incomingPacket.getData();
				in = new ByteArrayInputStream(incData);
				ois = new ObjectInputStream(in);
								
				Response response = null;
				try{
					request = (Service)ois.readObject();				
					System.out.println("Msg n°"+i+" :Réception d'un objet envoyé par le client.");
					serverData = request.exec(serverData);

					for (String string : serverData.keySet()) {
						List<String> list = new ArrayList<>( serverData.get(string));
						System.out.print("	" + string + " - ");

						for(int i2 = 0; i2 < list.size(); i2++) {
							System.out.print(list.get(i2) + "  ");
						}
						System.out.println("");
					}

					response = request.createResponse(true, "OK", serverData);
					
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
					response = request.createResponse(false, message, null);
					
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

			/*ois.close();
			oos.close();
			serverSocket.close();
			clientSocket.close();
			
			System.out.println("Msg:Serveur stoppé.");*/

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static HashMap<String, Set<String>> initializeServerData(){
		HashMap<String, Set<String>> map = new HashMap<>();
		
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
