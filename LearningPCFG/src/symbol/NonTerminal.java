package symbol;
import org.eclipse.jdt.core.dom.ASTNode;


public class NonTerminal extends Symbol {

	private ASTNode node;
	
	public NonTerminal(ASTNode node){
		this.node = node;
	}	

	protected String naive(){
		return node.getClass().getSimpleName();
	}

	@Override
	protected ASTNode getParent() {
		return node.getParent();
	}
	
}
