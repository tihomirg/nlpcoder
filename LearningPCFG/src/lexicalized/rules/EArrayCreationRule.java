package lexicalized.rules;

import interpreter.ENonTerminalFactory;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayCreation;

import scopes.Scopes;
import scopes.SimpleScopes;
import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class EArrayCreationRule extends ERule {

	private List<Symbol> dimensions;
	private Symbol type;
	private int dimensionLength;
	private Symbol initializer;

	public EArrayCreationRule(ArrayCreation node, ENonTerminalFactory factory) {
		super(node, factory);
		this.dimensions = makeLNonTerminalList(node.dimensions());
		this.type = eNonTerminal(node);
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
	public boolean ommit() {
		// TODO Auto-generated method stub
		return false;
	}

}
