package protocol.services;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

import protocol.InvalidRequestException;
import protocol.Response;

/**
 * Service est une classe permetant la cr�ation des diff�rents services
 */
public abstract class Service implements Serializable{
	private static final long serialVersionUID = 8674186373425255765L;
	
	private String serviceName;

	/**
	 * Constructeur de Service
	 *
	 * Permet la cr�ation d'un service
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
	 * Permet la modification de la map contenant les donn�es
	 *
	 * @param map
	 *              map qui contient les informations sur les nom et surnom
	 * @return la nouvelle map mise � jour
	 * @throws InvalidRequestException
	 *              Si le nom est ou n'est pas present dans la map suivant le service demand�
	 */
	public abstract HashMap<String, Set<String>> exec(HashMap<String, Set<String>> map) throws InvalidRequestException;

	/**
	 * Permet de g�n�rer la r�ponse qui sera envoy�e au client
	 *
	 * @param status
	 * 				Informe sur l'�tat de la requ�te
	 * @param message
	 * 				D�tail sur l'�tat de la requ�te
	 * @param map
	 * 				Les donn�es r�sultantes de l'�x�cution de la requ�te
	 *
	 * @return la g�n�ration de la r�ponse � transmettre au client
	 */
	public abstract Response createResponse(boolean status, String message, HashMap<String, Set<String>> map);
}
