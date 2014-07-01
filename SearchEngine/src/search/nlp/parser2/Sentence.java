package search.nlp.parser2;

import java.util.List;

import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.util.CoreMap;

public class Sentence {

	private CoreMap rawSentence;
	private List<RichToken> richTokens;
	private SemanticGraph semanticGraph;

	public Sentence(CoreMap rawSentence) {
		this.rawSentence = rawSentence;
	}

	public void setRichTokens(List<RichToken> richTokens) {
		this.richTokens = richTokens;
	}

	public void setDependancyGraph(SemanticGraph semanticGraph) {
		this.semanticGraph = semanticGraph;
	}
	
	public List<RichToken> getRichTokens() {
		return richTokens;
	}

}
