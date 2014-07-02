package search.nlp.parser2;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import nlp.parser.one.WordCorrector;
import util.Pair;
import api.Local;

public class ParserForCorrectingWords implements IParser{

	private WordCorrector corrector;

	public ParserForCorrectingWords(WordCorrector corrector) {
		this.corrector = corrector;
	}
	
	@Override
	public Input parse(Input curr) {
		String text = curr.getPreprocessedText();

		List<Pair<Integer, Local>> locals = curr.getLocals();
		List<Pair<Integer, String>> bools = curr.getBooleanLiterals();
		List<Pair<Integer, String>> strings = curr.getStringLiterals();
		List<Pair<Integer, String>> numbers = curr.getNumberLiterals();

		Set<Integer> filtered = getFiltered(locals, bools, strings, numbers);
		
		List<Pair<Integer, String>> words = extractWords(filtered, text);

		curr.setPreprocessedText(correctText(text, words, locals, bools, strings, numbers));
		
		return curr;
	}

	private String correctText(String text, List<Pair<Integer, String>> words,
			List<Pair<Integer, Local>> locals,
			List<Pair<Integer, String>> bools,
			List<Pair<Integer, String>> strings,
			List<Pair<Integer, String>> numbers) {

		String localizedText = text;

		for (int i = 0; i < words.size(); i++) {
			Pair<Integer, String> pair = words.get(i);
			int startPos = pair.getFirst();
			String word = pair.getSecond();

			String correction = corrector.tryCorrect(word);
			
			if (correction != null){
				localizedText = substituteWords(word, correction, startPos, localizedText);

				int fix = word.length() - correction.length();
				fixLocalIndexes(fix, locals, startPos);
				
				fixIndexes(fix, bools, startPos);
				fixIndexes(fix, numbers, startPos);
				fixIndexes(fix, strings, startPos);
			}
		}

		return localizedText;		
	}
	
	private String substituteWords(String word, String correction, int startPos, String text) {
		String prefix = text.substring(0, startPos);
		String postFix = text.substring(startPos + word.length());
		return prefix +correction+ postFix;
	}


	private void fixLocalIndexes(int fix, List<Pair<Integer, Local>> pairs, int startPos) {
		for (Pair<Integer, Local> pair : pairs) {
			int startPos2 = pair.getFirst();
			if (startPos2 > startPos){
				pair.setFirst(startPos2 - fix);
			}
		}
	}
	
	private void fixIndexes(int fix, List<Pair<Integer, String>> pairs, int startPos) {
		for (Pair<Integer, String> pair : pairs) {
			int startPos2 = pair.getFirst();
			if (startPos2 > startPos){
				pair.setFirst(startPos2 - fix);
			}
		}
	}

	private List<Pair<Integer, String>> extractWords(Set<Integer> filtered, String text) {
		List<Pair<Integer, String>> words = new LinkedList<Pair<Integer,String>>();

		String current = text;
		int index = 0;
		String word = "";
		while(index < text.length()){
			char currChar = text.charAt(index);
			if (!Character.isWhitespace(currChar)){
				if (!filtered.contains(index)) {
					int beginingPos = index;
					boolean complexWord = false;
					boolean lowerCase = false;
					do{
						lowerCase = lowerCase || Character.isLowerCase(currChar);
						complexWord = complexWord || (lowerCase && Character.isUpperCase(currChar));

						word += currChar;
						index++;

						if (index < text.length()){
							currChar = text.charAt(index);
						} else break;
					} while(!Character.isWhitespace(currChar));

					if (!complexWord){
						words.add(new Pair<Integer, String>(beginingPos, word));
					}
					word = "";
				} else {
					do{
						index++;
						if (index < text.length()){
							currChar = text.charAt(index);
						} else break;
					} while(!Character.isWhitespace(currChar));					
				}
			} else {
				index++;
			}

		}

		return words;
	}

	private Set<Integer> getFiltered(List<Pair<Integer, Local>> locals, List<Pair<Integer, String>> bools, List<Pair<Integer, String>> strings, List<Pair<Integer, String>> numbers) {
		Set<Integer> filtered = new HashSet<Integer>();

		for (Pair<Integer, Local> pair : locals) {
			filtered.add(pair.getFirst());	
		}

		for (Pair<Integer, String> pair : bools) {
			filtered.add(pair.getFirst());	
		}

		for (Pair<Integer, String> pair : strings) {
			filtered.add(pair.getFirst());	
		}

		for (Pair<Integer, String> pair : numbers) {
			filtered.add(pair.getFirst());	
		}

		return filtered;
	}

}
