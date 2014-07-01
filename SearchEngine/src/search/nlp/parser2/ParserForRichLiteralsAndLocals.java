package search.nlp.parser2;

import java.util.List;

import util.Pair;
import api.Local;

public class ParserForRichLiteralsAndLocals implements IParser {

	@Override
	public Input parse(Input curr) {
		List<Pair<Integer, Local>> locals = curr.getLocals();
		List<Pair<Integer, String>> bools = curr.getBooleanLiterals();
		List<Pair<Integer, String>> strings = curr.getStringLiterals();
		List<Pair<Integer, String>> numbers = curr.getNumberLiterals();
		
		List<Sentence> sentences = curr.getSentences();
		
		for (Sentence sentence : sentences) {
			List<RichToken> richTokens = sentence.getRichTokens();
			for (RichToken richToken : richTokens) {
				int position = richToken.getBeginPosition();
				
				Local local = tryFindLocal(locals, position);
				if (local != null) {
					richToken.setLocal(local);
				} else {
					String string = tryFindString(strings, position);
					if (string != null){
						richToken.setStringLiteral(string);
					} else {
						String number = tryFindNumber(numbers, position);
						if (number != null){
							richToken.setNumberLiteral(number);
						} else {
							String bool = tryFindBool(numbers, position);
							if (bool != null){
								richToken.setBooleanLiteral(bool);
							}
						}
					}
				}
			}
		}
		
		return curr;
	}

	private String tryFindBool(List<Pair<Integer, String>> numbers, int position) {
		return tryFind(numbers, position);
	}

	private String tryFind(List<Pair<Integer, String>> pairs, int position) {
		for (Pair<Integer, String> pair: pairs) {
			if(pair.getFirst() == position) return pair.getSecond();
		}
		
		return null;
	}

	private String tryFindNumber(List<Pair<Integer, String>> numbers, int position) {
		return tryFind(numbers, position);
	}

	private String tryFindString(List<Pair<Integer, String>> strings, int position) {
		return tryFind(strings, position);
	}

	private Local tryFindLocal(List<Pair<Integer, Local>> locals, int position) {
		for (Pair<Integer, Local> local : locals) {
			if(local.getFirst() == position) return local.getSecond();
		}
		
		return null;
	}

}
