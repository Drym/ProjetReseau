package client;

import protocol.Ajouter;
import protocol.Service;

public class Client {

	public static void main(String[] args) {
		Service s = new Ajouter();
		
		System.out.println(s.toString());
	}

}
