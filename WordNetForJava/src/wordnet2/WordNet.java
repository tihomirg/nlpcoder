package wordnet2;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import util.Pair;
import nlp.parser.TaggedWord;
import nlp.parser.Token;
import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.Pointer;
import edu.mit.jwi.morph.WordnetStemmer;

public class WordNet {

	private IDictionary dict;
	private WordnetStemmer stemmer;

	private APIWordCountStatistics apiWordCount;
	private SenseScorer scorer;
	
	public WordNet(){
		// construct the dictionary object and open it
		try {
			dict = new Dictionary(new URL("file", null , "C:/Program Files/WordNet/3.1/dict"));
			dict.open();
			stemmer = new WordnetStemmer(dict);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	

	public TaggedWord getTransformStanfordTaggedWord(String word, String tag){
		String shortStanfordTag = shortStanfordTag(tag);
		
		List<String> findStems = stemmer.findStems(word, toWordNetPos(shortStanfordTag));
		
		if (findStems != null && !findStems.isEmpty()) {
			String stem = findStems.get(0);
			return new TaggedWord(stem, shortStanfordTag);
		}
		else {
			return new TaggedWord(word, shortStanfordTag);
		}
	}	
	
	public List<ISynset> getSynsets(String lemma, POS pos) {
		List<ISynset> synsets = new LinkedList<ISynset>();
		IIndexWord idxWord = dict.getIndexWord(lemma, pos);
		if (idxWord != null){
			//IWordID wordID = idxWord.getWordIDs().get(0);
			for(IWordID wordID : idxWord.getWordIDs()){
				synsets.add(dict.getSynset(wordID.getSynsetID()));
			}
		}

		return synsets;
	}
	
	public List<WordMeaning> getMeanings(List<ISynset> synsets){
		List<WordMeaning> meanings = new LinkedList<WordMeaning>();
		for (ISynset synset: synsets) {
			meanings.add(getMeaning(synset));
		}
		return meanings;
	}

	private WordMeaning getMeaning(ISynset synset) {
		String gloss = synset.getGloss();
		
		return new WordMeaning(synset, gloss, scorer.getScore(gloss));
	}

	public String shortStanfordTag(String pos) {
		return Character.isLetter(pos.charAt(0)) ? Character.toString(pos.charAt(0)).toUpperCase() : pos;
	}

	public static POS toWordNetPos(String pos){
		if(pos.equals("N")){
			return POS.NOUN;
		} else if(pos.equals("V")){
			return POS.VERB;
		} else if(pos.equals("J")) {
			return POS.ADJECTIVE;
		} else if (pos.equals("R")){
			return POS.ADVERB;
		} else {
			return POS.NOUN;
		}
	}	
	
}
