package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.QualifiedType;

import symbol.Symbol;
import symbol.Tokens;
import util.List;

public class QualifiedTypeRule extends Rule {

	private Symbol qualifer;
	private Symbol name;
	private Symbol dot;

	public QualifiedTypeRule(QualifiedType node) {
		super(node);
		
		this.name = nonTerminal(node.getName());
		this.qualifer = nonTerminal(node.getQualifier());
		// TODO Auto-generated constructor stub
		this.dot = terminal(Tokens.DOT, node);
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		// TODO Auto-generated method stub
		list.f(this.qualifer).f(this.dot).f(this.name);
	}

}
