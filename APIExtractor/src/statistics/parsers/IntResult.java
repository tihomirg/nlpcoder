package statistics.parsers;

public class IntResult extends Result {
	private int value;
	
	public IntResult(int value, String rest) {
		super(rest);
		this.value = value;
	}

	public int getInteger() {
		return value;
	}
}
