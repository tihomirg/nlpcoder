package types;

public class Substitution {
	private final Variable var;
	private final Type type;
	
	public Substitution(Variable var, Type type) {
		this.var = var;
		this.type = type;
	}

	public Variable getVariable() {
		return var;
	}

	public Type getType() {
		return type;
	}

	@Override
	public String toString() {
		return "Substitution [" + var + " -> " + type + "]";
	}

	
}
