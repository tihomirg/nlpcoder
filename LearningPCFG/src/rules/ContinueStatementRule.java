package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ContinueStatement;

import symbol.Symbol;
import symbol.Tokens;
import util.List;

public class ContinueStatementRule extends Rule {

	private Symbol label;
	private Symbol continueTerminal;
	private Symbol semcolonTerminal;

	public ContinueStatementRule(ContinueStatement node) {
		super(node);
		
		ASTNode lab = node.getLabel();
		if (lab != null) this.label = nonTerminal(lab);
		
		this.continueTerminal = terminal(Tokens.CONTINUE, node);
		this.semcolonTerminal = terminal(Tokens.SEMICOLON, node);
		
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(this.continueTerminal);
		if(this.label != null){
			list.f(this.label);
		}
		list.f(this.semcolonTerminal);
	}

}
