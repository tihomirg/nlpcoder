package symbol;

public class StringLiteral extends Symbol {

	private String value;
	
	public StringLiteral(String value) {
		this.value = value;
	}

	@Override
	public String head() {
		return "String("+value+")";
	}

	@Override
	public String toString() {
		return value;
	}
}
