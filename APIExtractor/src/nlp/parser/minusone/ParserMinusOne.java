package nlp.parser.minusone;

import nlp.parser.IParser;
import nlp.parser.ISentence;
import nlp.parser.one.SentenceZero;

public class ParserMinusOne extends IParser {

	private static final String stringToken = " string ";

	public ISentence parse(SentenceMinusOne curr) { 
		String sentence = process(curr.getRep());
		
		System.out.println(sentence);
		
		return new SentenceZero(sentence);
	}

	private String process(String curr) {
		String sentence = extractStrings(curr);	
		String[] splits = sentence.split("[.,()<>]");
		
		sentence = "";
		
		for (String split : splits) {
			sentence += (split+" ");
		}
		
		return sentence;
	}

	private String extractStrings(String curr) {
		
		String sentence ="";
		String bsentence = "";
		
		boolean started = false;
		
		for (int i = 0; i < curr.length(); i++) {
			char ch = curr.charAt(i);
			
			if (ch != '"'){
				if (!started) {
					sentence += ch;	
				} else {
				   bsentence += ch;
				}
			} else {
				if (started){
					started = false;
					sentence+=stringToken;
				} else {
					started = true;
					bsentence = "";
				}
			}
			
		}
		
		if (started){
			sentence += bsentence;
		}
		
		return sentence;
	}
	
}
