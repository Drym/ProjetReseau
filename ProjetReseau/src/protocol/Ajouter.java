package protocol;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Ajouter extends Service {
	private String name;
	private List<String> nicknames;
	
	public Ajouter() {
		super("ADD");
		nicknames = new ArrayList<>();
	}
	
	public Ajouter(String name, List<String> nicknames) {
		super("ADD");
		this.name = name;
		this.nicknames = nicknames;
	}
	
	@Override
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.append("name", serviceName);
		json.append("name", name);
		JSONArray jsonNicknames = new JSONArray(nicknames);
		json.append("nicknames", jsonNicknames);
		
		return json;
	}
}
