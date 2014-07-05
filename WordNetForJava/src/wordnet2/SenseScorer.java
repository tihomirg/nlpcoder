package wordnet2;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import nlp.parser.TaggedWord;
import nlp.parser.one.WordTagger;
import util.Pair;
import wordnet2.WordNet;

public class SenseScorer {

	private static double fTreshold;
	private APIWordCountStatistics wordCount;
	private WordTagger tagger;
	private WordNet wordNet;
	
	public SenseScorer() {
		tagger = new WordTagger();
	}
	
	public double getScore(String gloss) {
		String firstGloss = getImportantPrefixOfGloss(gloss);
		String[] taggedGloss = tagger.tagSentence(firstGloss);

		List<TaggedWord> processedTaggedGloss = processTaggedGloss(taggedGloss);
		
		return apiWordImportanceScore(processedTaggedGloss);
	}
	
	private List<TaggedWord> processTaggedGloss(String[] gloss) {
		List<TaggedWord> words = new LinkedList<TaggedWord>();
		for (String string : gloss) {
			String[] pair = string.split("_");
			String word = pair[0].toLowerCase();
			String tag = pair[1];

			if (Character.isLetter(word.charAt(0))){

				words.add(wordNet.getTransformStanfordTaggedWord(word, tag));

			}
		}
		return words;
	}	

	private double apiWordCountScore(List<TaggedWord> processedTaggedGloss) {
		double sum = 0;
		for (TaggedWord word: processedTaggedGloss) {
			if(wordCount.isAPIWord(word)){
				sum++;
			}
		}
		return sum / processedTaggedGloss.size();
	}
	
	private String getImportantPrefixOfGloss(String gloss) {
		int index = gloss.indexOf("\"");

		if (index != -1) return gloss.substring(0, index);
		else return gloss;
	}

	private double apiWordImportanceScore(List<TaggedWord> processedTaggedGloss) {
		double sum = 0;
		for (TaggedWord pair : processedTaggedGloss) {
			sum += getFrequencyBelowTreshold(pair);
		}
		return sum / processedTaggedGloss.size();
	}
	
	private double getFrequencyBelowTreshold(TaggedWord word) {
		double f = wordCount.getCount(word);
		
		if (f < fTreshold) return f;
		else return 0;
	}

	private double glossInversFrequencyScore(List<TaggedWord> processedTaggedGloss) {
		double sum = 0;
		for (TaggedWord word : processedTaggedGloss) {
			double f = getFrequencyBelowTreshold(word);
			if (f > 0){
				sum += 1 / f;
			}
		}
		return sum / processedTaggedGloss.size();
	}	
	
}
