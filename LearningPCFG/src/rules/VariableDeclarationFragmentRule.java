package rules;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import symbol.Symbol;
import symbol.Tokens;
import util.List;

public class VariableDeclarationFragmentRule extends Rule {

	private Symbol name;
	private int dimensions;
	private Symbol lsquareTerminal;
	private Symbol rsquareTerminal;

	public VariableDeclarationFragmentRule(VariableDeclarationFragment node) {
		super(node);
		
		this.name = nonTerminal(node.getName());
		this.dimensions = node.getExtraDimensions();
		
		if (this.dimensions > 0){
			this.lsquareTerminal = terminal(Tokens.L_SQUARE_BRACKET, node);
			this.rsquareTerminal = terminal(Tokens.R_CURLY_BRACKET, node);
		}
		// TODO Auto-generated constructor stub
	}

	
	private List<Symbol> toDimensions() {
		return toIndexList(this.dimensions, this.lsquareTerminal, this.rsquareTerminal);
	}
	
	@Override
	protected void rhsAsList(List<Symbol> list) {
		// TODO Auto-generated method stub
		list.f(this.name);
		
		if (this.dimensions > 0){
			list.f(toDimensions());
		}
	}

}
