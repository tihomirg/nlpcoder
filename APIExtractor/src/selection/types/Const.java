package selection.types;

public class Const extends Type {
	private final String name;

	protected Const(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return this == obj;
	}

	@Override
	public String toString() {
		return "Const ("+ name + ")";
	}

	@Override
	public Type apply(Substitution sub, TypeFactory factory) {
		return this;
	}
	
	public Unifier unify(Type type, TypeFactory factory) {
		if (this.equals(type)) return Unifier.True();
		else if (type instanceof Variable){
			Variable var = (Variable) type;
			return var.unify(this, factory);
		}
		
		return Unifier.False();
	}

	@Override
	public boolean contains(Type type) {
		return this.equals(type);
	}
}
