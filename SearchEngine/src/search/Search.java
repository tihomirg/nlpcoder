package search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import nlp.parser.Group;
import api.StabileAPI;
import definitions.Declaration;

public class Search {

	private Table table;
	private Map<Declaration, RichDeclaration> declToRichDecl;
	private ScorerPipeline scorer;
	private ScoreListener listener;
	
	public Search(ScorerPipeline scorer, ScoreListener listener) {
		this.table = new Table();
		this.declToRichDecl = new HashMap<Declaration, RichDeclaration>();
		this.scorer = scorer;
		this.listener = listener;
	}
	
	public void add(StabileAPI api){
		addAll(api.getDecls());
		addToTable();
		declToRichDecl = null;
	}
	
	private void addToTable() {
		for (RichDeclaration rd : declToRichDecl.values()) {
			table.add(rd);
		}
	}

	private void addAll(List<Declaration> decls) {
		for (Declaration decl : decls) {
			add(decl);
		}
	}

	public void add(Declaration decl){
		Declaration uniqueDecl = decl.getUniqueDecl();
		RichDeclaration rd = getRichDeclaration(uniqueDecl, 0);
		rd.addAll(decl.getReceiverTokens());
	}
	
	public PriorityQueue<RichDeclaration> search(Group searchKeyGroup){
		List<WToken> searchKeys = searchKeyGroup.getSearchKeys();
		
		for (WToken searchKey : searchKeys) {
			table.search(searchKey);
		}
		
		PriorityQueue<RichDeclaration> bestRDs = listener.getBestRDs();
		publish(bestRDs);
		listener.clear();

		return bestRDs;
	}
	
	//For testing purpose
	private void publish(PriorityQueue<RichDeclaration> bestRDs) {
		// TODO Auto-generated method stub
		for (RichDeclaration rd : bestRDs) {
			System.out.println(rd);
		}
	}

	private RichDeclaration getRichDeclaration(Declaration uniqueDecl, double frequency) {
		RichDeclaration rd = null;
		
		if (!this.declToRichDecl.containsKey(uniqueDecl)){
			rd = new RichDeclaration(uniqueDecl, frequency, scorer, listener);
			this.declToRichDecl.put(uniqueDecl, rd);
		} else {
			rd = this.declToRichDecl.get(uniqueDecl);
		}
		
		return rd;
	}
}
