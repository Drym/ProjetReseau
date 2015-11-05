package protocol.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import protocol.InvalidRequestException;
import protocol.Response;

public class Ajouter extends Service {
	private static final long serialVersionUID = -8413665337871880822L;
	
	private String name;
	private Set<String> nicknames;
	
	public Ajouter() {
		super("ADD");
		nicknames = new HashSet<String>();
	}
	
	public Ajouter(String name, Set<String> nicknames) {
		super("ADD");
		this.name = name;
		this.nicknames = nicknames;
	}

	@Override
	public HashMap<String, Set<String>> exec(HashMap<String, Set<String>> map) throws InvalidRequestException {
		if(map.containsKey(name)) throw new InvalidRequestException(
				"Le nom "+name+" n'a pas pu être ajouté car déjà présent sur le serveur.");
		
		map.put(name, nicknames);
		
		return map;
	}

	@Override
	public Response createResponse(boolean status, String message, HashMap<String, Set<String>> map) {
		return new Response(status, message);
	}
}
