package symbol;

public class NumberLiteral extends Symbol {

	private String number;
	
	public NumberLiteral(String number) {
		this.number = number;
	}

	@Override
	public String head() {
		return number;
	}

	@Override
	public String toString() {
		return number;
	}

}
