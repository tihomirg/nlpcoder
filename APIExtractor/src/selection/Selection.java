package selection;

import java.util.List;

import selection.loaders.ClassLoader;
import selection.parser.one.Word;
import selection.parser.two.ConstituentTwo;
import selection.parser.two.SentenceTwo;

import definitions.Declaration;

public class Selection {
	private Table table;
	private int topListSize;
	
	public Selection(int size){
		this.table = new Table(Config.getNumOfTags());
		this.topListSize = size;
	}

	public void add(ClassLoader[] classes){
		for (ClassLoader clazz : classes) {
			add(clazz);
		}
	}	
	
	public void add(ClassLoader clazz){
		addAll(clazz.getDeclarations());
	}
	
	public void addAll(Declaration[] decls){
		for (Declaration declaration : decls) {
			add(declaration);
		}
	}
	
	public void add(Declaration decl){
		Indexes indexes = new Indexes(decl.getWords());
		RichDeclaration rd = new RichDeclaration(decl, indexes);
		  for(Word word: indexes.getWords())
			table.addRichDeclaration(word, rd);
	}
	
	public void tryInc(Word word, TopList top){
		table.tryInc(word, top);
	}
	
	public RichDeclarations select(Word word){
		return table.select(word);
	}

	public TopList tryInc(ConstituentTwo cons) {
		TopList top = new TopList(this.topListSize);
		for (Word word: cons.getWords()) {
			tryInc(word, top);
		}
		return top;
	}
	
	public void clear() {
		table.clear();
	}
}
