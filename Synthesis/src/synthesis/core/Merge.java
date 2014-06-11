package synthesis.core;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import synthesis.PartialExpression;
import synthesis.PartialExpressionScorer;
import util.Pair;

public class Merge {
	
	private List<MergeGroup> groups;
	private List<PartialExpression> completed;
	private long time;
	ExecutorService service;
	private boolean parallel;
	
	public Merge(List<PartialExpression> withConnections, int numOfGroups, int numOfLevels, int maxNumOfPexprPerLevel, PartialExpressionScorer scorer, boolean parallel) {
		this.groups = createGroups(withConnections, numOfGroups, numOfLevels, maxNumOfPexprPerLevel, scorer);
		this.parallel = parallel;
		this.completed = new LinkedList<PartialExpression>();
		
		System.out.println(this.groups.size());
		this.service = Executors.newFixedThreadPool(this.groups.size());
	}

	private List<MergeGroup> createGroups(List<PartialExpression> withConnections, int numOfGroups, int numOfLevles, int maxNumOfPexprPerLevel, PartialExpressionScorer scorer) {
		List<MergeGroup> groups = new LinkedList<MergeGroup>();
		
		int size = withConnections.size();
		int realGroupNum = Math.min(size, numOfGroups);
		
		for(int i=0; i < realGroupNum; i++){
			groups.add(new MergeGroup(numOfLevles, maxNumOfPexprPerLevel, scorer));
		}
		
		for(int i=0; i <size; i++){
			groups.get(i % realGroupNum).addPexpr(withConnections.get(i));
		}
		
		return groups;
	}

	public void run() {
		long currentTime = System.currentTimeMillis();
		if (this.parallel) this.startParallel();
		else this.startSequential();
		this.time = System.currentTimeMillis() - currentTime;
	}

	private void startSequential() {
		try {
			for (MergeGroup group : groups) {
				this.completed.addAll(group.call());
				// TODO Auto-generated catch block
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startParallel() {
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
		
//		for(PartialExpression pe : pair.getSecond()){
//			System.out.println(pe.toString());
//		}
//		
		sb.append(pair.getSecond()+"\n\n");

//		for(int i=0; i < groups.size(); i++){
//			MergeGroup group = groups.get(i);
//			sb.append("Group "+i+":");
//			sb.append(group+"\n\n");
//		}
		return sb.toString();
	}

	private Pair<List<PartialExpression>, List<PartialExpression>> filterConnected(List<PartialExpression> pexprs) {
		List<PartialExpression> with = new LinkedList<PartialExpression>();
		List<PartialExpression> without = new LinkedList<PartialExpression>();
		for (PartialExpression pexpr : pexprs) {
			if (pexpr.isCompletelyConnected()){
				without.add(pexpr);
			} else {
				with.add(pexpr);
			}
		}

		return new Pair<List<PartialExpression>,List<PartialExpression>>(with, without);
	}	
}
