package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Set;

import protocol.InvalidRequestException;
import protocol.Response;
import protocol.services.Service;

public class TaskServerTCP implements Runnable {

	private Socket clientSocket;
	private HashMap<String, Set<String>> serverData;
	
	public TaskServerTCP(Socket clientSocket, HashMap<String, Set<String>> serverData) {
		this.clientSocket = clientSocket;
		this.serverData = serverData;
	}
	
	@Override
	public void run() {
		try {

			
			ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
			ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
			Service request = null;

			for(int i = 1 ;; i++){
				
				Response response = null;
				try{
					request = (Service)ois.readObject();				
					System.out.println("Msg n°"+i+" :Réception d'un objet envoyé par le client.");
					serverData = request.exec(serverData);
					response = request.createResponse(true, "OK", serverData);
					
				} catch(EOFException eof){
					/*clientSocket = serverSocket.accept();
					ois = new ObjectInputStream(clientSocket.getInputStream());
					oos = new ObjectOutputStream(clientSocket.getOutputStream());
					continue;*/
					return;
					
				} catch (InvalidRequestException e) {
					String message = request.getServiceName() + " : " + e.getMessageError();
					response = request.createResponse(false, message, null);
					
				} catch(ClassCastException cce) {
					response = new Response(false, "ERREUR : requête non conforme au protocole.");
				} catch(NotSerializableException nse){
					response = new Response(false, "Objet non sérializable.");
				} catch(ClassNotFoundException cnfe){
					response = new Response(false, "Classe non trouvée.");					
				} catch (IOException ioe) {
					/*System.out.println("Déconnexion inattendue du client !");
					i = 1;
					clientSocket = serverSocket.accept();
					ois = new ObjectInputStream(clientSocket.getInputStream());
					oos = new ObjectOutputStream(clientSocket.getOutputStream());
					continue;*/
					return;
				}

				oos.writeObject(response);
				oos.reset();
				System.out.println("Msg n°" + i + " :Envoi d'une réponse au client.");
				oos.flush();
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

}
