package protocol;

public class InvalidRequestException extends Exception {
	private String messageError;
	
	public InvalidRequestException(String messageError) {
		this.messageError = messageError;
	}
	
	public String getMessageError(){
		return messageError;
	}
}
