package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.QualifiedName;

import symbol.Symbol;
import symbol.Tokens;
import util.List;

public class QualifiedNameRule extends Rule {

	private Symbol name;
	private Symbol qualifier;
	private Symbol dot;

	public QualifiedNameRule(QualifiedName node) {
		super(node);
		
		this.name = nonTerminal(node.getName());
		this.qualifier = nonTerminal(node.getQualifier());
		// TODO Auto-generated constructor stub
		
		this.dot = terminal(Tokens.DOT, node);
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		// TODO Auto-generated method stub
		list.f(this.qualifier).f(this.dot).f(this.name);
	}

}
