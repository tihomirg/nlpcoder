package selection;

import selection.parser.one.ParserOne;
import selection.parser.one.SentenceZero;
import selection.parser.two.ParserTwo;

public class Main {

	public static void main(String[] args) {
		WordProcessor wordProcessor = new WordProcessor();
		ParserPipeline parser = new ParserPipeline(new IParser[]{new ParserOne(wordProcessor), new ParserTwo(wordProcessor.getWordNet(), 1, 1)});
		
		SentenceZero zero = new SentenceZero("Buffer Input-Stream.");
		
		ISentence one = parser.parse(zero);
		
		System.out.print(one);
		
		
		//Selection selection = new Selection(new WordExtractorFromName(wordProcessor));
	}
}
