package symbol;

public abstract class SymbolWithReceiver implements Symbol {

	protected Symbol receiver;

	public SymbolWithReceiver() {}	
	
	public SymbolWithReceiver(Symbol receiver) {
		this.receiver = receiver;
	}

	protected String receiverToString() {
		if(receiver != null){
			return receiver +" . ";
		} else return "";
	}

}