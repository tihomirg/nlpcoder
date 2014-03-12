package symbol;

public class CharacterLiteral extends Symbol {

	private char value;
	
	public CharacterLiteral(char value) {
		this.value = value;
	}

	@Override
	public String head() {
		return Character.toString(value);
	}

}
