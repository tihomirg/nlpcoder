package symbol;

public class Variable extends Symbol {
	
	private String name;
	
	public Variable(String name) {
		this.name = name;
	}

	@Override
	public String head() {
		return name;
	}
	
	@Override
	public boolean isVariable(){
		return true;
	}
}
