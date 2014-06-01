package nlp.parser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class ComplexWordDecomposer {

	private StanfordCoreNLP pipeline;

	public ComplexWordDecomposer(StanfordCoreNLP pipeline) {
		this.pipeline = pipeline;
	}	

	public List<Token> decomposeTokenIfNeeded(Token oldToken) {
		return decomposeStringIfNeeded(oldToken.getLemma());
	}

	public List<Token> decomposeStringIfNeeded(String lemma) {
		List<Token> newTokens = new LinkedList<Token>();
		List<String> newLemmas = slice(lemma);

		if (newLemmas.size() > 1){

			String text = concatenate(newLemmas, " ");

			Annotation document = new Annotation(text);

			pipeline.annotate(document);

			List<CoreMap> sentences = document.get(SentencesAnnotation.class);

			for(CoreMap sentence: sentences) {
				for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
					newTokens.add(new Token(token.get(TextAnnotation.class), token.get(PartOfSpeechAnnotation.class), token.index()));
				}
			}
		}

		return newTokens;
	}	

	public List<Token> decomposeString(String lemma) {
		List<Token> newTokens = new LinkedList<Token>();
		List<String> newLemmas = slice(lemma);

		String text = concatenate(newLemmas, " ");

		Annotation document = new Annotation(text);

		pipeline.annotate(document);

		List<CoreMap> sentences = document.get(SentencesAnnotation.class);

		for(CoreMap sentence: sentences) {
			for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
				newTokens.add(new Token(token.get(TextAnnotation.class), token.get(PartOfSpeechAnnotation.class), token.index()));
			}
		}

		return newTokens;
	}

	private String concatenate(List<String> lemmas, String separator){
		StringBuffer sb = new StringBuffer("");
		if (lemmas.size() > 0){
			sb.append(lemmas.get(0));

			for (int i = 1; i < lemmas.size(); i++) {
				sb.append(separator+lemmas.get(i));
			}
		}
		return sb.toString();
	}

	private List<String> slice(String sentence) {
		String word ="";
		List<String> words = new ArrayList<String>();

		boolean prevSep = true;

		boolean prevLower = true;

		boolean lastAdded = true;
		for(char c: sentence.toCharArray()){

			if (Character.isLetter(c)) {
				if (Character.isUpperCase(c)){
					if ((prevSep || prevLower) && !word.equals("")){
						words.add(word);
						word=Character.toString(Character.toLowerCase(c));
					} else {
						word+=Character.toLowerCase(c);
					}
					prevSep = false;
					prevLower = false;	    	
				} else {
					prevLower = true;
					word+=c;
				}
				lastAdded = false;
			} else {
				if (!lastAdded){
					lastAdded = true;
					words.add(word);
					word = "";
				}
				prevSep = true;
			}
		}

		if (!lastAdded) words.add(word);

		return words;
	}
}
