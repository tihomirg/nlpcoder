package parser;

import java.util.LinkedList;
import java.util.List;

import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;

public class ParserNLP implements IParser {

	private StanfordCoreNLP pipeline;
	
	public ParserNLP(StanfordCoreNLP pipeline) {
		this.pipeline = pipeline;
	}

	@Override
	public Input parse(Input input) {
		String text = input.getText();
		
		Annotation document = new Annotation(text);

		pipeline.annotate(document);

		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		List<Sentence> sentenceReps = new LinkedList<Sentence>();

		for(CoreMap sentence: sentences) {
			Sentence sentenceRep = new Sentence(sentence);
			
			List<Token> tokens = new LinkedList<Token>();
			for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
				Token tokenRep = new Token(token);
				tokenRep.setLemma(token.get(TextAnnotation.class));
				tokenRep.setPos(token.get(PartOfSpeechAnnotation.class));
				tokens.add(tokenRep);
			}
			
			sentenceRep.setTokens(tokens);
			sentenceRep.setDependancyGraph(sentence.get(CollapsedCCProcessedDependenciesAnnotation.class));
			sentenceRep.setTree(sentence.get(TreeAnnotation.class));
			 
			sentenceReps.add(sentenceRep);
		}
		
		input.setSentences(sentenceReps);
		input.setCorefGraph(document.get(CorefChainAnnotation.class));
		
		return input;
	}
}
