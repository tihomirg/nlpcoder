package statistics.posttrees;

import java.util.List;

public abstract class Expr {

	private int frequency;

	public abstract void addArgs(List<Expr> args);
	
	public String getString() {
		throw new UnsupportedOperationException();
	}

	public void setFrequency(int value) {
		this.frequency = value;
	}
	
	public String toString(){
		return frequency+" : "+shortRep()+"("+argsRep()+")";
	}
	
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
	
	protected abstract String shortRep();
	
	protected abstract String argsRep();
}
