package symbol;

public class NumberLiteral implements Symbol {

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
