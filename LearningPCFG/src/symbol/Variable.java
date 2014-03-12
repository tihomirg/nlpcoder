package symbol;

public class Variable implements Symbol {
	
	private String name;
	
	public Variable(String name) {
		this.name = name;
	}

	@Override
	public String head() {
		return name;
	}
}
