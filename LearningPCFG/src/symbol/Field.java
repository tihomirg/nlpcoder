package symbol;

public class Field implements Symbol {

	private String name;

	public Field(String name) {
		this.name = name;
	}

	@Override
	public String head() {
		return name;
	}
	
	public String toString(){
		return name;
	}
}
