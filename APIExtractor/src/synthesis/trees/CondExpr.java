package synthesis.trees;

import synthesis.Representation;
import types.Type;

public class CondExpr extends Expr {

	private Type condType;
	private Type retType;
	private int condId;
	private int thenId;
	private int elseId;

	public CondExpr(Type condType, Type retType, int condId, int thenId, int elseId) {
		this.condType = condType;
		this.retType = retType;
		this.condId = condId;
		this.thenId = thenId;
		this.elseId = elseId;
	}

	@Override
	public String toString(Representation rep) {
		return rep.toString(condId)+" ? "+rep.toString(thenId)+" : "+rep.toString(elseId);
	}

}
