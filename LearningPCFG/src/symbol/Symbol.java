package symbol;
import org.eclipse.jdt.core.dom.ASTNode;

public abstract class Symbol {

	protected abstract ASTNode getParent();
	
	private ASTNode getGrandad() {
		ASTNode parent = getParent();
		return parent != null ? parent.getParent(): null;
	}
	
	protected abstract String toStringNaive();
	
	public String toString(){
	   switch(StateSplitterType.getType()){
		 case StateSplitterType.NAIVE : return toStringNaive();
		 case StateSplitterType.WITH_PARENT : return toStringWithParent();
		 case StateSplitterType.WITH_GRANDAD: return toStringWithGrandad();
		 default: return toStringNaive();
	   }
	}
	
	private String toStringWithParent(){
		ASTNode parent = getParent();
		return toStringNaive()+(parent != null ? "^"+parent.getClass().getSimpleName(): ""); 
	}
	
	private String toStringWithGrandad(){
		ASTNode grandad = getGrandad();
		return toStringWithParent()+(grandad != null ? "^"+grandad.getClass().getSimpleName(): ""); 
	}

	protected int naiveHashCode(){
		return toStringNaive().hashCode()^348274982;
	}	
	
	protected int withParentHashCode(){
		ASTNode parent = getParent();
		if (parent == null) return naiveHashCode();
		else return (naiveHashCode()+parent.getClass().getSimpleName().hashCode())^487535932;		
	}

	protected int withGrandadHashCode(){
		ASTNode grandad = getGrandad();
		if(grandad == null) return withParentHashCode();
		else return (withParentHashCode()+grandad.getClass().getSimpleName().hashCode())^95493943;	
	}	
}
