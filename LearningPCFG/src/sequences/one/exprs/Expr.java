package sequences.one.exprs;

import java.util.LinkedList;
import java.util.List;

import selection.types.Type;
import util.Pair;

public abstract class Expr {
	protected Type type;
	
	public Expr(Type type) {
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	public abstract String shortRep();
	
	public Pair<String, String> longRep(){
		return new Pair(shortRep(), shortRep()+"("+representation()+")");
	}
	
	public List<Pair<String, String>> longReps(){
		List<Pair<String, String>> list = new LinkedList<Pair<String, String>>();
		list.add(longRep());
		representations(list);
		return list;
	}

	protected abstract void representations(List<Pair<String, String>> list);	
	
	protected abstract String representation();

	public static String shortReps(Expr... args){
		String s = "";
		if (args.length > 0){
			s += args[0].shortRep();
			for (int i = 1; i < args.length; i++) {
				s+=","+args[i].shortRep();
			}
		}
		return s;
	}
}
