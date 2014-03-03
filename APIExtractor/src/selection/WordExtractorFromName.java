package selection;

import java.util.ArrayList;
import java.util.List;

import selection.trees.Constituent;
import selection.trees.Word;

import definitions.Declaration;

public class WordExtractorFromName implements IWordExtractor {

	private ParserPipeline strategy;
	
	public WordExtractorFromName(ParserPipeline strategy) {
		this.strategy = strategy;
	}

	@Override
	public Indexes get(Declaration decl) {
		
		return null;//strategy.slice(decl.getName());
	}
}
