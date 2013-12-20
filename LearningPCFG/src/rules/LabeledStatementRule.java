package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.LabeledStatement;

import symbol.Symbol;
import symbol.Tokens;
import util.List;

public class LabeledStatementRule extends Rule {

	private Symbol body;
	private Symbol label;
	private Symbol colon;

	public LabeledStatementRule(LabeledStatement node) {
		super(node);
		
		this.label = nonTerminal(node.getLabel());
		this.body = nonTerminal(node.getBody());
		
		this.colon = terminal(Tokens.COLON, node);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		// TODO Auto-generated method stub
		list.f(this.label).f(this.colon).f(this.label);
	}

}
