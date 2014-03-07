package selection;

import java.util.LinkedList;
import java.util.List;

import selection.parser.one.ParserOne;
import selection.parser.one.SentenceZero;
import selection.parser.one.trees.SentenceOne;

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

