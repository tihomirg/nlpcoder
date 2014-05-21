package synthesis.trees;

import java.util.List;

import synthesis.Representation;

import definitions.Declaration;

public class ConstructorInvocation extends Expr {

	private Declaration decl;
	private List<Integer> ids;

	public ConstructorInvocation(Declaration decl, List<Integer> ids) {
		this.decl = decl;
		this.ids = ids;
	}

	@Override
	public String toString(Representation rep) {
		return rep.toString(ids);
	}

}
