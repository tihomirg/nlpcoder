package search.nlp.parser2;

import java.util.LinkedList;
import java.util.List;

import api.Local;
import util.Pair;

public class Input {

	private String originalText;
	private String literalizedText;
	private List<Pair<Integer, String>> strings;
	private List<Pair<Integer, String>> numbers;
	private List<Pair<Integer, String>> bools;
	private List<Pair<Integer, Local>> locals;
	private String preprocessedText;
	private List<Sentence> sentences;
	
	public Input(String originalText) {
		this.originalText = originalText;
		this.strings = new LinkedList<Pair<Integer, String>>();
		this.numbers = new LinkedList<Pair<Integer, String>>();
		this.bools = new LinkedList<Pair<Integer, String>>();
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
	
	public void setBooleanLiterals(List<Pair<Integer, String>> bools) {
		this.bools = bools;
	}
	
	public void setNumberLiterals(List<Pair<Integer, String>> numbers) {
		this.numbers = numbers;
	}
	
	public List<Pair<Integer, String>> getBooleanLiterals() {
		return bools;
	}
	
	public List<Pair<Integer, String>> getStringLiterals() {
		return strings;
	}
	
	public List<Pair<Integer, String>> getNumberLiterals() {
		return numbers;
	}

	public void setLocals(List<Pair<Integer, Local>> locals) {
		this.locals = locals;
	}
	
	public List<Pair<Integer, Local>> getLocals() {
		return locals;
	}

	public void setPreprocessedText(String preprocessedText) {
		this.preprocessedText = preprocessedText;
	}
	
	public String getPreprocessedText() {
		return preprocessedText;
	}

	public void setSentences(List<Sentence> sentences) {
		this.sentences = sentences;
	}
	
	public List<Sentence> getSentences() {
		return sentences;
	}
}
