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
	
	public Search(ScorerPipeline scorer, ScoreListener listener, StabileAPI api) {
		this.table = new Table();
		this.scorer = scorer;
		this.listener = listener;
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
		table.add(new RichDeclaration(decl, 0, scorer, listener));
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
		
		for (RichDeclaration rd : bestRDs) {
			System.out.println(rd);
		}
		
		System.out.println();
	}
}
