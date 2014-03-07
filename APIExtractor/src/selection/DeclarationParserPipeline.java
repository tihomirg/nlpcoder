package selection;

public class DeclarationParserPipeline {

	private IDeclarationParser[] parsers;

	public DeclarationParserPipeline(IDeclarationParser[] parsers) {
		this.parsers = parsers;
	}
	
	public IDeclarationSentence parser(IDeclarationSentence sentence){
		IDeclarationSentence curr = sentence;
		for(IDeclarationParser parser: parsers){
			curr = parser.parse(curr);
		}
		return curr;
	}
}
