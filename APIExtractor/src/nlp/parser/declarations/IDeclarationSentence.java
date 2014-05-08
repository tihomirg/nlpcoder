package nlp.parser.declarations;

import nlp.parser.one.Word;

public interface IDeclarationSentence {
	public IDeclarationSentence apply(IDeclarationParser iDeclarationParser);
	
	public Word[] getWords();
}
