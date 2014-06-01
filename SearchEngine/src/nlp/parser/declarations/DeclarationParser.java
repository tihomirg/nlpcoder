package nlp.parser.declarations;

import java.util.LinkedList;
import java.util.List;

import nlp.parser.ComplexWordDecomposer;
import nlp.parser.Token;
import definitions.Declaration;

public class DeclarationParser implements IDeclarationParser {

	private ComplexWordDecomposer decomposer;
	
	public DeclarationParser(ComplexWordDecomposer decomposer) {
		this.decomposer = decomposer;
	}
	
	@Override
	public Declaration parse(Declaration decl) {
		List<Token> tokens = new LinkedList<Token>();		
		
		List<String> tokensAsStrings = decl.getTokensAsStrings();
		
		for (String name : tokensAsStrings) {
			tokens.addAll(decomposer.decomposeString(name));
		}
		
		decl.setTokens(tokens.toArray(new Token[tokens.size()]));
		return decl;
	}

}
