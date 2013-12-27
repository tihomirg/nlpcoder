package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.SuperFieldAccess;

import symbol.Symbol;
import symbol.Tokens;
import util.List;

public class SuperFieldAccessRule extends Rule {

	private Symbol qualifier;
	private Symbol field;
	private Symbol superTerminal;
	private Symbol dot;

	public SuperFieldAccessRule(SuperFieldAccess node) {
		super(node);
		// TODO Auto-generated constructor stub
		this.field = nonTerminal(node.getName());
		
		ASTNode qualifier = node.getQualifier();
		if(qualifier != null)
			this.qualifier = nonTerminal(qualifier);
		
		this.superTerminal = terminal(Tokens.SUPER, node);
		this.dot = terminal(Tokens.DOT, node);
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		if(this.qualifier != null) list.f(this.qualifier).f(this.dot);
		
		list.f(this.superTerminal).f(this.dot).f(this.field);
	}

}
