package types;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Variable extends ReferenceType {

	private static final long serialVersionUID = 2312467056934914968L;	

	public Variable(){}
	
	public Variable(String name) {
		super(name);
	}

	@Override
	public Type apply(Substitution sub, TypeFactory factory) {
		if (this.equals(sub.getVariable())) {
			return sub.getType();
		} else return this;
	}

	@Override
	public Unifier unify(Type type, TypeFactory factory) {
		if (!type.contains(this)){
			List<Substitution> subs = new LinkedList<Substitution>();
			subs.add(new Substitution(this, type));
			return new Unifier(true, subs);
		} else return Unifier.False();
	}

	@Override
	public boolean contains(Type type) {
		return this.equals(type);
	}

	@Override
	public List<Variable> getVariables() {
		return new LinkedList<Variable>(){{add(Variable.this);}};
	}
	
	@Override
	public List<String> getWords(InitialTypeFactory tf) {
		return EMPTY_STRING_LIST;
	}

	@Override
	protected Set<Type> getInheritedTypes(StabileTypeFactory factory) {
		return EMPTY_TYPE_SET;
	}	
	
	@Override
	public Set<Type> getCompatibleTypes(StabileTypeFactory factory) {
		return new HashSet<Type>(){{add(Variable.this);}};
	}

	@Override
	public Unifier checkCompatible(Type type, StabileTypeFactory factory) {
		if (type.isVoidType()) return Unifier.False();
		
		if (type.isNoType() || type.isNullType()) return Unifier.True();
		else return this.unify(type, factory);
	}

	@Override
	public boolean isVariable() {
		return true;
	}

	@Override
	public boolean isPolymorphicType() {
		return false;
	}

	@Override
	public boolean isConstantType() {
		return false;
	}

	@Override
	public boolean isBoxedType() {
		return false;
	}

	@Override
	public String toString() {
		return "Var(" + name + ")";
	}
}
