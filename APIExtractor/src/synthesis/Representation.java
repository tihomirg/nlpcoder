package synthesis;

import java.util.LinkedList;
import java.util.List;
import synthesis.trees.Expr;

public class Representation implements Cloneable {
	private static final int INITIAL_CAP = 20;
	private RepKey root;
	private Expr[] rep;
	private int length;
	
	public Representation(){
		this.rep = new Expr[INITIAL_CAP];
		this.root = new RepKey();
		int id = this.root.getId();
		this.rep[id] = Expr.REP_HOLE;
		this.length = id+1;
	}
	
	@Override
	protected Representation clone() {
		Representation representation = null;
		try {
			representation = (Representation) super.clone();
			representation.rep = this.rep.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return representation;
	}
	
	public String toString(){
		return toString(root.getId());
	}
	
	public String toString(int id){
		return rep[id].toString(this);
	}

	public List<Param> instantiate(Param param, statistics.posttrees.Expr expr) {
		RepKey repKey = param.getRepKey();
		List<statistics.posttrees.Expr> args = expr.getArgs();
		List<Integer> ids = allocate(args.size());
		
		rep[repKey.getId()] = expr.createRep(ids);
		
		setRepHoles(ids);
		
		return createParams(args, ids);
	}

	private void setRepHoles(List<Integer> ids) {
		for (int id : ids) {
			rep[id] = Expr.REP_HOLE;
		}
	}

	private List<Param> createParams(List<statistics.posttrees.Expr> args, List<Integer> ids) {
		List<Param> list = new LinkedList<Param>();
		for (int i=0; i < args.size(); i++) {
			list.add(new Param(args.get(i), ids.get(i)));
		}
		return list;
	}

	private List<Integer> allocate(int size) {
		List<Integer> indexes = new LinkedList<Integer>();
		int newLength = length + size;
				
		ensureSize(newLength);
		
		for(int i = length; i < newLength; i++){
			indexes.add(i);
		}
		
		length = newLength;
		return indexes;
	}

	private void ensureSize(int newLength) {
		if (rep.length < newLength){
			int length = newLength + INITIAL_CAP;
			Expr[] newArray = new Expr[length];
			System.arraycopy(rep, 0, newArray, 0, this.length);
			this.rep = newArray;
		}
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
