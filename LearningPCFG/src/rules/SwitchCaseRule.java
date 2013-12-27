package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.SwitchCase;

import symbol.Symbol;
import symbol.Tokens;
import util.List;

public class SwitchCaseRule extends Rule {

	private Symbol defaultTerminal;
	private Symbol expression;
	private Symbol colon;
	private Symbol caseTerminal;

	public SwitchCaseRule(SwitchCase node) {
		super(node);
		
		if(node.isDefault()){
			this.defaultTerminal = terminal(Tokens.DEFAULT, node);
		} else{
			this.expression = nonTerminal(node.getExpression());
			this.caseTerminal = terminal(Tokens.CASE, node);
		}
		
		this.colon = terminal(Tokens.COLON, node);
		
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		if(this.expression != null){
			list.f(this.caseTerminal).f(this.expression).f(this.colon);
		} else {
			list.f(this.defaultTerminal).f(this.colon);
		}
	}

}
