package rules;

import java.util.LinkedList;
import java.util.List;

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
		this.type = SymbolFactory.getNonTerminal(node.getType());
		this.dimensionLength = node.getType().getDimensions();
		
		ASTNode initializer = node.getInitializer();
		if (initializer != null){
			this.initializer = SymbolFactory.getNonTerminal(initializer);
		}
		
		this.newTerminal = SymbolFactory.getTerminal(Tokens.NEW, node);
		this.lsquareTerminal = SymbolFactory.getTerminal(Tokens.L_SQUARE_BRACKET, node);
		this.rsquareTerminal = SymbolFactory.getTerminal(Tokens.R_SQUARE_BRACKET, node);
		// TODO Auto-generated constructor stub
	}


	private List<Symbol> toExpressionIndexes(){
		return toIndexList(this.dimensions, this.lsquareTerminal, this.rsquareTerminal);
	}
	
	
	private List<Symbol> toEmptyIndexes(){
		return toIndexList(this.dimensionLength - this.dimensions.size(), this.lsquareTerminal, this.rsquareTerminal);
	}		
	
	@Override
	protected List<Symbol> rhsAsList() {
		List<Symbol> list = new LinkedList<Symbol>();
		list.add(this.newTerminal);
		list.add(this.type);
		list.addAll(toExpressionIndexes());
		list.addAll(toEmptyIndexes());
		
		if(this.initializer != null){
			list.add(this.initializer);
		}
		
		return list;
	}

}
