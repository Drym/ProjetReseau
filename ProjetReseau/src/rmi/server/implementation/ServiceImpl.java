package rmi.server.implementation;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class ServiceImpl extends UnicastRemoteObject {
	private static final long serialVersionUID = 8997018075376567935L;
	protected static Hashtable<String, Set<String>> data = initializeServerData();
	
	protected ServiceImpl() throws RemoteException {
		super();
	}
	
	private static Hashtable<String, Set<String>> initializeServerData(){
		Hashtable<String, Set<String>> map = new Hashtable<>();
		
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
