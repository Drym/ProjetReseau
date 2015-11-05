package protocol.services;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

import org.json.JSONObject;

import protocol.InvalidRequestException;
import protocol.Response;

public abstract class Service implements Serializable{
	private static final long serialVersionUID = 8674186373425255765L;
		
	public Service() {
	}
	
	public abstract HashMap<String, Set<String>> exec(HashMap<String, Set<String>> map) throws InvalidRequestException;
	public abstract Response createResponse(boolean status, String message, HashMap<String, Set<String>> map);
}
