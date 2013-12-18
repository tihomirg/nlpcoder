package rules;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;

import symbol.Symbol;
import symbol.SymbolFactory;

public abstract class Rule {
	private Symbol head;

	public Rule(ASTNode node){
		assert node != null;
		this.head = SymbolFactory.getNonTerminal(node);
	}
	
	protected abstract String rhs();
	
	public Symbol getHead() {
		return head;
	}
	
	public int hashCode(){
	  return head.hashCode()^342342423 + rhs().hashCode()^598496893;
	}

	public boolean equals(Object obj){
		if (obj == null) return false;
		if (obj instanceof Rule) return this.hashCode() == obj.hashCode();
		else return false;
	}
	
	protected static List<Symbol> makeNonTerminalList(List<ASTNode> nodes) {
		List<Symbol> list = new LinkedList<Symbol>();
		for(ASTNode node: nodes){
			list.add(SymbolFactory.getNonTerminal(node));
		}
		return list;
	}
	
	// e1 op e2 ... op en
	protected static String printInfixList(List<Symbol> symbols, Symbol operator) {
		String s = symbols.get(0).toString();
		
		int length = symbols.size();
		for(int i=1; i< length; i++){
			s+= (operator+" "+symbols.get(i));
		}
		
		return s;		
	}
	
	
	// op e1 op e2 ... op en
	protected static String printPrefixList(List<Symbol> symbols, Symbol operator) {
		String s = "";
		for(Symbol node: symbols){
			s+= (" "+operator+" "+node);
		}
		
		return s;	
	}

	// e1 e2 ... en
	protected static String printList(List<Symbol> symbols) {
		String s = "";
		for(Symbol node: symbols){
			s+= " "+node;
		}
		
		return s;	
	}
	
    public String toString(){
		return this.head +" -> "+this.rhs();
	}
}
