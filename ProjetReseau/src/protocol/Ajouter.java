package protocol;

import java.util.ArrayList;
import java.util.List;

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
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}
}
