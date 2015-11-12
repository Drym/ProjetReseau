package protocol;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

/**
 * Response est une classe permetant la cr�ation d'une r�ponse � transmettre au client
 */
public class Response implements Serializable{
	private static final long serialVersionUID = -1375764047129249512L;
	
	private boolean status;
	private HashMap<String, Set<String>> data;
	private String message;

	/**
	 * Constructeur de Response
	 *
	 * Permet la cr�ation d'un r�ponse
	 *
	 * @param status
	 * 				Informe sur l'�tat de la requ�te
	 * @param message
	 * 				D�tail sur l'�tat de la requ�te
	 */
	public Response(boolean status, String message) {
		this.status = status;
		this.message = message;
		data = new HashMap<>();
	}

	/**
	 * Constructeur de Response
	 *
	 * Permet la cr�ation d'un r�ponse
	 *
	 * @param status
	 * 				Informe sur l'�tat de la requ�te
	 * @param message
	 *  			D�tail sur l'�tat de la requ�te
	 * @param data
	 * 				Les donn�es r�sultantes de l'�x�cution de la requ�te
	 */
	public Response(boolean status, String message, HashMap<String, Set<String>> data) {
		this(status, message);
		this.data = data;
	}

	/**
	 * Accesseur en lecture du status de la r�ponse
	 *
	 * @return le status de la r�ponse
	 */
	public boolean getStatus(){
		return status;
	}

	/**
	 * Accesseur des donn�es de la r�ponse
	 *
	 * @return une HashMap contenant les donn�es
	 */
	public HashMap<String, Set<String>> getData(){
		return data;
	}

	/**
	 * Accesseur en lecture du message de la r�ponse
	 *
	 * @return le message de la r�ponse
	 */
	public String getMessage(){
		return message;
	}
}
