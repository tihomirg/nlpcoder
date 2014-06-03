package selection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import api.StabileAPI;
import definitions.Declaration;

public class Selection {

	private Table table;
	private Map<Declaration, RichDeclaration> declToRichDecl;
	private ScorerPipeline scorer;
	private ScoreListener listener;
	
	public Selection(ScorerPipeline scorer, ScoreListener listener) {
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
