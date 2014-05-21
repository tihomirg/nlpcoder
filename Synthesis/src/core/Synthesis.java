package core;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import synthesis.PartialExpression;

import definitions.PartialExpressionComparator;

public class Synthesis {

	private static final PartialExpressionComparator COMPARATOR = new PartialExpressionComparator();	
	private static final int DEFAULT_CAPACITY = 100;
	private int maxRounds;
	private int round;
	private List<SynthesisGroup> activeGroups;
	private List<SynthesisGroup> resolvedGroups;
	
	public Synthesis(List<PartialExpression> pexprs, HandlerTable handlerTable, int maxRounds, int steps) {
		this.maxRounds = maxRounds;
		this.activeGroups = createGroups(pexprs, handlerTable, steps);
		this.resolvedGroups = new LinkedList<SynthesisGroup>();
	}
	
	private List<SynthesisGroup> createGroups(List<PartialExpression> pexprs, HandlerTable handlerTable, int steps) {
		List<SynthesisGroup> groups = new LinkedList<SynthesisGroup>(); 
		for (PartialExpression pexpr : pexprs) {
			groups.add(new SynthesisGroup(pexpr, handlerTable, steps));
		}
		return groups;
	}

	public void run(){
		while(!activeGroups.isEmpty() && this.round < maxRounds){
			List<SynthesisGroup> ended = new LinkedList<SynthesisGroup>();
			for (SynthesisGroup group : activeGroups) {
				group.run();
				if (group.hasEnded()){
					ended.add(group);
				}
			}
			
			resolvedGroups.addAll(ended);
			activeGroups.removeAll(ended);
			
			this.round++;
		}
	}
}
