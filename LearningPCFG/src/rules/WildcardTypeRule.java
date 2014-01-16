package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.WildcardType;

import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class WildcardTypeRule extends Rule {

	private Symbol bound;
	private Symbol boundTerminal;

	public WildcardTypeRule(WildcardType node) {
		super(node);
		
		ASTNode bound = node.getBound();
		
		if(bound != null){
		  this.bound = nonTerminal(bound);
		  
		  if (node.isUpperBound()){
			  this.boundTerminal = Terminals.EXTENDS;
		  } else this.boundTerminal = Terminals.SUPER;
		}
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(Terminals.QUESTION_MARK);
		
		if(this.bound != null) list.f(this.boundTerminal).f(this.bound);
	}

}
