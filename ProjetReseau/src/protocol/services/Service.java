package protocol.services;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

import protocol.InvalidRequestException;
import protocol.Response;

/**
 * Service est une classe permetant la création des différents services
 */
public abstract class Service implements Serializable{
	private static final long serialVersionUID = 8674186373425255765L;
	
	private String serviceName;

	/**
	 * Constructeur de Service
	 *
	 * Permet la création d'un service
	 *
	 * @param sname
	 * 				Nom du service
	 */
	public Service(String sname) {
		serviceName = sname;
	}

	/**
	 * Accesseur en lecture du nom du service
	 *
	 * @return le nom du service
	 */
	public String getServiceName(){
		return serviceName;
	}

	/**
	 * Permet la modification de la map contenant les données
	 *
	 * @param map
	 *              map qui contient les informations sur les nom et surnom
	 * @return la nouvelle map mise à jour
	 * @throws InvalidRequestException
	 *              Si le nom est ou n'est pas present dans la map suivant le service demandé
	 */
	public abstract HashMap<String, Set<String>> exec(HashMap<String, Set<String>> map) throws InvalidRequestException;

	/**
	 * Permet de générer la réponse qui sera envoyée au client
	 *
	 * @param status
	 * 				Informe sur l'état de la requête
	 * @param message
	 * 				Détail sur l'état de la requête
	 * @param map
	 * 				Les données résultantes de l'éxécution de la requête
	 *
	 * @return la génération de la réponse à transmettre au client
	 */
	public abstract Response createResponse(boolean status, String message, HashMap<String, Set<String>> map);
}
