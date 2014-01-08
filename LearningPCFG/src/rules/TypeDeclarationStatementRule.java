package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;

import symbol.Symbol;
import util.List;

public class TypeDeclarationStatementRule extends Rule {

	private Symbol declaration;

	public TypeDeclarationStatementRule(TypeDeclarationStatement node) {
		super(node);
		
		this.declaration = nonTerminal(node.getDeclaration());
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void rhsAsList(List<Symbol> list) {
		// TODO Auto-generated method stub
		list.add(this.declaration);
	}

}
