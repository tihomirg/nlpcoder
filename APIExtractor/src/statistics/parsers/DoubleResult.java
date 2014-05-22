package statistics.parsers;

public class DoubleResult extends Result {
	private double value;
	
	public DoubleResult(double value, String rest) {
		super(rest);
		this.value = value;
	}

	public double getDouble() {
		return value;
	}
}
