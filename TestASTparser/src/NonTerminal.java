import org.eclipse.jdt.core.dom.ASTNode;


public class NonTerminal {
	private static int NAIVE = 0;
	private static int WITH_PARENT= 1;
	private static int WITH_GRANDAD = 2;
	
	private static int type = NAIVE;
	
	private ASTNode node;
	
	public NonTerminal(ASTNode node){
		this.node = node;
	}
	
	public String toString(){
	   return naive();
	}
	
	private String naive(){
		return node.getClass().getSimpleName();
	}
	
	private String withParent(){
		ASTNode parent = node.getParent();
		return naive()+(parent != null ? "^"+parent.getClass().getSimpleName(): ""); 
	}
	
	private String withGrandad(){
		ASTNode parent = node.getParent();
		ASTNode grandad = null;
		if (parent != null)
			grandad = parent.getParent();
		return naive()+(parent != null ? "^"+parent.getClass().getSimpleName(): "")+(grandad != null ? "^"+grandad.getClass().getSimpleName(): ""); 
	}	
}
