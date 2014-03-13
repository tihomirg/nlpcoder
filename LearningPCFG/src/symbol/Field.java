package symbol;

public class Field extends SymbolWithReceiver {

	private String name;

	public Field(String name) {
		this.name = name;
	}	
	
	public Field(String name, Symbol reciever) {
		super(reciever);
		this.name = name;
	}

	@Override
	public String head() {
		return "Field("+name+")";
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return receiverToString()+name;
	}
}
