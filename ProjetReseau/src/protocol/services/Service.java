package protocol.services;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import protocol.InvalidRequestException;
import protocol.Response;

/**
 * Service est une classe permettant la création des différents services. Cette
 * classe est abstraite, par conséquent pour utiliser un service il est
 * nécessaire d'instancier l'une des classes filles correspondant aus service
 * souhaité. Cette classe est ensuite utilisée côté serveur pour exécuter la
 * requête et pour générer la réponse à envoyer au client.
 */
public abstract class Service implements Serializable {
	private static final long serialVersionUID = 8674186373425255765L;

	/**
	 * Structure de données Thread-safe contenant les noms et les surnoms
	 * associés
	 */
	protected static Hashtable<String, Set<String>> serverData = initializeServerData();

	private String serviceName;

	/**
	 * Constructeur de Service
	 *
	 * Permet la création d'un service
	 *
	 * @param sname
	 *            Nom du service
	 */
	public Service(String sname) {
		serviceName = sname;
	}

	/**
	 * Accesseur en lecture du nom du service
	 *
	 * @return le nom du service
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * Exécute la requête client, modifie les données si nécessaire et génère la
	 * réponse voulue. Lance une exception de type InvalidRequestException en
	 * cas de problème. Cette méthode est utilisée uniquement par le serveur.
	 *
	 * @return la réponse Response créée
	 * @throws InvalidRequestException
	 *             Exception levée lorsque la requête n'a pas pu s'exécuter
	 *             correctement (ajout d'un nom déjà présent, suppression d'un
	 *             nom inexistant, etc)
	 */
	public abstract Response exec() throws InvalidRequestException;

	// Initialise la structure de données avec des données par défaut
	private static Hashtable<String, Set<String>> initializeServerData() {
		Hashtable<String, Set<String>> map = new Hashtable<>();

		for (int i = 1; i <= 3; i++) {
			Set<String> set = new HashSet<>();
			set.add("Surname" + i + ".1");
			set.add("Surname" + i + ".2");
			set.add("Surname" + i + ".3");
			map.put("Name" + i, set);
		}

		return map;
	}
}
