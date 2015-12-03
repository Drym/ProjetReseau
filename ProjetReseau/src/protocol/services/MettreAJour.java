package protocol.services;

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
	public Response exec() throws InvalidRequestException {
		if(newName == null || newName.equals("")) throw new InvalidRequestException("Veuillez entrer un nouveau nom non nul.");
		
		if (!serverData.containsKey(name))
			throw new InvalidRequestException(
					"Le nom "
							+ name
							+ " n'a pas pu être modifié car n'est pas présent sur le serveur.");

		// Pas de nicknames, donc modification du nom
		if (nicknames == null) {
			nicknames = serverData.get(name);
			serverData.remove(name);
			serverData.put(newName, nicknames);
		}
		// Les deux ou juste les surnoms
		else {
			if(nicknames.isEmpty()) throw new InvalidRequestException("Veuiller entrer une liste de surnoms non vide.");
			
			if(nicknames.contains("")) throw new InvalidRequestException("Veuiller entrer des surnoms non vides.");
			
			for (String key : serverData.keySet()) {
				Set<String> tmp = serverData.get(key);
				if(!key.equals(name)){
					for (String nname : tmp) {
						if(nicknames.contains(nname)) throw new InvalidRequestException(
								"Le nom " + name + " n'a pas pu être ajouté car le surnom "+nname+" est déjà présent sur le serveur.");
					}
				}
			}
			
			serverData.remove(name);
			serverData.put(newName, nicknames);
		}

		return new Response(true, "Utilisateur modifié");
	}
}
