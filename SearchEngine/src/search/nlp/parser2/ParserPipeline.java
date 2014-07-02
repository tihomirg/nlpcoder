package search.nlp.parser2;

/**
 * 1) ParserForLiterals
 * 2) ParserForLocals
 * 2/3) ParserForWordRepair //We do not repair locals and literals
 * 3) ParserForNaturalLanguage
 * 4) ParserForRichLiteralsAndLocals
 * 5) ParserForSemantincGraphRelations
 * 6) ParserForRightHandSideNeighbours
 * 7) ParserForComplexTokens
 * @author gvero
 *
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
