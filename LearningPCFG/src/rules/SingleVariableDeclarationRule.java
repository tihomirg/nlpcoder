package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;

import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class SingleVariableDeclarationRule extends Rule {

	private Symbol type;
	private Symbol name;
	private int dimensions;
	private boolean vargs;
	private Symbol initializer;

	public SingleVariableDeclarationRule(SingleVariableDeclaration node) {
		super(node);
		
		this.type = nonTerminal(node.getType());
		
		this.name = nonTerminal(node.getName());
		this.dimensions = node.getExtraDimensions();
		
	    this.vargs = node.isVarargs();
		
		ASTNode initializer = node.getInitializer();
		if(initializer != null){
			this.initializer = nonTerminal(initializer);
		}
	}

	private List<Symbol> toDimensions(){
		return toIndexList(this.dimensions, Terminals.L_SQUARE_BRACKET, Terminals.R_SQUARE_BRACKET);
	}
	
	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.type);
		
		if(this.vargs){
			list.f(Terminals.VARGS);
		}
		
		list.f(this.name).f(toDimensions());
		
		if(this.initializer != null){
			list.f(Terminals.ASSIGN).f(this.initializer);
		}

	}

}
