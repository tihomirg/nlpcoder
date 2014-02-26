package selection;

public class ParserPipeline {

	private IParser[] parsers;
	
	public ParserPipeline(IParser[] parsers) {
		this.parsers = parsers;
	}
	
	public ISentence parse(ISentence sentence){
		ISentence curr = sentence;
		for(IParser parser: parsers){
			curr = parser.parse(curr);
		}
		return curr;
	}
}
