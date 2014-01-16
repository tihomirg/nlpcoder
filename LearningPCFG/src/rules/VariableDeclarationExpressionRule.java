package rules;

import util.List;

import org.eclipse.jdt.core.dom.VariableDeclarationExpression;

import symbol.Symbol;
import symbol.Terminals;

public class VariableDeclarationExpressionRule extends Rule {

	private Symbol type;
	private List<Symbol> fragments;
	
	public VariableDeclarationExpressionRule(VariableDeclarationExpression node) {
		super(node);
		
		this.type = nonTerminal(node.getType());
		this.fragments = makeNonTerminalList(node.fragments());
	}

	private List<Symbol> toFragments(){
		return toInfixList(this.fragments, Terminals.COMMA);
	}
	
	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.type).f(toFragments());
	}

}
