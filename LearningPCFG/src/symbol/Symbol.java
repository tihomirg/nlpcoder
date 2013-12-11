package symbol;
import org.eclipse.jdt.core.dom.ASTNode;


public abstract class Symbol {
	private static final int NAIVE = 0;
	private static final int WITH_PARENT= 1;
	private static final int WITH_GRANDAD = 2;
	
	private static int type = NAIVE;
	
	protected abstract ASTNode getParent();
	
	private ASTNode getGrandad() {
		ASTNode parent = getParent();
		return parent != null ? parent.getParent(): null;
	}
	
	protected abstract String naive();
	
	public String toString(){
	   switch(type){
		 case NAIVE : return naive();
		 case WITH_PARENT : return withParent();
		 case WITH_GRANDAD: return withGrandad();
		 default: return naive();
	   }
	}
	
	private String withParent(){
		ASTNode parent = getParent();
		return naive()+(parent != null ? "^"+parent.getClass().getSimpleName(): ""); 
	}
	
	private String withGrandad(){
		ASTNode grandad = getGrandad();
		return withParent()+(grandad != null ? "^"+grandad.getClass().getSimpleName(): ""); 
	}
}
