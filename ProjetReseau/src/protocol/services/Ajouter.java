package protocol.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import protocol.InvalidRequestException;
import protocol.Response;

/**
 * Ajouter est une classe qui hérite de Service et permettant d'ajouter un nom sur le serveur
 */
public class Ajouter extends Service {
	private static final long serialVersionUID = -8413665337871880822L;
	
	private String name;
	private Set<String> nicknames;

	/**
	 * Constructeur d'Ajouter
	 *
	 * Permet l'ajout d'un sur le serveur
	 */
	public Ajouter() {
		super("ADD");
		nicknames = new HashSet<String>();
	}

	/**
	 * Constructeur d'Ajouter
	 *
	 * Permet l'ajout d'un sur le serveur
	 *
	 * @param name
	 * 				Le nom à ajouter
	 * @param nicknames
	 *				Les surnoms à ajouter
	 */
	public Ajouter(String name, Set<String> nicknames) {
		super("ADD");
		this.name = name;
		this.nicknames = nicknames;
	}

	@Override
	public HashMap<String, Set<String>> exec(HashMap<String, Set<String>> map) throws InvalidRequestException {
		if(map.containsKey(name)) throw new InvalidRequestException(
				"Le nom "+name+" n'a pas pu être ajouté car déjà présent sur le serveur.");
		
		for (String key : map.keySet()) {
			Set<String> tmp = map.get(key);
			for (String nname : tmp) {
				if(nicknames.contains(nname)) throw new InvalidRequestException(
						"Le nom " + name + " n'a pas pu être ajouté car le surnom "+nname+" est déjà présent sur le serveur.");
			}
		}
		
		map.put(name, nicknames);
		
		return map;
	}

	@Override
	public Response createResponse(boolean status, String message, HashMap<String, Set<String>> map) {
		return new Response(status, message);
	}
}
