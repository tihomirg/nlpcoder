package synthesis.trees;

import java.util.List;

import synthesis.Representation;
import definitions.Declaration;

public class InstanceMethodInvocation extends Expr {

	private Declaration decl;
	private List<Integer> argIds;
	private int recId;

	public InstanceMethodInvocation(Declaration decl, int recId, List<Integer> argIds) {
		this.decl = decl;
		this.recId = recId;
		this.argIds = argIds;
	}

	@Override
	public String toString(Representation rep) {
		return (decl.isStatic() ? shortName(decl.getClazz()): rep.toString(recId))+"."+decl.getName()+"("+rep.toString(argIds)+")";
	}
}