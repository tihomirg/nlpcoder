package rules;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayInitializer;

import symbol.Symbol;
import symbol.SymbolFactory;
import symbol.Tokens;

public class ArrayInitializerRule extends Rule {

	private List<Symbol> expressions;
	private Symbol rcurley;
	private Symbol lcurley;
	private Symbol comma;

	public ArrayInitializerRule(ArrayInitializer node) {
		super(node);
		this.expressions = makeNonTerminalList(node.expressions());
		this.rcurley = SymbolFactory.getTerminal(Tokens.R_CURLY_BRACKET, node);
		this.lcurley = SymbolFactory.getTerminal(Tokens.L_CURLY_BRACKET, node);
		this.comma = SymbolFactory.getTerminal(Tokens.COMMA, node);
		// TODO Auto-generated constructor stub
	}

	private List<Symbol> toExpressions(){
		return toInfixList(this.expressions, this.comma);
	}
	
	@Override
	protected List<Symbol> rhsAsList() {
		List<Symbol> list = new LinkedList<Symbol>();
		list.add(this.lcurley);
		list.addAll(toExpressions());
		list.add(this.rcurley);
		return list;
	}

}
