import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import config.Config;
import deserializers.KryoDeserializer;
import serializers.KryoSerializer;
import nlp.parser.RelatedWordsMap;
import nlp.parser.TaggedWord;
import nlp.parser.TaggedWordMeaning;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
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
		this.wordNet = new WordNet(stat);
	}
	
	public static void main1(String[] args) {
		RelatedWordsMapGenerator rwGen = new RelatedWordsMapGenerator();
		IIndexWord word = rwGen.getWord("join", POS.VERB);
		TaggedWord taggedWord = rwGen.getTaggedWord(word);
		PriorityQueue<WordMeaning> sortedMeanings = rwGen.getSortedMeanings(word, taggedWord);
		
		List<TaggedWordMeaning> taggedWordMeanings = rwGen.getTaggedWordMeanings(sortedMeanings, taggedWord);
		printTaggedMeanings(taggedWord, taggedWordMeanings);
		
	}
	
	public static void main2(String[] args) {
		RelatedWordsMapGenerator rwGen = new RelatedWordsMapGenerator();
		RelatedWordsMap relatedWordsMap = rwGen.createRelatedWordsMap();
		KryoSerializer serializer = new KryoSerializer();
		serializer.writeObject(Config.getRelatedWordsMapLocation(), relatedWordsMap);
	}
	
	public static void main(String[] args) {
		KryoDeserializer deserializer = new KryoDeserializer();
		
		RelatedWordsMap map = (RelatedWordsMap) deserializer.readObject(Config.getRelatedWordsMapLocation(), RelatedWordsMap.class);
		
		long time = System.currentTimeMillis();
		
		TaggedWord leftHSWord = new TaggedWord("copy", "N");
		List<TaggedWordMeaning> list = map.get(leftHSWord);
		
		System.out.println(leftHSWord);
		System.out.println("Time: "+(System.currentTimeMillis() - time) +" ms");
		for (TaggedWordMeaning taggedWordMeaning : list) {
			System.out.println(taggedWordMeaning);
		}
	}	

	private static void printTaggedMeanings(TaggedWord taggedWord, List<TaggedWordMeaning> taggedWordMeanings) {
		System.out.println(taggedWord);
		for (TaggedWordMeaning taggedWordMeaning : taggedWordMeanings) {
			System.out.println(taggedWordMeaning);
		}
	}

	private static void printSorted(PriorityQueue<WordMeaning> sortedMeanings) {
		while (!sortedMeanings.isEmpty()) {
			WordMeaning meaning = sortedMeanings.remove();
			System.out.println(meaning);
		}
	}
	
	private List<TaggedWordMeaning> getTaggedWordMeanings(PriorityQueue<WordMeaning> sortedMeanings, TaggedWord taggedWord) {
		List<TaggedWordMeaning> taggedWordMeanings = new LinkedList<TaggedWordMeaning>();
		Set<TaggedWord> visited = new HashSet<TaggedWord>();
		visited.add(taggedWord);
		while (!sortedMeanings.isEmpty()) {
			WordMeaning meaning = sortedMeanings.remove();
			TaggedWordMeaning taggedMeaning = toTaggedMeaning(meaning, visited);
			
			if (taggedMeaning != null) {
				taggedWordMeanings.add(taggedMeaning);
				visited.addAll(taggedMeaning.getWords());
			}
		}
		
		return taggedWordMeanings;
	}

	private TaggedWordMeaning toTaggedMeaning(WordMeaning meaning, Set<TaggedWord> visited) {
		List<TaggedWord> taggedWords = getTaggedWords(meaning);
		List<TaggedWord> onlyAPIWords = leaveOnlyAPIWords(taggedWords);
		
		onlyAPIWords.removeAll(visited);
		
		if (!onlyAPIWords.isEmpty()) {
			return new TaggedWordMeaning(meaning.getGloss(), meaning.getScore(), onlyAPIWords);
		} else return null;
	}

	private RelatedWordsMap createRelatedWordsMap() {
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
			TaggedWord taggedWord = getTaggedWord(word);
			PriorityQueue<WordMeaning> sortedMeanings = getSortedMeanings(word, taggedWord);
			
			rwm.put(taggedWord, getTaggedWordMeanings(sortedMeanings, taggedWord));
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
		
		List<WordMeaning> bestMeanings = best(wordNet.getMeanings(word), new HashSet<TaggedWord>());//originalWordSingleton);
		
		System.out.println(bestMeanings);
		
		for (WordMeaning meaning : bestMeanings) {
			List<TaggedWord> synonyms = addSynonyms(relatedWords, originalWordSingleton, meaning);
			//addHyponymsAndHypernyms(relatedWords, originalWordSingleton, meaning, synonyms);
		}
		
		return relatedWords;
	}
	
	public PriorityQueue<WordMeaning> getSortedMeanings(IIndexWord word, TaggedWord taggedWord) {
		PriorityQueue<WordMeaning> pq = new PriorityQueue<WordMeaning>(CAPACITY, WORD_MEANING_COMPARATOR);
		
		Set<TaggedWord> originalWordSingleton = new HashSet<TaggedWord>();
		originalWordSingleton.add(taggedWord);
		
		List<WordMeaning> bestMeanings = wordNet.getMeanings(word);
		pq.addAll(filter(bestMeanings, originalWordSingleton));
		
		for (WordMeaning meaning : bestMeanings) {
			List<WordMeaning> hyponymsAndHypernyms = filter(wordNet.getMeanings(wordNet.getHyponymsAndHypernyms(meaning)), originalWordSingleton);
			fixScore(hyponymsAndHypernyms, meaning.getScore());
			pq.addAll(hyponymsAndHypernyms);
		}
		
		return pq;
	}	

	private void fixScore(List<WordMeaning> hyponymsAndHypernyms, double score) {
		for (WordMeaning meaning: hyponymsAndHypernyms) {
			meaning.setScore(score * meaning.getScore()); 
		}
	}

	private List<TaggedWord> addSynonyms(Set<TaggedWord> relatedWords, Set<TaggedWord> originalWordSingleton, WordMeaning meaning) {
		List<TaggedWord> synonyms = getTaggedWords(meaning);
		relatedWords.addAll(leaveOnlyAPIWords(filterWords(synonyms, originalWordSingleton)));
		return synonyms;
	}

	private void addHyponymsAndHypernyms(Set<TaggedWord> relatedWords, Set<TaggedWord> originalWordSingleton, WordMeaning meaning, List<TaggedWord> synonyms) {
		List<ISynset> hyponymsAndHypernyms = wordNet.getHyponymsAndHypernyms(meaning);
		List<WordMeaning> hyponymsAndHypernymsMeanings = wordNet.getMeanings(hyponymsAndHypernyms);
		Set<TaggedWord> secondLevelVisited = new HashSet<TaggedWord>();
		secondLevelVisited.addAll(originalWordSingleton);
		secondLevelVisited.addAll(synonyms);
		
		List<WordMeaning> bestHyponymsAndHypernymsMeanings = best(hyponymsAndHypernymsMeanings, secondLevelVisited);
		
		relatedWords.addAll(leaveOnlyAPIWords(filterWords(getWords(bestHyponymsAndHypernymsMeanings), secondLevelVisited)));
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
	
	private List<WordMeaning> filter(List<WordMeaning> meanings) {
		List<WordMeaning> filtered = new LinkedList<WordMeaning>();
		
		for (WordMeaning meaning : meanings) {
			List<TaggedWord> taggedWords = wordNet.getTaggedWords(meaning);
			taggedWords = leaveOnlyAPIWords(taggedWords);
			
			if (!taggedWords.isEmpty()){
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
