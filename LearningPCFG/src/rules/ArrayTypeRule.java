package rules;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayType;

import symbol.Symbol;
import symbol.SymbolFactory;
import symbol.Tokens;

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
		this.rcurley = terminal(Tokens.R_CURLY_BRACKET, node);
		this.lcurley = terminal(Tokens.L_CURLY_BRACKET, node);		
		
	}
	
	private List<Symbol> toDimensions(){
		return toIndexList(this.dimentsions, this.lcurley, this.rcurley);
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.add(this.elementType);
		list.addAll(toDimensions());
	}

}
