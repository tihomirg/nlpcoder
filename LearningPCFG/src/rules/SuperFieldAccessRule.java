package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.SuperFieldAccess;

import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class SuperFieldAccessRule extends Rule {

	private Symbol qualifier;
	private Symbol field;

	public SuperFieldAccessRule(SuperFieldAccess node) {
		super(node);
		// TODO Auto-generated constructor stub
		this.field = nonTerminal(node.getName());
		
		ASTNode qualifier = node.getQualifier();
		if(qualifier != null)
			this.qualifier = nonTerminal(qualifier);
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		if(this.qualifier != null) list.f(this.qualifier).f(Terminals.DOT);
		
		list.f(Terminals.SUPER).f(Terminals.DOT).f(this.field);
	}

}
