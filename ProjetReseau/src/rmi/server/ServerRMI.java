package rmi.server;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import rmi.server.implementation.AjouterImpl;
import rmi.server.implementation.DisconnectImpl;
import rmi.server.implementation.ListerImpl;



public class ServerRMI {
	public static void main(String[] args) {		
		try {
			LocateRegistry.createRegistry(1099);
			
			String url = "rmi://" + InetAddress.getLocalHost().getHostAddress();
			
			AjouterImpl ajouter = new AjouterImpl();
			DisconnectImpl disconnect = new DisconnectImpl();
			ListerImpl lister = new ListerImpl();
			
			Naming.rebind(url+"/Ajouter", ajouter);
			Naming.rebind(url+"/Disconnect", disconnect);
			Naming.rebind(url+"/Lister", lister);
			
			System.out.println("Serveur lancé !");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
