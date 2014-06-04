package nlp.parser;

import java.util.LinkedList;
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
	
	public List<Sentence> getSentences() {
		return sentences;
	}
	
	public Map<Integer, CorefChain> getCorefGraph() {
		return corefGraph;
	}

	public List<Group> getSearchKeyGorups(){
		List<Group> list = new LinkedList<Group>();
		for (Sentence sentence : sentences) {
			list.addAll(sentence.getSearchKeyGroups());
		}
		return list;
	}
	
	public List<Group> getRemainingGorups(){
		List<Group> list = new LinkedList<Group>();
		for (Sentence sentence : sentences) {
			list.addAll(sentence.getRemainingGroups());
		}
		return list;		
	}	
	
	@Override
	public String toString() {
		return "text = " + text + "\n"+
	           "sentences = " + sentences+"\n"+
			   "corefGraph = " + corefGraph + "\n";
	}
	
}
