package util;

import org.eclipse.jdt.core.dom.ASTNode;

import symbol.NonTerminal;
import symbol.Symbol;

public class NonTermainalIndex extends SymbolIndex{

	private ASTNode node;

	public NonTermainalIndex(ASTNode node){
		this.node = node;
	}

	public int hashCode(){
		switch(type){
		case NAIVE : return naiveHashCode();
		case WITH_PARENT : return withParentHashCode();
		case WITH_GRANDAD: return withGrandadHashCode();
		default: return naiveHashCode();
		}		
	}
	
	private int naiveHashCode(){
		return node.getClass().toString().hashCode()^348274982;
	}

	private int withParentHashCode(){
		ASTNode parent = node.getParent();
		if (parent == null) return naiveHashCode();
		else return (naiveHashCode()+parent.getClass().toString().hashCode())^487535932;		
	}

	private int withGrandadHashCode(){
		ASTNode parent = node.getParent();	
		ASTNode grandad = null;
		if(parent == null){
			return naiveHashCode();  
		} else{
			grandad = parent.getParent();
			if(grandad == null) return withParentHashCode();
			else return (withParentHashCode()+grandad.getClass().toString().hashCode())^95493943;	
		}
	}

	@Override
	public Symbol getSymbol() {
		return new NonTerminal(node);
	}
}
