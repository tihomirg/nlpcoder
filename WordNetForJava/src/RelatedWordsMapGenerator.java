import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import nlp.parser.TaggedWord;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.POS;
import wordnet2.APIWordStatistics;
import wordnet2.WordMeaning;
import wordnet2.WordNet;


public class RelatedWordsMapGenerator {

	private static final int CAPACITY = 1000;
	private static final WordMeaningComparator WORD_MEANING_COMPARATOR = new WordMeaningComparator();
	private static final int BEST_NUM = 2;
	
	private APIWordStatistics stat;
	private WordNet wordNet;

	public RelatedWordsMapGenerator() {
		this.stat = new APIWordStatistics();
		stat.printWords();
		this.wordNet = new WordNet(stat);
	}
	
	public static void main(String[] args) {
		RelatedWordsMapGenerator rwGen = new RelatedWordsMapGenerator();
		
		IIndexWord word = rwGen.getWord("set", POS.VERB);
		TaggedWord taggedWord = rwGen.getTaggedWord(word);
		Set<TaggedWord> relatedWords = rwGen.getRelatedWords(word, taggedWord);
		
		System.out.println(relatedWords);
	}

	private RelatedWordsMap serialize() {
		RelatedWordsMap rwm = new RelatedWordsMap();
		POS[] poss = new POS[]{POS.NOUN, POS.VERB, POS.ADJECTIVE, POS.ADVERB};
		
		for (POS pos : poss) {
			traverse(pos, rwm);
		}
		return rwm;
	}

	private void traverse(POS pos, RelatedWordsMap rwm) {
		Iterator<IIndexWord> words = wordNet.getWords(pos);
		
		while(words.hasNext()){
			IIndexWord word = words.next();
			TaggedWord taggedWord = wordNet.getTaggedWord(word);
			
			rwm.put(taggedWord, getRelatedWords(word, taggedWord));
		}
	}
	
	public IIndexWord getWord(String lemma, POS pos){
		return wordNet.getWord(lemma, pos);
	}
	
	public TaggedWord getTaggedWord(IIndexWord word){
		return wordNet.getTaggedWord(word);
	}

	public Set<TaggedWord> getRelatedWords(IIndexWord word, TaggedWord taggedWord) {
		Set<TaggedWord> relatedWords = new HashSet<TaggedWord>();
		
		Set<TaggedWord> originalWordSingleton = new HashSet<TaggedWord>();
		originalWordSingleton.add(taggedWord);
		
		List<WordMeaning> bestMeanings = best(wordNet.getMeanings(word), originalWordSingleton);
		
		System.out.println(bestMeanings);
		
		for (WordMeaning meaning : bestMeanings) {
			List<TaggedWord> synonyms = getTaggedWords(meaning);
			
			List<ISynset> hyponymsAndHypernyms = wordNet.getHyponymsAndHypernyms(meaning);
			List<WordMeaning> hyponymsAndHypernymsMeanings = wordNet.getMeanings(hyponymsAndHypernyms);
			Set<TaggedWord> secondLevelVisited = new HashSet<TaggedWord>();
			secondLevelVisited.addAll(originalWordSingleton);
			secondLevelVisited.addAll(synonyms);
			
			List<WordMeaning> bestHyponymsAndHypernymsMeanings = best(hyponymsAndHypernymsMeanings, secondLevelVisited);
			
			relatedWords.addAll(leaveOnlyAPIWords(filterWords(synonyms, originalWordSingleton)));
			relatedWords.addAll(leaveOnlyAPIWords(filterWords(getWords(bestHyponymsAndHypernymsMeanings), secondLevelVisited)));
		}
		
		return relatedWords;
	}

	private List<TaggedWord> filterWords(List<TaggedWord> words, Set<TaggedWord> visited) {
		List<TaggedWord> list = new LinkedList<TaggedWord>();
		list.addAll(words);
		list.removeAll(visited);
		return list;
	}

	private List<TaggedWord> getWords(List<WordMeaning> wordMeanings) {
		List<TaggedWord> taggedWords = new LinkedList<TaggedWord>();
		for (WordMeaning wordMeaning : wordMeanings) {
			taggedWords.addAll(getTaggedWords(wordMeaning));
		}
		return taggedWords;
	}

	private List<TaggedWord> getTaggedWords(WordMeaning meaning) {
		return wordNet.getTaggedWords(meaning);
	}

	private List<WordMeaning> best(List<WordMeaning> meanings, Set<TaggedWord> visited) {
		return best(sort(filter(meanings, visited)));
	}

	private List<WordMeaning> filter(List<WordMeaning> meanings, Set<TaggedWord> visited) {
		List<WordMeaning> filtered = new LinkedList<WordMeaning>();
		
		for (WordMeaning meaning : meanings) {
			List<TaggedWord> taggedWords = wordNet.getTaggedWords(meaning);
			//System.out.println(taggedWords);
			taggedWords.removeAll(visited);
			taggedWords = leaveOnlyAPIWords(taggedWords);
			
			if (!taggedWords.isEmpty()){
				//System.out.println("RelatedWordsMapGenerator.filter()");
				filtered.add(meaning);
			}
		}
		
		return filtered;
	}

	private List<TaggedWord> leaveOnlyAPIWords(List<TaggedWord> taggedWords) {
		List<TaggedWord> filtered = new LinkedList<TaggedWord>();
		for (TaggedWord taggedWord : taggedWords) {
			if(this.stat.isAPIWord(taggedWord)){
				filtered.add(taggedWord);
			}
		}
		return filtered;
	}

	private List<WordMeaning> best(PriorityQueue<WordMeaning> sort) {
		List<WordMeaning> meanings = new LinkedList<WordMeaning>();
		int i = 0;
		while(!sort.isEmpty() && i < BEST_NUM){
			meanings.add(sort.remove());
			i++;
		}
		return meanings;
	}

	private PriorityQueue<WordMeaning> sort(List<WordMeaning> meanings) {
		PriorityQueue<WordMeaning> pq = new PriorityQueue<WordMeaning>(CAPACITY, WORD_MEANING_COMPARATOR);
		pq.addAll(meanings);
		return pq;
	}
}
