package protocol;

public class Lister extends Service {
	private int limit;
	private String startWith;
	
	public Lister() {
		super("LIST");
	}
	
	public Lister(int limit, String startWith) {
		super("LIST");
		this.limit = limit;
		this.startWith = startWith;
	}
	
	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}
}
