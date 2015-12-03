package protocol;

/**
 * InvalidRequestException est une classe permetant de gérer les exceptions (côté serveur).
 */
public class InvalidRequestException extends Exception {
	private static final long serialVersionUID = 6648124959414519238L;
	private String messageError;

	/**
	 * Constructeur de InvalidRequestException
	 *
	 * @param messageError
	 * 				Descriptif de l'erreur
	 */
	public InvalidRequestException(String messageError) {
		this.messageError = messageError;
	}

	/**
	 * Accesseur en lecture de messageError
	 *
	 * @return le descriptif de l'erreur
	 */
	public String getMessageError(){
		return messageError;
	}
}
