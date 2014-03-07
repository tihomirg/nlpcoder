package selection;

import java.util.List;

import selection.parser.one.SentenceZero;

public class DeclarationSentenceZero implements IDeclarationSentence {

	private SentenceZero[] sentences;

	public DeclarationSentenceZero(SentenceZero[] sentences) {
		this.sentences = sentences;
	}

	public SentenceZero[] getSentences() {
		return sentences;
	}

	@Override
	public IDeclarationSentence apply(IDeclarationParser iDeclarationParser) {
		return iDeclarationParser.parse(this);
	}
}
