package rules;

import util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayCreation;

import symbol.Symbol;
import symbol.SymbolFactory;
import symbol.Tokens;

public class ArrayCreationRule extends Rule {

	private List<Symbol> dimensions;
	private Symbol type;
	
	private Symbol newTerminal;
	private Symbol rsquareTerminal;
	private Symbol lsquareTerminal;
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
		
		this.newTerminal = terminal(Tokens.NEW, node);
		this.lsquareTerminal = terminal(Tokens.L_SQUARE_BRACKET, node);
		this.rsquareTerminal = terminal(Tokens.R_SQUARE_BRACKET, node);
		// TODO Auto-generated constructor stub
	}


	private List<Symbol> toExpressionIndexes(){
		return toIndexList(this.dimensions, this.lsquareTerminal, this.rsquareTerminal);
	}
	
	
	private List<Symbol> toEmptyIndexes(){
		return toIndexList(this.dimensionLength - this.dimensions.size(), this.lsquareTerminal, this.rsquareTerminal);
	}		
	
	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.newTerminal).f(this.type).f(toExpressionIndexes()).f(toEmptyIndexes());
		
		if(this.initializer != null){
			list.f(this.initializer);
		}
	}

}
