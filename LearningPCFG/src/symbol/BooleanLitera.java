package symbol;

public class BooleanLitera extends Symbol {

	private boolean value;
	
	public BooleanLitera(boolean value) {
		this.value = value;
	}

	@Override
	public String head() {
		return "Boolean("+Boolean.toString(value)+")";
	}

	@Override
	public String toString() {
		return Boolean.toString(value);
	}
}
