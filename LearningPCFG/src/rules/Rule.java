package rules;

import symbol.Symbol;

public abstract class Rule {
	private Symbol head;
	
	public Rule(Symbol head){
		this.head = head;
	}
	
	protected abstract String rhs();
	
    public String toString(){
		return this.head +" -> "+this.rhs();
	}
}
