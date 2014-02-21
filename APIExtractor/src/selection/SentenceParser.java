package selection;

import java.util.List;

public class SentenceParser {

	private ISentenceSliceStrategy strategy;

	public SentenceParser(ISentenceSliceStrategy strategy) {
		this.strategy = strategy;
	}

	public List<Words> slice(String sentence) {
		return strategy.slice(sentence);
	}
}
