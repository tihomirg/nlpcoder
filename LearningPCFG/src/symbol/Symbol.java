package symbol;
import java.util.Set;

import types.Type;
import definitions.Declaration;

public abstract class Symbol {
	
	public abstract String head();
	
	public abstract Type retType();
	
	public abstract boolean hasRetType();

	public abstract boolean isVariable();

	public abstract Set<Declaration> getDecls();

	public abstract boolean hasDecls();
	
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

	public String getName() {
		return null;
	}
}
