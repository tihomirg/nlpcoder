package selection.types;

import java.io.Serializable;
import java.util.List;

public abstract class Type implements Serializable{

	private static final long serialVersionUID = -900979897015622962L;
	protected final String head;

	private boolean regularType;
	private boolean primitiveType;
	private boolean holeType;
	private boolean boxedType;

	public Type(String head){
		this.head = head;
	}
	
	public boolean isHoleType() {
		return holeType;
	}

	public void setHoleType(boolean holeType) {
		this.holeType = holeType;
	}

	public boolean isBoxedType() {
		return boxedType;
	}

	public void setBoxedType(boolean boxedType) {
		this.boxedType = boxedType;
	}

	public boolean isRegularType() {
		return regularType;
	}

	public void setRegularType(boolean regularType) {
		this.regularType = regularType;
	}

	public boolean isPrimitiveType() {
		return primitiveType;
	}

	public void setPrimitiveType(boolean primitiveType) {
		this.primitiveType = primitiveType;
	}

	public Type apply(List<Substitution> subs, TypeFactory factory){
		Type curr = this;
		for (Substitution sub : subs) {
			curr = curr.apply(sub, factory);
		}
		return curr;
	}
	
	public abstract Type apply(Substitution sub, TypeFactory factory);
	public abstract boolean contains(Type type);
	public abstract Unifier unify(Type type, TypeFactory factory);

	public abstract List<String> caracteristicWords();

	public String getHead() {
		return head;
	}
	/**
	 * Only for ref types!
	 * @return
	 */
	public List<Type> compatable(){
		//TODO: Implement
		return null;
	}
	
	public boolean isCompatible(Type type){
		if(this.regularType){
			if (this.primitiveType) {
				return type.isRegularType() && (type.isPrimitiveType() || type.isBoxedType());
			} else {
				return type.isRegularType() && !type.isPrimitiveType() && this.compatable().contains(type);
			}
		} else {
			if(this.holeType) return true;
			else {
				if (this.primitiveType) {//operatorType
					return type.isRegularType() && (type.isPrimitiveType() || type.isBoxedType());
				} else { //NullType
					return type.isRegularType() && !type.isPrimitiveType();
				}
			}
		}
	}
}
