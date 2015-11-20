package protocol.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import protocol.InvalidRequestException;
import protocol.Response;

/**
 * MettreAJour est une classe qui hérite de Service et permettant la mise à jour
 * d'un nom ou d'un surnom sur le serveur.
 */
public class MettreAJour extends Service {
	private static final long serialVersionUID = -4554518145285427739L;

	private String name;
	private String newName = null;
	private Set<String> nicknames = null;

	/**
	 * Constructeur de MettreAJour
	 *
	 * Permet la mise à jour d'un nom ou d'un surnom
	 *
	 * @param name
	 *            Le nom permetant de trouver les surnoms à modifier
	 * @param nicknames
	 *            Les nouveaux surnoms
	 */
	public MettreAJour(String name, Set<String> nicknames) {
		super("UPDATE");
		this.name = name;
		this.newName = name;
		this.nicknames = nicknames;
	}

	/**
	 * Constructeur de MettreAJour
	 *
	 * Permet la mise à jour d'un nom ou d'un surnom
	 *
	 * @param name
	 *            Le nom à modifier
	 * @param newName
	 *            Le nouveau nom
	 */
	public MettreAJour(String name, String newName) {
		super("UPDATE");
		this.name = name;
		this.newName = newName;
	}

	/**
	 * Constructeur de MettreAJour
	 *
	 * Permet la mise à jour d'un nom ou d'un surnom
	 *
	 * @param name
	 *            Le nom à modifier
	 * @param newName
	 *            Le nouveau nom
	 * @param nicknames
	 *            Les nouveaux surnoms
	 */
	public MettreAJour(String name, String newName, Set<String> nicknames) {
		super("UPDATE");
		this.name = name;
		this.newName = newName;
		this.nicknames = nicknames;
	}

	@Override
	public HashMap<String, Set<String>> exec(HashMap<String, Set<String>> map)
			throws InvalidRequestException {
		if (!map.containsKey(name))
			throw new InvalidRequestException(
					"Le nom "
							+ name
							+ " n'a pas pu être modifié car n'est pas présent sur le serveur.");

		// Pas de nicknames, donc modification du nom
		if (nicknames == null) {
			nicknames = map.get(name);
			map.remove(name);
			map.put(newName, nicknames);
		}
		// Les deux
		else {
			map.remove(name);
			
			for (String key : map.keySet()) {
				Set<String> tmp = map.get(key);
				for (String nname : tmp) {
					if(nicknames.contains(nname)) throw new InvalidRequestException(
							"Le nom " + name + " n'a pas pu être ajouté car le surnom "+nname+" est déjà présent sur le serveur.");
				}
			}
			
			map.put(newName, nicknames);
		}

		return map;
	}

	@Override
	public Response createResponse(boolean status, String message,
			HashMap<String, Set<String>> map) {
		return new Response(status, message);
	}
}
