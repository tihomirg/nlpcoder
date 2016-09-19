package commons.examples;

import java.util.List;

import nlp.parser.Token;
import api.Local;
import search.ISText;

public class POSTaggerAllLeadingTokensExample extends POSTaggerExample {

	public POSTaggerAllLeadingTokensExample(String input, List<Token> expectedOutput) {
		super(input, expectedOutput);
	}
	
	public POSTaggerAllLeadingTokensExample(String input, List<Local> locals, List<Token> expectedOutput) {
		super(input, locals, expectedOutput);
	}
	
	protected List<Token> call(ISText iSText) {
		return iSText.runForLeadingTokens(input, locals);
	}
}
