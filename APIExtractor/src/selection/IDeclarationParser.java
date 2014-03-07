package selection;

public abstract class IDeclarationParser {
	
	public IDeclarationSentence parse(IDeclarationSentence sentence) { 
		return sentence.apply(this);
	}
	
	public IDeclarationSentence parse(DeclarationSentenceZero curr) { 
		return null;
	}
	
	public IDeclarationSentence parse(DeclarationSentenceOne curr) { 
		return null;
	}

}
