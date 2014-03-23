package selection;

import selection.parser.one.SentenceZero;
import selection.parser.one.Word;

public class DeclarationSentenceZero implements IDeclarationSentence {

	private SentenceZero[] sentences;

	public DeclarationSentenceZero(SentenceZero[] sentences) {
		this.sentences = sentences;
	}

	public SentenceZero[] getSentences() {
		return sentences;
	}
	
	public Word[] getWords(){
		return null;
	}

	@Override
	public IDeclarationSentence apply(IDeclarationParser iDeclarationParser) {
		return iDeclarationParser.parse(this);
	}
}
