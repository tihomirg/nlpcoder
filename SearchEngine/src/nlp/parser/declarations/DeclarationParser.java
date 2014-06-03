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
		simpleNameTokenization(decl);
		receiverTypeTokenization(decl);		
		remainderTypeTokenization(decl);
		
		return decl;
	}

	private void entireTokenization(Declaration decl) {
		List<Token> tokens = new LinkedList<Token>();		
		List<String> tokensAsStrings = decl.getTokensAsStrings();
		for (String name : tokensAsStrings) {
			tokens.addAll(decomposer.decomposeString(name));
		}
		decl.setTokens(tokens.toArray(new Token[tokens.size()]));
	}
	
	private void remainderTypeTokenization(Declaration decl) {
		List<Token> remainderTokens = new LinkedList<Token>();		
		List<String> remainder = decl.getRemainderOfTextualForm();
		for (String name : remainder) {
			remainderTokens.addAll(decomposer.decomposeString(name));
		}
		decl.setRemainderTokens(remainderTokens.toArray(new Token[remainderTokens.size()]));
	}	
	
	private void receiverTypeTokenization(Declaration decl) {
		List<Token> receiverTokens = new LinkedList<Token>();		
		List<String> receiver = decl.getReceiverTypeTextualForm();
		for (String name : receiver) {
			receiverTokens.addAll(decomposer.decomposeString(name));
		}
		decl.setReceiverTokens(receiverTokens.toArray(new Token[receiverTokens.size()]));
	}

	private void simpleNameTokenization(Declaration decl) {
		String simpleName = decl.getSimpleNameTextualForm();
		List<Token> simpleNameTokens = decomposer.decomposeString(simpleName);
		decl.setSimpleNameTokens(simpleNameTokens.toArray(new Token[simpleNameTokens.size()]));
	}

}
