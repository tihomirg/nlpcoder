package util;

import org.eclipse.jdt.core.dom.ASTNode;

import symbol.StateSplitterType;
import symbol.Symbol;

public abstract class SymbolIndex {
	
	public int hashCode(){
		switch(StateSplitterType.getType()){
		case StateSplitterType.NAIVE : return naiveHashCode();
		case StateSplitterType.WITH_PARENT: return withParentHashCode();
		case StateSplitterType.WITH_GRANDAD: return withGrandadHashCode();
		default: return naiveHashCode();
		}
	}
	
	public boolean equals(Object obj){
		if (obj == null) return false;
		if (obj instanceof SymbolIndex) return this.hashCode() == obj.hashCode();
		else return false;
	}
	
	protected abstract ASTNode getParent();

	private ASTNode getGrandad() {
		ASTNode parent = getParent();
		return parent != null ? parent.getParent(): null;
	}
	
	protected abstract String naiveName();

	protected int naiveHashCode(){
		return naiveName().hashCode()^348274982;
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

	public abstract Symbol getSymbol();
}
