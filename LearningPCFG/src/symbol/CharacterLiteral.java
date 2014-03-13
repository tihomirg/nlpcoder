package symbol;

public class CharacterLiteral extends Symbol {

	private char value;
	
	public CharacterLiteral(char value) {
		this.value = value;
	}

	@Override
	public String head() {
		return "Char("+Character.toString(value)+")";
	}

	@Override
	public String toString() {
		return Character.toString(value);
	}

	
}
