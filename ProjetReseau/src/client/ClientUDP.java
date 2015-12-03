package client;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;

import protocol.Response;
import protocol.services.*;

public class ClientUDP {

	public static void main(String[] args) {
		String hostName = "localhost";
		int portNumber = 1337;
		
		System.out.println(" === CLIENT SIDE === ");

		try {
			//Connexion
			DatagramSocket socket = new DatagramSocket();
			DatagramPacket sendPacket;
			InetAddress IPAddress = InetAddress.getByName(hostName);

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(outputStream);
			
			//Envois de la quete 1
			oos.writeObject(new Lister());
			byte[] data = outputStream.toByteArray();

			System.out.println("Msg:Envoi d'une requête LIST au serveur.");
			sendPacket = new DatagramPacket(data, data.length, IPAddress, portNumber);
			socket.send(sendPacket);

			//Réception
			byte[] incomingData = new byte[1024];
			DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
			socket.receive(incomingPacket);
			byte[] incData = incomingPacket.getData();
			ByteArrayInputStream in = new ByteArrayInputStream(incData);
			ObjectInputStream ois = new ObjectInputStream(in);
			
			//Reponse
			Response response = (Response) ois.readObject();
			System.out.println("Msg:Réception d'une réponse du serveur.");
			
			if (response.getStatus()) {
				System.out.println("Msg:Affichage d'une partie des données reçues:");

				Hashtable<String, Set<String>> map = response.getData();

				// Affichage de la map
				for (String string : response.getData().keySet()) {
					List<String> list = new ArrayList<>( map.get(string));
					System.out.print("	" + string + " - ");

					for(int i = 0; i < list.size(); i++) {
						System.out.print(list.get(i) + "  ");
					}
					System.out.println("");
				}
				
			} else {
				System.out.println(response.getMessage());
			}

			socket.close();
			oos.close();
			ois.close();
			socket = new DatagramSocket();

			outputStream = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(outputStream);

			//Envois de la quete 2
			oos.writeObject(new Supprimer("Name1"));
			data = outputStream.toByteArray();

			System.out.println("Msg:Envoi d'une suppression de l'utilisateur Name1 au serveur.");
			sendPacket = new DatagramPacket(data, data.length, IPAddress, portNumber);
			socket.send(sendPacket);

			//Réception
			incomingData = new byte[1024];
			incomingPacket = new DatagramPacket(incomingData, incomingData.length);
			socket.receive(incomingPacket);
			incData = incomingPacket.getData();
			in = new ByteArrayInputStream(incData);
			ois = new ObjectInputStream(in);

			//Reponse
			response = (Response) ois.readObject();
			System.out.println("Msg:Réception d'une réponse du serveur.");

			if (response.getStatus()) {
				System.out.println("Msg:Utilisateur Name1 supprimé.");
			} else {
				System.out.println(response.getMessage());
			}

			socket.close();
			oos.close();
			ois.close();

			socket = new DatagramSocket();
			outputStream = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(outputStream);

			//Envois de la quete 3
			oos.writeObject(new Lister());
			data = outputStream.toByteArray();

			System.out.println("Msg:Envoi d'une requête LIST au serveur.");
			sendPacket = new DatagramPacket(data, data.length, IPAddress, portNumber);
			socket.send(sendPacket);

			//Réception
			incomingData = new byte[1024];
			incomingPacket = new DatagramPacket(incomingData, incomingData.length);
			socket.receive(incomingPacket);
			incData = incomingPacket.getData();
			in = new ByteArrayInputStream(incData);
			ois = new ObjectInputStream(in);

			//Reponse
			response = (Response) ois.readObject();
			System.out.println("Msg:Réception d'une réponse du serveur.");

			if (response.getStatus()) {
				System.out.println("Msg:Affichage d'une partie des données reçues:");

				Hashtable<String, Set<String>> map = response.getData();

				// Affichage de la map
				for (String string : response.getData().keySet()) {
					List<String> list = new ArrayList<>( map.get(string));
					System.out.print("	" + string + " - ");

					for(int i = 0; i < list.size(); i++) {
						System.out.print(list.get(i) + "  ");
					}
					System.out.println("");
				}

			} else {
				System.out.println(response.getMessage());
			}

			oos.close();
			ois.close();
			socket.close();

		}catch (IOException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
