package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.IfStatement;

import symbol.Symbol;
import symbol.SymbolFactory;

public class IfStatementRule extends Rule{

	private Symbol ifTerminal;
	private Symbol lParTerminal;
	private Symbol rParTerminal;
	private Symbol elseParTerminal;
	private Symbol expression;
	private Symbol elseStatement;
	private Symbol thenStatement;
	
	public IfStatementRule(IfStatement node) {
		super(SymbolFactory.getNonTerminal(node));
		this.expression = SymbolFactory.getNonTerminal(node.getExpression());
		this.thenStatement = SymbolFactory.getNonTerminal(node.getThenStatement());
		this.ifTerminal = SymbolFactory.getTerminal("if", node);
		this.lParTerminal = SymbolFactory.getTerminal("(", node);
		this.rParTerminal = SymbolFactory.getTerminal(")", node);
		ASTNode elseNode = node.getElseStatement();
		if (elseNode != null){
		  this.elseStatement = SymbolFactory.getNonTerminal(elseNode);
		  this.elseParTerminal = SymbolFactory.getTerminal("else", node);
		}
	}

	@Override
	protected String rhs() {
		return this.ifTerminal +" "+this.lParTerminal+" "+this.expression+" "+this.rParTerminal+" "+this.thenStatement+(this.elseParTerminal != null ? " "+this.elseParTerminal+" "+this.elseStatement:"");
	}
}
