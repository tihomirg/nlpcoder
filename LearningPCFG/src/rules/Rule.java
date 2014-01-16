package rules;

import util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import config.Config;

import symbol.Symbol;
import symbol.SymbolFactory;

public abstract class Rule {
	protected Symbol head;

	protected Rule(){}
	
	public Rule(ASTNode node){
		assert node != null;
		this.head = Config.getFactory().getNonTerminal(node);
	}
	
	protected List<Symbol> rhsAsList(){
		List<Symbol> list = new List<Symbol>();
		rhsAsList(list);
		return list;
	}
	
	protected abstract void rhsAsList(List<Symbol> list);
	
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
	
	protected List<Symbol> makeNonTerminalList(java.util.List<ASTNode> nodes) {
		return makeNonTerminalList(nodes.toArray(new ASTNode[0]));
	}
	
	protected List<Symbol> makeNonTerminalList(ASTNode[] nodes) {
		List<Symbol> list = new List<Symbol>();
		for(ASTNode node: nodes){
			list.add(Config.getFactory().getNonTerminal(node));
		}
		return list;
	}
	
	// e1 op e2 ... op en
	protected static List<Symbol> toInfixList(List<Symbol> symbols, Symbol operator) {
		List<Symbol> list = new List<Symbol>();
		list.add(symbols.get(0));
		
		int length = symbols.size();
		for(int i=1; i< length; i++){
			list.add(operator);
			list.add(symbols.get(i));
		}
		
		return list;		
	}
	
	
	// op e1 op e2 ... op en
	protected static List<Symbol> toPrefixList(List<Symbol> symbols, Symbol operator) {
		List<Symbol> list = new List<Symbol>();
		for(Symbol node: symbols){
			list.add(operator);
			list.add(node);
		}
		
		return list;	
	}
	
	// op1 e1 op2 op1 e2 op2 ... op1 en op2
	protected static List<Symbol> toIndexList(List<Symbol> symbols, Symbol operator1, Symbol operator2) {
		List<Symbol> list = new List<Symbol>();
		for(Symbol node: symbols){
			list.add(operator1);
			list.add(node);
			list.add(operator2);
		}
		
		return list;
	}

	// op1 op2 op1 op2 ... op1 op2
	protected static List<Symbol> toIndexList(int length, Symbol operator1, Symbol operator2) {
		List<Symbol> list = new List<Symbol>();
		for(int i=0; i < length; i++){
			list.add(operator1);
			list.add(operator2);
		}
		return list;
	}	

	// e1 e2 ... en
	protected static String printList(List<Symbol> list) {
		String s = "";
		for(Symbol node: list){
			s+= " "+node;
		}
		
		return s;
	}	
	
    protected Symbol nonTerminal(ASTNode node){
    	return Config.getFactory().getNonTerminal(node);
    }
 
    protected Symbol terminal(String token){
    	return Config.getFactory().getTerminal(token);
    }
     
	protected String rhs(){
		return printList(rhsAsList());
	}	
	
    public String toString(){
		return this.head +" ->"+this.rhs();
	}
}
