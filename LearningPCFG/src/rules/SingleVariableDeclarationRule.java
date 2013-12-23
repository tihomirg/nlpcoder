package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;

import symbol.Symbol;
import symbol.Tokens;
import util.List;

public class SingleVariableDeclarationRule extends Rule {

	private Symbol type;
	private Symbol name;
	private int dimensions;
	private Symbol vargs;
	private Symbol initializer;
	private Symbol assign;
	private Symbol lsquare;
	private Symbol rsquare;

	public SingleVariableDeclarationRule(SingleVariableDeclaration node) {
		super(node);
		
		this.type = nonTerminal(node.getType());
		
		this.name = nonTerminal(node.getName());
		this.dimensions = node.getExtraDimensions();
		if (this.dimensions > 0){
			this.lsquare = terminal(Tokens.L_SQUARE_BRACKET, node);
			this.rsquare = terminal(Tokens.R_SQUARE_BRACKET, node);	
		}
		
		if(node.isVarargs()){
			this.vargs = terminal(Tokens.VARGS, node);
		}
		
		ASTNode initializer = node.getInitializer();
		if(initializer != null){
			this.initializer = nonTerminal(initializer);
			this.assign = terminal(Tokens.ASSIGN, node);
		}
		
		// TODO Auto-generated constructor stub
	}

	private List<Symbol> toDimensions(){
		return toIndexList(this.dimensions, this.lsquare, this.rsquare);
	}
	
	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.type);
		
		if(this.vargs != null){
			list.f(this.vargs);
		}
		
		list.f(this.name).f(toDimensions());
		
		if(this.initializer != null){
			list.f(this.assign).f(this.initializer);
		}

	}

}
