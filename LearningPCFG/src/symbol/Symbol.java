package symbol;
import org.eclipse.jdt.core.dom.ASTNode;

import rules.Rule;

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
		return toStringNaive()+(parent != null ? "^"+parent.getClass().getSimpleName(): "^NULL"); 
	}
	
	private String toStringWithGrandad(){
		ASTNode grandad = getGrandad();
		return toStringWithParent()+(grandad != null ? "^"+grandad.getClass().getSimpleName(): "^NULL"); 
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
	
	public int hashCode(){
		switch(StateSplitterType.getType()){
		case StateSplitterType.NAIVE : return naiveHashCode();
		case StateSplitterType.WITH_PARENT : return withParentHashCode();
		case StateSplitterType.WITH_GRANDAD: return withGrandadHashCode();
		default: return naiveHashCode();
		}
	}
	
	public boolean equals(Object obj){
		if (obj == null) return false;
		if (obj instanceof Symbol) return this.hashCode() == obj.hashCode();
		else return false;
	}
}
