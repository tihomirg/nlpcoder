package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ContinueStatement;

import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class ContinueStatementRule extends Rule {

	private Symbol label;

	public ContinueStatementRule(ContinueStatement node) {
		super(node);
		
		ASTNode lab = node.getLabel();
		if (lab != null) this.label = nonTerminal(lab);
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		list.f(Terminals.CONTINUE);
		if(this.label != null){
			list.f(this.label);
		}
		list.f(Terminals.SEMICOLON);
	}

}
