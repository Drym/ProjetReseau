package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

import protocol.Response;
import protocol.services.Ajouter;
import protocol.services.Lister;
import protocol.services.MettreAJour;
import protocol.services.Supprimer;
import protocol.services.Disconnect;

public class ClientTCP {

	public static void main(String[] args) {
		// Nom de l'hôte
		String hostName = "localhost";
		// Numéro de port
		int portNumber = 1337;
		ObjectInputStream ois;
		ObjectOutputStream oos;
		String read;
		Boolean continuer = true;
		
		System.out.println(" === CLIENT SIDE === ");
		Scanner scanner = new Scanner(System.in);

		try {
			// Création de la socket de connexion au serveur
			Socket socket = new Socket(hostName, portNumber);
			System.out.println("Msg:Demande de connexion au serveur.");
			// Ouverture des flux sur la socket permettant de transférer des objet sérialisés
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			
			//Boucle tant que l'utilisateur veut continuer à entrer des commandes
			while (continuer) {
				System.out.println("Que voulez-vous faire ?");
				read = scanner.nextLine();
				read = read.toLowerCase();

				//Requete ajouter
				if (read.equals("ajouter")) {

					System.out.println("Entrez le nom à ajouter");
					read = scanner.nextLine();
					String nom = read;

					System.out.println("Entrez les surnoms (! pour arreter)");
					Set<String> nicknames = new HashSet<>();

					while (!(read.equals("!"))) {
						read = scanner.nextLine();
						if (!read.equals("!"))
							nicknames.add(read);
					}

					// Ecriture de l'objet correspondant au service désiré sur le flux sortant
					oos.writeObject(new Ajouter(nom, nicknames));
					System.out.println("Msg:Envoi d'un ajout de l'utilisateur " + nom + " au serveur.");
					oos.flush();

					// Récupération de la réponse du serveur via l'objet Response (lecture sur le flux entrant)
					Response response = (Response) ois.readObject();
					System.out.println("Msg:Réception d'une réponse du serveur.");
					// Si le statut de la réponse est vrai, l'action souhaîtée du client est effectuée,
					// sinon le message d'erreur de la réponse est affichée
					if (response.getStatus()) {
						System.out.println("Msg:Utilisateur " + nom + " ajouté.");
					} else {
						System.out.println(response.getMessage());
					}
				}

				//Requete Lister
				else if (read.equals("lister")) {

					System.out.println("Entrez le début du nom à rechercher, laisser vide pour récupérer toute la liste, puis appuyez sur Entrée : ");
					read = scanner.nextLine();
					
					//Envois de la quete
					oos.writeObject(new Lister(read));
					System.out.println("Msg:Envoi d'une requête LIST au serveur.");
					oos.flush();

					//Reponse
					Response response = (Response) ois.readObject();
					System.out.println("Msg:Réception d'une réponse du serveur.");
					if (response.getStatus()) {
						System.out.println("Msg:Affichage d'une partie des données reçues:");

						Hashtable<String, Set<String>> map = response.getData();

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

				//Requete supprimer
				else if (read.equals("supprimer")) {

					System.out.println("Entrez le nom à supprimer");
					read = scanner.nextLine();
					String nom = read;

					//Envois de la requete
					oos.writeObject(new Supprimer(nom));
					System.out.println("Msg:Envoi d'une suppression de l'utilisateur " + nom + " au serveur.");
					oos.flush();

					//Reponse
					Response response = (Response) ois.readObject();
					System.out.println("Msg:Réception d'une réponse du serveur.");
					if (response.getStatus()) {
						System.out.println("Msg:Utilisateur " + nom + " supprimé.");
					} else {
						System.out.println(response.getMessage());
					}
				}

				//Requete ajouter
				else if (read.equals("modifier")) {

					System.out.println("Entrez le nom à modifier");
					read = scanner.nextLine();
					String nom = read;

					System.out.println("Voulez-vous faire ? (1/2/3)");
					System.out.println("1. Modifier le nom");
					System.out.println("2. Modifier le surnom");
					System.out.println("3. Modifier le nom et le surnom");
					read = scanner.nextLine();

					if (read.equals("1")) {
						System.out.println("Entrez le nouveau nom");
						read = scanner.nextLine();
						String newNom = read;

						//ecriture de la requete
						oos.writeObject(new MettreAJour(nom, newNom));
					}
					else if (read.equals("2")) {
						System.out.println("Entrez le/les nouveau(x) surnom(s) (! pour arreter)");
						Set<String> nicknames = new HashSet<>();

						while (!(read.equals("!"))) {
							read = scanner.nextLine();
							if (!read.equals("!"))
								nicknames.add(read);
						}

						//ecriture de la requete
						oos.writeObject(new MettreAJour(nom, nicknames));
					}

					else {
						System.out.println("Entrez le nouveau nom");
						read = scanner.nextLine();
						String newNom = read;

						System.out.println("Entrez le/les nouveau(x) surnom(s) (! pour arreter)");
						Set<String> nicknames = new HashSet<>();

						while (!(read.equals("!"))) {
							read = scanner.nextLine();
							if (!read.equals("!"))
								nicknames.add(read);
						}

						//ecriture de la requete
						oos.writeObject(new MettreAJour(nom, newNom, nicknames));
					}

					//Envois de la requete
					System.out.println("Msg:Envoi d'une modification de l'utilisateur " + nom + " au serveur.");
					oos.flush();

					//Reponse
					Response response = (Response) ois.readObject();
					System.out.println("Msg:Réception d'une réponse du serveur.");
					if (response.getStatus()) {
						System.out.println("Msg:Utilisateur " + nom + " modifié.");
					} else {
						System.out.println(response.getMessage());
					}
				}

				//Affiche l'aide
				else if (read.equals("help")) {
					System.out.println("Les differentes requetes sont :");
					System.out.println("Ajouter");
					System.out.println("Lister");
					System.out.println("Supprimer");
					System.out.println("Modifier");
					System.out.println("Disconnect");
				}

				//Fermeture ou non de la connexion
				else if(read.equals("disconnect")) {
					//Envois de la requete
					oos.writeObject(new Disconnect());
					System.out.println("Msg:Envoi d'une requête DISCONNECT au serveur.");
					oos.flush();

					//Reponse
					Response response = (Response) ois.readObject();
					System.out.println("Msg:Réception d'une réponse du serveur.");

					if (response.getStatus()) {
						System.out.println("Msg:Déconnecté");
					} else {
						System.out.println(response.getMessage());
					}

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
