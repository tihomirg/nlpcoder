package core;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import synthesis.Connection;
import synthesis.ExprGroup;
import synthesis.PartialExpression;
import util.Pair;

public class Synthesis<T extends SynthesisGroup> {

	private List<T> groups;

	private List<PartialExpression> completed;

	private long time;

	ExecutorService service;

	private boolean parallel;

	public Synthesis(List<List<ExprGroup>> exprGroupss, GroupBuilder<T> builder, boolean parallel) {
		this.groups = createGroups(exprGroupss, builder);
		this.parallel = parallel;
		this.completed = new LinkedList<PartialExpression>();
		this.service = Executors.newFixedThreadPool(this.groups.size());
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
		if (this.parallel) this.startParallel();
		else this.startSequential();
		this.time = System.currentTimeMillis() - currentTime;
	}

	private void startSequential() {
		try {
			for (T group : groups) {
				this.completed.addAll(group.call());
				// TODO Auto-generated catch block
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startParallel(){
		try {
			List<Future<PriorityQueue<PartialExpression>>> result = service.invokeAll(this.groups);
			
			service.shutdown();
			service.awaitTermination(10, TimeUnit.SECONDS);
			
			processResult(result);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	private void processResult(List<Future<PriorityQueue<PartialExpression>>> result) {
		for (Future<PriorityQueue<PartialExpression>> future : result) {
			try {
				this.completed.addAll(future.get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
