package rules;

import symbol.Symbol;

public abstract class Rule {
	private Symbol head;

	public Rule(Symbol head){
		assert head != null;
		this.head = head;
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
	
    public String toString(){
		return this.head +" -> "+this.rhs();
	}
}
