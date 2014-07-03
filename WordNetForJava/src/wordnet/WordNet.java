package wordnet;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import util.Pair;
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
	
	public Iterator<IIndexWord> getWords(POS pos){
		return dict.getIndexWordIterator(pos);
	}

	public List<String> getSteams(String word, POS pos){
		return stemmer.findStems(word, pos);
	}

	public String getSteam(String word, POS pos){
		List<String> findStems = stemmer.findStems(word, pos);
		if (findStems != null && !findStems.isEmpty()) return findStems.get(0);
		else return null;
	}

	public List<Pair<String, ISynset>> getGlosses(IIndexWord word){
		List<Pair<String, ISynset>> list = new LinkedList<Pair<String, ISynset>>();
		for(IWordID wordID : word.getWordIDs()){
			ISynset synset = dict.getSynset(wordID.getSynsetID());
			list.add(new Pair<String, ISynset>(synset.getGloss(), synset));
		}
		return list;
	}
	
	public List<ISynset> getSynonyms(String lemma, POS pos) {
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

	public List<Pair<String, String>> getRelatedWords(String lemma, POS pos){
		List<ISynset> synonyms = getSynonyms(lemma, pos);
		List<Pair<String, String>> tokens = new LinkedList<Pair<String, String>>();
		tokens.addAll(getTokens(synonyms));
		tokens.addAll(getTokens(getNeighbors(synonyms)));
		return tokens;
	}	
	
	private List<Pair<String, String>> getTokens(List<ISynset> synonyms) {
		List<Pair<String, String>> tokens = new LinkedList<Pair<String, String>>();
		for (ISynset iSynset : synonyms) {
			tokens.addAll(getTokens(iSynset));
		}
		return tokens;
	}
	
	public List<Pair<String, String>> getTokens(ISynset synset) {
		List<Pair<String, String>> words = new LinkedList<Pair<String, String>>();
		List<IWord> wordsI = synset.getWords();
		for (IWord iWord : wordsI) {
			String lemma = iWord.getLemma();
			words.add(new Pair(lemma, transformPos(iWord.getPOS().toString())));
		}
		return words;
	}	
	
	private String transformPos(String pos){
		return Character.isLetter(pos.charAt(0)) ? Character.toString(pos.charAt(0)).toUpperCase() : pos;
	}
	
	public List<ISynset> getNeighbors(List<ISynset> synsets) {
		List<ISynset> nSynsets = new LinkedList<ISynset>();
		for (ISynset iSynset : synsets) {
			nSynsets.addAll(getHypernyms(iSynset));
			nSynsets.addAll(getHyponyms(iSynset));
		}
		return nSynsets;
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

	public IIndexWord getWord(String lemma, POS verb) {
		return dict.getIndexWord(lemma, verb);
	}
}
