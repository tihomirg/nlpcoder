package selection;

import java.util.ArrayList;
import java.util.List;

import definitions.Declaration;

public class WordExtractorFromName implements IWordExtractor {

	private WordProcessor wp;
	
	public WordExtractorFromName(WordProcessor wp) {
		this.wp = wp;
	}

	@Override
	public List<WordIndex> get(Declaration decl) {
		List<WordIndex> words = new ArrayList<WordIndex>();
		List<String> sWords = wp.sliceComplexWord(decl.getName());
		for (String sWord: sWords){
			words.add(new WordIndex(sWord));
		}
		return words;
	}

	@Override
	public int getGroupNum() {
		return 1;
	}
}
