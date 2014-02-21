package selection;

import java.util.List;

import definitions.Declaration;

public class Selection {
	private WordDeclarationTable table;
	private IWordExtractor extractor;
	
	public Selection(){	
		this(new WordExtractorFromName(new WordProcessor(new WordCorrector())));
	}
	
	public Selection(IWordExtractor extractor){
		this.table = new WordDeclarationTable(extractor.getGroupNum());
		this.extractor = extractor;
	}
	
	public void add(Declaration decl){
		List<WordIndex> wordIndexes = extractor.get(decl);
		RichDeclaration rd = new RichDeclaration(decl);
		for(WordIndex wordIndex:  wordIndexes){
			table.addRichDeclaration(wordIndex, rd);
		}
	}
	
	public void tryInc(Word word){
		table.tryInc(word);
	}
}
