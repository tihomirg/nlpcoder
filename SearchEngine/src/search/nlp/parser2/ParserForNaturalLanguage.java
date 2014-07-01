package search.nlp.parser2;

import java.util.LinkedList;
import java.util.List;

import nlp.parser.Token;
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
	
	public ParserForNaturalLanguage(StanfordCoreNLP pipeline) {
		this.pipeline = pipeline;
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
			for (CoreLabel rawToken: rawSentence.get(TokensAnnotation.class)) {
				Token token = new Token(rawToken.get(TextAnnotation.class), rawToken.get(LemmaAnnotation.class), rawToken.get(PartOfSpeechAnnotation.class), rawToken.index());
			
				richTokens.add(new RichToken(token, rawToken.beginPosition(), rawToken.endPosition()));
			}
			
			sentence.setRichTokens(richTokens);
			sentence.setDependancyGraph(rawSentence.get(CollapsedCCProcessedDependenciesAnnotation.class));

			sentences.add(sentence);
		}
		
		curr.setSentences(sentences);
		
		return curr;
	}

}
