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
import edu.mit.jwi.item.POS;
import wordnet2.APIWordStatistics;
import wordnet2.WordMeaning;
import wordnet2.WordNet;


public class RelatedWordsSerializer {

	private static final int CAPACITY = 1000;
	private static final WordMeaningComparator WORD_MEANING_COMPARATOR = new WordMeaningComparator();
	private static final int BEST_NUM = 2;

	public static void main(String[] args) {
		APIWordStatistics stat = new APIWordStatistics();
		
		RelatedWordsMap rwm = new RelatedWordsMap();
		
		WordNet wordNet = new WordNet(stat);
		
		traverse(rwm, wordNet, POS.NOUN);
		traverse(rwm, wordNet, POS.VERB);
		traverse(rwm, wordNet, POS.ADJECTIVE);
		traverse(rwm, wordNet, POS.ADVERB);
	}

	private static void traverse(RelatedWordsMap rwm, WordNet wordNet, POS pos) {
		Iterator<IIndexWord> words = wordNet.getWords(pos);
		
		while(words.hasNext()){
			IIndexWord word = words.next();
			TaggedWord leftHSWord = wordNet.getTaggedWord(word);
			
			Set<TaggedWord> visited = new HashSet<TaggedWord>();
			visited.add(leftHSWord);
			
			List<WordMeaning> bestMeanings = best(wordNet.getMeanings(wordNet.getSynsets(word)), visited);
			
			Set<TaggedWord> rightHSWords = new HashSet<TaggedWord>();
			
			for (WordMeaning bestMeaning : bestMeanings) {
				List<TaggedWord> bestWords = getWords(bestMeaning, wordNet);
				
				List<ISynset> hyponymsAndHypernyms = wordNet.getHyponymsAndHypernyms(bestMeaning);
				List<WordMeaning> hyponymsAndHypernymsMeanings = wordNet.getMeanings(hyponymsAndHypernyms);
				Set<TaggedWord> secondLevelVisited = new HashSet<TaggedWord>();
				secondLevelVisited.addAll(visited);
				secondLevelVisited.addAll(bestWords);
				
				List<WordMeaning> bestHyponymsAndHypernymsMeanings = best(hyponymsAndHypernymsMeanings, secondLevelVisited);
				
				rightHSWords.addAll(getWords(bestHyponymsAndHypernymsMeanings, wordNet));
			}
			
			rwm.put(leftHSWord, rightHSWords);
		}
	}

	private static List<TaggedWord> getWords(List<WordMeaning> wordMeanings, WordNet wordNet) {
		List<TaggedWord> taggedWords = new LinkedList<TaggedWord>();
		for (WordMeaning wordMeaning : wordMeanings) {
			taggedWords.addAll(getWords(wordMeaning, wordNet));
		}
		return taggedWords;
	}

	private static List<TaggedWord> getWords(WordMeaning meaning, WordNet wordNet) {
		return wordNet.getTaggedWords(meaning);
	}

	private static List<WordMeaning> best(List<WordMeaning> meanings, Set<TaggedWord> visited) {
		return best(sort(filter(meanings, visited)));
	}

	private static List<WordMeaning> filter(List<WordMeaning> meanings, Set<TaggedWord> visited) {
		//TODO: Implement
		return meanings;
	}

	private static List<WordMeaning> best(PriorityQueue<WordMeaning> sort) {
		List<WordMeaning> meanings = new LinkedList<WordMeaning>();
		int i = 0;
		while(!sort.isEmpty() || i < BEST_NUM){
			meanings.add(sort.remove());
			i++;
		}
		return meanings;
	}

	private static PriorityQueue<WordMeaning> sort(List<WordMeaning> meanings) {
		PriorityQueue<WordMeaning> pq = new PriorityQueue<WordMeaning>(CAPACITY, WORD_MEANING_COMPARATOR);
		pq.addAll(meanings);
		return pq;
	}
	
}
