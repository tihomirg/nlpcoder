package rules;

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
		this.expression = SymbolFactory.getNonTerminal(node.getExpression());
		this.thenStatement = SymbolFactory.getNonTerminal(node.getThenStatement());
		
		this.ifTerminal = SymbolFactory.getTerminal(Tokens.IF, node);
		this.lParTerminal = SymbolFactory.getTerminal(Tokens.L_PAR, node);
		this.rParTerminal = SymbolFactory.getTerminal(Tokens.R_PAR, node);
		
		ASTNode elseNode = node.getElseStatement();
		if (elseNode != null){
		  this.elseStatement = SymbolFactory.getNonTerminal(elseNode);
		  
		  this.elseParTerminal = SymbolFactory.getTerminal(Tokens.ELSE, node);
		}
	}

	@Override
	protected String rhs() {
		return this.ifTerminal +" "+this.lParTerminal+" "+this.expression+" "+this.rParTerminal+" "+this.thenStatement+(this.elseParTerminal != null ? " "+this.elseParTerminal+" "+this.elseStatement:"");
	}
}
