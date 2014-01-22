package lexicalized.rules;

import org.eclipse.jdt.core.dom.Assignment;

import scopes.Scopes;
import scopes.SimpleScopes;
import symbol.Symbol;
import util.List;

public class LexicalizedAssignmentRule extends LexicalizedRule {

	private Symbol left;
	private Symbol right;
	private Symbol operator;

	public LexicalizedAssignmentRule(Assignment node, SimpleScopes scopes) {
		super(node, scopes);
		
		this.left = lNonTerminal(node.getLeftHandSide());
		this.right= lNonTerminal(node.getRightHandSide());
		this.operator = terminal(node.getOperator().toString());
	}

	@Override
	public boolean isUserDef() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.left).f(this.operator).f(this.right);
	}

}
