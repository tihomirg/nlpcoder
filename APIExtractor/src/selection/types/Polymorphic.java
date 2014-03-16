package selection.types;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Polymorphic extends Type {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8410671184621593335L;
	
	private final String name;
	private final Type[] params;
	
	protected Polymorphic(String name, Type[] params) {
		this.name = name;
		this.params = params;
	}

	public Type[] getParams() {
		return params;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return getHashCode(this.name, this.params);
	}

	protected static int getHashCode(String name, Type[] params) {
		final int prime = 31;
		int result = 1;
		result = prime * result + name.hashCode();
		result = prime * result + Arrays.hashCode(params);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		return this == obj;
	}

	@Override
	public Type apply(Substitution sub, TypeFactory factory) {
		return factory.createPolymorphic(this.name, apply(params, sub, factory));
	}

	private static Type[] apply(Type[] params, Substitution sub, TypeFactory factory) {
		Type[] types = new Type[params.length];
		for (int i = 0; i < types.length; i++) {
			types[i] = params[i].apply(sub, factory);
		}
		return types;
	}

	@Override
	public Unifier unify(Type type, TypeFactory factory) {
		if (this.equals(type)) return Unifier.True();
		if (type instanceof Polymorphic){
			Polymorphic poly = (Polymorphic) type;
			if(this.name.equals(poly.name) 
			   && this.params.length == poly.params.length){
				List<Substitution> subs = new LinkedList<Substitution>();
				for (int i = 0; i< this.params.length; i++) {
					Type newParam = this.params[i].apply(subs, factory);
					Type newThatParam = poly.params[i].apply(subs, factory);
					Unifier unify = newParam.unify(newThatParam, factory);
					if(unify.isSuccess()){
						subs.addAll(unify.getSubs());	
					} else {	
						return Unifier.False();
					}
				}
				
				return new Unifier(true, subs);
			}
		} else {
			if (type instanceof Variable){
				Variable var = (Variable) type;
				return var.unify(this, factory);
			}
		}
		
		return Unifier.False();
	}

	@Override
	public boolean contains(Type type) {
		if (this.equals(type)) return true;
		for (Type param : params) {
			if (param.contains(type)) return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Polymorphic ("+name + ", "+ Arrays.toString(params) + ")";
	}

	@Override
	public List<String> caracteristicWords() {
		List<String> list = new LinkedList<String>();
		list.add(name);
		
		for (Type type : params) {
			list.addAll(type.caracteristicWords());
		}
		
		return list;
	}
	
	
}
