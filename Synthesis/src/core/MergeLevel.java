package core;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import statistics.posttrees.Expr;
import synthesis.Connection;
import synthesis.ExprGroup;
import synthesis.Param;
import synthesis.PartialExpression;
import synthesis.PartialExpressionScorer;
import synthesis.comparators.PartialExpressionComparatorAsce;
import synthesis.comparators.PartialExpressionComparatorDesc;
import synthesis.handlers.Handler;
import synthesis.handlers.SearchKey;
import util.Pair;

public class MergeLevel {

	private static final PartialExpressionComparatorDesc COMPARATOR_DESC = new PartialExpressionComparatorDesc();
	private static final PartialExpressionComparatorAsce COMPARATOR_ASCE = new PartialExpressionComparatorAsce();

	private PriorityQueue<PartialExpression> activeBest;
	private List<PartialExpression> processed;
	private int maxNumOfPexpr;
	private int freeSpaces;
	private PriorityQueue<PartialExpression> activeWorst;

	private int levelId;
	private PartialExpressionScorer scorer;

	public MergeLevel(int levelId, int maxNumOfPexpr, PartialExpressionScorer scorer) {
		this.levelId = levelId;

		this.processed = new LinkedList<PartialExpression>();
		this.maxNumOfPexpr = maxNumOfPexpr;

		this.activeBest = new PriorityQueue<PartialExpression>(this.maxNumOfPexpr+1, COMPARATOR_DESC);
		this.activeWorst = new PriorityQueue<PartialExpression>(this.maxNumOfPexpr+1, COMPARATOR_ASCE);		

		this.freeSpaces = this.maxNumOfPexpr;
		this.scorer = scorer;
	}

	public Pair<List<PartialExpression>, List<PartialExpression>> resolveAll() {
		int size = activeSize();
		List<PartialExpression> completed = new LinkedList<PartialExpression>();
		List<PartialExpression> partial = new LinkedList<PartialExpression>();

		for(int i = 0; i < size; i++){
			Pair<List<PartialExpression>, List<PartialExpression>> resolved = resolveOne();
			completed.addAll(resolved.getFirst());
			partial.addAll(resolved.getSecond());
		}

		return new Pair<List<PartialExpression>, List<PartialExpression>>(completed, partial);
	}

	private int activeSize() {
		return this.activeWorst.size();
	}

	public Pair<List<PartialExpression>, List<PartialExpression>> resolveOne() {
		PartialExpression pexp = removeBestFromActive();
		this.processed.add(pexp);
		return resolve(pexp);
	}

	private Pair<List<PartialExpression>, List<PartialExpression>> resolve(PartialExpression pexp) {		
		Connection connection = pexp.getConnection();

		List<ExprGroup> relatedGroups = connection.getRelatedGroups();

		return createNewPartialExprss(pexp, connection, relatedGroups);		
	}	

	private Pair<List<PartialExpression>, List<PartialExpression>> createNewPartialExprss(PartialExpression pexp, Connection connection, List<ExprGroup> relatedGroups) {
		List<PartialExpression> completed = new LinkedList<PartialExpression>();
		List<PartialExpression> partial = new LinkedList<PartialExpression>();

		for (ExprGroup eGroup : relatedGroups) {

			Pair<List<PartialExpression>, List<PartialExpression>> pair = createNewPartialExprs(pexp, connection, eGroup.getCompletedExprs());
			completed.addAll(pair.getFirst());
			partial.addAll(pair.getSecond());

		}

		return new Pair<List<PartialExpression>, List<PartialExpression>>(completed, partial);
	}

	private Pair<List<PartialExpression>, List<PartialExpression>> createNewPartialExprs(PartialExpression pexpr1, Connection connection, List<PartialExpression> pexprs) {
		List<PartialExpression> completed = new LinkedList<PartialExpression>();
		List<PartialExpression> partial = new LinkedList<PartialExpression>();


		for (PartialExpression pexpr2: pexprs) {
			PartialExpression newPexpr = pexpr1.connect(connection, pexpr2, scorer);
			if (newPexpr.isCompletelyConnected()){
				completed.add(newPexpr);
			} else {
				partial.add(newPexpr);
			}
		}

		return new Pair<List<PartialExpression>, List<PartialExpression>>(completed, partial);
	}

	public void addAll(List<PartialExpression> pexprs){
		for (PartialExpression pexpr : pexprs) {
			add(pexpr);
		}
	}

	public void add(PartialExpression newPexp){		
		addToActive(newPexp);
		if (this.freeSpaces > 0){
			this.freeSpaces--;		
		} else {
			removeWorstFromActive();
		}
	}

	private void addToActive(PartialExpression pexp){
		this.activeBest.add(pexp);
		this.activeWorst.add(pexp);
	}

	private PartialExpression removeBestFromActive(){
		PartialExpression best = this.activeBest.remove();
		this.activeWorst.remove(best);
		return best;
	}

	private PartialExpression removeWorstFromActive(){
		PartialExpression worst = this.activeWorst.remove();
		this.activeBest.remove(worst);
		return worst;
	}

}
