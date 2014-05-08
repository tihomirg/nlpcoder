package nlp.parser.declarations;

import java.util.LinkedList;
import java.util.List;


import nlp.parser.one.ParserOne;
import nlp.parser.one.SentenceZero;
import nlp.parser.one.trees.SentenceOne;
import nlp.processors.WordProcessor;


public class DeclarationParserOne extends IDeclarationParser {

	private ParserOne parser;

	public DeclarationParserOne(WordProcessor processor) {
		this.parser = new ParserOne(processor);
	}
	
	public IDeclarationSentence parse(DeclarationSentenceZero sentence) {
		SentenceZero[] sentences = sentence.getSentences();
		List<SentenceOne> result = new LinkedList<SentenceOne>();
		for (SentenceZero iSentence : sentences) {
			result.add((SentenceOne) parser.parse(iSentence));
		}
		return new DeclarationSentenceOne(result);
	}
}

