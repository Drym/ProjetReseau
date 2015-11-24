package protocol.services;

import java.util.HashMap;
import java.util.Set;

import protocol.InvalidRequestException;
import protocol.Response;

/**
 * Lister est une classe qui hérite de Service et permettant d'obtenir la liste
 * des noms et surnoms présent sur le serveur
 */
public class Lister extends Service {
	private static final long serialVersionUID = 3350070951937150357L;

	private int limit;
	private String startWith;

	/**
	 * Constructeur de Lister
	 *
	 * Permet l'obtention de la liste des noms et surnoms
	 */
	public Lister() {
		super("LIST");
		limit = 0;
		startWith = "";
	}

	/**
	 * Constructeur de Lister
	 *
	 * Permet l'obtention de la liste des noms et surnoms
	 *
	 * @param limit
	 *            Permet de limiter le nombre de ligne retourner
	 * @param startWith
	 *            Permet de lister uniquement tous les noms qui commencent par
	 *            startWith
	 */
	public Lister(int limit, String startWith) {
		super("LIST");
		this.limit = limit;
		this.startWith = startWith;
	}

	@Override
	public HashMap<String, Set<String>> exec(HashMap<String, Set<String>> map)
			throws InvalidRequestException {
		// Pas de modification des données du serveur
		return map;
	}

	@Override
	public Response createResponse(boolean status, String message,
			HashMap<String, Set<String>> map) {
		// TODO : Pour l'instant limit et startWith sont ignorés
		return new Response(status, message, map);
	}

}
