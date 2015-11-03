package protocol;

import org.json.JSONObject;

public abstract class Service extends JSONObject{
	protected String serviceName;
	
	public Service() {
		this("noName");
	}
	
	public Service(String sname) {
		serviceName = sname;
	}
	
	public abstract String toJSON();
}
