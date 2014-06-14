package synthesis;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.math3.analysis.function.Min;

import types.Type;

public abstract class Representation implements Cloneable {
	protected static final int INITIAL_CAP = 20;
	protected RepKey root;
	protected int length;
	
	public Representation(){
		this.root = new RepKey();
		this.length = this.root.getId()+1;
	}
	
	public String toString(){
		return toString(root.getId());
	}
	
	public abstract String toString(int id);
	public abstract List<Param> instantiate(Param param, statistics.posttrees.Expr expr);
	public abstract Representation getParent();
	public abstract void setParent(Representation parent);
	public abstract int connect(Connection connection, Representation rep);	
	
	protected abstract void setRepHoles(List<Integer> ids);
	protected abstract void ensureSize(int newLength);
	
	public abstract SimpleRepresentation asSimpleRepresentation();
	public abstract ComplexRepresentation asComplexRepresentation();
	
	@Override
	protected Representation clone() throws CloneNotSupportedException {
		return (Representation) super.clone();
	}
	
	protected List<Param> createParams(List<statistics.posttrees.Expr> args, List<Type> argTypes, List<Integer> ids) {
		List<Param> list = new LinkedList<Param>();
		
		
		//TODO: This ugly hack can be removed once we 
		int size = Math.min(args.size(), argTypes.size());
		for (int i=0; i < size; i++) {
			list.add(new Param(args.get(i), argTypes.get(i), ids.get(i)));
		}
		
		for (int i = size; i < args.size(); i++) {
			list.add(new Param(args.get(i), ids.get(i)));
		}
		
		return list;
	}

	protected List<Integer> allocate(int size) {
		List<Integer> indexes = new LinkedList<Integer>();
		int newLength = length + size;
				
		ensureSize(newLength);
		
		for(int i = length; i < newLength; i++){
			indexes.add(i);
		}
		
		length = newLength;
		return indexes;
	}

	public String toString(List<Integer> ids) {
		StringBuilder sb = new StringBuilder();
		if(ids.size() > 0){
			sb.append(toString(ids.get(0)));
			for(int i=1; i < ids.size(); i++){
				sb.append(", "+toString(ids.get(i)));
			}
		}
		
		return sb.toString();
	}

}
