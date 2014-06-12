package search.nlp.parser;

import search.nlp.parser.IParser;

public class ParserPipeline {

	private IParser[] parsers;
	
	public ParserPipeline(IParser[] parsers) {
		this.parsers = parsers;
	}
	
	public Input parse(Input input){
		Input curr = input;
		for(IParser parser: parsers){
			curr = parser.parse(curr);
		}
		return curr;
	}
}
