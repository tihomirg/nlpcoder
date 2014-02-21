package selection;

public class Main {

	public static void main(String[] args) {
		WordProcessor wordProcessor = new WordProcessor(new WordCorrector());
		SentenceParser parser = new SentenceParser(new SentenceSliceStrategy(wordProcessor));
		Selection selection = new Selection(new WordExtractorFromName(wordProcessor));
		
		
		
	}
}
