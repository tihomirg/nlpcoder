package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.SwitchCase;

import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class SwitchCaseRule extends Rule {

	private Symbol expression;

	public SwitchCaseRule(SwitchCase node) {
		super(node);
		
		if(!node.isDefault()){
			this.expression = nonTerminal(node.getExpression());
		}
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		if(this.expression != null){
			list.f(Terminals.CASE).f(this.expression).f(Terminals.COLON);
		} else {
			list.f(Terminals.DEFAULT).f(Terminals.COLON);
		}
	}

}
