package wordnet2;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import nlp.parser.TaggedWord;
import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.Pointer;

public class WordNet {

	private IDictionary dict;
	private MeaningScorer scorer;
	private WordTransformator transformator;
	
	public WordNet(APIWordStatistics stat){
		// construct the dictionary object and open it
		try {
			dict = new Dictionary(new URL("file", null , "C:/Program Files/WordNet/3.1/dict"));
			dict.open();
			
			transformator = new WordTransformator(dict);
			scorer = new MeaningScorer(stat, transformator);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public Iterator<IIndexWord> getWords(POS pos){
		return dict.getIndexWordIterator(pos);
	}
	
	public List<ISynset> getSynsets(IIndexWord idxWord){
		List<ISynset> synsets = new LinkedList<ISynset>();
		if (idxWord != null){
			for(IWordID wordID : idxWord.getWordIDs()){
				synsets.add(dict.getSynset(wordID.getSynsetID()));
			}
		}

		return synsets;		
	}
	
	public List<ISynset> getHyponymsAndHypernyms(WordMeaning meaning){
		List<ISynset> synsets = getHyponyms(meaning);
		synsets.addAll(getHypernyms(meaning));
		return synsets;
	}
	
	public List<ISynset> getHyponyms(WordMeaning meaning){
		return getHyponyms(meaning.getSynset());
	}
	
	public List<ISynset> getHypernyms(WordMeaning meaning){
		return getHypernyms(meaning.getSynset());
	}	
	
	public List<WordMeaning> getMeanings(List<ISynset> synsets){
		List<WordMeaning> meanings = new LinkedList<WordMeaning>();
		for (ISynset synset: synsets) {
			meanings.add(getMeaning(synset));
		}
		return meanings;
	}

	public List<WordMeaning> getMeanings(IIndexWord idxWord){
		return getMeanings(getSynsets(idxWord));
	}
	
	public IIndexWord getWord(String lemma, POS pos) {
		return dict.getIndexWord(lemma, pos);
	}
	
	private WordMeaning getMeaning(ISynset synset) {
		String gloss = synset.getGloss();
		return new WordMeaning(synset, gloss, scorer.getScore(gloss));
	}
	
	public List<ISynset> getHyponyms(ISynset iSynset) {
		return get(iSynset, Pointer.HYPONYM);
	}

	public List<ISynset> getHypernyms(ISynset iSynset) {
		return get(iSynset, Pointer.HYPERNYM);
	}

	private List<ISynset> get(ISynset iSynset, Pointer pointer) {
		List<ISynset> synsets = new LinkedList<ISynset>();
		List<ISynsetID> relatedSynsets = iSynset.getRelatedSynsets(pointer);
		if (relatedSynsets != null && !relatedSynsets.isEmpty()){
			//ISynsetID iSynsetID = relatedSynsets.get(0);
			for (ISynsetID iSynsetID : relatedSynsets) {
				synsets.add(dict.getSynset(iSynsetID));
			}
		}
		return synsets;
	}

	public List<TaggedWord> getTaggedWords(WordMeaning meaning) {
		List<TaggedWord> taggedWords = new LinkedList<TaggedWord>();
		ISynset synset = meaning.getSynset();
		List<IWord> words = synset.getWords();
		
		for (IWord iWord : words) {
			taggedWords.add(getTaggedWord(iWord));
		}
		
		return taggedWords;
	}

	public TaggedWord getTaggedWord(IWord iWord) {
		return new TaggedWord(iWord.getLemma(), transformator.getShortStanfordTag(iWord.getPOS().toString()));
	}

	public TaggedWord getTaggedWord(IIndexWord word) {
		return  new TaggedWord(word.getLemma(), transformator.getShortStanfordTag(word.getPOS().toString()));
	}

	public List<TaggedWord> getTaggedWords(List<IWord> words) {
		List<TaggedWord> taggedWords = new LinkedList<TaggedWord>();
		
		for (IWord word: words) {
			taggedWords.add(getTaggedWord(word));
		}
		
		return taggedWords;
	}

	public List<TaggedWord> getTaggedWords(ISynset synset) {
		return getTaggedWords(synset.getWords());
	}
	
}
