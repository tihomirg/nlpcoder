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

	private String argHeadsToString(){
		String s ="";
		
		if (args.length > 0){
			s+= args[0].head();
			for (int i = 1; i < args.length; i++) {
				s+=", "+args[i].head();
			}
		}
		
		return s;
	}
	
	@Override
	public String toString() {
		return receiverToString()+"name ("+argHeadsToString()+")";
	}

	@Override
	public String head() {
		return name;
	}
}
