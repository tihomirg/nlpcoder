package sequences.trees;

import selection.types.Type;

public class Hole extends Expr{

	public Hole(){
		super(null);
	}
	
	public Hole(Type type) {
		super(type);
	}

	@Override
	public String toString() {
		return "h("+type+")";
	}	
	
}
