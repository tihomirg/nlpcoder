package symbol;

public class BooleanLitera extends Symbol {

	private boolean value;
	
	public BooleanLitera(boolean value) {
		this.value = value;
	}

	@Override
	public String head() {
		return Boolean.toString(value);
	}

}
