package statistics.parsers;

public abstract class Result {
	private String rest;
	
	public Result() {}
	
	public Result(String rest) {
		this.rest = rest;
	}

	public String getRest() {
		return rest;
	}
	public void setRest(String rest) {
		this.rest = rest;
	}
}
