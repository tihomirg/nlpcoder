package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.LabeledStatement;

import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class LabeledStatementRule extends Rule {

	private Symbol body;
	private Symbol label;

	public LabeledStatementRule(LabeledStatement node) {
		super(node);
		
		this.label = nonTerminal(node.getLabel());
		this.body = nonTerminal(node.getBody());
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		// TODO Auto-generated method stub
		list.f(this.label).f(Terminals.COLON).f(this.body);
	}

}
