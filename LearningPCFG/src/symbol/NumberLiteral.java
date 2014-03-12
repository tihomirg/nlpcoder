package symbol;

public class NumberLiteral extends Symbol {

	private String number;
	
	public NumberLiteral(String number) {
		this.number = number;
	}

	@Override
	public String head() {
		// TODO Auto-generated method stub
		return number;
	}

}
