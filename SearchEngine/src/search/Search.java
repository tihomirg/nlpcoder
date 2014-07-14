package search;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import search.nlp.parser.Group;
import search.nlp.parser2.RichToken;
import api.StabileAPI;
import definitions.Declaration;
import deserializers.FrequencyDeserializer;

public class Search {

	private Table table;
	private ScorerPipeline scorer;
	private ScoreListener listener;
	private int[][] indexScoress;
	
	private FrequencyDeserializer fd;
	
	public Search(ScorerPipeline scorer, ScoreListener listener, StabileAPI api, int[][] indexScoress, FrequencyDeserializer fd) {
		this.table = new Table();
		this.scorer = scorer;
		this.listener = listener;
		this.indexScoress = indexScoress;
		this.fd = fd;
		add(api);
	}
	
	public void add(StabileAPI api){
		addAll(api.getUniqueDecls());
	}

	private void addAll(List<Declaration> decls) {
		for (Declaration decl : decls) {
			add(decl);
		}
	}

	public void add(Declaration decl){
		table.add(new RichDeclaration(decl, fd.getLogFrequency(decl.getId()), scorer, listener, indexScoress));
	}
	
	public List<RichDeclaration> search(Group searchKeyGroup){
		Set<WToken> searchKeys = searchKeyGroup.getSearchKeys();
		
		for (WToken searchKey : searchKeys) {
			table.search(searchKey);
		}
		
		PriorityQueue<RichDeclaration> bestRDs = listener.getBestRDs();
		List<RichDeclaration> rds = cloneBestRDs(bestRDs);
		
		publish(searchKeys, bestRDs);
		listener.clear();

		return rds;
	}

	private List<RichDeclaration> cloneBestRDs(PriorityQueue<RichDeclaration> bestRDs) {
		List<RichDeclaration>  rds = new LinkedList<RichDeclaration>();
		for (RichDeclaration rd : bestRDs) {
			rds.add(rd.clone());
		}
		return rds;
	}
	
	//For testing purpose
	private void publish(Collection<WToken> searchKeys, PriorityQueue<RichDeclaration> bestRDs) {
		System.out.println("For words: "+ searchKeys);
		
		while(!bestRDs.isEmpty()) {
			System.out.println(bestRDs.remove());
		}
		
		System.out.println();
	}
	
	public List<RichDeclaration> search(RichToken richToken) {
		List<WToken> searchKeys = richToken.getAllTokens();
		
		for (WToken searchKey : searchKeys) {
			table.search(searchKey);
		}
		
		PriorityQueue<RichDeclaration> bestRDs = listener.getBestRDs();
		List<RichDeclaration> rds = cloneBestRDs(bestRDs);
		
		publish(searchKeys, bestRDs);
		listener.clear();
		return rds;
	}
}
