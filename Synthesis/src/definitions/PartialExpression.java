package definitions;

import java.util.LinkedList;
import types.Substitution;

public class PartialExpression implements Cloneable {

	private LinkedList<Param> params;
	private LinkedList<Substitution> subs;
	private Tree tree;

	private double score;
	
	
	@Override
	public PartialExpression clone() {
		PartialExpression exp = null;
		try {
			exp = (PartialExpression) super.clone();
			exp.tree = this.tree.clone();
			exp.subs = (LinkedList<Substitution>) this.subs.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
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
