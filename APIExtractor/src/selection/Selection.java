package selection;

import selection.parser.one.ParserOne;
import selection.parser.one.Word;

import definitions.Declaration;

public class Selection {
	private WordDeclarationTable table;
	private IWordExtractor extractor;
	
	public Selection(){	
		this(new WordExtractorFromName(new ParserPipeline(new IParser[]{new ParserOne(new WordProcessor())})));
	}
	
	public Selection(IWordExtractor extractor){
		this.table = new WordDeclarationTable(Config.getNumOfTags());
		this.extractor = extractor;
	}
	
	public void add(Declaration decl){
		Indexes indexes = extractor.get(decl);
		RichDeclaration rd = new RichDeclaration(decl, indexes);
		  for(Word word: indexes.getWords())
			table.addRichDeclaration(word, rd);
	}
	
	public void tryInc(Word word){
		table.tryInc(word);
	}
}
