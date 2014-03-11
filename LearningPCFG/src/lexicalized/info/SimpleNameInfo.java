package lexicalized.info;

public class SimpleNameInfo extends LexicalizedInfo {

	public SimpleNameInfo(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	protected String toNaiveString(){
		return this.isUserDef? "User":"API"; //Variables have names, but we use them only for the propagation.
	}

}
