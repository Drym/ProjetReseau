package protocol;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public class Response implements Serializable{
	private static final long serialVersionUID = -1375764047129249512L;
	
	private boolean status;
	private HashMap<String, Set<String>> data;
	private String message;
	
	public Response(boolean status, String message) {
		this.status = status;
		this.message = message;
		data = new HashMap<>();
	}
	
	public Response(boolean status, String message, HashMap<String, Set<String>> data) {
		this(status, message);
		this.data = data;
	}
	
	public boolean getStatus(){
		return status;
	}
	
	public HashMap<String, Set<String>> getData(){
		return data;
	}
	
	public String getMessage(){
		return message;
	}
}
