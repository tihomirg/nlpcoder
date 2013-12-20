package rules;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.IfStatement;

import symbol.Symbol;
import symbol.SymbolFactory;
import symbol.Tokens;

public class IfStatementRule extends Rule{

	private Symbol ifTerminal;
	private Symbol lParTerminal;
	private Symbol rParTerminal;
	private Symbol elseParTerminal;
	private Symbol expression;
	private Symbol elseStatement;
	private Symbol thenStatement;
	
	public IfStatementRule(IfStatement node) {
		super(node);
		this.expression = nonTerminal(node.getExpression());
		this.thenStatement = nonTerminal(node.getThenStatement());
		
		this.ifTerminal = terminal(Tokens.IF, node);
		this.lParTerminal = terminal(Tokens.L_PAR, node);
		this.rParTerminal = terminal(Tokens.R_PAR, node);
		
		ASTNode elseNode = node.getElseStatement();
		if (elseNode != null){
		  this.elseStatement = nonTerminal(elseNode);
		  this.elseParTerminal = terminal(Tokens.ELSE, node);
		}
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.add(this.ifTerminal);
		list.add(this.lParTerminal);
		list.add(this.expression);
		list.add(this.rParTerminal);
		list.add(this.thenStatement);
		
		if(this.elseParTerminal != null){
			list.add(this.elseParTerminal);
			list.add(this.elseStatement);
		}
	}
}
