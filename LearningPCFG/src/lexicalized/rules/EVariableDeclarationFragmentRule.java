package lexicalized.rules;

import interpreter.ENonTerminalFactory;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import scopes.Scopes;
import scopes.SimpleScopes;
import symbol.Symbol;
import symbol.Terminals;
import util.List;

public class EVariableDeclarationFragmentRule extends ERule {

	private Symbol name;
	private int dimensions;
	private Symbol initializer;

	public EVariableDeclarationFragmentRule(VariableDeclarationFragment node, ENonTerminalFactory factory) {
		super(node, factory);

		this.name = eNonTerminal(node.getName());		
		
		this.dimensions = node.getExtraDimensions();
		
		ASTNode initializer = node.getInitializer();
		
		if(initializer != null){
			this.initializer = eNonTerminal(initializer);
		}
	
	}

	@Override
	public boolean ommit() {
		// TODO Auto-generated method stub
		return false;
	}

	private List<Symbol> toDimensions() {
		return toIndexList(this.dimensions, Terminals.L_CURLY_BRACKET, Terminals.R_SQUARE_BRACKET);
	}
	
	@Override
	protected void rhsAsList(List<Symbol> list) {
		// TODO Auto-generated method stub
		list.f(this.name);
		
		if (this.dimensions > 0){
			list.f(toDimensions());
		}
		
		if(this.initializer != null){
			list.f(Terminals.ASSIGN);
			list.f(this.initializer);
		}
	}

}
