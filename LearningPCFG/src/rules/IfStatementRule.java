package rules;

import util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.IfStatement;

import symbol.Symbol;
import symbol.SymbolFactory;
import symbol.Terminals;

public class IfStatementRule extends Rule{

	private Symbol expression;
	private Symbol elseStatement;
	private Symbol thenStatement;
	
	public IfStatementRule(IfStatement node) {
		super(node);
		this.expression = nonTerminal(node.getExpression());
		this.thenStatement = nonTerminal(node.getThenStatement());
		
		ASTNode elseNode = node.getElseStatement();
		if (elseNode != null){
		  this.elseStatement = nonTerminal(elseNode);
		}
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(Terminals.IF).f(Terminals.L_PAR).f(this.expression).f(Terminals.R_PAR).f(this.thenStatement);
		
		if(this.elseStatement != null){
			list.f(Terminals.ELSE).f(this.elseStatement);
		}
	}
}
