package statistics.parsers;

public class StringResult extends Result {
	
	public String string;

	public StringResult(String string, String result) {
		super(result);
		this.string = string;
	}
	
	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}
	
}
