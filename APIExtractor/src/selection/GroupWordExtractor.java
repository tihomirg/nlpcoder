package selection;

import selection.parser.one.SentenceZero;
import selection.parser.one.Word;
import selection.parser.one.trees.SentenceOne;
import definitions.Declaration;

public class GroupWordExtractor implements IWordExtractor {
	
	private DeclarationParserPipeline strategy;

	public GroupWordExtractor(DeclarationParserPipeline strategy) {
		this.strategy = strategy;
	}

	@Override
	public Indexes get(Declaration decl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Word[] getWords(Declaration decl) {
		return ((DeclarationSentenceOne) strategy.parser(new DeclarationSentenceZero(make(decl.getGroups())))).getWords();
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
