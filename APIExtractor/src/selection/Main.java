package selection;

import selection.parser.one.ParserOne;
import selection.parser.one.SentenceZero;
import selection.parser.three.ParserThree;
import selection.parser.two.ParserTwo;

public class Main {

	public static void main(String[] args) {
		WordProcessor wordProcessor = new WordProcessor();
		
		int intervalDiameter = 2;
		ParserPipeline parser = new ParserPipeline(new IParser[]{
				new ParserOne(wordProcessor), 
				new ParserTwo(wordProcessor.getWordNet(), 3, intervalDiameter),
				new ParserThree(intervalDiameter)
		});

		SentenceZero zero = new SentenceZero("Make Buffer-printer read.");
		
		ISentence one = parser.parse(zero);
		
		System.out.print(one);
		
		
		//Selection selection = new Selection(new WordExtractorFromName(wordProcessor));
	}
}
