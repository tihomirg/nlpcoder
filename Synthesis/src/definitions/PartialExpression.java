package definitions;

import java.util.LinkedList;
import types.Substitution;

public class PartialExpression implements Cloneable {

	private LinkedList<Param> params;
	private LinkedList<Substitution> subs;
	private Tree tree;

	private int score;
	
	@Override
	public PartialExpression clone() throws CloneNotSupportedException {
		PartialExpression exp = (PartialExpression) super.clone();
		exp.tree = this.tree.clone();
		exp.subs = (LinkedList<Substitution>) this.subs.clone();
		return exp;
	}

	public boolean isComplete() {
		return subs.isEmpty();
	}
	
	@Override
	public String toString() {
		return tree.toString();
	}

}
