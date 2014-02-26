package selection;

import selection.parser.one.ParserOne;
import selection.parser.one.SentenceZero;

public class Main {

	public static void main(String[] args) {
		WordProcessor wordProcessor = new WordProcessor();
		ParserPipeline parser = new ParserPipeline(new IParser[]{new ParserOne(wordProcessor)});
		
		SentenceZero zero = new SentenceZero("Buffer Input-Stream.");
		
		ISentence one = parser.parse(zero);
		
		System.out.print(one);
		
		
		//Selection selection = new Selection(new WordExtractorFromName(wordProcessor));
	}
}
