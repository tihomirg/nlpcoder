package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.QualifiedType;

import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class QualifiedTypeRule extends Rule {

	private Symbol qualifer;
	private Symbol name;

	public QualifiedTypeRule(QualifiedType node) {
		super(node);
		
		this.name = nonTerminal(node.getName());
		this.qualifer = nonTerminal(node.getQualifier());
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		// TODO Auto-generated method stub
		list.f(this.qualifer).f(Terminals.DOT).f(this.name);
	}

}
