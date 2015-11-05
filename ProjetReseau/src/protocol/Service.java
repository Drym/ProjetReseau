package protocol;

//import json.JSONObject;
import com.atlassian.clover.reporters.json.JSONObject;

public abstract class Service extends JSONObject {
	protected String serviceName;
	
	public Service() {
		this("noName");
	}
	
	public Service(String sname) {
		serviceName = sname;
	}
	
	public abstract String toJSON();
}
