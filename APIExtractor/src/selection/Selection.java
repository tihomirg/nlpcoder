package selection;

import java.util.HashMap;
import java.util.Map;

import selection.parser.one.Word;
import selection.parser.two.ConstituentTwo;
import selection.scorers.AddScorer;
import selection.scorers.GroupScorer;
import selection.scorers.HitScorer;
import selection.scorers.Scorer;

import definitions.ClassInfo;
import definitions.Declaration;

public class Selection {
	private Table table;
	private int topListSize;
	
	private static final Map<Integer, Double> scores = new HashMap<Integer, Double>(){ {put(0, 1.0); put(1, 0.25);}};
	
	public Selection(int size){
		this.table = new Table(Config.getNumOfTags());
		this.topListSize = size;
	}

	public void add(ClassInfo[] classes, int maxWords, double nullProbs){
		for (ClassInfo clazz : classes) {
			add(clazz, maxWords, nullProbs);
		}
	}	
	
	public void add(ClassInfo clazz, int maxWords, double nullProbs){
		addAll(clazz.getDeclarations(), nullProbs);
	}
	
	public void addAll(Declaration[] decls, double nullProbs){
		for (Declaration declaration : decls) {
			add(declaration, nullProbs);
		}
	}
	
	public void add(Declaration decl, double nullProbs){
		Word[] words = decl.getWords();		
		RichDeclaration rd = new RichDeclaration(decl, new AddScorer(new Scorer[]{new GroupScorer(words, scores)}));
		
		for(Word word: words)
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
