package symbol;
import org.eclipse.jdt.core.dom.ASTNode;

import rules.Rule;

public abstract class Symbol {
	
	public boolean equals(Object obj){
		if (obj == null) return false;
		if (obj instanceof Symbol) return this.hashCode() == obj.hashCode();
		else return false;
	}
}
