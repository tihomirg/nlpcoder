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
		this.elementType = SymbolFactory.getNonTerminal(node.getElementType());
		this.dimentsions = node.getDimensions();
		this.rcurley = SymbolFactory.getTerminal(Tokens.R_CURLY_BRACKET, node);
		this.lcurley = SymbolFactory.getTerminal(Tokens.L_CURLY_BRACKET, node);		
		
	}
	
	private List<Symbol> toDimensions(){
		return toIndexList(this.dimentsions, this.lcurley, this.rcurley);
	}

	@Override
	protected List<Symbol> rhsAsList() {
		List<Symbol> list = new LinkedList<Symbol>();
		list.add(this.elementType);
		list.addAll(toDimensions());

		return list;
	}

}
