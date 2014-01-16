package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.QualifiedName;

import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class QualifiedNameRule extends Rule {

	private Symbol name;
	private Symbol qualifier;

	public QualifiedNameRule(QualifiedName node) {
		super(node);
		
		this.name = nonTerminal(node.getName());
		this.qualifier = nonTerminal(node.getQualifier());
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		// TODO Auto-generated method stub
		list.f(this.qualifier).f(Terminals.DOT).f(this.name);
	}

}
