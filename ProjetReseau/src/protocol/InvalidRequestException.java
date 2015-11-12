package protocol;

/**
 * InvalidRequestException est une classe permetant de gérer les Exception
 */
public class InvalidRequestException extends Exception {
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
