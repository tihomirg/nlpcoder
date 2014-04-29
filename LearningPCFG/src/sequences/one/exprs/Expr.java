package sequences.one.exprs;

import selection.types.Type;

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
	
	public String longRep(){
		return shortRep()+"("+representation()+")";
	}
	
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
