package core;

import java.util.LinkedList;
import java.util.List;

import synthesis.Connection;
import synthesis.ExprGroup;
import synthesis.PartialExpression;
import util.Pair;

public class Synthesis<T extends SynthesisGroup> {
	
	private List<T> groups;
	
	private List<PartialExpression> completed;

	private long time;
	
	public Synthesis(List<List<ExprGroup>> exprGroupss, GroupBuilder<T> builder) {
		this.groups = createGroups(exprGroupss, builder);
		this.completed = new LinkedList<PartialExpression>();
	}
	
	private List<T> createGroups(List<List<ExprGroup>> exprGroupss, GroupBuilder<T> builder) {
		List<T> groups = new LinkedList<T>(); 
		for (List<ExprGroup> exprGroups : exprGroupss) {			
			groups.add(builder.build(exprGroups));
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
		sb.append("Completed:\n");
		Pair<List<PartialExpression>, List<PartialExpression>> pair = filterConnected(this.completed);

		sb.append("With connections:\n");
		sb.append(pair.getFirst()+"\n\n");
		
		sb.append("Without connections:\n");		
		sb.append(pair.getSecond()+"\n\n");
		
		for(int i=0; i < groups.size(); i++){
			T group = groups.get(i);
			sb.append("Group "+i+":");
			sb.append(group+"\n\n");
		}
		return sb.toString();
	}

	private Pair<List<PartialExpression>, List<PartialExpression>> filterConnected(List<PartialExpression> pexprs) {
		List<PartialExpression> with = new LinkedList<PartialExpression>();
		List<PartialExpression> without = new LinkedList<PartialExpression>();
		for (PartialExpression pexpr : pexprs) {
			LinkedList<Connection> connections = pexpr.getConnections();
			if (!connections.isEmpty()){
				with.add(pexpr);
			} else {
				without.add(pexpr);
			}
		}
		
		return new Pair<List<PartialExpression>,List<PartialExpression>>(with, without);
	}
}
