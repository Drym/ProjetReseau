package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import protocol.InvalidRequestException;
import protocol.Response;
import protocol.services.Service;

public class TaskServerTCP implements Runnable {

	private Socket clientSocket;
	
	public TaskServerTCP(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {
		try {
			
			// Ouverture des flux entre le client et le serveur
			ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
			ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
			Service request = null;

			// Boucle infinie permettant de gérer les requêtes du client
			for(int i = 1 ;; i++){
				
				Response response = null;
				try{
					// Récupération de la requête client sur le flux en entrée
					request = (Service)ois.readObject();				
					System.out.println("Msg n°"+i+" :Réception d'un objet envoyé par le client.");
					// Exécution de la requête client et création de la réponse
					response = request.exec();
					
				} catch(EOFException eof){
					return;
					
				} catch (InvalidRequestException e) {
					// Gestion des erreurs sur l'utilisation d'un requête (ajout d'un nom vide par exemple)
					// Une réponse avec le message d'erreur est créée
					String message = request.getServiceName() + " : " + e.getMessageError();
					response = new Response(false, message, null);
					
				} catch(ClassCastException cce) {
					// Cas où l'objet envoyé par le client n'appartient pas au protocole d'application
					response = new Response(false, "ERREUR : requête non conforme au protocole.");
				} catch(NotSerializableException nse){
					// Cas où l'objet envoyé n'est pas sérialisable
					response = new Response(false, "Objet non sérializable.");
				} catch(ClassNotFoundException cnfe){
					// Cas où l'objet envoyé étend de la classe Service mais n'appartient tout de même pas au protocole d'application
					response = new Response(false, "Classe non trouvée.");					
				} catch (IOException ioe) {
					// Permet de gérer les déconnexions brutales
					return;
				}

				// Ecriture de la réponse sur le flux sortant
				oos.writeObject(response);
				// Assure que l'objet soit bien écrit sur le flux sortant (permet d'éviter certains problèmes)
				oos.reset();
				System.out.println("Msg n°" + i + " :Envoi d'une réponse au client.");
				oos.flush();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
