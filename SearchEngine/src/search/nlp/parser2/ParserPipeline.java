package search.nlp.parser2;

/**
 * 1) ParserForLiterals
 * 2) ParserForLocals
 * 3) ParserForCorrectingWords //We do not repair locals and literals
 * 4) ParserForNaturalLanguage
 * 5) ParserForRichLiteralsAndLocals
 * 6) ParserForSemantincGraphRelations
 * 7) ParserForRightHandSideNeighbours
 * 8) ParserForComplexTokens
 * 9) ParserForWTokens
 * 10) ParserForIndexes
 */
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
