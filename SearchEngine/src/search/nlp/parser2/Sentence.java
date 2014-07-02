package search.nlp.parser2;

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

}
