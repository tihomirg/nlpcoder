package search;

import java.util.List;
import java.util.PriorityQueue;

import nlp.parser.Group;
import api.StabileAPI;
import definitions.Declaration;

public class Search {

	private Table table;
	private ScorerPipeline scorer;
	private ScoreListener listener;
	private int[][] indexScoress;
	
	public Search(ScorerPipeline scorer, ScoreListener listener, StabileAPI api, int[][] indexScoress) {
		this.table = new Table();
		this.scorer = scorer;
		this.listener = listener;
		this.indexScoress = indexScoress;
		add(api);
	}
	
	public void add(StabileAPI api){
		addAll(api.getDecls());
	}

	private void addAll(List<Declaration> decls) {
		for (Declaration decl : decls) {
			add(decl);
		}
	}

	public void add(Declaration decl){
		table.add(new RichDeclaration(decl, 0, scorer, listener, indexScoress));
	}
	
	public PriorityQueue<RichDeclaration> search(Group searchKeyGroup){
		List<WToken> searchKeys = searchKeyGroup.getSearchKeys();
		
		for (WToken searchKey : searchKeys) {
			table.search(searchKey);
		}
		
		PriorityQueue<RichDeclaration> bestRDs = listener.getBestRDs();
		publish(searchKeys, bestRDs);
		listener.clear();

		return bestRDs;
	}
	
	//For testing purpose
	private void publish(List<WToken> searchKeys, PriorityQueue<RichDeclaration> bestRDs) {
		System.out.println("For words: "+ searchKeys);
		
		while(!bestRDs.isEmpty()) {
			System.out.println(bestRDs.remove());
		}
		
		System.out.println();
	}
}
