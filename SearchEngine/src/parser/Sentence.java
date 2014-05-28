package parser;

import java.util.List;

import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.util.CoreMap;

public class Sentence {
	
	private CoreMap map;
	private List<Token> tokens;
	private SemanticGraph dependancyGraph;
	
	public Sentence(CoreMap map) {
		this.map = map;
	}

	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}

	public void setDependancyGraph(SemanticGraph dependancyGraph) {
		this.dependancyGraph = dependancyGraph;
	}
}
