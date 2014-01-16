package rules;

import util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.InfixExpression;

import symbol.Symbol;
import symbol.SymbolFactory;

public class InfixExpressionRule extends Rule{

	private Symbol operator;
	private Symbol leftOperand;
	private Symbol rightOperand;
	private List<Symbol> extendedOperands;
	
	public InfixExpressionRule(InfixExpression node) {
		super(node);
		this.operator = terminal(node.getOperator().toString());		
		this.leftOperand = nonTerminal(node.getLeftOperand());
		this.rightOperand = nonTerminal(node.getRightOperand());		
		this.extendedOperands = makeNonTerminalList(node.extendedOperands());
	}
	
	private List<Symbol> toExtendedOperands(){
		return toPrefixList(this.extendedOperands, this.operator);
	}	
	
	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.leftOperand).f(this.operator).f(this.rightOperand).f(toExtendedOperands());
	}
}
