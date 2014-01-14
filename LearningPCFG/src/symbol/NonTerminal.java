package symbol;
import org.eclipse.jdt.core.dom.ASTNode;


public class NonTerminal extends Symbol {

	private ASTNode node;
	
	public NonTerminal(ASTNode node){
		assert node != null;		
		this.node = node;
	}	

	protected String toStringNaive(){
		
		Class c = node.getClass();
		
		return c.getSimpleName();
	}

	@Override
	protected ASTNode getParent() {
		return node.getParent();
	}
	
}
