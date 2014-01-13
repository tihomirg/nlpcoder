package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.WildcardType;

import symbol.Symbol;
import symbol.Tokens;
import util.List;

public class WildcardTypeRule extends Rule {

	private Symbol bound;
	private Symbol questionMark;
	private Symbol boundTerminal;

	public WildcardTypeRule(WildcardType node) {
		super(node);
		
		this.questionMark = terminal(Tokens.QUESTION_MARK, node);
		
		ASTNode bound = node.getBound();
		
		if(bound != null){
		  this.bound = nonTerminal(bound);
		  
		  if (node.isUpperBound()){
			  this.boundTerminal = terminal(Tokens.EXTENDS, node);
		  } else this.boundTerminal = terminal(Tokens.SUPER, node);
		}
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.questionMark);
		
		if(this.bound != null) list.f(this.boundTerminal).f(this.bound);
	}

}
