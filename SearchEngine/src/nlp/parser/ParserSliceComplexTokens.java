package nlp.parser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class ParserSliceComplexTokens implements IParser{

	private ComplexWordDecomposer decomposer;

	public ParserSliceComplexTokens(ComplexWordDecomposer decomposer) {
		this.decomposer = decomposer;
	}

	@Override
	public Input parse(Input input) {
		for (Sentence sentence : input.getSentences()) {
			Map<Integer, Group> groupMap = sentence.getGroupMap();

			for (Group group : groupMap.values()) {
				Token token = group.getToken();
				group.setTokenDecompositions(decomposer.decomposeToken(token));
			}
		}

		return input;
	}
}
