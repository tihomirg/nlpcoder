package symbol;

public class StringLiteral implements Symbol {

	private String value;
	
	public StringLiteral(String value) {
		this.value = value;
	}

	@Override
	public String head() {
		return value;
	}

}
