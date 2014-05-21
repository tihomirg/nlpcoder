package synthesis.trees;

import synthesis.Representation;
import definitions.Declaration;

public class InstanceFieldAccess extends Expr {

	private Declaration decl;
	private int recId;

	public InstanceFieldAccess(Declaration decl, int recId) {
		this.decl = decl;
		this.recId = recId;
	}

	@Override
	public String toString(Representation rep) {
		return (decl.isStatic() ? decl.getClazz(): rep.toString(recId))+"."+decl.getName();
	}

}
