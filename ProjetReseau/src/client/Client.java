package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.*;

import protocol.Response;
import protocol.services.Ajouter;
import protocol.services.Lister;

public class Client {

	public static void main(String[] args) {
		String hostName = "localhost";
		int portNumber = 1337;
		ObjectInputStream ois;
		ObjectOutputStream oos;
		String read;
		Boolean continuer = true;
		
		System.out.println(" === CLIENT SIDE === ");
		Scanner scanner = new Scanner(System.in);

		try {
			//Connexion
			Socket socket = new Socket(hostName, portNumber);
			System.out.println("Msg:Demande de connexion au serveur.");
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());

			//Boucle tant que l'utilisateur veut continuer
			while (continuer) {
				System.out.println("Que voulez-vous faire ?");
				read = scanner.nextLine();

				//Requete ajouter
				if (read.equals("Ajouter") || read.equals("ajouter")) {

					System.out.println("Entrez le nom");
					read = scanner.nextLine();
					String nom = read;

					System.out.println("Entrez les surnoms (! pour arreter)");
					Set<String> nicknames = new HashSet<>();

					while (!(read.equals("!"))) {
						read = scanner.nextLine();
						if (!read.equals("!"))
							nicknames.add(read);
					}

					//Envois de la requete
					oos.writeObject(new Ajouter(nom, nicknames));
					System.out.println("Msg:Envoi d'un ajout de l'utilisateur " + nom + " au serveur.");
					oos.flush();

					//Reponse
					Response response = (Response) ois.readObject();
					System.out.println("Msg:Réception d'une réponse du serveur.");
					if (response.getStatus()) {
						System.out.println("Msg:Utilisateur " + nom + " ajouté.");
					} else {
						System.out.println(response.getMessage());
					}
				}

				//Requete Lister
				else if (read.equals("Lister") || read.equals("lister")) {

					//Envois de la quete
					oos.writeObject(new Lister());
					System.out.println("Msg:Envoi d'une requête LIST au serveur.");
					oos.flush();

					//Reponse
					Response response = (Response) ois.readObject();
					System.out.println("Msg:Réception d'une réponse du serveur.");
					if (response.getStatus()) {
						System.out.println("Msg:Affichage d'une partie des données reçues:");

						HashMap<String, Set<String>> map = response.getData();

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
				}

				//Affiche l'aide
				else if (read.equals("Help") || read.equals("help")) {
					System.out.println("Les differentes requetes sont :");
					System.out.println("Ajouter");
					System.out.println("Lister");
					System.out.println("Disconnect");
				}

				//Fermeture ou non de la connexion
				else if(read.equals("Disconnect") || read.equals("disconnect")) {
					oos.close();
					ois.close();
					socket.close();
					continuer = false;
					System.out.println("Msg:Client déconnecté.");
				}

				//Mauvaise requete
				else {
					System.out.println("Cette requete n'existe pas ! Help pour plus d'information");
				}

				System.out.println(" \n_______________\n");

			}
		}catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}
