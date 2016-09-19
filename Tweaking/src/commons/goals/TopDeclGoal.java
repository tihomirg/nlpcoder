package commons.goals;

import java.util.List;
import java.util.PriorityQueue;

import commons.scores.RankedScore;
import commons.scores.Score;
import commons.values.RankedValue;
import definitions.Declaration;
import search.RichDeclaration;
import search.SearchReport;
import search.comparators.RichDeclarationComparatorDesc;

public class TopDeclGoal implements Goal<SearchReport, RankedValue>{

	private static final int INITCAP = 100;
	private static final RichDeclarationComparatorDesc COMPARATOR_DESC = new RichDeclarationComparatorDesc();
	private Declaration[] decls;
	private String name;

	public TopDeclGoal(String name, Declaration[] decls) {
		this.decls = decls;
		this.name = name;
	}
	
	@Override
	public Score<RankedValue> getScore(List<SearchReport> actualOutput) {
		RankedValue value = new RankedValue();
		for (Declaration decl: decls) {
			value.add(new RankedValue(findRank(storeInPQ(actualOutput), decl)));
		}
		return new RankedScore(name, value);
	}

	private PriorityQueue<RichDeclaration> storeInPQ(List<SearchReport> actualOutput) {
		PriorityQueue<RichDeclaration> pq = new PriorityQueue<RichDeclaration>(INITCAP, COMPARATOR_DESC);
		for (SearchReport searchReport : actualOutput) {
			pq.addAll(searchReport.getResults());
		}
		return pq;
	}

	private int findRank(PriorityQueue<RichDeclaration> pq, Declaration decl) {
		int rank = 0;
		while(!pq.isEmpty()){
			RichDeclaration rd = pq.remove();
			if(rd.getDecl().equals(decl)) return rank;
			rank++;
		}
		return -1;
	}
	
	public Declaration[] getDecls() {
		return decls;
	}
	
	public int size() {
		return decls.length;
	}

	@Override
	public String getExpectedOutput() {
		return this.decls.toString();
	}
}
