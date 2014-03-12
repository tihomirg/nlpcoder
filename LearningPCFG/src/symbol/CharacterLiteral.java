package symbol;

public class CharacterLiteral implements Symbol {

	private char value;
	
	public CharacterLiteral(char value) {
		this.value = value;
	}

	@Override
	public String head() {
		return Character.toString(value);
	}

}
