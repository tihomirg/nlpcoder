package symbol;

import org.eclipse.jdt.core.dom.ASTNode;


public class ValueScopeSymbol extends ScopeSymbol {

	private ASTNode value; 
	
	public ValueScopeSymbol(String token) {
		super(token);
	}	
	
	public ValueScopeSymbol(String token, ASTNode value) {
		super(token);
		this.setValue(value);
	}

	public ASTNode getValue() {
		return value;
	}

	public void setValue(ASTNode value) {
		this.value = value;
	}
	
	public String toString(){
		return super.toString()+ (value != null ? " = "+value : "");
	}

}
