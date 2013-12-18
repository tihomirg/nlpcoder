package rules;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;

import symbol.Symbol;
import symbol.SymbolFactory;
import symbol.Tokens;

public class VariableDeclarationExpressionRule extends Rule {

	private Symbol type;
	private List<Symbol> fragments;
	private Symbol commaTerminal;
	
	public VariableDeclarationExpressionRule(VariableDeclarationExpression node) {
		super(node);
		
		this.type = SymbolFactory.getNonTerminal(node.getType());
		this.fragments = makeNonTerminalList(node.fragments());
		this.commaTerminal = SymbolFactory.getTerminal(Tokens.COMMA, node);
		// TODO Auto-generated constructor stub
	}

	private String printFragments(){
		return printInfixList(this.fragments, this.commaTerminal);
	}
	
	@Override
	protected String rhs() {
		// TODO Auto-generated method stub
		return this.type+" "+printFragments();
	}

}
