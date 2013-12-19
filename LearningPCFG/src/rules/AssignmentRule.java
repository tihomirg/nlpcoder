package rules;

import java.util.LinkedList;
import java.util.List;

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
		this.left = SymbolFactory.getNonTerminal(node.getLeftHandSide());
		this.right= SymbolFactory.getNonTerminal(node.getRightHandSide());
		this.operator = SymbolFactory.getTerminal(node.getOperator().toString(), node);
	}

	@Override
	protected List<Symbol> rhsAsList() {
		List<Symbol> list = new LinkedList<Symbol>();
		list.add(this.left);
		list.add(this.operator);
		list.add(this.right);
		return list;
	}

}
