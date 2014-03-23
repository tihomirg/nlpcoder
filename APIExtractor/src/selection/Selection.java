package selection;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import selection.parser.one.Word;
import selection.parser.two.ConstituentTwo;
import selection.scorers.DeclFreqModelScorer;
import selection.scorers.FrequencyScorer;
import selection.scorers.GroupScorer;
import selection.scorers.MultiScorer;
import selection.scorers.Scorer;

import definitions.ClassInfo;
import definitions.Declaration;
import edu.mit.jwi.item.POS;

public class Selection {
	private Table table;
	private int topListSize;
	private Map<POS, Map<String, Integer>> frequency;
	private List<FrequencyScorer> fScorers;
	private DeclFreqMap fMap;
	private Map<Integer, Integer> declFreq;
	private int totalDeclNum;

	private static final Map<Integer, Double> scores = new HashMap<Integer, Double>(){ {put(0, 1.0); put(1, 0.25);}};

	public Selection(int size, Map<Integer, Integer> freq){
		this.table = new Table(Config.getNumOfTags());
		this.topListSize = size;
		this.frequency = new HashMap<POS, Map<String,Integer>>();
		this.fScorers = new LinkedList<FrequencyScorer>();
		this.declFreq = freq;
		this.fMap = new DeclFreqMap();
	}

	public void add(ClassInfo[] classes, int maxWords, double nullProbs){
		for (ClassInfo clazz : classes) {
			add(clazz, maxWords, nullProbs);
		}
		//makeFrequencies();
		createDeclFreq();
	}	

	public void add(ClassInfo clazz, int maxWords, double nullProbs){
		addAll(clazz.getUniqueDeclarations(), nullProbs);
	}

	public void addAll(Declaration[] decls, double nullProbs){
		for (Declaration declaration : decls) {
			add(declaration, nullProbs);
		}
	}
	
	private void createDeclFreq() {
		this.fMap.create(declFreq, totalDeclNum, Config.getSmoothFactor());
	}

	public void add(Declaration decl, double nullProbs){
		Word[] words = decl.getWords();
		RichDeclaration rd = new RichDeclaration(decl, new MultiScorer(new Scorer[]{new GroupScorer(words, scores), new DeclFreqModelScorer(fMap, decl.getId())}));
		
//		FrequencyScorer frequencyScorer = new FrequencyScorer(words);
//		fScorers.add(frequencyScorer);
//		RichDeclaration rd = new RichDeclaration(decl, new MultiScorer(new Scorer[]{frequencyScorer}));
		
		for(Word word: words)
			table.addRichDeclaration(word, rd);

		updateFrequency(words);
	}

	private void updateFrequency(Word[] words) {
		for (Word word : words) {
			POS pos = word.getPos();
			if (!frequency.containsKey(pos)) {
				frequency.put(pos, new HashMap<String, Integer>());
			}

			Map<String, Integer> map = frequency.get(pos);

			String lemma = word.getLemma();
			if (!map.containsKey(lemma)) {
				map.put(lemma, 0);
			}

			map.put(lemma, map.get(lemma) + 1);
		}

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
	
	private void makeFrequencies(){
		for (FrequencyScorer fScorer : fScorers) {
			fScorer.make(frequency);
		}
	}
}
