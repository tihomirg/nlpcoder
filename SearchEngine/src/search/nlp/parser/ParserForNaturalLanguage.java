package search.nlp.parser;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import nlp.parser.Token;
import nlp.parser.one.WordCorrector;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;

public class ParserForNaturalLanguage implements IParser {

	private StanfordCoreNLP pipeline;
	private WordPosCorrector posCorrector;
	
	public ParserForNaturalLanguage(StanfordCoreNLP pipeline, WordPosCorrector posCorrector) {
		this.pipeline = pipeline;
		this.posCorrector = posCorrector;
	}

	@Override
	public Input parse(Input curr) {
		String text = curr.getPreprocessedText();
		
		Annotation document = new Annotation(text);

		pipeline.annotate(document);

		List<CoreMap> rawSentences = document.get(SentencesAnnotation.class);
		List<Sentence> sentences = new LinkedList<Sentence>();

		for(CoreMap rawSentence: rawSentences) {
			Sentence sentence = new Sentence(rawSentence);
			
			List<RichToken> richTokens = new LinkedList<RichToken>();
			Map<Integer, RichToken> indexesToRichTokens = new HashMap<Integer, RichToken>();
			
			for (CoreLabel rawToken: rawSentence.get(TokensAnnotation.class)) {
				int index = rawToken.index();
				String lemma = rawToken.get(LemmaAnnotation.class);
				Token token = new Token(rawToken.get(TextAnnotation.class), lemma, posCorrector.correctPOS(lemma, rawToken.get(PartOfSpeechAnnotation.class)));
				RichToken richToken = new RichToken(token, index, rawToken.beginPosition(), rawToken.endPosition());
				richTokens.add(richToken);
				indexesToRichTokens.put(index, richToken);
			}
			
			sentence.setRichTokens(richTokens);
			sentence.setIndexesToRichTokens(indexesToRichTokens);
			sentence.setSemanticGraph(rawSentence.get(CollapsedCCProcessedDependenciesAnnotation.class));

			sentences.add(sentence);
		}
		
		curr.setSentences(sentences);
		
		return curr;
	}

}
