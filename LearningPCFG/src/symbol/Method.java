package symbol;

public class Method extends SymbolWithReceiver {
	private String name;
	private Symbol[] args;
	
	public Method(String name, Symbol receiver, Symbol[] args) {
		super(receiver);
		this.name = name;
		this.args = args;
	}

	public Method(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Symbol[] getArgs() {
		return args;
	}

	public void setArgs(Symbol[] args) {
		this.args = args;
	}

	private String argsToString(){
		return symbolHeadsToString(this.args);
	}

	@Override
	public String toString() {
		return receiverToString()+"name ("+argsToString()+")";
	}

	@Override
	public String head() {
		return name;
	}
}
