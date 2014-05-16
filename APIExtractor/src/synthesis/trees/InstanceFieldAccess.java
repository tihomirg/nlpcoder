package synthesis.trees;

import definitions.Declaration;

public class InstanceFieldAccess extends Expr {

	private Declaration decl;
	private int recId;

	public InstanceFieldAccess(Declaration decl, int recId) {
		this.decl = decl;
		this.recId = recId;
	}

}
