package types;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import definitions.ClassInfo;

public class PolymorphicType extends ReferenceType {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8410671184621593335L;
	private Type[] params;
	
	public PolymorphicType() {}
	
	public PolymorphicType(String name, Type[] params) {
		super(name);
		this.params = params;
	}	
	
	public PolymorphicType(String name, ClassInfo clazz, Type[] params) {
		super(name, clazz);
		this.params = params;
	}

	public Type[] getParams() {
		return params;
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
	public Type apply(Substitution sub, TypeFactory factory) {
		return factory.createPolymorphicType(name, clazz, apply(params, sub, factory));
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
		if (type.isPolymorphicType()){
			PolymorphicType poly = (PolymorphicType) type;
			if(this.getPrefix().equals(poly.getPrefix()) 
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
			if (type.isVariable()){
				return type.unify(this, factory);
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
	public List<String> getWords() {
		List<String> list = new LinkedList<String>();
		list.add(this.name);
		
		for (Type type : params) {
			list.addAll(type.getWords());
		}
		
		return list;
	}

	@Override
	public String toString() {
		return this.getPrefix()+"("+Arrays.toString(params)+ ") "+referenceToString();
	}

	@Override
	public boolean isPolymorphicType() {
		return true;
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
	public boolean isVariable() {
		return false;
	}	
}
