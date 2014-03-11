package lexicalized.rules;

import interpreter.ENonTerminalFactory;

import org.eclipse.jdt.core.dom.FieldAccess;

import scopes.SimpleScopes;
import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class EFieldAccessRule extends ERule {

	private Symbol exp;
	private Symbol name;

	public EFieldAccessRule(FieldAccess node, ENonTerminalFactory factory) {
		super(node, factory);
		this.exp = eNonTerminal(node.getExpression());
		this.name = eNonTerminal(node);
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.exp).f(Terminals.DOT).f(this.name);
	}
	
	public boolean ommit(){
		return true;
	}	

}
