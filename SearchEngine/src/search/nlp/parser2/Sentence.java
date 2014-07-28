package search.nlp.parser2;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.util.CoreMap;

public class Sentence {

	private CoreMap rawSentence;
	private List<RichToken> richTokens;
	private SemanticGraph semanticGraph;
	private Map<Integer, RichToken> indexesToRichTokens;

	public Sentence(CoreMap rawSentence) {
		this.rawSentence = rawSentence;
	}

	public void setRichTokens(List<RichToken> richTokens) {
		this.richTokens = richTokens;
	}

	public void setSemanticGraph(SemanticGraph semanticGraph) {
		this.semanticGraph = semanticGraph;
	}
	
	public List<RichToken> getRichTokens() {
		return richTokens;
	}
	
	public SemanticGraph getSemanticGraph() {
		return semanticGraph;
	}

	public void setIndexesToRichTokens(Map<Integer, RichToken> indexesToRichTokens) {
		this.indexesToRichTokens = indexesToRichTokens;
	}
	
	public Map<Integer, RichToken> getIndexesToRichTokens() {
		return indexesToRichTokens;
	}

	@Override
	public String toString() {
		return "Sentence ["
				+ "\nrawSentence=" + rawSentence
				+ "\nsemanticGraph=" + semanticGraph
				+ "\nrichTokens="+ richTokens+ "]\n";
	}

	public List<RichToken> getSearchKeyRichTokens() {
		List<RichToken> tokens = new LinkedList<RichToken>();
		
		for (RichToken richToken : richTokens) {
			if(richToken.isNormal()) tokens.add(richToken);
		}
		
		return tokens;
	}

	public List<RichToken> getStringLiteralRichTokens() {
		List<RichToken> tokens = new LinkedList<RichToken>();
		
		for (RichToken richToken : richTokens) {
			if(richToken.isStringLiteral()) tokens.add(richToken);
		}
		
		return tokens;
	}
	
	public List<RichToken> getNumberLiteralRichTokens() {
		List<RichToken> tokens = new LinkedList<RichToken>();
		
		for (RichToken richToken : richTokens) {
			if(richToken.isNumberLiteral()) tokens.add(richToken);
		}
		
		return tokens;
	}	
	
	public List<RichToken> getBooleanLiteralRichTokens() {
		List<RichToken> tokens = new LinkedList<RichToken>();
		
		for (RichToken richToken : richTokens) {
			if(richToken.isBooleanLiteral()) tokens.add(richToken);
		}
		
		return tokens;
	}
	
	public List<RichToken> getLocals() {
		List<RichToken> tokens = new LinkedList<RichToken>();
		
		for (RichToken richToken : richTokens) {
			if(richToken.isLocal()) tokens.add(richToken);
		}
		
		return tokens;
	}	
}
