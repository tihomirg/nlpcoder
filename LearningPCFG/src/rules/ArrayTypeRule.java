package rules;

import util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayType;

import symbol.Symbol;
import symbol.SymbolFactory;
import symbol.Terminals;

public class ArrayTypeRule extends Rule {

	private Symbol rcurley;
	private Symbol lcurley;
	private Symbol elementType;
	private int dimentsions;

	public ArrayTypeRule(ArrayType node) {
		super(node);
		// TODO Auto-generated constructor stub
		this.elementType = nonTerminal(node.getElementType());
		this.dimentsions = node.getDimensions();
	}
	
	private List<Symbol> toDimensions(){
		return toIndexList(this.dimentsions, Terminals.L_CURLY_BRACKET, Terminals.R_CURLY_BRACKET);
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.elementType).f(toDimensions());
	}

}
