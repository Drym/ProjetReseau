package protocol.services;

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
	public Response exec() throws InvalidRequestException {
		if(name == null || name.equals("")) throw new InvalidRequestException("Veuillez entrer un nom à ajouter non nul.");
		
		if(serverData.containsKey(name)) throw new InvalidRequestException(
				"Le nom "+name+" n'a pas pu être ajouté car déjà présent sur le serveur.");
		
		for (String key : serverData.keySet()) {
			Set<String> tmp = serverData.get(key);
			for (String nname : tmp) {
				if(nicknames.contains(nname)) throw new InvalidRequestException(
						"Le nom " + name + " n'a pas pu être ajouté car le surnom "+nname+" est déjà présent sur le serveur.");
			}
		}
		
		serverData.put(name, nicknames);
		
		return new Response(true, "Utilisateur ajouté");
	}
}
