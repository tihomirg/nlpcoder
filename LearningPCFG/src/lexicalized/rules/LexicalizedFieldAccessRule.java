package lexicalized.rules;

import lexicalized.symbol.LexicalizedNonTerminal;

import org.eclipse.jdt.core.dom.FieldAccess;

import scopes.Scopes;
import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class LexicalizedFieldAccessRule extends LexicalizedRule {

	private Symbol exp;
	private LexicalizedNonTerminal name;

	public LexicalizedFieldAccessRule(FieldAccess node, Scopes scopes) {
		super(node, scopes);
		this.exp = lNonTerminal(node.getExpression());
		this.name = lFieldNonTerminal(node);
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.exp).f(Terminals.DOT).f(this.name);
	}
	
	public boolean isUserDef(){
		return this.name.isUserDef();
	}	

}
