package rules;

import util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Assignment;

import symbol.Symbol;
import symbol.SymbolFactory;

public class AssignmentRule extends Rule {

	private Symbol operator;
	private Symbol right;
	private Symbol left;

	public AssignmentRule(Assignment node) {
		super(node);
		this.left = nonTerminal(node.getLeftHandSide());
		this.right= nonTerminal(node.getRightHandSide());
		this.operator = terminal(node.getOperator().toString());
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.left).f(this.operator).f(this.right);
	}

}
