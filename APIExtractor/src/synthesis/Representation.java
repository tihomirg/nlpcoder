package synthesis;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import synthesis.trees.Expr;



public class Representation implements Cloneable {
	private static final int INITIAL_CAP = 20;
	private RepKey root;
	private Expr[] rep;
	private int allocateIndex;
	
	public Representation(){
		this.rep = new Expr[INITIAL_CAP];
		this.root = new RepKey();
		int id = this.root.getId();
		this.rep[id] = Expr.REP_HOLE;
		this.allocateIndex = id+1;
	}
	
	@Override
	protected Representation clone() {
		Representation tree = null;
		try {
			tree = (Representation) super.clone();
			tree.rep = this.rep.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tree;
	}
	
	public String toString(){
		return toString(root.getId());
	}
	
	public String toString(int id){
		return rep[id].toString(this);
	}

	public List<Param> substitute(Param param, statistics.posttrees.Expr expr) {
		RepKey repKey = param.getRepKey();
		List<statistics.posttrees.Expr> args = expr.getArgs();
		List<Integer> ids = allocate(args.size());
		
		Expr repExpr = expr.createRep(ids);
		rep[repKey.getId()] = repExpr;
		
		return createParams(expr.getArgs(), ids);
	}

	private List<Param> createParams(List<statistics.posttrees.Expr> args, List<Integer> ids) {
		List<Param> list = new LinkedList<Param>();
		for (int i=0; i< args.size(); i++) {
			list.add(new Param(args.get(i), ids.get(i)));
		}
		return list;
	}

	private List<Integer> allocate(int size) {
		List<Integer> indexes = new LinkedList<Integer>();
		int newLength = allocateIndex + size;
		ensureSize(newLength);
		while(allocateIndex < newLength){
			indexes.add(allocateIndex++);
		}
		return indexes;
	}

	private void ensureSize(int newLength) {
		if (rep.length < newLength){
			int length = 2 * rep.length;
			Expr[] newArray = new Expr[length];
			System.arraycopy(rep, 0, newArray, 0, allocateIndex);
			this.rep = newArray;
		}
	}

	public String toString(List<Integer> ids) {
		StringBuilder sb = new StringBuilder();
		if(ids.size() > 1){
			sb.append(toString(ids.get(0)));
			for(int i=1; i < ids.size(); i++){
				sb.append(", "+toString(ids.get(i)));
			}
		}
		
		return sb.toString();
	}
}
