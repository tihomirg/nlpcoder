package search.nlp.parser;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import util.Pair;


public class ParserForLiterals implements IParser {

	private static final String TRUE = "true";
	private static final String FALSE = "false";
	private static final String STRING_TYPE = "String";
	private static final String INT_TYPE = "Integer";
	private static final String BOOL_TYPE = "Boolean";

	@Override
	public Input parse(Input curr) {
		String text = curr.getOriginalText();

		//String literals have highest priority
		List<Pair<Integer, String>> strings = extractStringLiterals(text);		
		String literalizedText = substituteStrings(text, strings);

		List<Pair<Integer, String>> numbers = extractNumberLiterals(literalizedText);
		literalizedText = substituteNumbers(literalizedText, numbers, strings);

		List<Pair<Integer, String>> bools = extractBoolLiterals(literalizedText);
		literalizedText = substituteBools(literalizedText, bools, numbers, strings);		

		curr.setStringLitersals(strings);
		curr.setNumberLiterals(numbers);
		curr.setBooleanLiterals(bools);
		
		curr.setLiteralizedText(literalizedText);

		return curr;
	}

	public List<Pair<Integer, String>> extractBoolLiterals(String text) {
		List<Pair<Integer, String>> bools = new LinkedList<Pair<Integer, String>>();
		String current = text;

		int index = 0;
		boolean condition = true;
		while(true){
			if (condition && current.startsWith(TRUE)){
				if (current.length() == TRUE.length()){
					bools.add(new Pair<Integer, String>(index, current));
					break;
				} else {
					char space = current.charAt(TRUE.length());
					if (!Character.isLetter(space)){
						bools.add(new Pair<Integer, String>(index, TRUE));						
					}

					index += TRUE.length();
					current = current.substring(TRUE.length());
					condition = false;
				}
			} else {
				if (condition && current.startsWith(FALSE)){
					if (current.length() == FALSE.length()){
						bools.add(new Pair<Integer, String>(index, current));
						break;
					} else {
						char space = current.charAt(FALSE.length());
						if (!Character.isLetter(space)){
							bools.add(new Pair<Integer, String>(index, FALSE));						
						}

						index += FALSE.length();
						current = current.substring(FALSE.length());
						condition = false;
					}
				} else {
					if (current.length() > 0){
						char space = current.charAt(0);
						current = current.substring(1);
						condition = !Character.isLetter(space);
						index++;
					} else break;
				}
			}
		}

		return bools;
	}

	private String substituteNumbers(String text, List<Pair<Integer, String>> numbers, List<Pair<Integer, String>> strings) {
		String literalizedText = text;
		int integerTypeLength = INT_TYPE.length();

		for (int i = 0; i < numbers.size(); i++) {
			Pair<Integer, String> pair = numbers.get(i);
			int startPos = pair.getFirst();
			String number = pair.getSecond();

			literalizedText = substituteString(number, startPos, literalizedText, INT_TYPE);

			int fix = number.length() - integerTypeLength;
			fixIndexes(fix, numbers, startPos);
			fixIndexes(fix, strings, startPos);
		}

		return literalizedText;
	}

	private String substituteBools(String text, List<Pair<Integer, String>> bools, List<Pair<Integer, String>> numbers, List<Pair<Integer, String>> strings) {
		String literalizedText = text;
		int integerTypeLength = BOOL_TYPE.length();

		for (int i = 0; i < bools.size(); i++) {
			Pair<Integer, String> pair = bools.get(i);
			int startPos = pair.getFirst();
			String number = pair.getSecond();

			literalizedText = substituteString(number, startPos, literalizedText, BOOL_TYPE);
			int fix = number.length() - integerTypeLength;
			
			fixIndexes(fix, bools, startPos);
			fixIndexes(fix, numbers, startPos);
			fixIndexes(fix, strings, startPos);
		}

		return literalizedText;
	}	
	
	//5 6 7 8     15 16 17 18 19 20
	//" m y "     "  r  e  s  t  "
	public String substituteStrings(String text, List<Pair<Integer, String>> strings) {	
		String literalizedText = text;
		int stringTypeLenght = STRING_TYPE.length();

		for (int i = 0; i < strings.size(); i++) {
			Pair<Integer, String> pair = strings.get(i);
			int startPos = pair.getFirst();
			String string = pair.getSecond();

			literalizedText = substituteString(string, startPos, literalizedText, STRING_TYPE);

			fixIndexes(string.length() - stringTypeLenght, strings, startPos);
		}

		return literalizedText;
	}

	//0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 20
	//f i l e   S t r i n g     o  p  e  n     "  r  e  s  t  "
	private void fixIndexes(int fix, List<Pair<Integer, String>> pairs, int startPos) {
		for (Pair<Integer, String> pair : pairs) {
			int startPos2 = pair.getFirst();
			if (startPos2 > startPos){
				pair.setFirst(startPos2 - fix);
			}
		}
	}

	//0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20
	//f i l e   " m y "   o  p  e  n     "  r  e  s  t  "	
	private String substituteString(String string, int startPos, String text, String type) {
		String prefix = text.substring(0, startPos);
		String postFix = text.substring(startPos + string.length());
		return prefix +type+ postFix;
	}

	public List<Pair<Integer, String>> extractNumberLiterals(String text) {
		List<Pair<Integer, String>> numbers = new LinkedList<Pair<Integer, String>>();
		String current = text;

		int i = 0;
		while(i < current.length()){
			char currChar = current.charAt(i);
			if (Character.isDigit(currChar)){
				String number = "";
				int index = i;

				do{
					number += currChar;
					i++;
					if (i < current.length()){
						currChar = current.charAt(i);
					} else break;
				} while(Character.isDigit(currChar));
				numbers.add(new Pair<Integer, String>(index, number));
			} else {
				if (currChar == '-') {
					String number = "";
					int index = i;
					boolean atLeasOneDig = false;

					do{
						number += currChar;
						i++;
						if (i < current.length()){
							currChar = current.charAt(i);
							if (Character.isDigit(currChar)){
								atLeasOneDig = true;
							} else break;
						} else break;
					} while(true);

					if(atLeasOneDig){
						numbers.add(new Pair<Integer, String>(index, number));
					} else {
						i = index+1;
					}
				} else {
					i++;
				}
			}
		}		

		return numbers;
	}

	//0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20
	//f i l e   " m y "   o  p  e  n     "  r  e  s  t  "
	public List<Pair<Integer, String>> extractStringLiterals(String text) {
		List<Pair<Integer, String>> strings = new LinkedList<Pair<Integer, String>>();
		String current = text;

		int index = 0;
		while(true){
			int openingQuotationIndex = current.indexOf("\"");       //5 ; 6
			if (openingQuotationIndex != -1){
				current = current.substring(openingQuotationIndex + 1);  //my" open "rest" ; "rest" 
				int closingQuotationIndex = current.indexOf("\"");   //2 ; 4
				if(closingQuotationIndex != -1){
					index += openingQuotationIndex;                  //5; 15
					strings.add(new Pair<Integer, String>(index, "\""+current.substring(0, closingQuotationIndex + 1)));  //5; 15   
					int size2 = closingQuotationIndex + 1; //4; 6
					current = current.substring(size2);  // open "rest"
					index += size2+1; //9
				} else break;
			} else break;
		}

		return strings;
	}

}
