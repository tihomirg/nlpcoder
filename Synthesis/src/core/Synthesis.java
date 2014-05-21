package core;

import java.util.LinkedList;
import java.util.List;
import synthesis.PartialExpression;

public class Synthesis<T extends SynthesisGroup> {
	
	private List<T> groups;

	public Synthesis(List<PartialExpression> pexprs, GroupBuilder<T> builder) {
		this.groups = createGroups(pexprs, builder);
	}
	
	private List<T> createGroups(List<PartialExpression> pexprs, GroupBuilder<T> builder) {
		List<T> groups = new LinkedList<T>(); 
		for (PartialExpression pexpr : pexprs) {
			groups.add(builder.build(pexpr));
		}
		return groups;
	}
	
	public void run(){
		for (SynthesisGroup group : groups) {
			group.run();
		}
	}
}
