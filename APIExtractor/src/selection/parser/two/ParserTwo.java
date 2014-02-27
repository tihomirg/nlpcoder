package selection.parser.two;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.Synset;

import selection.IParser;
import selection.ISentence;
import selection.WordProcessor;
import selection.parser.one.ConstituentOne;
import selection.parser.one.Word;
import selection.parser.one.WordNet;
import selection.parser.one.trees.SentenceOne;

public class ParserTwo extends IParser {

	private WordNet wordnet;	
	private int maxLevelDepth;
	private int intervalDiameter;
	
	public ParserTwo(WordNet wordnet, int maxLevelDepth, int intervalDiameter) {
		assert maxLevelDepth > 0;
		this.wordnet = wordnet;
		this.maxLevelDepth = maxLevelDepth;
		this.intervalDiameter = intervalDiameter;
	}

	public ISentence parse(SentenceOne curr) {
		Word[] words = curr.getWords();
		ConstituentOne[] constituents = curr.getConstituents();
		ConstituentTwo[] coustituents2 = getConstituents(constituents, words);
		return new SentenceTwo(coustituents2, words);
	}

	private ConstituentTwo[] getConstituents(ConstituentOne[] constituents, Word[] words) {
		ConstituentTwo[] constituents2 = new ConstituentTwo[constituents.length];
		
		for (int i = 0; i < constituents.length; i++) {
			constituents2[i] = getConstituent(constituents[i], words);
		}
		
		return constituents2;
	}

	private ConstituentTwo getConstituent(ConstituentOne cons, Word[] words) {
		int index = cons.getIndex();
		int firstImportantIndex = cons.smallestIndex();
		int lastImportantIndex = cons.largestIndex();
		int leftIndex = getSmallestIndex(firstImportantIndex);
		int rightIndex = getLargestIndex(lastImportantIndex, words.length);
		
		Word[] words2 = Arrays.copyOfRange(words, leftIndex, rightIndex+1);
		
		Wordset[] wordsets = new Wordset[words2.length];
		for (int i = 0; i < words2.length; i++) {
			wordsets[i] = getWordset(words2[i], index);
		}
		
		return new ConstituentTwo(wordsets, index, firstImportantIndex, lastImportantIndex);
	}

	private LinkedList<Word> prepareWords(LinkedList<Word> words, int constituentIndex) {
		for (Word word : words) {
			word.setConstIndex(constituentIndex);
		}
		return words;
	}

	private Wordset getWordset(Word word, int constituentIndex) {
		Level[] levels = new Level[maxLevelDepth];
		
		List<ISynset> synsets = getSynonyms(word);
		levels[0] = new Level(getWords(synsets, constituentIndex), 0);
		
		for (int i = 1; i < maxLevelDepth; i++) {
			synsets = getNeighbours(synsets);
		}
		
		return new Wordset(levels);
	}

	private List<ISynset> getNeighbours(List<ISynset> synsets) {
		// TODO Auto-generated method stub
		return wordnet.getNeighbors(synsets);
	}

	private List<Word> getWords(List<ISynset> synsets, int constituentIndex) {
        LinkedList<Word> words = new LinkedList<Word>();
        for (ISynset synset : synsets) {
			words.addAll(getWords(synset));
		}
        
		return prepareWords(words, constituentIndex);
	}

	private List<Word> getWords(ISynset synset) {
		return wordnet.getWords(synset);
	}

	private List<ISynset> getSynonyms(Word word) {
		return wordnet.getSynonyms(word);
	}

	private int getLargestIndex(int largestIndex, int sentenceLength) {
		return Math.min(largestIndex + intervalDiameter, sentenceLength-1);
	}

	private int getSmallestIndex(int smallestIndex) {
		return Math.max(smallestIndex - intervalDiameter, 0);
	}
	
}
