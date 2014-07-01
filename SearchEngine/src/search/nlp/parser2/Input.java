package search.nlp.parser2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import util.Pair;

public class Input {

	private String originalText;
	private String literalizedText;
	private List<Pair<Integer, String>> strings;
	private List<Pair<Integer, String>> numbers;
	private Map<Integer, String> bools;
	
	public Input(String originalText) {
		this.originalText = originalText;
		this.strings = new LinkedList<Pair<Integer, String>>();
		this.numbers = new LinkedList<Pair<Integer, String>>();
		this.bools = new HashMap<Integer, String>();
	}
	
	public String getOriginalText() {
		return originalText;
	}

	public String getLiteralizedText() {
		return literalizedText;
	}

	public void setLiteralizedText(String literalizedText) {
		this.literalizedText = literalizedText;
	}

	public void setStringLitersals(List<Pair<Integer, String>> strings) {
		this.strings = strings;
	}
	
	public void setBooleanLiterals(Map<Integer, String> bools) {
		this.bools = bools;
	}
	
	public void setNumberLiterals(List<Pair<Integer, String>> numbers) {
		this.numbers = numbers;
	}
}
