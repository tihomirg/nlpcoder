package core;

import java.util.LinkedList;
import java.util.List;
import synthesis.PartialExpression;

public class Synthesis<T extends SynthesisGroup> {
	
	private List<T> groups;
	
	private List<PartialExpression> completed;

	private long time;
	
	public Synthesis(List<PartialExpression> pexprs, GroupBuilder<T> builder) {
		this.groups = createGroups(pexprs, builder);
		this.completed = new LinkedList<PartialExpression>();
	}
	
	private List<T> createGroups(List<PartialExpression> pexprs, GroupBuilder<T> builder) {
		List<T> groups = new LinkedList<T>(); 
		for (PartialExpression pexpr : pexprs) {
			groups.add(builder.build(pexpr));
		}
		return groups;
	}
	
	public void run(){
		long currentTime = System.currentTimeMillis();
		this.start();
		this.time = System.currentTimeMillis() - currentTime;
	}
	
	public void start(){
		for (T group : groups) {
			this.completed.addAll(group.run());
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Time: "+ time+"ms\n");
		sb.append("Completed:");
		sb.append(this.completed+"\n\n");
		
		for(int i=0; i < groups.size(); i++){
			T group = groups.get(i);
			sb.append("Group "+i+":");
			sb.append(group+"\n\n");
		}
		return sb.toString();
	}
}
