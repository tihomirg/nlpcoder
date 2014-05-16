package synthesis.trees;

import java.util.List;

import definitions.Declaration;

public class InstanceMethodInvocation extends Expr {

	private Declaration decl;
	private List<Integer> argIds;

	public InstanceMethodInvocation(Declaration decl, List<Integer> argIds) {
		this.decl = decl;
		this.argIds = argIds;
	}

}
