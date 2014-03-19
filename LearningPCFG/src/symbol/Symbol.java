package symbol;

import java.util.Set;

import definitions.Declaration;

public abstract class Symbol {
	
	public abstract String head();
	
	public boolean isVariable(){
		return false;
	}
	
	public String getName(){
		return null;
	}

	public Set<Declaration> getDecls() {
		return null;
	}

	protected static String symbolHeadsToString(Symbol[] args) {
		String s ="";
		
		if (args.length > 0){
			s+= args[0].head();
			for (int i = 1; i < args.length; i++) {
				s+=", "+args[i].head();
			}
		}
		return s;
	}
	
	public boolean isTemp() {
		return false;
	}	
}
