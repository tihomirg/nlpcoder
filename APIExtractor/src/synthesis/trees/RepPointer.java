package synthesis.trees;

import synthesis.Representation;

public class RepPointer extends Expr {

	private int index;
	
	public RepPointer(int index) {
		this.index = index;
	}

	@Override
	public String toString(Representation rep) {
		return rep.getParent().toString(index);
	}

}
