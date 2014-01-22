package symbol;

public abstract class Symbol {
	
	public boolean equals(Object obj){
		if (obj == null) return false;
		if (obj instanceof Symbol) return this.hashCode() == obj.hashCode();
		else return false;
	}
}
