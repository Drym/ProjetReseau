package protocol.services;

import java.util.Hashtable;
import java.util.Set;

import protocol.InvalidRequestException;
import protocol.Response;

/**
 * Lister est une classe qui hérite de Service et permettant d'obtenir la liste
 * des noms et surnoms présent sur le serveur
 */
public class Lister extends Service {
	private static final long serialVersionUID = 3350070951937150357L;

	private String startWith;

	/**
	 * Constructeur de Lister
	 *
	 * Permet l'obtention de la liste des noms et surnoms
	 */
	public Lister() {
		super("LIST");
		startWith = "";
	}

	/**
	 * Constructeur de Lister
	 *
	 * Permet l'obtention de la liste des noms et surnoms
	 *
	 * @param startWith
	 *            Permet de lister uniquement tous les noms qui commencent par
	 *            startWith
	 */
	public Lister(String startWith) {
		super("LIST");
		if(startWith != null)
			this.startWith = startWith;
	}

	@Override
	public Response exec() throws InvalidRequestException {
		Hashtable<String, Set<String>> mapRes = new Hashtable<>();

		for (String name : serverData.keySet()) {
			if (name.startsWith(startWith))
				mapRes.put(name, serverData.get(name));
		}

		return new Response(true, "Liste reçue", mapRes);
	}
}
