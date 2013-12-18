package rules;

import java.util.LinkedList;
import java.util.List;

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
		this.operator = SymbolFactory.getTerminal(node.getOperator().toString(), node);		
		this.leftOperand = SymbolFactory.getNonTerminal(node.getLeftOperand());
		this.rightOperand = SymbolFactory.getNonTerminal(node.getRightOperand());		
		this.extendedOperands = makeNonTerminalList(node.extendedOperands());
	}
	
	private String printExtendedOperands(){
		return printPrefixList(this.extendedOperands, this.operator);
	}	
	
	@Override
	protected String rhs() {
		return this.leftOperand+" "+this.operator+" "+this.rightOperand+printExtendedOperands();
	}
}
