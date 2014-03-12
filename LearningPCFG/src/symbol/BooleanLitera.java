package symbol;

public class BooleanLitera implements Symbol {

	private boolean value;
	
	public BooleanLitera(boolean value) {
		this.value = value;
	}

	@Override
	public String head() {
		return Boolean.toString(value);
	}

}
