package merging.core;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import statistics.posttrees.Expr;
import synthesis.Param;
import synthesis.PartialExpression;
import synthesis.PartialExpressionScorer;
import synthesis.comparators.PartialExpressionComparatorAsce;
import synthesis.comparators.PartialExpressionComparatorDesc;
import synthesis.handlers.Handler;
import synthesis.handlers.SearchKey;
import util.Pair;

public class SynthesisLevel {
	private static final int DEFAULT_CAPACITY = 100;
	private static final PartialExpressionComparatorDesc COMPARATOR_DESC = new PartialExpressionComparatorDesc();
	private static final PartialExpressionComparatorAsce COMPARATOR_ASCE = new PartialExpressionComparatorAsce();

	private HandlerTable handlerTable;
	private PriorityQueue<PartialExpression> activeBest;
	private List<PartialExpression> processed;
	private int maxNumOfPexpr;
	private int freeSpaces;
	private PriorityQueue<PartialExpression> activeWorst;

	private int levelId;
	private PartialExpressionScorer scorer;

	public SynthesisLevel(int levelId, HandlerTable handlerTable, int maxNumOfPexpr, PartialExpressionScorer scorer) {
		this.levelId = levelId;
		this.handlerTable = handlerTable;

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

	private Pair<List<PartialExpression>, List<PartialExpression>> resolve(PartialExpression pexp) {		
		Param param = pexp.getParam();

		SearchKey searchKey = param.getSearchKey();
		Handler handler = searchKey.getHandler(handlerTable);

		PriorityQueue<Expr> queue = handler.handle(searchKey);

		//System.out.println(queue);

		return createNewPartialExprs(pexp, param, queue);		
	}

	private Pair<List<PartialExpression>,List<PartialExpression>> createNewPartialExprs(PartialExpression pexp, Param param, PriorityQueue<Expr> queue) {
		List<PartialExpression> partial = new LinkedList<PartialExpression>();
		List<PartialExpression> completed = new LinkedList<PartialExpression>();

		if (queue != null){
			for (Expr expr : queue) {
				PartialExpression newPexp = pexp.instantiate(param, expr, scorer);
				if(newPexp.isCompleted()) {
					completed.add(newPexp);
				} else {
					partial.add(newPexp);
				}
			}
		}

		return new Pair<List<PartialExpression>,List<PartialExpression>>(completed, partial);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Level: "+levelId+"\n");
		sb.append("Processed: "+processed+"\n");
		sb.append("Active: "+activeBest+"\n");
		return sb.toString();
	}
}
