package symbol;

public abstract class Symbol {
	
	public abstract String head();
	
	public boolean isVariable(){
		return false;
	}
}
