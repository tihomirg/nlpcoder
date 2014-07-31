package search;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import search.comparators.RichDeclarationComparatorDesc;
import search.nlp.parser.RichToken;
import api.StabileAPI;
import definitions.Declaration;
import deserializers.FrequencyDeserializer;

public class Search {

	private static final int INITIAL_CAP = 100;
	private static final RichDeclarationComparatorDesc COMPARATOR_DESC = new RichDeclarationComparatorDesc();
	private Table table;
	private ScorerPipeline scorer;
	private SelectListener listener;
	private FrequencyDeserializer fd;
	private int maxDecls;
	private int primaryIndex;
	private double initialPrimaryWeight;
	private int secondaryIndex;
	private double initialSecondaryWeight;
	
	public Search(ScorerPipeline scorer, SelectListener listener, StabileAPI api, FrequencyDeserializer fd, int maxDecls, int primaryIndex, double initialPrimaryWeight, int secondaryIndex, double initialSecondaryWeight) {
		this.table = new Table();
		this.scorer = scorer;
		this.listener = listener;
		this.fd = fd;
		this.maxDecls = maxDecls;
		this.primaryIndex = primaryIndex;
		this.initialPrimaryWeight = initialPrimaryWeight;
		this.secondaryIndex = secondaryIndex;
		this.initialSecondaryWeight = initialSecondaryWeight;
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
		table.add(new DeclarationSelectionEntry(decl, fd.getLogFrequency(decl.getId()), listener, primaryIndex, initialPrimaryWeight, secondaryIndex, initialSecondaryWeight));
	}
	
	public List<RichDeclaration> search(RichToken richToken) {
		List<WToken> searchKeys = richToken.getAllTokens();
		
		for (WToken searchKey : searchKeys) {
			table.search(searchKey);
		}
		
		List<DeclarationSelectionEntry> selected = listener.getSelected();
		PriorityQueue<RichDeclaration> rds = rank(selected, richToken);
		
		listener.clear();
		return keepBest(rds);
	}

	private List<RichDeclaration> keepBest(PriorityQueue<RichDeclaration> rds) {
		List<RichDeclaration> filtered = new LinkedList<RichDeclaration>();
		for (int i=0; i<maxDecls && !rds.isEmpty(); i++) {
			filtered.add(rds.remove());
		}
		return filtered;
	}

	private PriorityQueue<RichDeclaration> rank(List<DeclarationSelectionEntry> selectedEntries, RichToken richToken) {
		PriorityQueue<RichDeclaration> rds = new PriorityQueue<RichDeclaration>(INITIAL_CAP, COMPARATOR_DESC);
		for (DeclarationSelectionEntry selectedEntry: selectedEntries) {
			rds.add(new RichDeclaration(selectedEntry.getDecl(), scorer.calculate(selectedEntry, richToken)));
		}
		
		return rds;
	}
}
