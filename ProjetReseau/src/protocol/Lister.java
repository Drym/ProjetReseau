package protocol;

import org.json.JSONObject;

public class Lister extends Service {
	private int limit = 0;
	private String startWith = "test";
	
	public Lister() {
		super("LIST");
	}
	
	public Lister(int limit, String startWith) {
		super("LIST");
		this.limit = limit;
		this.startWith = startWith;
	}
	
	@Override
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.append("name", serviceName);
		json.append("limit", limit);
		json.append("startWith", startWith);
		
		return json;
	}
}
