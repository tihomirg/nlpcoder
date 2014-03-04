package selection;

import java.util.List;

import selection.loaders.ClassLoader;
import selection.parser.one.Word;

import definitions.Declaration;

public class Selection {
	private Table table;
	
	public Selection(){
		this.table = new Table(Config.getNumOfTags());
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
	
	public void tryInc(Word word){
		table.tryInc(word);
	}
	
	public RichDeclarations select(Word word){
		return table.select(word);
	}
}
