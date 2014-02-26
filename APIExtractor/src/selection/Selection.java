package selection;

import java.util.List;

import selection.parser.one.ParserOne;
import selection.trees.Constituent;
import selection.trees.Word;

import definitions.Declaration;

public class Selection {
	private WordDeclarationTable table;
	private IWordExtractor extractor;
	
	public Selection(){	
		this(new WordExtractorFromName(new ParserOne(new WordProcessor())));
	}
	
	public Selection(IWordExtractor extractor){
		this.table = new WordDeclarationTable(extractor.getGroupNum());
		this.extractor = extractor;
	}
	
	public void add(Declaration decl){
		List<List<Word>> wordsList = extractor.get(decl);
		RichDeclaration rd = new RichDeclaration(decl, wordsList);
		for(List<Word> words:  wordsList)
		  for(Word word: words)
			table.addRichDeclaration(word, rd);
	}
	
	public void tryInc(Word word){
		table.tryInc(word);
	}
}
