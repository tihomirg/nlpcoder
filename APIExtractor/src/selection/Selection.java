package selection;

import oldcorpus.LoadOldCorpus;
import selection.loaders.ClassLoader;
import selection.parser.one.Word;
import selection.parser.two.ConstituentTwo;

import definitions.Declaration;

public class Selection {
	private Table table;
	private int topListSize;
	private LoadOldCorpus loc;
	
	public Selection(int size, LoadOldCorpus loc){
		this.table = new Table(Config.getNumOfTags());
		this.topListSize = size;
		this.loc = loc;
	}

	public void add(ClassLoader[] classes, int maxWords, double nullProbs){
		for (ClassLoader clazz : classes) {
			add(clazz, maxWords, nullProbs);
		}
	}	
	
	public void add(ClassLoader clazz, int maxWords, double nullProbs){
		addAll(clazz.getDeclarations(), nullProbs);
	}
	
	public void addAll(Declaration[] decls, double nullProbs){
		for (Declaration declaration : decls) {
			add(declaration, nullProbs);
		}
	}
	
	public void add(Declaration decl, double nullProbs){
		Indexes indexes = new Indexes(decl.getWords(), nullProbs);
		RichDeclaration rd = new RichDeclaration(decl, indexes);

		//TODO: Should be replaced with a real prob. assignment.
		if (loc != null){
			loc.setProb(rd);
		}
		
		  for(Word word: indexes.getWords())
			table.addRichDeclaration(word, rd);
	}
	
	public void tryInc(Word word, TopList top, int consLength){
		table.tryInc(word, top, consLength);
	}
	
	public RichDeclarations select(Word word){
		return table.select(word);
	}

	public TopList tryInc(ConstituentTwo cons) {
		TopList top = new TopList(cons.getFirstImportantWord(), this.topListSize);
		int consLength = cons.getLength();
		for (Word word: cons.getWords()) {
			//System.out.println(word);
			tryInc(word, top, consLength);
		}
		return top;
	}
	
	public void clear() {
		table.clear();
	}
}
