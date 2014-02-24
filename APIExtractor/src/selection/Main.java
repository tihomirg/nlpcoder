package selection;

import java.util.Arrays;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		WordProcessor wordProcessor = new WordProcessor();
		SentenceParser parser = new SentenceParser(new SentenceSliceStrategy(wordProcessor));
		
		List<Words> slice = parser.slice("Buffer Input-Stream.");
		
		System.out.println(Arrays.toString(slice.toArray()));
		
		
		
		//Selection selection = new Selection(new WordExtractorFromName(wordProcessor));
	}
}
