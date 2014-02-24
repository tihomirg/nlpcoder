package selection;

import java.util.ArrayList;
import java.util.List;

public class SentenceParser {

	private ISentenceSliceStrategy strategy;

	public SentenceParser(ISentenceSliceStrategy strategy) {
		this.strategy = strategy;
	}

	public List<Words> slice(String sentence) {
		return strategy.slice(sentence);
	}
	
	//TODO: Higher phase would include extraction and recognition of special symbols.
	public List<Words> parse(String sentence){
		return new ArrayList<Words>();
	}	
}
