package protocol;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

/**
 * Response est une classe permettant la création d'une réponse à transmettre au client
 */
public class Response implements Serializable{
	private static final long serialVersionUID = -1375764047129249512L;
	
	private boolean status;
	private HashMap<String, Set<String>> data;
	private String message;

	/**
	 * Constructeur de Response
	 *
	 * Permet la création d'un réponse
	 *
	 * @param status
	 * 				Informe sur l'état de la requête
	 * @param message
	 * 				Détail sur l'état de la requête
	 */
	public Response(boolean status, String message) {
		this.status = status;
		this.message = message;
		data = new HashMap<>();
	}

	/**
	 * Constructeur de Response
	 *
	 * Permet la création d'un réponse
	 *
	 * @param status
	 * 				Informe sur l'ï¿½tat de la requête
	 * @param message
	 *  			Détail sur l'ï¿½tat de la requête
	 * @param data
	 * 				Les données résultantes de l'éxécution de la requête
	 */
	public Response(boolean status, String message, HashMap<String, Set<String>> data) {
		this(status, message);
		this.data = data;
	}

	/**
	 * Accesseur en lecture du status de la réponse
	 *
	 * @return le status de la réponse
	 */
	public boolean getStatus(){
		return status;
	}

	/**
	 * Accesseur des données de la réponse
	 *
	 * @return une HashMap contenant les données
	 */
	public HashMap<String, Set<String>> getData(){
		return data;
	}

	/**
	 * Accesseur en lecture du message de la réponse
	 *
	 * @return le message de la réponse
	 */
	public String getMessage(){
		return message;
	}
}
