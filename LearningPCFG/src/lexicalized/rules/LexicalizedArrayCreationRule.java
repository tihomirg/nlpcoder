package lexicalized.rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayCreation;

import scopes.Scopes;
import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class LexicalizedArrayCreationRule extends LexicalizedRule {

	private List<Symbol> dimensions;
	private Symbol type;
	private int dimensionLength;
	private Symbol initializer;

	public LexicalizedArrayCreationRule(ArrayCreation node, Scopes scopes) {
		super(node, scopes);
		this.dimensions = makeLNonTerminalList(node.dimensions());
		this.type = lArrayCreationNonTerminal(node);
		this.dimensionLength = node.getType().getDimensions();
		
		ASTNode initializer = node.getInitializer();
		if (initializer != null){
			this.initializer = nonTerminal(initializer);
		}
	}


	private List<Symbol> toExpressionIndexes(){
		return toIndexList(this.dimensions, Terminals.L_SQUARE_BRACKET, Terminals.R_SQUARE_BRACKET);
	}
	
	
	private List<Symbol> toEmptyIndexes(){
		return toIndexList(this.dimensionLength - this.dimensions.size(), Terminals.L_SQUARE_BRACKET, Terminals.R_SQUARE_BRACKET);
	}		
	
	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(Terminals.NEW).f(this.type).f(toExpressionIndexes()).f(toEmptyIndexes());
		
		if(this.initializer != null){
			list.f(this.initializer);
		}
	}

	@Override
	public boolean isUserDef() {
		// TODO Auto-generated method stub
		return false;
	}

}
