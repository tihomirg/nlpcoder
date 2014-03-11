package lexicalized.rules;

import interpreter.ENonTerminalFactory;

import org.eclipse.jdt.core.dom.Assignment;

import scopes.Scopes;
import scopes.SimpleScopes;
import symbol.Symbol;
import util.List;

public class EAssignmentRule extends ERule {

	private Symbol left;
	private Symbol right;
	private Symbol operator;

	public EAssignmentRule(Assignment node, ENonTerminalFactory factory) {
		super(node, factory);
		
		this.left = eNonTerminal(node.getLeftHandSide());
		this.right= eNonTerminal(node.getRightHandSide());
		this.operator = terminal(node.getOperator().toString());
	}

	@Override
	public boolean ommit() {
		//TODO: If "operator" is "=", "lhs" is a variable, we omit the assignment, and add it to the e-table.
		return false;
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.left).f(this.operator).f(this.right);
	}

}
