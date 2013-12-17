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
		super(SymbolFactory.getNonTerminal(node));
		this.operator = SymbolFactory.getTerminal(node.getOperator().toString(), node);		
		this.leftOperand = SymbolFactory.getNonTerminal(node.getLeftOperand());
		this.rightOperand = SymbolFactory.getNonTerminal(node.getRightOperand());		
		this.extendedOperands = makeExtendedOperands(node.extendedOperands());
	}
	
	private List<Symbol> makeExtendedOperands(List<ASTNode> operands){
		List<Symbol> list = new LinkedList<Symbol>();
		for(ASTNode operand: operands){
		  list.add(SymbolFactory.getNonTerminal(operand));	
		}
		return list;
	}
	
	private String printExtendedOperands(){
		String s ="";
		for(Symbol operand: this.extendedOperands){
			s += (" "+this.operator +" "+operand); 
		}
		return s;
	}	
	
	@Override
	protected String rhs() {
		return this.leftOperand+" "+this.operator+" "+this.rightOperand+printExtendedOperands();
	}
}
