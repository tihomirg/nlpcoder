package parser;

import java.util.List;
import java.util.Map;

import edu.stanford.nlp.dcoref.CorefChain;

public class Input {

	private String text;
	private List<Sentence> sentences;
	private Map<Integer, CorefChain> corefGraph;
	
	public Input(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}

	public void setSentences(List<Sentence> sentences) {
		this.sentences = sentences;
	}

	public void setCorefGraph(Map<Integer, CorefChain> corefGraph) {
		this.corefGraph = corefGraph;
	}
}
