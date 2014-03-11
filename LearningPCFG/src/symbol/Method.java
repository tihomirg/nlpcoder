package symbol;

public class Method implements Symbol {
	private String name;
	private Symbol receiver;
	private Symbol[] args;
	
	public Method(String name, Symbol receiver, Symbol[] args) {
		this.name = name;
		this.receiver = receiver;
		this.args = args;
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
	
	private String receiverToString(){
		if(receiver != null){
			return receiver +".";
		} else return "";
	}
	
	@Override
	public String toString() {
		return "name ("+argHeadsToString()+")";
	}

	@Override
	public String head() {
		return name;
	}
}
