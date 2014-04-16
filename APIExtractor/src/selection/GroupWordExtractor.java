package selection;

import selection.parser.one.SentenceZero;
import selection.parser.one.Word;
import selection.parser.one.trees.SentenceOne;
import selection.scorers.Scorer;
import definitions.Declaration;

public class GroupWordExtractor extends WordExtractor {
	
	private DeclarationParserPipeline strategy;

	public GroupWordExtractor(DeclarationParserPipeline strategy) {
		this.strategy = strategy;
	}

	@Override
	public Word[] getWords(Declaration decl) {
		return strategy.parser(new DeclarationSentenceZero(make(decl.getCaracteristicWordGroups()))).getWords();
	}

	private SentenceZero[] make(String[] groups) {
		int length = groups.length;
		SentenceZero[] sentences = new SentenceZero[length];
		for (int i = 0; i < sentences.length; i++) {
			sentences[i] = new SentenceZero(groups[i]);
		}
		return sentences;
	}

}
