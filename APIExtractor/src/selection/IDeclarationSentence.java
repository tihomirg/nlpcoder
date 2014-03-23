package selection;

import selection.parser.one.Word;

public interface IDeclarationSentence {
	public IDeclarationSentence apply(IDeclarationParser iDeclarationParser);
	
	public Word[] getWords();
}
