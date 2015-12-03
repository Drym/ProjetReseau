package rmi;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import rmi.protocol.Disconnect;
import rmi.protocol.Lister;
import rmi.server.ResponseRMI;

public class ClientRMI {
	public static void main(String[] args) {
		try {
			System.out.println("Début du client");
			System.out.println("Récupération de Lister");
			Lister lister = (Lister) Naming.lookup("rmi://"
					+ InetAddress.getLocalHost().getHostAddress() + "/Lister");
			System.out.println("Exécution de Lister");
			ResponseRMI response = lister.execute();

			System.out.println("Affichage de la réponse");
			if (response.getStatus()) {
				Hashtable<String, Set<String>> map = response.getData();
				// Affichage de la map
				for (String string : response.getData().keySet()) {
					List<String> list = new ArrayList<>(map.get(string));
					System.out.print("	" + string + " - ");

					for (int i = 0; i < list.size(); i++) {
						System.out.print(list.get(i) + "  ");
					}
					System.out.println("");
				}
			} else {
				System.out.println(response.getMessage());
			}
			
			System.out.println("Récupération de Disconnect");
			Disconnect disconnect = (Disconnect) Naming.lookup("rmi://"
					+ InetAddress.getLocalHost().getHostAddress() + "/Disconnect");
			System.out.println("Exécution de Disconnect");
			response = disconnect.execute();
			
			System.out.println("Affichage de la réponse");
			System.out.println(response.getMessage());

			System.out.println("Fin du client !");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
