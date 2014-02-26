package selection;

import java.util.ArrayList;
import java.util.List;

import selection.trees.Constituent;
import selection.trees.Word;

import definitions.Declaration;

public class WordExtractorFromName implements IWordExtractor {

	private IParser strategy;
	
	public WordExtractorFromName(IParser strategy) {
		this.strategy = strategy;
	}

	@Override
	public List<List<Word>> get(Declaration decl) {
		return null;//strategy.slice(decl.getName());
	}

	@Override
	public int getGroupNum() {
		return 1;
	}
}
