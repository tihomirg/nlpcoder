package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class VariableDeclarationFragmentRule extends Rule {

	private Symbol name;
	private int dimensions;
	private Symbol initializer;

	public VariableDeclarationFragmentRule(VariableDeclarationFragment node) {
		super(node);
		
		this.name = nonTerminal(node.getName());
		this.dimensions = node.getExtraDimensions();
		
		ASTNode initializer = node.getInitializer();
		
		if(initializer != null){
			this.initializer = nonTerminal(initializer);
		}
	}

	
	private List<Symbol> toDimensions() {
		return null;//toIndexList(this.dimensions, Terminals.L_CURLY_BRACKET, Terminals.R_SQUARE_BRACKET);
	}
	
	@Override
	protected void rhsAsList(List<Symbol> list) {
		// TODO Auto-generated method stub
		list.f(this.name);
		
		if (this.dimensions > 0){
			list.f(toDimensions());
		}
		
		if(this.initializer != null){
			//list.f(Terminals.ASSIGN);
			list.f(this.initializer);
		}
	}

}
