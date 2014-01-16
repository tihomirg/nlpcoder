package rules;

import util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayCreation;

import symbol.Symbol;
import symbol.SymbolFactory;
import symbol.Terminals;

public class ArrayCreationRule extends Rule {

	private List<Symbol> dimensions;
	private Symbol type;
	
	private int dimensionLength;
	private Symbol initializer;

	public ArrayCreationRule(ArrayCreation node) {
		super(node);
		this.dimensions = makeNonTerminalList(node.dimensions());
		this.type = nonTerminal(node.getType());
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

}
